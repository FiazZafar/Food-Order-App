package com.sahiwal.onlinefoodapp.models;

public class Price {
    private int Id;
    private String Value;

    public Price(){

    }

    @Override
    public String toString() {
        return Value;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

}
