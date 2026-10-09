package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SettingsViewController
{
    @javafx.fxml.FXML
    private TextField contactNumberTF;
    @javafx.fxml.FXML
    private TextField totalShopTF;
    @javafx.fxml.FXML
    private TextField propertyNameTF;
    @javafx.fxml.FXML
    private TextField ownerNameTF;
    @javafx.fxml.FXML
    private TextField totalFloorsTF;
    @javafx.fxml.FXML
    private TextField propertyAddressTF;
    @javafx.fxml.FXML
    private TextField totalGarmentsTF;

    @javafx.fxml.FXML
    public void initialize() {
        String appData = System.getenv("APPDATA");
        String userID = User.currentUser.getUserID();

        File userFolder = new File(
                appData
                        + File.separator
                        + "RentManagementSystem"
                        + File.separator
                        + "UserData"
                        + File.separator
                        + userID
        );

        File propertyFile = new File(
                userFolder,
                "PropertyInformation_" + userID + ".bin"
        );

        if (!propertyFile.exists()) {
            return;
        }

        try (FileInputStream fis = new FileInputStream(propertyFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            PropertyInformation property =
                    (PropertyInformation) ois.readObject();

            ownerNameTF.setText(property.getOwnerName());
            propertyNameTF.setText(property.getPropertyName());
            propertyAddressTF.setText(property.getPropertyAddress());
            contactNumberTF.setText(property.getContactNumber());

            totalFloorsTF.setText(
                    String.valueOf(property.getTotalFloor()));

            totalGarmentsTF.setText(
                    String.valueOf(property.getTotalGarment()));

            totalShopTF.setText(
                    String.valueOf(property.getTotalShop()));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void backupAndRestoreButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("backup-restore-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Backup & Restore!");
        nextStage.setScene(scene);
        nextStage.show();
    }

    @javafx.fxml.FXML
    public void profileAndChangePasswordButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("account-profile-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Account Profile!");
        nextStage.setScene(scene);
        nextStage.show();

    }

    @javafx.fxml.FXML
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("DashBoard!");
        nextStage.setScene(scene);
        nextStage.show();
    }
}