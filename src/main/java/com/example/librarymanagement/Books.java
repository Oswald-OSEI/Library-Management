package com.example.librarymanagement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Books{
    private int bookId;
    private String title;
    private String author;
    private int quantitiesInStock;
    private int numberOfPages;
    private boolean available;

   Books( String title,int numberOfPages,int quantitiesInStock, String author){
    this.title = title;
    this.numberOfPages = numberOfPages;
    this.quantitiesInStock = quantitiesInStock;
    this.author = author;
    this.available = quantitiesInStock>0;
   }
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    // Getter and Setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for numberOfPages
    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    // Getter and Setter for quantitiesInStock
    public int getQuantitiesInStock() {
        return quantitiesInStock;
    }

    public void setQuantitiesInStock(int quantitiesInStock) {
        this.quantitiesInStock = quantitiesInStock;
    }

    // Getter and Setter for author
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean getAvailable(){
       return this.available;
    }

   public void addBook(Books books){
    books.saveToDatabase();
    System.out.println("Book added successfully");
   }
   // Method to get a connection to the database
   private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(Mains.URL, Mains.USER, Mains.PASSWORD);
}

// Method to save the book to the database
private void saveToDatabase() {
    String query = "INSERT INTO books (title, quantitiesInStock, available, author, numberOfPages) VALUES (?, ?, ?, ?, ?)";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, title);
        statement.setInt(2, quantitiesInStock);
        statement.setBoolean(3, available);
        statement.setString(4, author);
        statement.setInt(5, numberOfPages);
        statement.executeUpdate();
        System.out.println("Book added to database successfully.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
