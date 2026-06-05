package com.parkmk.util

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object AnalyticsHelper {

    private val analytics = Firebase.analytics

    // Screen views
    fun logScreen(screenName: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    // Auth events
    fun logLogin(method: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.METHOD, method)
        }
        analytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
    }

    fun logSignUp(method: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.METHOD, method)
        }
        analytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle)
    }

    // Parking events
    fun logParkingStarted(spotName: String, zone: String, pricePerHour: Int) {
        val bundle = Bundle().apply {
            putString("spot_name", spotName)
            putString("zone", zone)
            putInt("price_per_hour", pricePerHour)
        }
        analytics.logEvent("parking_started", bundle)
    }

    fun logParkingStopped(spotName: String, durationSec: Long, totalCost: Double) {
        val bundle = Bundle().apply {
            putString("spot_name", spotName)
            putLong("duration_seconds", durationSec)
            putDouble("total_cost", totalCost)
        }
        analytics.logEvent("parking_stopped", bundle)
    }

    fun logSmsSent(zone: String, plate: String) {
        val bundle = Bundle().apply {
            putString("zone", zone)
            putString("plate", plate)
        }
        analytics.logEvent("sms_sent", bundle)
    }

    // Vehicle events
    fun logVehicleAdded(type: String) {
        val bundle = Bundle().apply {
            putString("vehicle_type", type)
        }
        analytics.logEvent("vehicle_added", bundle)
    }

    fun logVehicleDeleted() {
        analytics.logEvent("vehicle_deleted", null)
    }

    // Language event
    fun logLanguageChanged(language: String) {
        val bundle = Bundle().apply {
            putString("language", language)
        }
        analytics.logEvent("language_changed", bundle)
    }
}