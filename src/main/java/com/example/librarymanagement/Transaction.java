<<<<<<< HEAD

=======
>>>>>>> 24665cb4a3176ee5fa718ba53264b18216591652
package com.example.librarymanagement;

import java.util.Date;

public class Transaction {
    private int transactionId;
    private Patron patron;
<<<<<<< HEAD
    private String patronName;
    private Books book;
    private String bookTitle;
    private Librarian approvedBy;
    private String approvalName;
    private Librarian receivedBy;
    private String receiveName;
=======
    private Books book;
    private Librarian approvedBy;
    private Librarian receivedBy;
>>>>>>> 24665cb4a3176ee5fa718ba53264b18216591652
    private Date dateBorrowed;
    private Date dateReturned;
    private boolean isReturned;

<<<<<<< HEAD
    public Transaction(Patron patron, Books book, Librarian approvedBy, Librarian receivedBy, Date dateBorrowed, Date dateReturned, boolean isReturned) {
=======
    public Transaction(Patron patron, Books book, Librarian approvedBy, Librarian receivedBy, Date dateBorrowed, Date dateReturned, boolean isReturned  ) {
>>>>>>> 24665cb4a3176ee5fa718ba53264b18216591652
        this.patron = patron;
        this.book = book;
        this.approvedBy = approvedBy;
        this.receivedBy = receivedBy;
<<<<<<< HEAD
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

    public Patron getPatron() {
        return this.patron;
=======
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
>>>>>>> 24665cb4a3176ee5fa718ba53264b18216591652
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public Books getBook() {
<<<<<<< HEAD
        return this.book;
=======
        return book;
>>>>>>> 24665cb4a3176ee5fa718ba53264b18216591652
    }

    public void setBook(Books book) {
        this.book = book;
    }

<<<<<<< HEAD
    public String getBookTitle() {
        return this.bookTitle;
    }

    public void setBookTitle(String title) {
        this.bookTitle = title;
    }

    public Librarian getApprovedBy() {
        return this.approvedBy;
=======
    public Librarian getApprovedBy() {
        return approvedBy;
>>>>>>> 24665cb4a3176ee5fa718ba53264b18216591652
    }

    public void setApprovedBy(Librarian approvedBy) {
        this.approvedBy = approvedBy;
    }

<<<<<<< HEAD
    public String getPatronName() {
        return this.patronName;
    }

    public void setPatronName(String patronName) {
        this.patronName = patronName;
    }

    public Librarian getReceivedBy() {
        return this.receivedBy;
=======
    public Librarian getReceivedBy() {
        return receivedBy;
>>>>>>> 24665cb4a3176ee5fa718ba53264b18216591652
    }

    public void setReceivedBy(Librarian receivedBy) {
        this.receivedBy = receivedBy;
    }

    public Date getDateBorrowed() {
<<<<<<< HEAD
        return this.dateBorrowed;
=======
        return dateBorrowed;
>>>>>>> 24665cb4a3176ee5fa718ba53264b18216591652
    }

    public void setDateBorrowed(Date dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public Date getDateReturned() {
<<<<<<< HEAD
        return this.dateReturned;
=======
        return dateReturned;
>>>>>>> 24665cb4a3176ee5fa718ba53264b18216591652
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public boolean isReturned() {
<<<<<<< HEAD
        return this.isReturned;
    }

    public void setReturned(boolean returned) {
        this.isReturned = returned;
    }
}
=======
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}
>>>>>>> 24665cb4a3176ee5fa718ba53264b18216591652
