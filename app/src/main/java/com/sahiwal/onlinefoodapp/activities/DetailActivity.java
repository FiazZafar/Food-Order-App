package com.sahiwal.onlinefoodapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.databinding.ActivityDetailBinding;
import com.sahiwal.onlinefoodapp.helper.ManagmentCart;
import com.sahiwal.onlinefoodapp.models.Food;

public class DetailActivity extends BasicActivity {

    ActivityDetailBinding binding;
    Food foodObj;
    private int num = 1;
    private ManagmentCart managmentCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntents();
        setElements();
    }

    private void setElements() {
            managmentCart = new ManagmentCart(this);
            if (foodObj != null){

                binding.backBtn.setOnClickListener(view -> finish());
                binding.productTitle.setText(foodObj.getTitle());
                binding.descriptionTxt.setText(foodObj.getDescription());
                binding.currentPrice.setText("$ " + String.valueOf(foodObj.getPrice()));
                binding.totalTimeTxt.setText(foodObj.getTimeValue() + "mins");
                binding.ratingTxt.setText(String.valueOf(foodObj.getStar()));
                binding.totalPriceTxt.setText(String.valueOf(num * foodObj.getPrice()));
                binding.addToCartBtn.setOnClickListener(view ->startActivity(new Intent(DetailActivity.this, CartActivity.class)));

                Glide.with(this).load(foodObj.getImagePath()).into(binding.productImage);

                binding.plusBtn.setOnClickListener(view ->{
                    num = num + 1;
                    binding.totalQuantity.setText(String.valueOf(num));
                    binding.totalPriceTxt.setText("$ " + num * foodObj.getPrice() );
                });
                binding.minusBtn.setOnClickListener(view ->{
                    if (num > 1){
                        num = num - 1;
                        binding.totalQuantity.setText(String.valueOf(num));
                        binding.totalPriceTxt.setText("$ " + num * foodObj.getPrice() );
                    }
                });
                binding.addToCartBtn.setOnClickListener(view ->{
                    foodObj.setNumberInCart(num);
                    managmentCart.insertFood(foodObj);
                });
            }
    }

    private void getIntents() {
       foodObj = (Food) getIntent().getSerializableExtra("object");
    }
}