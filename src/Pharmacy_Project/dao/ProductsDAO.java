package Pharmacy_Project.dao;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Customer;
import Pharmacy_Project.model.Products;

/**
 * Clase para manejar las operaciones CRUD de la tabla productos en la base de datos.
 */

public class ProductsDAO {

    private ConnectionDB connectionDB = new ConnectionDB();

    /**
     * Agrega un nuevo producto a la base de datos.
     * @param products Objeto Products con los datos del producto a agregar.
     */
    public void add(Products products){
        Connection con = connectionDB.getConnection();

        String query = "INSERT INTO productos (id_categoria, nombre, descripcion, precio, stock_actual, stock_minimo, fecha_vencimiento, lote) VALUES (?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,products.getId_categoria());
            stmt.setString(2,products.getNombre());
            stmt.setString(3,products.getDescripcion());
            stmt.setInt(4,products.getPrecio());
            stmt.setInt(5,products.getStock_actual());
            stmt.setInt(6,products.getStock_minimo());
            stmt.setDate(7,products.getFecha_vencimiento());
            stmt.setString(8,products.getLote());


            int result = stmt.executeUpdate();

            if (result>0)
                JOptionPane.showMessageDialog(null,"Agregado exitosamente");
            else
                JOptionPane.showMessageDialog(null,"Dont added");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Dont added");

        }
    }

    /**
     * Actualiza los datos de un producto en la base de datos.
     * @param products Objeto Products con los datos actualizados del producto.
     */


    public void update(Products products){
        Connection con = connectionDB.getConnection();

        String query = "UPDATE productos SET id_categoria = ?, nombre = ?, descripcion = ?, precio = ?, stock_actual = ?, stock_minimo = ?, fecha_vencimiento = ?, lote = ? WHERE id_producto = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,products.getId_categoria());
            stmt.setString(2,products.getNombre());
            stmt.setString(3,products.getDescripcion());
            stmt.setInt(4,products.getPrecio());
            stmt.setInt(5,products.getStock_actual());
            stmt.setInt(6,products.getStock_minimo());
            stmt.setDate(7,products.getFecha_vencimiento());
            stmt.setString(8,products.getLote());
            stmt.setInt(9,products.getId_producto());


            int result = stmt.executeUpdate();

            if (result>0)
                JOptionPane.showMessageDialog(null,"Producto actualizado exitosamente");
            else
                JOptionPane.showMessageDialog(null,"Product not found");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un producto de la base de datos.
     * @param id ID del producto a eliminar.
     */


    public void delete(int id)
    {
        Connection con = connectionDB.getConnection();
        String query = "DELETE FROM productos WHERE id_producto = ?";

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
     * Obtiene el ID máximo de la tabla categoria.
     * @return ID máximo encontrado en la tabla categoria.
     */

    public int obtenerID() {
        int id = 0;
        Connection con = connectionDB.getConnection();
        String query = "SELECT MAX(id_categoria) FROM categoria ";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }

        return id;
    }

}
