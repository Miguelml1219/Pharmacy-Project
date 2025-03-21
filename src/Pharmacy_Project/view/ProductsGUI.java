package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.ProductsDAO;
import Pharmacy_Project.model.Products;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class ProductsGUI {
    private JPanel main;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox1;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JButton updatedButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton BackButton;
    private JFrame frame;
    private JFrame parentFrame;
    private ProductsDAO productsDAO = new ProductsDAO();
    private ConnectionDB connectionDB = new ConnectionDB();
    private Map<String, Integer> categoryMap = new HashMap<>();


    int rows = 0;

    public ProductsGUI(JFrame parentFrame) {

        textField1.setVisible(false);
        this.parentFrame = parentFrame;

        showdata();
        updateComboBox();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() || textField4.getText().trim().isEmpty()|| textField5.getText().trim().isEmpty()
                        || textField6.getText().trim().isEmpty()|| textField7.getText().trim().isEmpty()|| textField8.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete all fields");
                } else {

                    String nombre = textField2.getText();
                    int id_categoria = categoryMap.get(comboBox1.getSelectedItem().toString());
                    String descripcion = textField3.getText();
                    String precioT = textField4.getText();
                    String stockA = textField5.getText();
                    String stockM = textField6.getText();
                    String fechaT = textField7.getText();
                    String lote = textField8.getText();


                    if (!precioT.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "The price field should only contain numbers");
                        return;
                    } else if (!stockA.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "The current stock field should only contain numbers");
                        return;
                    }else if(!stockM.matches("\\d+")){
                        JOptionPane.showMessageDialog(null, "The minimum stock field should only contain numbers");
                        return;
                    } else if (!fechaT.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        JOptionPane.showMessageDialog(null, "Incorrect date format. Should be YYYYY-MM-DD");
                        return;
                    }

                    Date fecha_vencimiento;
                    try {
                        // Verificar si la fecha es válida
                        LocalDate fechaValidada = LocalDate.parse(fechaT);
                        fecha_vencimiento = Date.valueOf(fechaValidada);
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid date. Verify that the month and day are correct.");
                        return;
                    }

                    int precio = Integer.parseInt(precioT);
                    int stock_actual = Integer.parseInt(stockA);
                    int stock_minimo = Integer.parseInt(stockM);


                    boolean namExist = false;
                    for (int i = 0; i < table1.getRowCount(); i++) {
                        String namExisting = table1.getValueAt(i, 2).toString();
                        if (namExisting.equalsIgnoreCase(nombre)) {
                            namExist = true;
                            break;
                        }
                    }

                    if (namExist) {
                        JOptionPane.showMessageDialog(null, "The name " + nombre + " already exists");
                        textField2.setText("");
                        return;
                    }



                    Products products = new Products(0,id_categoria, nombre, descripcion, precio, stock_actual, stock_minimo, fecha_vencimiento, lote);
                    productsDAO.add(products);

                    clear();
                    showdata();
                }
            }
        });

        updatedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() || textField4.getText().trim().isEmpty()|| textField5.getText().trim().isEmpty()
                        || textField6.getText().trim().isEmpty()|| textField7.getText().trim().isEmpty()|| textField8.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete all fields");
                } else {

                    int id_producto = Integer.parseInt(textField1.getText());
                    String nombre = textField2.getText();
                    int id_categoria = categoryMap.get(comboBox1.getSelectedItem().toString());
                    String descripcion = textField3.getText();
                    String precioT = textField4.getText();
                    String stockA = textField5.getText();
                    String stockM = textField6.getText();
                    Date fecha_vencimiento = Date.valueOf(textField7.getText());
                    String lote = textField8.getText();


                    if (!precioT.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "The price field should only contain numbers");
                        return;
                    } else if (!stockA.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "The current stock field should only contain numbers");
                        return;
                    }else if(!stockM.matches("\\d+")){
                        JOptionPane.showMessageDialog(null, "The minimum stock field should only contain numbers");
                        return;
                    }


                    int precio = Integer.parseInt(precioT);
                    int stock_actual = Integer.parseInt(stockA);
                    int stock_minimo = Integer.parseInt(stockM);


                    Products products = new Products(id_producto,id_categoria, nombre, descripcion, precio, stock_actual, stock_minimo, fecha_vencimiento, lote);
                    productsDAO.update(products);

                    clear();
                    showdata();
                }

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table1.getSelectedRow();


                if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() || textField4.getText().trim().isEmpty()|| textField5.getText().trim().isEmpty()
                        || textField6.getText().trim().isEmpty()|| textField7.getText().trim().isEmpty()|| textField8.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Complete all fields");

                }else if (selectedRow == -1) { // Si no hay fila seleccionada
                    JOptionPane.showMessageDialog(null, "Please, select a product to remove");
                } else{
                    int id_cliente = Integer.parseInt(textField1.getText());
                    productsDAO.delete(id_cliente);
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

                if(selectedRows >= 0)
                {
                    textField1.setText((String) table1.getValueAt(selectedRows,0));
                    textField2.setText((String) table1.getValueAt(selectedRows,2));
                    textField3.setText((String) table1.getValueAt(selectedRows,3));
                    textField4.setText((String) table1.getValueAt(selectedRows,4));
                    textField5.setText((String) table1.getValueAt(selectedRows,5));
                    textField6.setText((String) table1.getValueAt(selectedRows,6));
                    textField7.setText((String) table1.getValueAt(selectedRows,7));
                    textField8.setText((String) table1.getValueAt(selectedRows,8));
                    String categoria = (String) table1.getValueAt(selectedRows, 1);  // estado
                    comboBox1.setSelectedItem(categoria);

                    rows = selectedRows;
                }

            }
        });
    }

    public void showdata()
    {
        NonEditableTableModel modeloa = new NonEditableTableModel();
        table1.setDefaultEditor(Object.class, null);


        modeloa.addColumn("id_product");
        modeloa.addColumn("Category");
        modeloa.addColumn("Product");
        modeloa.addColumn("Description");
        modeloa.addColumn("Price");
        modeloa.addColumn("Current Stock");
        modeloa.addColumn("Minimum Stock");
        modeloa.addColumn("Expiration Date");
        modeloa.addColumn("Lot");

        table1.setModel(modeloa);

        String[] dato = new String[9];

        Connection con = connectionDB.getConnection();

        try
        {

            String query = "SELECT p.id_producto, c.nombre_categoria, p.nombre, p.descripcion, " +
                    "p.precio, p.stock_actual, p.stock_minimo, p.fecha_vencimiento, p.lote " +
                    "FROM productos p " +
                    "JOIN categoria c ON p.id_categoria = c.id_categoria";

            PreparedStatement pstmt = con.prepareStatement(query);
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
                dato[7] = rs.getString(8);
                dato[8] = rs.getString(9);

                modeloa.addRow(dato);
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
        textField7.setText("");
        textField8.setText("");
    }

    public void updateComboBox() {
        // Limpiar todos los mapas
        categoryMap.clear();
        comboBox1.removeAllItems();

        Connection con = connectionDB.getConnection();
        try (Statement stmt = con.createStatement()) {
            // Productos
            ResultSet rs1 = stmt.executeQuery("SELECT id_categoria, nombre_categoria FROM categoria");
            while (rs1.next()) {
                categoryMap.put(rs1.getString("nombre_categoria"), rs1.getInt("id_categoria"));
                comboBox1.addItem(rs1.getString("nombre_categoria"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating data in the ComboBox", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void runProducts() {

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
