package com.sahiwal.onlinefoodapp.models;

public class Location {
    private int Id;
    private String loc;

    public Location() {
    }
    public int getId() {
        return Id;
    }

    @Override
    public String toString() {
        return loc;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

}
