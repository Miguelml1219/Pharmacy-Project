package Pharmacy_Project.dao;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de acceso a datos (DAO) para la gestión de clientes en la base de datos.
 */
public class CustomersDAO {
    private ConexionDB conexionDB = new ConexionDB();

    /**
     * Obtiene la lista de todos los clientes registrados en la base de datos.
     *
     * @return Lista de objetos {@link Customers} que representan a los clientes.
     */
    public List<Customers> obtenerCliente() {
        List<Customers> clientes = new ArrayList<>();
        String query = "SELECT * FROM clientes";

        try (Connection con = conexionDB.getconnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Customers cliente = new Customers(rs.getInt("id_cliente"),
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
     * @param customers Objeto {@link Customers} que contiene los datos del cliente a agregar.
     * @return {@code true} si el cliente se agregó con éxito, {@code false} en caso contrario.
     */
    public boolean agregarCliente(Customers customers) {
        String query = "INSERT INTO clientes (cedula, nombre_completo, telefono, correo_electronico, direccion, categoria) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = conexionDB.getconnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, customers.getCedula());
            pst.setString(2, customers.getNombre_completo());
            pst.setString(3, customers.getTelefono());
            pst.setString(4, customers.getCorreo_electronico());
            pst.setString(5, customers.getDireccion());
            pst.setString(6, customers.getCategoria());

            int resultado = pst.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un cliente de la base de datos según su ID.
     *
     * @param id Identificador único del cliente a eliminar.
     * @return {@code true} si el cliente fue eliminado correctamente, {@code false} si no se encontró el cliente o hubo un error.
     */
    public boolean eliminarCliente(int id) {
        String query = "DELETE FROM clientes WHERE id_cliente = ?";
        try (Connection con = conexionDB.getconnection();
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
