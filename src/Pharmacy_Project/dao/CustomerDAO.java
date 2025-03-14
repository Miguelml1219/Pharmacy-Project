package Pharmacy_Project.dao;



import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Customer;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private ConnectionDB connectionDB = new ConnectionDB();

    public List<Customer> obtenerCliente() {
        List<Customer> clientes = new ArrayList<>();
        String query = "SELECT * FROM clientes";

        try (Connection con = connectionDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Customer cliente = new Customer(rs.getInt("id_cliente"),
                        rs.getString("cedula"), rs.getString("nombre_completo"),
                        rs.getString("telefono"), rs.getString("correo_electronico"), rs.getString("direccion"),
                        rs.getString("categoria"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }


    public boolean agregarCliente(Customer customer) {
        String query = "INSERT INTO clientes (cedula, nombre_completo, telefono, correo_electronico, direccion, categoria) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = connectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, customer.getCedula());
            pst.setString(2, customer.getNombre_completo());
            pst.setString(3, customer.getTelefono());
            pst.setString(4, customer.getCorreo_electronico());
            pst.setString(5,customer.getDireccion());
            pst.setString(6,customer.getCategoria());

            int resultado = pst.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCliente(int id) {
        String query = "DELETE FROM clientes WHERE id_cliente = ?";
        try (Connection con = connectionDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id);
            int files = stmt.executeUpdate();
            if (files > 0) {
                JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro el registro.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

