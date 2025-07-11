package com.sahiwal.onlinefoodapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.databinding.ActivityProfileScreenBinding;

public class ProfileScreen extends AppCompatActivity {

    ActivityProfileScreenBinding binding;
    private final static int REQUEST_CODE = 200;

    SharedPreferences pref ;
    SharedPreferences.Editor editor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pref = getSharedPreferences("UsersProfilePref",MODE_PRIVATE);
        editor = pref.edit();

        String userImage = pref.getString("UserImage",null);
        String userEmail = pref.getString("userEmail",null);
        String userName = pref.getString("userName",null);
        String userAddress = pref.getString("Address",null);
        if (userName != null) binding.userName.setText(userName);
        if (userEmail != null) binding.userEmail.setText(userEmail);
        if (userImage != null && !userImage.isEmpty())
            Glide.with(this).load(Uri.parse(userImage))
                    .error(R.drawable.account_circle_24px)
                    .placeholder(R.drawable.account_circle_24px)
                    .into(binding.userProfile);
        if (userAddress != null) binding.userAddress.setText(userAddress);

        binding.backBtn.setOnClickListener(view -> onBackPressed());
        binding.uploadProfile.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,REQUEST_CODE);
        });
        binding.addAddressBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("AddressScreen",true);
            startActivity(intent);
            finish();
        });
        binding.ordersHistory.setOnClickListener(view -> {
            startActivity(new Intent(ProfileScreen.this, OrdersActivity.class));
        });
        binding.favoriteItems.setOnClickListener(view -> {
            startActivity(new Intent(this, FavoriteScreen.class));
        });
        binding.logoutBtn.setOnClickListener(view -> {
           if (FirebaseAuth.getInstance().getCurrentUser() != null){
               FirebaseAuth.getInstance().signOut();
               startActivity(new Intent(ProfileScreen.this,LoginActivity.class));
               finish();
           }
        });
        binding.updatePassword.setOnClickListener(view -> {
           FirebaseAuth.getInstance().sendPasswordResetEmail("Hello Fiaz");
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){

            Uri uri = data.getData();
            binding.userProfile.setImageURI(uri);

            editor.putString("UserImage", String.valueOf(uri));
            editor.apply();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}