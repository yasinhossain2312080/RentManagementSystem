package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardViewController
{
    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void historyButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("history-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("History!");
        nextStage.setScene(scene);
        nextStage.show();
    }


    @javafx.fxml.FXML
    public void tenantsButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("tenants-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Tenants!");
        nextStage.setScene(scene);
        nextStage.show();
    }

    @javafx.fxml.FXML
    public void settingsButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Settings!");
        nextStage.setScene(scene);
        nextStage.show();

    }

    @javafx.fxml.FXML
    public void rentPaymentsButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("rent-payments-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Rent Payments!");
        nextStage.setScene(scene);
        nextStage.show();

    }

    @javafx.fxml.FXML
    public void addTenantsButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-tenants-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Add Tenants!");
        nextStage.setScene(scene);
        nextStage.show();
    }

    @javafx.fxml.FXML
    public void logoutButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("login page!");
        nextStage.setScene(scene);
        nextStage.show();
    }
}