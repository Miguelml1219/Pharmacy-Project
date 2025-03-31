package Pharmacy_Project.network.socket_server;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase que implementa un servidor de chat con una interfaz gráfica.
 */


public class Socket_ServerGUI {
    private JTextArea textArea1;
    public JPanel main;
    private JButton sendMessageButton;
    private JTextField textField1;
    private JButton exitButton;
    BufferedReader in;
    PrintWriter out;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private boolean isRunning = false;

    /**
     * Constructor que inicializa la interfaz gráfica y configura los eventos de los botones.
     */

    public Socket_ServerGUI() {

        textArea1.setEditable(false); // Desactiva el menú contextual

        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SendMessage();
                textField1.setText("");
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (out != null) {
                    out.println("PharmaPLus abandono el chat");
                }
                JOptionPane.showMessageDialog(null, "Abandonaste el chat");
                closeServer();
            }
        });

        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    SendMessage();
                    textField1.setText("");
                }
            }
        });

        new Thread(this::Server).start();
    }

    /**
     * Método que inicia el servidor y espera conexiones de clientes.
     */

    public void Server() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            //JOptionPane.showMessageDialog(null, "Start Server. Wait connection...");
            Socket clientSocket = serverSocket.accept();
            //JOptionPane.showMessageDialog(null, "Connected Client.");

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            new Thread(this::receivedMessage).start();

        } catch (IOException e) {
            //JOptionPane.showMessageDialog(null, "Server Error" + e.getMessage());
        }
    }


    /**
     * Método que escucha los mensajes recibidos desde el cliente y los muestra en la interfaz.
     */

    public void receivedMessage() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                textArea1.append("Cliente: " + message + "\n");
                if(message.equalsIgnoreCase("shutdown"))
                {
                    closeServer(); // Cierra la aplicación
                    break;
                }
                if (message.contains("abandono el chat")) {
                    JOptionPane.showMessageDialog(null, "El otro usuario abandono el chat");
                    closeServer(); // Cierra la aplicación
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Server Error");
        }
    }

    /**
     * Método que envía un mensaje al cliente y lo muestra en la interfaz.
     */

    public void SendMessage() {
        String sendMessage = textField1.getText();
        textArea1.append("PharmaPlus: " + sendMessage + "\n");
        out.println(sendMessage);
    }


    /**
     * Método que cierra el servidor y libera los recursos.
     */

    public void closeServer() {
        isRunning = false;
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        SwingUtilities.getWindowAncestor(main).dispose();
    }

    /**
     * Método que inicia el servidor y muestra la interfaz gráfica.
     */


    public static void startServer() {


        JFrame serverFrame = new JFrame("Data Base Server");
        Socket_ServerGUI serverGUI = new Socket_ServerGUI();
        serverFrame.setContentPane(serverGUI.main);
        serverFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        serverFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(serverFrame,"Abandonaste el chat");

//                serverGUI.closeServer();
//                serverFrame.dispose();
            }
        });
        serverFrame.pack();
        serverFrame.setSize(300, 300);
        serverFrame.setLocation(1000, 200);
        serverFrame.setVisible(true);


    }
}