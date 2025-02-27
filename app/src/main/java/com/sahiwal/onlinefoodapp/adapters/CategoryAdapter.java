package com.sahiwal.onlinefoodapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.activities.FoodListActivity;
import com.sahiwal.onlinefoodapp.models.Category;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<Category> categoriesList;
    Context context;
    public CategoryAdapter(ArrayList<Category> categoriesList){
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category currentCategory = categoriesList.get(position);
        holder.titleTxt.setText(currentCategory.getName());

        // Set background resource dynamically if needed
        int backgroundResourceId = context.getResources().getIdentifier(
                "car_" + (position + 1) + "_bg", "drawable", context.getPackageName());
        holder.productImage.setBackgroundResource(backgroundResourceId);

        // Load image using Glide
        int drawableResourceId = context.getResources().getIdentifier(
                currentCategory.getImagePath(), "drawable", context.getPackageName());

        // Use Glide to load the image
        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.productImage);

        // Set click listener
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, FoodListActivity.class);
            intent.putExtra("categoryId", currentCategory.getId());
            intent.putExtra("categoryName", currentCategory.getName());
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTxt;
        ImageView productImage;
        public ViewHolder(@NonNull View view) {
            super(view);

            titleTxt = view.findViewById(R.id.itemName);
            productImage = view.findViewById(R.id.itemImage);
        }
    }
}
