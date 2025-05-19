package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService = new UserService();

    @GetMapping
    public List<User> getAllUsers() {
        try {
            return userService.getAllUsers();
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving users: " + e.getMessage());
        }
    }
}
