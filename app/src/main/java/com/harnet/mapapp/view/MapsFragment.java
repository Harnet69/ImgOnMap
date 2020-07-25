package com.harnet.mapapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
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

import java.util.Objects;

public class MapsFragment extends Fragment  {
    private OnMessageSendListener onMessageSendListener;

    private MapController mapController;
    private PermissionController permissionController;
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
            mapController = new MapController(googleMap);
            mapController.setGoogleMap(warsaw);
            permissionController.checkLocationPermission();

            locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            provider = locationManager.getBestProvider(new Criteria(), false);

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d("MapAppCheck", "onLocationChanged: " + location.toString());
                    mapController.onPostExecute(location.getLatitude(), location.getLongitude());
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            // update coordinates
            locationManager.requestLocationUpdates(provider, 10000, 0, locationListener);

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        permissionController = new PermissionController(getContext(), getActivity());
        return inflater.inflate(R.layout.fragment_maps, container, false);
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