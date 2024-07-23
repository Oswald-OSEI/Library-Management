package com.example.librarymanagement;
import com.example.librarymanagement.models.Books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BooksTest {

    private Books book;

    @BeforeEach
    void setUp() {
        book = new Books("Test Book", 200, 5, "Test Author");
    }

    @Test
    void testConstructor() {
        assertEquals("Test Book", book.getTitle());
        assertEquals(200, book.getNumberOfPages());
        assertEquals(5, book.getQuantitiesInStock());
        assertEquals("Test Author", book.getAuthor());
        assertTrue(book.getAvailable());
    }

    @Test
    void testSetAndGetBookId() {
        book.setBookId(1);
        assertEquals(1, book.getBookId());
    }

    @Test
    void testSetAndGetTitle() {
        book.setTitle("New Title");
        assertEquals("New Title", book.getTitle());
    }

    @Test
    void testSetAndGetNumberOfPages() {
        book.setNumberOfPages(300);
        assertEquals(300, book.getNumberOfPages());
    }

    @Test
    void testSetAndGetQuantitiesInStock() {
        book.setQuantitiesInStock(10);
        assertEquals(10, book.getQuantitiesInStock());
    }

    @Test
    void testSetAndGetAuthor() {
        book.setAuthor("New Author");
        assertEquals("New Author", book.getAuthor());
    }

    @Test
    void testAvailabilityWhenStockIsZero() {
        Books noStockBook = new Books("No Stock", 100, 0, "Author");
        assertFalse(noStockBook.getAvailable());
    }
}
