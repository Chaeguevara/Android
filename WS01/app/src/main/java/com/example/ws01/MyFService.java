package com.example.ws01;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFService extends FirebaseMessagingService {
    public MyFService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String control = remoteMessage.getData().get("control");
        String data = remoteMessage.getData().get("data");
        String hi = remoteMessage.getData().get("hi");
        Log.d("[TAG]:",title+" "+control+" "+data);

        // Send data to main activity
        Intent intent = new Intent("notification");
        // put data onto intent
        intent.putExtra("title",title);
        intent.putExtra("control",control);
        intent.putExtra("data",data);
        intent.putExtra("hi",hi);
        // Throw intent to main
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
