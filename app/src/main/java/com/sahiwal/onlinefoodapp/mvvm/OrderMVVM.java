package com.sahiwal.onlinefoodapp.mvvm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.sahiwal.onlinefoodapp.FbInterfaces.OrdersInterface;
import com.sahiwal.onlinefoodapp.firebaseHelpers.OrdersFB;
import com.sahiwal.onlinefoodapp.models.OrderHistory;

import java.util.ArrayList;
import java.util.List;

public class OrderMVVM extends ViewModel {
    OrdersInterface ordersInterface = new OrdersFB();
    MutableLiveData<List<OrderHistory>> myOrders = new MutableLiveData<>();

    public MutableLiveData<List<OrderHistory>> getMyOrders() {
        return myOrders;
    }

    public void setMyOrders() {
        String userId = FirebaseAuth.getInstance().getUid();
        ordersInterface.fetchAllOrders(userId,onOrders -> {
            if (onOrders != null) myOrders.setValue(onOrders);
            else myOrders.postValue(new ArrayList<>());
        });
    }
}
