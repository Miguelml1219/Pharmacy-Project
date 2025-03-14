package DAO;

import Conexion.ConexionDB;
import Modelo.Clientes;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientesDAO {
    private ConexionDB conexionDB = new ConexionDB();

    public List<Clientes> obtenerCliente() {
        List<Clientes> clientes = new ArrayList<>();
        String query = "SELECT * FROM clientes";

        try (Connection con = conexionDB.getconnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Clientes cliente = new Clientes(rs.getInt("id_cliente"),
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


    public boolean agregarCliente(Clientes clientes) {
        String query = "INSERT INTO clientes (cedula, nombre_completo, telefono, correo_electronico, direccion, categoria) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = conexionDB.getconnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, clientes.getCedula());
            pst.setString(2, clientes.getNombre_completo());
            pst.setString(3, clientes.getTelefono());
            pst.setString(4, clientes.getCorreo_electronico());
            pst.setString(5,clientes.getDireccion());
            pst.setString(6,clientes.getCategoria());

            int resultado = pst.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCliente(int id) {
        String query = "DELETE FROM clientes WHERE id_cliente = ?";
        try (Connection con = conexionDB.getconnection();
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
