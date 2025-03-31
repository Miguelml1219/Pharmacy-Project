package Pharmacy_Project.dao;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Customer;
import Pharmacy_Project.utils.EmailSender;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.io.IOException;
import java.util.Properties;


/**
 * Clase DAO para gestionar operaciones CRUD sobre la entidad Cliente en la base de datos.
 */

public class CustomerDAO {

    private ConnectionDB connectionDB = new ConnectionDB();

    /**
     * Agrega un nuevo cliente a la base de datos.
     * @param customer Objeto Customer con la información del cliente.
     */
    public void add(Customer customer)
    {
        Connection con = connectionDB.getConnection();
        String query = "INSERT INTO clientes(cedula, nombre_completo, telefono, correo_electronico, direccion, categoria) VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, customer.getCedula());
            pst.setString(2, customer.getNombre_completo());
            pst.setString(3, customer.getTelefono());
            pst.setString(4, customer.getCorreo_electronico());
            pst.setString(5, customer.getDireccion());
            pst.setString(6, customer.getCategoria());

            int result = pst.executeUpdate();

            if (result > 0){
                JOptionPane.showMessageDialog(null, "Agregado exitosamente");
                EmailSender.sendEmail(customer.getCorreo_electronico(), customer.getNombre_completo());

            }else{
                JOptionPane.showMessageDialog(null,"Dont added");
            }

        }

        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Dont added");

        }
    }

    /**
     * Actualiza la información de un cliente existente en la base de datos.
     * @param customer Objeto Customer con los nuevos datos del cliente.
     */

    public void update(Customer customer){
        Connection con = connectionDB.getConnection();

        String query = "UPDATE clientes SET cedula = ?, nombre_completo = ?, telefono = ?, correo_electronico = ?, direccion = ?, categoria = ? WHERE id_cliente = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,customer.getCedula());
            stmt.setString(2,customer.getNombre_completo());
            stmt.setString(3,customer.getTelefono());
            stmt.setString(4,customer.getCorreo_electronico());
            stmt.setString(5,customer.getDireccion());
            stmt.setString(6,customer.getCategoria());
            stmt.setInt(7,customer.getId_cliente());


            int result = stmt.executeUpdate();

            if (result>0)
                JOptionPane.showMessageDialog(null,"Cliente actualizado exitosamente");
            else
                JOptionPane.showMessageDialog(null,"Customer not found");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un cliente de la base de datos según su ID.
     * @param id ID del cliente a eliminar.
     */

    public void delete(int id)
    {
        Connection con = connectionDB.getConnection();
        String query = "DELETE FROM clientes WHERE id_cliente = ?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);

            int result = pst.executeUpdate();

            if (result>0)
                JOptionPane.showMessageDialog(null,"Eliminado exitosamente");
            else
                JOptionPane.showMessageDialog(null,"Dont delete");

        }

        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Dont delete");

        }
    }

    /**
     * Envía una factura en formato PDF al correo del cliente.
     * @param filePath Ruta del archivo PDF de la factura.
     * @param customerName Nombre del cliente.
     * @param emailCliente Correo electrónico del cliente.
     */

    public void enviarFacturaPorCorreo(String filePath, String customerName, String emailCliente) {
        String remitente = "pharmacy1503@gmail.com";
        String clave = "hxdn snye vvkg sayh";  // Usa una clave de aplicación de Gmail
        String asunto = "Factura de tu compra";
        //String cuerpo = "Attached you will find the invoice of your purchase, thank you for choosing us!";

        String cuerpo = "<div style='font-family: Arial, sans-serif; color: #333; padding: 20px;'>"
                + "<h1>Hola " + customerName + ",</h1>"
                + "<p>Gracias por tu compra en <b>PharmaPlus</b>. Adjuntamos su factura.</p>"
                + "<p>Agradecemos su preferencia y esperamos poder servirle de nuevo!</p>"
                + "<br>"
                + "<img src='cid:logo' width='500' alt='Pharmacy Logo' style='border-radius: 10px;'>"
                + "<p>Puedes acceder a nuestra plataforma aquí: <a href='https://pharmaplus1503.netlify.app/' target='_blank'>PharmaPlus</a></p>"
                + "<p>Saludos cordiales,<br><b>Equipo PharmaPlus</b></p>";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailCliente));
            message.setSubject(asunto);

            // 🔹 Parte del cuerpo en HTML
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(cuerpo, "text/html");

            MimeBodyPart imagePart = new MimeBodyPart();
            String imagePath = "C:/Users/crist/IdeaProjects/Pharmacy-Project/src/Pharmacy_Project/utils/logo.png";
            imagePart.attachFile(imagePath);
            imagePart.setContentID("<logo>");
            imagePart.setDisposition(MimeBodyPart.INLINE);


            // Adjuntar archivo PDF
            MimeBodyPart attachPart = new MimeBodyPart();
            attachPart.attachFile(new File("Factura.pdf"));


            // 🔹 Agrupar todas las partes en un solo mensaje
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(imagePart);
            multipart.addBodyPart(attachPart);

            message.setContent(multipart);

            Transport.send(message);
            JOptionPane.showMessageDialog(null, "Factura enviada a: " + emailCliente);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error when sending the invoice.");
        }
    }

    /**
     * Obtiene el correo electrónico de un cliente según su selección.
     * @param clienteSeleccionado Nombre del cliente en formato "ID - Nombre".
     * @return Correo electrónico del cliente.
     */

    public String obtenerCorreo(String clienteSeleccionado) {
        String email = null;

        // Extraer el ID del cliente desde el formato "5 - Manuel"
        String[] partes = clienteSeleccionado.split(" - ");
        int idCliente = Integer.parseInt(partes[0]);

        String sql = "SELECT correo_electronico FROM clientes WHERE id_cliente = ?";

        try (Connection conn = connectionDB.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idCliente);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                email = rs.getString("correo_electronico");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return email;
    }



}
