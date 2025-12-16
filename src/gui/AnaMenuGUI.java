package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AnaMenuGUI extends JFrame {

    public AnaMenuGUI() {
        setTitle("Ana Menü");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        // Bilgi Butonu
        JButton btnBilgi = new JButton("Bilgi");
        btnBilgi.setFont(new Font("Arial", Font.BOLD, 24));
        btnBilgi.addActionListener(e -> new BilgiMenuGUI().setVisible(true));
        add(btnBilgi);

        // Yönetim Butonu
        JButton btnYonetim = new JButton("Yönetim");
        btnYonetim.setFont(new Font("Arial", Font.BOLD, 24));
        // Yönetim girişi penceresini açar
        btnYonetim.addActionListener(e -> new YonetimGirisGUI().setVisible(true));
        add(btnYonetim);

        // Arka plan resim gereksinimi için JPanel kullanılıp ayarlanabilir. Basitleştirme için atlanmıştır.
    }
}