package com.sahiwal.onlinefoodapp.activities;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private FusedLocationProviderClient locationProviderClient;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private double lat,lng;
    SharedPreferences.Editor editor;
    private boolean isMapReady = false;
    private Marker selectedMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        editor = getSharedPreferences("UsersProfilePref" , MODE_PRIVATE).edit();

        Intent intent = getIntent();
        Boolean addressScreen = intent.getBooleanExtra("AddressScreen",false);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        binding.locationSelectBtn.setOnClickListener(view -> {
            if (addressScreen){
                startActivity(new Intent(MapsActivity.this, ProfileScreen.class));
                finish();
            }else {
                startActivity(new Intent(MapsActivity.this, CartActivity.class));
                finish();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else {
            getCurrentLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();

        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();

                if (isMapReady) {
                    LatLng latLng = new LatLng(lat, lng);
                    updateMarker(latLng);
                }
                getCurrentAddress(lat,lng);
            }
        });
    }

    private void getCurrentAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String fullAddress = address.getAddressLine(0);

                editor.putString("Address", fullAddress);
                editor.putString("Latitude", String.valueOf(lat));
                editor.putString("Longitude", String.valueOf(lng));
                editor.apply();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        isMapReady = true;

        // Show current location if already available
        if (lat != 0 && lng != 0) {
            updateMarker(new LatLng(lat, lng));
        }

        // Allow user to tap and select new location
        mMap.setOnMapClickListener(latLng -> {
            updateMarker(latLng);
            getCurrentAddress(latLng.latitude, latLng.longitude);
        });
    }

    private void updateMarker(LatLng latLng) {
        if (selectedMarker != null) {
            selectedMarker.remove();
        }

        selectedMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Selected Location"));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));

        // Also update lat/lng for internal use
        lat = latLng.latitude;
        lng = latLng.longitude;
    }

}