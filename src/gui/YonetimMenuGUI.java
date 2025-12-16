package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class YonetimMenuGUI extends JFrame {

    public YonetimMenuGUI() {
        setTitle("Yönetim Menüsü");
        setSize(400, 400);
        setLayout(new GridLayout(4, 1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Öğrenci Ekleme/Silme
        JButton btnOgrenciYonetim = new JButton("Öğrenci Yönetimi");
        add(btnOgrenciYonetim);

        // Kitap Ekleme/Silme
        JButton btnKutuphaneYonetim = new JButton("Kütüphane Yönetimi");
        add(btnKutuphaneYonetim);

        // Spor Salonu Üye Ekleme
        JButton btnSporYonetim = new JButton("Spor Salonu Yönetimi");
        add(btnSporYonetim);

        // Duyuru Ekleme
        JButton btnDuyuruEkle = new JButton("Duyuru Ekle");
        add(btnDuyuruEkle);
    }
}