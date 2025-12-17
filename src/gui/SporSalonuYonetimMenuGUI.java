package gui;

import javax.swing.*;
import java.awt.*;

public class SporSalonuYonetimMenuGUI extends JFrame {

    public SporSalonuYonetimMenuGUI() {
        setTitle("Spor Salonu Yönetimi");
        setSize(350, 250); // Boyutu biraz büyüttük
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1, 10, 10)); // 2 Buton alt alta
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // 1. Onay Bekleyenler Butonu
        JButton btnOnay = new JButton("Onay Bekleyenler");
        btnOnay.setFont(new Font("Arial", Font.BOLD, 14));
        btnOnay.setBackground(new Color(255, 255, 200)); // Sarımsı
        btnOnay.addActionListener(e -> new SporSalonuOnayGUI().setVisible(true));

        // 2. Üye Listesi ve Silme Butonu (YENİ)
        JButton btnListe = new JButton("Üye Listesi / Silme");
        btnListe.setFont(new Font("Arial", Font.BOLD, 14));
        btnListe.addActionListener(e -> new SporSalonuUyeYonetimGUI().setVisible(true));

        add(btnOnay);
        add(btnListe);

        setLocationRelativeTo(null);
    }
}