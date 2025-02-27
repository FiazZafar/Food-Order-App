package com.sahiwal.onlinefoodapp.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

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
        binding.backBtn.setOnClickListener(view -> finish());

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
        Query query;
        if (isSearch){
                query = myRef.orderByChild("Title").equalTo(searchText);
                binding.listTitle.setText(searchText);
        }else {
             query = myRef.orderByChild("CategoryId").equalTo(categoryId);
            binding.listTitle.setText(categoryName);

        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myList.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        myList.add(snapshot1.getValue(Food.class));
                    }
                    if (myList.size() > 0){
                        binding.foodListRecycler.setLayoutManager(new GridLayoutManager(FoodListActivity.this,2));
                        binding.foodListRecycler.setAdapter(new FoodListByCategory(myList));
                    }else {
                        try {
                            binding.progressBar.wait(5000);
                            binding.progressBar.setVisibility(GONE);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    binding.progressBar.setVisibility(GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}