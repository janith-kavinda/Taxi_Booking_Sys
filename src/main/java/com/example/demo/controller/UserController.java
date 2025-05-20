package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import org.springframework.http.HttpStatus;
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

     @PutMapping("/{id}") // e.g., PUT /users/some-user-id
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            if (!id.equals(user.getId())) {
                return ResponseEntity.badRequest().body(Map.of("message", "User ID in path does not match user ID in body."));
            }
            // Before updating, fetch the existing user to preserve password and memberSince if not explicitly provided
            User existingUser = userService.getUserById(id);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found with ID: " + id));
            }

            // Copy mutable fields from the incoming user to the existing user
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setAddress(user.getAddress());
            existingUser.setCountry(user.getCountry());
            existingUser.setLocation(user.getLocation());
            

            // Only update email if it's not registered to another user and it's changed
            if (!existingUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                if (userService.isEmailRegistered(user.getEmail())) {
                    return ResponseEntity.badRequest().body(Map.of("message", "Email address is already registered to another account."));
                }
            }
            existingUser.setEmail(user.getEmail()); // Update email if valid

            userService.updateUser(existingUser); // Pass the modified existingUser

            // Return updated user data (excluding password)
            Map<String, Object> updatedUserData = Map.of(
                    "id", existingUser.getId(),
                    "name", existingUser.getName(),
                    "email", existingUser.getEmail(),
                    "phoneNumber", existingUser.getPhoneNumber() != null ? existingUser.getPhoneNumber() : "",
                    "address", existingUser.getAddress() != null ? existingUser.getAddress() : "",
                    "country", existingUser.getCountry() != null ? existingUser.getCountry() : "",
                    "location", existingUser.getLocation() != null ? existingUser.getLocation() : "",
                    "memberSince", existingUser.getMemberSince() != null ? existingUser.getMemberSince() : "",
                    "lastLogin", existingUser.getLastLogin() != null ? existingUser.getLastLogin() : ""
                   
            );
            return ResponseEntity.ok(Map.of("message", "Profile updated successfully!", "user", updatedUserData));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error updating profile: " + e.getMessage()));
        }
    }

}
