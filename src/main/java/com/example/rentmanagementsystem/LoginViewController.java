package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController
{
    @javafx.fxml.FXML
    private TextField userNameTextField;

    @javafx.fxml.FXML
    private PasswordField passwordField;


    @javafx.fxml.FXML
    public void initialize() {

        userNameTextField.setOnAction(event -> {
            passwordField.requestFocus();
        });

        passwordField.setOnAction(event -> {
            try {
                loginButtonOnAction(new ActionEvent(passwordField, null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @javafx.fxml.FXML
    public void resisterButtonOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-new-account-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        nextStage.setTitle("Create New Account!");

        nextStage.setScene(scene);

        nextStage.show();
    }


    @javafx.fxml.FXML
    public void loginButtonOnAction(ActionEvent actionEvent) throws IOException {

        String userID = userNameTextField.getText();

        String password = passwordField.getText();


        if(userID.isEmpty() || password.isEmpty()){

            Methods.Alert("Please enter user ID and Password.");

            return;
        }


        User user = BinaryFileManager.searchUserIDAndPassword("Users.bin", userID, password);


        if(user == null){

            Methods.Alert("Invalid userID and Password.");

            return;
        }


        User.currentUser = user;


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());


        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        nextStage.setTitle("dashboard!");

        nextStage.setScene(scene);

        nextStage.show();
    }


    @javafx.fxml.FXML
    public void forgotPasswordButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fogot-password-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Forgot Password!");
        nextStage.setScene(scene);
        nextStage.show();
    }
}