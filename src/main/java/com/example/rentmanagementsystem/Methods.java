package com.example.rentmanagementsystem;

import javafx.scene.control.Alert;

public class Methods {

    public static void Alert(String alertText) {
        Alert myAlert = new Alert(Alert.AlertType.INFORMATION);
        myAlert.setContentText(alertText);
        myAlert.showAndWait();
        return;
    }
}
