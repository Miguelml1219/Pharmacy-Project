package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.CustomerDAO;
import Pharmacy_Project.dao.OrderDAO;
import Pharmacy_Project.dao.Order_DetailDAO;
import Pharmacy_Project.dao.Financial_MovementsDAO;
import Pharmacy_Project.model.Financial_Movements;
import Pharmacy_Project.model.Order;
import Pharmacy_Project.model.Order_Detail;
import Pharmacy_Project.utils.QRGenerator;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que gestiona la interfaz de detalles de pedido en la farmacia.
 */

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
    private Financial_MovementsDAO financial_movementsDAO = new Financial_MovementsDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private ConnectionDB connectionDB = new ConnectionDB();
    private Map<String, Integer> customerMap = new HashMap<>();
    private Map<String, Integer> productMap = new HashMap<>();

    int rows=0;

    /**
     * Constructor de la interfaz de detalles de pedido.
     * @param parentFrame La ventana padre de la interfaz.
     */

    public Order_DetailGUI(JFrame parentFrame) {

        createOrderButton.setFont(new java.awt.Font("Marlett Non-latin", java.awt.Font.BOLD, 16));
        deleteOrderButton.setFont(new java.awt.Font("Marlett Non-latin", java.awt.Font.BOLD, 16));
        addProductButton.setFont(new java.awt.Font("Marlett Non-latin", java.awt.Font.BOLD, 16));
        changeProductButton.setFont(new java.awt.Font("Marlett Non-latin", java.awt.Font.BOLD, 16));
        deleteProductButton.setFont(new java.awt.Font("Marlett Non-latin", java.awt.Font.BOLD, 16));
        BackButton.setFont(new java.awt.Font("Marlett Non-latin", java.awt.Font.BOLD, 16));
        placeOrderButton.setFont(new java.awt.Font("Marlett Non-latin", java.awt.Font.BOLD, 16));

        createOrderButton.setBackground(new Color(160, 208, 160)); // Verde más claro al pasar el mouse
        createOrderButton.setForeground(Color.WHITE); // Texto en blanco
        createOrderButton.setBorder(BorderFactory.createLineBorder(new Color(96, 160, 96), 3)); // Borde verde oscuro

        deleteOrderButton.setBackground(new Color(255, 102, 102)); // Rojo base
        deleteOrderButton.setForeground(Color.WHITE); // Texto en blanco
        deleteOrderButton.setBorder(BorderFactory.createLineBorder(new Color(176, 32, 48), 3)); // Borde rojo oscuro

        addProductButton.setBackground(new Color(0, 200, 0)); // Verde base
        addProductButton.setForeground(Color.WHITE); // Texto en blanco
        addProductButton.setBorder(BorderFactory.createLineBorder(new Color(96, 160, 96), 3)); // Borde verde oscuro

        changeProductButton.setBackground(new Color(255, 193, 7)); // Verde base
        changeProductButton.setForeground(Color.WHITE); // Texto en blanco
        changeProductButton.setBorder(BorderFactory.createLineBorder(new Color(153, 115, 0), 3)); // Borde verde oscuro

        deleteProductButton.setBackground(new Color(255, 102, 102)); // Verde base
        deleteProductButton.setForeground(Color.WHITE); // Texto en blanco
        deleteProductButton.setBorder(BorderFactory.createLineBorder(new Color(176, 32, 48), 3)); // Borde verde oscuro

        BackButton.setBackground(new Color(41,171,226)); // Verde base
        BackButton.setForeground(Color.WHITE); // Texto en blanco
        BackButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde verde oscuro

        placeOrderButton.setBackground(new Color(0, 200, 0)); // Verde base
        placeOrderButton.setForeground(Color.WHITE); // Texto en blanco
        placeOrderButton.setBorder(BorderFactory.createLineBorder(new Color(96, 160, 96), 3)); // Borde verde oscuro




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

        //Agrega eventos a los botones y componentes interactivos.


        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = order_detailDAO.obtenerID();

                String selectedName = (String) comboBox2.getSelectedItem();

                int id_cliente = customerMap.get(selectedName);

                Timestamp fecha = Timestamp.valueOf(textField2.getText());


                Order order = new Order(0, id_cliente, fecha, 0, "", "Preparación");
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

        createOrderButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                createOrderButton.setBackground(new Color(120, 190, 120)); // Verde más intenso al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                createOrderButton.setBackground(new Color(160, 208, 160)); // Restaurar color base
            }
        });

        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table1.getRowCount()>0){
                    JOptionPane.showMessageDialog(null, "Primero elimina un Producto", "Alert", JOptionPane.WARNING_MESSAGE);
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

        deleteOrderButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteOrderButton.setBackground(new Color(220, 53, 69)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deleteOrderButton.setBackground(new Color(255, 102, 102)); // Restaurar color base
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
                    JOptionPane.showMessageDialog(null, "Completa el campo Cantidad");
                }else {
                    clearTable();
                    int id_producto = productMap.get(comboBox3.getSelectedItem().toString());
                    int cantidad = Integer.parseInt(textField4.getText());
                    String und_medida = (String) comboBox4.getSelectedItem();

                    textField5.setText("Preparación");

                    String cantidadT = textField1.getText();

                    if (!cantidadT.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "El campo Télefono solo debe contener nuúmeros");
                        return;
                    }

                    if (!order_detailDAO.verificarStockDisponible(id_producto, cantidad)) {
                        JOptionPane.showMessageDialog(null, "No hay suficiente inventario para el producto!", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Bloquea la adición del producto si no hay stock suficiente
                    }

                    clearTable();
                    int id = order_detailDAO.obtenerID();
                    int precio_unitario = order_detailDAO.obtainPriceProduct(id_producto);
//                    int subtotal = cantidad * precio_unitario;

                    int subtotal;
                    if (und_medida.equals("Blister")) {
                        subtotal = cantidad * precio_unitario * 10; // Un blister tiene 10 unidades
                    } else if (und_medida.equals("Caja")) {
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

        addProductButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addProductButton.setBackground(new Color(160, 208, 160)); // Verde más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addProductButton.setBackground(new Color(0, 200, 0)); // Restaurar color base
            }
        });


        changeProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField4.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Completa el campo Cantidad");
                }else {

                    int id_detalle =Integer.parseInt(textField7.getText());
                    int id_producto = productMap.get(comboBox3.getSelectedItem().toString());
                    int cantidad = Integer.parseInt(textField4.getText());
                    String und_medida = (String) comboBox4.getSelectedItem();

                    textField5.setText("Preparación");

                    String cantidadT = textField1.getText();

                    if (!cantidadT.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "El campo Télefono solo debe contener números");
                        return;
                    }

                    clearTable();
                    int id = order_detailDAO.obtenerID();
                    int precio_unitario = order_detailDAO.obtainPriceProduct(id_producto);
//                    int subtotal = cantidad * precio_unitario;

                    int subtotal;
                    if (und_medida.equals("Blister")) {
                        subtotal = cantidad * precio_unitario * 10; // Un blister tiene 10 unidades
                    } else if (und_medida.equals("Caja")) {
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

        changeProductButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                changeProductButton.setBackground(new Color(255, 193, 7)); // Amarillo más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                changeProductButton.setBackground(new Color(255, 193, 7)); // Restaurar color base
            }
        });

        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table1.getSelectedRow();

                if (textField4.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Completa el campo Cantidad");
                }else if (selectedRow == -1) { // Si no hay fila seleccionada
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un producto para eliminar");
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

        deleteProductButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteProductButton.setBackground(new Color(220, 53, 69)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deleteProductButton.setBackground(new Color(255, 102, 102)); // Restaurar color base
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
                    JOptionPane.showMessageDialog(null, "Por favor ingresa una cantidad");
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

                    Order order = new Order(currentId, 0, fecha, total, metodo, "Enviado");
                    orderDAO.upOrder(order);

                    // Generar factura y obtener la ruta
                    String filePath = generarFacturaPDF();

                    if (filePath != null) {
                        // Obtener el correo del cliente desde la base de datos
                        String emailCliente = customerDAO.obtenerCorreo(comboBox2.getSelectedItem().toString());
                        String customerName = comboBox2.getSelectedItem().toString();


                        if (emailCliente != null && !emailCliente.isEmpty()) {
                            customerDAO.enviarFacturaPorCorreo(filePath, customerName,emailCliente);
                            //generarFacturaPDF();

                        } else {
                            JOptionPane.showMessageDialog(null, "Email not found");
                        }
                    }

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

            //Genera la factura de la orden

            public String generarFacturaPDF() {
                try {
                    Document document = new Document();
                    String filePath = "Factura.pdf";
                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
                    document.open();

                    // Cargar la imagen de fondo
                    String imagePath = "src/Pharmacy_Project/utils/diseñofactura.png";
                    Image background = Image.getInstance(imagePath);
                    background.setAbsolutePosition(0, 0);
                    background.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight());

                    // Agregar imagen de fondo al documento
                    PdfContentByte canvas = writer.getDirectContentUnder();
                    canvas.addImage(background);

                    document.add(new Paragraph(" "));
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph("Factura de Venta", new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD)));
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph("Número de Pedido: " + textField1.getText()));
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph("Fecha: " + textField2.getText()));
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph("Cliente: " + comboBox2.getSelectedItem().toString()));
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph("Categoria: " + textField3.getText()));
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph("Método de Pago: " + comboBox1.getSelectedItem().toString()));
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph(" "));

                    PdfPTable table = new PdfPTable(5);
                    table.setWidthPercentage(100);
                    table.setWidths(new float[]{3, 2, 1, 2, 2});
                    table.addCell("Producto");
                    table.addCell("Presentación");
                    table.addCell("Cantidad");
                    table.addCell("Precio");
                    table.addCell("Subtotal");

                    for (int i = 0; i < table1.getRowCount(); i++) {
                        table.addCell(table1.getValueAt(i, 2).toString());
                        table.addCell(table1.getValueAt(i, 3).toString());
                        table.addCell(table1.getValueAt(i, 4).toString());
                        table.addCell(table1.getValueAt(i, 5).toString());
                        table.addCell(table1.getValueAt(i, 6).toString());
                    }

                    document.add(table);
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph("Total + IVA: " + textField6.getText()));
                    document.close();

                    //JOptionPane.showMessageDialog(null, "Invoice generated and saved correctly: " + filePath);


                    Desktop.getDesktop().open(new File(filePath));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }


        });

        placeOrderButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                placeOrderButton.setBackground(new Color(160, 208, 160)); // Verde más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                placeOrderButton.setBackground(new Color(0, 200, 0)); // Restaurar color base
            }
        });



        comboBox1.addActionListener(e ->  {

            String metodoSeleccionado = (String) comboBox1.getSelectedItem();

            if (metodoSeleccionado.equals("Transferencia")) {

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
     * Metodo para actualizar la categoria dependiendo del client elegido.
     */


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

    /**
     * Metodo para actualizar el total de la orden.
     */

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
            case "Cliente Frecuente":
                discount = (int)(sum * 0.5);
                break;
            case "Cliente Mayor":
                discount = (int)(sum * 0.4);
                break;
            case "Cliente Regular":
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

    /**
     * Muestra los detalles del pedido en la tabla.
     */

    public void showdata()
    {
        NonEditableTableModel modelo = new NonEditableTableModel();

        modelo.addColumn("id_detalle");
        modelo.addColumn("id_pedido");
        modelo.addColumn("Producto");
        modelo.addColumn("Presentación");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
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


    /**
     * Clase interna que define un modelo de tabla no editable.
     */

    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }


    /**
     * Clase para limpiar las filas de la tabla al iniciar la pantalla.
     */

    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Elimina todas las filas
    }

    /**
     * Clase para limpiar los campos.
     */


    public void clear()
    {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
    }

    /**
     * Clase para actualizar el comboBox de clientes y productos.
     */


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

    /**
     * Método que inicializa y muestra la ventana de productos con bajo stock.
     */

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

                int option = JOptionPane.showConfirmDialog(frame, "¿Estas seguro que quieres salir?\nCualquier operación que estes haciendo y no hayas guardado se perdera.","Confirmar Salida",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
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
