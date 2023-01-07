package com.example.pronouncetwo.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pronouncetwo.R;
import com.example.pronouncetwo.databinding.FragmentNotificationsBinding;

import java.util.Locale;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private SeekBar seekBarPitch;
    private SeekBar seekBarSpeed;
    private Spinner language_spinner;
    private Switch appSwitch;
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;



    private SharedPreferences spinnerSharedPreferences;
    private SharedPreferences switchSharedPreferences;
    private static final String PREFERENCES_FILE_NAME = "preferences_file";
    private static final String SPINNER_VALUE_KEY = "spinner_value";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);




        seekBarPitch = binding.pitchSeekBar;
        seekBarSpeed = binding.speedSeekBar;




        SharedViewModel sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.setPitch(seekBarPitch.getProgress());
        sharedViewModel.setSpeed(seekBarSpeed.getProgress());

        spinnerSharedPreferences = getActivity().getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        switchSharedPreferences = getActivity().getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);












        seekBarPitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the value of the seekBarPitch in the notifications view model
                sharedViewModel.setPitch(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Update the value of the seekBarPitch in the notifications view model
                sharedViewModel.setPitch(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Update the value of the seekBarPitch in the notifications view model
                sharedViewModel.setPitch(seekBar.getProgress());
            }
        });

        seekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the value of the seekBarSpeed in the notifications view model
                sharedViewModel.setSpeed(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Update the value of the seekBarSpeed in the notifications view model
                sharedViewModel.setSpeed(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Update the value of the seekBarSpeed in the notifications view model
                sharedViewModel.setSpeed(seekBar.getProgress());
            }
        });


        language_spinner = binding.languageSpinner;
        language_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                // Set the language for the TextToSpeech object here
                // For example:


                Locale locale = Locale.forLanguageTag(selectedLanguage);
                sharedViewModel.setTTSLocale(locale);

                SharedPreferences.Editor spinnerEditor = spinnerSharedPreferences.edit();
                spinnerEditor.putString(SPINNER_VALUE_KEY, selectedLanguage);
                spinnerEditor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // In your Java code

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language_spinner.setAdapter(adapter);



//        App Type Switcher
        appSwitch = binding.appSwitcher;
        appSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appSwitch.isChecked()){
                    Toast.makeText(getActivity(), "Switch Is On", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Switch Is OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });
        appSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save the value of the appSwitch to shared preferences
                SharedPreferences.Editor editor = switchSharedPreferences.edit();
                editor.putBoolean("switch_key", isChecked);
                editor.apply();
            }
        });



//      Adding Animation To the Parent UI Element
        cardView1 = binding.cardView1;
        cardView2 = binding.cardView2;
        cardView3 = binding.cardView3;
        cardView4 = binding.cardView4;

        cardView1.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_two));
        cardView2.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_two));
        cardView3.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_two));
        cardView4.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_two));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onPause() {
        super.onPause();

        // Get the shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SEEKBAR_PREFERENCES", Context.MODE_PRIVATE);

        // Get the editor
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Store the values of the seekBar in the shared preferences
        editor.putInt("seekBarPitch", seekBarPitch.getProgress());
        editor.putInt("seekBarSpeed", seekBarSpeed.getProgress());

        // Commit the changes
        editor.apply();




        // Get the selected value of the spinner
        String selectedValue = language_spinner.getSelectedItem().toString();

        // Get a SharedPreferences editor
        editor = spinnerSharedPreferences.edit();

        // Put the selected value in the SharedPreferences
        editor.putString(SPINNER_VALUE_KEY, selectedValue);

        // Commit the changes
        editor.apply();


//        get the current status of the Switch


        editor = switchSharedPreferences.edit();
        editor.putBoolean("switch_key", appSwitch.isChecked());
        editor.apply();



    }

    @Override
    public void onResume() {
        super.onResume();

        // Get the shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SEEKBAR_PREFERENCES", Context.MODE_PRIVATE);

        // Retrieve the values of the seekBar from the shared preferences
        int pitch = sharedPreferences.getInt("seekBarPitch", 0);
        int speed = sharedPreferences.getInt("seekBarSpeed", 0);

        // Set the values of the seekBar
        seekBarPitch.setProgress(pitch);
        seekBarSpeed.setProgress(speed);



// Get the saved value from the SharedPreferences
        String savedValue = spinnerSharedPreferences.getString(SPINNER_VALUE_KEY, "");

        // Set the saved value as the selected value of the spinner
        int savedValueIndex = ((ArrayAdapter)language_spinner.getAdapter()).getPosition(savedValue);
        language_spinner.setSelection(savedValueIndex);



//        Get the saved value from the switchSharedPreferences
        switchSharedPreferences= getActivity().getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        boolean switchValue = switchSharedPreferences.getBoolean("switch_key", false);
        // Set the value of the appSwitch to the switch
        appSwitch.setChecked(switchValue);

    }




}