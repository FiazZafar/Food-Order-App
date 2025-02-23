package com.sahiwal.onlinefoodapp.models;

public class Location {
    private int Id;
    private String Loc;

    public Location() {
    }
    public int getId() {
        return Id;
    }

    @Override
    public String toString() {
        return Loc;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getLoc() {
        return Loc;
    }

    public void setLoc(String loc) {
        Loc = loc;
    }

}
