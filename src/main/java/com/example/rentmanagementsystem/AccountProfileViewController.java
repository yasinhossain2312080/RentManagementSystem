package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class AccountProfileViewController
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
    }

    @javafx.fxml.FXML
    public void updateProfileButtonOnAction(ActionEvent actionEvent) {

        String OwnerName = ownerNameTF.getText();
        String PropertyName = propertyNameTF.getText();
        String PropertyAddress = propertyAddressTF.getText();
        String ContactNumber = contactNumberTF.getText();
        String TotalFloorText = totalFloorsTF.getText();
        String TotalGarmentsText = totalGarmentsTF.getText();
        String TotalShopText = totalShopTF.getText();

        // Check any field is empty
        if (OwnerName.isEmpty() ||
                PropertyName.isEmpty() ||
                PropertyAddress.isEmpty() ||
                ContactNumber.isEmpty() ||
                TotalFloorText.isEmpty() ||
                TotalGarmentsText.isEmpty() ||
                TotalShopText.isEmpty()) {

            Methods.Alert("Please fill up all the fields.");

            return;
        }

        Integer TotalFloor = Integer.parseInt(TotalFloorText);
        Integer TotalGarments = Integer.parseInt(TotalGarmentsText);
        Integer TotalShop = Integer.parseInt(TotalShopText);

        PropertyInformation property =
                new PropertyInformation(
                        OwnerName,
                        PropertyName,
                        PropertyAddress,
                        ContactNumber,
                        TotalFloor,
                        TotalGarments,
                        TotalShop
                );


        try {

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

            if (!userFolder.exists()) {
                userFolder.mkdirs();
            }

            File propertyFile = new File(userFolder, "PropertyInformation_" + userID + ".bin");
            FileOutputStream fos = new FileOutputStream(propertyFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(property);
            oos.close();
            fos.close();

            System.out.println("Property Information saved successfully..");
            Methods.Alert("Property Information Successfully Saved.");

        } catch (Exception e) {
            e.printStackTrace();
            Methods.Alert("Property Information could not be saved.");
        }
    }

    @javafx.fxml.FXML
    public void couldYouChangeYourPasswordButtonOnAction(ActionEvent actionEvent)  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("change-password-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Change Password!");
        nextStage.setScene(scene);
        nextStage.show();
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