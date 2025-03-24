package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.Financial_MovementsDAO;
import Pharmacy_Project.model.Category;
import Pharmacy_Project.model.Financial_Movements;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Financial_MovementsGUI {

    private JFrame frame;
    private JFrame parentFrame;
    private JPanel main;
    private JButton BackButton;
    private JTable table1;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JTextField textField2;
    private JComboBox comboBox4;
    private JTextField textField3;
    private JTextField textField4;
    private ConnectionDB connectionDB = new ConnectionDB();
    private Financial_MovementsDAO financial_movementsDAO = new Financial_MovementsDAO();
    int rows = 0;

    public Financial_MovementsGUI(JFrame parentFrame)
    {
        textField4.setEditable(false);
        textField3.setEditable(false);
        comboBox1.setVisible(false);
        textField1.setVisible(false);
        this.parentFrame = parentFrame;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        textField4.setText(now.format(formatter));

        showdata();

        comboBox3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateComboBox3();
                }
            }
        });

        // Listener para comboBox2 (Ingresos)
        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateDescriptionIncomes();
                }
            }
        });

        // Listener para comboBox1 (Gastos)
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateDescriptionExpenses();
                }
            }
        });

        // Llamar a la función para establecer la UI y la descripción correctas al inicio
        updateComboBox3();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(textField2.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Complete quantity field");

                }else{

                    String tipo = (String) comboBox3.getSelectedItem();
                    String categoria;
                    if (comboBox2.isVisible()) {
                        categoria = (String) comboBox2.getSelectedItem();
                    } else {
                        categoria = (String) comboBox1.getSelectedItem();
                    }
                    int monto = Integer.parseInt(textField2.getText());
                    Timestamp fecha = Timestamp.valueOf(textField4.getText());
                    String descripcion = textField3.getText();
                    String metodo = (String) comboBox4.getSelectedItem();

                    Financial_Movements financial_movements = new Financial_Movements(0,tipo,categoria,monto,fecha,descripcion,metodo);
                    financial_movementsDAO.add(financial_movements);
                    clear();
                    showdata();
                }



            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table1.getSelectedRow();

                if (selectedRow == -1) { // Si no hay fila seleccionada
                    JOptionPane.showMessageDialog(null, "Please, select a movement to update");
                }
                if(textField2.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Complete quantity field");

                }else{

                    int id_movimiento = Integer.parseInt(textField1.getText());
                    String tipo = (String) comboBox3.getSelectedItem();
                    String categoria;
                    if (comboBox2.isVisible()) {
                        categoria = (String) comboBox2.getSelectedItem();
                    } else {
                        categoria = (String) comboBox1.getSelectedItem();
                    }
                    int monto = Integer.parseInt(textField2.getText());
                    Timestamp fecha = Timestamp.valueOf(textField4.getText());
                    String descripcion = textField3.getText();
                    String metodo = (String) comboBox4.getSelectedItem();

                    Financial_Movements financial_movements = new Financial_Movements(id_movimiento,tipo,categoria,monto,fecha,descripcion,metodo);
                    financial_movementsDAO.update(financial_movements);
                    clear();
                    showdata();
                }


            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table1.getSelectedRow();

                if (selectedRow == -1) { // Si no hay fila seleccionada
                    JOptionPane.showMessageDialog(null, "Please, select a movement to remove");
                } else {

                    int id_movement = Integer.parseInt(table1.getValueAt(selectedRow,0).toString());
                    String tipo_movimiento = table1.getValueAt(selectedRow,1).toString();
                    int monto = Integer.parseInt(table1.getValueAt(selectedRow,3).toString());
                    String metodo_pago = table1.getValueAt(selectedRow,6).toString();

                    financial_movementsDAO.delete(id_movement,metodo_pago,monto,tipo_movimiento);
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
                    textField2.setText((String)table1.getValueAt(selectedRows,3));
                    textField3.setText((String)table1.getValueAt(selectedRows,5));
                    String categoria = (String)table1.getValueAt(selectedRows,2);
                    comboBox1.setSelectedItem(categoria);

                    rows = selectedRows;

                }
            }
        });
    }

    public void updateComboBox3() {
        String selectedItem = (String) comboBox3.getSelectedItem();
        if (selectedItem.equals("Income")) {
            comboBox2.setVisible(true);
            comboBox2.setEnabled(true);
            comboBox1.setVisible(false);
            updateDescriptionIncomes(); // Establecer descripción inicial de Income
        } else if (selectedItem.equals("Expenses")) {
            comboBox1.setVisible(true);
            comboBox1.setEnabled(true);
            comboBox2.setVisible(false);
            updateDescriptionExpenses(); // Establecer descripción inicial de Expenses
        }
    }

    public void updateDescriptionIncomes() {
        String selectedItem = (String) comboBox2.getSelectedItem();
        if (selectedItem != null) {
            switch (selectedItem) {
                case "Sales returns":
                    textField3.setText("A sale is refunded due to a product return.");
                    break;
                case "Grants and subsidies":
                    textField3.setText("External funding is received without repayment.");
                    break;
                case "Commissions received":
                    textField3.setText("Money is earned for mediating sales or services.");
                    break;
                default:
                    textField3.setText("");
                    break;
            }
        }
    }

    // Método para actualizar la descripción desde comboBox1 (Expenses)
    private void updateDescriptionExpenses() {
        String selectedItem = (String) comboBox1.getSelectedItem();
        if (selectedItem != null) {
            switch (selectedItem) {
                case "Rent":
                    textField3.setText("Payment is made for the use of a space or property.");
                    break;
                case "Public services":
                    textField3.setText("Expenses for water, electricity, gas, and internet are covered.");
                    break;
                case "Purchase of medicines and supplies":
                    textField3.setText("Products are acquired to stock the business.");
                    break;
                default:
                    textField3.setText("");
                    break;
            }
        }
    }

    public void showdata() {
        Financial_MovementsGUI.NonEditableTableModel modelo = new Financial_MovementsGUI.NonEditableTableModel();

        modelo.addColumn("Id_Movement");
        modelo.addColumn("Type Movement");
        modelo.addColumn("Category");
        modelo.addColumn("Amount");
        modelo.addColumn("Date");
        modelo.addColumn("Description");
        modelo.addColumn("Method Payment");

        table1.setModel(modelo);

        String[] dato = new String[7];
        Connection con = connectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM movimientos_financieros");

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);
                dato[4] = rs.getString(5);
                dato[5] = rs.getString(6);
                dato[6] = rs.getString(7);

                modelo.addRow(dato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void clear(){
        textField1.setText("");
        textField2.setText("");
    }


    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    public void runMovements() {

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


