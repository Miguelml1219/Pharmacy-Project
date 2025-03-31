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


/**
 * Clase que representa la interfaz de usuario para la caja registradora en la farmacia.
 * Permite visualizar los datos de la tabla "caja" en la base de datos.
 */

public class Cash_RegisterGUI {

    private JFrame frame;
    private JFrame parentFrame;
    private JPanel main;
    private JTable table1;
    private JButton BackButton;
    private ConnectionDB connectionDB = new ConnectionDB();

    /**
     * Constructor de la interfaz de la caja registradora.
     * @param parentFrame La ventana principal desde donde se abre esta interfaz.
     */

    public Cash_RegisterGUI(JFrame parentFrame)
    {
        this.parentFrame = parentFrame;
        showdata();

        BackButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));

        BackButton.setBackground(new Color(41,171,226)); // Verde base
        BackButton.setForeground(Color.WHITE); // Texto en blanco
        BackButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde verde oscuro

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

    }

    /**
     * Método que obtiene y muestra los datos de la tabla "caja" en la base de datos.
     */

    public void showdata() {
        Cash_RegisterGUI.NonEditableTableModel modelo = new Cash_RegisterGUI.NonEditableTableModel();

        modelo.addColumn("Id Caja");
        modelo.addColumn("Concepto");
        modelo.addColumn("Valor");

        table1.setModel(modelo);

        String[] dato = new String[3];
        Connection con = connectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM caja");

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);

                modelo.addRow(dato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clase interna para un modelo de tabla no editable.
     */

    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    /**
     * Método que inicializa y muestra la ventana de la caja registradora.
     */

    public void runCash() {

        frame = new JFrame("Data Base Pharmacy");
        frame.setContentPane(this.main);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int option = JOptionPane.showConfirmDialog(frame, "¿Estas seguro que quieres salir?\nCualquier operación que estes haciendo y no hayas guardado se perdera.","Confirmar Salida",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                if(option == JOptionPane.YES_OPTION)
                {
                    frame.dispose(); // Cierra la ventana
                    System.exit(0);
                }
            }
        });

    }
}
