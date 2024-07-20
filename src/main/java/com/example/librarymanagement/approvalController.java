package com.example.librarymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;

public class approvalController {

    @FXML
    private TextField BBookID, BTitle, PBName, bPpassword, LPId, LPpassword, LPId1, LPpassword1, PBID;
    @FXML
    private Button Approve, ReceiveButton, loginButton;
    @FXML
    private TextField BookID, PID, Ppassword, loginID, loginEmail, loginPassword;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private final Connection conn = getConnection();

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(Mains.URL, Mains.USER, Mains.PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateBookStock(int bookID, int delta) throws SQLException {
        String updateStockQuery = "UPDATE books SET quantitiesInStock = quantitiesInStock + ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateStockQuery)) {
            preparedStatement.setInt(1, delta);
            preparedStatement.setInt(2, bookID);
            preparedStatement.executeUpdate();
        }
    }

    @FXML
    void backtoFrontPage(ActionEvent event) throws IOException {
        this.root = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("front page.fxml")));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        String id = loginID.getText();
        String email = loginEmail.getText();
        String password = loginPassword.getText();

        if (id.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill in all fields.");
            return;
        }

        try {
            int librarianID = Integer.parseInt(id);

            String query = "SELECT * FROM librarian WHERE librarian_id = ? AND email = ? AND password = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, librarianID);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Login successful!");
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("front page.fxml")));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Invalid credentials.");
                    }
                }
            }
        } catch (SQLException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "An error occurred: " + e.getMessage());
        }
    }

    @FXML
    void approveBorrowing(ActionEvent event) {
        String bookID = BBookID.getText();
        String title = BTitle.getText();
        String patronID = PBID.getText();
        String patronName = PBName.getText();
        String patronPassword = bPpassword.getText();
        String librarianId = LPId.getText();
        String librariansPassword = LPpassword.getText();

        if (bookID.isEmpty() || title.isEmpty() || patronID.isEmpty() || patronName.isEmpty() || patronPassword.isEmpty() || librarianId.isEmpty() || librariansPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill in all fields.");
            return;
        }

        try {
            int bID = Integer.parseInt(bookID);
            int pID = Integer.parseInt(patronID);
            int LId = Integer.parseInt(librarianId);

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
                                                String createTransactionQuery = "INSERT INTO Transaction (patron_id, book_id, date_borrowed, approved_by) VALUES (?, ?, NOW(), ?)";
                                                try (PreparedStatement transactionStatement = conn.prepareStatement(createTransactionQuery)) {
                                                    transactionStatement.setInt(1, pID);
                                                    transactionStatement.setInt(2, bID);
                                                    transactionStatement.setInt(3, LId); //id of the logged in librarian

                                                    transactionStatement.executeUpdate();
                                                    updateBookStock(bID, -1); // Decrease the stock by 1
                                                    showAlert(Alert.AlertType.INFORMATION, "Success", "Transaction approved successfully!");

                                                    // Clear input fields
                                                    BBookID.clear();
                                                    BTitle.clear();
                                                    PBID.clear();
                                                    PBName.clear();
                                                    bPpassword.clear();
                                                }
                                            } else {
                                                showAlert(Alert.AlertType.ERROR, "Error", "Invalid librarian credentials.");
                                            }
                                        }
                                    }

                                } else {
                                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid Patron Credentials.");
                                }
                            }


                        }
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Book does not exist.");
                    }
                }
            }
        } catch (SQLException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "An error occurred: " + e.getMessage());
        }
    }


    @FXML
    void approveReturn() {
        String bookID = BookID.getText();
        String patronID = PID.getText();
        String patronPassword = Ppassword.getText();
        String librarianId = LPId1.getText();
        String librarianPassword = LPpassword1.getText();

        if (bookID.isEmpty() || patronID.isEmpty() || patronPassword.isEmpty() || librarianId.isEmpty() || librarianPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill in all fields.");
            return;
        }

        try {
            int bID = Integer.parseInt(bookID);
            int pID = Integer.parseInt(patronID);
            int LId = Integer.parseInt(librarianId);

            // Verify patron credentials
            String verifyPatronQuery = "SELECT * FROM patrons WHERE patron_id = ? AND password = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(verifyPatronQuery)) {
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
                                                if (rowsUpdated > 0) {
                                                    updateBookStock(bID, 1); // Increase the stock by 1
                                                    showAlert(Alert.AlertType.INFORMATION, "Success", "Book return approved successfully!");

                                                    // Clear input fields
                                                    BookID.clear();
                                                    PID.clear();
                                                    Ppassword.clear();
                                                    LPId1.clear();
                                                    LPpassword1.clear();
                                                } else {
                                                    showAlert(Alert.AlertType.ERROR, "Error", "No matching transaction found.");
                                                }
                                            }
                                        } else {
                                            showAlert(Alert.AlertType.ERROR, "Error", "Invalid librarian credentials.");
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        } catch (SQLException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "An error occurred: " + e.getMessage());
        }
    }
}