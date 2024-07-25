// package com.example.librarymanagement.IntegrationTest;

// import java.sql.SQLException;
// import java.util.List;

// import org.junit.jupiter.api.AfterEach;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.example.librarymanagement.models.Patron;
// import com.example.librarymanagement.models.Transaction;
// import com.example.librarymanagement.service.PatronService;

// public class PatronServiceIntegrationTest extends DataBaseSetupTest{

//     private PatronService patronService;
//     private Patron testPatron;

//     @BeforeEach
//     void setUp() throws SQLException {
//         patronService = new PatronService();
//         testPatron = new Patron();
//         testPatron.setName("Test Patron");
//         testPatron.setEmail("patron@test.com");
//         testPatron.setTelNumber("9876543210");
//         testPatron.setPassword("patronpass");
//         //patronService.addPatron(testPatron.getName(), testPatron.getEmail(), testPatron.getTelNumber(), testPatron.getPassword());

//         //cleanupDatabase();
//     }

//     @Test
//     void testAddPatron() {
//         int result = patronService.addPatron(
//             testPatron.getName(),
//             testPatron.getEmail(),
//             testPatron.getTelNumber(),
//             testPatron.getPassword()
//         );
//         assertEquals(1, result);
//     }

//     @Test
//     void testGetPatronHistory() {
//         // First, add a patron and create some transactions
//         //patronService.addPatron(testPatron.getName(), testPatron.getEmail(), testPatron.getTelNumber(), testPatron.getPassword());
//         // Add some transactions for this patron
//         Patron testPatron = patronService.getPatronById(1);
//         List<Transaction> transactions = patronService.getPatronHistory(
//             (int) testPatron.getPersonId(),
//             testPatron.getName(),
//             testPatron.getPassword()
//         );

//         assertNull(transactions);
//         assertTrue(transactions.isEmpty());
//     }

//     @Test
//     void testGetPatronById() {
//         patronService.addPatron(testPatron.getName(), testPatron.getEmail(), testPatron.getTelNumber(), testPatron.getPassword());
//         Patron retrievedPatron = patronService.getPatronById(testPatron.getPersonId());
//         assertNotNull(retrievedPatron);
//         assertEquals(testPatron.getName(), retrievedPatron.getName());
//     }

//     @AfterEach
//     void tearDown() throws SQLException {
//         //cleanupDatabase();
//     }
// }