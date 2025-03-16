package Pharmacy_Project.dao;


import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Cash_Register;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cash_RegisterDAO {
    private ConnectionDB connectionDB = new ConnectionDB();

    public List<Cash_Register> obtenerCaja() {
        List<Cash_Register> cajas = new ArrayList<>();
        String query = "SELECT * FROM caja";

        try (Connection con = connectionDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Cash_Register caja = new Cash_Register(rs.getInt("id_caja"),
                        rs.getString("concepto"), rs.getInt("valor"));
                cajas.add(caja);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cajas;
    }


    public boolean agregarCaja(Cash_Register caja) {
        String query = "INSERT INTO caja (concepto, valor) VALUES (?, ?)";

        try (Connection con = connectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, caja.getConcepto());
            pst.setInt(2, caja.getValor());


            int resultado = pst.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCaja(int id) {
        String query = "DELETE FROM caja WHERE id_caja = ?";
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
