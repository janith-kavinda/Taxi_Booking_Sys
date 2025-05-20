package com.taxi.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Vehicle {
    private int id;
    private String model;
    private String type;
    private double costPerHour;
    private double costPerDay;
    private int seats;

    private static ArrayList<Vehicle> vehicles = new ArrayList<>();

    static {
        vehicles.add(new Vehicle(1, "Alto", "Car", 1500, 4000, 4));
        vehicles.add(new Vehicle(2, "Wagon R", "Car", 2500, 6000, 4));
        vehicles.add(new Vehicle(3, "Toyota Corolla", "Car", 3000, 8500, 5));
        vehicles.add(new Vehicle(4, "Toyota Prius", "Car", 4500, 10000, 5));
        vehicles.add(new Vehicle(5, "Audi", "Car", 5500, 11500, 5));

        vehicles.add(new Vehicle(6, "Caravan", "van", 4000, 13500, 11));
        vehicles.add(new Vehicle(7, "Toyota Hiace", "van ", 5500, 15000, 14));
        vehicles.add(new Vehicle(8, "HyRoof ", "van", 6000, 18000, 15));

    }

    //create the constructor
    public Vehicle(int id, String model, String type, double costPerHour, double costPerDay, int seats) {
        this.id = id;
        this.model = model;
        this.type = type;
        this.costPerHour = costPerHour;
        this.costPerDay = costPerDay;
        this.seats = seats;
    }

    //getters and the setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(double costPerHour) {
        this.costPerHour = costPerHour;
    }

    public double getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(double costPerDay) {
        this.costPerDay = costPerDay;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }


    public double getPriceUsingDay(int day) {
        return day * costPerDay;
    }

    public double getPriceUsingHour(int hour) {
        return hour * costPerHour;
    }

    public static ArrayList<Vehicle> getVehicles(){
        return vehicles;
    }

    public static Vehicle getVehicleById(int id){
        for (Vehicle vehicle: vehicles){
            if (vehicle.getId() == id){
                return vehicle;
            }
        }
        return null;
    }
}
