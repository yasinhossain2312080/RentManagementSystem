package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class BackupAndRestoreViewController {

    @FXML
    private Label lastBackupLabel;
    @FXML
    private Label backupStatusLabel;
    @FXML
    private ComboBox<String> googleAccountComboBox;

    private File getUserFolder() {

        String appData = System.getenv("APPDATA");
        String userID = User.currentUser.getUserID();
        File userFolder = new File(appData
                                + File.separator
                                + "RentManagementSystem"
                                + File.separator
                                + "UserData"
                                + File.separator
                                + userID
                );

        if (!userFolder.exists()) {
            userFolder.mkdirs();
        }
        return userFolder;
    }

    // Current user's Google account file
    private File getGoogleAccountFile() {

        return new File(getUserFolder(), "googleAccount.txt");
    }

    // Current user's last backup file
    private File getLastBackupFile() {
        return new File(getUserFolder(), "lastBackup.txt");
    }

    // Current user's Backup folder
    private File getBackupFolder() {
        File backupFolder = new File(getUserFolder(), "Backup");
        if (!backupFolder.exists()) {
            backupFolder.mkdirs();
        }
        return backupFolder;
    }


    // Current user's Restore folder
    private File getRestoreFolder() {

        File restoreFolder = new File(getUserFolder(), "Restore");
        if (!restoreFolder.exists()) {
            restoreFolder.mkdirs();
        }

        return restoreFolder;
    }

    private void loadGoogleAccount() {
        File file = getGoogleAccountFile();
        if (file.exists()) {
            try {

                FileInputStream fis = new FileInputStream(file);byte[] data = fis.readAllBytes();
                fis.close();
                String email = new String(data);
                googleAccountComboBox.getItems().clear();
                googleAccountComboBox.getItems().add(email);
                googleAccountComboBox.setValue(email);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void initialize() {
        loadGoogleAccount();
        lastBackupLabel.setText("Last Backup: " + loadLastBackupTime());
        backupStatusLabel.setText("Status: Ready");
    }
    private String loadLastBackupTime() {

        File file = getLastBackupFile();
        if (!file.exists()) {
            return "No backup yet";}

        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = fis.readAllBytes();
            fis.close();
            return new String(data);
        } catch (Exception e) {

            return "Unknown";
        }
    }

    @FXML
    public void restoreButtonOnAction(
            ActionEvent actionEvent) {

        try {
            // Download backup from current user's Google Drive

            File zipFile = GoogleDriveService.downloadLatestBackup();
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry zipEntry;
            // Restore into current user's folder
            File dataFolder = getUserFolder();

            while (
                    (zipEntry = zis.getNextEntry())
                            != null
            ) {

                if (
                        zipEntry.getName()
                                .endsWith(".bin")
                ) {
                    File restoredFile = new File(dataFolder, zipEntry.getName());
                    FileOutputStream fos = new FileOutputStream(restoredFile);
                    byte[] buffer = new byte[1024];

                    int length;
                    while ((length = zis.read(buffer)) > 0
                    ) {

                        fos.write(
                                buffer,
                                0,
                                length
                        );
                    }

                    fos.close();
                    zis.closeEntry();
                }
            }

            zis.close();
            fis.close();
            backupStatusLabel.setText("Status: Restore successful");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Restore");
            alert.setHeaderText(null);
            alert.setContentText("Restore completed successfully.");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Restore Error");
            alert.setHeaderText(null);
            alert.setContentText("Restore failed.");
            alert.showAndWait();
        }
    }

    @FXML
    public void connectGoogleAccountButtonOnAction(ActionEvent actionEvent) {
        try {
            String email = GoogleDriveService.getGoogleAccountEmail();
            googleAccountComboBox.getItems().clear();
            googleAccountComboBox.getItems().add(email);
            googleAccountComboBox.setValue(email);
            File file = getGoogleAccountFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(email.getBytes());

            fos.close();
            Methods.Alert("Google Account Connected Successfully.");
            return;

        } catch (Exception e) {
            e.printStackTrace();
            Methods.Alert("Google account connection failed.");
            return;
        }
    }

    @FXML
    public void backupNowButtonOnAction(
            ActionEvent actionEvent) {
        File backupFolder = getBackupFolder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String dateTime = LocalDateTime.now().format(formatter);

        File zipFile = new File(backupFolder, "RentManagementBackup_" + dateTime + ".zip");


        try {

            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);

            // Backup only the current user's files
            File dataFolder = getUserFolder();
            File[] files = dataFolder.listFiles();

            if (files != null) {
                for (File file : files) {
                    // Only backup .bin files
                    if (
                            file.getName()
                                    .endsWith(".bin")
                    ) {

                        FileInputStream fis =
                                new FileInputStream(
                                        file
                                );

                        ZipEntry zipEntry = new ZipEntry(file.getName());
                        zos.putNextEntry(zipEntry);
                        byte[] buffer = new byte[1024];
                        int length;
                        while (
                                (length =
                                        fis.read(buffer))
                                        > 0
                        ) {

                            zos.write(buffer, 0, length
                            );
                        }

                        fis.close();
                        zos.closeEntry();
                    }
                }
            }

            zos.close();
            fos.close();

            // Upload current user's backup
            GoogleDriveService.uploadBackup(zipFile);

            FileOutputStream timeOutput = new FileOutputStream(getLastBackupFile());
            timeOutput.write(dateTime.getBytes());
            timeOutput.close();
            lastBackupLabel.setText("Last Backup: " + dateTime);
            backupStatusLabel.setText("Status: Backup successful");
            Methods.Alert("Backup completed successfully.");
            return;

        } catch (Exception e) {
            e.printStackTrace();
            Methods.Alert("Backup failed.");
            return;
        }
    }

    @FXML
    public void backButtonOnAction(
            ActionEvent actionEvent)
            throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Settings!");
        nextStage.setScene(scene);
        nextStage.show();
    }
}