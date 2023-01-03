package com.example.pronouncetwo.ui.notifications;

import android.widget.SeekBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Settings");
    }

    public LiveData<String> getText() {
        return mText;
    }









    private float seekBarPitch;
    private float seekBarSpeed;

    public float getSeekBarPitch() {
        return seekBarPitch;
    }

    public void setSeekBarPitch(float seekBarPitch) {
        this.seekBarPitch = seekBarPitch;
    }
}