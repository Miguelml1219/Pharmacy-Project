package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.OrderDAO;
import Pharmacy_Project.dao.Order_DetailDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class SentGUI {

    private JFrame frame;
    private JFrame parentFrame;
    private JPanel main;
    private JComboBox comboBox1;
    private JTable table1;
    private JButton BackButton;
    private JButton acceptButton;
    private JTextField textField1;
    private ConnectionDB connectionDB= new ConnectionDB();
    private OrderDAO orderDAO = new OrderDAO();
    private Order_DetailDAO order_detailDAO = new Order_DetailDAO();

    int rows = 0;

    public SentGUI(JFrame parentFrame){

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


    public void showdata()
    {
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
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }


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
