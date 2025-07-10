package com.sahiwal.onlinefoodapp.activities;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.databinding.ActivityProcessOrderBinding;

public class ProcessOrderActivity extends AppCompatActivity {

    ActivityProcessOrderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProcessOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}