package Pharmacy_Project;

import Pharmacy_Project.reports.ReportGUI;
import Pharmacy_Project.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase Admin_Menu representa la interfaz de administración del sistema.
 * Desde este menú, los administradores pueden gestionar productos, clientes,
 * caja registradora, movimientos financieros, categorías y generar reportes.
 */

public class Admin_Menu {

    private JPanel main;
    private JButton productsButton;
    private JButton cashRegisterButton;
    private JButton financialMovementsButton;
    private JButton categoryButton;
    private JButton reportsButton;
    private JButton customersButton;
    private JButton employeeButton;
    private JButton tablesButton;
    private JButton inventoryButton;
    private JButton BackButton;
    private JButton ordersSentButton;
    private JButton reportButton;
    private JButton detailInventoryButton;
    private JButton amountProductsButton;
    private JButton lowStockButton;
    private JLabel tittle;
    private JFrame frame;
    private JFrame parentFrame;


    /**
     * Constructor de Admin_Menu.
     *
     * @param parentFrame JFrame del menú principal para poder regresar a él.
     */
    public Admin_Menu(JFrame parentFrame)
    {
        this.parentFrame = parentFrame;

//        // Configuración del tamaño del botón "Back"
//        Dimension backButtonSize = new Dimension(86, 23);
//        BackButton.setPreferredSize(backButtonSize);
//        BackButton.setMinimumSize(backButtonSize);
//        BackButton.setMaximumSize(backButtonSize);


        customersButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        financialMovementsButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        productsButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        categoryButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        cashRegisterButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        reportsButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        ordersSentButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        lowStockButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        BackButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));

        customersButton.setBackground(new Color(41,171,226)); // Azul base
        customersButton.setForeground(Color.WHITE); // Texto en blanco
        customersButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        financialMovementsButton.setBackground(new Color(41,171,226)); // Azul base
        financialMovementsButton.setForeground(Color.WHITE); // Texto en blanco
        financialMovementsButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        productsButton.setBackground(new Color(41,171,226)); // Azul base
        productsButton.setForeground(Color.WHITE); // Texto en blanco
        productsButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        categoryButton.setBackground(new Color(41,171,226)); // Azul base
        categoryButton.setForeground(Color.WHITE); // Texto en blanco
        categoryButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        cashRegisterButton.setBackground(new Color(41,171,226)); // Azul base
        cashRegisterButton.setForeground(Color.WHITE); // Texto en blanco
        cashRegisterButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        reportsButton.setBackground(new Color(41,171,226)); // Azul base
        reportsButton.setForeground(Color.WHITE); // Texto en blanco
        reportsButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        ordersSentButton.setBackground(new Color(41,171,226)); // Azul base
        ordersSentButton.setForeground(Color.WHITE); // Texto en blanco
        ordersSentButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        lowStockButton.setBackground(new Color(41,171,226)); // Azul base
        lowStockButton.setForeground(Color.WHITE); // Texto en blanco
        lowStockButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        BackButton.setBackground(new Color(255, 102, 102)); // Azul base
        BackButton.setForeground(Color.WHITE); // Texto en blanco
        BackButton.setBorder(BorderFactory.createLineBorder(new Color(139, 0, 0), 3)); // Borde azul oscuro


        // Acción para abrir la gestión de clientes
        customersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerGUI customerGUI = new CustomerGUI(frame);
                customerGUI.runCustomer();
            }
        });

        customersButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                customersButton.setBackground(new Color(0, 123, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                customersButton.setBackground(new Color(41,171,226)); // Restaurar color base
            }
        });

        // Acción para abrir la gestión de productos
        productsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductsGUI productsGUI = new ProductsGUI(frame);
                productsGUI.runProducts();
            }
        });

        productsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                productsButton.setBackground(new Color(0, 123, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                productsButton.setBackground(new Color(41,171,226)); // Restaurar color base
            }
        });

        // Acción para abrir la caja registradora
        cashRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cash_RegisterGUI cash_registerGUI = new Cash_RegisterGUI(frame);
                cash_registerGUI.runCash();
            }
        });

        categoryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                categoryButton.setBackground(new Color(0, 123, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                categoryButton.setBackground(new Color(41,171,226)); // Restaurar color base
            }
        });

        // Acción para abrir los movimientos financieros
        financialMovementsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Financial_MovementsGUI financial_movementsGUI = new Financial_MovementsGUI(frame);
                financial_movementsGUI.runMovements();
            }
        });

        financialMovementsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                financialMovementsButton.setBackground(new Color(0, 123, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                financialMovementsButton.setBackground(new Color(41,171,226)); // Restaurar color base
            }
        });

        // Acción para abrir la gestión de categorías
        categoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CategoryGUI categoryGUI = new CategoryGUI(frame);
                categoryGUI.runCategory();
            }
        });

        categoryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                categoryButton.setBackground(new Color(0, 123, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                categoryButton.setBackground(new Color(41,171,226)); // Restaurar color base
            }
        });

        // Acción para generar reportes
        reportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportGUI reportGUI = new ReportGUI(frame);
                reportGUI.runReport();
            }
        });

        reportsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                reportsButton.setBackground(new Color(0, 123, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                reportsButton.setBackground(new Color(41,171,226)); // Restaurar color base
            }
        });

        lowStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LowStock lowstock = new LowStock(frame);
                lowstock.runLow();
            }
        });

        lowStockButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lowStockButton.setBackground(new Color(0, 123, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lowStockButton.setBackground(new Color(41,171,226)); // Restaurar color base
            }
        });

        ordersSentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SentGUI sentGUI = new SentGUI(frame);
                sentGUI.runSent();
            }
        });

        ordersSentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ordersSentButton.setBackground(new Color(0, 123, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ordersSentButton.setBackground(new Color(41,171,226)); // Restaurar color base
            }
        });

        // Acción para regresar al menú principal
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
                BackButton.setBackground(new Color(220, 53, 69)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                BackButton.setBackground(new Color(255, 102, 102)); // Restaurar color base
            }
        });

    }

    /**
     * Método para inicializar y mostrar la ventana del menú de administración.
     */

    public void runAdmin() {

        frame = new JFrame("Data Base Pharmacy");
        frame.setContentPane(this.main);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int option = JOptionPane.showConfirmDialog(frame, "¿Está seguro de que desea salir?\nCualquier operación que esté realizando y no haya guardado se perderá.","Confirmar Salida",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                if(option == JOptionPane.YES_OPTION)
                {
                    frame.dispose(); // Cierra la ventana
                    System.exit(0);
                }
            }
        });
    }

}
