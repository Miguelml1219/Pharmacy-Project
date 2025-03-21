package Pharmacy_Project.dao;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Customer;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para la gestión de clientes en la base de datos.
 */
public class CustomerDAO {
    private ConnectionDB connectionDB = new ConnectionDB();

    /**
     * Obtiene la lista de clientes desde la base de datos.
     *
     * @return una lista de objetos {@link Customer} con los clientes registrados.
     */
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

    /**
     * Agrega un nuevo cliente a la base de datos.
     *
     * @param customer el objeto {@link Customer} que representa el cliente a agregar.
     * @return true si el cliente fue agregado exitosamente, false en caso contrario.
     */
    public boolean agregarCliente(Customer customer) {
        String query = "INSERT INTO clientes (cedula, nombre_completo, telefono, correo_electronico, direccion, categoria) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = connectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, customer.getCedula());
            pst.setString(2, customer.getNombre_completo());
            pst.setString(3, customer.getTelefono());
            pst.setString(4, customer.getCorreo_electronico());
            pst.setString(5, customer.getDireccion());
            pst.setString(6, customer.getCategoria());

            int resultado = pst.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un cliente de la base de datos por su ID.
     *
     * @param id el identificador del cliente a eliminar.
     * @return true si el cliente fue eliminado exitosamente, false en caso contrario.
     */
    public boolean eliminarCliente(int id) {
        String query = "DELETE FROM clientes WHERE id_cliente = ?";
        try (Connection con = connectionDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id);
            int files = stmt.executeUpdate();
            if (files > 0) {
                JOptionPane.showMessageDialog(null, "Registration successfully deleted!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "The record was not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
