package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.OrderDAO;
import Pharmacy_Project.dao.Order_DetailDAO;
import Pharmacy_Project.model.Order;
import Pharmacy_Project.model.Order_Detail;
import Pharmacy_Project.utils.QRGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Order_DetailGUI {
    private JComboBox comboBox1;
    private JPanel main;
    private JButton BackButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JComboBox comboBox2;
    private JButton createOrderButton;
    private JButton deleteOrderButton;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JTextField textField4;
    private JButton addProductButton;
    private JButton changeProductButton;
    private JButton deleteProductButton;
    private JTable table1;
    private JTextField textField5;
    private JTextField textField6;
    private JButton placeOrderButton;
    private JTextField textField7;
    private JFrame frame;
    private JFrame parentFrame;
    private Order_DetailDAO order_detailDAO = new Order_DetailDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private ConnectionDB connectionDB = new ConnectionDB();
    private Map<String, Integer> customerMap = new HashMap<>();
    private Map<String, Integer> productMap = new HashMap<>();

    int rows=0;

    public Order_DetailGUI(JFrame parentFrame) {


        updateComboBox();
        updateCat(comboBox2.getSelectedItem().toString());

        textField1.setEditable(false);
        textField2.setEditable(false);
        textField3.setEditable(false);
        textField5.setEditable(false);
        textField6.setEditable(false);
        textField7.setVisible(false);
        comboBox3.setEnabled(false);
        comboBox4.setEnabled(false);
        comboBox1.setEnabled(false);
        textField4.setEditable(false);
        deleteOrderButton.setEnabled(false);
        addProductButton.setEnabled(false);
        changeProductButton.setEnabled(false);
        deleteProductButton.setEnabled(false);
        placeOrderButton.setEnabled(false);


        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        textField2.setText(now.format(formatter));
        this.parentFrame = parentFrame;
        showdata();


        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = order_detailDAO.obtenerID();

                String selectedName = (String) comboBox2.getSelectedItem();

                int id_cliente = customerMap.get(selectedName);

                Timestamp fecha = Timestamp.valueOf(textField2.getText());


                Order order = new Order(0, id_cliente, fecha, 0, "", "Preparation");
                orderDAO.addOrder(order);

                textField1.setText(String.valueOf(id+1));


                showdata();

                BackButton.setEnabled(false);
                textField1.setEditable(false);
                textField2.setEditable(false);
                textField3.setEditable(false);
                comboBox2.setEnabled(false);
                comboBox1.setEnabled(true);
                createOrderButton.setEnabled(false);

                comboBox3.setEnabled(true);
                comboBox4.setEnabled(true);
                textField4.setEnabled(true);
                deleteOrderButton.setEnabled(true);
                addProductButton.setEnabled(true);
                changeProductButton.setEnabled(true);
                deleteProductButton.setEnabled(true);
                placeOrderButton.setEnabled(true);
                textField4.setEditable(true);

                clearTable();
            }
        });

        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table1.getRowCount()>0){
                    JOptionPane.showMessageDialog(null, "First delete the product", "Alert", JOptionPane.WARNING_MESSAGE);
                }else {
                    int currentId = order_detailDAO.obtenerID();
                    orderDAO.delete(currentId);

                    clearTable();

                    textField1.setEditable(false);
                    textField2.setEditable(false);
                    textField3.setEditable(false);
                    textField5.setEditable(false);
                    textField6.setEditable(false);
                    comboBox3.setEnabled(false);
                    comboBox4.setEnabled(false);
                    comboBox1.setEnabled(false);
                    textField4.setEnabled(false);
                    deleteOrderButton.setEnabled(false);
                    addProductButton.setEnabled(false);
                    changeProductButton.setEnabled(false);
                    deleteProductButton.setEnabled(false);
                    placeOrderButton.setEnabled(false);
                    textField1.setText("");

                    comboBox2.setEnabled(true);
                    createOrderButton.setEnabled(true);
                    BackButton.setEnabled(true);

                    showdata();
                    clearTable();

                }

            }
        });

        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED && comboBox2.getSelectedItem() != null) {
                    String clienteSeleccionado = comboBox2.getSelectedItem().toString();
                    updateCat(clienteSeleccionado);
                }
            }
        });

        clearTable();

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField4.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete quantity field");
                }else {
                    clearTable();
                    int id_producto = productMap.get(comboBox3.getSelectedItem().toString());
                    int cantidad = Integer.parseInt(textField4.getText());
                    String und_medida = (String) comboBox4.getSelectedItem();

                    textField5.setText("Preparation");

                    String cantidadT = textField1.getText();

                    if (!cantidadT.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "The field should only contain numbers");
                        return;
                    }

                    if (!order_detailDAO.verificarStockDisponible(id_producto, cantidad)) {
                        JOptionPane.showMessageDialog(null, "Not enough stock for this product!", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Bloquea la adición del producto si no hay stock suficiente
                    }

                    clearTable();
                    int id = order_detailDAO.obtenerID();
                    int precio_unitario = order_detailDAO.obtainPriceProduct(id_producto);
//                    int subtotal = cantidad * precio_unitario;

                    int subtotal;
                    if (und_medida.equals("Blister")) {
                        subtotal = cantidad * precio_unitario * 10; // Un blister tiene 10 unidades
                    } else if (und_medida.equals("Box")) {
                        subtotal = cantidad * precio_unitario * 20; // Una caja tiene 20 unidades
                    } else {
                        // Para presentación "Unidad" u otras
                        subtotal = cantidad * precio_unitario;
                    }


                    Order_Detail order_detail = new Order_Detail(0, id, id_producto, und_medida, cantidad, precio_unitario, subtotal);
                    order_detailDAO.add(order_detail);

                    clearTable();
                    showdata();
                    updateTotal();
                }



            }
        });
        changeProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField4.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete quantity field");
                }else {

                    int id_detalle =Integer.parseInt(textField7.getText());
                    int id_producto = productMap.get(comboBox3.getSelectedItem().toString());
                    int cantidad = Integer.parseInt(textField4.getText());
                    String und_medida = (String) comboBox4.getSelectedItem();

                    textField5.setText("Preparation");

                    String cantidadT = textField1.getText();

                    if (!cantidadT.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "The field should only contain numbers");
                        return;
                    }

                    clearTable();
                    int id = order_detailDAO.obtenerID();
                    int precio_unitario = order_detailDAO.obtainPriceProduct(id_producto);
//                    int subtotal = cantidad * precio_unitario;

                    int subtotal;
                    if (und_medida.equals("Blister")) {
                        subtotal = cantidad * precio_unitario * 10; // Un blister tiene 10 unidades
                    } else if (und_medida.equals("Box")) {
                        subtotal = cantidad * precio_unitario * 20; // Una caja tiene 20 unidades
                    } else {
                        // Para presentación "Unidad" u otras
                        subtotal = cantidad * precio_unitario;
                    }


                    Order_Detail order_detail = new Order_Detail(id_detalle, 0, id_producto, und_medida, cantidad, precio_unitario, subtotal);
                    order_detailDAO.update(order_detail);

                    clearTable();
                    showdata();
                    updateTotal();
                }

            }
        });
        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table1.getSelectedRow();

                if (textField4.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete quantity field");
                }else if (selectedRow == -1) { // Si no hay fila seleccionada
                    JOptionPane.showMessageDialog(null, "Please, select a product to remove");
                } else{
                    int id_detalle = Integer.parseInt(textField7.getText());
                    order_detailDAO.delete(id_detalle);

                    textField4.setText("");
                    textField5.setText("");
                    showdata();
                    updateTotal();
                }
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);

                int selectedRows = table1.getSelectedRow();

                if(selectedRows>=0)
                {
                    textField1.setText((String)table1.getValueAt(selectedRows,1));
                    textField4.setText((String)table1.getValueAt(selectedRows,4));
                    textField7.setText((String)table1.getValueAt(selectedRows,0));

                    rows = selectedRows;
                }
            }
        });

        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(textField4.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Please enter an amount");
                }else{

                    Timestamp fecha = Timestamp.valueOf(textField2.getText());
                    int total = Integer.parseInt(textField6.getText());
                    int currentId = order_detailDAO.obtenerID();
                    String metodo = (String) comboBox1.getSelectedItem();

                    for (int i = 0; i < table1.getRowCount(); i++) {
                        int idProducto = productMap.get(table1.getValueAt(i, 2).toString());
                        String presentacion = table1.getValueAt(i, 3).toString();
                        int cantidad = Integer.parseInt(table1.getValueAt(i, 4).toString());

                        order_detailDAO.actualizarStock(idProducto, presentacion, cantidad);
                    }

                    Order order = new Order(currentId, 0, fecha, total, metodo, "Sent");
                    orderDAO.upOrder(order);

                    clearTable();

                    textField1.setText("");
                    textField4.setText("");
                    textField5.setText("");
                    textField6.setText("");

                    BackButton.setEnabled(true);
                    createOrderButton.setEnabled(true);
                    comboBox2.setEnabled(true);

                    textField1.setEditable(false);
                    textField2.setEditable(false);
                    textField3.setEditable(false);
                    textField5.setEditable(false);
                    textField6.setEditable(false);
                    comboBox3.setEnabled(false);
                    comboBox4.setEnabled(false);
                    comboBox1.setEnabled(false);
                    textField4.setEnabled(false);
                    deleteOrderButton.setEnabled(false);
                    addProductButton.setEnabled(false);
                    changeProductButton.setEnabled(false);
                    deleteProductButton.setEnabled(false);
                    placeOrderButton.setEnabled(false);

                }





            }
        });


        comboBox1.addActionListener(e ->  {

            String metodoSeleccionado = (String) comboBox1.getSelectedItem();

            if (metodoSeleccionado.equals("Transfer")) {

                QRGenerator.showqr();
                //System.out.println("✅ Generated QR");
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

    public void updateCat(String cliente) {
        try {

            int idCliente = customerMap.get(cliente);

            try (Connection con = connectionDB.getConnection()) {
                String sql = "SELECT categoria FROM clientes WHERE id_cliente = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, idCliente);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    textField3.setText(rs.getString("categoria"));
                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    int totalt=0;

    public void updateTotal() {

        int sum = 0;
        int discount = 0;

        for (int i = 0; i < table1.getRowCount();i++)
        {
            sum += Integer.parseInt(table1.getValueAt(i, 6).toString());
        }

        String category = textField3.getText();
        switch (category)
        {
            case "Frequent Customer":
                discount = (int)(sum * 0.5);
                break;
            case "Senior Customer":
                discount = (int)(sum * 0.4);
                break;
            case "Regular Customer":
                discount = (int)(sum * 0.3);
                break;
            default:
                discount=0;
        }

        int subtotalwithDiscount = sum - discount;

        int iva = (int)(subtotalwithDiscount * 0.19);

        int totalFinal = subtotalwithDiscount + iva;

        // Mostrar el total final
        textField6.setText(String.valueOf(totalFinal));

    }


    public void showdata()
    {
        NonEditableTableModel modelo = new NonEditableTableModel();

        modelo.addColumn("id_detail");
        modelo.addColumn("id_order");
        modelo.addColumn("Product");
        modelo.addColumn("Presentation");
        modelo.addColumn("Amount");
        modelo.addColumn("Price");
        modelo.addColumn("Subtotal");

        table1.setModel(modelo);

        String[] dato = new String[7];
        Connection con = connectionDB.getConnection();

        try
        {
            table1.getColumnModel().getColumn(0).setMinWidth(0);
            table1.getColumnModel().getColumn(0).setMaxWidth(0);
            table1.getColumnModel().getColumn(0).setWidth(0);

            table1.getColumnModel().getColumn(1).setMinWidth(0);
            table1.getColumnModel().getColumn(1).setMaxWidth(0);
            table1.getColumnModel().getColumn(1).setWidth(0);

            int currentOrderId = order_detailDAO.obtenerID();

            //String query = "SELECT * FROM detalle_orden WHERE id_orden = ?";

            String query = "SELECT d.id_detalle, d.id_pedido, i.nombre, d.und_medida, d.cantidad, d.precio_unitario, d.subtotal " +
                    "FROM detalle_pedido d " +
                    "JOIN productos i ON d.id_producto = i.id_producto JOIN pedidos o ON d.id_pedido = o.id_pedido " +
                    "WHERE d.id_pedido = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, currentOrderId);
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

    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Elimina todas las filas
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

    public void updateComboBox() {
        // Limpiar todos los mapas
        customerMap.clear();
        productMap.clear();
        comboBox2.removeAllItems();
        comboBox3.removeAllItems();

        Connection con = connectionDB.getConnection();
        try (Statement stmt = con.createStatement()) {
            // Clientes
            ResultSet rs1 = stmt.executeQuery("SELECT id_cliente, nombre_completo FROM clientes");
            while (rs1.next()) {

                int clientId = rs1.getInt("id_cliente");
                String clientName = rs1.getString("nombre_completo");
                String displayText = clientId + " - " + clientName;

                // Guardar en el mapa con el formato de visualización como clave
                customerMap.put(displayText, clientId);

                // Añadir el texto de visualización al comboBox
                comboBox2.addItem(displayText);
            }

            ResultSet rs2 = stmt.executeQuery("SELECT id_producto, nombre FROM productos");
            while (rs2.next()) {
                productMap.put(rs2.getString("nombre"), rs2.getInt("id_producto"));
                comboBox3.addItem(rs2.getString("nombre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating data in the ComboBox", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }







    public void runOrder() {

        frame = new JFrame("Data Base Pharmacy");
        frame.setContentPane(this.main);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        // Agregar WindowListener para limpiar la tabla al cerrar
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?\nAny operations you are performing will be lost.","Confirm exit",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                if(option == JOptionPane.YES_OPTION)
                {
                    clearTable();
                    frame.dispose(); // Cierra la ventana
                    System.exit(0);
                }
            }
        });
    }

}
