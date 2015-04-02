package com.example.android.androidwearjderobot;

import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;


public class ListenerServiceFromWear extends WearableListenerService {

    private static final String HELLO_WORLD_WEAR_PATH = "/hello-world-wear";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

//        /*
//         * Receive the message from wear
//         */
//        if (messageEvent.getPath().equals(HELLO_WORLD_WEAR_PATH)) {
//
//            Intent startIntent = new Intent(this, SecondActivity.class);
//            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(startIntent);
//            //showToast(messageEvent.getPath());
//        }
        Intent startIntent = new Intent(this, MainActivity2Activity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startIntent);
       //showToast(messageEvent.getPath());


    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


}