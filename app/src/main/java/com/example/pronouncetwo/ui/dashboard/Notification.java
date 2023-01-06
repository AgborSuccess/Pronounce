package com.example.pronouncetwo.ui.dashboard;

public class Notification{
    String Title;
    String description;
    long createdTime;

    private String title;
    private String body;

    public Notification(String title, String body) {
        this.title = title;
        this.body = body;
    }


    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }


    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }






}
