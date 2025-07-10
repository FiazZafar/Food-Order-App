package com.sahiwal.onlinefoodapp.activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.adapters.FavFoodAdapter;
import com.sahiwal.onlinefoodapp.databinding.ActivityFavoriteScreenBinding;
import com.sahiwal.onlinefoodapp.models.Food;
import com.sahiwal.onlinefoodapp.mvvm.FavMVVM;

import java.util.ArrayList;
import java.util.List;

public class FavoriteScreen extends AppCompatActivity {
    ActivityFavoriteScreenBinding binding;
    FavMVVM favMVVM;
    List<Food> myFood;
    FavFoodAdapter favFoodAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        favMVVM = new ViewModelProvider(this).get(FavMVVM.class);
        favMVVM.setFavItem();

        myFood = new ArrayList<>();
        favFoodAdapter = new FavFoodAdapter(myFood,this);

        binding.progressBar.setVisibility(View.VISIBLE);

        binding.favListRecycler.setLayoutManager(new GridLayoutManager(this,2));
        binding.favListRecycler.setAdapter(favFoodAdapter);
        favMVVM.getFavItem().observe(this,onList -> {
            binding.progressBar.setVisibility(View.GONE);
            if(onList != null)
                favFoodAdapter.updateList(onList);
        });
    }
}