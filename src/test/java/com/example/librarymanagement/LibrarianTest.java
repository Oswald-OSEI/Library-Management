package com.example.librarymanagement;
import com.example.librarymanagement.models.Librarian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibrarianTest {

    private Librarian librarian;

    @BeforeEach
    void setUp() {
        librarian = new Librarian("Librarian1", "lib1@example.com", "1111111111", "libpass1");
    }

    @Test
    void testConstructor() {
        assertEquals("Librarian1", librarian.getName());
        assertEquals("lib1@example.com", librarian.getEmail());
        assertEquals("1111111111", librarian.getTelNumber());
        assertEquals("libpass1", librarian.getPassword());
    }
}