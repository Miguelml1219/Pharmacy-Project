package Pharmacy_Project.reports;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.utils.BackGround;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportGUI {
    private JPanel main;
    private JTable table1;
    private JButton diaryButton;
    private JButton weeklyButton;
    private JButton monthlyButton;
    private JButton bestSellersButton;
    private JButton customersPurchasesButton;
    private JButton BackButton;
    private ReportDAO reportDAO = new ReportDAO();

    private JFrame frame;
    private JFrame parentFrame;

    private ConnectionDB connectionDB = new ConnectionDB();

    public ReportGUI(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        // Crear una fuente personalizada
        Font headerFont = new Font("Marlett Non-latin", Font.BOLD, 14); // Fuente Arial, Negrita, Tamaño 14
        JTableHeader tableHeader = table1.getTableHeader();
        tableHeader.setFont(headerFont);

        diaryButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        weeklyButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        monthlyButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        bestSellersButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        customersPurchasesButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        BackButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));

        diaryButton.setBackground(new Color(0, 123, 255)); // Azul base
        diaryButton.setForeground(Color.WHITE); // Texto en blanco
        diaryButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        weeklyButton.setBackground(new Color(0, 123, 255)); // Azul base
        weeklyButton.setForeground(Color.WHITE); // Texto en blanco
        weeklyButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        monthlyButton.setBackground(new Color(0, 123, 255)); // Azul base
        monthlyButton.setForeground(Color.WHITE); // Texto en blanco
        monthlyButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        bestSellersButton.setBackground(new Color(0, 123, 255)); // Azul base
        bestSellersButton.setForeground(Color.WHITE); // Texto en blanco
        bestSellersButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        customersPurchasesButton.setBackground(new Color(0, 123, 255)); // Azul base
        customersPurchasesButton.setForeground(Color.WHITE); // Texto en blanco
        customersPurchasesButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        BackButton.setBackground(new Color(128, 192, 128)); // Verde base
        BackButton.setForeground(Color.WHITE); // Texto en blanco
        BackButton.setBorder(BorderFactory.createLineBorder(new Color(96, 160, 96), 3)); // Borde verde oscuro


        diaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportDAO.sqldiary();
                showdata();

            }
        });

        diaryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                diaryButton.setBackground(new Color(102, 178, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                diaryButton.setBackground(new Color(0, 123, 255)); // Restaurar color base
            }
        });

        weeklyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportDAO.sqlWeekly();
                showWeeklyReport();
            }
        });

        weeklyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                weeklyButton.setBackground(new Color(102, 178, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                weeklyButton.setBackground(new Color(0, 123, 255)); // Restaurar color base
            }
        });

        monthlyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportDAO.sqlMonthly();
                showMonthlyReport();

            }
        });

        monthlyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                monthlyButton.setBackground(new Color(102, 178, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                monthlyButton.setBackground(new Color(0, 123, 255)); // Restaurar color base
            }
        });

        bestSellersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportDAO.sqlBestSellers();
                showBestSellersReport();

            }
        });

        bestSellersButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bestSellersButton.setBackground(new Color(102, 178, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bestSellersButton.setBackground(new Color(0, 123, 255)); // Restaurar color base
            }
        });



        customersPurchasesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportDAO.sqlTopCustomers();
                showTopCustomersReport();

            }
        });

        customersPurchasesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                customersPurchasesButton.setBackground(new Color(102, 178, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                customersPurchasesButton.setBackground(new Color(0, 123, 255)); // Restaurar color base
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
                BackButton.setBackground(new Color(160, 208, 160)); // Verde más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                BackButton.setBackground(new Color(128, 192, 128)); // Restaurar color base
            }
        });


    }
    public void showdata() {
        NonEditableTableModel modelo = new NonEditableTableModel();
        modelo.addColumn("Date");
        modelo.addColumn("Daily Sales");
        table1.setModel(modelo);

        try {
            ResultSet rs = reportDAO.sqldiary();

            if (rs != null) {
                while (rs.next()) {
                    String fecha = rs.getString("fecha_pedido");
                    int ventaDiaria = rs.getInt("venta_diaria");

                    modelo.addRow(new Object[]{
                            fecha,
                            ventaDiaria
                    });
                }

                // Cierra el ResultSet después de usarlo
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error when displaying data: " + e.getMessage());
        }
    }

    public void showWeeklyReport() {
        NonEditableTableModel modelo = new NonEditableTableModel();
        modelo.addColumn("Week");
        modelo.addColumn("Start of week");
        modelo.addColumn("End of week");
        modelo.addColumn("Weekly Sale");
        table1.setModel(modelo);

        try {
            ResultSet rs = reportDAO.sqlWeekly();

            if (rs != null) {
                while (rs.next()) {
                    String semana = rs.getString("semana");
                    String inicioSemana = rs.getString("inicio_semana");
                    String finSemana = rs.getString("fin_semana");
                    int ventaSemanal = rs.getInt("venta_semanal");

                    modelo.addRow(new Object[]{
                            semana,
                            inicioSemana,
                            finSemana,
                            ventaSemanal                    });
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error when displaying weekly data: " + e.getMessage());
        }
    }

    public void showMonthlyReport() {
        NonEditableTableModel modelo = new NonEditableTableModel();
        modelo.addColumn("Year");
        modelo.addColumn("Month");
        modelo.addColumn("Monthly Sales");
        table1.setModel(modelo);

        try {
            ResultSet rs = reportDAO.sqlMonthly();

            if (rs != null) {
                while (rs.next()) {
                    int año = rs.getInt("año");
                    int mes = rs.getInt("mes");
                    int ventaMensual = rs.getInt("venta_mensual");

                    modelo.addRow(new Object[]{
                            año,
                            mes,
                          ventaMensual
                    });
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error displaying monthly data: " + e.getMessage());
        }
    }

    public void showBestSellersReport() {
        NonEditableTableModel modelo = new NonEditableTableModel();
        modelo.addColumn("Product");
        modelo.addColumn("Quantity Sold");
        modelo.addColumn("Total Sales");
        table1.setModel(modelo);

        try {
            ResultSet rs = reportDAO.sqlBestSellers();

            if (rs != null) {
                while (rs.next()) {
                    String producto = rs.getString("productos");
                    int cantidadVendida = rs.getInt("cantidad_vendida");
                    int totalVentas = rs.getInt("total_ventas");

                    modelo.addRow(new Object[]{
                            producto,
                            cantidadVendida,
                            totalVentas
                    });
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error displaying best-selling products: " + e.getMessage());
        }
    }

    public void showTopCustomersReport() {
        NonEditableTableModel modelo = new NonEditableTableModel();
        modelo.addColumn("ID Customer");
        modelo.addColumn("Customer");
        modelo.addColumn("Order Number");
        modelo.addColumn("Total Spent");
        table1.setModel(modelo);

        try {
            ResultSet rs = reportDAO.sqlTopCustomers();

            if (rs != null) {
                while (rs.next()) {
                    int idCliente = rs.getInt("id_cliente");
                    String cliente = rs.getString("clientes");
                    int numeroOrdenes = rs.getInt("numero_ordenes");
                    int totalGastado = rs.getInt("total_gastado");

                    modelo.addRow(new Object[]{
                            idCliente,
                            cliente,
                            numeroOrdenes,
                           totalGastado
                    });
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error when displaying customers with more purchases: " + e.getMessage());
        }
    }


    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }



    public void runReport() {

        frame = new JFrame("Data Base Pharmacy");
        frame.setContentPane(this.main);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

