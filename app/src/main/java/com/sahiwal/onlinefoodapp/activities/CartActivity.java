package com.sahiwal.onlinefoodapp.activities;

import static android.icu.lang.UCharacter.DecompositionType.VERTICAL;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sahiwal.onlinefoodapp.R;
import com.sahiwal.onlinefoodapp.adapters.CartAdapter;
import com.sahiwal.onlinefoodapp.databinding.ActivityCartBinding;
import com.sahiwal.onlinefoodapp.helper.ManagmentCart;
import com.sahiwal.onlinefoodapp.interfaces.ChangeNumberItemsListener;
import com.sahiwal.onlinefoodapp.models.Food;

import java.util.ArrayList;

public class CartActivity extends BasicActivity {

    ActivityCartBinding binding;
    ManagmentCart myCart;
    ArrayList<Food> myList;
    ChangeNumberItemsListener changeNumberItemsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myCart = new ManagmentCart(this);
        myList = myCart.getListCart();

        calculateCart();
        setElements();
        initList();
    }

    private void initList() {
    if (myCart.getListCart().isEmpty()){
        binding.cartScrollview.setVisibility(GONE);
        binding.emptyTxt.setVisibility(VISIBLE);
    }else {
        binding.emptyTxt.setVisibility(GONE);
        binding.cartScrollview.setVisibility(VISIBLE);
    }
        binding.cartRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.cartRecycler.setAdapter(new CartAdapter(myList, myCart, new ChangeNumberItemsListener() {
            @Override
            public void change() {
                calculateCart();
            }
        }));
    }

    private void calculateCart(){

       double taxRate =  0.02;
       double deliveryCost =  10.0;
        taxRate = Math.round(myCart.getTotalFee() * taxRate * 100.0)/100;
        double total  = Math.round((taxRate + myCart.getTotalFee() + deliveryCost) *100)/100;
        double itemTotal = Math.round(myCart.getTotalFee() * 100)/100;



       binding.subTotalAmount.setText("$" + itemTotal);
       binding.deliveryCost.setText("$" + deliveryCost);
       binding.taxCost.setText("$" + taxRate);
       binding.totalAmount.setText("$" + total);
    }
    private void setElements() {
        binding.backBtn.setOnClickListener(view -> finish());

    }
}