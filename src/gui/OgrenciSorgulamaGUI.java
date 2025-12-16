package gui;

import javax.swing.*;
import main.KampusUygulamasi;
import model.Ogrenci;
import service.OgrenciServisi;
import exception.OgrenciBulunamadiException;
import java.awt.event.ActionEvent;
import java.awt.*;

public class OgrenciSorgulamaGUI extends JFrame {

    private JTextField txtOgrenciNo;
    private JTextArea txtBilgiAlani;
    private JButton btnSorgula;
    private OgrenciServisi ogrenciServisi;

    public OgrenciSorgulamaGUI() {
        this.ogrenciServisi = new OgrenciServisi(); // Servisi başlat

        setTitle("Öğrenci Bilgi Sorgulama");
        setSize(500, 400);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Üst Panel (Giriş)
        JPanel topPanel = new JPanel(new FlowLayout());
        txtOgrenciNo = new JTextField(10);
        btnSorgula = new JButton("Sorgula");

        topPanel.add(new JLabel("Öğrenci No:"));
        topPanel.add(txtOgrenciNo);
        topPanel.add(btnSorgula);

        add(topPanel, BorderLayout.NORTH);

        // Orta Alan (Bilgi Listeleme)
        txtBilgiAlani = new JTextArea("Öğrenci bilgilerini listelemek için numara giriniz.", 15, 40);
        txtBilgiAlani.setEditable(false);
        add(new JScrollPane(txtBilgiAlani), BorderLayout.CENTER);

        btnSorgula.addActionListener(this::sorgulaAction);
    }

    private void sorgulaAction(ActionEvent e) {
        String ogrNo = txtOgrenciNo.getText();

        // try-catch bloğu (Bölüm 7 gereksinimi)
        try {
            // Öğrenciyi servis üzerinden bul (Özel Exception fırlatma potansiyeli)
            Ogrenci bulunanOgrenci = ogrenciServisi.ogrenciNoIleBul(ogrNo);

            if (bulunanOgrenci == null) {
                // Eğer Map yapısında bulunamazsa (normalde Servis içinde exception fırlatılmalı)
                throw new OgrenciBulunamadiException(ogrNo);
            }

            // Bilgileri JTextArea'ya formatlı şekilde yaz (Kapsülleme ile getter'lar kullanılır)
            String bilgi = String.format(
                    "--- ÖĞRENCİ BİLGİLERİ ---\n" +
                            "Tam Adı: %s\n" +
                            "Öğrenci No: %s\n" +
                            "Bölüm: %s\n" +
                            "Pozisyon: %s\n" + // Polimorfik metot çağrısı
                            "Not Ortalaması: %d\n" +
                            "Durum: %s",
                    bulunanOgrenci.tamAdGetir(),
                    bulunanOgrenci.getOgrenciNo(),
                    bulunanOgrenci.getBolum(),
                    bulunanOgrenci.getPozisyon(), // Polimorfizm örneği
                    bulunanOgrenci.getNotOrtalamasi(),
                    bulunanOgrenci.detayliRaporOlustur() // Polimorfik Raporlama
            );

            txtBilgiAlani.setText(bilgi);

        } catch (OgrenciBulunamadiException ex) {
            // Özel Exception Yakalama (Bölüm 7 gereksinimi)
            txtBilgiAlani.setText("HATA:\n" + ex.getMessage());
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Öğrenci Bulunamadı", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            // Genel Exception Yakalama
            txtBilgiAlani.setText("Bilinmeyen Hata:\n" + ex.getMessage());
        }
    }
}