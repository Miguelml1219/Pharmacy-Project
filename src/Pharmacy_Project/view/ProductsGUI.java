package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.ProductsDAO;
import Pharmacy_Project.model.Products;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.RowFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * Clase ProductsGUI gestiona la interfaz de usuario para agregar los productos de la farmacia.
 * Permite visualizar, buscar y actualizar los productos.
 */


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
    private JTextField search;
    private JButton downloadPDFButton;
    private JFrame frame;
    private JFrame parentFrame;
    private ProductsDAO productsDAO = new ProductsDAO();
    private ConnectionDB connectionDB = new ConnectionDB();
    private Map<String, Integer> categoryMap = new HashMap<>();
    private TableRowSorter<DefaultTableModel> sorter;
    private CustomerGUI.NonEditableTableModel modeloa;


    /**
     * Constructor de la clase ProductsGUI.
     * @param parentFrame Marco padre desde donde se abre esta ventana.
     */

    int rows = 0;

    public ProductsGUI(JFrame parentFrame) {

        addButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        updatedButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        deleteButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        BackButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        downloadPDFButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 14));

        addButton.setBackground(new Color(0, 200, 0)); // Verde base
        addButton.setForeground(Color.WHITE); // Texto en blanco
        addButton.setBorder(BorderFactory.createLineBorder(new Color(96, 160, 96), 3)); // Borde verde oscuro

        updatedButton.setBackground(new Color(211, 158, 0)); // Amarillo oscuro base
        updatedButton.setForeground(Color.WHITE); // Texto en blanco
        updatedButton.setBorder(BorderFactory.createLineBorder(new Color(153, 115, 0), 3)); // Borde amarillo más oscuro


        deleteButton.setBackground(new Color(220, 53, 69)); // Rojo base
        deleteButton.setForeground(Color.WHITE); // Texto en blanco
        deleteButton.setBorder(BorderFactory.createLineBorder(new Color(176, 32, 48), 3)); // Borde rojo oscuro

        BackButton.setBackground(new Color(41,171,226)); // Verde base
        BackButton.setForeground(Color.WHITE); // Texto en blanco
        BackButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde verde oscuro

        downloadPDFButton.setBackground(new Color(255, 102, 102)); // Azul base
        downloadPDFButton.setForeground(Color.WHITE); // Texto en blanco
        downloadPDFButton.setBorder(BorderFactory.createLineBorder(new Color(139, 0, 0), 3)); // Borde azul oscuro

        textField1.setVisible(false);
        this.parentFrame = parentFrame;
        sorter = new TableRowSorter<>(modeloa);
        table1.setRowSorter(sorter);
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

                        LocalDate fechaMinima = LocalDate.of(2026,3,1);

                        if(fechaValidada.isBefore(fechaMinima))
                        {
                            JOptionPane.showMessageDialog(null, "The date must be from March 2026 onwards.");
                            return;
                        }

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

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addButton.setBackground(new Color(160, 208, 160)); // Verde más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addButton.setBackground(new Color(0, 200, 0)); // Restaurar color base
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

        updatedButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                updatedButton.setBackground(new Color(255, 193, 7)); // Amarillo más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                updatedButton.setBackground(new Color(211, 158, 0)); // Restaurar color base
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

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteButton.setBackground(new Color(255, 102, 102)); // Rojo más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deleteButton.setBackground(new Color(220, 53, 69)); // Restaurar color base
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

        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = search.getText().trim().toLowerCase();

                if (sorter != null) {
                    // Filtro que busca en todas las columnas
                    RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
                        @Override
                        public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                            for (int i = 0; i < entry.getValueCount(); i++) {
                                if (entry.getStringValue(i).toLowerCase().contains(searchText)) {
                                    return true;
                                }
                            }
                            return false;
                        }
                    };

                    sorter.setRowFilter(filter);
                }
            }
        });

        downloadPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePDF();
            }
        });

        downloadPDFButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                downloadPDFButton.setBackground(new Color(220, 53, 69)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                downloadPDFButton.setBackground(new Color(255, 102, 102)); // Restaurar color base
            }
        });

    }

    public void generatePDF(){
        Document documento = new Document(PageSize.A4);

        try {
            String filePath = "Factura_Productos.pdf";
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(filePath));

            documento.open();

            String imagePath = "src/Pharmacy_Project/utils/plantilla.jpeg";
            File imgFile = new File(imagePath);
            if (!imgFile.exists()) {
                JOptionPane.showMessageDialog(null, "Error: Background image not found.");
                return;
            }

            com.itextpdf.text.Image background = com.itextpdf.text.Image.getInstance(imagePath);
            background.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            background.setAbsolutePosition(0, 0);

            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(background);


            documento.add(new Paragraph("\n\n\n"));
            documento.add(new Paragraph("\n\n\n"));

            Paragraph titulo = new Paragraph("Products",
                    FontFactory.getFont("Tahoma", 22, java.awt.Font.BOLD, BaseColor.BLUE));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph("\n\n"));

            PdfPTable tabla = new PdfPTable(9);
            tabla.setWidthPercentage(110);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);

            String[] headers = {"Id product", "Id category", "Name", "Description", "Price", "Current Stock", "Minimum Stock", "Expiration Date", "Lot"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header,
                        FontFactory.getFont("Tahoma", 12, java.awt.Font.BOLD, BaseColor.WHITE)));
                cell.setBackgroundColor(BaseColor.BLUE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
            }

            try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/farmacia", "root", "");
                 PreparedStatement pst = cn.prepareStatement("SELECT * FROM productos");
                 ResultSet rs = pst.executeQuery()) {

                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "No products were found.");
                } else {
                    while (rs.next()) {
                        for (int i = 1; i <= 9; i++) {
                            tabla.addCell(rs.getString(i));
                        }
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error in the database: " + ex.getMessage());
            }

            documento.add(tabla);
            documento.close();

            JOptionPane.showMessageDialog(null, "PDF successfully generated.");

            Desktop.getDesktop().open(new File(filePath));


        } catch (DocumentException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Error generating the PDF: " + ex.getMessage());
        }

    }

    /**
     * Muestra los datos de los pedidos en la tabla.
     */

    public void showdata()
    {
        if (sorter != null) {
            table1.setRowSorter(null);
        }

        NonEditableTableModel modeloa = new NonEditableTableModel();

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

            sorter = new TableRowSorter<>(modeloa);
            table1.setRowSorter(sorter);

            // Restablecer el filtro de búsqueda si hay texto
            if (!search.getText().trim().isEmpty()) {
                search.setText(search.getText());
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Clase para limpiar las filas de la tabla al iniciar la pantalla.
     */


    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    /**
     * Clase para limpiar campos.
     */

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

    /**
     * Clase para actualizar la categoria de los productos.
     */


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

    /**
     * Inicia la ventana de gestión de productos.
     */



    public void runProducts() {

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
