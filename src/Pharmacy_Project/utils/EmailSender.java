package Pharmacy_Project.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase utilitaria para el envío de correos electrónicos utilizando el servicio SMTP de Gmail.
 */

public class EmailSender {

    /**
     * Envía un correo electrónico de bienvenida a un nuevo cliente registrado en la farmacia.
     *
     * @param recipientEmail Dirección de correo electrónico del destinatario.
     * @param customerName   Nombre del cliente.
     */

    public static void sendEmail(String recipientEmail, String customerName) {
        final String senderEmail = "pharmacy1503@gmail.com"; //correo de Gmail
        final String appPassword = "hxdn snye vvkg sayh"; // contraseña de aplicación de Gmail

        // Configuración de propiedades para la conexión SMTP

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Autenticación con el servidor SMTP

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        try {
            // Creación del mensaje de correo electrónico

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Welcome to PharmaPlus");
            //message.setText("Hello " + customerName + ",\n\nYour pharmacy registration has been successful, thank you for joining!\n\n\nRegards,\nML Pharmacy");

            // Cuerpo del correo en formato HTML

            MimeBodyPart textPart = new MimeBodyPart();
            // 🖼️ Mensaje en formato HTML con una imagen de internet
            String htmlMessage = "<h1>Hello " + customerName + ",</h1>"
                    + "<p>Your pharmacy registration has been successful, thank you for joining!</p>"
                    + "<img src='cid:logo' width='500' height='200'alt='Logo de la farmacia'>"
                    + "<p>Regards,<br>PharmaPlus</p>";
            textPart.setContent(htmlMessage, "text/html");

            // Adjuntar imagen al correo

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

            // Crear el cuerpo del mensaje con las partes de texto e imagen

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

            // Envío del correo electrónico

            Transport.send(message);
            System.out.println("Mail successfully sent to: " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error sending mail.");
        }
    }
}
