package gui;

import javax.swing.*;
import java.awt.*;

public class DuyuruYonetimMenuGUI extends JFrame {

    public DuyuruYonetimMenuGUI() {
        setTitle("Duyuru Yönetimi");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1, 10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Duyuru Ekle Butonu
        JButton btnEkle = new JButton("Duyuru Ekle");
        btnEkle.setFont(new Font("Arial", Font.BOLD, 16));
        // Mevcut DuyuruEkleGUI sınıfını açar
        btnEkle.addActionListener(e -> new DuyuruEkleGUI().setVisible(true));

        // 2. Duyuru Sil Butonu
        JButton btnSil = new JButton("Duyuru Sil");
        btnSil.setFont(new Font("Arial", Font.BOLD, 16));
        // Birazdan oluşturacağımız silme ekranını açar
        btnSil.addActionListener(e -> new DuyuruSilGUI().setVisible(true));

        add(btnEkle);
        add(btnSil);
        setLocationRelativeTo(null);
    }
}