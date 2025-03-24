package Pharmacy_Project.network.socket_server;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
                    out.println("Server has left the chat.");
                }
                JOptionPane.showMessageDialog(null, "You have left the chat.");
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

    public void Server() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            //JOptionPane.showMessageDialog(null, "Start Server. Wait connection...");
            Socket clientSocket = serverSocket.accept();
            //JOptionPane.showMessageDialog(null, "Connected Client.");

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            new Thread(this::receivedMessage).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Server Error" + e.getMessage());
        }
    }

    public void receivedMessage() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                textArea1.append("Client: " + message + "\n");
                if(message.equalsIgnoreCase("shutdown"))
                {
                    closeServer(); // Cierra la aplicación
                    break;
                }
                if (message.contains("has left the chat")) {
                    JOptionPane.showMessageDialog(null, "The other user has left the chat.");
                    closeServer(); // Cierra la aplicación
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Server Error");
        }
    }

    public void SendMessage() {
        String sendMessage = textField1.getText();
        textArea1.append("Server: " + sendMessage + "\n");
        out.println(sendMessage);
    }



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


    public static void startServer() {


        JFrame serverFrame = new JFrame("Data Base Server");
        Socket_ServerGUI serverGUI = new Socket_ServerGUI();
        serverFrame.setContentPane(serverGUI.main);
        serverFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        serverFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(serverFrame,"You left the chat");

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