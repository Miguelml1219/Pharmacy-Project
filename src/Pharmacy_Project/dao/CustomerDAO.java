package Pharmacy_Project.dao;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Customer;
import Pharmacy_Project.utils.EmailSender;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO {

    private ConnectionDB connectionDB = new ConnectionDB();


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
                JOptionPane.showMessageDialog(null, "Succesfully added");
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
                JOptionPane.showMessageDialog(null,"Update customer successfully");
            else
                JOptionPane.showMessageDialog(null,"Customer not found");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


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
                JOptionPane.showMessageDialog(null,"Succesfully delete");
            else
                JOptionPane.showMessageDialog(null,"Dont delete");

        }

        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Dont delete");

        }
    }

}
