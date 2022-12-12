package com.example.pronouncetwo.ui.home;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pronouncetwo.R;
import com.example.pronouncetwo.databinding.FragmentHomeBinding;

import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.TxtToSpeechEditText;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final ImageButton playButton = binding.PlayButton;
        final EditText txt = binding.TxtToSpeechEditText;

        // variable declaration
        TextToSpeech tts = null;

// TextToSpeech initialization, must go within the onCreateView method
        TextToSpeech finalTts = tts;
        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int result = finalTts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Lenguage not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Its working and the problem is from the tts",Toast.LENGTH_SHORT).show();
                String txtToSpeech = txt.getText().toString();
                finalTts.speak(txtToSpeech, TextToSpeech.QUEUE_ADD, null);
                finalTts.shutdown();
            }
        });

        return root;

    }

    



    @Override
    public void onDestroyView() {

        super.onDestroyView();
        binding = null;
    }
}