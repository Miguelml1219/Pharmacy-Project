package Pharmacy_Project.network.socket_client;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Clase que representa un cliente de chat basado en sockets con una interfaz gráfica.
 */

public class Socket_ClientGUI {

    private JTextArea textArea1;
    public JPanel main;
    private JTextField textField1;
    private JButton sendMessageButton;
    private JButton exitButton;
    private Socket socket;
    PrintWriter out;
    BufferedReader in;
    private boolean isRunning = false;

    /**
     * Constructor de la clase, inicializa los componentes de la interfaz gráfica y sus eventos.
     */

    public Socket_ClientGUI() {

        textArea1.setEditable(false);// Desactiva el menú contextual


        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SendMessage();
                textField1.setText("");
            }
        });

        // Añadir acción al botón Exit
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (out != null) {
                    out.println("Client has left the chat.");
                }
                JOptionPane.showMessageDialog(null, "You have left the chat.");
                closeClient();
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
    }

    /**
     * Establece la conexión con el servidor de chat.
     *
     * @param serverAddress Dirección IP o nombre del servidor.
     * @return true si la conexión fue exitosa, false en caso de error.
     */

    public boolean Server(String serverAddress) {

        try {
            socket = new Socket(serverAddress, 12345);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            new Thread(this::receivedMessage).start();
            return true;

        } catch (IOException t) {
            //JOptionPane.showMessageDialog(null, "Error in client: " + t.getMessage());
            return false;
        }
    }

    /**
     * Escucha y recibe mensajes del servidor de manera continua.
     * Si el servidor cierra la conexión, el cliente también se cierra.
     */

    public void receivedMessage() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                textArea1.append("Server: " + message + "\n");

                if (message.contains("has left the chat")) {
                    JOptionPane.showMessageDialog(null, "The other user has left the chat.");
                    closeClient();
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error Server");
        }
    }

    /**
     * Envía un mensaje al servidor si el campo de texto no está vacío.
     */

    public void SendMessage() {
        String sendMessage = textField1.getText();
        textArea1.append("Client: " + sendMessage + "\n");
        out.println(sendMessage);
    }

    /**
     * Cierra la conexión con el servidor y libera los recursos.
     */

    public void closeClient() {
        isRunning = false;
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        SwingUtilities.getWindowAncestor(main).dispose();
    }

    /**
     * Envía una señal al servidor para que cierre la conexión de forma externa.
     * Intenta conectarse al servidor y enviar el comando "shutdown".
     * Si la conexión no es posible, la excepción es ignorada.
     */

    private static void closeServerExternally() {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("shutdown");  // Enviar señal de apagado
            socket.close();
        } catch (IOException ignored) {
        }
    }


    /**
     * Inicia la interfaz del cliente de chat y solicita la dirección del servidor.
     */

    public static void startClient() {
        String serverAddress;

        while (true){
            serverAddress = JOptionPane.showInputDialog("Enter the IP of Server (localhost it's local): ");

            if (serverAddress == null) {
                JOptionPane.showMessageDialog(null, "Connection cancelled.");
                closeServerExternally();
                return;
            }

//            if (serverAddress.equalsIgnoreCase("localhost")) {
//                break;
//            } else {
//                JOptionPane.showMessageDialog(null, "Please, enter 'localhost'.");
//            }
            break;
        }


        Socket_ClientGUI clientGUI = new Socket_ClientGUI();

        if(clientGUI.Server(serverAddress)){
            JFrame clientFrame = new JFrame("Data Base Client");
            clientFrame.setContentPane(clientGUI.main);
            clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            clientFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    JOptionPane.showMessageDialog(clientFrame,"You left the chat");

//                    clientGUI.closeClient();
//                    clientFrame.dispose();
                }
            });
            clientFrame.pack();
            clientFrame.setSize(300, 300);
            clientFrame.setLocation(225, 200);
            clientFrame.setVisible(true);
        }else {
            //JOptionPane.showMessageDialog(null, "Failed to connect to server.");
        }
    }
}