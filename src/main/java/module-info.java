module com.example.librarymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.librarymanagement to javafx.fxml;
    exports com.example.librarymanagement;
}