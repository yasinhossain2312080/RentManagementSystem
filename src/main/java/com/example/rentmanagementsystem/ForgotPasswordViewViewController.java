package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotPasswordViewViewController {

    @javafx.fxml.FXML
    private TextField enterPhoneNumberTextField;

    @javafx.fxml.FXML
    private TextField enterEmailTextField;


    @javafx.fxml.FXML
    public void initialize() {
    }


    @javafx.fxml.FXML
    public void backToLoginPageButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Login Page!");
        nextStage.setScene(scene);
        nextStage.show();
    }

    @javafx.fxml.FXML
    public void sendVerificationCodeButtonOnAction(ActionEvent actionEvent) throws IOException {

        String phoneNumber = enterPhoneNumberTextField.getText();
        String email = enterEmailTextField.getText();

        // Check empty fields
        if (phoneNumber.isEmpty() || email.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter Email and Phone Number.");
            alert.showAndWait();
            return;
        }

        // Search user by email and phone
        User user = BinaryFileManager.searchUserByEmailAndPhone("Users.bin", email, phoneNumber);

        // User not found
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Verification Failed");
            alert.setHeaderText(null);
            alert.setContentText("Email or Phone Number is incorrect.");
            alert.showAndWait();

            return;
        }

        // Generate OTP
        String otp = OTPManager.generateOTP(email);

        // Send OTP to user's email
        EmailService.sendOTP(email, otp);


        // Open Set Password page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("set-password-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Set Password!");
        nextStage.setScene(scene);
        nextStage.show();
    }

    @javafx.fxml.FXML
    public void forgotUserIdButtonOnAction(ActionEvent actionEvent) {
        String email = enterEmailTextField.getText();
        String phoneNumber = enterPhoneNumberTextField.getText();

        if (email.isEmpty() || phoneNumber.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter Email and Phone Number.");
            alert.showAndWait();

            return;
        }

        User user = BinaryFileManager.searchUserByEmailAndPhone(
                "Users.bin",
                email,
                phoneNumber
        );

        if (user == null) {
            Methods.Alert("Email or Phone Number is incorrect.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User ID Found");
        alert.setHeaderText(null);
        alert.setContentText("Your ID is: " + user.getUserID());
        alert.showAndWait();
    }
}