package Pharmacy_Project.dao;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Order_Detail;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase DAO para la gestión de detalles de pedidos en la base de datos.
 */
public class Order_DetailDAO {

    private ConnectionDB connectionDB = new ConnectionDB();


    /**
     * Agrega un nuevo detalle de pedido a la base de datos.
     * @param order_detail Objeto Order_Detail con los datos a insertar.
     */
    public void add(Order_Detail order_detail)
    {
        Connection con = connectionDB.getConnection();


        String query = "INSERT INTO detalle_pedido (id_pedido, id_producto, und_medida, cantidad, precio_unitario, subtotal) VALUES (?,?,?,?,?,?)";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,order_detail.getId_pedido());
            pst.setInt(2,order_detail.getId_producto());
            pst.setString(3,order_detail.getUnd_medida());
            pst.setInt(4,order_detail.getCantidad());
            pst.setInt(5,order_detail.getPrecio_unitario());
            pst.setInt(6,order_detail.getSubtotal());

            int result = pst.executeUpdate();

            if (result>0)
                JOptionPane.showMessageDialog(null,"Agregada exitosamente");
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
     * Actualiza un detalle de pedido existente en la base de datos.
     * @param order_detail Objeto Order_Detail con los datos actualizados.
     */


    public void update(Order_Detail order_detail){
        Connection con = connectionDB.getConnection();

        String query = "UPDATE detalle_pedido SET id_producto = ?, und_medida = ?, cantidad = ?, precio_unitario = ? , subtotal = ? WHERE id_detalle = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1,order_detail.getId_producto());
            stmt.setString(2,order_detail.getUnd_medida());
            stmt.setInt(3,order_detail.getCantidad());
            stmt.setInt(4,order_detail.getPrecio_unitario());
            stmt.setInt(5,order_detail.getSubtotal());
            stmt.setInt(6,order_detail.getId_detalle());


            int result = stmt.executeUpdate();

            if (result>0)
                JOptionPane.showMessageDialog(null,"Orden actualizada exitosamente");
            else
                JOptionPane.showMessageDialog(null,"Order not found");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un detalle de pedido de la base de datos.
     * @param id ID del detalle de pedido a eliminar.
     */

    public void delete(int id)
    {
        Connection con = connectionDB.getConnection();
        String query = "DELETE FROM detalle_pedido WHERE id_detalle = ?";

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
     * Obtiene el ID máximo de los pedidos registrados en la base de datos.
     * @return ID del último pedido registrado.
     */

    public int obtenerID() {
        int id = 0;
        Connection con = connectionDB.getConnection();
        String query = "SELECT MAX(id_pedido) FROM pedidos ";

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

    /**
     * Obtiene el precio de un producto en la base de datos.
     * @param idProducto ID del producto.
     * @return Precio del producto.
     */

    public int obtainPriceProduct(int nameProduct) {
        int precio = 0;
        Connection con = connectionDB.getConnection();
        String query = "SELECT precio FROM productos WHERE id_producto = ?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, nameProduct);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                precio = rs.getInt("precio");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving product price");
        }

        return precio;
    }

    /**
     * Actualiza el stock de un producto después de una venta.
     * @param idProducto ID del producto.
     * @param presentation Presentación del producto (Unit, Blister, Box).
     * @param amount Cantidad vendida.
     */

    public void actualizarStock(int idProducto, String presentation, int amount) {
        int unit = 1; // Por defecto para Unit

        if (presentation.equals("Blister")) {
            unit = 10;
        } else if (presentation.equals("Caja")) {
            unit = 20;
        }

        int amountT = amount * unit;

        Connection connection = connectionDB.getConnection();

        try {

            String checkQuery = "SELECT nombre, stock_actual, stock_minimo FROM productos WHERE id_producto = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setInt(1, idProducto);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String nameProduct = rs.getString("nombre");
                int currentStock = rs.getInt("stock_actual");
                int stockMinimo = rs.getInt("stock_minimo");
                if (currentStock >= amountT) {

                    String updateQuery = "UPDATE productos SET stock_actual = stock_actual - ? WHERE id_producto = ?";
                    PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                    updateStmt.setInt(1, amountT);
                    updateStmt.setInt(2, idProducto);
                    updateStmt.executeUpdate();

                    int nuevoStock = currentStock - amountT;
                    JOptionPane.showMessageDialog(null, "Stock actualizado correctamente. " + amountT+ " unidades fueron descontadas ");

                    // Mostrar alerta si el stock está en su mínimo o menor
                    if (nuevoStock <= stockMinimo) {
                        JOptionPane.showMessageDialog(null,
                                "Bajo Stock para: " + nameProduct + " - Reabastecimiento requerido!",
                                "Stock Alert", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No hay suficiente stock. Stock Actual: " + currentStock + ", Requerido: " + amountT);
                }

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar stock: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Verifica si hay suficiente stock disponible para un producto.
     * @param idProducto ID del producto.
     * @param cantidadSolicitada Cantidad requerida.
     * @return true si hay stock suficiente, false en caso contrario.
     */

    public boolean verificarStockDisponible(int idProducto, int cantidadSolicitada) {
        ConnectionDB connectionDB = new ConnectionDB();
        Connection con = connectionDB.getConnection();
        String query = "SELECT stock_actual FROM productos WHERE id_producto = ?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, idProducto);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int stockActual = rs.getInt("stock_actual");

                if (stockActual < cantidadSolicitada) {
                    return false; // No hay suficiente stock
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true; // Hay suficiente stock
    }
}
