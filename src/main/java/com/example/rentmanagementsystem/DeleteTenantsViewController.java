package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class DeleteTenantsViewController
{
    @javafx.fxml.FXML
    private TextField nameTF;
    @javafx.fxml.FXML
    private Label idLabel;
    @javafx.fxml.FXML
    private Label monthlyRentLabel;
    @javafx.fxml.FXML
    private Label flatRoomShopLabel;
    @javafx.fxml.FXML
    private Label locationAreaLabel;
    @javafx.fxml.FXML
    private Label phoneNumberLabel;
    @javafx.fxml.FXML
    private TextField idTF;
    @javafx.fxml.FXML
    private Label advanceLabel;
    @javafx.fxml.FXML
    private Label nameLabel;

    private Tenants selectedTenant;

    @javafx.fxml.FXML
    public void initialize() {
    }


    @javafx.fxml.FXML
    public void searchButtonOnAction(ActionEvent actionEvent) {
        String name = nameTF.getText().trim();
        String id = idTF.getText().trim();

        if(name.isEmpty() && id.isEmpty()){
            Methods.Alert("Please Enter NAME or ID.");
            return;
        }
        ArrayList<Object>objects = BinaryFileManager.ReadObjects("Tenants.bin");
        boolean found = false;
        for(Object object : objects){
            Tenants tenant = (Tenants) object;

            if(!name.isEmpty() && tenant.getName().equalsIgnoreCase(name)||
                    (!id.isEmpty() && tenant.getId().equalsIgnoreCase(id))){
                selectedTenant = tenant;
                nameLabel.setText(tenant.getName());
                idLabel.setText(tenant.getId());
                advanceLabel.setText(String.valueOf(tenant.getAdvance()));
                phoneNumberLabel.setText(tenant.getPhoneNumber());
                locationAreaLabel.setText(tenant.getLocation());
                monthlyRentLabel.setText(String.valueOf(tenant.getMonthlyRent()));
                flatRoomShopLabel.setText(tenant.getFlat());

                found = true;
                break;
            }
        }
        if(!found){
            Methods.Alert("TENANT NOT FOUND!!");
        }
    }

    @javafx.fxml.FXML
    public void deleteButtonOnAction(ActionEvent actionEvent) {
        if(selectedTenant == null){
            Methods.Alert("Please search for a Tenant first.");
            return;
        }
        Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);
        myAlert.setTitle("Delete Tenant");
        myAlert.setHeaderText(null);
        myAlert.setContentText("Are you sure you want to delete " + selectedTenant.getName() + "?");

        Optional<ButtonType> result = myAlert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            deleteTenantFromFile();

            selectedTenant= null;

            nameTF.setText("");
            idTF.setText("");

            nameLabel.setText("");
            idLabel.setText("");
            advanceLabel.setText("");
            phoneNumberLabel.setText("");
            locationAreaLabel.setText("");
            monthlyRentLabel.setText("");
            flatRoomShopLabel.setText("");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Tenant deleted successfully.");
            alert.showAndWait();
        }
    }

    private void deleteTenantFromFile(){
        ArrayList<Object>objects = BinaryFileManager.ReadObjects("Tenants.bin");
        ArrayList<Object>updateList = new ArrayList<>();
        for(Object object:objects){
            Tenants tenant = (Tenants) object;
            if(!tenant.getId().equals(selectedTenant.getId())){
                updateList.add(tenant);
            }
        }
        BinaryFileManager.writeAllObject("Tenants.bin",updateList);
    }

    @javafx.fxml.FXML
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("tenants-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Tenants!");
        nextStage.setScene(scene);
        nextStage.show();
    }
}