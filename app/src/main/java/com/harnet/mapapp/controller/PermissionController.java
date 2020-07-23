package com.harnet.mapapp.controller;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionController {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private Context context;
    private Activity mapActivity;

    public PermissionController(Context context, Activity mapActivity) {
        this.context = context;
        this.mapActivity = mapActivity;
    }

    // check if permission have granted already, if didn't - asks for it
    public void checkLocationPermission() {
        Log.i("MapAppCheck", "checkLocationPermission: ");
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mapActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                alertDialog();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(mapActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    private void alertDialog(){
        new AlertDialog.Builder(context)
                .setTitle("Location permission")
                .setMessage("App need your permission")
                .setPositiveButton("Give", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Prompt the user once explanation has been shown
                        ActivityCompat.requestPermissions(mapActivity,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION);
                    }
                })
                .create()
                .show();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//        System.out.println("DONE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
//                    Log.i("MapAppCheck", "onRequestPermissionsResult: WORK");
                        return;
                    }
                    //TODO update user location(you can define time or distance for saving battery life)
                    // refresh activity after permission granted
                    mapActivity.finish();
//                    startActivity(getIntent());
//                    // location-related task you need to do.
//                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                        //Request location updates:
//                        if(provider != null){
//                            locationManager.requestLocationUpdates(provider, 10000, 0, locationListener);
//                        }
//                    }
//                } else {
//                    // permission denied, boo! Disable a functionality that depends on this permission.
//                    Intent mainActivityIntent = new Intent(this, MainActivity.class);
//                    startActivity(mainActivityIntent);
                }
                return;
            }
        }
    }

}
