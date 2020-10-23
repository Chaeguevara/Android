package com.example.ws;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    FragmentMap fragmentMap;
    FragmentMovie fragmentMovie;

    ActionBar actionBar;

    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define and ask permission to user
        String [] permission = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
        ActivityCompat.requestPermissions(this,permission,101);

        //Assign & define fragments
        fragmentMap = (FragmentMap) getSupportFragmentManager().findFragmentById(
                R.id.fragment
        );
        fragmentMovie = new FragmentMovie();

        // set Action bar
        actionBar = getSupportActionBar();
        actionBar.setTitle("Workshop");
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_USE_LOGO);

        // get network status and change Logo
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String cmd = intent.getAction();
                ConnectivityManager cm = null;
                NetworkInfo mobile = null;
                NetworkInfo wifi = null;
                if(cmd.equals("android.net.conn.CONNECTIVITY_CHANGE")){
                    cm = (ConnectivityManager) context.getSystemService(
                            Context.CONNECTIVITY_SERVICE
                    );
                    mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                }
                if(mobile != null && mobile.isConnected()){
                    //do something when the app is connected to mobile
                }else if(wifi != null && wifi.isConnected()){
                    //when wifi is connected, set actionbar logo
                    actionBar.setLogo(R.drawable.wifi);
                }else{
                    actionBar.setLogo(R.drawable.wifi_no);
                }

            }
        };
        registerReceiver(broadcastReceiver,intentFilter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    public void ckbt(View v){
        if(v.getId() == R.id.button){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment,fragmentMap
            ).commit();
        }else if(v.getId() == R.id.button2) {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment,fragmentMovie
            ).commit();
            Log.d("[BT2]","BT2 click");
        }
    }
}