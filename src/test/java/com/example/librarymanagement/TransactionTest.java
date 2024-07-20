package com.example.librarymanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private Transaction transaction;
    private Patron patron;
    private Books book;
    private Librarian approvedBy;
    private Librarian receivedBy;
    private Date dateBorrowed;
    private Date dateReturned;

    @BeforeEach
    void setUp() {
        patron = new Patron("John Doe", "john@example.com", "1234567890", "password123");
        book = new Books("Test Book", 200, 5, "Test Author");
        approvedBy = new Librarian("Librarian1", "lib1@example.com", "1111111111", "libpass1");
        receivedBy = new Librarian("Librarian2", "lib2@example.com", "2222222222", "libpass2");
        dateBorrowed = new Date();
        dateReturned = new Date();
        transaction = new Transaction(patron, book, approvedBy, receivedBy, dateBorrowed, dateReturned, false);
    }

    @Test
    void testConstructor() {
        assertEquals(patron, transaction.getPatron());
        assertEquals(book, transaction.getBook());
        assertEquals(approvedBy, transaction.getApprovedBy());
        assertEquals(receivedBy, transaction.getReceivedBy());
        assertEquals(dateBorrowed, transaction.getDateBorrowed());
        assertEquals(dateReturned, transaction.getDateReturned());
        assertFalse(transaction.getReturned());
    }

    @Test
    void testSetAndGetTransactionId() {
        transaction.setTransactionId(1);
        assertEquals(1, transaction.getTransactionId());
    }

    @Test
    void testSetAndGetApprovalName() {
        transaction.setApprovalName("Approval Name");
        assertEquals("Approval Name", transaction.getApprovalName());
    }

    @Test
    void testSetAndGetReceiveName() {
        transaction.setReceiveName("Receive Name");
        assertEquals("Receive Name", transaction.getReceiveName());
    }

    @Test
    void testSetAndGetBookTitle() {
        transaction.setBookTitle("New Book Title");
        assertEquals("New Book Title", transaction.getBookTitle());
    }

    @Test
    void testSetAndGetPatronName() {
        transaction.setPatronName("New Patron Name");
        assertEquals("New Patron Name", transaction.getPatronName());
    }

    @Test
    void testSetReturned() {
        transaction.setReturned(true);
        assertTrue(transaction.getReturned());
    }
}