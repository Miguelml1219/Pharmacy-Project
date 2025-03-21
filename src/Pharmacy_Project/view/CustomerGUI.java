package Pharmacy_Project.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.CustomerDAO;
import Pharmacy_Project.model.Customer;

public class CustomerGUI {
    private JPanel main;
    private JButton registerButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTable table1;
    private JTextField textField4;
    private JButton BackButton;
    private JComboBox comboBox1;
    private JTextField textField5;
    private JTextField textField6;
    private JFrame frame;
    private JFrame parentFrame;
    private CustomerDAO customerDAO = new CustomerDAO();
    private ConnectionDB connectionDB = new ConnectionDB();

    int rows = 0;


    public CustomerGUI(JFrame parentFrame)
    {
        textField1.setEditable(false);
        textField1.setVisible(false);
        showdata();

        this.parentFrame = parentFrame;

        Dimension backButtonSize = new Dimension(86, 23);
        BackButton.setPreferredSize(backButtonSize);
        BackButton.setMinimumSize(backButtonSize);
        BackButton.setMaximumSize(backButtonSize);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() || textField4.getText().trim().isEmpty() || textField5.getText().trim().isEmpty() || textField6.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete all fields");
                } else {
                    String num_doc = textField6.getText();
                    String nombre = textField2.getText();
                    String telefono = textField3.getText();
                    String email = textField4.getText();
                    String direccion = textField5.getText();
                    String categoria = (String) comboBox1.getSelectedItem();

                    if (!num_doc.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "The Num_Document field should only contain numbers");
                        return;
                    }
                    if (!telefono.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "The phone field should only contain numbers");
                        return;
                    }

                    int numDoc =Integer.parseInt(num_doc);

                    boolean docExist = false;
                    for (int i = 0; i < table1.getRowCount(); i++) {
                        int docExisting = Integer.parseInt(table1.getValueAt(i, 1).toString());
                        if (docExisting == numDoc) {
                            docExist = true;
                            break;
                        }
                    }

                    if (docExist) {
                        JOptionPane.showMessageDialog(null, "The number of document " + numDoc + " already exists");
                        textField6.setText("");
                        return;
                    }

                    Customer customer = new Customer(0, num_doc, nombre, telefono,email,direccion,categoria);
                    customerDAO.add(customer);
                    clear();
                    showdata();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() || textField4.getText().trim().isEmpty() || textField5.getText().trim().isEmpty() || textField6.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete all fields");
                }else{
                    String num_doc = textField6.getText();
                    String nombre = textField2.getText();
                    String telefono = textField3.getText();
                    String email = textField4.getText();
                    String direccion = textField5.getText();
                    String categoria = (String) comboBox1.getSelectedItem();

                    int id_cliente = Integer.parseInt(textField1.getText());

                    if (!num_doc.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "The Num_Document field should only contain numbers");
                        return;
                    }
                    if (!telefono.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "The field should only contain numbers");
                        return;
                    }

                    int numDoc =Integer.parseInt(num_doc);


                    Customer customer = new Customer(id_cliente, num_doc, nombre, telefono,email,direccion,categoria);
                    customerDAO.update(customer);
                    clear();
                    showdata();
                }

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table1.getSelectedRow();

                if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() || textField4.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete all fields");
                }else if (selectedRow == -1) { // Si no hay fila seleccionada
                    JOptionPane.showMessageDialog(null, "Please, select a client to remove");
                } else{
                    int id_cliente = Integer.parseInt(textField1.getText());
                    customerDAO.delete(id_cliente);

                    clear();
                    showdata();
                }

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

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);

                int selectedRows = table1.getSelectedRow();

                if(selectedRows>=0)
                {
                    textField1.setText((String)table1.getValueAt(selectedRows,0));
                    textField6.setText((String)table1.getValueAt(selectedRows,1));
                    textField2.setText((String)table1.getValueAt(selectedRows,2));
                    textField3.setText((String)table1.getValueAt(selectedRows,3));
                    textField4.setText((String)table1.getValueAt(selectedRows,4));
                    textField5.setText((String)table1.getValueAt(selectedRows,5));
                    String categoria = (String)table1.getValueAt(selectedRows,6);
                    comboBox1.setSelectedItem(categoria);

                    rows = selectedRows;
                }
            }
        });
    }

    public void showdata()
    {
        NonEditableTableModel modelo = new NonEditableTableModel();

        modelo.addColumn("Id_Customer");
        modelo.addColumn("Num_Document");
        modelo.addColumn("Full Name");
        modelo.addColumn("Phone Number");
        modelo.addColumn("Email");
        modelo.addColumn("Address");
        modelo.addColumn("Category");

        table1.setModel(modelo);

        String[] dato = new String[7];
        Connection con = connectionDB.getConnection();

        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM clientes");

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


    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }


    public void clear()
    {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
    }

    public void runCustomer() {

        frame = new JFrame("Data Base Pharmacy");
        frame.setContentPane(this.main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?\nAny operations you are performing will be lost.","Confirm exit",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                if(option == JOptionPane.YES_OPTION)
                {
                    frame.dispose(); // Cierra la ventana
                    System.exit(0);
                }
            }
        });
    }


}
