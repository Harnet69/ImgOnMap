package com.harnet.mapapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.harnet.mapapp.R;
import com.harnet.mapapp.controller.PermissionController;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MapsFragment.OnMessageSendListener, MainFragment.OnMessageSendListener {
    private Fragment fragment;
    private Bundle exchangeBundle;

    private Button whereBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        whereBtn = findViewById(R.id.where_button);

        exchangeBundle = new Bundle();
        //display default fragment1
        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContFrameLayout, new MainFragment())
                    .commit();
        }
    }

    // It's necessary to override method here to handle permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("MapAppCheck", "onRequestPermissionsResult: called from activity");
        finish();
        startActivity(getIntent());
        PermissionController.getInstance().onRequestPermissionsResult(requestCode, grantResults);
    }

    @Override
    public void onMessageSend(String message) {
        exchangeBundle.putString("message", message);
        if(message.equals("Map")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContFrameLayout, new MapsFragment())
                    .commit();
        }
    }
}