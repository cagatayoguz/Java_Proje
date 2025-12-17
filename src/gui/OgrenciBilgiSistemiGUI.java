package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Map;

public class OgrenciBilgiSistemiGUI extends JFrame {

    public OgrenciBilgiSistemiGUI() {
        setTitle("Öğrenci Sorgulama");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Arka Plan
        JPanel mainPanel = new JPanel(new GridBagLayout()); // Ortalamak için
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        // Beyaz Kart
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(40, 40, 40, 40)
        ));

        // Başlık
        JLabel lblTitle = new JLabel("Öğrenci Bilgisi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Açıklama
        JLabel lblDesc = new JLabel("Numara ile sorgulama yapın");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDesc.setForeground(Color.GRAY);
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Input Alanı
        JTextField txtNo = new JTextField(15);
        txtNo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtNo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtNo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10)
        ));

        // Sorgula Butonu
        JButton btnSorgula = new JButton("Sorgula");
        btnSorgula.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSorgula.setForeground(Color.WHITE);
        btnSorgula.setBackground(new Color(13, 110, 253)); // Mavi
        btnSorgula.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnSorgula.setFocusPainted(false);
        btnSorgula.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Buton Aksiyonu
        btnSorgula.addActionListener(e -> {
            String ogrNo = txtNo.getText().trim();
            if (ogrNo.isEmpty()) return;

            Map<String, String[]> ogrenciler = DosyaIslemleri.ogrencileriOku();
            if (ogrenciler.containsKey(ogrNo)) {
                String[] bilgiler = ogrenciler.get(ogrNo);
                String mesaj = String.format(
                        "Ad: %s\nSoyad: %s\nBölüm: %s\nSınıf: %s\nOrtalama: %s",
                        bilgiler[0], bilgiler[1], bilgiler[2], bilgiler[3], bilgiler[4]);
                JOptionPane.showMessageDialog(this, mesaj, "Öğrenci Bulundu", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Öğrenci bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Bileşenleri Karta Ekle
        cardPanel.add(lblTitle);
        cardPanel.add(lblDesc);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(new JLabel("Öğrenci Numarası:"));
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(txtNo);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(btnSorgula);

        mainPanel.add(cardPanel);
    }
}