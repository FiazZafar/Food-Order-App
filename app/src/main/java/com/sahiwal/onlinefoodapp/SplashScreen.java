package com.sahiwal.onlinefoodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sahiwal.onlinefoodapp.activities.BasicActivity;
import com.sahiwal.onlinefoodapp.activities.MainActivity;
import com.sahiwal.onlinefoodapp.activities.IntroActivity;
import com.sahiwal.onlinefoodapp.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends BasicActivity {

    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mAuth.getCurrentUser() != null){
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }else {
                    startActivity(new Intent(this, IntroActivity.class));
                    finish();
            }

        }, 5000); // Splash duration
    }
}
