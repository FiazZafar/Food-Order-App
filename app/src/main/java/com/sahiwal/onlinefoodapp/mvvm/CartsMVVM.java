package com.sahiwal.onlinefoodapp.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.sahiwal.onlinefoodapp.FbInterfaces.CartsInterface;
import com.sahiwal.onlinefoodapp.FbInterfaces.OrdersInterface;
import com.sahiwal.onlinefoodapp.firebaseHelpers.CartsFb;
import com.sahiwal.onlinefoodapp.firebaseHelpers.OrdersFB;
import com.sahiwal.onlinefoodapp.helper.RetrofitClient;
import com.sahiwal.onlinefoodapp.interfaces.PaymentCallback;
import com.sahiwal.onlinefoodapp.models.Food;
import com.sahiwal.onlinefoodapp.models.OrderHistory;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;

import java.util.ArrayList;
import java.util.List;


public class CartsMVVM extends ViewModel {

    CartsInterface cartsInterface = new CartsFb();
    OrdersInterface ordersInterface = new OrdersFB();
    private MutableLiveData<List<Food>> myCarts = new MutableLiveData<>();
    private MutableLiveData<Boolean> myOrders = new MutableLiveData<>();
    private MutableLiveData<Boolean> paymentMethod = new MutableLiveData<>();

    public LiveData<Boolean> getOrderStatus() {
        return myOrders;
    }
    public void setOrdersInterface(OrderHistory orders) {
        String userId = FirebaseAuth.getInstance().getUid();
        ordersInterface.addToOrders(userId,orders,onOrderSaved -> {
            myOrders.setValue(onOrderSaved);
        });
    }

    public MutableLiveData<Boolean> getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(double amount,PaymentSheet paymentSheet) {
        RetrofitClient retrofitClient = new RetrofitClient();
        retrofitClient.callPayment(String.valueOf(amount), new PaymentCallback() {
            @Override
            public void onSuccess(String clientSecret) {
                if (clientSecret != null) presentPaymentSheet(clientSecret,paymentSheet);
            }


            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void presentPaymentSheet(String clientSecret,PaymentSheet paymentSheet) {
        PaymentSheet.Configuration myConfig = new PaymentSheet.Configuration("Food App");
        paymentSheet.presentWithPaymentIntent(clientSecret,myConfig);
    }
    public MutableLiveData<List<Food>> getMyCarts() {
        return myCarts;
    }

    public void setMyCarts() {
        String userId = FirebaseAuth.getInstance().getUid();
        cartsInterface.fetchAllCarts(userId,list -> {
            if (list != null)
                myCarts.setValue(list);
            else
                myCarts.setValue(new ArrayList<>());
        });
    }
}
