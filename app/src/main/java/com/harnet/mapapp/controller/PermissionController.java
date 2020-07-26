package com.harnet.mapapp.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
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

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        Log.i("MapAppCheck", "onRequestPermissionsResult: ");
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    //TODO refresh activity after permission granted

                    Log.i("MapAppCheck", "onRequestPermissionsResult: Permission was granted");
//                        mapActivity.finish();
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        //Request location updates:
//                        if(provider != null){
//                            locationManager.requestLocationUpdates(provider, 10000, 0, locationListener);
//                        }
                    }
                } else {
                    // permission denied, boo! Disable a functionality that depends on this permission.
//                    Intent mainActivityIntent = new Intent(this, MainActivity.class);
//                    startActivity(mainActivityIntent);
                    Log.i("MapAppCheck", "onRequestPermissionsResult: Permission denied");
                }
                return;
            }
        }
    }
}
