package com.sahiwal.onlinefoodapp.mvvm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.sahiwal.onlinefoodapp.FbInterfaces.CartsInterface;
import com.sahiwal.onlinefoodapp.firebaseHelpers.FavFB;
import com.sahiwal.onlinefoodapp.models.Food;

import java.util.ArrayList;
import java.util.List;

public class FavMVVM extends ViewModel {
    CartsInterface anInterface = new FavFB();

    MutableLiveData<List<Food>> setFav = new MutableLiveData<>();

    public MutableLiveData<List<Food>> getFavItem() {
        return setFav;
    }

    public void setFavItem() {
        String userId = FirebaseAuth.getInstance().getUid();
        anInterface.fetchAllCarts(userId,onFavList -> {
            if (onFavList != null)
                setFav.setValue(onFavList);
            else
                setFav.setValue(new ArrayList<>());
        });
    }

}
