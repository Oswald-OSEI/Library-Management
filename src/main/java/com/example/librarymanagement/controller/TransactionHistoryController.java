package com.example.librarymanagement.controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import com.example.librarymanagement.mainClasses.Mains;
import com.example.librarymanagement.models.Transaction;
import com.example.librarymanagement.service.PatronService;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TransactionHistoryController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    PatronService patronService = new PatronService();

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
    private TextField sid;

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

    @FXML
    void backtoFrontPage(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("front page.fxml")));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    // function to add a patron
    // @FXML
    // private void addPatron() {
    //     String sid =  sid.getText();
    //     String name = PName.getText();
    //     String email = PEmail.getText();
    //     String telNumber = PNumber.getText();
    //     String password = Ppassword.getText();
    //     if (sid.isEmpty() || name.isEmpty() || email.isEmpty() || telNumber.isEmpty() || password.isEmpty()) {
    //         showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill in all fields.");
    //         return;
    //     }
    //     int id = Integer.parseInt(sid);
    //     try {
    //         int output = patronService.addPatron(id, name, email, telNumber, password);
    //         if (output == 1){
    //         showAlert(AlertType.INFORMATION, "Success!", "Patron added successfully.");
    //         // Clear input fields
    //         PName.clear();
    //         PEmail.clear();
    //         PNumber.clear();
    //         Ppassword.clear();}
    //     } catch (NumberFormatException e) {
    //         showAlert(AlertType.ERROR, "Form Error!", "Invalid Entry");
    //     }
    // }

    @FXML
    private void showAlert(Alert.AlertType alertType, String title, String message) {
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
            List <Transaction> transaction = patronService.getPatronHistory(patronId, name, password);
            transactions.addAll(transaction);
            Platform.runLater(() -> Ttable.setItems(transactions));
            // Clear input fields
            PName.clear();
            PEmail.clear();
            PNumber.clear();
            Ppassword.clear();
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Form Error!", "Invalid Entry");
        }
        showTransactions(transactions);
    }




    public void showTransactions(ObservableList<Transaction> transactions) {
        ColBook.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        ColBorrowed.setCellValueFactory(new PropertyValueFactory<>("dateBorrowed"));
        ColApproved.setCellValueFactory(new PropertyValueFactory<>("approvalName"));
        ColReturned.setCellValueFactory(new PropertyValueFactory<>("dateReturned"));
        ColReceivedBy.setCellValueFactory(new PropertyValueFactory<>("receiveName"));
        Ttable.setItems(transactions);
    }
}
