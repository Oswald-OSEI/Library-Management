package com.example.librarymanagement.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.librarymanagement.mainClasses.Mains;
import com.example.librarymanagement.models.Librarian;
import com.example.librarymanagement.models.Patron;

public class LibrarianService {
    BookService bookService = new BookService();
    private Connection getConnection() {
        try {
            return DriverManager.getConnection(Mains.URL, Mains.USER, Mains.PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

     private final Connection conn = getConnection();

    public int addLibrarian(int id, String name, String email, String telNumber, String password){
        Librarian librarian = new Librarian(id, name, email, telNumber, password);
        saveToDatabase(librarian);
        int result = 1;
        return result;
    }
    
    public int login(int librarianID, String email, String password)
    {
        int output = 0;
        try {
            String query = "SELECT * FROM librarian WHERE librarian_id = ? AND email = ? AND password = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, librarianID);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        output = 1;
                    }}
                }
        } catch (SQLException | NumberFormatException e) {
            output = 2;
        }
        return output;
    }

    public int approveBorrowing(int bID, int pID, int LId, String title, String patronName, String patronPassword, String librariansPassword){

        int output = 0;
          try {
            //verify Book credentials
            String verifyBook = "SELECT * FROM books WHERE id = ? AND title = ?";
            try (PreparedStatement ps = conn.prepareStatement(verifyBook)) {
                ps.setInt(1, bID);
                ps.setString(2, title);
                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        // Verify patron credentials

                        String verifyPatronQuery = "SELECT * FROM patrons WHERE patron_id = ? AND name = ? AND password = ?";
                        try (PreparedStatement preparedStatement = conn.prepareStatement(verifyPatronQuery)) {
                            preparedStatement.setInt(1, LId);
                            preparedStatement.setString(2, patronName);
                            preparedStatement.setString(3, patronPassword);

                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                if (resultSet.next()) {

                                    //verify librarian's credentials
                                    String verifylibrarian = "SELECT * FROM librarian WHERE librarian_id = ? AND password = ?";
                                    try (PreparedStatement prepStatement = conn.prepareStatement(verifylibrarian)) {
                                        prepStatement.setInt(1, LId);
                                        prepStatement.setString(2, librariansPassword);

                                        try (ResultSet reSet = prepStatement.executeQuery()) {
                                            if (reSet.next()) {

                                                // Create transaction
                                                String createTransactionQuery = "INSERT INTO transaction (transaction_id ,patron_id, book_id, date_borrowed, approved_by) VALUES (?, ?, ?, NOW(), ?)";

                                                try (PreparedStatement transactionStatement = conn.prepareStatement(createTransactionQuery)) {
                                                    transactionStatement.setInt(1, 1);
                                                    transactionStatement.setInt(2, pID);
                                                    transactionStatement.setInt(3, bID);
                                                    transactionStatement.setInt(4, LId); //id of the logged in librarian
                                                    transactionStatement.executeUpdate();
                                                    bookService.updateBookStock(bID, -1); // Decrease the stock by 1
                                                    output= 1;
                                                    System.out.println(output);
                                                }

                                            } else {
                                               output = 2;
                                            }
                                        }
                                    }

                                } else {
                                   output =  3;
                                }
                            }


                        }
                    } else {
                        output =  4;
                    }
                }
            }
        } catch (SQLException | NumberFormatException e) {
        }
        return output;
    }

    public int approveReturn(int pID, int LId,int bID, String librarianPassword, String patronPassword){
        // Verify patron credentials
        int output=0;
            String verifyPatronQuery = "SELECT * FROM patrons WHERE patron_id = ? AND password = ?";
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(verifyPatronQuery)) {
                preparedStatement.setInt(1, pID);
                preparedStatement.setString(2, patronPassword);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        try (ResultSet reSet = preparedStatement.executeQuery()) {
                            if (reSet.next()) {
                                //verify librarian's credentials
                                String verifylibrarian = "SELECT * FROM librarian WHERE librarian_id = ? AND password = ?";
                                try (PreparedStatement prepStatement = conn.prepareStatement(verifylibrarian)) {
                                    prepStatement.setInt(1, LId);
                                    prepStatement.setString(2, librarianPassword);

                                    try (ResultSet resultSet1 = prepStatement.executeQuery()) {
                                        if (resultSet1.next()) {
                                            // Update transaction
                                            String updateTransactionQuery = "UPDATE transaction SET date_returned = NOW(),is_returned = true,  received_by = ? WHERE book_id = ? AND patron_id = ? AND date_returned IS NULL";
                                            try (PreparedStatement transactionStatement = conn.prepareStatement(updateTransactionQuery)) {
                                                transactionStatement.setInt(1, LId);
                                                transactionStatement.setInt(2, bID);
                                                transactionStatement.setInt(3, pID);

                                                int rowsUpdated = transactionStatement.executeUpdate();
                                                output = 1;

                                                if (rowsUpdated > 0) {
                                                    bookService.updateBookStock(bID, 1); // Increase the stock by 1

                                                } 
                                            }
                                        } 
                                    }
                                }
                            }
                        }

                    }
                }
            
    }catch (SQLException | NumberFormatException e) {
       
    }
    return output;
    }

    public Librarian getLibrarianById(int librarianId) {
        Librarian librarian = new Librarian();
        String query = "SELECT * FROM Librarian WHERE librarian_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, librarianId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                            librarian.setName(resultSet.getString("name"));
                            librarian.setEmail(resultSet.getString("email"));
                            librarian.setTelNumber(resultSet.getString("tel_number"));
                            librarian.setPassword(resultSet.getString("password"));
                            librarian.setPersonId(resultSet.getInt("librarian_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return librarian;
    }

    //Method to save to database
public void saveToDatabase(Librarian librarian) {
        String query = "INSERT INTO librarian (librarian_id, name, email, tel_number, password) VALUES (?,?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, librarian.getPersonId());
            statement.setString(2, librarian.getName());
            statement.setString(3, librarian.getEmail());
            statement.setString(4, librarian.getTelNumber());
            statement.setString(5, librarian.getPassword());
            statement.executeUpdate();
            System.out.println("librarian added to database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

