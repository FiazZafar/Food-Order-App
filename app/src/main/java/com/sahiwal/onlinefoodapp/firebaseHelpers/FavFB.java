package com.sahiwal.onlinefoodapp.firebaseHelpers;

import androidx.annotation.NonNull;

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

public class FavFB implements CartsInterface {

    DatabaseReference cartRef = FirebaseDatabase.getInstance()
            .getReference().child("Food App")
            .child("MyFavourites");
    @Override
    public void addToCart(String userId, Food favItem, FirebaseCallbacks<Boolean> result) {
        cartRef.child(userId).child(String.valueOf(favItem.getId()))
                .setValue(favItem).addOnCompleteListener(task -> {
                   if (task.isSuccessful())
                       result.onComplete(true);
                   else
                       result.onComplete(false);
                });
    }
    @Override
    public void updateQuantity(String userId, String itemId, int quantity, FirebaseCallbacks<Boolean> result) {}
    @Override
    public void removeFromCart(String userId, FirebaseCallbacks<Boolean> result) {
        cartRef.child(userId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful())
                result.onComplete(true);
            else
                result.onComplete(false);
        });
    }

    @Override
    public void removeOneFromCart(String userId, String Id, FirebaseCallbacks<Boolean> result) {
        cartRef.child(userId).child(Id).removeValue().addOnCompleteListener(task -> {
            if(task.isSuccessful())
                result.onComplete(true);
            else
                result.onComplete(false);
        });
    }

    @Override
    public void fetchAllCarts(String userId, FirebaseCallbacks<List<Food>> cartList) {
        List<Food> myList = new ArrayList<>();
        cartRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snap : snapshot.getChildren()){
                        Food myFood = snap.getValue(Food.class);
                        if (myFood.getFav() != null){
                            myList.add(myFood);
                        }
                    }
                    cartList.onComplete(myList);
                }else {
                    cartList.onComplete(myList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                cartList.onComplete(myList);

            }
        });
    }
}
