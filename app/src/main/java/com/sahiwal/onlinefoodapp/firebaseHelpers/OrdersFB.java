package com.sahiwal.onlinefoodapp.firebaseHelpers;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sahiwal.onlinefoodapp.FbInterfaces.OrdersInterface;
import com.sahiwal.onlinefoodapp.interfaces.FirebaseCallbacks;
import com.sahiwal.onlinefoodapp.models.OrderHistory;

import java.util.ArrayList;
import java.util.List;

public class OrdersFB implements OrdersInterface {
    DatabaseReference orderRef = FirebaseDatabase.getInstance()
            .getReference().child("Food App")
            .child("MyOrders");
    @Override
    public void addToOrders(String userId, OrderHistory orderHistory, FirebaseCallbacks<Boolean> onResult) {
        orderRef.child(userId).push().setValue(orderHistory).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                onResult.onComplete(true);
            else
                onResult.onComplete(false);
        });
    }
    @Override
    public void fetchAllOrders(String userId, FirebaseCallbacks<List<OrderHistory>> onOrders) {
        List<OrderHistory> orderHistoryList = new ArrayList<>();
        orderRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot order : snapshot.getChildren()){
                        OrderHistory myOrder = order.getValue(OrderHistory.class);
                        if (myOrder != null) orderHistoryList.add(myOrder);
                    }
                    onOrders.onComplete(orderHistoryList);
                }else {
                    onOrders.onComplete(orderHistoryList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onOrders.onComplete(orderHistoryList);
            }
        });
    }
    @Override
    public void removeAllOrders(String userId, FirebaseCallbacks<Boolean> onRemove) {
        orderRef.child(userId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful())
                onRemove.onComplete(true);
            else
                onRemove.onComplete(false);
        });
    }
}
