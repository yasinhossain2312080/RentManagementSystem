package com.example.rentmanagementsystem;

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
import java.util.function.BinaryOperator;

public class HistoryViewController
{
    @javafx.fxml.FXML
    private TableView<RentPayments> paymentsHistoryTableView;

    @javafx.fxml.FXML
    private TableColumn<RentPayments, Double> rentTC;

    @javafx.fxml.FXML
    private TableColumn<RentPayments, String> nameTC;

    @javafx.fxml.FXML
    private TextField tenantsNameTF;

    @javafx.fxml.FXML
    private TableColumn<RentPayments, String> statusTC;

    @javafx.fxml.FXML
    private TableColumn<RentPayments, String> monthTC;

    @javafx.fxml.FXML
    private TableColumn<RentPayments, String> idTC;

    @javafx.fxml.FXML
    private TableColumn<RentPayments, Double> currentDueTC;

    @javafx.fxml.FXML
    private TextField idTF;

    private ArrayList<Integer> displayedPaymentIndexes = new ArrayList<>();

    @javafx.fxml.FXML
    private TableColumn<RentPayments, Double> totalTC;

    @javafx.fxml.FXML
    private TableColumn<RentPayments, Double> previousDueTC;
    @javafx.fxml.FXML
    public void initialize() {
        nameTC.setCellValueFactory(new PropertyValueFactory<RentPayments,String>("tenantName"));
        idTC.setCellValueFactory(new PropertyValueFactory<RentPayments,String>("TenantId"));
        monthTC.setCellValueFactory(new PropertyValueFactory<RentPayments,String>("month"));
        rentTC.setCellValueFactory(new PropertyValueFactory<RentPayments,Double>("monthlyRent"));
        totalTC.setCellValueFactory(new PropertyValueFactory<RentPayments,Double>("TotalAmount"));
        currentDueTC.setCellValueFactory(new PropertyValueFactory<RentPayments,Double>("CurrentDue"));
        previousDueTC.setCellValueFactory(new PropertyValueFactory<RentPayments,Double>("PreviousDue"));
        statusTC.setCellValueFactory(new PropertyValueFactory<RentPayments,String>("PaymentStatus"));

        tenantsNameTF.setOnAction(event -> {
            idTF.requestFocus();
        });

        tenantsNameTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                tenantsNameTF.requestFocus();
            }
        });

        idTF.setOnAction(event -> {
            try {
                searchButtonOnAction(
                        new ActionEvent(idTF, null)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        idTF.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                tenantsNameTF.requestFocus();
            }
        });
    }

    @javafx.fxml.FXML
    public void searchButtonOnAction(ActionEvent actionEvent) {
        String name = tenantsNameTF.getText().trim();
        String id = idTF.getText().trim();

        if (name.isEmpty() && id.isEmpty()) {
            Methods.Alert("Please enter NAME or ID.");
            return;
        }

        paymentsHistoryTableView.getItems().clear();
        displayedPaymentIndexes.clear();

        ArrayList<Object> objects =
                BinaryFileManager.ReadObjects("RentPayments.bin");

        boolean found = false;

        for (int i = 0; i < objects.size(); i++) {
            RentPayments payment = (RentPayments) objects.get(i);

            boolean match;

            if (!name.isEmpty() && !id.isEmpty()) {
                match = payment.getTenantName().equalsIgnoreCase(name)
                        && payment.getTenantId().equalsIgnoreCase(id);
            } else if (!name.isEmpty()) {
                match = payment.getTenantName().equalsIgnoreCase(name);
            } else {
                match = payment.getTenantId().equalsIgnoreCase(id);
            }

            if (match) {
                paymentsHistoryTableView.getItems().add(payment);
                displayedPaymentIndexes.add(i);
                found = true;
            }
        }

        if (!found) {
            Methods.Alert("PAYMENT HISTORY NOT FOUND!!");
        }

    }

    @javafx.fxml.FXML
    public void clearButtonOnAction(ActionEvent actionEvent) {
        tenantsNameTF.clear();
        idTF.clear();
        paymentsHistoryTableView.getItems().clear();
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

    @javafx.fxml.FXML
    public void deleteButtonOnAction(ActionEvent actionEvent) {
        int selectedIndex =
                paymentsHistoryTableView.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            Methods.Alert("Please select a Payment History to delete.");
            return;
        }

        javafx.scene.control.Alert alert =
                new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.CONFIRMATION
                );

        alert.setTitle("Delete Payment History");
        alert.setHeaderText("Are you sure you want to delete this payment?");
        alert.setContentText("This action cannot be undone.");

        java.util.Optional<javafx.scene.control.ButtonType> result =
                alert.showAndWait();

        if (result.isEmpty()
                || result.get() != javafx.scene.control.ButtonType.OK) {
            return;
        }

        if (selectedIndex >= displayedPaymentIndexes.size()) {
            Methods.Alert("Please search again and select the payment.");
            return;
        }

        int fileIndex = displayedPaymentIndexes.get(selectedIndex);

        ArrayList<Object> objects =
                BinaryFileManager.ReadObjects("RentPayments.bin");

        if (fileIndex < 0 || fileIndex >= objects.size()) {
            Methods.Alert("Payment History not found. Please search again.");
            return;
        }

        objects.remove(fileIndex);

        BinaryFileManager.writeAllObject("RentPayments.bin", objects);

        Methods.Alert("Selected Payment History deleted successfully.");

        paymentsHistoryTableView.getItems().clear();
        displayedPaymentIndexes.clear();
        searchButtonOnAction(actionEvent);
    }
}