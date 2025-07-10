package com.sahiwal.onlinefoodapp.interfaces;

public interface PaymentCallback {
    void onSuccess(String clientSecret);
    void onFailure(String error);
}
