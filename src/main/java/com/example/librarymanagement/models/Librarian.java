package com.example.librarymanagement.models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.librarymanagement.mainClasses.Mains;

public class Librarian extends Person {
    public Librarian(){}

    public Librarian(String name, String email, String telNumber, String password) {
        super(name, email, telNumber, password);
    }
    public Librarian(int id, String name, String email, String telNumber, String password) {
        super(id, name, email, telNumber, password);
    }
    // Method to get a connection to the database
    private Connection getConnection(){
        Connection connection;
        try{
            connection = DriverManager.getConnection(Mains.URL, Mains.USER, Mains.PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    Connection connection = getConnection();

    public void approveBorrowing(Transaction transaction) {
       String query =  "INSERT INTO Transaction (patron_id, book_id, approved_by) VALUES (?, ?, ?)";
       String updateBookQuery = "UPDATE Books SET quantitiesInStock = quantitiesInStock - 1 WHERE id = ?";
       try (PreparedStatement preparedStatement = connection.prepareStatement(query);
            PreparedStatement updateBookStatement = connection.prepareStatement(updateBookQuery)) {
            preparedStatement.setInt(1, transaction.getPatron().getPersonId());
            preparedStatement.setInt(2, transaction.getBook().getBookId());
            preparedStatement.setInt(3, this.getPersonId());
            preparedStatement.executeUpdate();
           // Update the book's quantity in stock
           updateBookStatement.setInt(1, transaction.getBook().getBookId());
           updateBookStatement.executeUpdate();
            System.out.println("Borrowing approved by: " + this.name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void approveBookReturning(Transaction transaction) {
        String query = "UPDATE Transactions SET librarian_received_id = ?, is_returned = ?, date_returned = CURRENT_DATE WHERE transaction_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.getPersonId());
            preparedStatement.setBoolean(2, true);
            preparedStatement.setInt(3, transaction.getTransactionId());
            preparedStatement.executeUpdate();

            transaction.setReceivedBy(this);
            transaction.setReturned(true);

            String updateBookStockQuery = "UPDATE Books SET quantitiesInStock = quantitiesInStock + 1 WHERE book_id = ?";
            try (PreparedStatement updateStockStmt = connection.prepareStatement(updateBookStockQuery)) {
                updateStockStmt.setInt(1, transaction.getBook().getBookId());
                updateStockStmt.executeUpdate();
            }

            System.out.println("Returning approved by: " + this.name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
