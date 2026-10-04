package com.example.rentmanagementsystem;

import com.google.api.client.googleapis.mtls.MtlsProvider;

import java.io.*;
import java.util.ArrayList;

public class BinaryFileManager {
    public static void writeObject(String fileName, Object object) {

        try {

            File file = new File(fileName);

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


    public static ArrayList<Object>ReadObjects(String fileName){
        ArrayList<Object> objects = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists() || file.length() ==0){
            return objects;
        }
        try{

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                try{
                    Object object = ois.readObject();
                    objects.add(object);
                } catch (EOFException e) {
                    break;
                }
            }
            ois.close();
            fis.close();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
    }
        return objects;
    }

    public static void writeAllObject(String fileName , ArrayList<Object>objects){
        try{
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(Object object : objects){
                oos.writeObject(object);
            }
            oos.close();
            fos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static boolean userIDExists(String fileName , String userID){
        ArrayList<Object>objects = ReadObjects(fileName);
        for(Object object : objects){
            if(object instanceof User){
                User user = (User) object;
                if(user.getUserID().equals(userID)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean userEmailOrPhoneNumber(String fileName , String email , String phoneNumber){
        ArrayList<Object>objects = ReadObjects(fileName);
        for(Object object : objects){
            if (object instanceof User){
                User user  = (User) object;
                if(user.getEmail().equals(email) ||
                user.getPhoneNumber().equals(phoneNumber)){
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

    public static User searchUserIDAndPassword(String fileName , String id , String password){
        ArrayList<Object>objects = ReadObjects(fileName);
        for(Object object :objects){
            if(object instanceof  User){
                User user = (User) object;
                if(user.getUserID().equals(id) &&
                user.getPassword().equals(password)){
                    return user;
                }
            }
        }
        return null;
    }
}
