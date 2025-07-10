package com.sahiwal.onlinefoodapp.models;

import com.sahiwal.onlinefoodapp.helper.OrderEnum;

public class OrderHistory {
    int orderId;
    int totalItems;
    Double totalPrice;
    String deliveryAddress;

    OrderEnum orderEnum;
    public OrderHistory(int totalItems, Double totalPrice, String deliveryAddress,OrderEnum orderEnum) {
        this.totalItems = totalItems;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
        this.orderEnum = orderEnum;
    }

    public OrderHistory() {
    }

    public OrderEnum getOrderEnum() {
        return orderEnum;
    }

    public void setOrderEnum(OrderEnum orderEnum) {
        this.orderEnum = orderEnum;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
