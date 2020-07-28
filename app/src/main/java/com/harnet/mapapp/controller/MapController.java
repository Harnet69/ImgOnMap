package com.harnet.mapapp.controller;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.harnet.mapapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapController {
    private GoogleMap mMap;
    private Context context;
    private Marker marker;

    private Geocoder geocoder;
    private List<Address> addresses;


    public MapController(GoogleMap mMap, Context context) {
        this.mMap = mMap;
        this.context = context;
        geocoder = new Geocoder(context, Locale.getDefault());
    }

    public void setGoogleMap(LatLng cityCoord){
        // zoom the map between 1 and 20
        int mapScale = 12;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityCoord, mapScale));
    }

    public void onPostExecute(double lat, double lng) {
        LatLng latLong = new LatLng(lat, lng);
        if (marker == null) {
            MarkerOptions options = new MarkerOptions().position(latLong)
                    .title("User")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_marker));
            marker = mMap.addMarker(options);
//            mMap.addMarker(new MarkerOptions().position(placeFromQR).title("Marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        }
        else {
            marker.setPosition(latLong);
        }

        // get addresses from coordinates
        getAddresses(lat, lng);
        if(addresses != null && addresses.size() > 0){
            Log.i("MapAppCheck", "Addresses on this coordinates: " + addresses.toString());
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLong));
        // Zoom in camera to a user location
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(16f));
        // zoom in to appropriate
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 12));
    }

    private void getAddresses(double lat, double lng){
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
