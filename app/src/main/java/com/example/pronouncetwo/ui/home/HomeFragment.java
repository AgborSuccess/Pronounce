package com.example.pronouncetwo.ui.home;

import static android.graphics.Color.rgb;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pronouncetwo.R;
import com.example.pronouncetwo.databinding.FragmentHomeBinding;
import com.example.pronouncetwo.ui.notifications.NotificationsFragment;
import com.example.pronouncetwo.ui.notifications.NotificationsViewModel;

import java.net.URISyntaxException;
import java.util.Locale;

public class HomeFragment extends Fragment implements TextToSpeech.OnInitListener {

    private FragmentHomeBinding binding;
    private TextToSpeech TTS;
    private TextView confirmationText;
    private ImageButton playButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TTS = new TextToSpeech(getActivity(), (TextToSpeech.OnInitListener) this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.TxtToSpeechEditText;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        playButton = binding.PlayButton;
        EditText txt = binding.TxtToSpeechEditText;
        confirmationText = binding.TTSconfirmation;

        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        float seekBarPitch = notificationsViewModel.getSeekBarPitch();
        float seekBarSpeed = notificationsViewModel.getSeekBarSpeed();






        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = txt.getText().toString();
                speak(text, seekBarSpeed, seekBarPitch);
            }
        });

        return root;

    }











    private void speak(String text, float seekBarSpeed, float seekBarPitch){

        float pitch = (float) seekBarPitch / 50;
        if(pitch < 0.1)  pitch = 0.1f;
        float speed = (float) seekBarSpeed / 50;
        if(speed < 0.1)  speed = 0.1f;

        TTS.setPitch(pitch);
        TTS.setSpeechRate(speed);

        TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }





    @Override
    public void onDestroyView() {
        if (TTS != null){
            TTS.stop();
            TTS.shutdown();
        }

        super.onDestroyView();
        binding = null;

    }

    @Override
    public void onInit(int status) {
        TTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    confirmationText.setText("TTS Running....");
                    confirmationText.setTextColor(rgb(0, 255, 0));

                    int result = TTS.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        confirmationText.setText("Missing Data or Laguage Not Supported");
                        confirmationText.setTextColor(rgb(255, 0, 0));
                    }else{
                        playButton.setEnabled(true);
                    }
                }else{
                    confirmationText.setText("TTS not Running....");
                    confirmationText.setTextColor(rgb(255, 0, 0));
                }
            }
        });

    }
}