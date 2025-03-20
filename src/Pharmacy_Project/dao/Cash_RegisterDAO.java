package Pharmacy_Project.dao;

import Pharmacy_Project.connection.ConnectionDB;
import java.sql.*;

public class Cash_RegisterDAO {
    private Connection connection;

    public Cash_RegisterDAO() {
        connection = new ConnectionDB().getConnection();
    }

    public ResultSet obtenerMovimientos() throws SQLException {
        String sql = "SELECT id_caja, concepto, valor FROM caja";
        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }

    public void registrarMovimiento(String concepto, double valor) throws SQLException {
        String sql = "INSERT INTO caja (concepto, valor) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, concepto);
        ps.setDouble(2, valor);
        ps.executeUpdate();
    }

    public void actualizarMovimiento(int idCaja, String concepto, double valor) throws SQLException {
        String sql = "UPDATE caja SET concepto=?, valor=? WHERE id_caja=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, concepto);
        ps.setDouble(2, valor);
        ps.setInt(3, idCaja);
        ps.executeUpdate();
    }

    public void eliminarMovimiento(int idCaja) throws SQLException {
        String sql = "DELETE FROM caja WHERE id_caja=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idCaja);
        ps.executeUpdate();
    }
}

