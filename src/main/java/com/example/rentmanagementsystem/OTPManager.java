package com.example.rentmanagementsystem;

import java.util.Random;

public class OTPManager {

    private static String generatedOTP;
    private static String userEmail;
    private static long otpGeneratedTime;

    public static String generateOTP(String email) {

        Random random = new Random();

        int otp = 100000 + random.nextInt(900000);

        generatedOTP = String.valueOf(otp);

        userEmail = email;

        otpGeneratedTime = System.currentTimeMillis();

        return generatedOTP;
    }

    public static boolean verifyOTP(String enteredOTP) {

        if (generatedOTP == null) {
            return false;
        }

        long currentTime = System.currentTimeMillis();

        long timePassed = currentTime - otpGeneratedTime;

        // 5 minutes = 5 × 60 × 1000 milliseconds
        if (timePassed > 5 * 60 * 1000) {

            clearOTP();

            return false;
        }

        return generatedOTP.equals(enteredOTP);
    }

    public static String getUserEmail() {

        return userEmail;
    }

    public static void clearOTP() {

        generatedOTP = null;
        userEmail = null;
        otpGeneratedTime = 0;
    }
}