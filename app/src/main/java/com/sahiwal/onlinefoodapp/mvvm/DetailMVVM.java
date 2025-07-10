package com.sahiwal.onlinefoodapp.mvvm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.sahiwal.onlinefoodapp.FbInterfaces.CartsInterface;
import com.sahiwal.onlinefoodapp.firebaseHelpers.CartsFb;
import com.sahiwal.onlinefoodapp.firebaseHelpers.FavFB;
import com.sahiwal.onlinefoodapp.models.Food;

public class DetailMVVM extends ViewModel {
    CartsInterface cartsInterface = new CartsFb();
    CartsInterface favInterFace = new FavFB();
    private MutableLiveData<Boolean> addToCart = new MutableLiveData<>();
    private MutableLiveData<Boolean> addToFav = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateQuantity = new MutableLiveData<>();

    public MutableLiveData<Boolean> getUpdateQuantity() {
        return updateQuantity;
    }

    public void setUpdateQuantity(int itemId,int quantity) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        cartsInterface.updateQuantity(userId, String.valueOf(itemId),quantity, onUpdate -> {
            if (onUpdate != null) updateQuantity.setValue(onUpdate);
        });

    }

    public MutableLiveData<Boolean> getAddToFavStatus() {
        return addToCart;
    }

    public void setAddToFavStatus(Food food) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        favInterFace.addToCart(userId,food ,onSaved -> {
            addToCart.setValue(onSaved);
        });
    }
    public MutableLiveData<Boolean> getAddToCart() {
        return addToCart;
    }

    public void setAddToCart(Food food) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        cartsInterface.addToCart(userId,food ,onSaved -> {
            addToCart.setValue(onSaved);
        });
    }
}
