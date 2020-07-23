package com.harnet.mapapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.harnet.mapapp.R;
import com.harnet.mapapp.controller.MapController;
import com.harnet.mapapp.controller.PermissionController;

public class MapsFragment extends Fragment {
    private OnMessageSendListener onMessageSendListener;

    private MapController mapController = new MapController();
    private PermissionController permissionController;

    private View mapView;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private String provider;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng warsaw = new LatLng(52.2297, 21.0122);
            googleMap.addMarker(new MarkerOptions().position(warsaw).title("Marker in Warsaw"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(warsaw));
            // set up the initial map
            mapController.setGoogleMap(googleMap, warsaw);
            permissionController.checkLocationPermission();

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mapView = inflater.inflate(R.layout.fragment_maps, container, false);
        permissionController = new PermissionController(getContext(), getActivity());
        return mapView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("MapAppCheck", "onRequestPermissionsResult: ");
        permissionController.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public interface OnMessageSendListener {
        public void onMessageSend(String message);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            onMessageSendListener = (OnMessageSendListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+ "must implemented onMessageSend");
        }
    }
}