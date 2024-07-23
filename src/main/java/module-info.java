module com.example.librarymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens com.example.librarymanagement.controller to javafx.fxml;
    opens com.example.librarymanagement.mainClasses to javafx.fxml;

    exports com.example.librarymanagement.controller;
    exports com.example.librarymanagement.mainClasses;
    exports com.example.librarymanagement.models;
    exports com.example.librarymanagement.service;
}