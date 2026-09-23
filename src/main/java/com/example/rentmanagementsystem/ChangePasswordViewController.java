package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePasswordViewController
{
    @javafx.fxml.FXML
    private PasswordField confirmPasswordPF;
    @javafx.fxml.FXML
    private PasswordField newPasswordPF;
    @javafx.fxml.FXML
    private PasswordField currentPasswordPF;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("account-profile-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Account Profile!");
        nextStage.setScene(scene);
        nextStage.show();
    }

    @javafx.fxml.FXML
    public void changePasswordButtonOnAction(ActionEvent actionEvent) {
    }
}