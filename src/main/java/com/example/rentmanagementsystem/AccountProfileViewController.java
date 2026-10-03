package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        Integer TotalFloor = Integer.parseInt(totalFloorsTF.getText());
        Integer TotalGarments = Integer.parseInt(totalGarmentsTF.getText());
        Integer TotalShop = Integer.parseInt(totalShopTF.getText());

        PropertyInformation property = new PropertyInformation(
                OwnerName,PropertyName,PropertyAddress,ContactNumber,TotalFloor,TotalGarments,TotalShop
        );

        try{
            FileOutputStream fos = new FileOutputStream("PropertyInformation.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(property);

            oos.close();
            fos.close();

            System.out.println("Property Information saved successfully..");

            Methods.Alert("Property Information Successfully Saved.");

        }catch (Exception e){
            e.printStackTrace();
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