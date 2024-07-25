package com.example.librarymanagement.IntegrationTest;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.librarymanagement.models.Books;
import com.example.librarymanagement.models.Librarian;
import com.example.librarymanagement.models.Patron;
import com.example.librarymanagement.service.BookService;
import com.example.librarymanagement.service.LibrarianService;
import com.example.librarymanagement.service.PatronService;

public class LibrarianServiceIntegrationTest extends DataBaseSetupTest {

    private LibrarianService librarianService;
    private BookService bookService;
    private PatronService patronService;
    private Librarian testLibrarian;

    @BeforeEach
    void setUp() throws SQLException {
        librarianService = new LibrarianService();
        bookService = new BookService();
        patronService = new PatronService();

        testLibrarian = new Librarian();
        testLibrarian.setPersonId(1);
        testLibrarian.setName("Test Librarian");
        testLibrarian.setEmail("test@library.com");
        testLibrarian.setPassword("password123");
        testLibrarian.setTelNumber("1234567890");
        // Add method to save librarian
        cleanupDatabase();
    }

    @Test
    void testLogin() {
        int result = librarianService.login(testLibrarian.getPersonId(), testLibrarian.getEmail(), testLibrarian.getPassword());
        assertEquals(1, result);
    }

    @Test
    void testApproveBorrowing() {
        Books testBook = new Books();
        testBook.setTitle("testBook1");
        testBook.setAuthor("testBook1Author");
        testBook.setNumberOfPages(150);
        testBook.setQuantitiesInStock(6);
        bookService.addBook(testBook);

        Patron testPatron = new Patron();
        testPatron.setName("Patron1");
        testPatron.setEmail("Patron1@gmail.com");
        testPatron.setTelNumber("0549657436");
        testPatron.setPassword("Patron1@password");
        patronService.addPatron(testPatron.getName(), testPatron.getEmail(), testPatron.getTelNumber(), testPatron.getPassword());

        int result = librarianService.approveBorrowing(
            testBook.getBookId(),
            testPatron.getPersonId(),
            testLibrarian.getPersonId(),
            testBook.getTitle(),
            testPatron.getName(),
            testPatron.getPassword(),
            testLibrarian.getPassword()
        );

        assertEquals(1, result);
    }

    @Test
    void testApproveReturn() {
        // Similar to approveBorrowing, first set up a borrowed book
        Patron testPatron = patronService.getPatronById(1);
        Books testBook = bookService.getBookById(1);
        int result = librarianService.approveReturn(
            testPatron.getPersonId(),
            testLibrarian.getPersonId(),
            testBook.getBookId(),
            testLibrarian.getPassword(),
            testPatron.getPassword()
        );

        assertEquals(1, result);
    }

    @Test
    void testGetLibrarianById() {
        Librarian retrievedLibrarian = librarianService.getLibrarianById(testLibrarian.getPersonId());
        assertNotNull(retrievedLibrarian);
        assertEquals(testLibrarian.getName(), retrievedLibrarian.getName());
    }

    @AfterEach
    void tearDown() throws SQLException {
        cleanupDatabase();
    }
}