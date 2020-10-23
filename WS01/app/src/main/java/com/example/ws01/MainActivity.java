package com.example.ws01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    ActionBar actionBar;
    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;

    NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permission pop up
        String [] permission = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE
        };
        ActivityCompat.requestPermissions(this,permission,101);


        //Create & Assign fragments
        fragment1 =
                (Fragment1)getSupportFragmentManager().findFragmentById(
                        R.id.fragment
                );
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
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

        //Firebase
        FirebaseMessaging.getInstance().subscribeToTopic("car").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg = "FCM Complete ...";
                if(!task.isSuccessful()){
                    msg = "FCM Fail";
                }
                Log.d("[TAG]:",msg);
            }
        });

        // get something with name "notification"
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver,new IntentFilter("notification"));
    }// end on create

    public BroadcastReceiver receiver = new BroadcastReceiver(){
        //receive data
        @Override
        public void onReceive(Context context, Intent intent) {
            String title = null;
            String control = null;
            String data = null;
            String hi = null;

            //Show data on Toast
            if(intent != null){
                title = intent.getStringExtra("title");
                control = intent.getStringExtra("control");
                data = intent.getStringExtra("data");
                hi = intent.getStringExtra("hi");
                Toast.makeText(MainActivity.this,title+" "+ control +" "+data,Toast.LENGTH_SHORT).show();
            }

            //Vibrate device
            Vibrator vibrator =
                    (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if(Build.VERSION.SDK_INT >= 26){
                vibrator.vibrate(VibrationEffect.createOneShot(1000,10));
            }else{
                vibrator.vibrate(1000);
            }

            //play music
            MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.mp);
            player.start();
            //notification
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = null;
            if(Build.VERSION.SDK_INT >= 26){
                if(manager.getNotificationChannel("ch2") == null){
                    manager.createNotificationChannel(
                            new NotificationChannel("ch2","chname2",NotificationManager.IMPORTANCE_DEFAULT));
                    builder = new NotificationCompat.Builder(context,"ch2");
                }
                builder = new NotificationCompat.Builder(context,"ch2");
            }else{
                builder = new NotificationCompat.Builder(context);
            }

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,101,intent,PendingIntent.FLAG_UPDATE_CURRENT
            );

            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
            builder.setContentTitle(title);
            builder.setContentText(control+" "+data+" "+hi);
            builder.setSmallIcon(R.drawable.wifi);
            Notification noti = builder.build();
            manager.notify(1,noti);

        }
    };

    //Click and change fragments
    public void ckbt(View v){
        if(v.getId() == R.id.button){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment, fragment1
            ).commit();
        }else if(v.getId() == R.id.button2){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment, fragment2
            ).commit();
        }else if(v.getId() == R.id.button3){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment,fragment3
            ).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }
}