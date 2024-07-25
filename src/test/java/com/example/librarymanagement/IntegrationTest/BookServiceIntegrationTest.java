package com.example.librarymanagement.IntegrationTest;


import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.librarymanagement.models.Books;
import com.example.librarymanagement.service.BookService;

public class BookServiceIntegrationTest extends DataBaseSetupTest {

    private BookService bookService;
    private Books testBook;

    @BeforeEach
    void setUp() throws SQLException {
        bookService = new BookService();
        testBook = new Books();
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setQuantitiesInStock(10);
        testBook.setNumberOfPages(200);
        testBook.setBookId(1);

        //cleanupDatabase();
    }

    @Test
    void testAddBook() {
        bookService.addBook(testBook);
        Books retrievedBook = bookService.getBookById(testBook.getBookId());
        assertNotNull(retrievedBook);
        assertEquals(testBook.getTitle(), retrievedBook.getTitle());
    }

    @Test
    void testUpdateBookStock() throws SQLException {
        bookService.addBook(testBook);
        bookService.updateBookStock(testBook.getBookId(), 5);
        Books updatedBook = bookService.getBookById(testBook.getBookId());
        assertEquals(15, updatedBook.getQuantitiesInStock());
    }

    @Test
    void testDeleteBook() {
        bookService.addBook(testBook);
        int rowsAffected = bookService.deleteById(testBook.getBookId());
        assertEquals(1, rowsAffected);
        assertNull(bookService.getBookById(testBook.getBookId()));
    }

    @Test
    void testUpdateBook() {
        bookService.addBook(testBook);
        int rowsAffected = bookService.updateById(testBook.getBookId(), "Updated Title", "title");
        assertEquals(1, rowsAffected);
        Books updatedBook = bookService.getBookById(testBook.getBookId());
        assertEquals("Updated Title", updatedBook.getTitle());
    }

    @AfterEach
    void tearDown() throws SQLException {
        cleanupDatabase();
    }
}