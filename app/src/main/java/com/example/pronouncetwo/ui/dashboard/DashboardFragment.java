package com.example.pronouncetwo.ui.dashboard;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pronouncetwo.MainActivity;
import com.example.pronouncetwo.R;
import com.example.pronouncetwo.databinding.FragmentDashboardBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private List<Notification> mNotifications;
    private Notification_adapter notification_adapter;
    private ScheduledExecutorService mScheduledExecutorService;
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";
//    private static final long INTERVAL = 300000; // 5 minutes
    private static final long INTERVAL = 30000; // 30 seconds

    private Notification notification;

    private void sendNotification(Context context, String title, String message) {
// Set up the notification intent
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

// Create the notification channel (required on Android 8.0 and above)
        String channelId = "random_notification_channel";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "Random Notifications", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("This channel is for random notifications");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

// Create the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24) // You can use any icon you want
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

// Send the notification
        notificationManager.notify(0, notificationBuilder.build());
    }






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNotifications = new ArrayList<>();
        notification_adapter = new Notification_adapter(getContext(), mNotifications);

//        // Create and start the NotificationSchedulerService
//        Intent serviceIntent = new Intent(getActivity(), NotificationSchedulerService.class);
//        serviceIntent.putExtra("context", getActivity());
//        getActivity().startService(serviceIntent);

        mScheduledExecutorService = Executors.newScheduledThreadPool(1);

    }





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        RecyclerView recyclerView;
        recyclerView = binding.recyclerview;

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        recyclerView.setAdapter(notification_adapter);

        mNotifications.add(new Notification("Title", "Body"));

        final String[] messages = {"Hello!", "How are you?", "This is a random message"};
        final String message = messages[new Random().nextInt(messages.length)];

        // Create a new notification object
        notification = new Notification("Random Message", message);
        mNotifications.add(notification);

        for (int i = 1; i < 3; i++){
            mNotifications.add(notification);
        }





//        Using Firebase to get the notifications Firebase Realtime Firebase

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("notification");

        mScheduledExecutorService = Executors.newScheduledThreadPool(1);

        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // Retrieve data from the database and send the notification
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, String> data = (Map<String, String>) dataSnapshot.getValue();
                        String title = data.get("title");
                        String message = data.get("message");
                        // You can also retrieve other data from the database here

                        // Send the notification
                        sendNotification(getContext(), title, message);

                        // Update the recycler view with the new notification
                        mNotifications.add(new Notification(title, message));
                        notification_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                    }
                });
            }
        }, 0, INTERVAL, TimeUnit.MILLISECONDS);










//        NotificationSchedulerService service = new NotificationSchedulerService(mNotifications, notification_adapter, mContext);





        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}