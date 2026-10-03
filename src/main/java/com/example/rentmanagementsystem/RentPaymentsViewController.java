package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.plaf.metal.MetalTheme;
import java.io.IOException;
import java.util.ArrayList;

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
    private TextField TenantsID;
    @javafx.fxml.FXML
    private TextField paidAmountTextFiled;
    private Tenants selectedTenant;

    @javafx.fxml.FXML
    public void initialize() {
        paymentsMethodComboBox.getItems().addAll("Cash","Bkash","Nagad","Roket");

        paymentsStatusComboBox.getItems().addAll("Paid","Partially Paid");
        paymentsStatusComboBox.setDisable(true);

    }

    @javafx.fxml.FXML
    public void searchButtonOnAction(ActionEvent actionEvent) {
        String name = tenantsNameTF.getText().trim();
        String id = TenantsID.getText().trim();
        if(name.isEmpty() && (id.isEmpty())){
            Methods.Alert("Please Enter Tenant NAME or ID.");
            return;
        }

        ArrayList<Object> objects = BinaryFileManager.ReadObjects("Tenants.bin");
        boolean found = false;
        for(Object object : objects){
            Tenants tenant = (Tenants) object;
            if(!name.isEmpty() && (tenant.getName().equalsIgnoreCase(name))||
                    (!id.isEmpty()) &&(tenant.getId().equalsIgnoreCase(id))){
                selectedTenant = tenant;

                monthlyRentTF.setText(String.valueOf(tenant.getMonthlyRent()));

                double due = getPreviousDue(tenant.getId());
                previousDue.setText(String.valueOf(due));
                found= true;
                break;
            }
        }
        if(!found){
            Methods.Alert("TENANT NOT FOUND!!");
        }
    }

    private double getPreviousDue(String tenantId){
        ArrayList<Object>objects = BinaryFileManager.ReadObjects("RentPayments.bin");
        double PreviousDue = 0;
        for(Object object:objects){
            RentPayments payment = (RentPayments) object;
            if(payment.getTenantId().equalsIgnoreCase(tenantId)){
                PreviousDue = payment.getCurrentDue();
            }
        }
        return PreviousDue;
    }


    @javafx.fxml.FXML
    public void calculateButtonOnAction(ActionEvent actionEvent) {

        try {
            double monthlyRent = Double.parseDouble(monthlyRentTF.getText());
            double previous = Double.parseDouble(previousDue.getText());
            double electricity = Double.parseDouble(electricityBillTextField.getText());
            double water = Double.parseDouble(waterBillTextField.getText());
            double gas = Double.parseDouble(gasBillTextField.getText());
            double paidAmount = Double.parseDouble(paidAmountTextFiled.getText());
            double total = monthlyRent + previous + electricity + water + gas;

            if(paidAmount > total){
                Methods.Alert("Paid Amount cannot be greater then Total Amount.");
            }
            double currentDue = (total - paidAmount);
            totalTextFiled.setText(String.valueOf(total));
            currentDueTextField.setText(String.valueOf(currentDue));

            if(paidAmount == total){
                paymentsStatusComboBox.setValue("Paid");
            }else if (paidAmount > 0){
                paymentsStatusComboBox.setValue("Partially Paid");
            }else {
                paymentsStatusComboBox.setValue("Due");
            }

        } catch (NumberFormatException e) {
            Methods.Alert("Please enter valid bill amounts.");
            return;
        }

    }

    @javafx.fxml.FXML
    public void savePaymentsButtonOnAction(ActionEvent actionEvent) {
        if(selectedTenant == null){
            Methods.Alert("Please search a Tenant first.");
            return;
        }

        String month = monthTF.getText().trim();

        if(month.isEmpty()){
            Methods.Alert("Please enter payment month.");
            return;
        }
        if(paymentsDateDatePicker.getValue() == null){
            Methods.Alert("Please select payment date.");
            return;
        }
        if(paymentsMethodComboBox.getValue() == null){
            Methods.Alert("Please select payment method.");
            return;
        }
        try{
                double monthlyRent = Double.parseDouble(monthlyRentTF.getText());
                double previous = Double.parseDouble(previousDue.getText());
                double electricityBill = Double.parseDouble(electricityBillTextField.getText());
                double waterBill = Double.parseDouble(waterBillTextField.getText());
                double gasBill= Double.parseDouble(gasBillTextField.getText());
                double total = Double.parseDouble(totalTextFiled.getText());
                double paidAmount = Double.parseDouble(paidAmountTextFiled.getText());
                double currentDue = Double.parseDouble(currentDueTextField.getText());

                RentPayments payment = new RentPayments(
                    selectedTenant.getName(),
                    selectedTenant.getId(),
                    month,
                    paymentsMethodComboBox.getValue(),
                    paymentsStatusComboBox.getValue(),
                    monthlyRent,
                    previous,
                    electricityBill,
                    waterBill,
                    gasBill,
                    total,
                    paidAmount,
                    currentDue,
                    paymentsDateDatePicker.getValue()
            );

            BinaryFileManager.writeObject("RentPayments.bin",payment);
            Methods.Alert("Payment Successfully save.");

            tenantsNameTF.clear();
            TenantsID.clear();
            monthTF.clear();
            paymentsDateDatePicker.setValue(null);
            paymentsMethodComboBox.setValue(null);
            paymentsStatusComboBox.setValue(null);
            monthlyRentTF.clear();
            electricityBillTextField.clear();
            gasBillTextField.clear();
            waterBillTextField.clear();
            previousDue.clear();
            totalTextFiled.clear();
            paidAmountTextFiled.clear();
            currentDueTextField.clear();
            selectedTenant = null;
        }
        catch (NumberFormatException e){
            Methods.Alert("Please calculate the payment first.");
        }
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