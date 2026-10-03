package com.example.rentmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Match;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class EditTenantsViewController
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
        String searchName = nameTF.getText().trim();
        String searchId = idTF.getText().trim();

        if (searchId.isEmpty() && searchName.isEmpty()){
            Methods.Alert("Please Write NAME or ID");
            return;
        }

        ArrayList<Object> objects = BinaryFileManager.ReadObjects("Tenants.bin");
        boolean found = false;
        for(Object object :objects){
            Tenants tenant = (Tenants)object;

            boolean match;
            if(!searchName.isEmpty() && ! searchId.isEmpty()){
                match = tenant.getName().equalsIgnoreCase(searchName) &&
                        tenant.getId().equalsIgnoreCase(searchId);
            }else if(!searchName.isEmpty()){
                match = tenant.getName().equalsIgnoreCase(searchName);
            }else {
                match = tenant.getId().equalsIgnoreCase(searchId);
            }
            if(match){

                this.selectedTenant = tenant;

                nameLabel.setText(tenant.getName());
                idLabel.setText(tenant.getId());
                advanceLabel.setText(String.valueOf(tenant.getAdvance()));
                phoneNumberLabel.setText(String.valueOf(tenant.getPhoneNumber()));
                locationAreaLabel.setText(selectedTenant.getLocation());
                monthlyRentLabel.setText(String.valueOf(tenant.getMonthlyRent()));
                flatRoomShopLabel.setText(selectedTenant.getFlat());

                found = true;
                break;
            }
        }

        if(!found){
            Methods.Alert("TENANT NOT FOUND!!");

        nameTF.clear();
        idTF.clear();
        nameLabel.setText("");
        idLabel.setText("");
        advanceLabel.setText("");
        phoneNumberLabel.setText("");
        locationAreaLabel.setText("");
        monthlyRentLabel.setText("");
        flatRoomShopLabel.setText("");
        }
    }

    @javafx.fxml.FXML
    public void doYouWantToEditButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (selectedTenant == null) {
            Methods.Alert("Please search for a tenant first");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("edit-tenants-inside-view.fxml"));
        Parent root = fxmlLoader.load();
        EditTenantsInsideViewController controller = fxmlLoader.getController();
        controller.setTenant(selectedTenant);
        Scene scene = new Scene(root);
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Edit Tenants!");
        nextStage.setScene(scene);
        nextStage.show();
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