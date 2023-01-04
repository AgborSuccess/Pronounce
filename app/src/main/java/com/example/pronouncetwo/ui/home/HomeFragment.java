package com.example.pronouncetwo.ui.home;

import static android.graphics.Color.rgb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.pronouncetwo.ui.notifications.SharedViewModel;

import java.net.URISyntaxException;
import java.util.Locale;

public class HomeFragment extends Fragment implements TextToSpeech.OnInitListener {

    private FragmentHomeBinding binding;
    private TextToSpeech TTS;
    private TextView confirmationText;
    private ImageButton playButton;
    private EditText txt;

    private float pitch;
    private float speed;


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
        txt = binding.TxtToSpeechEditText;
        confirmationText = binding.TTSconfirmation;

        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);



        SharedViewModel sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.getPitch().observe(getViewLifecycleOwner(), newPitch -> this.pitch = newPitch);
        sharedViewModel.getSpeed().observe(getViewLifecycleOwner(), newSpeed -> this.speed = newSpeed);






        playButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        view.setBackgroundColor(Color.GRAY);
                        break;
                    case MotionEvent.ACTION_UP:
                        view.setBackgroundColor(Color.WHITE);
                        break;
                    default:
                        view.setBackgroundColor(Color.WHITE);
                }
                return false;
            }
        });


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = txt.getText().toString();

                if (text.isEmpty() == true){
                    confirmationText.setText("Put in Some Text");
                    confirmationText.setTextColor(rgb(255, 0, 0));
                }else{
                    speak(text);
                    confirmationText.setText("TTS Running....");
                    confirmationText.setTextColor(rgb(0, 255, 0));
                }

            }
        });

        return root;

    }











    private void speak(String text){


        if(pitch < 0.1)  pitch = 0.1f;
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



    @Override
    public void onPause() {
        super.onPause();

        // Get the shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MY_PREFERENCES", Context.MODE_PRIVATE);

        // Get the editor
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Store the value of the EditText in the shared preferences
        editor.putString("edit_text_value", txt.getText().toString());

        // Commit the changes
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Get the shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MY_PREFERENCES", Context.MODE_PRIVATE);

        // Retrieve the value of the EditText from the shared preferences
        String editTextValue = sharedPreferences.getString("edit_text_value", "");

        // Set the value of the EditText
        txt.setText(editTextValue);
    }

}