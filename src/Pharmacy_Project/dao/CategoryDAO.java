package Pharmacy_Project.dao;

import java.sql.*;

public class CategoryDAO {
    private Connection conn;

    public CategoryDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/drogueria", "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCategory(String id, String name) {
        try {
            String query = "INSERT INTO categorias (id, nombre) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(String id, String name) {
        try {
            String query = "UPDATE categorias SET nombre = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(String id) {
        try {
            String query = "DELETE FROM categorias WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
