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
import com.sahiwal.onlinefoodapp.activities.MainActivity;
import com.sahiwal.onlinefoodapp.models.Food;

import java.util.ArrayList;

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
            holder.title.setText(currentFood.getTitle());
            holder.price.setText("$ " + currentFood.getPrice());
            holder.time.setText(currentFood.getTimeValue());
            holder.rating.setText(String.valueOf(currentFood.getStar()));
            holder.cartBtn.setOnClickListener(view -> context.startActivity(new Intent(context, MainActivity.class)));

            Glide.with(context).load(currentFood.getImagePath())
            .placeholder(R.drawable.intro_pic)
            .into(holder.image);
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
