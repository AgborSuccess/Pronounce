package com.example.pronouncetwo;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pronouncetwo.ui.dashboard.DashboardFragment;
import com.example.pronouncetwo.ui.home.HomeFragment;
import com.example.pronouncetwo.ui.notifications.NotificationsFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.pronouncetwo.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragment(new HomeFragment());


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.navigation_notification);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(18);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_home:
                        loadFragment(new HomeFragment());
                        return true;
                    case R.id.navigation_notification:
                        loadFragment(new DashboardFragment());
                        badgeDrawable.setVisible(false);
                        return true;
                    case R.id.navigation_settings:
                        loadFragment(new NotificationsFragment());
                        return true;
                }
                return false;
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}