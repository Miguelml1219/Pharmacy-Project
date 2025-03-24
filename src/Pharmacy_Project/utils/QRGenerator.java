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

public class QRGenerator {
    public static void showqr() {

        JFrame frame = new JFrame("Nequi");
        frame.setSize(280, 280);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String rutaImagen = "C:/Users/crist/IdeaProjects/Pharmacy-Project/src/Pharmacy_Project/utils/qrnequi.jpeg";

        ImageIcon icon = new ImageIcon(rutaImagen);
        Image img = icon.getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(img));


        frame.add(label, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
