package com.taxi.models;

public class Rental {
    private int id;
    private int vehicleID;
    private int userID;
    private String date;
    private String type;
    private int duration;
    private double price;

    public Rental(int id, int vehicleID, int userID, String date, String type, int duration, double price) {
        this.id = id;
        this.vehicleID = vehicleID;
        this.userID = userID;
        this.date = date;
        this.type = type;
        this.duration = duration;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return id + "," + vehicleID + "," + userID + "," + date + "," + type + "," + duration + "," + price + "\n";
    }
}