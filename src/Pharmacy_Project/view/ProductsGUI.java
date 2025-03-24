package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.ProductsDAO;
import Pharmacy_Project.model.Products;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que representa la interfaz gráfica para la gestión de productos.
 */
public class ProductsGUI {
    private JButton registrarButton, actualizarButton, eliminarButton;
    private JTextField textField1, textField2, textField3, textField4, textField5, textField6, textField7;
    private JTable table1;
    private JPanel main;
    private ProductsDAO productosDAO = new ProductsDAO();
    private ConnectionDB connectionDB = new ConnectionDB();

    /**
     * Constructor de la clase ProductsGUI. Inicializa la interfaz y carga los datos de los productos.
     */
    public ProductsGUI() {
        obtenerProductos();

        registrarButton.addActionListener(e -> {
            agregarProducto();
            obtenerProductos();
        });

        eliminarButton.addActionListener(e -> {
            eliminarProducto();
            obtenerProductos();
        });

        actualizarButton.addActionListener(e -> {
            actualizarProducto();
            obtenerProductos();
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int seleccion = table1.getSelectedRow();
                if (seleccion >= 0) {
                    textField1.setText(table1.getValueAt(seleccion, 2).toString());
                    textField2.setText(table1.getValueAt(seleccion, 3).toString());
                    textField3.setText(table1.getValueAt(seleccion, 4).toString());
                    textField4.setText(table1.getValueAt(seleccion, 5).toString());
                    textField5.setText(table1.getValueAt(seleccion, 6).toString());
                    textField6.setText(table1.getValueAt(seleccion, 7).toString());
                    textField7.setText(table1.getValueAt(seleccion, 8).toString());
                }
            }
        });
    }

    /**
     * Obtiene los datos de los productos desde la base de datos y los muestra en la tabla.
     */
    public void obtenerProductos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("id_category");
        modelo.addColumn("Name");
        modelo.addColumn("Description");
        modelo.addColumn("Price");
        modelo.addColumn("Current-Piece Stock ");
        modelo.addColumn("Minimum Stock ");
        modelo.addColumn("Expiration Date ");
        modelo.addColumn("Lot");

        table1.setModel(modelo);

        try (Connection con = connectionDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM productos")) {

            while (rs.next()) {
                String[] datos = new String[9];
                datos[0] = rs.getString("id_producto");
                datos[1] = rs.getString("id_categoria");
                datos[2] = rs.getString("nombre");
                datos[3] = rs.getString("descripcion");
                datos[4] = rs.getString("precio");
                datos[5] = rs.getString("stock_actual");
                datos[6] = rs.getString("stock_minimo");
                datos[7] = rs.getString("fecha_vencimiento");
                datos[8] = rs.getString("lote");
                modelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error when loading products.");
        }
    }

    /**
     * Agrega un nuevo producto a la base de datos con los datos ingresados en la interfaz.
     */
    public void agregarProducto() {
        try {
            String nombre = textField1.getText();
            String descripcion = textField2.getText();
            int precio = Integer.parseInt(textField3.getText());
            int stock_actual = Integer.parseInt(textField4.getText());
            int stock_minimo = Integer.parseInt(textField5.getText());
            String lote = textField7.getText();

            if (stock_minimo > stock_actual) {
                JOptionPane.showMessageDialog(null, "The minimum stock cannot be higher than the current stock.");
                return;
            }

            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaUtil = formato.parse(textField6.getText());
            java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());

            int id_categoria = 1;
            Products products = new Products(0, id_categoria, precio, stock_actual, stock_minimo, nombre, descripcion, lote, fechaSql);

            if (productosDAO.agregarProductos(products)) {
                JOptionPane.showMessageDialog(null, "Product successfully added.");
                obtenerProductos();
            } else {
                JOptionPane.showMessageDialog(null, "Error adding product.");
            }
        } catch (ParseException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error in input format.");
        }
    }

    /**
     * Actualiza el producto seleccionado con los nuevos valores ingresados en la interfaz.
     */
    public void actualizarProducto() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Select a product to update");
            return;
        }

        try (Connection con = connectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE productos SET nombre=?, descripcion=?, precio=?, stock_actual=?, stock_minimo=?, fecha_vencimiento=?, lote=? WHERE id_producto=?")) {

            int id = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
            ps.setString(1, textField1.getText());
            ps.setString(2, textField2.getText());
            ps.setInt(3, Integer.parseInt(textField3.getText()));
            ps.setInt(4, Integer.parseInt(textField4.getText()));
            ps.setInt(5, Integer.parseInt(textField5.getText()));
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaUtil = formato.parse(textField6.getText());
            ps.setDate(6, new java.sql.Date(fechaUtil.getTime()));
            ps.setString(7, textField7.getText());
            ps.setInt(8, id);

            ps.executeUpdate();
            obtenerProductos();
        } catch (SQLException | ParseException e) {
            JOptionPane.showMessageDialog(null, "Error updating product.");
        }
    }

    /**
     * Elimina el producto seleccionado en la tabla de la base de datos.
     */
    public void eliminarProducto() {
        try {
            int selectedRow = table1.getSelectedRow();
            int id = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
            productosDAO.eliminarProducto(id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid ID.");
        }
    }
    /**
     * Método principal para ejecutar la interfaz de gestión de clientes.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("CRUD CUSTOMERS");
        frame.setContentPane(new ProductsGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 500);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
