package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class Cash_RegisterGUI {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTable table1;
    private JButton registrarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JButton calcularTotalButton;
    private JLabel totalLabel;
    private JPanel main;
    private JLabel cajaLabel;
    private JLabel IDLabel;
    private JLabel conceptoLabel;
    private JLabel valorLabel;
    private DefaultTableModel tableModel;
    private Connection connection;

    public Cash_RegisterGUI() {
        connection = new ConnectionDB().getConnection();
        tableModel = new DefaultTableModel(new String[]{"ID", "Concepto", "Valor"}, 0);
        table1.setModel(tableModel);

        registrarButton.addActionListener(e -> registrarMovimiento());
        actualizarButton.addActionListener(e -> actualizarMovimiento());
        eliminarButton.addActionListener(e -> eliminarMovimiento());
        calcularTotalButton.addActionListener(e -> calcularTotal());

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    textField1.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    textField2.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    textField3.setText(tableModel.getValueAt(selectedRow, 2).toString());
                }
            }
        });

        cargarMovimientos();
    }

    public void registrarMovimiento() {
        try {
            String sql = "INSERT INTO caja (concepto, valor) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, textField2.getText());
            ps.setDouble(2, Double.parseDouble(textField3.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Movimiento registrado exitosamente");
            cargarMovimientos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar movimiento: " + e.getMessage());
        }
    }

    public void actualizarMovimiento() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un movimiento para actualizar");
            return;
        }

        try {
            int id = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
            String sql = "UPDATE caja SET concepto=?, valor=? WHERE id_caja=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, textField2.getText());
            ps.setDouble(2, Double.parseDouble(textField3.getText()));
            ps.setInt(3, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Movimiento actualizado exitosamente");
            cargarMovimientos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar movimiento: " + e.getMessage());
        }
    }

    public void eliminarMovimiento() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un movimiento para eliminar");
            return;
        }

        try {
            int id = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
            String sql = "DELETE FROM caja WHERE id_caja=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Movimiento eliminado exitosamente");
            cargarMovimientos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar movimiento: " + e.getMessage());
        }
    }

    public void cargarMovimientos() {
        tableModel.setRowCount(0);
        try {
            String sql = "SELECT id_caja, concepto, valor FROM caja";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id_caja"),
                        rs.getString("concepto"),
                        rs.getDouble("valor")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar movimientos: " + e.getMessage());
        }
    }

    public void calcularTotal() {
        double total = 0.0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            total += Double.parseDouble(tableModel.getValueAt(i, 2).toString());
        }
        totalLabel.setText("Total: $" + total);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Caja Registradora");
            Cash_RegisterGUI gui = new Cash_RegisterGUI();
            frame.setContentPane(gui.main);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
