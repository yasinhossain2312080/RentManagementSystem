package com.example.rentmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class TenantsViewController
{
    @javafx.fxml.FXML
    private TableColumn<Tenants , String> locationAreaTC;
    @javafx.fxml.FXML
    private TableColumn<Tenants , String> nameTC;
    @javafx.fxml.FXML
    private TableColumn<Tenants, String> flatRoomShopTC;
    @javafx.fxml.FXML
    private TableColumn<Tenants , String> phoneNumberTC;
    @javafx.fxml.FXML
    private TableColumn<Tenants , String> idTC;
    @javafx.fxml.FXML
    private TableColumn<Tenants , Double> advancedTC;
    @javafx.fxml.FXML
    private TableView<Tenants> tenantsInformationTableView;
    @javafx.fxml.FXML
    private TextField searchTenantsTF;
    @javafx.fxml.FXML
    private TableColumn<Tenants , Double> monthlyRentTC;

    @javafx.fxml.FXML
    public void initialize() {

        idTC.setCellValueFactory(new PropertyValueFactory<Tenants , String>("id"));
        nameTC.setCellValueFactory(new PropertyValueFactory<Tenants , String>("name"));
        flatRoomShopTC.setCellValueFactory(new PropertyValueFactory<Tenants , String>("flat"));
        phoneNumberTC.setCellValueFactory(new PropertyValueFactory<Tenants , String>("phoneNumber"));
        locationAreaTC.setCellValueFactory(new PropertyValueFactory<Tenants , String>("location"));
        monthlyRentTC.setCellValueFactory(new PropertyValueFactory<Tenants , Double>("monthlyRent"));
        advancedTC.setCellValueFactory(new PropertyValueFactory<Tenants , Double>("advance"));

    }

    @javafx.fxml.FXML
    public void clickButtonOnAction(ActionEvent actionEvent) {
        tenantsInformationTableView.getItems().clear();

        String searchName = searchTenantsTF.getText().trim();
        String searchId = searchTenantsTF.getText().trim();

        if (searchName.isEmpty() && searchId.isEmpty()){
            tenantsInformationTableView.getItems();
            return;
        }
        ObservableList<Tenants> searchList =
                FXCollections.observableArrayList();

        ArrayList<Object> objects =
                BinaryFileManager.ReadObjects("Tenants.bin");

        for (Object object : objects) {

            Tenants tenant = (Tenants) object;

            if (tenant.getName().equalsIgnoreCase(searchName) ||
            tenant.getId().equalsIgnoreCase(searchId)) {
                searchList.add(tenant);
            }
        }
        if(searchList.isEmpty()){
            Methods.Alert("TENANT NOT FOUND!!");
            return;
        }

        tenantsInformationTableView.setItems(searchList);
    }

    @javafx.fxml.FXML
    public void editButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("edit-tenants-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Edit Tenants!");
        nextStage.setScene(scene);
        nextStage.show();

    }

    @javafx.fxml.FXML
    public void deleteButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("delete-tenants-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Delete Tenants!");
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