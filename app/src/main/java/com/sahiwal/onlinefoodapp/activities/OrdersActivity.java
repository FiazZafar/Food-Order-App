package com.sahiwal.onlinefoodapp.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sahiwal.onlinefoodapp.adapters.OrderAdapter;
import com.sahiwal.onlinefoodapp.databinding.ActivityOrdersBinding;
import com.sahiwal.onlinefoodapp.models.OrderHistory;
import com.sahiwal.onlinefoodapp.mvvm.OrderMVVM;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private ActivityOrdersBinding binding;
    private OrderMVVM orderMVVM;
    private OrderAdapter orderAdapter;
    private List<OrderHistory> orderHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        orderMVVM = new ViewModelProvider(this).get(OrderMVVM.class);
        orderMVVM.setMyOrders();

        orderHistoryList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderHistoryList);

        binding.orderListRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.orderListRecycler.setAdapter(orderAdapter);

        binding.backBtn.setOnClickListener(v -> onBackPressed());

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.noResultFound.setVisibility(View.GONE);
        binding.orderListRecycler.setVisibility(View.GONE);

        orderMVVM.getMyOrders().observe(this, orderList -> {
            binding.progressBar.setVisibility(View.GONE);

            if (orderList != null && !orderList.isEmpty()) {

                binding.orderListRecycler.setVisibility(View.VISIBLE);
                binding.noResultFound.setVisibility(View.GONE);

                orderHistoryList.clear();
                orderHistoryList.addAll(orderList);

                // Refresh adapter — no need to set adapter again!
                orderAdapter.notifyDataSetChanged();

            } else {
                binding.noResultFound.setVisibility(View.VISIBLE);
                binding.orderListRecycler.setVisibility(View.GONE);
            }
        });

    }
}
