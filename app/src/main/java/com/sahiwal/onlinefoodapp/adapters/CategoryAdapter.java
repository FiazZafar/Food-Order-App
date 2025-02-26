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

        switch (position){
            case 1:
                holder.productImage.setBackgroundResource(R.drawable.car_1_bg);
                break;
            case 2:
                holder.productImage.setBackgroundResource(R.drawable.car_2_bg);
                break;
            case 3:
                holder.productImage.setBackgroundResource(R.drawable.car_3_bg);
                break;
            case 4:
                holder.productImage.setBackgroundResource(R.drawable.car_4_bg);
                break;
            case 5:
                holder.productImage.setBackgroundResource(R.drawable.car_5_bg);
                break;
            case 6:
                holder.productImage.setBackgroundResource(R.drawable.car_6_bg);
                break;
            case 7:
                holder.productImage.setBackgroundResource(R.drawable.car_7_bg);
                break;
            default:
                holder.productImage.setBackgroundResource(R.drawable.car_8_bg);
        }

        int drawableResourceId = context.getResources().getIdentifier(currentCategory
                .getImagePath(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(context).load(drawableResourceId).into(holder.productImage);
        Glide.with(context).load(currentCategory.getImagePath()).into(holder.productImage);

        holder.itemView.setOnClickListener(view ->
        {
            Intent intent = new Intent(context, FoodListByCategory.class);
            intent.putExtra("catergoryId",currentCategory.getId());
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
