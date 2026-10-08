package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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

        nameTF.setOnAction(event -> {
            idTF.requestFocus();
        });

        nameTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                nameTF.requestFocus();
            }
        });

        idTF.setOnAction(event -> {
            advanceTF.requestFocus();
        });

        idTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                nameTF.requestFocus();
            }
        });


        advanceTF.setOnAction(event -> {
            phoneNumberTF.requestFocus();
        });

        advanceTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                idTF.requestFocus();
            }
        });

        phoneNumberTF.setOnAction(event -> {
            flatRoomShopTF.requestFocus();
        });

        phoneNumberTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                advanceTF.requestFocus();
            }
        });


        flatRoomShopTF.setOnAction(event -> {
            locationAreaTF.requestFocus();
        });

        flatRoomShopTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                phoneNumberTF.requestFocus();
            }
        });

        locationAreaTF.setOnAction(event -> {
            monthlyRentTF.requestFocus();
        });

        locationAreaTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                flatRoomShopTF.requestFocus();
            }
        });


        monthlyRentTF.setOnAction(event -> {
            try {
                addTenantsButtonOnAction(new ActionEvent(monthlyRentTF, null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        monthlyRentTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                locationAreaTF.requestFocus();
            }
        });
    }


    @FXML
    public void addTenantsButtonOnAction(ActionEvent actionEvent) {

        String name = nameTF.getText();
        String id = idTF.getText();
        String location = locationAreaTF.getText();
        String flat = flatRoomShopTF.getText();
        String phoneNumber = phoneNumberTF.getText();

        if (name.isEmpty()
                || id.isEmpty()
                || location.isEmpty()
                || flat.isEmpty()
                || phoneNumber.isEmpty()) {

            Methods.Alert("Please fill up all the fields.");
            return;
        }

        boolean digitFound = false;

        for(int i = 0; i < nameTF.getText().length(); i++) {

            if(nameTF.getText().charAt(i) >= '0'
                    && nameTF.getText().charAt(i) <= '9') {

                digitFound = true;
            }
        }

        if(digitFound) {

            Methods.Alert("Tenant name cannot contain numbers.");
            return;
        }

        boolean CharacterFound = false;

        for(int i = 0; i < advanceTF.getText().length(); i++) {

            if ((advanceTF.getText().charAt(i) >= 'a'
                    && advanceTF.getText().charAt(i) <= 'z')
                    ||
                    (advanceTF.getText().charAt(i) >= 'A'
                            && advanceTF.getText().charAt(i) <= 'Z')) {

                CharacterFound = true;
            }
        }

        if(CharacterFound) {

            Methods.Alert("Advance cannot contain letters.");
            return;
        }

        if (!phoneNumber.matches("\\d+")) {

            Methods.Alert("Phone number must contain only numbers.");
            return;
        }

        if (!phoneNumber.matches("\\d{11}")) {

            Methods.Alert("Phone number must be exactly 11 digits.");
            return;
        }

        double advance = 0;
        double monthlyRent = 0;

        try {

            advance = Double.parseDouble(advanceTF.getText());
            monthlyRent = Double.parseDouble(monthlyRentTF.getText());

        } catch (NumberFormatException e) {

            Methods.Alert("Advance and Monthly Rent must be number.");

            return;
        }

        String userID = User.currentUser.getUserID();

        Tenants t1 = new Tenants(
                name,
                id,
                location,
                flat,
                phoneNumber,
                advance,
                monthlyRent,
                userID
        );

        BinaryFileManager.writeObject("Tenants.bin", t1);

        Methods.Alert("Tenant Added Successfully..");

        nameTF.clear();
        idTF.clear();
        advanceTF.clear();
        phoneNumberTF.clear();
        flatRoomShopTF.clear();
        locationAreaTF.clear();
        monthlyRentTF.clear();
    }


    @FXML
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("dashboard-view.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load());

        Stage nextStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        nextStage.setTitle("DashBoard!");

        nextStage.setScene(scene);

        nextStage.show();
    }
}