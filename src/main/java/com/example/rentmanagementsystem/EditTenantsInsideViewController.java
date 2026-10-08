package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class EditTenantsInsideViewController
{
    @javafx.fxml.FXML
    private TextField editNameTF;

    @javafx.fxml.FXML
    private TextField editIDTF;

    @javafx.fxml.FXML
    private TextField advanceTF;

    @javafx.fxml.FXML
    private TextField locationAreaTF;

    @javafx.fxml.FXML
    private TextField monthlyRentTF;

    @javafx.fxml.FXML
    private TextField phoneNumberTF;

    @javafx.fxml.FXML
    private TextField flatRoomShopTF;


    private Tenants selectedTenant;


    public void setTenant(Tenants tenant) {

        this.selectedTenant = tenant;

        editNameTF.setText(tenant.getName());

        editIDTF.setText(tenant.getId());

        advanceTF.setText(
                String.valueOf(tenant.getAdvance())
        );

        phoneNumberTF.setText(
                tenant.getPhoneNumber()
        );

        locationAreaTF.setText(
                tenant.getLocation()
        );

        flatRoomShopTF.setText(
                tenant.getFlat()
        );

        monthlyRentTF.setText(
                String.valueOf(tenant.getMonthlyRent())
        );
    }


    @javafx.fxml.FXML
    public void initialize() {

        // Name → ID
        editNameTF.setOnAction(event -> {
            editIDTF.requestFocus();
        });

        editNameTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                editNameTF.requestFocus();
            }
        });


        // ID → Advance
        editIDTF.setOnAction(event -> {
            advanceTF.requestFocus();
        });

        editIDTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                editNameTF.requestFocus();
            }
        });


        // Advance → Phone Number
        advanceTF.setOnAction(event -> {
            phoneNumberTF.requestFocus();
        });

        advanceTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                editIDTF.requestFocus();
            }
        });


        // Phone Number → Location/Area
        phoneNumberTF.setOnAction(event -> {
            locationAreaTF.requestFocus();
        });

        phoneNumberTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                advanceTF.requestFocus();
            }
        });


        // Location/Area → Monthly Rent
        locationAreaTF.setOnAction(event -> {
            monthlyRentTF.requestFocus();
        });

        locationAreaTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                phoneNumberTF.requestFocus();
            }
        });


        // Monthly Rent → Flat/Room/Dokan
        monthlyRentTF.setOnAction(event -> {
            flatRoomShopTF.requestFocus();
        });

        monthlyRentTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                locationAreaTF.requestFocus();
            }
        });

        // Flat/Room/Dokan → Update
        flatRoomShopTF.setOnAction(event -> {
            try {
                updateButtonOnAction(
                        new ActionEvent(flatRoomShopTF, null)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        flatRoomShopTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                monthlyRentTF.requestFocus();
            }
        });

    }

    @javafx.fxml.FXML
    public void updateButtonOnAction(ActionEvent actionEvent) {

        if(selectedTenant == null){
            Methods.Alert("NO TENANT SELECTED");
            return;
        }


        String name = editNameTF.getText().trim();
        String id = editIDTF.getText().trim();
        String location = locationAreaTF.getText().trim();
        String flat = flatRoomShopTF.getText().trim();
        String advance = advanceTF.getText().trim();
        String monthlyRentText = monthlyRentTF.getText().trim();
        String phoneNumber = phoneNumberTF.getText().trim();


        if(name.isEmpty()
                || id.isEmpty()
                || location.isEmpty()
                || flat.isEmpty()
                || advance.isEmpty()
                || monthlyRentText.isEmpty()
                || phoneNumber.isEmpty()){

            Methods.Alert("Please All Option..");
            return;
        }


        double advanced;
        double monthlyRent;


        try{

            advanced = Double.parseDouble(advance);
            monthlyRent = Double.parseDouble(monthlyRentText);

        }catch (NumberFormatException e){

            Methods.Alert(
                    "Advance and Monthly Rent must be numbers."
            );

            return;
        }

        String userID = selectedTenant.getUserID();

        Tenants updatedTenant = new Tenants(
                name,
                id,
                location,
                flat,
                phoneNumber,
                advanced,
                monthlyRent,
                userID
        );


        ArrayList<Object> objects =
                BinaryFileManager.ReadObjects("Tenants.bin");


        ArrayList<Object> updateList =
                new ArrayList<>();


        for(Object object : objects){

            Tenants tenant = (Tenants) object;

            if(tenant.getId().equals(selectedTenant.getId())){

                updateList.add(updatedTenant);

            }
            else{

                updateList.add(tenant);
            }
        }


        BinaryFileManager.writeAllObject("Tenants.bin", updateList);

        selectedTenant = updatedTenant;
        Methods.Alert("TENANT UPDATE SUCCESSFULLY.");
        return;
    }


    @javafx.fxml.FXML
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("edit-tenants-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Edit Tenants View!");
        nextStage.setScene(scene);
        nextStage.show();
    }
}