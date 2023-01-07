package com.example.pronouncetwo.ui.dashboard;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificationSchedulerService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";
    private static final long INTERVAL = 300000; // 5 minutes
    private List<Notification> mNotifications;
    private Notification_adapter mAdapter;
    private Context mContext;



    private ScheduledExecutorService mScheduledExecutorService;

    public NotificationSchedulerService(List<Notification> mNotifications, Notification_adapter mAdapter, Context mContext) {
        super("NotificationSchedulerService");
        this.mNotifications = mNotifications;
        this.mAdapter = mAdapter;
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mScheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Schedule the task to run periodically
        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                sendNotification();
            }
        }, 0, INTERVAL, TimeUnit.MILLISECONDS);
    }
    private void sendNotification() {
        // Get a random message
        final String[] messages = {"Hello!", "How are you?", "This is a random message"};
        final String message = messages[new Random().nextInt(messages.length)];

        // Create a new notification object
        Notification notification = new Notification("Random Message", message);
        mNotifications.add(notification);


//
//        Intent broadcastIntent = new Intent(NotificationBroadcastReceiver.class.getName());
//        broadcastIntent.putExtra("notification", notification);
//        mContext.sendBroadcast(broadcastIntent);


        // Send the notification using FCM
        sendMessageToTopic(message);



    }


    private void sendMessageToTopic(final String message) {
        // [START send_to_topic]
        // The topic name can be optionally prefixed with "/topics/".
        final String topic = "notifications";

        // See documentation
    }


}