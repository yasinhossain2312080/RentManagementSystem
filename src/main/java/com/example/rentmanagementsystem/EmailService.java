package com.example.rentmanagementsystem;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailService {
    public static void sendOTP(String receiverEmail, String otp) {
        Properties config = new Properties();
        try {

            var inputStream =
                    EmailService.class.getResourceAsStream("/emailConfig.properties");

            if (inputStream == null) {
                System.out.println("emailConfig.properties not found.");
                return;
            }
            config.load(inputStream);

            inputStream.close();

        } catch (IOException e) {

            e.printStackTrace();
            return;
        }

        String senderEmail = config.getProperty("email");
        String appPassword = config.getProperty("appPassword");

        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication(
                                senderEmail,
                                appPassword
                        );
                    }
                }
        );

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
            message.setSubject("Rent Management System - Password Reset OTP");
            message.setText(
                    "Hello Sir/ Ma'am,\n\n"
                            + "Your password reset OTP is: " + otp + "\n\n"
                            + "This OTP is valid for 5 minutes.\n\n"
                            + "If you did not request a password reset, please ignore this email.\n\n"
                            + "Regards,\n"
                            + "Rent Management System"
            );
            Transport.send(message);
            System.out.println("OTP sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
