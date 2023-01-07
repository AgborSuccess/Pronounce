package com.example.pronouncetwo.ui.dashboard;


import java.util.ArrayList;
import java.util.List;

public class NotificationData {
    private static final NotificationData instance = new NotificationData();

    private List<Notification> mNotifications;
    private Notification_adapter mAdapter;

    private NotificationData() {
        // Initialize the list and adapter
        mNotifications = new ArrayList<>();
        mAdapter = new Notification_adapter(null, mNotifications);
    }

    public static NotificationData getInstance() {
        return instance;
    }

    public void setAdapter(Notification_adapter adapter) {
        mAdapter = adapter;
    }

    public Notification_adapter getAdapter() {
        return mAdapter;
    }

    public void setNotifications(List<Notification> notifications) {
        mNotifications = notifications;
    }

    public List<Notification> getNotifications() {
        return mNotifications;
    }
}
