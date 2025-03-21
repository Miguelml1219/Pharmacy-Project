package Pharmacy_Project;

import Pharmacy_Project.network.socket_server.Socket_ServerGUI;
import Pharmacy_Project.network.socket_client.Socket_ClientGUI;
import Pharmacy_Project.view.Order_DetailGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    private JFrame frame;


    /**
     * Constructor de la clase Main_Menu.
     *
     * @param frame Ventana principal del sistema.
     */

    public Main_Menu(JFrame frame)
    {
        this.frame  = frame;


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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?\nAny operations you are performing will be lost.","Confirm exit",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                if(option == JOptionPane.NO_OPTION)
                {
                    frame.dispose(); // Cierra la ventana
                    System.exit(0);
                }
            }
        });

    }

}
