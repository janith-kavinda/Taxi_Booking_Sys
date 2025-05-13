package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import java.io.IOException;

@RestController
public class AuthController {

    private final UserService userService = new UserService();

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        try {
            if (userService.isEmailRegistered(user.getEmail())) {
                return "Email already registered.";
            }
            userService.addUser(user);
            return "Registration successful.";
        } catch (IOException e) {
            return "Error during registration: " + e.getMessage();
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        try {
            if (userService.validateUser(user.getEmail(), user.getPassword())) {
                return "Login successful.";
            } else {
                return "Invalid email or password.";
            }
        } catch (IOException e) {
            return "Error during login: " + e.getMessage();
        }
    }
}
