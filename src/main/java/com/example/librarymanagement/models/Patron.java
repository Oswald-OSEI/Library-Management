package com.example.librarymanagement.models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.librarymanagement.main.Mains;

// Patron.java
public class Patron extends Person {
        public Patron(String name, String email, String telNumber, String password) {
            super(name, email, telNumber, password);
        }
        //method to save patron to database
        public void addPatron(Patron patron){
            patron.saveToDatabase();
            System.out.println("patron added");
        }
        // Method to get a connection to the database
        private Connection getConnection() throws SQLException {
            return DriverManager.getConnection(Mains.URL, Mains.USER, Mains.PASSWORD);}


    //Method to save to database
    public void saveToDatabase() {
        String query = "INSERT INTO patrons (name, email, tel_number, password) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, telNumber);
            statement.setString(4, password);
            statement.executeUpdate();
            System.out.println("Patron added to database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
