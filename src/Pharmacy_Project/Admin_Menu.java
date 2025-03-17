package Pharmacy_Project;

import Pharmacy_Project.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton reportButton;
    private JButton detailInventoryButton;
    private JButton amountProductsButton;
    private JButton lowIncomeButton;
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

        // Configuración del tamaño del botón "Back"
        Dimension backButtonSize = new Dimension(86, 23);
        BackButton.setPreferredSize(backButtonSize);
        BackButton.setMinimumSize(backButtonSize);
        BackButton.setMaximumSize(backButtonSize);

        // Acción para abrir la gestión de clientes
        customersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerGUI customerGUI = new CustomerGUI(frame);
                customerGUI.runCustomer();
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

        // Acción para abrir la caja registradora
        cashRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cash_RegisterGUI cash_registerGUI = new Cash_RegisterGUI(frame);
                cash_registerGUI.runCash();
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

        // Acción para abrir la gestión de categorías
        categoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CategoryGUI categoryGUI = new CategoryGUI(frame);
                categoryGUI.runCategory();
            }
        });

        // Acción para generar reportes
        reportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                //ReportGUI reportGUI = new ReportGUI(frame);
//                reportGUI.runReport();
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

    }

    /**
     * Método para inicializar y mostrar la ventana del menú de administración.
     */

    public void runAdmin() {

        frame = new JFrame("Data Base Pharmacy");
        frame.setContentPane(this.main);
//          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(530,450);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
