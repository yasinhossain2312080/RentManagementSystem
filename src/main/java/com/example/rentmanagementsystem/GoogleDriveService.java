package com.example.rentmanagementsystem;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class GoogleDriveService {

    private static final String APPLICATION_NAME =
            "Rent Management System";

    private static final JsonFactory JSON_FACTORY =
            GsonFactory.getDefaultInstance();

    private static final List<String> SCOPES =
            Collections.singletonList(DriveScopes.DRIVE_FILE);


    private static File getUserFolder() {

        String appData =
                System.getenv("APPDATA");

        String userID =
                User.currentUser.getUserID();

        File userFolder =
                new File(
                        appData
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


    private static File getTokenFolder() {

        File tokenFolder =
                new File(
                        getUserFolder(),
                        "tokens"
                );

        if (!tokenFolder.exists()) {

            tokenFolder.mkdirs();
        }

        return tokenFolder;
    }


    private static Credential getCredentials(
            final NetHttpTransport HTTP_TRANSPORT)
            throws Exception {

        InputStream inputStream =
                GoogleDriveService.class
                        .getResourceAsStream(
                                "/credentials.json"
                        );


        if (inputStream == null) {

            throw new Exception(
                    "credentials.json not found."
            );
        }


        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(
                        JSON_FACTORY,
                        new InputStreamReader(inputStream)
                );


        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT,
                        JSON_FACTORY,
                        clientSecrets,
                        SCOPES
                )
                        .setDataStoreFactory(
                                new FileDataStoreFactory(
                                        getTokenFolder()
                                )
                        )
                        .setAccessType("offline")
                        .build();


        LocalServerReceiver receiver =
                new LocalServerReceiver.Builder()
                        .setPort(8888)
                        .build();


        return new AuthorizationCodeInstalledApp(
                flow,
                receiver
        ).authorize("user");
    }


    public static Drive getDriveService()
            throws Exception {

        final NetHttpTransport HTTP_TRANSPORT =
                GoogleNetHttpTransport
                        .newTrustedTransport();


        Credential credential =
                getCredentials(HTTP_TRANSPORT);


        return new Drive.Builder(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                credential
        )
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


    public static String getGoogleAccountEmail()
            throws Exception {

        Drive driveService =
                getDriveService();


        Drive.About.Get request =
                driveService.about().get();


        request.setFields(
                "user(emailAddress)"
        );


        return request.execute()
                .getUser()
                .getEmailAddress();
    }


    public static void uploadBackup(File zipFile)
            throws Exception {

        Drive driveService =
                getDriveService();


        com.google.api.services.drive.model.File fileMetadata =
                new com.google.api.services.drive.model.File();


        fileMetadata.setName(
                zipFile.getName()
        );


        com.google.api.client.http.FileContent mediaContent =
                new com.google.api.client.http.FileContent(
                        "application/zip",
                        zipFile
                );


        driveService.files()
                .create(
                        fileMetadata,
                        mediaContent
                )
                .setFields("id, name")
                .execute();
    }


    public static File downloadLatestBackup()
            throws Exception {

        Drive driveService =
                getDriveService();


        List<com.google.api.services.drive.model.File> files =
                driveService.files()
                        .list()
                        .setQ(
                                "name contains " +
                                        "'RentManagementBackup_' " +
                                        "and trashed = false"
                        )
                        .setOrderBy(
                                "createdTime desc"
                        )
                        .setPageSize(1)
                        .setFields(
                                "files(id, name)"
                        )
                        .execute()
                        .getFiles();


        if (files.isEmpty()) {

            throw new Exception(
                    "No backup file found in Google Drive."
            );
        }


        com.google.api.services.drive.model.File backupFile =
                files.get(0);


        File restoreFolder =
                new File(
                        getUserFolder(),
                        "Restore"
                );


        if (!restoreFolder.exists()) {

            restoreFolder.mkdirs();
        }


        File downloadedFile =
                new File(
                        restoreFolder,
                        backupFile.getName()
                );


        FileOutputStream outputStream =
                new FileOutputStream(
                        downloadedFile
                );


        driveService.files()
                .get(backupFile.getId())
                .executeMediaAndDownloadTo(
                        outputStream
                );


        outputStream.close();


        return downloadedFile;
    }
}