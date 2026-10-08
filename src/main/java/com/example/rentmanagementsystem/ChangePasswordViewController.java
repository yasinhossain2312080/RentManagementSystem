package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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
        // Current Password → New Password
        currentPasswordPF.setOnAction(event -> {
            newPasswordPF.requestFocus();
        });

        currentPasswordPF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                currentPasswordPF.requestFocus();
            }
        });

        newPasswordPF.setOnAction(event -> {
            confirmPasswordPF.requestFocus();
        });

        newPasswordPF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                currentPasswordPF.requestFocus();
            }
        });

        confirmPasswordPF.setOnAction(event -> {
            try {
                changePasswordButtonOnAction(
                        new ActionEvent(confirmPasswordPF, null)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        confirmPasswordPF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                newPasswordPF.requestFocus();
            }
        });
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
        String currentPassword = currentPasswordPF.getText();
        String newPassword = newPasswordPF.getText();
        String confirmPassword = confirmPasswordPF.getText();

        if (currentPassword.isEmpty()
                || newPassword.isEmpty()
                || confirmPassword.isEmpty()) {

            Methods.Alert("Please fill in all password fields.");
            return;
        }

        if (User.currentUser == null) {

            Methods.Alert("No logged-in user found.");
            return;
        }

        if (!User.currentUser.getPassword().equals(currentPassword)) {

            Methods.Alert("Current password is incorrect.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {

            Methods.Alert("New password and confirm password do not match.");
            return;
        }

        if (currentPassword.equals(newPassword)) {

            Methods.Alert("New password must be different from current password.");
            return;
        }

        ArrayList<Object> objects = BinaryFileManager.ReadObjects("Users.bin");

        boolean passwordChanged = false;

        for (Object object : objects) {

            if (object instanceof User) {

                User user = (User) object;

                if (user.getUserID().equals(User.currentUser.getUserID())) {

                    user.setPassword(newPassword);

                    passwordChanged = true;

                    break;
                }
            }
        }

        if (passwordChanged) {
            BinaryFileManager.writeAllObject("Users.bin", objects);
            User.currentUser.setPassword(newPassword);
            Methods.Alert("Password changed successfully.");
            currentPasswordPF.clear();
            newPasswordPF.clear();
            confirmPasswordPF.clear();

        } else {

            Methods.Alert("Unable to change password.");
            return;
        }
    }
}