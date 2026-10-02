package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class TenantListViewController
{
    @javafx.fxml.FXML
    private TableColumn<Tenants,String> NameTC;
    @javafx.fxml.FXML
    private TableView<Tenants> tenantsListTableView;
    @javafx.fxml.FXML
    private TableColumn<Tenants,String> idTC;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void editButtonOnAction(ActionEvent actionEvent) {

    }

    @javafx.fxml.FXML
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Dash Board!");
        nextStage.setScene(scene);
        nextStage.show();

    }
}