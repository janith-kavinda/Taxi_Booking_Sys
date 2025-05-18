package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService = new UserService();

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            if (userService.isEmailRegistered(user.getEmail())) {
                return ResponseEntity.badRequest()
                    .body(Map.of("message", "Email address is already registered"));
            }
            userService.addUser(user);
            return ResponseEntity.ok(Map.of("message", "Registration successful! Please login to continue."));
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", "Error during registration: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");

            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("message", "Email is required"));
            }
            if (password == null || password.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("message", "Password is required"));
            }

            User user = userService.validateUser(email, password);
            
            if (user != null) {
                // Create response without password
                Map<String, Object> userData = Map.of(
                    "id", user.getId(),
                    "name", user.getName(),
                    "email", user.getEmail(),
                    "phoneNumber", user.getPhoneNumber() != null ? user.getPhoneNumber() : "",
                    "address", user.getAddress() != null ? user.getAddress() : "",
                    "country", user.getCountry() != null ? user.getCountry() : "",
                    "location", user.getLocation() != null ? user.getLocation() : "",
                    "memberSince", user.getMemberSince() != null ? user.getMemberSince() : new java.util.Date().toString()
                );
                return ResponseEntity.ok(userData);
            } else {
                // Check if email exists to provide better error message
                User existingUser = userService.getUserByEmail(email);
                if (existingUser != null) {
                    return ResponseEntity.badRequest()
                        .body(Map.of("message", "Invalid password for this email address"));
                } else {
                    return ResponseEntity.badRequest()
                        .body(Map.of("message", "No account found with this email address"));
                }
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", "Error during login: " + e.getMessage()));
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        try {
            return userService.getAllUsers();
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving users: " + e.getMessage());
        }
    }
}
