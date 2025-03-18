package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cash_RegisterGUI {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTable table1;
    private JButton registrarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JButton calcularTotalButton;
    private JLabel totalLabel;
    private JPanel main;
    private DefaultTableModel tableModel;
    private JLabel cajaLabel;
    private JLabel IDLabel;
    private JLabel conceptoLabel;
    private JLabel valorLabel;
    private Container mainPanel;
    private ConnectionDB connection;

    public Cash_RegisterGUI() {
        tableModel = new DefaultTableModel(new String[]{"ID", "Concepto", "Valor"}, 0);
        table1.setModel(tableModel);

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarProducto();
            }
        });

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        calcularTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularTotal();
            }
        });
    }

    private void registrarProducto() {
        String id = textField1.getText();
        String concepto = textField2.getText();
        String valor = textField3.getText();
        tableModel.addRow(new Object[]{id, concepto, valor});
        limpiarCampos();
    }

    private void actualizarProducto() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.setValueAt(textField1.getText(), selectedRow, 0);
            tableModel.setValueAt(textField2.getText(), selectedRow, 1);
            tableModel.setValueAt(textField3.getText(), selectedRow, 2);
        }
    }

    private void eliminarProducto() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        }
    }

    private void calcularTotal() {
        double total = 0.0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            total += Double.parseDouble(tableModel.getValueAt(i, 2).toString());
        }
        totalLabel.setText("Total: $" + total);
    }

    private void limpiarCampos() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Caja Registradora");
        Cash_RegisterGUI gui = new Cash_RegisterGUI();
        frame.setContentPane(gui.main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
/* dreidy */