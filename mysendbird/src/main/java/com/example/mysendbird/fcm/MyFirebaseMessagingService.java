package com.example.mysendbird.fcm;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mysendbird.utils.PrefUtils;
import com.example.mysendbird.utils.PushUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sendbird.calls.SendBirdCall;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (SendBirdCall.handleFirebaseMessageData(remoteMessage.getData())) {
            Log.i("[Module]", "[MyFirebaseMessagingService] onMessageReceived() => " + remoteMessage.getData().toString());
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.i("[Module]", "[MyFirebaseMessagingService] onNewToken(token: " + token + ")");

        if (SendBirdCall.getCurrentUser() != null)  {
            PushUtils.registerPushToken(getApplicationContext(), token, e -> {
                if (e != null) {
                    Log.i("[Module]", "[MyFirebaseMessagingService] registerPushTokenForCurrentUser() => e: " + e.getMessage());
                }
            });
        } else {
            PrefUtils.setPushToken(getApplicationContext(), token);
        }
    }
}
