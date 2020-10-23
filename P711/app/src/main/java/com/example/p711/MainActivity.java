package com.example.p711;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    TextView tx;
    NotificationManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx = findViewById(R.id.tx);
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
    } // end onCreate

    public BroadcastReceiver receiver = new BroadcastReceiver(){
        //receive data
        @Override
        public void onReceive(Context context, Intent intent) {
            String title = null;
            String control = null;
            String data = null;
            String hi = null;

            //Show data on textview
            if(intent != null){
                title = intent.getStringExtra("title");
                control = intent.getStringExtra("control");
                data = intent.getStringExtra("data");
                hi = intent.getStringExtra("hi");
                tx.setText(control+" "+data+" "+hi);
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
            builder.setSmallIcon(R.drawable.d3);
            Notification noti = builder.build();
            manager.notify(1,noti);

        }
    };
}