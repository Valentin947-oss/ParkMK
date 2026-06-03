package com.parkmk.data.repository

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.*
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.parkmk.model.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseRepository {

    private val db        = Firebase.firestore
    private val auth      = FirebaseAuth.getInstance()
    private val analytics = Firebase.analytics

    val isLoggedIn: Boolean get() = auth.currentUser != null
    val isAnonymous: Boolean get() = auth.currentUser?.isAnonymous == true
    val currentUser: FirebaseUser? get() = auth.currentUser
    private val uid: String get() = auth.currentUser?.uid ?: error("Not authenticated")

    private fun userRef()    = db.collection("users").document(uid)
    private fun vehiclesRef()= userRef().collection("vehicles")
    private fun sessionsRef()= userRef().collection("sessions")
    private fun citiesRef()  = db.collection("Citites")
    private fun spotsRef(cityId: String) = citiesRef().document(cityId).collection("spots")
    private fun smsLogRef()  = db.collection("sms_log")

    // ── AUTH ──────────────────────────────────────────────────────────────────

    suspend fun signInAnonymously(): FirebaseUser {
        val result = auth.signInAnonymously().await()
        analytics.logEvent(FirebaseAnalytics.Event.LOGIN) { param("method", "anonymous") }
        return result.user!!
    }

    suspend fun signInWithEmail(email: String, password: String): FirebaseUser {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        analytics.logEvent(FirebaseAnalytics.Event.LOGIN) { param("method", "email") }
        return result.user!!
    }

    suspend fun registerWithEmail(email: String, password: String, displayName: String, phone: String): FirebaseUser {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        result.user!!.updateProfile(
            UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
        ).await()
        createUserDocument(result.user!!, displayName, phone)
        analytics.logEvent(FirebaseAnalytics.Event.SIGN_UP) { param("method", "email") }
        return result.user!!
    }

    suspend fun signInWithGoogle(idToken: String): FirebaseUser {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val result = auth.signInWithCredential(credential).await()
        if (result.additionalUserInfo?.isNewUser == true)
            createUserDocument(result.user!!, result.user!!.displayName ?: "", "")
        analytics.logEvent(FirebaseAnalytics.Event.LOGIN) { param("method", "google") }
        return result.user!!
    }

    suspend fun signInWithFacebook(token: com.facebook.AccessToken): FirebaseUser {
        val credential = FacebookAuthProvider.getCredential(token.token)
        val result = auth.signInWithCredential(credential).await()
        if (result.additionalUserInfo?.isNewUser == true)
            createUserDocument(result.user!!, result.user!!.displayName ?: "", "")
        analytics.logEvent(FirebaseAnalytics.Event.LOGIN) { param("method", "facebook") }
        return result.user!!
    }

    suspend fun sendPasswordResetEmail(email: String) =
        auth.sendPasswordResetEmail(email).await()

    fun signOut() {
        auth.signOut()
        try { com.facebook.login.LoginManager.getInstance().logOut() } catch (_: Exception) {}
    }

    // ── USER ──────────────────────────────────────────────────────────────────

    private suspend fun createUserDocument(user: FirebaseUser, name: String, phone: String) {
        userRef().set(AppUser(uid = user.uid, displayName = name, email = user.email ?: "", phone = phone)).await()
        subscribeToTopic("all_users")
    }

    suspend fun getUser(): AppUser? =
        userRef().get().await().toObject(AppUser::class.java)

    fun observeUser(): Flow<AppUser?> = callbackFlow {
        val l = userRef().addSnapshotListener { s, _ -> trySend(s?.toObject(AppUser::class.java)) }
        awaitClose { l.remove() }
    }

    suspend fun updateFcmToken(token: String) {
        if (isLoggedIn) userRef().update("fcmToken", token).await()
    }

    // ── VEHICLES ──────────────────────────────────────────────────────────────

    fun observeVehicles(): Flow<List<Vehicle>> = callbackFlow {
        val l = vehiclesRef().orderBy("isActive", Query.Direction.DESCENDING)
            .addSnapshotListener { s, _ -> trySend(s?.toObjects(Vehicle::class.java) ?: emptyList()) }
        awaitClose { l.remove() }
    }

    suspend fun getVehicles(): List<Vehicle> =
        vehiclesRef().orderBy("isActive", Query.Direction.DESCENDING).get().await().toObjects(Vehicle::class.java)

    suspend fun addVehicle(plate: String, displayName: String, type: String, colorHex: String): Vehicle {
        val ref = vehiclesRef().document()
        val v = Vehicle(id = ref.id, plate = plate, plateSms = Vehicle.plateToSms(plate),
            displayName = displayName.ifBlank { plate }, type = type, colorHex = colorHex)
        ref.set(v).await()
        return v
    }

    suspend fun setVehicleActive(vehicleId: String) {
        val batch = db.batch()
        vehiclesRef().get().await().forEach { batch.update(it.reference, "isActive", false) }
        batch.update(vehiclesRef().document(vehicleId), "isActive", true)
        batch.update(userRef(), "activeVehicleId", vehicleId)
        batch.commit().await()
    }

    suspend fun deleteVehicle(vehicleId: String) =
        vehiclesRef().document(vehicleId).delete().await()

    // ── SESSIONS ──────────────────────────────────────────────────────────────

    suspend fun createSession(spot: ParkingSpot, vehicle: Vehicle, smsNumber: String): ParkingSession {
        val ref = sessionsRef().document()
        val smsBody = "${spot.zoneLetter} ${vehicle.plateSms}"
        val session = ParkingSession(
            id = ref.id, cityId = "bitola", cityName = "Битола",
            spotId = spot.id, spotName = spot.name,
            zoneName = spot.zoneName, zoneLetter = spot.zoneLetter,
            vehicleId = vehicle.id, plate = vehicle.plate,
            pricePerHour = spot.pricePerHour, smsNumber = smsNumber, smsBody = smsBody,
            status = SessionStatus.ACTIVE.name
        )
        ref.set(session).await()
        analytics.logEvent("parking_started") {
            param("zone", spot.zoneLetter)
            param("price_per_hour", spot.pricePerHour.toLong())
        }
        return session
    }

    fun observeActiveSession(): Flow<ParkingSession?> = callbackFlow {
        val l = sessionsRef().whereEqualTo("status", SessionStatus.ACTIVE.name).limit(1)
            .addSnapshotListener { s, _ -> trySend(s?.toObjects(ParkingSession::class.java)?.firstOrNull()) }
        awaitClose { l.remove() }
    }

    suspend fun stopSession(sessionId: String, durationSeconds: Long, totalCost: Double) {
        val batch = db.batch()
        batch.update(sessionsRef().document(sessionId), mapOf(
            "status" to SessionStatus.CANCELLED.name,
            "durationSeconds" to durationSeconds,
            "totalCost" to totalCost,
            "endTime" to FieldValue.serverTimestamp()
        ))
        batch.update(userRef(), mapOf(
            "totalParkings" to FieldValue.increment(1),
            "totalSpentDen" to FieldValue.increment(totalCost)
        ))
        batch.commit().await()
        analytics.logEvent("parking_stopped") {
            param("duration_seconds", durationSeconds)
            param("total_cost", totalCost.toLong())
        }
    }

    suspend fun getSessionHistory(): List<ParkingSession> =
        sessionsRef().orderBy("startTime", Query.Direction.DESCENDING).limit(50).get().await().toObjects(ParkingSession::class.java)

    // ── CITIES & SPOTS ────────────────────────────────────────────────────────

    suspend fun getSpotsForCity(cityId: String): List<ParkingSpot> =
        spotsRef(cityId).whereEqualTo("isActive", true).get().await().toObjects(ParkingSpot::class.java)

    // ── SMS LOG ───────────────────────────────────────────────────────────────

    suspend fun logSms(plate: String, smsNumber: String, smsBody: String, sessionId: String) {
        smsLogRef().add(mapOf(
            "userId" to uid, "plate" to plate, "smsNumber" to smsNumber,
            "smsBody" to smsBody, "sessionId" to sessionId,
            "sentAt" to FieldValue.serverTimestamp(), "platform" to "android"
        )).await()
    }

    // ── FCM ───────────────────────────────────────────────────────────────────

    suspend fun getFcmToken(): String = FirebaseMessaging.getInstance().token.await()

    private fun subscribeToTopic(topic: String) =
        FirebaseMessaging.getInstance().subscribeToTopic(topic)

    fun logScreenView(screenName: String) {
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
    }
}
