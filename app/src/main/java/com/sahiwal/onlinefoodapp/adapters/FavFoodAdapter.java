package com.sahiwal.onlinefoodapp.adapters;

import android.app.Activity;
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
import java.util.List;

public class FavFoodAdapter extends RecyclerView.Adapter<FavFoodAdapter.ViewHolder> {

    List<Food> myFoodList;
    Context context;

    public FavFoodAdapter(List<Food> myFoodList, Context context) {
        this.myFoodList = myFoodList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_by_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food myFood = myFoodList.get(position);
        holder.productName.setText(myFood.getTitle());
        holder.productTime.setText(myFood.getTimeValue() + "mins");
        holder.productPrice.setText("$ " + myFood.getPrice());
        holder.productRating.setText(String.valueOf(myFood.getStar()));
        Glide.with(context).load(myFood.getImagePath()).into(holder.productImage);


        holder.itemView.setOnClickListener(view -> {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("object", (Serializable) myFood);
                    context.startActivity(intent);
                    if (context instanceof Activity){
                        ((Activity) context).finish();
                    }
                }
        );
    }
    public void updateList(List<Food> newFood){
        myFoodList.clear();
        myFoodList.addAll(newFood);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return myFoodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productTime, productPrice, productRating;

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
