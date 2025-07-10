package com.sahiwal.onlinefoodapp.helper;

import android.util.Log;

import com.sahiwal.onlinefoodapp.interfaces.PaymentApi;
import com.sahiwal.onlinefoodapp.interfaces.PaymentCallback;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.1.109:8080/";
    private static Retrofit retrofit;

    public RetrofitClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public void callPayment(String amount, PaymentCallback callback) {
        PaymentApi paymentApi = retrofit.create(PaymentApi.class);

        Map<String, Object> data = new HashMap<>();
        data.put("amount", amount);

        Call<Map<String, Object>> call = paymentApi.paymentMethod(data);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String clientSecret = response.body().get("clientSecret").toString();
                    Log.d("PAYMENT", "Client Secret: " + clientSecret);
                    callback.onSuccess(clientSecret);
                } else {
                    Log.e("PAYMENT", "Failed to get client secret");
                    callback.onFailure("Failed to get client secret");
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}


