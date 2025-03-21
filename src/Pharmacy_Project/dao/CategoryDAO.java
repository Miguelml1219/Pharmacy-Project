package Pharmacy_Project.dao;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Category;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para la gestión de categorías en la base de datos.
 */
public class CategoryDAO {
    private ConnectionDB connectionDB = new ConnectionDB();

    /**
     * Obtiene la lista de categorías desde la base de datos.
     *
     * @return una lista de objetos {@link Category} con las categorías registradas.
     */
    public List<Category> obtenerCategoria() {
        List<Category> categoria = new ArrayList<>();
        String query = "SELECT * FROM categoria";

        try (Connection con = connectionDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Category categorias = new Category(rs.getInt("id_categoria"),
                        rs.getString("categoria"));
                categoria.add(categorias);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoria;
    }

    /**
     * Agrega una nueva categoría a la base de datos.
     *
     * @param categoria el objeto {@link Category} que representa la categoría a agregar.
     * @return true si la categoría fue agregada exitosamente, false en caso contrario.
     */
    public boolean agregarCategoria(Category categoria) {
        String query = "INSERT INTO categoria (nombre_categoria) VALUES (?)";

        try (Connection con = connectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, categoria.getCategoria());

            int resultado = pst.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una categoría de la base de datos por su ID.
     *
     * @param id el identificador de la categoría a eliminar.
     * @return true si la categoría fue eliminada exitosamente, false en caso contrario.
     */
    public boolean eliminarCategoria(int id) {
        String query = "DELETE FROM categoria WHERE id_categoria = ?";
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
