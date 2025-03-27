package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.dao.Financial_MovementsDAO;
import Pharmacy_Project.model.Financial_Movements;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.RowFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Clase Financial_MovementsGUI
 *
 * Esta clase proporciona una interfaz gráfica para gestionar los movimientos financieros dentro del sistema de farmacia.
 * Permite agregar, actualizar, eliminar y visualizar movimientos financieros, así como generar informes en formato PDF.
 */

public class Financial_MovementsGUI {

    private JFrame frame;
    private JFrame parentFrame;
    private JPanel main;
    private JButton BackButton;
    private JTable table1;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JTextField textField2;
    private JComboBox comboBox4;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField search;
    private JButton downloadPDFButton;
    private ConnectionDB connectionDB = new ConnectionDB();
    private Financial_MovementsDAO financial_movementsDAO = new Financial_MovementsDAO();
    private TableRowSorter<DefaultTableModel> sorter;
    private CustomerGUI.NonEditableTableModel modelo;
    int rows = 0;

    /**
     * Constructor de la clase.
     *
     * @param parentFrame La ventana principal desde donde se abre esta interfaz.
     */

    public Financial_MovementsGUI(JFrame parentFrame)
    {
        addButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        updateButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        deleteButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        BackButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        downloadPDFButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 14));

        addButton.setBackground(new Color(0, 200, 0)); // Verde base
        addButton.setForeground(Color.WHITE); // Texto en blanco
        addButton.setBorder(BorderFactory.createLineBorder(new Color(96, 160, 96), 3)); // Borde verde oscuro

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




        textField4.setEditable(false);
        textField3.setEditable(false);
        comboBox1.setVisible(false);
        textField1.setVisible(false);

        sorter = new TableRowSorter<>(modelo);
        table1.setRowSorter(sorter);
        showdata();

        this.parentFrame = parentFrame;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        textField4.setText(now.format(formatter));

        comboBox3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateComboBox3();
                }
            }
        });

        // Listener para comboBox2 (Ingresos)
        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateDescriptionIncomes();
                }
            }
        });

        // Listener para comboBox1 (Gastos)
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateDescriptionExpenses();
                }
            }
        });

        // Llamar a la función para establecer la UI y la descripción correctas al inicio
        updateComboBox3();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(textField2.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Complete quantity field");

                }else{

                    String tipo = (String) comboBox3.getSelectedItem();
                    String categoria;
                    if (comboBox2.isVisible()) {
                        categoria = (String) comboBox2.getSelectedItem();
                    } else {
                        categoria = (String) comboBox1.getSelectedItem();
                    }
                    int monto = Integer.parseInt(textField2.getText());
                    Timestamp fecha = Timestamp.valueOf(textField4.getText());
                    String descripcion = textField3.getText();
                    String metodo = (String) comboBox4.getSelectedItem();

                    Financial_Movements financial_movements = new Financial_Movements(0,tipo,categoria,monto,fecha,descripcion,metodo);
                    financial_movementsDAO.add(financial_movements);
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

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table1.getSelectedRow();

                if (selectedRow == -1) { // Si no hay fila seleccionada
                    JOptionPane.showMessageDialog(null, "Please, select a movement to update");
                }
                if(textField2.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Complete quantity field");

                }else{

                    int id_movimiento = Integer.parseInt(textField1.getText());
                    String tipo = (String) comboBox3.getSelectedItem();
                    String categoria;
                    if (comboBox2.isVisible()) {
                        categoria = (String) comboBox2.getSelectedItem();
                    } else {
                        categoria = (String) comboBox1.getSelectedItem();
                    }
                    int monto = Integer.parseInt(textField2.getText());
                    Timestamp fecha = Timestamp.valueOf(textField4.getText());
                    String descripcion = textField3.getText();
                    String metodo = (String) comboBox4.getSelectedItem();

                    Financial_Movements financial_movements = new Financial_Movements(id_movimiento,tipo,categoria,monto,fecha,descripcion,metodo);
                    financial_movementsDAO.update(financial_movements);
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

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table1.getSelectedRow();

                if (selectedRow == -1) { // Si no hay fila seleccionada
                    JOptionPane.showMessageDialog(null, "Please, select a movement to remove");
                } else {

                    int id_movement = Integer.parseInt(table1.getValueAt(selectedRow,0).toString());
                    String tipo_movimiento = table1.getValueAt(selectedRow,1).toString();
                    int monto = Integer.parseInt(table1.getValueAt(selectedRow,3).toString());
                    String metodo_pago = table1.getValueAt(selectedRow,6).toString();

                    financial_movementsDAO.delete(id_movement,metodo_pago,monto,tipo_movimiento);
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

                if(selectedRows>=0)
                {
                    textField1.setText((String)table1.getValueAt(selectedRows,0));
                    textField2.setText((String)table1.getValueAt(selectedRows,3));
                    textField3.setText((String)table1.getValueAt(selectedRows,5));
                    String categoria = (String)table1.getValueAt(selectedRows,2);
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
     * Método para generar un informe en formato PDF con los movimientos financieros registrados.
     */


    public void generatePDF(){
        Document documento = new Document(PageSize.A4);

        try {
            String filePath = "Factura_Movimiento.pdf";
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

            Paragraph titulo = new Paragraph("Financial Movements",
                    FontFactory.getFont("Tahoma", 22, java.awt.Font.BOLD, BaseColor.BLUE));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph("\n\n"));

            PdfPTable tabla = new PdfPTable(7);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);

            String[] headers = {"Id movement", "Type movement", "Category", "Amount", "Date", "Description", "Method payment"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header,
                        FontFactory.getFont("Tahoma", 12, java.awt.Font.BOLD, BaseColor.WHITE)));
                cell.setBackgroundColor(BaseColor.BLUE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
            }

            try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/farmacia", "root", "");
                 PreparedStatement pst = cn.prepareStatement("SELECT * FROM movimientos_financieros");
                 ResultSet rs = pst.executeQuery()) {

                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "No movements were found.");
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

            JOptionPane.showMessageDialog(null, "PDF successfully generated.");

            Desktop.getDesktop().open(new File(filePath));


        } catch (DocumentException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Error generating the PDF: " + ex.getMessage());
        }

    }


    /**
     * Método para actualizar la informacion/descripcion de un movimientos.
     */


    public void updateComboBox3() {
        String selectedItem = (String) comboBox3.getSelectedItem();
        if (selectedItem.equals("Income")) {
            comboBox2.setVisible(true);
            comboBox2.setEnabled(true);
            comboBox1.setVisible(false);
            updateDescriptionIncomes(); // Establecer descripción inicial de Income
        } else if (selectedItem.equals("Expenses")) {
            comboBox1.setVisible(true);
            comboBox1.setEnabled(true);
            comboBox2.setVisible(false);
            updateDescriptionExpenses(); // Establecer descripción inicial de Expenses
        }
    }

    public void updateDescriptionIncomes() {
        String selectedItem = (String) comboBox2.getSelectedItem();
        if (selectedItem != null) {
            switch (selectedItem) {
                case "Sales returns":
                    textField3.setText("A sale is refunded due to a product return.");
                    break;
                case "Grants and subsidies":
                    textField3.setText("External funding is received without repayment.");
                    break;
                case "Commissions received":
                    textField3.setText("Money is earned for mediating sales or services.");
                    break;
                default:
                    textField3.setText("");
                    break;
            }
        }
    }

    // Método para actualizar la descripción desde comboBox1 (Expenses)
    private void updateDescriptionExpenses() {
        String selectedItem = (String) comboBox1.getSelectedItem();
        if (selectedItem != null) {
            switch (selectedItem) {
                case "Rent":
                    textField3.setText("Payment is made for the use of a space or property.");
                    break;
                case "Public services":
                    textField3.setText("Expenses for water, electricity, gas, and internet are covered.");
                    break;
                case "Purchase of medicines and supplies":
                    textField3.setText("Products are acquired to stock the business.");
                    break;
                default:
                    textField3.setText("");
                    break;
            }
        }
    }

    /**
     * Método que muestra los datos de los movimientos financieros en la tabla.
     */

    public void showdata() {

        if (sorter != null) {
            table1.setRowSorter(null);
        }

        Financial_MovementsGUI.NonEditableTableModel modelo = new Financial_MovementsGUI.NonEditableTableModel();

        modelo.addColumn("Id_Movement");
        modelo.addColumn("Type Movement");
        modelo.addColumn("Category");
        modelo.addColumn("Amount");
        modelo.addColumn("Date");
        modelo.addColumn("Description");
        modelo.addColumn("Method Payment");

        table1.setModel(modelo);

        String[] dato = new String[7];
        Connection con = connectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM movimientos_financieros");

            while (rs.next()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para limpiar los campos de entrada.
     */

    public void clear(){
        textField1.setText("");
        textField2.setText("");
    }

    /**
     * Clase interna para definir un modelo de tabla no editable.
     */

    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    /**
     * Método para ejecutar la ventana de gestión de movimientos financieros.
     */

    public void runMovements() {

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


