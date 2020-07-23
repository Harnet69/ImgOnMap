package com.harnet.mapapp.controller;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class MapController {

    public void setGoogleMap(GoogleMap mMap, LatLng cityCoord){
        // zoom the map between 1 and 20
        int mapScale = 12;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityCoord, mapScale));
    }


}
