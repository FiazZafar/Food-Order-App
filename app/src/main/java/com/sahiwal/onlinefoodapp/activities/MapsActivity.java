package com.sahiwal.onlinefoodapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
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
    private double lat, lng;
    private SharedPreferences.Editor editor;
    private Marker selectedMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        editor = getSharedPreferences("UsersProfilePref", MODE_PRIVATE).edit();

        boolean addressScreen = getIntent().getBooleanExtra("AddressScreen", false);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        binding.locationSelectBtn.setOnClickListener(view -> {
            startActivity(new Intent(
                    MapsActivity.this,
                    addressScreen ? ProfileScreen.class : CartActivity.class
            ));
            finish();
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Enable blue dot assuming permissions are granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        getCurrentLocationAndZoom();

        mMap.setOnMapClickListener(latLng -> {
            updateMarker(latLng);
            saveAddress(latLng.latitude, latLng.longitude);
        });
    }

    private void getCurrentLocationAndZoom() {
        locationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                updateMarker(latLng);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
                saveAddress(location.getLatitude(), location.getLongitude());
            }
        });
    }

    private void updateMarker(LatLng latLng) {
        if (selectedMarker != null) selectedMarker.remove();

        selectedMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Selected Location"));

        lat = latLng.latitude;
        lng = latLng.longitude;
    }

    private void saveAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                editor.putString("Address", address.getAddressLine(0));
                editor.putString("Latitude", String.valueOf(lat));
                editor.putString("Longitude", String.valueOf(lng));
                editor.apply();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
