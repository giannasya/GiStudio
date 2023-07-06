package com.example.studio.Controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.studio.Model.BookingModel;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {

    private static final String EMAIL_HOST = "smtp.gmail.com";
    private static final int EMAIL_PORT = 587;
    private static final String EMAIL_USERNAME = "praba.elmahdi@gmail.com";
    private static final String EMAIL_PASSWORD = "ivjptcfwgtbtyafl";

    public static void sendPasswordEmail(String recipientEmail, BookingModel bookingModel) {
        // Set email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", EMAIL_HOST);
        properties.put("mail.smtp.port", EMAIL_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a session with authentication
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            // Create a message
            final Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Booking Details");
            message.setText("Thank you for trusting us and booked our studio, heres your receipt : "
                    + "\nUsername: " + bookingModel.getUsername() + "\nBookingDate: " + bookingModel.getTanggal()
                    + "\nBooked Room: " + bookingModel.getRoom());

            // Send the message on a separate thread using AsyncTask
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        Transport.send(message);
                        Log.d("EmailSender", "Email sent successfully!");
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        Log.e("EmailSender", "Failed to send email.");
                    }
                    return null;
                }
            }.execute();
        } catch (MessagingException e) {
            e.printStackTrace();
            Log.e("EmailSender", "Failed to create or send email.");
        }
    }
}
