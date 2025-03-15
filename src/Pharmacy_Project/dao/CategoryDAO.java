package Pharmacy_Project.dao;


import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Category;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private ConnectionDB connectionDB = new ConnectionDB();

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
