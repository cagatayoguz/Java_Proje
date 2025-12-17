package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DuyuruEkleGUI extends JFrame {

    private JTextField txtKonu;
    private JTextArea txtIcerik;

    public DuyuruEkleGUI() {
        setTitle("Yeni Duyuru Ekle");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tarih (Otomatik Bugün)
        String bugun = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        JLabel lblTarih = new JLabel("Tarih: " + bugun);
        lblTarih.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblTarih);

        // Konu
        panel.add(new JLabel("Duyuru Konusu:"));
        txtKonu = new JTextField();
        panel.add(txtKonu);

        // İçerik
        panel.add(new JLabel("Duyuru İçeriği:"));

        add(panel, BorderLayout.NORTH);

        // İçerik Alanı (Büyük Kutu)
        txtIcerik = new JTextArea();
        txtIcerik.setLineWrap(true);
        add(new JScrollPane(txtIcerik), BorderLayout.CENTER);

        // Kaydet Butonu
        JButton btnKaydet = new JButton("Duyuruyu Yayınla");
        btnKaydet.setFont(new Font("Arial", Font.BOLD, 14));
        btnKaydet.addActionListener(e -> kaydetAction(bugun));
        add(btnKaydet, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void kaydetAction(String tarih) {
        if (txtKonu.getText().trim().isEmpty() || txtIcerik.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen konu ve içerik giriniz.");
            return;
        }

        try {
            DosyaIslemleri.duyuruEkle(tarih, txtKonu.getText(), txtIcerik.getText());
            JOptionPane.showMessageDialog(this, "Duyuru başarıyla yayınlandı!");
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
    }
}