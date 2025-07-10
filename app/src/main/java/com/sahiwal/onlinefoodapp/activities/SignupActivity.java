package com.sahiwal.onlinefoodapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.databinding.ActivitySignupBinding;
import com.sahiwal.onlinefoodapp.helper.TinyDB;

public class SignupActivity extends BasicActivity {
    ActivitySignupBinding binding;
    SharedPreferences preferences ;
    SharedPreferences.Editor editor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("UsersProfilePref",MODE_PRIVATE);
        editor = preferences.edit();


        binding.signUpBtn.setOnClickListener(v ->setElements());
        binding.loginBtn.setOnClickListener(v -> startActivity(new Intent(this,LoginActivity.class)));
        binding.googleBtn.setOnClickListener(v -> signUpWithGoogle());
        binding.twitterBtn.setOnClickListener(v -> signUpWithTwitter());
        binding.facebookBtn.setOnClickListener(v -> signUpWithFacebook());
    }
    private void setElements() {
      String emailTxt =  binding.emailTxt.getText().toString();
      String userName =  binding.nameTxt.getText().toString();
      String passwordTxt = binding.passwordTxt.getText().toString();
      String confirmPassTxt = binding.confirmPasswordTxt.getText().toString();
      if (passwordTxt.length() < 6){
            binding.passwordTxt.setError("password is too short...");
      } else if (! passwordTxt.equals(confirmPassTxt)) {
            binding.passwordTxt.setError("password doesn't match...");
            binding.confirmPasswordTxt.setError("password doesn't match...");
      } else if (emailTxt == null || passwordTxt == null || confirmPassTxt == null) {
            binding.loginBtn.setError("fill all credentials first...");
      }else {
          setUpCustomSignUp(emailTxt,passwordTxt,userName);
      }
    }
    private void setUpCustomSignUp(String emailTxt,String passwordTxt,String userName) {
        mAuth.createUserWithEmailAndPassword(emailTxt,passwordTxt)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(SignupActivity.this, "Login Succeessfull...", Toast.LENGTH_SHORT).show();
                        editor.putString("userName",userName);
                        editor.putString("userEmail",emailTxt);
                        editor.apply();
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    }else {
                        Toast.makeText(this, "Login failed...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}