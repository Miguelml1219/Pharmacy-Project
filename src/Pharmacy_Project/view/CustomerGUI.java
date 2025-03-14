package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.CustomerDAO;
import Pharmacy_Project.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class CustomerGUI {
    private JPanel main;
    private JButton agregarButton, eliminarButton, actualizarButton;
    private JTextField textField1, textField2, textField3, textField4, textField5;
    private JComboBox<String> comboBoxCategoria;  // ComboBox para categoría
    private JTable Tabla;
    private CustomerDAO customerDAO = new CustomerDAO();
    private ConnectionDB connectionDB = new ConnectionDB();


    public CustomerGUI() {
        obtenerClientes();

        agregarButton.addActionListener(e -> {
            agregarCliente();
            obtenerClientes();
        });
        eliminarButton.addActionListener(e -> {
            eliminarCliente();
            obtenerClientes();
        });
        actualizarButton.addActionListener(e -> {
            actualizarCliente();
            obtenerClientes();
        });

        Tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int seleccion = Tabla.getSelectedRow();
                if (seleccion >= 0) {
                    textField1.setText(Tabla.getValueAt(seleccion, 1).toString());
                    textField2.setText(Tabla.getValueAt(seleccion, 2).toString());
                    textField3.setText(Tabla.getValueAt(seleccion, 3).toString());
                    textField4.setText(Tabla.getValueAt(seleccion, 4).toString());
                    textField5.setText(Tabla.getValueAt(seleccion, 5).toString());
                    comboBoxCategoria.setSelectedItem(Tabla.getValueAt(seleccion, 6).toString());
                }

            }
        });
    }

    public void obtenerClientes() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Document");
        modelo.addColumn("Name");
        modelo.addColumn("Number");
        modelo.addColumn("Email");
        modelo.addColumn("Location");
        modelo.addColumn("Category");

        Tabla.setModel(modelo);
        String[] datos = new String[7];
        Connection con = connectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM clientes");

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                modelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(main, "Error when loading customers.");
        }
    }


    private void agregarCliente() {
        String cedula = textField1.getText();
        String nombre = textField2.getText();
        String telefono = textField3.getText();
        String correo = textField4.getText();
        String direccion = textField5.getText();
        String categoria = comboBoxCategoria.getSelectedItem().toString();

        Customer customer = new Customer(0, cedula, nombre, telefono, correo, direccion, categoria);
        if (customerDAO.agregarCliente(customer)) {
            JOptionPane.showMessageDialog(main, "Customer successfully added.");
        } else {
            JOptionPane.showMessageDialog(main, "Error adding customer.");
        }
    }

    private void eliminarCliente() {
        try {
            int selectedRow = Tabla.getSelectedRow(); //selectedrow para seleccionar en id del cliente
            int id = Integer.parseInt(Tabla.getValueAt(selectedRow, 0).toString());
            if (customerDAO.eliminarCliente(id)) {

            } else {
                JOptionPane.showMessageDialog(main, "Customer not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(main, "ID invalid.");
        }
    }

    private void actualizarCliente() {
        int selectedRow = Tabla.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Select a customer to update");
            return;
        }

        try (Connection con = connectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE clientes SET cedula=?, nombre_completo=?, telefono=?, correo_electronico=?, direccion=?, categoria=? WHERE id_cliente=?")) {

            int id = Integer.parseInt(Tabla.getValueAt(selectedRow, 0).toString());

            ps.setString(1, textField1.getText()); // Cedula
            ps.setString(2, textField2.getText()); // Nombre
            ps.setString(3, textField3.getText()); // Telefono
            ps.setString(4, textField4.getText()); // Correo
            ps.setString(5, textField5.getText()); // Dirección
            ps.setString(6, comboBoxCategoria.getSelectedItem().toString()); // Categoria
            ps.setInt(7, id);

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "Client successfully upgraded");
                obtenerClientes();
            } else {
                JOptionPane.showMessageDialog(null, "Client not found for update");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating client: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID invalid.");
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("CRUD CUSTOMERS");
        frame.setContentPane(new CustomerGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 500);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
