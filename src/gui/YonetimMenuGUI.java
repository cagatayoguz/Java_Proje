package gui;

import javax.swing.*;
import java.awt.*;

public class YonetimMenuGUI extends JFrame {

    public YonetimMenuGUI() {
        setTitle("Yönetim Menüsü");
        setSize(400, 400);
        setLayout(new GridLayout(4, 1, 10, 10)); // Butonlar arası boşluk eklendi
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 1. Öğrenci Yönetimi Butonu
        JButton btnOgrenciYonetim = new JButton("Öğrenci Yönetimi");
        btnOgrenciYonetim.setFont(new Font("Arial", Font.BOLD, 14));

        // --- DÜZELTME BURADA ---
        // Tıklanınca OgrenciYonetimMenuGUI penceresini açar
        btnOgrenciYonetim.addActionListener(e -> new OgrenciYonetimMenuGUI().setVisible(true));

        add(btnOgrenciYonetim);

        // 2. Kütüphane Yönetimi Butonu
        JButton btnKutuphaneYonetim = new JButton("Kütüphane Yönetimi");
        btnKutuphaneYonetim.setFont(new Font("Arial", Font.BOLD, 14));
        btnKutuphaneYonetim.addActionListener(e -> new KutuphaneYonetimMenuGUI().setVisible(true));
        add(btnKutuphaneYonetim);

        // 3. Spor Salonu Yönetimi Butonu
        // YonetimMenuGUI.java içinde btnSporYonetim butonunu bul ve değiştir:
        JButton btnSporYonetim = new JButton("Spor Salonu Yönetimi");
        btnSporYonetim.setFont(new Font("Arial", Font.BOLD, 14));
        btnSporYonetim.addActionListener(e -> new SporSalonuYonetimMenuGUI().setVisible(true));
        add(btnSporYonetim);

        // 4. Duyuru Ekleme Butonu
        JButton btnDuyuruYonetim = new JButton("Duyuru Yönetimi"); // İsmi değişti
        btnDuyuruYonetim.setFont(new Font("Arial", Font.BOLD, 14));
        btnDuyuruYonetim.addActionListener(e -> new DuyuruYonetimMenuGUI().setVisible(true));
        add(btnDuyuruYonetim);

        setLocationRelativeTo(null); // Ekranın ortasında açılsın
    }
}