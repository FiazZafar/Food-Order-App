package com.sahiwal.onlinefoodapp.FbInterfaces;

import com.sahiwal.onlinefoodapp.interfaces.FirebaseCallbacks;
import com.sahiwal.onlinefoodapp.models.OrderHistory;

import java.util.List;

public interface OrdersInterface {
    void addToOrders(String userId,OrderHistory orderHistory, FirebaseCallbacks<Boolean> onResult);
    void fetchAllOrders(String userId, FirebaseCallbacks<List<OrderHistory>> onOrders);
    void removeAllOrders(String userId,FirebaseCallbacks<Boolean> onRemove);
}
