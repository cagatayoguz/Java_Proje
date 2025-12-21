package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DuyuruEkleGUI extends JFrame {

    public DuyuruEkleGUI() {
        setTitle("Yeni Duyuru Yayınla");
        setSize(500, 600); // Boyutu biraz artırdık
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        // --- Kart Paneli ---
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(30, 30, 30, 30)));
        cardPanel.setPreferredSize(new Dimension(420, 500));

        // Başlık
        JLabel lblTitle = new JLabel("Duyuru Oluştur");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblTitle);
        cardPanel.add(Box.createVerticalStrut(20));

        // --- 1. Tarih Alanı (EKSİK OLAN KISIM EKLENDİ) ---
        JLabel lTarih = new JLabel("Tarih (İsteğe Bağlı):");
        lTarih.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Kullanıcıya ipucu vermek için ToolTip ekledik
        JTextField txtTarih = new JTextField();
        txtTarih.setToolTipText("Boş bırakırsanız bugünün tarihi atılır.");
        txtTarih.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        cardPanel.add(lTarih);
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(txtTarih);
        cardPanel.add(Box.createVerticalStrut(15));

        // --- 2. Duyuru Başlığı Alanı ---
        JLabel l1 = new JLabel("Duyuru Başlığı:");
        l1.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField txtBaslik = new JTextField();
        txtBaslik.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        cardPanel.add(l1);
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(txtBaslik);
        cardPanel.add(Box.createVerticalStrut(15));

        // --- 3. İçerik Alanı ---
        JLabel l2 = new JLabel("İçerik:");
        l2.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextArea txtIcerik = new JTextArea(6, 20);
        txtIcerik.setLineWrap(true);
        txtIcerik.setWrapStyleWord(true); // Kelime bütünlüğünü bozmadan alt satıra geç
        JScrollPane scroll = new JScrollPane(txtIcerik);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        cardPanel.add(l2);
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(scroll);
        cardPanel.add(Box.createVerticalStrut(20));

        // --- Yayınla Butonu ---
        JButton btnYayinla = new JButton("Yayınla");
        btnYayinla.setBackground(new Color(155, 89, 182));
        btnYayinla.setForeground(Color.WHITE);
        btnYayinla.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnYayinla.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnYayinla.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // --- BUTON AKSİYONU (SIKI DENETİMLİ VERSİYON) ---
        btnYayinla.addActionListener(e -> {
            try {
                String tarihStr = txtTarih.getText().trim();
                String baslik = txtBaslik.getText().trim();
                String icerik = txtIcerik.getText().trim();

                // 1. Temel Boşluk Kontrolü
                if (baslik.isEmpty() || icerik.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Başlık ve İçerik boş olamaz!", "Eksik Bilgi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // 2. Tarih Kontrolü
                if (tarihStr.isEmpty()) {
                    // Boş bırakırsa otomatik BUGÜN'ün tarihini atar (Sorun yok)
                    DosyaIslemleri.duyuruEkle(baslik, icerik);
                    JOptionPane.showMessageDialog(this, "Tarih girilmediği için BUGÜNÜN tarihiyle kaydedildi.");
                }
                else {
                    // --- SIKI YÖNETİM BURADA BAŞLIYOR ---
                    try {
                        // Kullanıcının girdiği tarihi "dd.MM.yyyy" formatına göre test ediyoruz.
                        // Eğer format yanlışsa (Örn: 2023-12-25 veya "Yarın"), burası 'DateTimeParseException' hatası verir.
                        java.time.format.DateTimeFormatter formatlayici = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        java.time.LocalDate.parse(tarihStr, formatlayici);

                        // Hata vermediyse format doğrudur, kaydedebilirsin.
                        DosyaIslemleri.duyuruEkle(tarihStr, baslik, icerik);
                        JOptionPane.showMessageDialog(this, "Duyuru başarıyla kaydedildi.");

                    } catch (java.time.format.DateTimeParseException ex) {
                        // Format yanlışsa buraya düşer ve uyarı verir.
                        JOptionPane.showMessageDialog(this,
                                "Hatalı Tarih Formatı!\nLütfen 'gün.ay.yıl' şeklinde giriniz.\nÖrnek: 25.12.2023",
                                "Format Hatası",
                                JOptionPane.ERROR_MESSAGE);
                        return; // Kaydetmeden çıkar
                    }
                }

                this.dispose(); // Pencereyi kapat

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        cardPanel.add(btnYayinla);
        mainPanel.add(cardPanel);
    }
}