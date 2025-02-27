package com.sahiwal.onlinefoodapp.activities;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    RecyclerView foodRecycler,categoryRecycler;
    DatabaseReference myRef;
    Spinner timeSpinner,locationSpinner,priceSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

        timeSpinner = binding.deliveryTimeSpinner;
        locationSpinner = binding.locationSpinner;
        priceSpinner = binding.priceSpinner;




        binding.logoutBtn.setOnClickListener(view ->{
        if (mAuth.getCurrentUser() != null) {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }});
        binding.searchBarBtn.setOnClickListener(view -> {
            String searchedItem = binding.searchBarEdt.getText().toString().trim();
            if (searchedItem == ""){
                Intent intent = new Intent(MainActivity.this, FoodListByCategory.class);
                intent.putExtra("searchedItem",searchedItem);
                intent.putExtra("isSearched",true);
                startActivity(intent);
            }
        }
        );
         initializeSpinner("Location", Location.class,locationSpinner);
        initializeSpinner("Price", Price.class,priceSpinner);
        initializeSpinner("Time", Time.class,timeSpinner);

        initBestFood();
        initCategory();
        foodRecycler = new RecyclerView(this);
        foodRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.HORIZONTAL,false));

    }

    private void initBestFood() {
        myRef = mDatabase.getReference("Food");
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
        ArrayAdapter<T> adapter = new ArrayAdapter<>(this, R.layout.sp_item);
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