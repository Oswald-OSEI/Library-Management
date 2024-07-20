package com.example.librarymanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    private Person person;

    @BeforeEach
    void setUp() {
        // Create a concrete subclass of Person for testing
        person = new Patron("John Doe", "john@example.com", "1234567890", "password123");
    }

    @Test
    void testConstructor() {
        assertEquals("John Doe", person.getName());
        assertEquals("john@example.com", person.getEmail());
        assertEquals("1234567890", person.getTelNumber());
        assertEquals("password123", person.getPassword());
    }

    @Test
    void testSetAndGetPersonId() {
        person.setPersonId(1);
        assertEquals(1, person.getPersonId());
    }

    @Test
    void testSetAndGetName() {
        person.setName("Jane Doe");
        assertEquals("Jane Doe", person.getName());
    }

    @Test
    void testSetAndGetEmail() {
        person.setEmail("jane@example.com");
        assertEquals("jane@example.com", person.getEmail());
    }

    @Test
    void testSetAndGetTelNumber() {
        person.setTelNumber("0987654321");
        assertEquals("0987654321", person.getTelNumber());
    }

    @Test
    void testSetAndGetPassword() {
        person.setPassword("newpassword123");
        assertEquals("newpassword123", person.getPassword());
    }
}