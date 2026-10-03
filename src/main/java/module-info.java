module com.example.rentmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;


    opens com.example.rentmanagementsystem to javafx.fxml;
    exports com.example.rentmanagementsystem;
}