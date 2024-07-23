package com.example.librarymanagement.service;

import java.sql.*;

import com.example.librarymanagement.mainClasses.Mains;
import com.example.librarymanagement.models.*;


import com.example.librarymanagement.models.Transaction;
import javafx.application.Platform;
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

    BookService bookService = new BookService();
    LibrarianService librarianService = new LibrarianService();

    public int addPatron(String name, String email, String telNumber, String password) {
        Patron patron = new Patron(name, email, telNumber, password);
        saveToDatabase(patron);
        int output = 1;
        return output;
    }

    public Transaction getPatronHistory(int patronId, String name, String password) {
        Transaction transaction = new Transaction();
        String query = "SELECT * FROM Patrons WHERE patron_id = ? AND name = ? AND password = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, patronId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Patron patron = new Patron(
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("tel_number"),
                            resultSet.getString("password")
                    );
                    patron.setPersonId(patronId);
                    String query1 = "SELECT * FROM Transaction WHERE patron_id = ?";
                    try (PreparedStatement pS = conn.prepareStatement(query1)) {
                        pS.setInt(1, patron.getPersonId());
                        try (ResultSet rs = pS.executeQuery()) {
                            while (rs.next()) {
                                Books book = bookService.getBookById(rs.getInt("book_id"));
                                String bookTitle = book.getTitle();
                                Librarian approvedBy = librarianService.getLibrarianById(rs.getInt("approved_by"));
                                String approvee = approvedBy.getName();
                                Librarian receivedBy = librarianService.getLibrarianById(rs.getInt("received_by"));
                                String receiver = receivedBy != null ? receivedBy.getName() : "Not Returned";
                                transaction = new Transaction(patron, book, approvedBy, receivedBy, rs.getTimestamp("date_borrowed"), rs.getTimestamp("date_returned"), rs.getBoolean("is_returned"));
                                transaction.setBookTitle(bookTitle);
                                transaction.setApprovalName(approvee);
                                transaction.setReceiveName(receiver);

                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transaction;
    }

public Patron getPatronById(int patronId) {
        Patron patron = new Patron();
        String query = "SELECT * FROM Patrons WHERE patron_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, patronId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                            patron.setName(resultSet.getString("name"));
                            patron.setEmail(resultSet.getString("email"));
                            patron.setTelNumber(resultSet.getString("tel_number"));
                            patron.setPassword(resultSet.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patron;
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
   

