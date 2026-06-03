package com.parkmk.ui.parking;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\b\u001a\u00020\tJ\u001e\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\u0011\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ0\u0010\u0012\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/parkmk/ui/parking/ParkingNotificationManager;", "", "()V", "handler", "Landroid/os/Handler;", "notif1Runnable", "Ljava/lang/Runnable;", "notif2Runnable", "cancelAll", "", "scheduleNotifications", "context", "Landroid/content/Context;", "spotName", "", "durationSeconds", "", "scheduleTestNotification", "showNotification", "manager", "Landroid/app/NotificationManager;", "id", "", "title", "body", "app_debug"})
public final class ParkingNotificationManager {
    @org.jetbrains.annotations.NotNull()
    private static final android.os.Handler handler = null;
    @org.jetbrains.annotations.Nullable()
    private static java.lang.Runnable notif1Runnable;
    @org.jetbrains.annotations.Nullable()
    private static java.lang.Runnable notif2Runnable;
    @org.jetbrains.annotations.NotNull()
    public static final com.parkmk.ui.parking.ParkingNotificationManager INSTANCE = null;
    
    private ParkingNotificationManager() {
        super();
    }
    
    public final void scheduleTestNotification(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String spotName) {
    }
    
    public final void scheduleNotifications(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String spotName, long durationSeconds) {
    }
    
    private final void showNotification(android.content.Context context, android.app.NotificationManager manager, int id, java.lang.String title, java.lang.String body) {
    }
    
    public final void cancelAll() {
    }
}