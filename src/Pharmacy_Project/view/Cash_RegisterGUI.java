package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.Cash_RegisterDAO;
import Pharmacy_Project.model.Cash_Register;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

/**
 * Clase que representa la interfaz gráfica para la gestión de caja.
 */
public class Cash_RegisterGUI {
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton registerButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable table1;
    private JPanel main;

    private Cash_RegisterDAO cashRegisterDAO = new Cash_RegisterDAO();
    private ConnectionDB connectionDB = new ConnectionDB();

    /**
     * Constructor de la clase Cash_RegisterGUI. Inicializa la interfaz y carga los datos de la caja.
     */
    public Cash_RegisterGUI() {
        obtenerCaja();

        registerButton.addActionListener(e -> {
            agregarCaja();
            obtenerCaja();
        });
        deleteButton.addActionListener(e -> {
            eliminarCaja();
            obtenerCaja();
        });
        updateButton.addActionListener(e -> {
            actualizarCaja();
            obtenerCaja();
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int seleccion = table1.getSelectedRow();
                if (seleccion >= 0) {
                    comboBox1.setSelectedItem(table1.getValueAt(seleccion, 1).toString());
                    textField1.setText(table1.getValueAt(seleccion, 2).toString());
                }
            }
        });
    }

    /**
     * Obtiene los datos de la caja desde la base de datos y los muestra en la tabla.
     */
    public void obtenerCaja() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Concept");
        modelo.addColumn("Value");

        table1.setModel(modelo);
        String[] datos = new String[3];
        Connection con = connectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM caja");

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                modelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error when loading box.");
        }
    }

    /**
     * Agrega una nueva caja a la base de datos con los datos ingresados en la interfaz.
     */
    private void agregarCaja() {
        String concepto = comboBox1.getSelectedItem().toString();
        int valor = Integer.parseInt(textField1.getText());

        Cash_Register caja = new Cash_Register(0, concepto, valor);
        if (cashRegisterDAO.agregarCaja(caja)) {
            JOptionPane.showMessageDialog(null, "Box successfully added.");
        } else {
            JOptionPane.showMessageDialog(null, "Error when adding box.");
        }
    }

    /**
     * Elimina la caja seleccionada en la tabla de la base de datos.
     */
    private void eliminarCaja() {
        try {
            int selectedRow = table1.getSelectedRow();
            int id = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
            if (cashRegisterDAO.eliminarCaja(id)) {
                JOptionPane.showMessageDialog(null, "Box successfully deleted.");
            } else {
                JOptionPane.showMessageDialog(null, "The box was not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID invalid.");
        }
    }

    /**
     * Actualiza la caja seleccionada con los nuevos valores ingresados en la interfaz.
     */
    private void actualizarCaja() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Select a box to update");
            return;
        }

        try (Connection con = connectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE caja SET concepto=?, valor=? WHERE id_caja=?")) {

            int id = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
            ps.setString(1, comboBox1.getSelectedItem().toString());
            ps.setString(2, textField1.getText());
            ps.setInt(3, id);

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "Box successfully upgraded");
                obtenerCaja();
            } else {
                JOptionPane.showMessageDialog(null, "No update box found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating box: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID invalid.");
        }
    }

    /**
     * Método principal para ejecutar la interfaz de gestión de caja.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("CRUD CASH");
        frame.setContentPane(new Cash_RegisterGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 500);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
