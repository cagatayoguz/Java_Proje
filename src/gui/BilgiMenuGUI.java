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
        btnOgrenci.addActionListener(e -> new OgrenciBilgiSistemiGUI().setVisible(true));
        add(btnOgrenci);

        JButton btnAkademisyen = new JButton("Akademisyen Bilgi Sistemi");
        btnAkademisyen.addActionListener(e -> new AkademisyenListeGUI().setVisible(true));
        add(btnAkademisyen);

        JButton btnDersProgrami = new JButton("Ders Programı");
        btnDersProgrami.addActionListener(e -> new FakulteSecimGUI().setVisible(true));
        add(btnDersProgrami);

        JButton btnYemekhane = new JButton("Yemekhane");
        btnYemekhane.addActionListener(e -> new YemekhaneGUI().setVisible(true));
        add(btnYemekhane);

        JButton btnKutuphane = new JButton("Kütüphane");
        btnKutuphane.addActionListener(e -> new KutuphaneListeGUI().setVisible(true)); // Kütüphane listesini aç
        add(btnKutuphane);

        JButton btnSporSalonu = new JButton("Spor Salonu");
        btnSporSalonu.addActionListener(e -> new SporSalonuMenuGUI().setVisible(true));
        add(btnSporSalonu);

        JButton btnTakvim = new JButton("Takvimler"); // İsmi değişti
        btnTakvim.addActionListener(e -> new TakvimSecimGUI().setVisible(true));
        add(btnTakvim);

        JButton btnDuyurular = new JButton("Duyurular");
        add(btnDuyurular);

        JButton btnGeri = new JButton("Geri");
        btnGeri.addActionListener(e -> this.dispose());
        add(btnGeri);
    }
}