package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class AddTenantsViewController
{
    @FXML
    private TextField locationAreaTF;
    @FXML
    private TextField advanceTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField monthlyRentTF;
    @FXML
    private TextField phoneNumberTF;
    @FXML
    private TextField idTF;
    @FXML
    private TextField flatRoomShopTF;

    @FXML
    public void initialize() {

    }

    @FXML
    public void addTenantsButtonOnAction(ActionEvent actionEvent) {
        String name = nameTF.getText();
        String id = idTF.getText();
        String location = locationAreaTF.getText();
        String flat = flatRoomShopTF.getText();
        String phoneNumber = phoneNumberTF.getText();

        double advance = 0;
        double monthlyRent = 0;

        try{
            advance = Double.parseDouble(advanceTF.getText());
            monthlyRent = Double.parseDouble(monthlyRentTF.getText());
        }catch (NumberFormatException e){
            Methods.Alert("Advance and Monthly Rent must be number.");
            return;
        }

        boolean digitFound = false;
        for(int i =0 ; i <  nameTF.getText().length(); i++){
            if(nameTF.getText().charAt(i) >='0' && nameTF.getText().charAt(i) <= '9'){
                digitFound = true;
            }
        }

        boolean CharacterFound = false;
        for(int i = 0 ; i < advanceTF.getText().length(); i++){
            if (advanceTF.getText().charAt(i)>='a' && advanceTF.getText().charAt(i)<='z' ||
            advanceTF.getText().charAt(i) >= 'A' && advanceTF.getText().charAt(i) <= 'Z'){
                CharacterFound = true;
            }
        }


        if(name.isEmpty() || id.isEmpty() || location.isEmpty() || flat.isEmpty() || digitFound || CharacterFound){
            Methods.Alert("please FillUp this option first");
            return;
        }

        Tenants t1 = new Tenants(
                name,
                id,
                location,
                flat,
                phoneNumber,
                advance,
                monthlyRent
        );

        BinaryFileManager.writeObject("Tenants.bin",t1);
        Methods.Alert("Tenant Added Successfully..");
    }

    @FXML
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("DashBoard!");
        nextStage.setScene(scene);
        nextStage.show();
    }
}