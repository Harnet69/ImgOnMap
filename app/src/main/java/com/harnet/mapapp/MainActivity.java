package com.harnet.mapapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MapsFragment.OnMessageSendListener, MainFragment.OnMessageSendListener {
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
                    .replace(R.id.fragmentContFrameLayout, new MainFragment())
                    .commit();
        }
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
//        System.out.println(message);
    }
}