package com.sahiwal.onlinefoodapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.activities.DetailActivity;
import com.sahiwal.onlinefoodapp.activities.MainActivity;
import com.sahiwal.onlinefoodapp.models.Food;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class BestFoodAdapter extends RecyclerView.Adapter<BestFoodAdapter.ViewHolder> {
    ArrayList<Food> myFood;
    Context context;
    public BestFoodAdapter(ArrayList<Food> myFood){
        this.myFood = myFood;
    }

    @NonNull
    @Override
    public BestFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.best_food_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BestFoodAdapter.ViewHolder holder, int position) {
        Food currentFood = myFood.get(position);

        // Set text values
        holder.title.setText(currentFood.getTitle());
        holder.price.setText(String.format(Locale.getDefault(), "$%.2f", currentFood.getPrice())); // Format price
        holder.time.setText(currentFood.getTimeValue() + " min");
        holder.rating.setText(String.valueOf(currentFood.getStar()));

        // Set click listener for cart button
        holder.cartBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", (Serializable) currentFood); // Pass food ID or other details
            context.startActivity(intent);
        });

        // Load image using Glide with error handling
        String imagePath = currentFood.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            Glide.with(context)
                    .load(imagePath)
                    .into(holder.image);
        }
    }
    @Override
    public int getItemCount() {
        return myFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cartBtn,title,price,rating,time;
        ImageView image;

        ViewHolder(View view){
            super(view);
            cartBtn = view.findViewById(R.id.cartBtn);
            title = view.findViewById(R.id.productName);
            price = view.findViewById(R.id.priceTxt);
            rating = view.findViewById(R.id.ratingTxt);
            time = view.findViewById(R.id.timeTxt);
            image = view.findViewById(R.id.productImg);
        }
    }
}
