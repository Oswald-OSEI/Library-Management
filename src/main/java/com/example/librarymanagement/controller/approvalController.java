package com.example.librarymanagement.controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import com.example.librarymanagement.main.Mains;
import com.example.librarymanagement.service.BookService;
import com.example.librarymanagement.service.LibrarianService;

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

public class approvalController {

    LibrarianService librarianService = new LibrarianService();
    BookService bookService = new BookService();
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

        try{
            int librarianID = Integer.parseInt(id);
            int output = librarianService.login(librarianID, email, password);
                    if (output == 1) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Login successful!");
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("front page.fxml")));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Invalid credentials.");
                    }
                
        } catch (NumberFormatException e) {
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
            int output = librarianService.approveBorrowing(bID, pID, LId, title, patronName, patronPassword, librariansPassword);
            if(output==1){
            showAlert(Alert.AlertType.INFORMATION, "Success", "Transaction approved successfully!");

            // Clear input fields
            BBookID.clear();
            BTitle.clear();
            PBID.clear();
            PBName.clear();
            bPpassword.clear();}
            else if(output==2){

            showAlert(Alert.AlertType.ERROR, "Error", "Invalid librarian credentials.");
                                }
            else if(output==3){
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid Patron Credentials.");
                    }
            else if (output ==4){
                        showAlert(Alert.AlertType.ERROR, "Error", "Book does not exist.");
                    }

        } catch (NumberFormatException e) {
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
            int output = librarianService.approveReturn(pID, LId, bID, librarianPassword, patronPassword);
            if(output == 1){showAlert(Alert.AlertType.INFORMATION, "Success", "Book return approved successfully!");
             // Clear input fields
            BookID.clear();
            PID.clear();
            Ppassword.clear();
            LPId1.clear();
            LPpassword1.clear();}
            else if (output==2){showAlert(Alert.AlertType.ERROR, "Error", "No matching transaction found.");}
            else if(output ==3) {showAlert(Alert.AlertType.ERROR, "Error", "Invalid librarian credentials.");}
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "An error occurred: " + e.getMessage());
        }
    }
}
