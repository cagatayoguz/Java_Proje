package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import java.awt.*;

public class OgrenciEkleGUI extends JFrame {

    // Alanlar
    JTextField txtNo, txtAd, txtSoyad, txtBolum, txtSinif, txtOrt;

    public OgrenciEkleGUI() {
        setTitle("Öğrenci Ekle");
        setSize(350, 400);
        setLayout(new GridLayout(7, 2, 5, 5));

        add(new JLabel("Öğrenci No:")); txtNo = new JTextField(); add(txtNo);
        add(new JLabel("Ad:")); txtAd = new JTextField(); add(txtAd);
        add(new JLabel("Soyad:")); txtSoyad = new JTextField(); add(txtSoyad);
        add(new JLabel("Bölüm:")); txtBolum = new JTextField(); add(txtBolum);
        add(new JLabel("Sınıf:")); txtSinif = new JTextField(); add(txtSinif);
        add(new JLabel("Ortalama:")); txtOrt = new JTextField(); add(txtOrt);

        JButton btnKaydet = new JButton("Kaydet");
        btnKaydet.addActionListener(e -> kaydetAction());
        add(btnKaydet);

        setLocationRelativeTo(null);
    }

    private void kaydetAction() {
        try {
            DosyaIslemleri.ogrenciEkle(
                    txtAd.getText(), txtSoyad.getText(), txtNo.getText(),
                    txtBolum.getText(), txtSinif.getText(), txtOrt.getText()
            );
            JOptionPane.showMessageDialog(this, "Öğrenci başarıyla eklendi!");
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
    }
}