package com.harnet.mapapp.controller;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.harnet.mapapp.R;

public class MapController {
    private GoogleMap mMap;
    private Marker marker;

    public MapController(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public void setGoogleMap(LatLng cityCoord){
        // zoom the map between 1 and 20
        int mapScale = 12;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLong));
        // Zoom in camera to a user location
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(16f));
        // zoom in to appropriate
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 12));
    }
}
