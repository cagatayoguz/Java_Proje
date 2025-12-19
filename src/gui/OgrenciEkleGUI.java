package gui;

import service.DosyaIslemleri;
import model.LisansOgrenci; // Yeni oluşturduğumuz sınıf
import model.Ogrenci;       // Abstract sınıfımız
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

        // --- FORM KARTI ---
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

        // Panele Ekleme
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

        // --- OOP ENTEGRASYONU YAPILAN KISIM ---
        btnKaydet.addActionListener(e -> {
            try {
                // 1. Temel Boşluk Kontrolleri
                if (txtAd.getText().trim().isEmpty()) throw new GecersizGirisBilgisiException("Ad boş olamaz.");
                if (txtSoyad.getText().trim().isEmpty()) throw new GecersizGirisBilgisiException("Soyad boş olamaz.");
                if (txtNo.getText().trim().isEmpty()) throw new GecersizGirisBilgisiException("Öğrenci No boş olamaz.");
                if (txtBolum.getText().trim().isEmpty()) throw new GecersizGirisBilgisiException("Bölüm boş olamaz.");

                // 2. NESNE OLUŞTURMA (Polimorfizm Kullanımı)
                // LisansOgrenci üretiyoruz ama Ogrenci referansında tutuyoruz.
                Ogrenci yeniOgrenci = new LisansOgrenci(
                        txtAd.getText().trim(),
                        txtSoyad.getText().trim(),
                        txtNo.getText().trim(),
                        txtBolum.getText().trim()
                );

                // 3. SETTER METOTLARI İLE VALIDATION (Sınıf Kurallarını Çalıştır)
                // Bölüm kontrolünü sınıf üzerinden yapıyoruz
                yeniOgrenci.setBolum(txtBolum.getText().trim());

                // Not Ortalaması Kontrolü (Sınıfın içindeki 0-100 kuralı burada çalışacak)
                if (txtOrt.getText().trim().isEmpty()) throw new GecersizGirisBilgisiException("Ortalama boş olamaz.");
                try {
                    int ortDeger = Integer.parseInt(txtOrt.getText().trim());
                    // BURASI KRİTİK: Eğer 101 girilirse Ogrenci sınıfı hata fırlatacak
                    yeniOgrenci.setNotOrtalamasi(ortDeger);
                } catch (NumberFormatException nfe) {
                    throw new GecersizGirisBilgisiException("Ortalama sayısal bir değer olmalıdır.");
                }

                // 4. DOSYAYA KAYDETME
                // Verileri artık doğrulanmış nesneden (yeniOgrenci) alıyoruz
                DosyaIslemleri.ogrenciEkle(
                        yeniOgrenci.getAd(),
                        yeniOgrenci.getSoyad(),
                        yeniOgrenci.getBolum(),
                        yeniOgrenci.getOgrenciNo(),
                        txtSinif.getText().trim(), // Sınıf bilgisi modelde olmadığı için direkt alıyoruz
                        String.valueOf(txtOrt.getText().trim())
                );

                // İsteğe bağlı: Polimorfizm örneği olarak konsola rapor basabilirsin
                System.out.println(yeniOgrenci.detayliRaporOlustur());

                JOptionPane.showMessageDialog(this, "Öğrenci başarıyla eklendi.");
                this.dispose();

            } catch (GecersizGirisBilgisiException ex) {
                // Ogrenci sınıfından gelen hatalar (örn: "Not 0-100 arasında olmalı") burada yakalanır
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Geçersiz İşlem", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage(), "Sistem Hatası", JOptionPane.ERROR_MESSAGE);
            }
        });

        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(btnKaydet);
        mainPanel.add(cardPanel);
    }

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
        p.add(l); p.add(Box.createVerticalStrut(5)); p.add(field); p.add(Box.createVerticalStrut(10));
    }
}