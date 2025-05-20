package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service; // Import this

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
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
                System.err.println("Error creating users.txt file: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public void addUser(User user) throws IOException {
        // Generate a unique ID for new users
        if (user.getId() == null || user.getId().trim().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
        }

        // Set member since date for new users
        if (user.getMemberSince() == null) {
            user.setMemberSince(new java.util.Date().toString());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Format:
            // id,name,email,password,phoneNumber,address,country,location,memberSince
            String userData = String.join(",",
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getPhoneNumber() != null ? user.getPhoneNumber() : "",
                    user.getAddress() != null ? user.getAddress() : "",
                    user.getCountry() != null ? user.getCountry() : "",
                    user.getLocation() != null ? user.getLocation() : "",
                    user.getMemberSince());
            writer.write(userData);
            writer.newLine();
        }
    }

    public User getUserById(String id) throws IOException {
        List<User> users = getAllUsers();
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                String[] parts = line.split(",");
                if (parts.length >= 4) // At least id, name, email, password
                {
                    User user = new User();
                    user.setId(parts[0]);
                    user.setName(parts[1]);
                    user.setEmail(parts[2]);
                    user.setPassword(parts[3]);

                    // Set optional fields if they exist
                    if (parts.length > 4)
                        user.setPhoneNumber(parts[4]);
                    if (parts.length > 5)
                        user.setAddress(parts[5]);
                    if (parts.length > 6)
                        user.setCountry(parts[6]);
                    if (parts.length > 7)
                        user.setLocation(parts[7]);
                    if (parts.length > 8)
                        user.setMemberSince(parts[8]);

                    users.add(user);
                }
            }
        }
        return users;
    }

    public boolean isEmailRegistered(String email) throws IOException {
        List<User> users = getAllUsers();
        return users.stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    public User validateUser(String email, String password) throws IOException {
        List<User> users = getAllUsers();
        return users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email) &&
                        user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public User getUserByEmail(String email) throws IOException {
        List<User> users = getAllUsers();
        return users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    // Helper method to write all users back to the file
    private void saveAllUsers(List<User> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) { // false to overwrite
            for (User user : users) {
                String userData = String.join(",",
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getPhoneNumber() != null ? user.getPhoneNumber() : "",
                        user.getAddress() != null ? user.getAddress() : "",
                        user.getCountry() != null ? user.getCountry() : "",
                        user.getLocation() != null ? user.getLocation() : "",
                        user.getMemberSince() != null ? user.getMemberSince() : new Date().toString(), // Ensure
                                                                                                       // memberSince is
                                                                                                       // always set
                        user.getLastLogin() != null ? user.getLastLogin() : ""

                );
                writer.write(userData);
                writer.newLine();
            }
        }
    }

    public void updateUser(User updatedUser) throws IOException {
        if (updatedUser.getId() == null || updatedUser.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("User ID is required for update.");
        }

        List<User> users = getAllUsers();
        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            User existingUser = users.get(i);
            if (existingUser.getId().equals(updatedUser.getId())) {

                existingUser.setName(updatedUser.getName());
                existingUser.setEmail(updatedUser.getEmail());

                existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
                existingUser.setAddress(updatedUser.getAddress());
                existingUser.setCountry(updatedUser.getCountry());
                existingUser.setLocation(updatedUser.getLocation());

                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("User with ID " + updatedUser.getId() + " not found.");
        }

        saveAllUsers(users); // Save the updated list back to the file
    }

    public void deleteUserById(String id) throws IOException {
        List<User> users = getAllUsers();
        boolean removed = users.removeIf(user -> user.getId().equals(id));

        if (!removed) {
            throw new IllegalArgumentException("User with ID " + id + " not found.");
        }

        saveAllUsers(users); // Overwrite the file with the updated list
    }

}
