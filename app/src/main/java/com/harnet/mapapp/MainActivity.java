package com.harnet.mapapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MapsFragment.OnMessageSendListener {
    private Fragment fragment;
    private Bundle exchangeBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exchangeBundle = new Bundle();
        //display default fragment1
        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContFrameLayout, new MapsFragment())
                    .commit();
        }
    }

    @Override
    public void onMessageSend(String message) {
        exchangeBundle.putString("message", message);
//        System.out.println(message);
    }
}