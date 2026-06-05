package com.parkmk.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0006\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\bJ\u001e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fJ\u001e\u0010\u0010\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\bJ\u000e\u0010\u0017\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\bJ\u0016\u0010\u0018\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bJ\u000e\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\bJ\u0006\u0010\u001c\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/parkmk/util/AnalyticsHelper;", "", "()V", "analytics", "Lcom/google/firebase/analytics/FirebaseAnalytics;", "logLanguageChanged", "", "language", "", "logLogin", "method", "logParkingStarted", "spotName", "zone", "pricePerHour", "", "logParkingStopped", "durationSec", "", "totalCost", "", "logScreen", "screenName", "logSignUp", "logSmsSent", "plate", "logVehicleAdded", "type", "logVehicleDeleted", "app_debug"})
public final class AnalyticsHelper {
    @org.jetbrains.annotations.NotNull()
    private static final com.google.firebase.analytics.FirebaseAnalytics analytics = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.parkmk.util.AnalyticsHelper INSTANCE = null;
    
    private AnalyticsHelper() {
        super();
    }
    
    public final void logScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String screenName) {
    }
    
    public final void logLogin(@org.jetbrains.annotations.NotNull()
    java.lang.String method) {
    }
    
    public final void logSignUp(@org.jetbrains.annotations.NotNull()
    java.lang.String method) {
    }
    
    public final void logParkingStarted(@org.jetbrains.annotations.NotNull()
    java.lang.String spotName, @org.jetbrains.annotations.NotNull()
    java.lang.String zone, int pricePerHour) {
    }
    
    public final void logParkingStopped(@org.jetbrains.annotations.NotNull()
    java.lang.String spotName, long durationSec, double totalCost) {
    }
    
    public final void logSmsSent(@org.jetbrains.annotations.NotNull()
    java.lang.String zone, @org.jetbrains.annotations.NotNull()
    java.lang.String plate) {
    }
    
    public final void logVehicleAdded(@org.jetbrains.annotations.NotNull()
    java.lang.String type) {
    }
    
    public final void logVehicleDeleted() {
    }
    
    public final void logLanguageChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String language) {
    }
}