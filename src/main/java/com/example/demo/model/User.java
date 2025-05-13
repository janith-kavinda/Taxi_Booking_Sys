package com.example.demo.model;

public class User {
    private String id;
    private String name;
    private String email;
    private String password; // Add password field

    // Constructors
    public User() {}

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = null; // Default password to null
    }

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getPassword() { // Add getter for password
        return password;
    }

    public void setPassword(String password) { // Add setter for password
        this.password = password;
    }
}
