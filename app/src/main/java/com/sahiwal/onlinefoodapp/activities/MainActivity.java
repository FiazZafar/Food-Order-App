package com.sahiwal.onlinefoodapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.sahiwal.onlinefoodapp.databinding.ActivityMainBinding;

public class MainActivity extends BasicActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    binding.logoutBtn.setOnClickListener(view ->{
        if (mAuth.getCurrentUser() != null) {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }});
    binding.
    }
}