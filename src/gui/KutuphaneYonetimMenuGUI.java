package gui;

import javax.swing.*;
import java.awt.*;

public class KutuphaneYonetimMenuGUI extends JFrame {
    public KutuphaneYonetimMenuGUI() {
        setTitle("Kütüphane Yönetimi");
        setSize(300, 300); // Boyutu biraz büyüttük
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10)); // 3 Buton olacak
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Kitap Ekle
        JButton btnEkle = new JButton("Kitap Ekle");
        btnEkle.setFont(new Font("Arial", Font.BOLD, 14));
        btnEkle.addActionListener(e -> new KitapEkleGUI().setVisible(true));

        // 2. Kitap Sil
        JButton btnSil = new JButton("Kitap Sil");
        btnSil.setFont(new Font("Arial", Font.BOLD, 14));
        btnSil.addActionListener(e -> new KitapSilGUI().setVisible(true));

        // 3. YENİ: Onay İşlemleri
        JButton btnOnay = new JButton("Onay Bekleyenler");
        btnOnay.setFont(new Font("Arial", Font.BOLD, 14));
        btnOnay.setBackground(new Color(255, 255, 200)); // Sarımsı uyarı rengi
        btnOnay.addActionListener(e -> new KutuphaneOnayGUI().setVisible(true));

        add(btnEkle);
        add(btnSil);
        add(btnOnay);

        setLocationRelativeTo(null);
    }
}