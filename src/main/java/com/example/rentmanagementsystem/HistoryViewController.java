package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HistoryViewController
{
    @javafx.fxml.FXML
    private TableView paymentsHistoryTableView;
    @javafx.fxml.FXML
    private TableColumn rentTC;
    @javafx.fxml.FXML
    private TableColumn nameTC;
    @javafx.fxml.FXML
    private TextField tenantsNameTF;
    @javafx.fxml.FXML
    private TableColumn statusTC;
    @javafx.fxml.FXML
    private TableColumn monthTC;
    @javafx.fxml.FXML
    private TableColumn idTC;
    @javafx.fxml.FXML
    private TableColumn currentDueTC;
    @javafx.fxml.FXML
    private TextField idTF;
    @javafx.fxml.FXML
    private TableColumn totalTC;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void clearButtonOnAction(ActionEvent actionEvent) {
    }


    @javafx.fxml.FXML
    public void searchButtonOnAction(ActionEvent actionEvent) {
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