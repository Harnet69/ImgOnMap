package com.harnet.mapapp.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class PermissionController {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private Context context;
    private Activity mapActivity;

    public PermissionController(Context context, Activity mapActivity) {
        this.context = context;
        this.mapActivity = mapActivity;
    }

    // check if permission have granted already
    public void checkLocationPermission() {
        Log.i("MapAppCheck", "checkLocationPermission: ");
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(mapActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }
}
