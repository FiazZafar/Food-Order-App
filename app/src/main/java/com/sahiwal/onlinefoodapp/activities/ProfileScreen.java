package com.sahiwal.onlinefoodapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
                    .error(R.drawable.profile_pic)
                    .placeholder(R.drawable.profile_pic)
                    .into(binding.userProfile);
        if (userAddress != null) binding.userAddress.setText(userAddress);

        binding.backBtn.setOnClickListener(view -> onBackPressed());
        binding.uploadProfile.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,REQUEST_CODE);
        });
        binding.addAddressBtn.setOnClickListener(view -> {
            mapSettings();
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
    private void mapSettings() {
        if (checkLocationPermission() && isGpsEnabled()) {
            Intent intent = new Intent(ProfileScreen.this, MapsActivity.class);
            intent.putExtra("AddressScreen",true);
            startActivity(intent);
            finish();
        } else {
            requestLocationPermission(); // If not granted, request
        }
    }
    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    private boolean isGpsEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (isGpsEnabled()) {
                startActivity(new Intent(ProfileScreen.this, MapsActivity.class));
            } else {
                Toast.makeText(this, "Please enable GPS", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }
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