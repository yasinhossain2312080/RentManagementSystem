package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SetPasswordViewController {

    @javafx.fxml.FXML
    private TextField newPasswordTextField;

    @javafx.fxml.FXML
    private TextField verificationCodeTextField;

    @javafx.fxml.FXML
    private TextField confirmPasswordTextField;


    @javafx.fxml.FXML
    public void initialize() {
    }


    @javafx.fxml.FXML
    public void resetPasswordButtonOnAction(ActionEvent actionEvent) {

        String otp = verificationCodeTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();


        // Check empty fields
        if (otp.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();

            return;
        }


        // Verify OTP
        if (!OTPManager.verifyOTP(otp)) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid OTP");
            alert.setHeaderText(null);
            alert.setContentText("The verification code is incorrect.");
            alert.showAndWait();

            return;
        }


        // Check password match
        if (!newPassword.equals(confirmPassword)) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Error");
            alert.setHeaderText(null);
            alert.setContentText("New password and confirm password do not match.");
            alert.showAndWait();

            return;
        }


        // Get user email
        String email = OTPManager.getUserEmail();


        if (email == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("User information not found.");
            alert.showAndWait();

            return;
        }


        // Read all users
        ArrayList<Object> objects =
                BinaryFileManager.ReadObjects("Users.bin");


        boolean passwordChanged = false;


        // Find user and change password
        for (Object object : objects) {

            if (object instanceof User) {

                User user = (User) object;

                if (user.getEmail().trim().equalsIgnoreCase(email.trim())) {

                    user.setPassword(newPassword);

                    passwordChanged = true;

                    break;
                }
            }
        }


        // Save updated users
        if (passwordChanged) {

            BinaryFileManager.writeAllObject(
                    "Users.bin",
                    objects
            );


            // Clear OTP
            OTPManager.clearOTP();


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Reset Successful");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Your password has been changed successfully."
            );
            alert.showAndWait();


            // Go to Login Page
            try {

                FXMLLoader fxmlLoader =
                        new FXMLLoader(
                                HelloApplication.class.getResource("login-view.fxml")
                        );

                Scene scene = new Scene(fxmlLoader.load());

                Stage nextStage =
                        (Stage) ((Node) actionEvent.getSource())
                                .getScene()
                                .getWindow();

                nextStage.setTitle("Login Page!");
                nextStage.setScene(scene);
                nextStage.show();

            } catch (IOException e) {

                e.printStackTrace();
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("User not found.");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void backToLoginPageButtonOnAction(ActionEvent actionEvent)
            throws IOException {

        FXMLLoader fxmlLoader =
                new FXMLLoader(
                        HelloApplication.class.getResource("login-view.fxml")
                );

        Scene scene = new Scene(fxmlLoader.load());

        Stage nextStage =
                (Stage) ((Node) actionEvent.getSource())
                        .getScene()
                        .getWindow();

        nextStage.setTitle("Login Page!");
        nextStage.setScene(scene);
        nextStage.show();
    }
}