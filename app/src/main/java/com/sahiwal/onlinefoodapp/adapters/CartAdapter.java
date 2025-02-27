package com.sahiwal.onlinefoodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.helper.ManagmentCart;
import com.sahiwal.onlinefoodapp.interfaces.ChangeNumberItemsListener;
import com.sahiwal.onlinefoodapp.models.Food;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<Food> foodArrayList;
    private Context context;
    private int num;
    private ManagmentCart cart;
    ChangeNumberItemsListener changeNumberItemsListener;
    public CartAdapter(ArrayList<Food> food,ManagmentCart cart,ChangeNumberItemsListener changeNumberItemsListener){
        this.foodArrayList = food;
        this.cart = cart;
        this.changeNumberItemsListener = changeNumberItemsListener;
    }
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Food myFood = foodArrayList.get(position);
        num = myFood.getNumberInCart();
        holder.title.setText(myFood.getTitle());
        holder.totalPrice.setText("$" + myFood.getNumberInCart() * myFood.getPrice());
        holder.price.setText(num + "* $ " +(num * myFood.getPrice()));
        holder.quantity.setText(String.valueOf(myFood.getNumberInCart()));
        Glide.with(context)
                .load(myFood.getImagePath())
                .transform(new CenterCrop(),new RoundedCorners(38)).into(holder.image);

        holder.plusBtn.setOnClickListener(view -> {
            cart.plusNumberItem(foodArrayList, position, new ChangeNumberItemsListener() {
                @Override
                public void change() {
                    notifyDataSetChanged();
                    changeNumberItemsListener.change();
                }
            });
        });
        holder.minusBtn.setOnClickListener(view ->{
            if (num>1){
                cart.minusNumberItem(foodArrayList, position, new ChangeNumberItemsListener() {
                    @Override
                    public void change() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.change();
                    }
                });
            }

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
