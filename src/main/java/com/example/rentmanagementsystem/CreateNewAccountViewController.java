package com.example.rentmanagementsystem;

import com.google.api.client.googleapis.mtls.MtlsProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateNewAccountViewController
{
    @javafx.fxml.FXML
    private TextField userNameTextField;
    @javafx.fxml.FXML
    private TextField phoneNumberTextField;
    @javafx.fxml.FXML
    private TextField passwordTextField;
    @javafx.fxml.FXML
    private TextField emailTextField;
    @javafx.fxml.FXML
    private TextField confirmPasswordTextField;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void createNewAccountButtonOnAction(ActionEvent actionEvent) {

        String userName = userNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String password = passwordTextField.getText();
        String email = emailTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        if(password.isEmpty() || userName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || confirmPassword.isEmpty()){
            Methods.Alert("Please fillup all option.");
            return;
        }
        if(!password.equals(confirmPassword)){
            Methods.Alert("Password and Confirm password do not match.");
            return;
        }

        if(BinaryFileManager.userEmailOrPhoneNumber("Users.bin",email,phoneNumber)){
            Methods.Alert("This phone number or email is already registered.");
            return;
        }
        String userID;
        do {
            userID = String.valueOf((int)(Math.random() * 9000) + 1000);
        }while (BinaryFileManager.userIDExists("Users.bin",userID));

        User user = new User(
                userName,userID,email,phoneNumber,password
        );


        BinaryFileManager.writeObject("Users.bin",user);

        Alert myAlert = new Alert(Alert.AlertType.INFORMATION);
        myAlert.setTitle("Account Created");
        myAlert.setHeaderText("Account Created Successfully");
        myAlert.setContentText("Your Account Created Successfully.\n\n"
        + "Your User ID : " + userID
        + "\n\nPlease remember your User ID.");
        myAlert.showAndWait();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage nextStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            nextStage.setTitle("Login Page!");
            nextStage.setScene(scene);
            nextStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void backToLoginPageButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Login Page!");
        nextStage.setScene(scene);
        nextStage.show();
    }
}