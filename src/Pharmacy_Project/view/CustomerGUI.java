package Pharmacy_Project.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.awt.Font;


import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.CustomerDAO;
import Pharmacy_Project.model.Customer;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.RowFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Clase CustomerGUI que representa la interfaz gráfica para la gestión de clientes.
 * Permite registrar, actualizar, eliminar y buscar clientes en la base de datos.
 */

public class CustomerGUI {
    private JPanel main;
    private JButton registerButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTable table1;
    private JTextField textField4;
    private JButton BackButton;
    private JComboBox comboBox1;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField search;
    private JButton downloadPDFButton;
    private TableRowSorter<DefaultTableModel> sorter;
    private  NonEditableTableModel modelo;
    private JFrame frame;
    private JFrame parentFrame;
    private CustomerDAO customerDAO = new CustomerDAO();
    private ConnectionDB connectionDB = new ConnectionDB();


    int rows = 0;


    /**
     * Constructor de la clase CustomerGUI.
     * Configura la interfaz gráfica y los eventos de los botones.
     * @param parentFrame Marco padre desde donde se abre esta ventana.
     */

    public CustomerGUI(JFrame parentFrame) {

//        Font headerFont = new Font("Marlett Non-latin", Font.BOLD, 14); // Fuente Arial, Negrita, Tamaño 14
//        JTableHeader tableHeader = table1.getTableHeader();
//        tableHeader.setFont(headerFont);
//
        registerButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        updateButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        deleteButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        BackButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        downloadPDFButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));

        registerButton.setBackground(new Color(0, 200, 0)); // Verde base
        registerButton.setForeground(Color.WHITE); // Texto en blanco
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(96, 160, 96), 3)); // Borde verde oscuro

        updateButton.setBackground(new Color(211, 158, 0)); // Amarillo oscuro base
        updateButton.setForeground(Color.WHITE); // Texto en blanco
        updateButton.setBorder(BorderFactory.createLineBorder(new Color(153, 115, 0), 3)); // Borde amarillo más oscuro


        deleteButton.setBackground(new Color(220, 53, 69)); // Rojo base
        deleteButton.setForeground(Color.WHITE); // Texto en blanco
        deleteButton.setBorder(BorderFactory.createLineBorder(new Color(176, 32, 48), 3)); // Borde rojo oscuro

        BackButton.setBackground(new Color(41,171,226)); // Verde base
        BackButton.setForeground(Color.WHITE); // Texto en blanco
        BackButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde verde oscuro

        downloadPDFButton.setBackground(new Color(255, 102, 102)); // Azul base
        downloadPDFButton.setForeground(Color.WHITE); // Texto en blanco
        downloadPDFButton.setBorder(BorderFactory.createLineBorder(new Color(139, 0, 0), 3)); // Borde azul oscuro

        textField1.setEditable(false);
        textField1.setVisible(false);
//        showdata();
        sorter = new TableRowSorter<>(modelo);
        table1.setRowSorter(sorter);
        showdata();

        this.parentFrame = parentFrame;

        Dimension backButtonSize = new Dimension(86, 23);
        BackButton.setPreferredSize(backButtonSize);
        BackButton.setMinimumSize(backButtonSize);
        BackButton.setMaximumSize(backButtonSize);


        //Registra un nuevo cliente en la base de datos.

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() || textField4.getText().trim().isEmpty() || textField5.getText().trim().isEmpty() || textField6.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Completa todos los campos");
                } else {
                    String num_doc = textField6.getText();
                    String nombre = textField2.getText();
                    String telefono = textField3.getText();
                    String email = textField4.getText();
                    String direccion = textField5.getText();
                    String categoria = (String) comboBox1.getSelectedItem();

                    if (!num_doc.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "El campo Numero de Documento solo debe contener numeros");
                        return;
                    }
                    if (!telefono.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "El campo Numero de Teléfono solo debe contener numeros");
                        return;
                    }

                    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                    if (!email.matches(emailRegex)) {
                        JOptionPane.showMessageDialog(null, "Correo inválido. Introduzca un correo válido.");
                        return;
                    }

                    int numDoc = Integer.parseInt(num_doc);

                    boolean docExist = false;
                    boolean emailExist = false;

                    for (int i = 0; i < table1.getRowCount(); i++) {
                        int docExisting = Integer.parseInt(table1.getValueAt(i, 1).toString());
                        String emailExisting = table1.getValueAt(i, 4).toString();

                        if (docExisting == numDoc) {
                            docExist = true;
                        }

                        if (emailExisting.equalsIgnoreCase(email)) {
                            emailExist = true;
                        }
                    }

                    if (docExist) {
                        JOptionPane.showMessageDialog(null, "El Numero de Documento " + numDoc + " ya existe");
                        textField6.setText("");
                        return;
                    }

                    if (emailExist) {
                        JOptionPane.showMessageDialog(null, "El correo " + email + " ya está registrado");
                        textField4.setText("");
                        return;
                    }

                    Customer customer = new Customer(0, num_doc, nombre, telefono, email, direccion, categoria);
                    customerDAO.add(customer);
                    clear();
                    showdata();
                }
            }
        });

        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setBackground(new Color(160, 208, 160)); // Verde más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setBackground(new Color(0, 200, 0)); // Restaurar color base
            }
        });

        //Actualiza un nuevo cliente en la base de datos.

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() || textField4.getText().trim().isEmpty() || textField5.getText().trim().isEmpty() || textField6.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Completa todos los campos");
                } else {
                    String num_doc = textField6.getText();
                    String nombre = textField2.getText();
                    String telefono = textField3.getText();
                    String email = textField4.getText();
                    String direccion = textField5.getText();
                    String categoria = (String) comboBox1.getSelectedItem();

                    int id_cliente = Integer.parseInt(textField1.getText());

                    if (!num_doc.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "El campo Numero de Documento solo debe contener numeros");
                        return;
                    }
                    if (!telefono.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "El campo Numero de Teléfono solo debe contener numeros");
                        return;
                    }

                    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                    if (!email.matches(emailRegex)) {
                        JOptionPane.showMessageDialog(null, "Correo inválido. Introduzca un correo válido.");
                        return;
                    }

                    int numDoc = Integer.parseInt(num_doc);


                    Customer customer = new Customer(id_cliente, num_doc, nombre, telefono, email, direccion, categoria);
                    customerDAO.update(customer);
                    clear();
                    showdata();
                }

            }
        });

        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                updateButton.setBackground(new Color(255, 193, 7)); // Amarillo más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                updateButton.setBackground(new Color(211, 158, 0)); // Restaurar color base
            }
        });

        // Elimina un cliente seleccionado de la base de datos.

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table1.getSelectedRow();

                if (textField2.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() || textField4.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Completa todos los campos");
                } else if (selectedRow == -1) { // Si no hay fila seleccionada
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un cliente para eliminar");
                } else {
                    int id_cliente = Integer.parseInt(textField1.getText());
                    customerDAO.delete(id_cliente);

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
                if (parentFrame != null) {
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

                if (selectedRows >= 0) {
                    textField1.setText((String) table1.getValueAt(selectedRows, 0));
                    textField6.setText((String) table1.getValueAt(selectedRows, 1));
                    textField2.setText((String) table1.getValueAt(selectedRows, 2));
                    textField3.setText((String) table1.getValueAt(selectedRows, 3));
                    textField4.setText((String) table1.getValueAt(selectedRows, 4));
                    textField5.setText((String) table1.getValueAt(selectedRows, 5));
                    String categoria = (String) table1.getValueAt(selectedRows, 6);
                    comboBox1.setSelectedItem(categoria);

                    rows = selectedRows;
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

    }

    /**
     * Método para generar PDF de registro de clientes.
     */


    public void generatePDF(){
        Document documento = new Document(PageSize.A4);

        try {
            String filePath = "Factura_Cliente.pdf";
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(filePath));

            documento.open();

            String imagePath = "src/Pharmacy_Project/utils/plantilla.jpeg";
            File imgFile = new File(imagePath);
            if (!imgFile.exists()) {
                JOptionPane.showMessageDialog(null, "Error: Background image not found.");
                return;
            }

            Image background = Image.getInstance(imagePath);
            background.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            background.setAbsolutePosition(0, 0);

            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(background);


            documento.add(new Paragraph("\n\n\n"));
            documento.add(new Paragraph("\n\n\n"));

            Paragraph titulo = new Paragraph("Clientes Registrados",
                    FontFactory.getFont("Tahoma", 22, Font.BOLD, BaseColor.BLUE));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph("\n\n"));

            PdfPTable tabla = new PdfPTable(7);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);

            String[] headers = {"ID", "Documento", "Nombre", "Teléfono", "Correo", "Dirección", "Categoria"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header,
                        FontFactory.getFont("Tahoma", 12, Font.BOLD, BaseColor.WHITE)));
                cell.setBackgroundColor(BaseColor.BLUE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
            }

            try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/farmacia", "root", "");
                 PreparedStatement pst = cn.prepareStatement("SELECT * FROM clientes");
                 ResultSet rs = pst.executeQuery()) {

                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "No clientes encontrados.");
                } else {
                    while (rs.next()) {
                        for (int i = 1; i <= 7; i++) {
                            tabla.addCell(rs.getString(i));
                        }
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error in the database: " + ex.getMessage());
            }

            documento.add(tabla);
            documento.close();

            JOptionPane.showMessageDialog(null, "PDF generado exitosamente.");

            Desktop.getDesktop().open(new File(filePath));


        } catch (DocumentException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Error generating the PDF: " + ex.getMessage());
        }

    }

    /**
     * Muestra los datos de los clientes en la tabla.
     */


    public void showdata()
    {

        // Eliminar el sorter anterior si existe
        if (sorter != null) {
            table1.setRowSorter(null);
        }

        NonEditableTableModel modelo = new NonEditableTableModel();

        modelo.addColumn("Id_Cliente");
        modelo.addColumn("Num_Documento");
        modelo.addColumn("Nombre Completo");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Correo");
        modelo.addColumn("Dirección");
        modelo.addColumn("Categoria");

        table1.setModel(modelo);

        String[] dato = new String[7];
        Connection con = connectionDB.getConnection();

        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM clientes");

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

            sorter = new TableRowSorter<>(modelo);
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
     * Clase para definir un modelo de tabla no editable.
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
    }

    /**
     * Método para inicializar y mostrar la ventana de gestión de clientes.
     */

    public void runCustomer() {

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
