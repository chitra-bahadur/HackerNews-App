package com.urbanpiperapp.net;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.urbanpiperapp.ui.activities.MainActivity;
import com.urbanpiperapp.utils.NotificationUtils;

import java.util.Date;

/**
 * Created by chitra on 12/1/18.
 */

public class FirebaseMessageService extends FirebaseMessagingService {

    private static final String TAG = FirebaseMessageService.class.getSimpleName();
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null) {
            return;
        }

        String title = "";
        String messageBody = "";
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            messageBody = remoteMessage.getNotification().getBody();
        } else {
            title = remoteMessage.getData().get("title");
            messageBody = remoteMessage.getData().get("message");
        }
        Intent intent = new Intent(this, MainActivity.class);
        showNotificationMessage(getApplicationContext(), title, messageBody, String.valueOf(new Date()),intent);
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent);
    }
}
