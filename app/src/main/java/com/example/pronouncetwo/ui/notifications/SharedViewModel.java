package com.example.pronouncetwo.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Float> pitch = new MutableLiveData<>();
    private MutableLiveData<Float> speed = new MutableLiveData<>();

    public void setPitch(float value) {
        pitch.setValue(value);
    }

    public void setSpeed(float value) {
        speed.setValue(value);
    }

    public LiveData<Float> getPitch() {
        return pitch;
    }

    public LiveData<Float> getSpeed() {
        return speed;
    }
}
