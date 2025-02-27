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
import com.sahiwal.onlinefoodapp.models.Food;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodListByCategory extends RecyclerView.Adapter<FoodListByCategory.ViewHolder>{
    ArrayList<Food> foodList ;
    Context context;
    public FoodListByCategory(ArrayList<Food> myList){
        this.foodList = myList;
    }
    @NonNull
    @Override
    public FoodListByCategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.food_list_by_category,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListByCategory.ViewHolder holder, int position) {
       Food myFood = foodList.get(position);
       holder.productName.setText(myFood.getTitle());
       holder.productTime.setText(myFood.getTimeValue() + "mins");
       holder.productPrice.setText("$ " + myFood.getPrice());
       holder.productRating.setText(String.valueOf(myFood.getStar()));
       Glide.with(context).load(myFood.getImagePath()).into(holder.productImage);


       holder.itemView.setOnClickListener(view ->{
           Intent intent = new Intent(context, DetailActivity.class);
           intent.putExtra("object" , (Serializable) myFood);
           context.startActivity(intent);
       }
    );
}

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName,productTime,productPrice,productRating;

        public ViewHolder(@NonNull View view) {
            super(view);
            productImage = view.findViewById(R.id.productImgFc);
            productName = view.findViewById(R.id.productNameFc);
            productTime = view.findViewById(R.id.producttimeFc);
            productPrice = view.findViewById(R.id.productPriceFc);
            productRating = view.findViewById(R.id.productRatingFc);
        }
    }
}
