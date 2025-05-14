package com.example.demo.service;

import com.example.demo.model.User;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service // Mark as a Spring service
public class UserService {
    private static final String FILE_PATH = "C:/Temp/users.txt"; // Updated to a writable directory

    public UserService() {
        File file = new File(FILE_PATH);
        File directory = file.getParentFile(); // Ensure the directory exists
        if (directory != null && !directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }
        if (!file.exists()) {
            try {
                file.createNewFile(); // Create the file if it doesn't exist
            } catch (IOException e) {
                System.err.println("Error creating users.txt file. Please check file permissions: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public void addUser(User user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.getId() + "," + user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," + user.getPhoneNumber() + "," + user.getAddress());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to users.txt file. Please check file permissions: " + e.getMessage());
            throw e;
        }
    }

    public List<User> getAllUsers() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) { // Ensure all fields are present
                    users.add(new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
                }
            }
        }
        return users;
    }

    public boolean isEmailRegistered(String email) throws IOException {
        List<User> users = getAllUsers();
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    public boolean validateUser(String email, String password) throws IOException {
        List<User> users = getAllUsers();
        return users.stream().anyMatch(user -> user.getEmail().equals(email) && user.getPassword().equals(password));
    }

    public void updateUser(User updatedUser) throws IOException {
        List<User> users = getAllUsers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                if (user.getEmail().equals(updatedUser.getEmail())) {
                    writer.write(updatedUser.getId() + "," + updatedUser.getName() + "," + updatedUser.getEmail() + "," + updatedUser.getPassword() + "," + updatedUser.getPhoneNumber() + "," + updatedUser.getAddress());
                } else {
                    writer.write(user.getId() + "," + user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," + user.getPhoneNumber() + "," + user.getAddress());
                }
                writer.newLine();
            }
        }
    }

    public void deleteUser(String email) throws IOException {
        List<User> users = getAllUsers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                if (!user.getEmail().equals(email)) {
                    writer.write(user.getId() + "," + user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," + user.getPhoneNumber() + "," + user.getAddress());
                    writer.newLine();
                }
            }
        }
    }
}
