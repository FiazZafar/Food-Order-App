package com.sahiwal.onlinefoodapp.interfaces;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface PaymentApi {
    @POST("api/payment/create-payment-intent")
    @Headers("Content-Type: application/json")
    Call<Map<String, Object>> paymentMethod(@Body Map<String, Object> amount);
}

