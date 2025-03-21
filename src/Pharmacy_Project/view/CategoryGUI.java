package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Pharmacy_Project.dao.CategoryDAO;


public class CategoryGUI {
    private JPanel main;
    private JTable table1;
    private JTextField textField1;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField textField2;
    private JButton BackButton;
    private JFrame frame;
    private JFrame parentFrame;
    private CategoryDAO categoryDAO = new CategoryDAO();
    private ConnectionDB connectionDB = new ConnectionDB();

    int rows = 0;

    public CategoryGUI(JFrame parentFrame) {

        textField1.setVisible(false);
        this.parentFrame = parentFrame;

        showdata();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField2.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Complete the field Name Category");

                } else {

                    String name_cat = textField2.getText();

                    if (!name_cat.matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
                        JOptionPane.showMessageDialog(null, "The Name Category field must only contain letters");
                        return;
                    }

                    boolean namExist = false;
                    for (int i = 0; i < table1.getRowCount(); i++) {
                        String namExisting = table1.getValueAt(i, 1).toString();
                        if (namExisting.equalsIgnoreCase(name_cat)) {
                            namExist = true;
                            break;
                        }
                    }

                    if (namExist) {
                        JOptionPane.showMessageDialog(null, "The name " + name_cat + " already exists");
                        textField2.setText("");
                        return;
                    }

                    Category category = new Category(0, name_cat);
                    categoryDAO.add(category);
                    clear();
                    showdata();
                }

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField2.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Complete the field Name Category");

                } else {

                    String name_cat = textField2.getText();

                    int id_category = Integer.parseInt(textField1.getText());

                    if (!name_cat.matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
                        JOptionPane.showMessageDialog(null, "The Name Category field must only contain letters");
                        return;
                    }

                    boolean namExist = false;
                    for (int i = 0; i < table1.getRowCount(); i++) {
                        String namExisting = table1.getValueAt(i, 1).toString();
                        if (namExisting.equalsIgnoreCase(name_cat)) {
                            namExist = true;
                            break;
                        }
                    }

                    if (namExist) {
                        JOptionPane.showMessageDialog(null, "The name " + name_cat + " already exists");
                        textField2.setText("");
                        return;
                    }

                    Category category = new Category(id_category, name_cat);
                    categoryDAO.update(category);
                    clear();
                    showdata();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table1.getSelectedRow();

                if (textField2.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete the field Name Category");
                }else if (selectedRow == -1) { // Si no hay fila seleccionada
                    JOptionPane.showMessageDialog(null, "Please, select a category to remove");
                } else {

                    int id_category = Integer.parseInt(textField1.getText());

                    categoryDAO.delete(id_category);
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
                    textField2.setText((String)table1.getValueAt(selectedRows,1));

                    rows = selectedRows;

                }
            }
        });
    }

    public void showdata() {
        CategoryGUI.NonEditableTableModel modelo = new CategoryGUI.NonEditableTableModel();

        modelo.addColumn("Id_Category");
        modelo.addColumn("Name Category");

        table1.setModel(modelo);

        String[] dato = new String[2];
        Connection con = connectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM categoria");

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);

                modelo.addRow(dato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    public void clear(){
        textField1.setText("");
        textField2.setText("");
    }

    public void runCategory() {

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
