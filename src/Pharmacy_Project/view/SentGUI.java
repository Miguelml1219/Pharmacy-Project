package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.Financial_MovementsDAO;
import Pharmacy_Project.dao.OrderDAO;
import Pharmacy_Project.dao.Order_DetailDAO;
import Pharmacy_Project.model.Financial_Movements;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Clase SentGUI gestiona la interfaz de usuario para actualizar el estado de los pedidos en la farmacia.
 * Permite visualizar, buscar y actualizar los pedidos.
 */

public class SentGUI {

    private JFrame frame;
    private JFrame parentFrame;
    private JPanel main;
    private JComboBox comboBox1;
    private JTable table1;
    private JButton BackButton;
    private JButton acceptButton;
    private JTextField search;
    private ConnectionDB connectionDB= new ConnectionDB();
    private OrderDAO orderDAO = new OrderDAO();
    private Order_DetailDAO order_detailDAO = new Order_DetailDAO();
    private TableRowSorter<DefaultTableModel> sorter;
    private CustomerGUI.NonEditableTableModel modelo;
    private Financial_MovementsDAO financial_movementsDAO= new Financial_MovementsDAO();

    int rows = 0;

    /**
     * Constructor de la clase SentGUI.
     * @param parentFrame Marco padre desde donde se abre esta ventana.
     */

    public SentGUI(JFrame parentFrame){

        acceptButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        BackButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));

        BackButton.setBackground(new Color(41,171,226)); // Verde base
        BackButton.setForeground(Color.WHITE); // Texto en blanco
        BackButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde verde oscuro

        acceptButton.setBackground(new Color(0, 200, 0)); // Verde base
        acceptButton.setForeground(Color.WHITE); // Texto en blanco
        acceptButton.setBorder(BorderFactory.createLineBorder(new Color(96, 160, 96), 3)); // Borde verde oscuro

        sorter = new TableRowSorter<>(modelo);
        table1.setRowSorter(sorter);
        showdata();
        this.parentFrame = parentFrame;


        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table1.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select an order");
                    return;
                }

                Object value = table1.getValueAt(selectedRow, 0);
                int orderId;

                orderId = Integer.parseInt(value.toString());

                String status = (String) comboBox1.getSelectedItem();

                Connection con = connectionDB.getConnection();

                String query = "UPDATE pedidos SET estado = ? WHERE id_pedido = ?";

                try {
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, status);
                    pst.setInt(2, orderId);
                    int resultado = pst.executeUpdate();

                    if (resultado > 0) {
                        if("Delivered".equalsIgnoreCase(status))
                        {
                            String totalQuery = "SELECT total_pedido, fecha_pedido FROM pedidos WHERE id_pedido = ?";
                            PreparedStatement totalStmt = con.prepareStatement(totalQuery);
                            totalStmt.setInt(1,orderId);
                            ResultSet rs = totalStmt.executeQuery();

                            if(rs.next())
                            {
                                int total = rs.getInt("total_pedido");
                                Timestamp fecha = rs.getTimestamp("fecha_pedido");
                                String metodo = table1.getValueAt(selectedRow,4).toString();

                                Financial_Movements financial_movements = new Financial_Movements(0,
                                        "Income","Sale",total,fecha,
                                        "Sale made - Order #" + orderId, metodo);

                                financial_movementsDAO.add(financial_movements);                            }
                        }
                        JOptionPane.showMessageDialog(null, "Order updated successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Order not update");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error updating order status");
                }

                showdata();

            }
        });

        acceptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                acceptButton.setBackground(new Color(160, 208, 160)); // Verde más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                acceptButton.setBackground(new Color(0, 200, 0)); // Restaurar color base
            }
        });

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

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2 && table1.getSelectedRow()!= -1)
                {
                    int selectedRow = table1.getSelectedRow();
                    int orderId = Integer.parseInt(table1.getValueAt(selectedRow,0).toString());

                    DetailGUI detailGUI = new DetailGUI(frame,orderId);
                    detailGUI.runDetail();
                }
            }
        });

    }

    /**
     * Muestra los datos de los pedidos en la tabla.
     */


    public void showdata()
    {
        if (sorter != null) {
            table1.setRowSorter(null);
        }

        SentGUI.NonEditableTableModel modelo = new SentGUI.NonEditableTableModel();

        modelo.addColumn("Id_Order");
        modelo.addColumn("Id_Customer");
        modelo.addColumn("Date Order");
        modelo.addColumn("Total order");
        modelo.addColumn("Method Payment");
        modelo.addColumn("Status");

        table1.setModel(modelo);

        String[] dato = new String[6];
        Connection con = connectionDB.getConnection();

        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pedidos");

            while (rs.next())
            {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);
                dato[4] = rs.getString(5);
                dato[5] = rs.getString(6);

                modelo.addRow(dato);
            }

            sorter = new TableRowSorter<>(modelo);
            table1.setRowSorter(sorter);

            // Restablecer el filtro de búsqueda si hay texto
            if (!search.getText().trim().isEmpty()) {
                search.setText(search.getText());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Clase para limpiar las filas de la tabla al iniciar la pantalla.
     */

    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }


    /**
     * Inicia la ventana de gestión de pedidos enviados.
     */


    public void runSent() {

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
