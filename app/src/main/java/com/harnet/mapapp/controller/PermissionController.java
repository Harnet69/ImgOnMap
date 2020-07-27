package com.harnet.mapapp.controller;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.harnet.mapapp.view.MainActivity;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class PermissionController {
    private static PermissionController instance = null;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private Context context;
    private Activity mapActivity;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private String provider;

    private PermissionController(Context context, Activity mapActivity, LocationManager locationManager,
                                 LocationListener locationListener, String provider) {
        this.context = context;
        this.mapActivity = mapActivity;
        this.locationManager = locationManager;
        this.locationListener = locationListener;
        this.provider = provider;
    }

    public static PermissionController getInstance(Context context, Activity mapActivity, LocationManager locationManager,
                                                   LocationListener locationListener, String provider) {
        if (instance == null) {
            instance = new PermissionController(context, mapActivity, locationManager, locationListener, provider);
        }
        return instance;
    }

    public static PermissionController getInstance() {
        return instance;
    }

    // check if permission have granted already, if didn't - asks for it
        public void checkLocationPermission() {
        // if user haven't given to us the permission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mapActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                if (!((Activity) context).isFinishing()) {
                    //show dialog
                    alertDialog();
                }
            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(mapActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PermissionController.MY_PERMISSIONS_REQUEST_LOCATION);
                Log.i("MapAppCheck", "checkLocationPermission: ask for permission");
            }
        }
    }

    private void alertDialog() {
        new AlertDialog.Builder(context)
                .setTitle("Location")
                .setMessage("Give the permission")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Prompt the user once explanation has been shown
                        requestPermissions(mapActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PermissionController.MY_PERMISSIONS_REQUEST_LOCATION);
                    }
                })
                .create()
                .show();
    }
    //TODO find where is the trigger of Permission granted

    //    work with the result of permission asking
    public void onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(mapActivity, "Permission granted", Toast.LENGTH_LONG).show();

                    // permission was granted, yay!
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    //TODO update user location(you can define time or distance for saving battery life)
                    // refresh activity after permission granted
//                    finish();
//                    startActivity(getIntent());

                    Log.i("MapAppCheck", "onRequestPermissionsResult: Refresh the page");
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        //Request location updates:
                        if (provider != null) {
                            locationManager.requestLocationUpdates(provider, 10000, 0, locationListener);
                        }
                    }
                } else {
                    // permission denied, boo! Disable a functionality that depends on this permission.
//                    Intent mainActivityIntent = new Intent(this, MainActivity.class);
//                    startActivity(mainActivityIntent);
                    Log.i("MapAppCheck", "onRequestPermissionsResult: Permission denied");
                    Toast.makeText(context, "User location unknown", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}