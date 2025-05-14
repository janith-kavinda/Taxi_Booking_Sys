package com.example.demo.model;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber; // Add phoneNumber field
    private String address; // Add address field

    // Constructors
    public User() {}

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = null; // Default password to null
        this.phoneNumber = null; // Default phoneNumber to null
        this.address = null; // Default address to null
    }

    public User(String id, String name, String email, String password, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() { // Add getter for phoneNumber
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) { // Add setter for phoneNumber
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() { // Add getter for address
        return address;
    }

    public void setAddress(String address) { // Add setter for address
        this.address = address;
    }
}
