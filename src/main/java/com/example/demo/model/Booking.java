package com.example.demo.model;

public class Booking {
    private String bookingId;
    private String userId;
    private String name; // New field for customer name
    private String pickupLocation;
    private String dropLocation;
    private String vehicleType; // New field for vehicle type

    // Constructors
    public Booking() {}
    public Booking(String bookingId, String userId, String name, String pickupLocation, String dropLocation, String vehicleType) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.name = name;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.vehicleType = vehicleType;
    }

    // Getters and Setters
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
