package com.sahiwal.onlinefoodapp.FbInterfaces;

import com.sahiwal.onlinefoodapp.interfaces.FirebaseCallbacks;
import com.sahiwal.onlinefoodapp.models.Food;

import java.util.List;

public interface FavInterface {
    void addToCart(String userId, Food cartsModel, FirebaseCallbacks<Boolean> result);
    void updateQuantity(String userId, String itemId,int quantity, FirebaseCallbacks<Boolean> result);
    void removeFromCart(String userId, FirebaseCallbacks<Boolean> result);
    void removeOneFromCart(String userId,String Id, FirebaseCallbacks<Boolean> result);
    void fetchAllCarts(String userId,FirebaseCallbacks<List<Food>> cartList);
}
