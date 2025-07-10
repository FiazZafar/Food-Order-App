package com.sahiwal.onlinefoodapp.firebaseHelpers;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sahiwal.onlinefoodapp.FbInterfaces.CartsInterface;
import com.sahiwal.onlinefoodapp.interfaces.FirebaseCallbacks;
import com.sahiwal.onlinefoodapp.models.Food;
import java.util.ArrayList;
import java.util.List;

public class CartsFb implements CartsInterface{

    DatabaseReference cartRef = FirebaseDatabase.getInstance()
            .getReference().child("Food App")
            .child("MyCarts");
    @Override
    public void addToCart(String userId,Food food,FirebaseCallbacks<Boolean> result) {
        cartRef.child(userId).child(String.valueOf(food.getId())).setValue(food).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                result.onComplete(true);
            }else {
                result.onComplete(false);
            }
        });
    }

    @Override
    public void updateQuantity(String userId, String itemId,int quantity,
                               FirebaseCallbacks<Boolean> result) {
        cartRef.child(userId).child(itemId).child("numberInCart")
                .setValue(quantity).addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        result.onComplete(true);
                    else
                        result.onComplete(false);
                });
    }

    @Override
    public void removeFromCart(String userId,FirebaseCallbacks<Boolean> result) {
        cartRef.child(userId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful())
                result.onComplete(true);
            else
                result.onComplete(false);
        });
    }
    @Override
    public void removeOneFromCart(String userId, String itemId, FirebaseCallbacks<Boolean> result) {
        cartRef.child(userId).child(itemId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful())
                result.onComplete(true);
            else
                result.onComplete(false);
        });
    }
    @Override
    public void fetchAllCarts(String userId, FirebaseCallbacks<List<Food>> cartList) {
        List<Food> list = new ArrayList<>();
        cartRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Food foodItem = itemSnapshot.getValue(Food.class);
                    if (foodItem != null) {
                        list.add(foodItem);
                    }
                }
                cartList.onComplete(list);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                cartList.onComplete(new ArrayList<>());
            }
        });
    }

}
