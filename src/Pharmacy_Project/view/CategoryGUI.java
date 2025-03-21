package Pharmacy_Project.view;
import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.CategoryDAO;
import Pharmacy_Project.model.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

/**
 * Clase que representa la interfaz gráfica para la gestión de categorías.
 */
public class CategoryGUI {
    private JComboBox comboBox1;
    private JButton registrarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JTable table1;
    private JPanel main;
    private CategoryDAO categoryDAO = new CategoryDAO();
    private ConnectionDB connectionDB = new ConnectionDB();

    /**
     * Constructor de la clase CategoryGUI. Inicializa la interfaz y carga los datos de las categorías.
     */
    public CategoryGUI() {
        obtenerCategoria();

        registrarButton.addActionListener(e -> {
            agregarCategoria();
            obtenerCategoria();
        });
        eliminarButton.addActionListener(e -> {
            eliminarCategoria();
            obtenerCategoria();
        });
        actualizarButton.addActionListener(e -> {
            actualizarCategoria();
            obtenerCategoria();
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int seleccion = table1.getSelectedRow();
                if (seleccion >= 0) {
                    comboBox1.setSelectedItem(table1.getValueAt(seleccion, 1).toString());
                }
            }
        });
    }

    /**
     * Obtiene los datos de las categorías desde la base de datos y los muestra en la tabla.
     */
    public void obtenerCategoria() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Category");

        table1.setModel(modelo);
        String[] datos = new String[2];
        Connection con = connectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM categoria");

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                modelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading category.");
        }
    }

    /**
     * Agrega una nueva categoría a la base de datos con los datos ingresados en la interfaz.
     */
    private void agregarCategoria() {
        String categoria = comboBox1.getSelectedItem().toString();

        Category ct = new Category(0, categoria);
        if (categoryDAO.agregarCategoria(ct)) {
            JOptionPane.showMessageDialog(null, "Category successfully added.");
        } else {
            JOptionPane.showMessageDialog(null, "Error adding category.");
        }
    }

    /**
     * Elimina la categoría seleccionada en la tabla de la base de datos.
     */
    private void eliminarCategoria() {
        try {
            int selectedRow = table1.getSelectedRow();
            int id = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
            if (categoryDAO.eliminarCategoria(id)) {
                JOptionPane.showMessageDialog(null, "Category successfully deleted.");
            } else {
                JOptionPane.showMessageDialog(null, "The category was not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID invalid.");
        }
    }

    /**
     * Actualiza la categoría seleccionada con los nuevos valores ingresados en la interfaz.
     */
    private void actualizarCategoria() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Select a category to update");
            return;
        }

        try (Connection con = connectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE categoria SET nombre_categoria=? WHERE id_categoria=?")) {

            int id = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
            ps.setString(1, comboBox1.getSelectedItem().toString());
            ps.setInt(2, id);

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "Category successfully updated");
                obtenerCategoria();
            } else {
                JOptionPane.showMessageDialog(null, "No category found for update");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating category: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID invalid.");
        }
    }

    /**
     * Método principal para ejecutar la interfaz de gestión de categorías.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("CRUD CATEGORY");
        frame.setContentPane(new CategoryGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 500);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
