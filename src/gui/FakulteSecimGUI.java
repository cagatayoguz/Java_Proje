package gui;

import javax.swing.*;
import java.awt.*;

public class FakulteSecimGUI extends JFrame {

    public FakulteSecimGUI() {
        setTitle("Fakülte Seçimi");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10)); // 3 Satır, aralarda 10px boşluk

        // Butonları Oluştur
        JButton btnTeknoloji = new JButton("Teknoloji Fakültesi");
        JButton btnMuhendislik = new JButton("Mühendislik Fakültesi");
        JButton btnFen = new JButton("Fen Fakültesi");

        // Font Ayarları
        Font butonFont = new Font("Arial", Font.BOLD, 16);
        btnTeknoloji.setFont(butonFont);
        btnMuhendislik.setFont(butonFont);
        btnFen.setFont(butonFont);

        // Tıklama Olayları - Bölüm Seçim Ekranına Yönlendirir
        // Her biri kendi fakülte adını parametre olarak gönderir
        btnTeknoloji.addActionListener(e -> new BolumSecimGUI("Teknoloji Fakültesi").setVisible(true));
        btnMuhendislik.addActionListener(e -> new BolumSecimGUI("Mühendislik Fakültesi").setVisible(true));
        btnFen.addActionListener(e -> new BolumSecimGUI("Fen Fakültesi").setVisible(true));

        // Ekleme
        add(btnTeknoloji);
        add(btnMuhendislik);
        add(btnFen);

        // Kenar boşluğu (Padding)
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }
}