package com.example.librarymanagement.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.librarymanagement.main.Mains;
import com.example.librarymanagement.models.Patron;

import javafx.scene.control.Alert.AlertType;

public class PatronService {
    private Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(Mains.URL, Mains.USER, Mains.PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
    Connection conn = getConnection();

public int addPatron(String name, String email, String telNumber, String password){
     Patron patron = new Patron(name, email, telNumber, password);
        saveToDatabase(patron);
        int output =1;           
 return output;           
}
//Method to save to database
public void saveToDatabase(Patron patron) {
        String query = "INSERT INTO patrons (name, email, tel_number, password) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patron.getName());
            statement.setString(2, patron.getEmail());
            statement.setString(3, patron.getTelNumber());
            statement.setString(4, patron.getPassword());
            statement.executeUpdate();
            System.out.println("Patron added to database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
   

