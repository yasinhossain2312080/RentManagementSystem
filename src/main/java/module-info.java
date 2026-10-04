module com.example.rentmanagementsystem {

    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.httpserver;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;

    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.services.drive;
    requires com.google.api.client.auth;
    requires google.api.client;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client.extensions.java6.auth;

    requires jakarta.mail;

    opens com.example.rentmanagementsystem to javafx.fxml;

    exports com.example.rentmanagementsystem;
}