package Pharmacy_Project.view;
import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.CustomerDAO;
import Pharmacy_Project.model.Customer;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;


/**
 * Clase que representa la interfaz gráfica para la gestión de clientes.
 */
public class CustomerGUI {
    private JPanel main;
    private JButton agregarButton, eliminarButton, actualizarButton;
    private JTextField textField1, textField2, textField3, textField4, textField5;
    private JComboBox<String> comboBoxCategoria;
    private JTable Tabla;
    private JButton generatePDFButton;
    private CustomerDAO customerDAO = new CustomerDAO();
    private ConnectionDB connectionDB = new ConnectionDB();

    /**
     * Constructor de la clase CustomerGUI. Inicializa la interfaz y carga los datos de los clientes.
     */
    public CustomerGUI() {
        obtenerClientes();

        agregarButton.addActionListener(e -> {
            agregarCliente();
            obtenerClientes();
        });
        eliminarButton.addActionListener(e -> {
            eliminarCliente();
            obtenerClientes();
        });
        actualizarButton.addActionListener(e -> {
            actualizarCliente();
            obtenerClientes();
        });

        Tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int seleccion = Tabla.getSelectedRow();
                if (seleccion >= 0) {
                    textField1.setText(Tabla.getValueAt(seleccion, 1).toString());
                    textField2.setText(Tabla.getValueAt(seleccion, 2).toString());
                    textField3.setText(Tabla.getValueAt(seleccion, 3).toString());
                    textField4.setText(Tabla.getValueAt(seleccion, 4).toString());
                    textField5.setText(Tabla.getValueAt(seleccion, 5).toString());
                    comboBoxCategoria.setSelectedItem(Tabla.getValueAt(seleccion, 6).toString());
                }
            }
        });

        /**
         * Genera un archivo PDF con la lista de clientes registrados.
         */
        generatePDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Document documento = new Document(PageSize.A4);

                try {
                    String ruta = System.getProperty("user.home") + "/Desktop/clientes.pdf";
                    PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(ruta));

                    documento.open();

                    String imagePath = "src/imagenes/fondo.png";
                    File imgFile = new File(imagePath);
                    if (!imgFile.exists()) {
                        JOptionPane.showMessageDialog(null, "Error: No se encontró la imagen de fondo.");
                        return;
                    }

                    Image background = Image.getInstance(imagePath);
                    background.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
                    background.setAbsolutePosition(0, 0);

                    PdfContentByte canvas = writer.getDirectContentUnder();
                    canvas.addImage(background);

                    Paragraph titulo = new Paragraph("Clientes Registrados",
                            FontFactory.getFont("Tahoma", 22, Font.BOLD, BaseColor.BLUE));
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    documento.add(titulo);
                    documento.add(new Paragraph("\n\n"));

                    PdfPTable tabla = new PdfPTable(7);
                    tabla.setWidthPercentage(100);
                    tabla.setSpacingBefore(10f);
                    tabla.setSpacingAfter(10f);

                    String[] headers = {"ID", "Documento", "Nombre", "Teléfono", "Correo", "Dirección", "Categoría"};
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
                            JOptionPane.showMessageDialog(null, "No se encontraron clientes.");
                        } else {
                            while (rs.next()) {
                                for (int i = 1; i <= 7; i++) {
                                    tabla.addCell(rs.getString(i));
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error en la base de datos: " + ex.getMessage());
                    }

                    documento.add(tabla);
                    documento.close();

                    JOptionPane.showMessageDialog(null, "PDF generado correctamente en el escritorio.");

                } catch (DocumentException | IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + ex.getMessage());
                }

            }

        });
    }

    /**
     * Obtiene los datos de los clientes desde la base de datos y los muestra en la tabla.
     */
    public void obtenerClientes() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Document");
        modelo.addColumn("Name");
        modelo.addColumn("Number");
        modelo.addColumn("Email");
        modelo.addColumn("Location");
        modelo.addColumn("Category");

        Tabla.setModel(modelo);
        String[] datos = new String[7];
        Connection con = connectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM clientes");

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                modelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(main, "Error when loading customers.");
        }
    }

    /**
     * Agrega un nuevo cliente a la base de datos con los datos ingresados en la interfaz.
     */
    private void agregarCliente() {
        String cedula = textField1.getText();
        String nombre = textField2.getText();
        String telefono = textField3.getText();
        String correo = textField4.getText();
        String direccion = textField5.getText();
        String categoria = comboBoxCategoria.getSelectedItem().toString();

        Customer customer = new Customer(0, cedula, nombre, telefono, correo, direccion, categoria);
        if (customerDAO.agregarCliente(customer)) {
            JOptionPane.showMessageDialog(main, "Customer successfully added.");
        } else {
            JOptionPane.showMessageDialog(main, "Error adding customer.");
        }
    }

    /**
     * Elimina el cliente seleccionado en la tabla de la base de datos.
     */
    private void eliminarCliente() {
        try {
            int selectedRow = Tabla.getSelectedRow();
            int id = Integer.parseInt(Tabla.getValueAt(selectedRow, 0).toString());
            if (customerDAO.eliminarCliente(id)) {
                JOptionPane.showMessageDialog(main, "Customer successfully deleted.");
            } else {
                JOptionPane.showMessageDialog(main, "Customer not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(main, "ID invalid.");
        }
    }

    /**
     * Actualiza el cliente seleccionado con los nuevos valores ingresados en la interfaz.
     */
    private void actualizarCliente() {
        int selectedRow = Tabla.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Select a customer to update");
            return;
        }

        try (Connection con = connectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE clientes SET cedula=?, nombre_completo=?, telefono=?, correo_electronico=?, direccion=?, categoria=? WHERE id_cliente=?")) {

            int id = Integer.parseInt(Tabla.getValueAt(selectedRow, 0).toString());

            ps.setString(1, textField1.getText());
            ps.setString(2, textField2.getText());
            ps.setString(3, textField3.getText());
            ps.setString(4, textField4.getText());
            ps.setString(5, textField5.getText());
            ps.setString(6, comboBoxCategoria.getSelectedItem().toString());
            ps.setInt(7, id);

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "Client successfully updated");
                obtenerClientes();
            } else {
                JOptionPane.showMessageDialog(null, "Client not found for update");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating client: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID invalid.");
        }
    }

    /**
     * Método principal para ejecutar la interfaz de gestión de clientes.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("CRUD CUSTOMERS");
        frame.setContentPane(new CustomerGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 500);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
