package com.example.librarymanagement.models;

public class Books{
    private int bookId;
    private String title;
    private String author;
    private int quantitiesInStock;
    private int numberOfPages;
    private boolean available;

    public Books(){}

    public Books( String title,int numberOfPages,int quantitiesInStock, String author){
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

}
 

