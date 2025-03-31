package Pharmacy_Project.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Clase QRGenerator que permite mostrar una imagen de un código QR en una ventana.
 */
public class QRGenerator {

    /**
     * Muestra una ventana emergente con una imagen de código QR.
     * La imagen del código QR debe estar ubicada en la ruta especificada.
     */

    public static void showqr() {

        JFrame frame = new JFrame("Scanea el QR ");
        frame.setSize(360, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setUndecorated(true); // Sin bordes del sistema
        frame.setBackground(new Color(70, 70, 70)); // Fondo gris claro

        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 70, 70)); // Color gris oscuro
        panel.setLayout(new BorderLayout()); // Usar BorderLayout para centrar la imagen

        // Ruta de la imagen del código QR
        //String rutaImagen = "C:/Users/crist/IdeaProjects/Pharmacy-Project/src/Pharmacy_Project/utils/qrnequi.jpeg";
        String rutaImagen = "src/Pharmacy_Project/utils/qrnequi.jpeg";


        ImageIcon icon = new ImageIcon(rutaImagen);
        Image img = icon.getImage().getScaledInstance(315, 315, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(img));

        panel.add(label, BorderLayout.CENTER);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
