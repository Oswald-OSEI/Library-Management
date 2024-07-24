package com.example.librarymanagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.example.librarymanagement.service.BookService;
import com.example.librarymanagement.models.Books;

class BookServiceTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private BookService bookService;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        bookService = new BookService();

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Use reflection to set the private connection field
        try {
            java.lang.reflect.Field connectionField = BookService.class.getDeclaredField("connection");
            connectionField.setAccessible(true);
            connectionField.set(bookService, mockConnection);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testUpdateBookStock() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        bookService.updateBookStock(1, 5);

        verify(mockPreparedStatement).setInt(1, 5);
        verify(mockPreparedStatement).setInt(2, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDeleteById() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int result = bookService.deleteById(1);

        assertEquals(1, result);
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetBookById() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("title")).thenReturn("Test Book");
        when(mockResultSet.getInt("numberOfPages")).thenReturn(200);
        when(mockResultSet.getInt("quantitiesInStock")).thenReturn(10);
        when(mockResultSet.getString("author")).thenReturn("Test Author");
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Books book = bookService.getBookById(1);

        assertNotNull(book);
        assertEquals("Test Book", book.getTitle());
        assertEquals(200, book.getNumberOfPages());
        assertEquals(10, book.getQuantitiesInStock());
        assertEquals("Test Author", book.getAuthor());
    }
}