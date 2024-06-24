package com.example.librarymanagement;

import java.util.Date;

public class Transaction {
    private int transactionId;
    private Patron patron;
    private Books book;
    private Librarian approvedBy;
    private Librarian receivedBy;
    private Date dateBorrowed;
    private Date dateReturned;
    private boolean isReturned;

    public Transaction(Patron patron, Books book, Librarian approvedBy, Librarian receivedBy, Date dateBorrowed, Date dateReturned, boolean isReturned  ) {
        this.patron = patron;
        this.book = book;
        this.approvedBy = approvedBy;
        this.receivedBy = receivedBy;
        this.dateBorrowed = new Date();
        this.dateReturned = new Date();
        this.isReturned = false;
    }

    // Getters and Setters
    public void setTransactionId(int Id){
        this.transactionId = Id;}

    public int getTransactionId(){
        return transactionId;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public Librarian getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Librarian approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Librarian getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(Librarian receivedBy) {
        this.receivedBy = receivedBy;
    }

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(Date dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}