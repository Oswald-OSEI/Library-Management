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
        testLibrarian = new Librarian();
        bookService = new BookService();
        patronService = new PatronService();
        testLibrarian.setPersonId(1);
        testLibrarian.setName("Test Librarian");
        testLibrarian.setEmail("test@library.com");
        testLibrarian.setPassword("password");
        testLibrarian.setTelNumber("1234567890");

        cleanupDatabase();
    }

    @Test
    void testLogin() {
        librarianService.addLibrarian(testLibrarian.getPersonId(), testLibrarian.getName(), testLibrarian.getEmail(),testLibrarian.getTelNumber(), testLibrarian.getPassword());
        int result = librarianService.login(1, "test@library.com", "password");
        assertEquals(1, result);
    }

    @Test
    void testApproveBorrowing() {

        Books testBook = new Books();
        testBook.setBookId(1);
        testBook.setTitle("testBook1");
        testBook.setAuthor("testBook1Author");
        testBook.setNumberOfPages(150);
        testBook.setQuantitiesInStock(6);
        bookService.addBook(testBook);

        Patron testPatron = new Patron();
        testPatron.setPersonId(1);
        testPatron.setName("Patron1");
        testPatron.setEmail("Patron1@gmail.com");
        testPatron.setTelNumber("0549657436");
        testPatron.setPassword("Patron1@password");
        patronService.addPatron(testPatron.getPersonId(), testPatron.getName(), testPatron.getEmail(), testPatron.getTelNumber(), testPatron.getPassword());

        librarianService.addLibrarian(testLibrarian.getPersonId(), testLibrarian.getName(), testLibrarian.getEmail(),testLibrarian.getTelNumber(), testLibrarian.getPassword());

        int result = librarianService.approveBorrowing(
            testBook.getBookId(),
            testPatron.getPersonId(),
                1,
            testBook.getTitle(),
            testPatron.getName(),
            testPatron.getPassword(),
                "password"
        );

        assertEquals(1, result);
    }

     @Test
     void testApproveReturn() {
         Books testBook = new Books();
         testBook.setBookId(1);
         testBook.setTitle("testBook1");
         testBook.setAuthor("testBook1Author");
         testBook.setNumberOfPages(150);
         testBook.setQuantitiesInStock(6);
         bookService.addBook(testBook);

         Patron testPatron = new Patron();
         testPatron.setPersonId(1);
         testPatron.setName("Patron1");
         testPatron.setEmail("Patron1@gmail.com");
         testPatron.setTelNumber("0549657436");
         testPatron.setPassword("Patron1@password");
         patronService.addPatron(testPatron.getPersonId(), testPatron.getName(), testPatron.getEmail(), testPatron.getTelNumber(), testPatron.getPassword());

         librarianService.addLibrarian(testLibrarian.getPersonId(), testLibrarian.getName(), testLibrarian.getEmail(),testLibrarian.getTelNumber(), testLibrarian.getPassword());
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
     librarianService.addLibrarian(testLibrarian.getPersonId(), testLibrarian.getName(), testLibrarian.getEmail(),testLibrarian.getTelNumber(), testLibrarian.getPassword());
       Librarian retrievedLibrarian = librarianService.getLibrarianById(testLibrarian.getPersonId());
       assertNotNull(retrievedLibrarian);
       assertEquals(testLibrarian.getName(), retrievedLibrarian.getName());
    }

    @AfterEach
    void tearDown() throws SQLException {
        cleanupDatabase();
    }
}