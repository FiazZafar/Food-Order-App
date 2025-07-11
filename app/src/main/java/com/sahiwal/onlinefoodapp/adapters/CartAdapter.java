package com.sahiwal.onlinefoodapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.firebase.auth.FirebaseAuth;
import com.sahiwal.onlinefoodapp.FbInterfaces.CartsInterface;
import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.firebaseHelpers.CartsFb;
import com.sahiwal.onlinefoodapp.interfaces.ChangeNumberItemsListener;
import com.sahiwal.onlinefoodapp.models.Food;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<Food> foodArrayList;
    private Context context;
    private CartsInterface cartsInterface = new CartsFb();

    ChangeNumberItemsListener<List<Food>> changeNumberItemsListener;

    public CartAdapter(ArrayList<Food> food,ChangeNumberItemsListener<List<Food>> changeNumberItemsListener){
        this.foodArrayList = food;
        this.changeNumberItemsListener = changeNumberItemsListener;
    }
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_list,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        String userId = FirebaseAuth.getInstance().getUid();
        Food myFood = foodArrayList.get(position);

        holder.title.setText(myFood.getTitle());
        holder.price.setText("$ " + myFood.getPrice());
        holder.quantity.setText(String.valueOf(myFood.getNumberInCart()));

        DecimalFormat format = new DecimalFormat("0.00");
        holder.totalPrice.setText("$ " + format.format(
                myFood.getNumberInCart() * myFood.getPrice()));

        Glide.with(context)
                .load(myFood.getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(38))
                .into(holder.image);

        holder.plusBtn.setOnClickListener(view -> {
            int newCount = myFood.getNumberInCart() + 1;
            myFood.setNumberInCart(newCount);

            holder.quantity.setText(String.valueOf(newCount));
            holder.totalPrice.setText("$ " + format.format(newCount * myFood.getPrice()));

            changeNumberItemsListener.change(foodArrayList);
        });

        holder.minusBtn.setOnClickListener(view -> {
            int currentCount = myFood.getNumberInCart();
            if (currentCount > 1) {
                int newCount = currentCount - 1;
                myFood.setNumberInCart(newCount);

                holder.quantity.setText(String.valueOf(newCount));
                holder.totalPrice.setText("$ " + format.format(newCount * myFood.getPrice()));

                changeNumberItemsListener.change(foodArrayList);
            } else {
                Toast.makeText(context, "Long press to remove item", Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(view -> {
            cartsInterface.removeOneFromCart(userId, String.valueOf(myFood.getId()), onRemove -> {
                foodArrayList.remove(position);
                notifyItemRemoved(position);
            });
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,price,totalPrice,plusBtn,minusBtn,quantity;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.productNameCart);
            price = itemView.findViewById(R.id.productPriceCart);
            totalPrice = itemView.findViewById(R.id.productTotalPriceCart);
            plusBtn = itemView.findViewById(R.id.plusBtn);
            minusBtn = itemView.findViewById(R.id.minusBtn);
            quantity = itemView.findViewById(R.id.totalQuantity);
            image = itemView.findViewById(R.id.productImageCart);
        }
    }
}
