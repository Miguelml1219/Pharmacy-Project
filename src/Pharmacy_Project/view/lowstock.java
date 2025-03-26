package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 * Clase LowStock que representa la interfaz gráfica para visualizar los productos con bajo stock en la farmacia.
 */

public class LowStock {

    private JPanel main;
    private JTable table1;
    private JButton BackButton;
    private JTextField search;

    private JFrame frame;
    private JFrame parentFrame;
    private ConnectionDB connectionDB = new ConnectionDB();
    private TableRowSorter<DefaultTableModel> sorter;
    private CustomerGUI.NonEditableTableModel modelo;

    /**
     * Constructor de la clase LowStock.
     * @param parentFrame El marco padre desde donde se llama esta ventana.
     */

    public LowStock(JFrame parentFrame)
    {

        BackButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));

        BackButton.setBackground(new Color(41,171,226)); // Verde base
        BackButton.setForeground(Color.WHITE); // Texto en blanco
        BackButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde verde oscuro

        this.parentFrame = parentFrame;
        sorter = new TableRowSorter<>(modelo);
        table1.setRowSorter(sorter);
        showdata();

        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (parentFrame != null){
                    parentFrame.setVisible(true);
                }
                frame.dispose();
            }
        });

        BackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                BackButton.setBackground(new Color(102, 178, 255)); // Verde más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                BackButton.setBackground(new Color(41,171,226)); // Restaurar color base
            }
        });

        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = search.getText().trim().toLowerCase();

                if (sorter != null) {
                    // Filtro que busca en todas las columnas
                    RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
                        @Override
                        public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                            for (int i = 0; i < entry.getValueCount(); i++) {
                                if (entry.getStringValue(i).toLowerCase().contains(searchText)) {
                                    return true;
                                }
                            }
                            return false;
                        }
                    };

                    sorter.setRowFilter(filter);
                }
            }
        });
    }

    /**
     * Método para cargar los datos de los productos con bajo stock desde la base de datos.
     */

    public void showdata() {

        if (sorter != null) {
            table1.setRowSorter(null);
        }

        LowStock.NonEditableTableModel modelo = new LowStock.NonEditableTableModel();

        modelo.addColumn("Product");
        modelo.addColumn("Current Stock");
        modelo.addColumn("Minimum Stock");

        table1.setModel(modelo);

        String[] dato = new String[3];
        Connection con = connectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nombre, stock_actual, stock_minimo FROM productos WHERE stock_actual <= stock_minimo");

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);

                modelo.addRow(dato);
            }

            sorter = new TableRowSorter<>(modelo);
            table1.setRowSorter(sorter);

            // Restablecer el filtro de búsqueda si hay texto
            if (!search.getText().trim().isEmpty()) {
                search.setText(search.getText());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clase interna que define un modelo de tabla no editable.
     */

    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    /**
     * Método que inicializa y muestra la ventana de productos con bajo stock.
     */

    public void runLow() {

        frame = new JFrame("Data Base Pharmacy");
        frame.setContentPane(this.main);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?\nAny operation you are performing and have not saved will be lost.","Confirm exit",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                if(option == JOptionPane.YES_OPTION)
                {
                    frame.dispose(); // Cierra la ventana
                    System.exit(0);
                }
            }
        });

    }
}
