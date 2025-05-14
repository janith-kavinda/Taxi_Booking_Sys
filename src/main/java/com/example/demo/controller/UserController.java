package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // Constructor injection without @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return "User added successfully!";
        } catch (IOException e) {
            return "Error adding user: " + e.getMessage();
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
