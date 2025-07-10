package com.sahiwal.onlinefoodapp.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.sahiwal.onlinefoodapp.databinding.ActivityDetailBinding;
import com.sahiwal.onlinefoodapp.models.Food;
import com.sahiwal.onlinefoodapp.mvvm.DetailMVVM;

public class DetailActivity extends BasicActivity {
    ActivityDetailBinding binding;
    Food foodObj;
    private int num = 1;
    DetailMVVM myCarts ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myCarts = new ViewModelProvider(this).get(DetailMVVM.class);

        binding.addToCartBtn.setEnabled(true);

        getIntents();
        setElements();
    }
    @SuppressLint("SetTextI18n")
    private void setElements() {
            if (foodObj != null){
                foodObj.setNumberInCart(num);
                binding.backBtn.setOnClickListener(view -> finish());
                binding.productTitle.setText(foodObj.getTitle());
                binding.descriptionTxt.setText(foodObj.getDescription());
                binding.currentPrice.setText("$ " + foodObj.getPrice());
                binding.totalTimeTxt.setText(foodObj.getTimeValue() + "mins");
                binding.ratingTxt.setText(String.valueOf(foodObj.getStar()));
                binding.totalPriceTxt.setText(String.valueOf(num * foodObj.getPrice()));
                Glide.with(this).load(foodObj.getImagePath()).into(binding.productImage);

                binding.plusBtn.setOnClickListener(view ->{
                    num = num + 1;
                    binding.totalQuantity.setText(String.valueOf(num));
                    binding.totalPriceTxt.setText("$ " + num * foodObj.getPrice() );
                    myCarts.setUpdateQuantity(foodObj.getId(),num);
                });
                binding.minusBtn.setOnClickListener(view ->{
                    if (num > 1){
                        num = num - 1;
                        binding.totalQuantity.setText(String.valueOf(num));
                        binding.totalPriceTxt.setText("$ " + num * foodObj.getPrice() );
                        myCarts.setUpdateQuantity(foodObj.getId(),num);
                    }
                });
                binding.addToCartBtn.setOnClickListener(view ->{
                    foodObj.setNumberInCart(num);
                    myCarts.setAddToCart(foodObj);
                    myCarts.getAddToCart().observe(this,onSaved -> {
                        if (onSaved){
                            binding.addToCartBtn.setEnabled(false);
                            Toast.makeText(this, "Saved To Cart", Toast.LENGTH_SHORT).show();
                            finish();
                        }else
                            Toast.makeText(this, "Failed to save in cart...", Toast.LENGTH_SHORT).show();
                    });
                });
                binding.favoriteItems.setOnClickListener(view -> {
                    if (foodObj.getFav() != null){
                        if (!foodObj.getFav()){
                        Toast.makeText(this, "Added to favourite's", Toast.LENGTH_SHORT).show();
                        foodObj.setFav(true);
                        myCarts.setAddToFavStatus(foodObj);
                        }else {
                        Toast.makeText(this, "Removed from favourite's", Toast.LENGTH_SHORT).show();
                        foodObj.setFav(false);
                        myCarts.setAddToFavStatus(foodObj);
                        }
                    }else {
                        foodObj.setFav(true);
                        myCarts.setAddToFavStatus(foodObj);
                    }
                });
            }
    }
    private void getIntents() {
       foodObj = (Food)
               getIntent().getSerializableExtra("object");
    }
}