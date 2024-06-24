package com.example.librarymanagement;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.lang.*;
import java.util.Objects;

public class TransactionHistoryController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void switchToApproval(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("approve.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private TextField PName;

    @FXML
    private TextField PID;

    @FXML
    private TextField PEmail;

    @FXML
    private TextField PNumber;

    @FXML
    private TextField Ppassword;

    @FXML
    private Button ApproveB;

    @FXML
    private Button ApproveR;

    @FXML
    private Button Pcheck;

    @FXML
    private Button PAdd;

    @FXML
    private TableView<Transaction> Ttable;

    @FXML
    private TableColumn<?, ?> ColBook;

    @FXML
    private TableColumn<?, ?> ColBorrowed;

    @FXML
    private TableColumn<?, ?> ColApproved;

    @FXML
    private TableColumn<?, ?> ColReturned;

    @FXML
    private TableColumn<?, ?> ColReceivedBy;

    private Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection(Mains.URL, Mains.USER, Mains.PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
    Connection conn = getConnection();

    //function to add a patron
    @FXML
    private void addPatron() {
        String name = PName.getText();
        String email = PEmail.getText();
        String telNumber = PNumber.getText();
        String password = Ppassword.getText();
        if (name.isEmpty() || email.isEmpty() || telNumber.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill in all fields.");
            return;
        }
        try{
        Patron patron = new Patron(name, email, telNumber, password);
        patron.addPatron(patron);
        showAlert(AlertType.INFORMATION, "Success!", "Patron added successfully.");
        // Clear input fields
        PName.clear();
        PEmail.clear();
        PNumber.clear();
        Ppassword.clear();
    } catch (NumberFormatException e) {
        showAlert(AlertType.ERROR, "Form Error!", "Invalid Entry");
    }
    }
    @FXML
    private void showAlert (Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    // Add your controller logic here
    @FXML
    private void getPatron() {
        String patronID = PID.getText();
        String name = PName.getText();
        String password = Ppassword.getText();
        if (patronID.isEmpty() || name.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill in all fields.");
            return;
        }
        try {
            int patronId = Integer.parseInt(patronID);
            String query = "SELECT * FROM Patrons WHERE patron_id = ? AND name = ? AND password = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
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
                            try (PreparedStatement pS = conn.prepareStatement(query1)){
                                pS.setInt(1, patron.getPersonId());
                                try(ResultSet rs = pS.executeQuery()){
                                    ObservableList<Transaction> transactions = FXCollections.observableArrayList();
                                while (rs.next()){
                                    Books book = getBookById(rs.getInt("id"));
                                    Librarian approvedBy = getLibrarianById(rs.getInt("approvedBy"));
                                    Librarian receivedBy = getLibrarianById(rs.getInt("receivedBy"));
                                    Transaction transaction = new Transaction(
                                            patron,
                                            book,
                                            approvedBy,
                                            receivedBy,
                                            rs.getTimestamp("date_borrowed"),
                                            rs.getTimestamp("date_received"),
                                            rs.getBoolean("isReturned")
                                            );
                                    transactions.add(transaction);
                                }
                                    Platform.runLater(() -> Ttable.setItems(transactions));

                            }
                        }
                        // Clear input field
                        PName.clear();
                        PEmail.clear();
                        PNumber.clear();
                        Ppassword.clear();
                    }
                    }
                } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Form Error!", "Invalid Entry");
        }
    }

    //method to retrieve book by its ID
    public Books getBookById(int bookId) {
        String query = "SELECT * FROM Books WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Books(
                            resultSet.getString("title"),
                            resultSet.getInt("numberOfPages"),
                            resultSet.getInt("quantitiesInStock"),
                            resultSet.getString("author")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //to fetch librarian by Id
    public Librarian getLibrarianById(int librarianId) {
        String query = "SELECT * FROM Librarians WHERE librarian_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, librarianId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Librarian(
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("tel_number"),
                            resultSet.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Patron getPatronById(int patronId) {
        String query = "SELECT * FROM Patrons WHERE patron_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, patronId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Patron(
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("tel_number"),
                            resultSet.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}