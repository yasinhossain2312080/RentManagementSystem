package com.example.rentmanagementsystem;

import java.io.*;
import java.util.ArrayList;

public class BinaryFileManager {

    private static final String DATA_FOLDER =
            System.getenv("APPDATA")
                    + File.separator
                    + "RentManagementSystem";

    private static File getFile(String fileName) {
        if (fileName.equals("Users.bin")) {
            File folder = new File(DATA_FOLDER);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            return new File(folder, fileName);
        }

        String userID = User.currentUser.getUserID();
        File userFolder = new File(
                DATA_FOLDER
                        + File.separator
                        + "UserData"
                        + File.separator
                        + userID
        );
        if (!userFolder.exists()) {
            userFolder.mkdirs();
        }
        return new File(userFolder, fileName);
    }

    public static void writeObject(String fileName, Object object) {

        try {

            File file = getFile(fileName);
            if (file.exists() && file.length() > 0) {
                FileOutputStream fos = new FileOutputStream(file, true);
                ObjectOutputStream oos = new appendableObjectOutputStream(fos);
                oos.writeObject(object);
                oos.close();
                fos.close();

            } else {

                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(object);
                oos.close();
                fos.close();
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    public static ArrayList<Object> ReadObjects(String fileName) {
        ArrayList<Object> objects = new ArrayList<>();
        File file = getFile(fileName);
        if (!file.exists() || file.length() == 0) {
            return objects;
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                try {

                    Object object = ois.readObject();
                    objects.add(object);
                } catch (EOFException e) {
                    break;
                }
            }
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
        }
        return objects;
    }

    public static void writeAllObject(
            String fileName,
            ArrayList<Object> objects) {
        try {

            File file = getFile(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Object object : objects) {
                oos.writeObject(object);
            }

            oos.close();
            fos.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    public static boolean userIDExists(
            String fileName,
            String userID) {
        ArrayList<Object> objects = ReadObjects(fileName);
        for (Object object : objects) {
            if (object instanceof User) {
                User user = (User) object;
                if (user.getUserID().equals(userID)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean userEmailOrPhoneNumber(
            String fileName,
            String email,
            String phoneNumber) {
        ArrayList<Object> objects = ReadObjects(fileName);
        for (Object object : objects) {
            if (object instanceof User) {
                User user = (User) object;
                if (user.getEmail().equals(email) || user.getPhoneNumber().equals(phoneNumber)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static User searchUserByEmailAndPhone(
            String fileName,
            String email,
            String phoneNumber) {
        ArrayList<Object> objects = ReadObjects(fileName);
        for (Object object : objects) {
            if (object instanceof User) {
                User user = (User) object;
                if (user.getEmail().trim().equalsIgnoreCase(email.trim())
                        && user.getPhoneNumber().trim().equals(phoneNumber.trim())) {
                    return user;
                }
            }
        }

        return null;
    }


    public static User searchUserIDAndPassword(
            String fileName,
            String id,
            String password) {
        ArrayList<Object> objects = ReadObjects(fileName);
        for (Object object : objects) {
            if (object instanceof User) {
                User user = (User) object;
                if (user.getUserID().equals(id)
                        && user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }
}