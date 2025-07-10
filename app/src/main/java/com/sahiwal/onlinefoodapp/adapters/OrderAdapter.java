package com.sahiwal.onlinefoodapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.models.OrderHistory;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    List<OrderHistory> historyList ;
    public OrderAdapter(List<OrderHistory> historyList){
        this.historyList = historyList;
    }
    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_list
                ,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        OrderHistory currentOrder = historyList.get(position);

        holder.orderCounter.setText("Order No: " + (position + 1));
        holder.totalPrice.setText(String.valueOf(currentOrder.getTotalPrice()));
        holder.totalItems.setText(String.valueOf(currentOrder.getTotalItems()));
        holder.orderAddress.setText(String.valueOf(currentOrder.getDeliveryAddress()));
        holder.orderStatus.setText(String.valueOf(currentOrder.getOrderEnum()));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderCounter,totalPrice,totalItems,orderAddress,orderStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderCounter = itemView.findViewById(R.id.orderNo);
            totalPrice = itemView.findViewById(R.id.orderPriceNo);
            totalItems = itemView.findViewById(R.id.totalItemsNo);
            orderAddress = itemView.findViewById(R.id.deliveryAddressNo);
            orderStatus = itemView.findViewById(R.id.deliveryStatusNo);
        }
    }
}
