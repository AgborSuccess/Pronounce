package com.example.pronouncetwo.ui.dashboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra("notification")) {
            // Get the notification from the intent
            Notification notification = (Notification) intent.getSerializableExtra("notification");

            // Add the notification to the list in the Singleton data class
            NotificationData data = NotificationData.getInstance();
            data.getNotifications().add(notification);

            // Notify the adapter that the data has changed
            data.getAdapter().notifyDataSetChanged();
        }
    }
}

