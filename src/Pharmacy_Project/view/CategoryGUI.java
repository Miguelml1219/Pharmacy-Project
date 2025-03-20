package Pharmacy_Project.view;
import Pharmacy_Project.connection.ConnectionDB;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class CategoryGUI {
    private JTextField textField1;
    private JTextField textField2;
    private JTable table1;
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JPanel main;
    private JLabel IDLabel;
    private JLabel nombre_CategoriaLabel;
    private DefaultTableModel tableModel;
    private Connection connection;

    public CategoryGUI() {
        connection = new ConnectionDB().getConnection();
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre Categoría"}, 0);
        table1.setModel(tableModel);

        agregarButton.addActionListener(e -> agregarCategoria());
        actualizarButton.addActionListener(e -> actualizarCategoria());
        eliminarButton.addActionListener(e -> eliminarCategoria());

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    textField1.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    textField2.setText(tableModel.getValueAt(selectedRow, 1).toString());
                }
            }
        });

        cargarCategoria();
    }

    public void agregarCategoria() {
        try {
            String sql = "INSERT INTO categoria (nombre_categoria) VALUES (?)"; // Eliminé id_categoria
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, textField2.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Categoría agregada exitosamente");
            cargarCategoria();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar categoría: " + e.getMessage());
        }
    }

    public void actualizarCategoria() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una categoría para actualizar");
            return;
        }

        try {
            String id_categoria = table1.getValueAt(selectedRow, 0).toString();
            String query = "UPDATE categoria SET nombre_categoria=? WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, textField2.getText());
            ps.setInt(2, Integer.parseInt(id_categoria));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Categoría actualizada exitosamente");
            cargarCategoria();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar categoría: " + e.getMessage());
        }
    }

    public void eliminarCategoria() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una categoría para eliminar");
            return;
        }

        try {
            String id_categoria = table1.getValueAt(selectedRow, 0).toString();
            String deleteQuery = "DELETE FROM categoria WHERE id_categoria=?";
            PreparedStatement ps = connection.prepareStatement(deleteQuery);
            ps.setInt(1, Integer.parseInt(id_categoria));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Categoría eliminada exitosamente");
            cargarCategoria();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar categoría: " + e.getMessage());
        }
    }

    public void cargarCategoria() {
        tableModel.setRowCount(0);
        try {
            String query = "SELECT id_categoria, nombre_categoria FROM categoria";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_categoria")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar categoría: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestión de Categoría");
            CategoryGUI gui = new CategoryGUI();
            frame.setContentPane(gui.main);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}