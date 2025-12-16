package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BilgiMenuGUI extends JFrame {

    public BilgiMenuGUI() {
        setTitle("Bilgi Menüsü");
        setSize(400, 600);
        setLayout(new GridLayout(9, 1)); // En az 8 maddeli menüye uygun (Bölüm 11)
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Menü maddeleri:
        JButton btnOgrenci = new JButton("Öğrenci Bilgi Sistemi");
        btnOgrenci.addActionListener(e -> { /* Öğrenci Bilgi Sorgulama Ekranı */ });
        add(btnOgrenci);

        JButton btnAkademisyen = new JButton("Akademisyen Bilgi Sistemi");
        add(btnAkademisyen);

        JButton btnDersProgrami = new JButton("Ders Programı");
        add(btnDersProgrami);

        JButton btnYemekhane = new JButton("Yemekhane");
        add(btnYemekhane);

        JButton btnKutuphane = new JButton("Kütüphane");
        btnKutuphane.addActionListener(e -> new KutuphaneListeGUI().setVisible(true)); // Kütüphane listesini aç
        add(btnKutuphane);

        JButton btnSporSalonu = new JButton("Spor Salonu");
        add(btnSporSalonu);

        JButton btnTakvim = new JButton("Akademik Takvim");
        add(btnTakvim);

        JButton btnDuyurular = new JButton("Duyurular");
        add(btnDuyurular);

        JButton btnGeri = new JButton("Geri");
        btnGeri.addActionListener(e -> this.dispose());
        add(btnGeri);
    }
}