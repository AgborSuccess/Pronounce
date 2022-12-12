package com.example.pronouncetwo.ui.home;

import android.widget.ImageButton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    ImageButton playButton;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Type In your Text.....");

    }

    public LiveData<String> getText() {
        return mText;
    }
}