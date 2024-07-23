package com.example.librarymanagement.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.librarymanagement.main.Mains;
import com.example.librarymanagement.models.Books;

public class BookService {
    //BookService Constructor
    public BookService(){}

    // Method to get a connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Mains.URL, Mains.USER, Mains.PASSWORD);
    }

    public void updateBookStock(int bookID, int delta) throws SQLException {
        String updateStockQuery = "UPDATE books SET quantitiesInStock = quantitiesInStock + ? WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(updateStockQuery)) {
            preparedStatement.setInt(1, delta);
            preparedStatement.setInt(2, bookID);
            preparedStatement.executeUpdate();
        }
    }

    public void addBook(Books book) {
        saveToDatabase(book);
        System.out.println("Book added successfully");
    }

    //Method to delete Book
    public int deleteById(int bookId){
            int rowsAffected = 0;

            String deleteSQL = "DELETE FROM books WHERE id = ?";

            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

                // Set the parameter for the query
                preparedStatement.setInt(1, bookId);

                // Execute the delete query
                rowsAffected = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                rowsAffected = 0;
            } 
        return rowsAffected;
    }

    public int updateById(int bookId, String NValue, String updateType){
         String updateSQL = "";
         int rowsAffected=0;

            switch (updateType.toLowerCase()) {
                case "title":
                    updateSQL = "UPDATE books SET title = ? WHERE id = ?";
                    break;
                case "stock":
                    updateSQL = "UPDATE books SET quantitiesInStock = ? WHERE id = ?";
                    break;
                case "available":
                    updateSQL = "UPDATE books SET available = ? WHERE id = ?";
                    break;
                case "author":
                    updateSQL = "UPDATE books SET author = ? WHERE id = ?";
                    break;
                case "pages":
                    updateSQL = "UPDATE books SET numberOfPages = ? WHERE id = ?";
                    break;
                default:
                    System.out.println( "Update type can't be performed.");
                    return 0;
            }

            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

                switch (updateType.toLowerCase()) {
                    case "stock":
                    case "pages":
                        preparedStatement.setInt(1, Integer.parseInt(NValue));
                        break;
                    case "author":
                    case "title":
                        preparedStatement.setString(1, NValue);
                        break;
                }

                preparedStatement.setInt(2, bookId);

                rowsAffected = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("Invalid format for quantity: " + NValue);
                e.printStackTrace();
            }
            return rowsAffected;

    }

        
    // Method to save the book to the database
    private void saveToDatabase(Books book) {
        String query = "INSERT INTO books (title, quantitiesInStock, available, author, numberOfPages) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getQuantitiesInStock());
            statement.setBoolean(3, book.getAvailable());
            statement.setString(4, book.getAuthor());
            statement.setInt(5, book.getNumberOfPages());
            statement.executeUpdate();
            System.out.println("Book added to database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}