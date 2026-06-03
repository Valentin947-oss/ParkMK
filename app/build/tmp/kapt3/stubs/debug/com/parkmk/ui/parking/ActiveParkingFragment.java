package com.parkmk.ui.parking;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\u001a\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0015H\u0002J\u0010\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u0006H\u0002J\b\u0010\u001f\u001a\u00020\u0015H\u0002J\b\u0010 \u001a\u00020\u0015H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/parkmk/ui/parking/ActiveParkingFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_b", "Lcom/parkmk/databinding/FragmentActiveParkingBinding;", "activeVehicle", "Lcom/parkmk/model/Vehicle;", "b", "getB", "()Lcom/parkmk/databinding/FragmentActiveParkingBinding;", "handler", "Landroid/os/Handler;", "smsSent", "", "spot", "Lcom/parkmk/model/ParkingSpot;", "startMs", "", "ticker", "Ljava/lang/Runnable;", "loadVehiclesFromFirestore", "", "onDestroyView", "onViewCreated", "view", "Landroid/view/View;", "savedInstanceState", "Landroid/os/Bundle;", "sendSms", "setVehicle", "vehicle", "showVehiclePicker", "stopParking", "app_debug"})
public final class ActiveParkingFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.parkmk.databinding.FragmentActiveParkingBinding _b;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler handler = null;
    private long startMs = 0L;
    private boolean smsSent = false;
    private com.parkmk.model.ParkingSpot spot;
    @org.jetbrains.annotations.Nullable()
    private com.parkmk.model.Vehicle activeVehicle;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Runnable ticker = null;
    
    public ActiveParkingFragment() {
        super();
    }
    
    private final com.parkmk.databinding.FragmentActiveParkingBinding getB() {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadVehiclesFromFirestore() {
    }
    
    private final void setVehicle(com.parkmk.model.Vehicle vehicle) {
    }
    
    private final void showVehiclePicker() {
    }
    
    private final void sendSms() {
    }
    
    private final void stopParking() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}