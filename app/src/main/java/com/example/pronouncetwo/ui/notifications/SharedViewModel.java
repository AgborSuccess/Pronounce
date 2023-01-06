package com.example.pronouncetwo.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Locale;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Float> pitch = new MutableLiveData<>();
    private MutableLiveData<Float> speed = new MutableLiveData<>();

    private MutableLiveData<Locale> ttsLocale = new MutableLiveData<>();

    public void setPitch(float value) {
        pitch.setValue(value);
    }

    public void setSpeed(float value) {
        speed.setValue(value);
    }

    public void setTTSLocale(Locale locale) {
        ttsLocale.setValue(locale);
    }

    public LiveData<Float> getPitch() {
        return pitch;
    }

    public LiveData<Float> getSpeed() {
        return speed;
    }

    public LiveData<Locale> getTTSLocale() {
        return ttsLocale;
    }
}
