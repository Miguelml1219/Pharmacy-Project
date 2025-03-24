package Pharmacy_Project;

import Pharmacy_Project.network.socket_server.Socket_ServerGUI;
import Pharmacy_Project.network.socket_client.Socket_ClientGUI;
import Pharmacy_Project.view.Order_DetailGUI;
import Pharmacy_Project.utils.BackGround;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Clase que representa el menú principal del sistema de la farmacia.
 */
public class Main_Menu {

    private JButton adminButton;
    private JButton placeOrderButton;
    private JPanel main;
    private JButton adminMenuButton;
    private JButton chatButton;
    private JButton exitButton;
    private JLabel tittle;
    private JFrame frame;


    /**
     * Constructor de la clase Main_Menu.
     *
     * @param frame Ventana principal del sistema.
     */

    public Main_Menu(JFrame frame)
    {
        this.frame  = frame;

        // Establecer el layout para que no desordene tus botones
        main.setLayout(new GridBagLayout()); // Centrar los botones
        tittle.setFont(new Font("Marlett Non-latin", Font.BOLD | Font.ITALIC, 120)); // Fuente Serif con Bold Italic y 72pt
        tittle.setBorder(BorderFactory.createEmptyBorder(30, 10, 110, 10)); // Espacio superior



        Dimension buttonSize = new Dimension(500, 30); // Ancho: 200px, Alto: 40px
        adminMenuButton.setPreferredSize(buttonSize);
        placeOrderButton.setPreferredSize(buttonSize);
        chatButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        adminMenuButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        placeOrderButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        chatButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));
        exitButton.setFont(new Font("Marlett Non-latin", Font.BOLD, 16));

        adminMenuButton.setBackground(new Color(0, 123, 255)); // Azul base
        adminMenuButton.setForeground(Color.WHITE); // Texto en blanco
        adminMenuButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        placeOrderButton.setBackground(new Color(0, 123, 255)); // Azul base
        placeOrderButton.setForeground(Color.WHITE); // Texto en blanco
        placeOrderButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        chatButton.setBackground(new Color(0, 123, 255)); // Azul base
        chatButton.setForeground(Color.WHITE); // Texto en blanco
        chatButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        exitButton.setBackground(new Color(0, 123, 255)); // Azul base
        exitButton.setForeground(Color.WHITE); // Texto en blanco
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 179), 3)); // Borde azul oscuro

        // Agregar los botones ya creados en el GUI
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 10, 20, 10); // Espaciado entre los botones

        main.add(adminMenuButton, gbc);
        main.add(placeOrderButton, gbc);
        main.add(chatButton, gbc);
        main.add(exitButton, gbc);


        adminMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin_Menu admin_menu = new Admin_Menu(frame);
                admin_menu.runAdmin();
                frame.setVisible(false);
            }
        });
        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Order_DetailGUI order_detailGUI = new Order_DetailGUI(frame);
                order_detailGUI.runOrder();
                frame.setVisible(false);

            }
        });

        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int option = JOptionPane.showConfirmDialog(null, "Want to enter the chat?", "Enter to the chat", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {

                    new Thread(() -> {
                        Socket_ServerGUI.startServer();
                    }).start();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    new Thread(() -> {
                        Socket_ClientGUI.startClient();
                    }).start();

                } else {
                    JOptionPane.showMessageDialog(null, "Canceled Chat");
                }
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(main);
                if (frame != null) {
                    JOptionPane.showMessageDialog(null,"GoodBye ;)");
                    frame.dispose();
                }
            }
        });

        adminMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                adminMenuButton.setBackground(new Color(102, 178, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                adminMenuButton.setBackground(new Color(0, 123, 255)); // Restaurar color base
            }
        });

        placeOrderButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                placeOrderButton.setBackground(new Color(102, 178, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                placeOrderButton.setBackground(new Color(0, 123, 255)); // Restaurar color base
            }
        });

        chatButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                chatButton.setBackground(new Color(102, 178, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                chatButton.setBackground(new Color(0, 123, 255)); // Restaurar color base
            }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(new Color(102, 178, 255)); // Azul más claro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(new Color(0, 123, 255)); // Restaurar color base
            }
        });
    }

    /**
         * Método principal para ejecutar la aplicación.
            *
            * @param args Argumentos de la línea de comandos.
     */

    public static void main(String[] args) {

        JFrame frame = new JFrame("Data Base Pharmacy");
        Main_Menu main_menu = new Main_Menu(frame); // Se pasa el frame al constructor de Menu
        frame.setContentPane(main_menu.main);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?\nAny operation you are performing and have not saved will be lost..","Confirm exit",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                if(option == JOptionPane.YES_OPTION)
                {
                    frame.dispose(); // Cierra la ventana
                    System.exit(0);
                }
            }
        });

    }

}
