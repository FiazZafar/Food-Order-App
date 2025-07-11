package com.sahiwal.onlinefoodapp.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.adapters.BestFoodAdapter;
import com.sahiwal.onlinefoodapp.adapters.CategoryAdapter;
import com.sahiwal.onlinefoodapp.adapters.FoodListByCategory;
import com.sahiwal.onlinefoodapp.databinding.ActivityMainBinding;
import com.sahiwal.onlinefoodapp.models.Category;
import com.sahiwal.onlinefoodapp.models.Food;
import com.sahiwal.onlinefoodapp.models.Location;
import com.sahiwal.onlinefoodapp.models.Price;
import com.sahiwal.onlinefoodapp.models.Time;
import java.util.ArrayList;

public class MainActivity extends BasicActivity {
    ActivityMainBinding binding;
    DatabaseReference myRef;
    Spinner timeSpinner,locationSpinner,priceSpinner;
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        timeSpinner = binding.deliveryTimeSpinner;
        locationSpinner = binding.locationSpinner;
        priceSpinner = binding.priceSpinner;

        pref = getSharedPreferences("UsersProfilePref",MODE_PRIVATE);
        editor = pref.edit();

        String userName = pref.getString("userName",null);
        String userImage = pref.getString("UserImage",null);
        if (userName != null){
            binding.userName.setText(userName);
        }
        if (userImage != null) {
            Glide.with(this).load(userImage)
                    .error(R.drawable.account_circle_24px)
                    .placeholder(R.drawable.account_circle_24px)
                    .into(binding.profilePic);
        }
        binding.profilePic.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,ProfileScreen.class));
        });

        binding.logoutBtn.setOnClickListener(view ->{
        if (mAuth.getCurrentUser() != null) {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }});
        binding.searchBarBtn.setOnClickListener(view -> {
            String searchedItem = binding.searchBarEdt.getText().toString().trim();
            if (!searchedItem.equals("")){
                Intent intent = new Intent(MainActivity.this, FoodListActivity.class);
                intent.putExtra("searchedItem",searchedItem);
                intent.putExtra("isSearched",true);
                startActivity(intent);
            }
        });
        binding.cartBtn.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this,CartActivity.class));
        });

        initializeSpinner("Location", Location.class,locationSpinner);
        initializeSpinner("Price", Price.class,priceSpinner);
        initializeSpinner("Time", Time.class,timeSpinner);

        initBestFood();
        initCategory();

    }

    private void initBestFood() {
        myRef = mDatabase.getReference("Foods");
        ArrayList<Food> bestFoodList = new ArrayList<>();
        binding.foodsProgressBar.setVisibility(VISIBLE);
        Query query = myRef.orderByChild("BestFood").equalTo(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        bestFoodList.add(snap.getValue(Food.class));
                    }
                    if (bestFoodList.size() > 0){
                        binding.foodsRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                        binding.foodsRecycler.setAdapter(new BestFoodAdapter(bestFoodList));
                        binding.foodsProgressBar.setVisibility(GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initCategory() {
        myRef = mDatabase.getReference("Category");
        ArrayList<Category> categoryList = new ArrayList<>();
        binding.categoriesProgressBar.setVisibility(VISIBLE);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        categoryList.add(snap.getValue(Category.class));
                    }
                    if (categoryList.size() > 0){
                        binding.categoriesRecycler.setLayoutManager(new GridLayoutManager(MainActivity.this,4));
                        binding.categoriesRecycler.setAdapter(new CategoryAdapter(categoryList));
                        binding.categoriesProgressBar.setVisibility(GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private <T> void initializeSpinner(String reference,Class<T> clazz, Spinner spinner){
        myRef = mDatabase.getReference(reference);
        ArrayList<T> arrayList = new ArrayList<>();
        ArrayAdapter<T> adapter = new ArrayAdapter<>(this, R.layout.sp_item,arrayList);
        adapter.setDropDownViewResource(R.layout.sp_item);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren()) {
                        arrayList.add(snapshot1.getValue(clazz));
                    }
                    adapter.notifyDataSetChanged();
                    spinner.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}