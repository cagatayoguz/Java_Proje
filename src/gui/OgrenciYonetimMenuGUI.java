package gui;

import javax.swing.*;
import java.awt.*;

public class OgrenciYonetimMenuGUI extends JFrame {

    public OgrenciYonetimMenuGUI() {
        setTitle("Öğrenci Yönetimi");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1, 10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnEkle = new JButton("Öğrenci Ekle");
        btnEkle.setFont(new Font("Arial", Font.BOLD, 16));
        btnEkle.addActionListener(e -> new OgrenciEkleGUI().setVisible(true));

        JButton btnSil = new JButton("Öğrenci Sil");
        btnSil.setFont(new Font("Arial", Font.BOLD, 16));
        btnSil.addActionListener(e -> new OgrenciSilGUI().setVisible(true));

        add(btnEkle);
        add(btnSil);
        setLocationRelativeTo(null);
    }
}