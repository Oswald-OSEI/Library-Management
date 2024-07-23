package com.example.librarymanagement;

import com.example.librarymanagement.models.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatronTest {

    private Patron patron;

    @BeforeEach
    void setUp() {
        patron = new Patron("John Doe", "john@example.com", "1234567890", "password123");
    }

    @Test
    void testConstructor() {
        assertEquals("John Doe", patron.getName());
        assertEquals("john@example.com", patron.getEmail());
        assertEquals("1234567890", patron.getTelNumber());
        assertEquals("password123", patron.getPassword());
    }

}