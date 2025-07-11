package com.sahiwal.onlinefoodapp.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sahiwal.onlinefoodapp.adapters.CartAdapter;
import com.sahiwal.onlinefoodapp.databinding.ActivityCartBinding;
import com.sahiwal.onlinefoodapp.helper.OrderEnum;
import com.sahiwal.onlinefoodapp.models.Food;
import com.sahiwal.onlinefoodapp.models.OrderHistory;
import com.sahiwal.onlinefoodapp.mvvm.CartsMVVM;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CartActivity extends BasicActivity {
    ActivityCartBinding binding;
    ArrayList<Food> myList;
    String address;
    double totals;
    private PaymentSheet paymentSheet;
    private CartsMVVM cartsMVVM ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cartsMVVM = new ViewModelProvider(this).get(CartsMVVM.class);
        cartsMVVM.setMyCarts();

        myList = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences("UsersProfilePref",MODE_PRIVATE);
        address = preferences.getString("Address",null);

        String publish_key = "pk_test_51QWyBoBqyIue1vHwZaXj0h9bNYBZIeMv84r1er9BQCZ" +
                "IsdU4VM7EjsfXXRbtRxvBLWN4bZx9YyvSgPL4rjgzlOoi003Iw1L9r2";
        PaymentConfiguration.init(this,publish_key);
        paymentSheet = new PaymentSheet(this,this::onPaymentSheetResult);

        if (address != null){
            binding.userAddress.setText(address);
        }

        cartsMVVM.getMyCarts().observe(this,list -> {
            if (list != null){
                myList.addAll(list);

                calculateCart();

                initList();
            }
        });
        binding.addAddressBtn.setOnClickListener(view -> {
            startActivity(new Intent(CartActivity.this,MapsActivity.class));
            finish();
        });
        binding.placeOrderBtn.setOnClickListener(view ->{
            if (totals > 0){
                if (address != null){
                    cartsMVVM.setPaymentMethod(totals,paymentSheet);
                }else {
                    Toast.makeText(this, "Empty address", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
            }
        });

        setElements();
    }
    private void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
            if (updateOrderHistory()){
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(this, "Payment Canceled", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            String error = ((PaymentSheetResult.Failed) paymentSheetResult).getError().getMessage();
            Toast.makeText(this, "Payment Failed: " + error, Toast.LENGTH_LONG).show();
        }
    }

    private Boolean updateOrderHistory() {
        AtomicReference<Boolean> isUpdated = new AtomicReference<>(false);
        cartsMVVM.setOrdersInterface(new OrderHistory(myList.size(),
                totals,address, OrderEnum.PENDING));
        cartsMVVM.getOrderStatus().observe(this,onSaved -> {
            if (onSaved) {
                isUpdated.set(onSaved);
            }
        });
        return isUpdated.get();
    }

    private void initList() {
    if (myList.isEmpty()){
        binding.cartScrollview.setVisibility(GONE);
        binding.emptyTxt.setVisibility(VISIBLE);
    }else {
        binding.emptyTxt.setVisibility(GONE);
        binding.cartScrollview.setVisibility(VISIBLE);
    }
        binding.cartRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));
        binding.cartRecycler.setAdapter(new CartAdapter(myList,onUpdatedList -> {
            if (onUpdatedList != null) {
                myList = new ArrayList<>(onUpdatedList);
                calculateCart();
            }}));
    }

    @SuppressLint("SetTextI18n")
    private void calculateCart(){
       double taxRate =  0.02;
       double deliveryCost =  10.0;
        taxRate = (double) Math.round(getTotalFee(myList) * taxRate * 100.0) /100;
        totals  = (double) Math.round((taxRate + getTotalFee(myList) + deliveryCost) * 100) /100;
        double itemTotal = (double) Math.round(getTotalFee(myList) * 100) /100;

       binding.subTotalAmount.setText("$" + itemTotal);
       binding.deliveryCost.setText("$" + deliveryCost);
       binding.taxCost.setText("$" + taxRate);
       binding.totalAmount.setText("$" + totals);
    }
    private void setElements() {
        binding.backBtn.setOnClickListener(view -> finish());
    }

    public Double getTotalFee(List<Food> foodList){
        double fee=0;
        if (!foodList.isEmpty()){
            for (int i = 0; i < foodList.size(); i++) {
                fee=fee+(foodList.get(i).getPrice() * foodList.get(i).getNumberInCart());
            }
        }else {
            Toast.makeText(this, "Empty List.", Toast.LENGTH_SHORT).show();
        }
        return fee;
    }
}