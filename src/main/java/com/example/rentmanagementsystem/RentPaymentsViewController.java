package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RentPaymentsViewController
{
    @javafx.fxml.FXML
    private TextField totalTextFiled;
    @javafx.fxml.FXML
    private DatePicker paymentsDateDatePicker;
    @javafx.fxml.FXML
    private TextField monthlyRentTF;
    @javafx.fxml.FXML
    private TextField waterBillTextField;
    @javafx.fxml.FXML
    private TextField electricityBillTextField;
    @javafx.fxml.FXML
    private TextField previousDue;
    @javafx.fxml.FXML
    private TextField monthTF;
    @javafx.fxml.FXML
    private TextField tenantsNameTF;
    @javafx.fxml.FXML
    private TextField gasBillTextField;
    @javafx.fxml.FXML
    private TextField currentDueTextField;
    @javafx.fxml.FXML
    private ComboBox<String> paymentsMethodComboBox;
    @javafx.fxml.FXML
    private ComboBox<String> paymentsStatusComboBox;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void savePaymentsButtonOnAction(ActionEvent actionEvent) {
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