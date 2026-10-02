package com.example.rentmanagementsystem;

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

}
