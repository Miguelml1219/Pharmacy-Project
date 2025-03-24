package Pharmacy_Project.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {
    public static void sendEmail(String recipientEmail, String customerName) {
        final String senderEmail = "pharmacy1503@gmail.com"; //correo de Gmail
        final String appPassword = "hxdn snye vvkg sayh"; // contraseña de aplicación de Gmail

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Welcome to PharmaPlus");
            //message.setText("Hello " + customerName + ",\n\nYour pharmacy registration has been successful, thank you for joining!\n\n\nRegards,\nML Pharmacy");

            MimeBodyPart textPart = new MimeBodyPart();
            // 🖼️ Mensaje en formato HTML con una imagen de internet
            String htmlMessage = "<h1>Hello " + customerName + ",</h1>"
                    + "<p>Your pharmacy registration has been successful, thank you for joining!</p>"
                    + "<img src='cid:logo' width='500' height='200'alt='Logo de la farmacia'>"
                    + "<p>Regards,<br>PharmaPlus</p>";
            textPart.setContent(htmlMessage, "text/html");

            MimeBodyPart imagePart = new MimeBodyPart();
            String imagePath = "C:/Users//crist/IdeaProjects/Pharmacy-Project/src/Pharmacy_Project/utils/logo.png"; // ⚠️ Cambia esta ruta

            try {
                imagePart.attachFile(new File(imagePath));
            } catch (IOException e) {
                System.out.println("Error al adjuntar la imagen: " + e.getMessage());
                return; // Sale del método si no puede adjuntar la imagen
            }

            imagePart.setContentID("<logo>"); // Identificador de la imagen
            imagePart.setDisposition(MimeBodyPart.INLINE);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);


            Transport.send(message);
            System.out.println("Mail successfully sent to: " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error sending mail.");
        }
    }
}
