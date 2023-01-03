package com.example.pronouncetwo.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pronouncetwo.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private SeekBar seekBarPitch;
    private SeekBar seekBarSpeed;

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

        notificationsViewModel.setSeekBarPitch(seekBarPitch.getProgress());
        notificationsViewModel.setSeekBarSpeed(seekBarSpeed.getProgress());





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
    }


}