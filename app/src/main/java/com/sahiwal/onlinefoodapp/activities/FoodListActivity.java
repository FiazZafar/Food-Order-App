package com.sahiwal.onlinefoodapp.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.adapters.BestFoodAdapter;
import com.sahiwal.onlinefoodapp.adapters.FoodListByCategory;
import com.sahiwal.onlinefoodapp.databinding.ActivityFoodListBinding;
import com.sahiwal.onlinefoodapp.models.Food;

import java.util.ArrayList;

public class FoodListActivity extends BasicActivity {

    ActivityFoodListBinding binding;
    int categoryId;
    boolean isSearch;
    String searchText,categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFoodListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backBtn.setOnClickListener(view -> onBackPressed());

       getIntents();
        initFoodList();
    }
    private void getIntents(){
        Intent intent = getIntent();
        categoryId = intent.getIntExtra("categoryId",-1);
        categoryName = intent.getStringExtra("categoryName");
        searchText = intent.getStringExtra("searchedItem");
        isSearch = intent.getBooleanExtra("isSearched",false);
    }
    private void initFoodList() {

        DatabaseReference myRef = mDatabase.getReference("Foods");
        ArrayList<Food> myList = new ArrayList<>();

        binding.progressBar.setVisibility(VISIBLE);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Food food = snapshot1.getValue(Food.class);
                        if (food != null) {
                            if (isSearch) {
                                if (food.getTitle().toLowerCase().contains(searchText)) {
                                    myList.add(food);
                                }
                            } else {
                                if (categoryId == food.getCategoryId()) {
                                    myList.add(food);
                                }
                            }
                        }
                    }
                        binding.listTitle.setText(isSearch ? searchText : categoryName);
                        if (!myList.isEmpty()) {
                            binding.foodListRecycler.setLayoutManager(new GridLayoutManager(FoodListActivity.this, 2));
                            binding.foodListRecycler.setAdapter(new FoodListByCategory(myList));
                        } else {

                            Toast.makeText(FoodListActivity.this, "No items found", Toast.LENGTH_SHORT).show();
                        }
                        binding.progressBar.setVisibility(GONE);
                }else {
                    Toast.makeText(FoodListActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                }
                binding.progressBar.setVisibility(GONE);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(FoodListActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}