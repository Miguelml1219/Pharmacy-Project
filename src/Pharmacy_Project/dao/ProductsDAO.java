package Pharmacy_Project.dao;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Products;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para la gestión de productos en la base de datos.
 */
public class ProductsDAO {
    private ConnectionDB connectionDB = new ConnectionDB();

    /**
     * Agrega un nuevo producto a la base de datos.
     *
     * @param products el objeto {@link Products} que representa el producto a agregar.
     * @return true si el producto fue agregado exitosamente, false en caso contrario.
     */
    public boolean agregarProductos(Products products) {
        String query = "INSERT INTO productos (id_categoria, nombre, descripcion, precio, stock_actual, stock_minimo, fecha_vencimiento, lote) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = connectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, products.getId_categoria());
            pst.setString(2, products.getNombre());
            pst.setString(3, products.getDescripcion());
            pst.setDouble(4, products.getPrecio());
            pst.setInt(5, products.getStock_actual());
            pst.setInt(6, products.getStock_minimo());
            pst.setDate(7, new java.sql.Date(products.getFecha_vencimiento().getTime()));
            pst.setString(8, products.getLote());

            int resultado = pst.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un producto de la base de datos por su ID.
     *
     * @param id el identificador del producto a eliminar.
     * @return true si el producto fue eliminado exitosamente, false en caso contrario.
     */
    public boolean eliminarProducto(int id) {
        String query = "DELETE FROM productos WHERE id_producto = ?";
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
