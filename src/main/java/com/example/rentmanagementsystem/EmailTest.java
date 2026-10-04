package com.example.rentmanagementsystem;

public class EmailTest {

    public static void main(String[] args) {

        String otp = "123456";

        EmailService.sendOTP(
                "yasinhossain3009@gmail.com",
                otp
        );

        System.out.println("Test completed.");
    }
}
