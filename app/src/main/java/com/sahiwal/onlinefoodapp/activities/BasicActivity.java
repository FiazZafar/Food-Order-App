package com.sahiwal.onlinefoodapp.activities;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sahiwal.onlinefoodapp.R;

public class BasicActivity extends AppCompatActivity {
    FirebaseAuth mAuth ;
    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getColor(R.color.red));

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

    }

    public void signUpWithGoogle() {
    }
    public void signUpWithTwitter() {

    }

    public void signUpWithFacebook() {

    }

}