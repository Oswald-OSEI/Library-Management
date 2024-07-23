
package com.example.librarymanagement.models;

import java.util.Date;

public class Transaction {
    private int transactionId;
    private Patron patron;
    private String patronName;
    private Books book;
    private String bookTitle;
    private Librarian approvedBy;
    private String approvalName;
    private Librarian receivedBy;
    private String receiveName;
    private Date dateBorrowed;
    private Date dateReturned;
    private boolean isReturned;

    public Transaction(){}

    public Transaction(Patron patron, Books book, Librarian approvedBy, Librarian receivedBy, Date dateBorrowed, Date dateReturned, boolean isReturned) {
        this.patron = patron;
        this.book = book;
        this.approvedBy = approvedBy;
        this.receivedBy = receivedBy;
        this.dateBorrowed = dateBorrowed;
        this.dateReturned = dateReturned;
        this.isReturned = isReturned;
    }

    public void setTransactionId(int Id) {
        this.transactionId = Id;
    }

    public int getTransactionId() {
        return this.transactionId;
    }

    public String getApprovalName() {
        return this.approvalName;
    }

    public void setApprovalName(String Name) {
        this.approvalName = Name;
    }

    public String getReceiveName() {
        return this.receiveName;
    }

    public void setReceiveName(String receivee) {
        this.receiveName = receivee;
    }


    // Getters and Setters

    public Patron getPatron() {
        return this.patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public Books getBook() {

        return this.book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public String getBookTitle() {
        return this.bookTitle;
    }

    public void setBookTitle(String title) {
        this.bookTitle = title;
    }

    public Librarian getApprovedBy() {
        return this.approvedBy;}

    public void setApprovedBy(Librarian approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getPatronName() {
        return this.patronName;
    }

    public void setPatronName(String patronName){
        this.patronName = patronName;
    }

    public Librarian getReceivedBy(){
            return this.receivedBy;
            }
     public void setReceivedBy(Librarian receivedBy){
        this.receivedBy = receivedBy;}

     public Date getDateBorrowed() {
        return dateBorrowed;}

     public void setDateBorrowed(Date dateBorrowed){
        this.dateBorrowed = dateBorrowed;}

      public Date getDateReturned () {
                return this.dateReturned;}

       public void setDateReturned (Date dateReturned){
                this.dateReturned = dateReturned;}


       public void setReturned ( boolean returned){
        this.isReturned = returned;}
      public boolean getReturned(){
        return this.isReturned;
      }

}
