package com.parkmk.ui.map;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0014H\u0002J\b\u0010\u0016\u001a\u00020\u0014H\u0016J\b\u0010\u0017\u001a\u00020\u0014H\u0016J\b\u0010\u0018\u001a\u00020\u0014H\u0016J\u001a\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0014H\u0002J\u0010\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0012H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0010\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u000f0\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/parkmk/ui/map/MapFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_b", "Lcom/parkmk/databinding/FragmentMapBinding;", "b", "getB", "()Lcom/parkmk/databinding/FragmentMapBinding;", "bottomSheetBehavior", "Lcom/google/android/material/bottomsheet/BottomSheetBehavior;", "Landroid/view/View;", "locationOverlay", "Lorg/osmdroid/views/overlay/mylocation/MyLocationNewOverlay;", "locationPermission", "Landroidx/activity/result/ActivityResultLauncher;", "", "kotlin.jvm.PlatformType", "selectedSpot", "Lcom/parkmk/model/ParkingSpot;", "addParkingMarkers", "", "enableMyLocation", "onDestroyView", "onPause", "onResume", "onViewCreated", "view", "savedInstanceState", "Landroid/os/Bundle;", "setupLocation", "showSpotDetail", "spot", "app_debug"})
public final class MapFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.parkmk.databinding.FragmentMapBinding _b;
    @org.jetbrains.annotations.Nullable()
    private com.parkmk.model.ParkingSpot selectedSpot;
    private com.google.android.material.bottomsheet.BottomSheetBehavior<android.view.View> bottomSheetBehavior;
    private org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay locationOverlay;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> locationPermission = null;
    
    public MapFragment() {
        super();
    }
    
    private final com.parkmk.databinding.FragmentMapBinding getB() {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupLocation() {
    }
    
    private final void enableMyLocation() {
    }
    
    private final void addParkingMarkers() {
    }
    
    private final void showSpotDetail(com.parkmk.model.ParkingSpot spot) {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}