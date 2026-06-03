package com.parkmk.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00a0\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J.\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u001bJ\b\u0010\u001c\u001a\u00020\u001dH\u0002J&\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010$J&\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\b2\u0006\u0010(\u001a\u00020\u00122\u0006\u0010)\u001a\u00020\u0012H\u0082@\u00a2\u0006\u0002\u0010*J\u001e\u0010+\u001a\n -*\u0004\u0018\u00010,0,2\u0006\u0010.\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010/J\u000e\u00100\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u00101J\u0014\u00102\u001a\b\u0012\u0004\u0012\u00020\u001f03H\u0086@\u00a2\u0006\u0002\u00101J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020!032\u0006\u00105\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010/J\u0010\u00106\u001a\u0004\u0018\u000107H\u0086@\u00a2\u0006\u0002\u00101J\u0014\u00108\u001a\b\u0012\u0004\u0012\u00020\u001603H\u0086@\u00a2\u0006\u0002\u00101J\u000e\u00109\u001a\u00020&2\u0006\u0010:\u001a\u00020\u0012J.\u0010;\u001a\u00020&2\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u0010#\u001a\u00020\u00122\u0006\u0010<\u001a\u00020\u00122\u0006\u0010=\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u000e\u0010>\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001f0?J\u000e\u0010@\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001070?J\u0012\u0010A\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u0016030?J.\u0010B\u001a\u00020\b2\u0006\u0010C\u001a\u00020\u00122\u0006\u0010D\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00122\u0006\u0010)\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u001e\u0010E\u001a\n -*\u0004\u0018\u00010,0,2\u0006\u0010C\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010/J\b\u0010F\u001a\u00020\u001dH\u0002J\u0016\u0010G\u001a\u00020&2\u0006\u0010.\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010/J\u000e\u0010H\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u00101J\u001e\u0010I\u001a\u00020\b2\u0006\u0010C\u001a\u00020\u00122\u0006\u0010D\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010JJ\u0016\u0010K\u001a\u00020\b2\u0006\u0010L\u001a\u00020MH\u0086@\u00a2\u0006\u0002\u0010NJ\u0016\u0010O\u001a\u00020\b2\u0006\u0010P\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010/J\u0006\u0010Q\u001a\u00020&J\b\u0010R\u001a\u00020\u001dH\u0002J\u0010\u0010S\u001a\u00020\u001d2\u0006\u00105\u001a\u00020\u0012H\u0002J&\u0010T\u001a\u00020&2\u0006\u0010=\u001a\u00020\u00122\u0006\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00020XH\u0086@\u00a2\u0006\u0002\u0010YJ\u001e\u0010Z\u001a\u0010\u0012\f\u0012\n -*\u0004\u0018\u00010,0,0[2\u0006\u0010\\\u001a\u00020\u0012H\u0002J\u0016\u0010]\u001a\u00020&2\u0006\u0010L\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010/J\b\u0010^\u001a\u00020_H\u0002J\b\u0010`\u001a\u00020\u001dH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b8F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u000fR\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006a"}, d2 = {"Lcom/parkmk/data/repository/FirebaseRepository;", "", "()V", "analytics", "Lcom/google/firebase/analytics/FirebaseAnalytics;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "currentUser", "Lcom/google/firebase/auth/FirebaseUser;", "getCurrentUser", "()Lcom/google/firebase/auth/FirebaseUser;", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "isAnonymous", "", "()Z", "isLoggedIn", "uid", "", "getUid", "()Ljava/lang/String;", "addVehicle", "Lcom/parkmk/model/Vehicle;", "plate", "displayName", "type", "colorHex", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "citiesRef", "Lcom/google/firebase/firestore/CollectionReference;", "createSession", "Lcom/parkmk/model/ParkingSession;", "spot", "Lcom/parkmk/model/ParkingSpot;", "vehicle", "smsNumber", "(Lcom/parkmk/model/ParkingSpot;Lcom/parkmk/model/Vehicle;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createUserDocument", "", "user", "name", "phone", "(Lcom/google/firebase/auth/FirebaseUser;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteVehicle", "Ljava/lang/Void;", "kotlin.jvm.PlatformType", "vehicleId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFcmToken", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSessionHistory", "", "getSpotsForCity", "cityId", "getUser", "Lcom/parkmk/model/AppUser;", "getVehicles", "logScreenView", "screenName", "logSms", "smsBody", "sessionId", "observeActiveSession", "Lkotlinx/coroutines/flow/Flow;", "observeUser", "observeVehicles", "registerWithEmail", "email", "password", "sendPasswordResetEmail", "sessionsRef", "setVehicleActive", "signInAnonymously", "signInWithEmail", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signInWithFacebook", "token", "Lcom/facebook/AccessToken;", "(Lcom/facebook/AccessToken;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signInWithGoogle", "idToken", "signOut", "smsLogRef", "spotsRef", "stopSession", "durationSeconds", "", "totalCost", "", "(Ljava/lang/String;JDLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "subscribeToTopic", "Lcom/google/android/gms/tasks/Task;", "topic", "updateFcmToken", "userRef", "Lcom/google/firebase/firestore/DocumentReference;", "vehiclesRef", "app_debug"})
public final class FirebaseRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.analytics.FirebaseAnalytics analytics = null;
    
    public FirebaseRepository() {
        super();
    }
    
    public final boolean isLoggedIn() {
        return false;
    }
    
    public final boolean isAnonymous() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.firebase.auth.FirebaseUser getCurrentUser() {
        return null;
    }
    
    private final java.lang.String getUid() {
        return null;
    }
    
    private final com.google.firebase.firestore.DocumentReference userRef() {
        return null;
    }
    
    private final com.google.firebase.firestore.CollectionReference vehiclesRef() {
        return null;
    }
    
    private final com.google.firebase.firestore.CollectionReference sessionsRef() {
        return null;
    }
    
    private final com.google.firebase.firestore.CollectionReference citiesRef() {
        return null;
    }
    
    private final com.google.firebase.firestore.CollectionReference spotsRef(java.lang.String cityId) {
        return null;
    }
    
    private final com.google.firebase.firestore.CollectionReference smsLogRef() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object signInAnonymously(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.google.firebase.auth.FirebaseUser> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object signInWithEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.google.firebase.auth.FirebaseUser> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object registerWithEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    java.lang.String displayName, @org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.google.firebase.auth.FirebaseUser> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object signInWithGoogle(@org.jetbrains.annotations.NotNull()
    java.lang.String idToken, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.google.firebase.auth.FirebaseUser> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object signInWithFacebook(@org.jetbrains.annotations.NotNull()
    com.facebook.AccessToken token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.google.firebase.auth.FirebaseUser> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendPasswordResetEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Void> $completion) {
        return null;
    }
    
    public final void signOut() {
    }
    
    private final java.lang.Object createUserDocument(com.google.firebase.auth.FirebaseUser user, java.lang.String name, java.lang.String phone, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUser(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.parkmk.model.AppUser> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.parkmk.model.AppUser> observeUser() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateFcmToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.parkmk.model.Vehicle>> observeVehicles() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getVehicles(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.parkmk.model.Vehicle>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addVehicle(@org.jetbrains.annotations.NotNull()
    java.lang.String plate, @org.jetbrains.annotations.NotNull()
    java.lang.String displayName, @org.jetbrains.annotations.NotNull()
    java.lang.String type, @org.jetbrains.annotations.NotNull()
    java.lang.String colorHex, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.parkmk.model.Vehicle> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setVehicleActive(@org.jetbrains.annotations.NotNull()
    java.lang.String vehicleId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteVehicle(@org.jetbrains.annotations.NotNull()
    java.lang.String vehicleId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Void> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createSession(@org.jetbrains.annotations.NotNull()
    com.parkmk.model.ParkingSpot spot, @org.jetbrains.annotations.NotNull()
    com.parkmk.model.Vehicle vehicle, @org.jetbrains.annotations.NotNull()
    java.lang.String smsNumber, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.parkmk.model.ParkingSession> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.parkmk.model.ParkingSession> observeActiveSession() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object stopSession(@org.jetbrains.annotations.NotNull()
    java.lang.String sessionId, long durationSeconds, double totalCost, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getSessionHistory(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.parkmk.model.ParkingSession>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getSpotsForCity(@org.jetbrains.annotations.NotNull()
    java.lang.String cityId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.parkmk.model.ParkingSpot>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object logSms(@org.jetbrains.annotations.NotNull()
    java.lang.String plate, @org.jetbrains.annotations.NotNull()
    java.lang.String smsNumber, @org.jetbrains.annotations.NotNull()
    java.lang.String smsBody, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getFcmToken(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final com.google.android.gms.tasks.Task<java.lang.Void> subscribeToTopic(java.lang.String topic) {
        return null;
    }
    
    public final void logScreenView(@org.jetbrains.annotations.NotNull()
    java.lang.String screenName) {
    }
}