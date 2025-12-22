package gui;

import model.LisansOgrenci;
import model.Ogrenci;
import exception.GecersizGirisBilgisiException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class OgrenciEkleGUI extends JFrame {

    public OgrenciEkleGUI() {
        setTitle("Yeni Öğrenci Kaydı");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        // KART PANELİ
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(40, 40, 40, 40)
        ));

        // Başlık
        JLabel lblTitle = new JLabel("Öğrenci Ekle");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblTitle);
        cardPanel.add(Box.createVerticalStrut(20));

        // Form Alanları
        JTextField txtAd = createField();
        JTextField txtSoyad = createField();
        JTextField txtNo = createField();
        JTextField txtBolum = createField();
        JTextField txtSinif = createField();
        JTextField txtOrt = createField();

        addLabeledField(cardPanel, "Adı:", txtAd);
        addLabeledField(cardPanel, "Soyadı:", txtSoyad);
        addLabeledField(cardPanel, "Öğrenci No:", txtNo);
        addLabeledField(cardPanel, "Bölümü:", txtBolum);
        addLabeledField(cardPanel, "Sınıfı:", txtSinif);
        addLabeledField(cardPanel, "Ortalama:", txtOrt);

        // Kaydet Butonu
        JButton btnKaydet = new JButton("Kaydet");
        btnKaydet.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setBackground(new Color(13, 110, 253));
        btnKaydet.setFocusPainted(false);
        btnKaydet.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKaydet.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnKaydet.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Kaydet butonu
        btnKaydet.addActionListener(e -> {
            try {
                // Boş alan kontrolü
                if (txtAd.getText().trim().isEmpty() || txtSoyad.getText().trim().isEmpty() ||
                        txtNo.getText().trim().isEmpty() || txtBolum.getText().trim().isEmpty()) {
                    throw new GecersizGirisBilgisiException("Lütfen tüm alanları doldurunuz.");
                }

                // Doğrudan servisi çağırmak yerine önce nesneyi oluşturuyoruz.
                Ogrenci yeniOgrenci = new LisansOgrenci(
                        txtAd.getText().trim(),
                        txtSoyad.getText().trim(),
                        txtNo.getText().trim(),
                        txtBolum.getText().trim()
                );

                // setBolum ve setNotOrtalamasi metotları, model içindeki kurallara uymazsa hata fırlatır.
                yeniOgrenci.setBolum(txtBolum.getText().trim());

                if (!txtOrt.getText().trim().isEmpty()) {
                    try {
                        double ort = Double.parseDouble(txtOrt.getText().trim());
                        yeniOgrenci.setNotOrtalamasi(ort);
                    } catch (NumberFormatException nfe) {
                        throw new GecersizGirisBilgisiException("Ortalama sayısal bir değer olmalıdır.");
                    }
                }

                // Arka planda DosyaIslemleri çalışıyor.
                if (yeniOgrenci.kaydet()) {
                    JOptionPane.showMessageDialog(this, "Öğrenci sisteme başarıyla kaydedildi.");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Kayıt işlemi başarısız oldu!", "Hata", JOptionPane.ERROR_MESSAGE);
                }

            } catch (GecersizGirisBilgisiException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Eksik Bilgi", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Sistem hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(btnKaydet);
        mainPanel.add(cardPanel);
    }

    // Yardımcı Metotlar
    private JTextField createField() {
        JTextField tf = new JTextField(15);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200)), new EmptyBorder(5, 8, 5, 8)));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return tf;
    }

    private void addLabeledField(JPanel p, String labelText, JTextField field) {
        JLabel l = new JLabel(labelText);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(5));
        p.add(field);
        p.add(Box.createVerticalStrut(10));
    }
}