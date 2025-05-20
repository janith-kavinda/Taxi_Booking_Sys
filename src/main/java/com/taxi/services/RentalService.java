package com.taxi.services;
import com.taxi.models.Rental;
import com.taxi.utils.FileUtils;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

public class RentalService {
    private static String DATA_FILE =  "rentals.txt";
    private static ArrayList<Rental> rentals = null;

    public static void GenerateFile(String path){
        DATA_FILE = path + "/" + "rentals.txt";
    }

    public static void loadRentals() throws IOException {
        ArrayList<String> lines = FileUtils.readAllLines(DATA_FILE);
        rentals = new ArrayList<>();
        if (lines == null) return;
        for (String line: lines){
            String[] parts = line.split(",");

            int id = Integer.parseInt(parts[0]);
            int vehicleID = Integer.parseInt(parts[1]);
            int userID = Integer.parseInt(parts[2]);
            String date = parts[3];
            String type = parts[4];
            int duration = Integer.parseInt(parts[5]);
            Double price = Double.parseDouble(parts[6]);

            Rental rental = new Rental(id, vehicleID, userID, date, type, duration, price);
            rentals.add(rental);
        }
    }

    //creating the booking
    public static void createRental(int vehicleID, int userID, String date, String type, int duration, double price){
        int rentalID;
        if (rentals.isEmpty()){
            rentalID = 1;
        }
        else{
            rentalID = rentals.get(rentals.size() - 1).getId() + 1;
        }
        Rental rental = new Rental(rentalID, vehicleID, userID, date, type, duration, price);
        rentals.add(rental);

        FileUtils.writeToFile(DATA_FILE, rental.toString(), true);

    }

    public static Rental getRentalById(int id){
        for (Rental rental: rentals){
            if (rental.getId() == id){
                return rental;
            }
        }
        return null;
    }

    public static void updateRental(int ID, String date, int duration, double price){
        Rental rental = getRentalById(ID);
        if (rental != null){
            rental.setDate(date);
            rental.setDuration(duration);
            rental.setPrice(price);
            saveAllRentals();
        }
    }

    public static void deleteRental(int id) throws IOException {
        Rental rental = getRentalById(id);
        if (rental != null){
            rentals.remove(rental);
            saveAllRentals();
        }
    }

    private static void saveAllRentals(){
        String data = "";
        for (Rental rental: rentals){
            data += rental.toString();
        }
        FileUtils.writeToFile(DATA_FILE, data, false);
    }

    public static ArrayList<Rental> getRentals(){
        return rentals;
    }

}