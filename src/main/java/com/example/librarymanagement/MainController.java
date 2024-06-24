package com.example.librarymanagement;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.lang.*;
public class MainController{

    @FXML
    private TextField VType;
    @FXML
    private TextField TXID;

    @FXML
    private TextField TXTITLE;

    @FXML
    private TextField TXPAGES;

    @FXML
    private TextField TXAUTHUR;

    @FXML
    private TextField TXSTOCK;

    @FXML
    private TextField newValue;

    @FXML
    private TextField TXID1;

    @FXML
    private Button BTADD;

    @FXML
    private Button EditButton;

    @FXML
    private Button Deletebutton;


    @FXML
    private TableView<Books> TABLE;

    @FXML
    private TableColumn<Books, Integer> COLID;

    @FXML
    private TableColumn<Books, String> COLTITLE;

    @FXML
    private TableColumn<Books, Integer> COLPAGES;

    @FXML
    private TableColumn<Books, Integer> COLSTOCK;

    @FXML
    private TableColumn<Books, String> COLAUTHUR;

    ObservableList<Books> bookList = FXCollections.observableArrayList();


    public void initialize() {
        // Optional initialization code can go here.
        showBooks();
    }

    private Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection(Mains.URL, Mains.USER, Mains.PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    @FXML
    private void addBook() {
        String title = TXTITLE.getText();
        String pagesText = TXPAGES.getText();
        String quantitiesInStockText = TXSTOCK.getText();
        String author = TXAUTHUR.getText();

        if (title.isEmpty() || pagesText.isEmpty() || quantitiesInStockText.isEmpty() || author.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error!", "Please fill in all fields.");
            return;
        }

        try {
            int pages = Integer.parseInt(pagesText);
            int quantitiesInStock = Integer.parseInt(quantitiesInStockText);

            Books book = new Books(title, pages, quantitiesInStock, author);
            book.addBook(book);
            showAlert(AlertType.INFORMATION, "Success!", "Book added successfully.");


            // Clear input fields
            TXID.clear();
            TXTITLE.clear();
            TXPAGES.clear();
            TXAUTHUR.clear();
            TXSTOCK.clear();
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Form Error!", "Please enter valid numbers for pages and stock.");
        }
        bookList.clear();
        initialize();
    }
@FXML
    private void deleteBookById() {
        String id = TXID1.getText();
        int bookId = Integer.parseInt(id);
        String deleteSQL = "DELETE FROM books WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            // Set the parameter for the query
            preparedStatement.setInt(1, bookId);

            // Execute the delete query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(AlertType.INFORMATION, "Success!", "Book deleted successfully.");
                Platform.runLater(() -> TABLE.refresh());

            } else {
                showAlert(AlertType.INFORMATION, "Unsuccessfull", "Book Id doesn't exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bookList.clear();
   initialize();
    }

    @FXML
    private void updateBookById() {
        int bookId = Integer.parseInt(TXID.getText()); // Assuming PBID is a TextField for entering the book ID

        // Assuming updateType and newValue are predefined
        String updateType = VType.getText();
        String NValue = newValue.getText(); // Replace newValue with the actual new value

        String updateSQL = "";

        switch(updateType.toLowerCase()) {
            case "title":
                updateSQL = "UPDATE books SET title = ? WHERE id = ?";
                break;
            case "quantitiesinstock":
                updateSQL = "UPDATE books SET quantitiesInStock = ? WHERE id = ?";
                break;
            case "available":
                updateSQL = "UPDATE books SET available = ? WHERE id = ?";
                break;
            case "author":
                updateSQL = "UPDATE books SET author = ? WHERE id = ?";
                break;
            case "numberofpages":
                updateSQL = "UPDATE books SET numberOfPages = ? WHERE id = ?";
                break;
            default:
                showAlert(AlertType.INFORMATION, "Failed!", "Update type can't be performed.");
                return;
        }

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            switch(updateType.toLowerCase()) {
                case "quantitiesInStock":
                case "numberOfPages":
                    preparedStatement.setInt(1, Integer.parseInt(NValue));
                    break;
                case "author":
                case "title":
                    preparedStatement.setString(1, NValue);
                    break;
            }

            preparedStatement.setInt(2, bookId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(AlertType.INFORMATION, "Success!", "Book updated successfully.");
            } else {
                showAlert(AlertType.INFORMATION, "Unsuccessful!", "Book doesn't exist. Check book ID entered.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid format for quantity: " + NValue);
            e.printStackTrace();
        }
        bookList.clear();
        initialize();
    }


    public ObservableList<Books> getBooksList(){

         Connection conn = getConnection();
         String query = "SELECT id, title, numberOfPages, quantitiesInStock, author FROM books";
         Statement st;
         ResultSet rs;
         try{
             st = conn.createStatement();
             rs = st.executeQuery(query);
             Books books;
             while(rs.next()){
                 books = new Books( rs.getString("title"),rs.getInt("numberOfPages"), rs.getInt("quantitiesInStock"),rs.getString("author") );
                 books.setBookId(rs.getInt("id"));
                 bookList.add(books);
             }


         } catch (SQLException ex) {
             ex.printStackTrace();
         }
        return bookList;
    }
    public void showBooks(){
        ObservableList<Books> bookList = getBooksList();
        COLID.setCellValueFactory(new PropertyValueFactory<Books, Integer>("bookId"));
        COLTITLE.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
        COLPAGES.setCellValueFactory(new PropertyValueFactory<Books, Integer>("numberOfPages"));
        COLSTOCK.setCellValueFactory(new PropertyValueFactory<Books, Integer>("quantitiesInStock"));
        COLAUTHUR.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        TABLE.setItems(bookList);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
