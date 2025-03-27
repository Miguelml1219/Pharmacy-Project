package Pharmacy_Project.reports;

import Pharmacy_Project.connection.ConnectionDB;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase ReportDAO que gestiona las consultas relacionadas con los reportes de ventas y clientes.
 */

public class  ReportDAO {
    private ConnectionDB connectionDB = new ConnectionDB();

    /**
     * Consulta las ventas diarias agrupadas por fecha.
     * @return ResultSet con la fecha y el total de ventas diarias.
     */

    public ResultSet sqldiary() {
        Connection con = connectionDB.getConnection();
        String query = "SELECT fecha_pedido, SUM(total_pedido) as venta_diaria " +
                "FROM pedidos " +
                "WHERE estado = 'Sent' " +
                "GROUP BY fecha_pedido " +
                "ORDER BY fecha_pedido DESC";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            return pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar ventas diarias: " + e.getMessage());
            return null;
        }
    }

    /**
     * Consulta las ventas semanales agrupadas por semana.
     * @return ResultSet con la semana, el rango de fechas y el total de ventas semanales.
     */

    public ResultSet sqlWeekly() {
        Connection con = connectionDB.getConnection();
        String query = "SELECT " +
                "YEARWEEK(fecha_pedido, 1) as semana, " +
                "MIN(fecha_pedido) as inicio_semana, " +
                "MAX(fecha_pedido) as fin_semana, " +
                "SUM(total_pedido) as venta_semanal " +
                "FROM pedidos " +
                "WHERE estado = 'Sent' " +
                "GROUP BY YEARWEEK(fecha_pedido, 1) " +
                "ORDER BY semana DESC";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            return pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar ventas semanales: " + e.getMessage());
            return null;
        }
    }


    /**
     * Consulta las ventas mensuales agrupadas por año y mes.
     * @return ResultSet con el año, el mes y el total de ventas mensuales.
     */

    public ResultSet sqlMonthly() {
        Connection con = connectionDB.getConnection();
        String query = "SELECT " +
                "YEAR(fecha_pedido) as año, " +
                "MONTH(fecha_pedido) as mes, " +
                "SUM(total_pedido) as venta_mensual " +
                "FROM pedidos " +
                "WHERE estado = 'Sent' " +
                "GROUP BY YEAR(fecha_pedido), MONTH(fecha_pedido) " +
                "ORDER BY año DESC, mes DESC";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            return pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar ventas mensuales: " + e.getMessage());
            return null;
        }
    }

    /**
     * Consulta los productos más vendidos.
     * @return ResultSet con el nombre del producto, la cantidad vendida y el total en ventas.
     */

    public ResultSet sqlBestSellers() {
        Connection con = connectionDB.getConnection();
         String query = "SELECT " +
                "i.nombre as productos, " +
                "SUM(d.cantidad) as cantidad_vendida, " +
                "SUM(d.subtotal) as total_ventas " +
                "FROM detalle_pedido d " +
                "JOIN pedidos o ON d.id_pedido = o.id_pedido JOIN productos i ON d.id_producto = i.id_producto " +
                "WHERE o.estado = 'Sent' " +
                "GROUP BY d.id_producto " +
                "ORDER BY cantidad_vendida DESC";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            return pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar productos más vendidos: " + e.getMessage());
            return null;
        }
    }


    /**
     * Consulta los clientes con mayor cantidad de compras.
     * @return ResultSet con el ID del cliente, nombre, número de órdenes y total gastado.
     */

    public ResultSet sqlTopCustomers() {
        Connection con = connectionDB.getConnection();
        String query = "SELECT " +
                "c.id_cliente, " +
                "c.nombre_completo as clientes, " +
                "COUNT(o.id_pedido) as numero_ordenes, " +
                "SUM(o.total_pedido) as total_gastado " +
                "FROM clientes c " +
                "JOIN pedidos o ON c.id_cliente = o.id_cliente " +
                "WHERE o.estado = 'Sent' " +
                "GROUP BY c.id_cliente, c.nombre_completo " +
                "ORDER BY total_gastado DESC";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            return pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar clientes con más compras: " + e.getMessage());
            return null;
        }
    }


}
