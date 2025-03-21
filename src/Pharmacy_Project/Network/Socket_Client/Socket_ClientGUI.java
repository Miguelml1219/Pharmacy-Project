package Pharmacy_Project.network.socket_client;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

    public boolean Server(String serverAddress) {

        try {
            socket = new Socket(serverAddress, 12345);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            new Thread(this::receivedMessage).start();
            return true;

        } catch (IOException t) {
            JOptionPane.showMessageDialog(null, "Error in client: " + t.getMessage());
            return false;
        }
    }


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

    public void SendMessage() {
        String sendMessage = textField1.getText();
        textArea1.append("Client: " + sendMessage + "\n");
        out.println(sendMessage);
    }

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

    private static void closeServerExternally() {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("shutdown");  // Enviar señal de apagado
            socket.close();
        } catch (IOException ignored) {
        }
    }

    public static void startClient() {
        String serverAddress;

        while (true){
            serverAddress = JOptionPane.showInputDialog("Enter the IP of Server (localhost it's local): ");

            if (serverAddress == null) {
                JOptionPane.showMessageDialog(null, "Connection cancelled.");
                closeServerExternally();
                return;
            }

            if (serverAddress.equalsIgnoreCase("localhost")) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Please, enter 'localhost'.");
            }
        }


        Socket_ClientGUI clientGUI = new Socket_ClientGUI();

        if(clientGUI.Server(serverAddress)){
            JFrame clientFrame = new JFrame("Data Base Client");
            clientFrame.setContentPane(clientGUI.main);
            clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            clientFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    clientGUI.closeClient();
                    clientFrame.dispose();
                }
            });
            clientFrame.pack();
            clientFrame.setSize(300, 300);
            clientFrame.setLocation(225, 200);
            clientFrame.setVisible(true);
        }else {
            JOptionPane.showMessageDialog(null, "Failed to connect to server.");
        }
    }
}
