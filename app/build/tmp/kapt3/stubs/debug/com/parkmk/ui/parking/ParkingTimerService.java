package com.parkmk.ui.parking;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\u0014\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010\u000f\u001a\u00020\nH\u0016J\"\u0010\u0010\u001a\u00020\u00112\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0011H\u0016J\b\u0010\u0014\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/parkmk/ui/parking/ParkingTimerService;", "Landroid/app/Service;", "()V", "handler", "Landroid/os/Handler;", "ticker", "Ljava/lang/Runnable;", "buildNotification", "Landroid/app/Notification;", "createChannel", "", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onDestroy", "onStartCommand", "", "flags", "startId", "updateNotification", "Companion", "app_debug"})
public final class ParkingTimerService extends android.app.Service {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_ID = "parking_timer_channel";
    public static final int NOTIF_ID = 1001;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_START = "START";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_STOP = "STOP";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_START_MS = "start_ms";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_SPOT = "spot_name";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_SPOT_ID = "spot_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_RATE = "rate";
    private static boolean isRunning = false;
    private static long startMs = 0L;
    @org.jetbrains.annotations.NotNull()
    private static java.lang.String spotName = "";
    @org.jetbrains.annotations.NotNull()
    private static java.lang.String spotId = "s1";
    private static double rate = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler handler = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Runnable ticker = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.parkmk.ui.parking.ParkingTimerService.Companion Companion = null;
    
    public ParkingTimerService() {
        super();
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    private final void updateNotification() {
    }
    
    private final android.app.Notification buildNotification() {
        return null;
    }
    
    private final void createChannel() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\r\n\u0002\u0010\t\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0086T\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001a\"\u0004\b\u001f\u0010\u001cR\u001a\u0010 \u001a\u00020!X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%\u00a8\u0006&"}, d2 = {"Lcom/parkmk/ui/parking/ParkingTimerService$Companion;", "", "()V", "ACTION_START", "", "ACTION_STOP", "CHANNEL_ID", "EXTRA_RATE", "EXTRA_SPOT", "EXTRA_SPOT_ID", "EXTRA_START_MS", "NOTIF_ID", "", "isRunning", "", "()Z", "setRunning", "(Z)V", "rate", "", "getRate", "()D", "setRate", "(D)V", "spotId", "getSpotId", "()Ljava/lang/String;", "setSpotId", "(Ljava/lang/String;)V", "spotName", "getSpotName", "setSpotName", "startMs", "", "getStartMs", "()J", "setStartMs", "(J)V", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final boolean isRunning() {
            return false;
        }
        
        public final void setRunning(boolean p0) {
        }
        
        public final long getStartMs() {
            return 0L;
        }
        
        public final void setStartMs(long p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSpotName() {
            return null;
        }
        
        public final void setSpotName(@org.jetbrains.annotations.NotNull()
        java.lang.String p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSpotId() {
            return null;
        }
        
        public final void setSpotId(@org.jetbrains.annotations.NotNull()
        java.lang.String p0) {
        }
        
        public final double getRate() {
            return 0.0;
        }
        
        public final void setRate(double p0) {
        }
    }
}