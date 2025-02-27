package com.sahiwal.onlinefoodapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import com.sahiwal.onlinefoodapp.databinding.ActivityIntroBinding;

public class IntroActivity extends BasicActivity {

    ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(Color.parseColor("#FFE4B5"));

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(this, MainActivity.class));
        }else {

            binding.signUpBtn.setOnClickListener(v ->startActivity(new Intent(this, SignupActivity.class)));
            binding.loginBtn.setOnClickListener(v ->startActivity(new Intent(this, LoginActivity.class)));

        }
    }
}