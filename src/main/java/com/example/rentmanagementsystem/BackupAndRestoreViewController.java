package com.example.rentmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class BackupAndRestoreViewController
{
    @javafx.fxml.FXML
    private Label lastBackupLabel;
    @javafx.fxml.FXML
    private TextField googleAccountTextField;
    @javafx.fxml.FXML
    private Label backupStatusLabel;

    private void loadGoogleAccount() {

        File file = new File("googleAccount.txt");

        if (file.exists()) {

            try {

                FileInputStream fis =
                        new FileInputStream(file);

                byte[] data =
                        fis.readAllBytes();

                fis.close();

                String email =
                        new String(data);

                googleAccountTextField.setText(email);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    @javafx.fxml.FXML
    public void initialize() {

        loadGoogleAccount();

        lastBackupLabel.setText("Last Backup: " + loadLastBackupTime());

        backupStatusLabel.setText("Status: Ready");

    }

    private String loadLastBackupTime() {

        File file =
                new File("lastBackup.txt");

        if (!file.exists()) {

            return "No backup yet";
        }

        try {

            FileInputStream fis =
                    new FileInputStream(file);

            byte[] data =
                    fis.readAllBytes();

            fis.close();

            return new String(data);

        } catch (Exception e) {

            return "Unknown";
        }
    }

    @javafx.fxml.FXML
    public void restoreButtonOnAction(ActionEvent actionEvent) {
        try {

            // Google Drive থেকে latest backup ZIP download
            File zipFile =
                    GoogleDriveService.downloadLatestBackup();


            // ZIP file open
            FileInputStream fis =
                    new FileInputStream(zipFile);

                ZipInputStream zis =
                    new ZipInputStream(fis);


            ZipEntry zipEntry;


            // ZIP-এর প্রতিটি file বের করা
            while ((zipEntry = zis.getNextEntry()) != null) {

                if (zipEntry.getName().endsWith(".bin")) {

                    File restoredFile =
                            new File(zipEntry.getName());


                    FileOutputStream fos =
                            new FileOutputStream(restoredFile);


                    byte[] buffer =
                            new byte[1024];

                    int length;


                    while ((length = zis.read(buffer)) > 0) {

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


            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Restore Error");

            alert.setHeaderText(null);

            alert.setContentText(
                    "Restore failed."
            );

            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void connectGoogleAccountButtonOnAction(ActionEvent actionEvent) {
        try {

            String email =
                    GoogleDriveService.getGoogleAccountEmail();

            googleAccountTextField.setText(email);

            File file = new File("googleAccount.txt");

            FileOutputStream fos =
                    new FileOutputStream(file);

            fos.write(email.getBytes());

            fos.close();

            Alert alert =
                    new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Google Drive");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Google account connected successfully."
            );

            alert.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Google Drive");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Google account connection failed."
            );

            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void backupNowButtonOnAction(ActionEvent actionEvent) {
        File backupFolder = new File("Backup");

        if (!backupFolder.exists()) {
            backupFolder.mkdir();
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

        String dateTime =
                LocalDateTime.now().format(formatter);

        File zipFile = new File(
                backupFolder,
                "RentManagementBackup_" + dateTime + ".zip"
        );

        try {

            FileOutputStream fos =
                    new FileOutputStream(zipFile);

            ZipOutputStream zos = new ZipOutputStream(fos);

            File[] files = new File(".").listFiles();

            for (File file : files) {

                if (file.getName().endsWith(".bin")) {

                    FileInputStream fis =
                            new FileInputStream(file);

                    ZipEntry zipEntry =
                            new ZipEntry(file.getName());

                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];

                    int length;

                    while ((length = fis.read(buffer)) > 0) {

                        zos.write(buffer, 0, length);
                    }

                    fis.close();

                    zos.closeEntry();
                }
            }

            zos.close();
            fos.close();

            GoogleDriveService.uploadBackup(zipFile);

            FileOutputStream timeOutput =
                    new FileOutputStream("lastBackup.txt");

            timeOutput.write(
                    dateTime.getBytes()
            );

            timeOutput.close();


            lastBackupLabel.setText(
                    "Last Backup: " + dateTime
            );

            backupStatusLabel.setText(
                    "Status: Backup successful"
            );

            Alert alert =
                    new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Backup");

            alert.setHeaderText(null);

            alert.setContentText(
                    "Backup completed successfully."
            );

            alert.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Backup Error");

            alert.setHeaderText(null);

            alert.setContentText(
                    "Backup failed."
            );

            alert.showAndWait();
        }
    }



    @javafx.fxml.FXML
    public void backButtonOnAction(ActionEvent actionEvent)  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage nextStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        nextStage.setTitle("Settings!");
        nextStage.setScene(scene);
        nextStage.show();
    }
}