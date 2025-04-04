package Pharmacy_Project.view;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.RowFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


import Pharmacy_Project.dao.CategoryDAO;


/**
 * Clase que gestiona la interfaz gráfica para la administración de categorías.
 */

public class CategoryGUI {
    private JPanel main;
    private JTable table1;
    private JTextField textField1;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField textField2;
    private JButton BackButton;
    private JTextField search;
    private JFrame frame;
    private JFrame parentFrame;
    private CategoryDAO categoryDAO = new CategoryDAO();
    private ConnectionDB connectionDB = new ConnectionDB();
    private TableRowSorter<DefaultTableModel> sorter;
    private CustomerGUI.NonEditableTableModel modelo;

    int rows = 0;

    /**
     * Constructor de la clase.
     * @param parentFrame Ventana principal desde la que se abre esta ventana.
     */

    public CategoryGUI(JFrame parentFrame) {

        addButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        updateButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        deleteButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        BackButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));

        addButton.setBackground(new Color(0, 200, 0)); // Verde base
        addButton.setForeground(Color.WHITE); // Texto en blanco
        addButton.setBorder(BorderFactory.createLineBorder(new Color(96, 160, 96), 3)); // Borde verde oscuro

        updateButton.setBackground(new Color(211, 158, 0)); // Verde base
        updateButton.setForeground(Color.WHITE); // Texto en blanco
        updateButton.setBorder(BorderFactory.createLineBorder(new Color(153, 115, 0), 3)); // Borde verde oscuro

        deleteButton.setBackground(new Color(220, 53, 69)); // Rojo base
        deleteButton.setForeground(Color.WHITE); // Texto en blanco
        deleteButton.setBorder(BorderFactory.createLineBorder(new Color(176, 32, 48), 3)); // Borde rojo oscuro

        BackButton.setBackground(new Color(41,171,226)); // Verde base
        BackButton.setForeground(Color.WHITE); // Texto en blanco
        BackButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde verde oscuro

        textField1.setVisible(false);
        this.parentFrame = parentFrame;
        sorter = new TableRowSorter<>(modelo);
        table1.setRowSorter(sorter);
        showdata();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField2.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Completa el campo Nombre Categoria");

                } else {

                    String name_cat = textField2.getText();

                    if (!name_cat.matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
                        JOptionPane.showMessageDialog(null, "El campo Nombre Categoria solo puede contener letras");
                        return;
                    }

                    boolean namExist = false;
                    for (int i = 0; i < table1.getRowCount(); i++) {
                        String namExisting = table1.getValueAt(i, 1).toString();
                        if (namExisting.equalsIgnoreCase(name_cat)) {
                            namExist = true;
                            break;
                        }
                    }

                    if (namExist) {
                        JOptionPane.showMessageDialog(null, "El nombre " + name_cat + " ya existe");
                        textField2.setText("");
                        return;
                    }

                    Category category = new Category(0, name_cat);
                    categoryDAO.add(category);
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

                if (textField2.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Completa el campo Nombre Categoria");

                } else {

                    String name_cat = textField2.getText();

                    int id_category = Integer.parseInt(textField1.getText());

                    if (!name_cat.matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
                        JOptionPane.showMessageDialog(null, "El campo Nombre Categoria solo puede contener letras");
                        return;
                    }

                    boolean namExist = false;
                    for (int i = 0; i < table1.getRowCount(); i++) {
                        String namExisting = table1.getValueAt(i, 1).toString();
                        if (namExisting.equalsIgnoreCase(name_cat)) {
                            namExist = true;
                            break;
                        }
                    }

                    if (namExist) {
                        JOptionPane.showMessageDialog(null, "El nombre " + name_cat + " ya existe");
                        textField2.setText("");
                        return;
                    }

                    Category category = new Category(id_category, name_cat);
                    categoryDAO.update(category);
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

                if (textField2.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Completa el campo Nombre Categoria");
                }else if (selectedRow == -1) { // Si no hay fila seleccionada
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una categoria para eliminar");
                } else {

                    int id_category = Integer.parseInt(textField1.getText());

                    categoryDAO.delete(id_category);
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
                    textField2.setText((String)table1.getValueAt(selectedRows,1));

                    rows = selectedRows;

                }
            }
        });

        // Evento para filtrar la tabla al escribir en el campo de búsqueda


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
     * Muestra los datos de las categorías en la tabla.
     */

    public void showdata() {

        if (sorter != null) {
            table1.setRowSorter(null);
        }

        CategoryGUI.NonEditableTableModel modelo = new CategoryGUI.NonEditableTableModel();

        modelo.addColumn("Id_Categoria");
        modelo.addColumn("Nombre Categoria");

        table1.setModel(modelo);

        String[] dato = new String[2];
        Connection con = connectionDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM categoria");

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);

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
     * Clase para definir un modelo de tabla no editable.
     * Extiende DefaultTableModel y sobrescribe isCellEditable para evitar la edición de celdas.
     */

    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    /**
     * Método para limpiar los campos de texto de la interfaz.
     */

    public void clear(){
        textField1.setText("");
        textField2.setText("");
    }


    /**
     * Método para inicializar y mostrar la ventana de gestión de categorías.
     */

    public void runCategory() {

        frame = new JFrame("Data Base Pharmacy");
        frame.setContentPane(this.main);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);

        // Agrega un listener para manejar el cierre de la ventana

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                // Muestra un cuadro de diálogo de confirmación antes de cerrar

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
