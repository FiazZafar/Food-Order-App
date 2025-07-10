package com.sahiwal.onlinefoodapp.activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.adapters.OrderAdapter;
import com.sahiwal.onlinefoodapp.databinding.ActivityOrdersBinding;
import com.sahiwal.onlinefoodapp.mvvm.OrderMVVM;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    ActivityOrdersBinding binding;
    OrderMVVM orderMVVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        orderMVVM = new ViewModelProvider(this).get(OrderMVVM.class);
        orderMVVM.setMyOrders();

        binding.orderListRecycler.setLayoutManager(new LinearLayoutManager(this));

        binding.listTitle.setText("Order's History");

        orderMVVM.getMyOrders().observe(this,orderList -> {
            if (orderList != null){
                binding.progressBar.setVisibility(View.GONE);
                binding.orderListRecycler.setAdapter(new OrderAdapter(orderList));
            }
        });

    }
}