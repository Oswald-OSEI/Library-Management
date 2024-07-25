package com.example.librarymanagement.models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.librarymanagement.mainClasses.Mains;

public class Librarian extends Person {
    public Librarian(){}

    public Librarian(String name, String email, String telNumber, String password) {
        super(name, email, telNumber, password);
    }
    public Librarian(int id, String name, String email, String telNumber, String password) {
        super(id, name, email, telNumber, password);
    }
    
}
