package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.Order_DetailDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.sql.*;

/**
 * Clase DetailGUI que representa la interfaz gráfica para la visualización de detalles de pedidos.
 * Permite mostrar los productos asociados a una orden específica.
 */

public class DetailGUI {

    private JPanel main;
    private JTable table1;
    private JButton BackButton;
    private JTextField search;

    private JFrame frame;
    private JFrame parentFrame;
    private ConnectionDB connectionDB = new ConnectionDB();
    private Order_DetailDAO order_detailDAO = new Order_DetailDAO();
    private TableRowSorter<DefaultTableModel> sorter;
    private CustomerGUI.NonEditableTableModel modelo;

    /**
     * Constructor de la clase DetailGUI.
     * Inicializa la ventana de detalles de un pedido y muestra los datos.
     * @param parentFrame Marco padre desde donde se abre esta ventana.
     * @param orderId ID del pedido cuyos detalles se desean visualizar.
     */

    public DetailGUI(JFrame parentFrame, int orderId)
    {
        this.parentFrame = parentFrame;

        showdata(orderId);

        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (parentFrame != null){
                    parentFrame.setVisible(true);
                }
                frame.dispose();
            }
        });

    }

    /**
     * Muestra los datos de los detalles de un pedido en la tabla.
     * @param orderId ID del pedido cuyos detalles se desean visualizar.
     */

    public void showdata(int orderId)
    {
        DetailGUI.NonEditableTableModel modelo = new DetailGUI.NonEditableTableModel();

        modelo.addColumn("id_detail");
        modelo.addColumn("id_order");
        modelo.addColumn("Product");
        modelo.addColumn("Presentation");
        modelo.addColumn("Amount");
        modelo.addColumn("Price");
        modelo.addColumn("Subtotal");

        table1.setModel(modelo);

        String[] dato = new String[7];
        Connection con = connectionDB.getConnection();

        try
        {
            table1.getColumnModel().getColumn(0).setMinWidth(0);
            table1.getColumnModel().getColumn(0).setMaxWidth(0);
            table1.getColumnModel().getColumn(0).setWidth(0);

            table1.getColumnModel().getColumn(1).setMinWidth(0);
            table1.getColumnModel().getColumn(1).setMaxWidth(0);
            table1.getColumnModel().getColumn(1).setWidth(0);

            String query = "SELECT d.id_detalle, d.id_pedido, i.nombre, d.und_medida, d.cantidad, d.precio_unitario, d.subtotal " +
                    "FROM detalle_pedido d " +
                    "JOIN productos i ON d.id_producto = i.id_producto JOIN pedidos o ON d.id_pedido = o.id_pedido " +
                    "WHERE d.id_pedido = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);
                dato[4] = rs.getString(5);
                dato[5] = rs.getString(6);
                dato[6] = rs.getString(7);

                modelo.addRow(dato);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Clase para definir un modelo de tabla no editable.
     */

    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    /**
     * Ejecuta la ventana de detalles del pedido.
     */

    public void runDetail() {

        frame = new JFrame("Data Base Pharmacy");
        frame.setContentPane(this.main);
//          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(900,650);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
