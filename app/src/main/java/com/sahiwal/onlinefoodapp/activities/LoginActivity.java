package com.sahiwal.onlinefoodapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import com.sahiwal.onlinefoodapp.databinding.ActivityLoginBinding;

public class LoginActivity extends BasicActivity {
    ActivityLoginBinding binding;

    SharedPreferences preferences ;
    SharedPreferences.Editor editor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("UsersProfilePref",MODE_PRIVATE);
        editor = preferences.edit();


        binding.loginBtn.setOnClickListener(v -> getCredentials());
        binding.signUpBtn.setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class)));
        binding.googleBtn.setOnClickListener(v -> signUpWithGoogle());
        binding.twitterBtn.setOnClickListener(v -> signUpWithTwitter());
        binding.facebookBtn.setOnClickListener(v -> signUpWithFacebook());
    }

    private void  getCredentials(){
        String userName = binding.userName.getText().toString();
        String emailTxt = binding.emailTxt.getText().toString();
        String passwordTxt = binding.passwordTxt.getText().toString();

        if (emailTxt == null || passwordTxt == null ){
            binding.loginBtn.setError("fill all credentials...");
        }else {
            setupLogin(emailTxt,passwordTxt,userName);
        }
    }
    private void setupLogin(String emailTxt ,String passwordTxt,String userName){
        mAuth.signInWithEmailAndPassword(emailTxt,passwordTxt).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "Login Successfull...", Toast.LENGTH_SHORT).show();
                editor.putString("userName",userName);
                editor.putString("userEmail",emailTxt);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

}