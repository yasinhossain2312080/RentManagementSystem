package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class BackupAndRestoreViewController
{
    @javafx.fxml.FXML
    private Label lastBackupLabel;
    @javafx.fxml.FXML
    private TextField googleAccountTextField;
    @javafx.fxml.FXML
    private Label backupStatusLabel;

    @javafx.fxml.FXML
    public void initialize() {

    }

    @javafx.fxml.FXML
    public void restoreButtonOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void connectGoogleAccountButtonOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void backupNowButtonOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void backButtonOnAction(ActionEvent actionEvent)  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Settings!");
        nextStage.setScene(scene);
        nextStage.show();
    }
}