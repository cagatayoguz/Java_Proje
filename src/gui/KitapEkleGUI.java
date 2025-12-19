package gui;

import model.Kitap;
import model.Kutuphane;
import service.KutuphaneServisi;
import exception.GecersizGirisBilgisiException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class KitapEkleGUI extends JFrame {

    public KitapEkleGUI() {
        setTitle("Hızlı Kitap Ekle");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        // Kart Paneli
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(30, 30, 30, 30)
        ));

        JLabel lblTitle = new JLabel("Kitap Kaydı");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(20));

        // Form Alanları
        JTextField txtAd = createField();
        JTextField txtYazar = createField();
        JTextField txtIsbn = createField();

        addLabel(card, "Kitap Adı:", txtAd);
        addLabel(card, "Yazar:", txtYazar);
        addLabel(card, "ISBN No:", txtIsbn);

        // Kaydet Butonu
        JButton btnKaydet = new JButton("Kaydet");
        btnKaydet.setBackground(new Color(13, 110, 253));
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnKaydet.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKaydet.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // --- BUTON İŞLEMİ (DÜZELTİLDİ) ---
        btnKaydet.addActionListener(e -> {
            try {
                // 1. Boşluk Kontrolü
                if (txtAd.getText().trim().isEmpty() || txtIsbn.getText().trim().isEmpty()) {
                    throw new GecersizGirisBilgisiException("Kitap Adı ve ISBN zorunludur.");
                }

                // 2. OOP Kontrolü: Kutuphane Sınıfını Kullan
                // HATA BURADAYDI: "100" parametresini sildik. Artık sadece isim istiyor.
                Kutuphane raf = new Kutuphane("Sanal Raf");

                // Mevcut kitapları yükle (Çift ISBN kontrolü için)
                KutuphaneServisi servis = new KutuphaneServisi();
                raf.mevcutKitaplariYukle(servis.tumKitaplariGetir()); // Servisten kitapları alıp rafa koyuyoruz

                // Yeni Kitap Oluştur
                Kitap yeniKitap = new Kitap(
                        txtAd.getText().trim(),
                        txtYazar.getText().trim(),
                        txtIsbn.getText().trim(),
                        true
                );

                // Ekleme Denemesi (Aynı ISBN varsa raf.kitapEkle hata fırlatır)
                raf.kitapEkle(yeniKitap);

                // 3. Dosyaya Yaz
                // Nesneye "Kendini Kaydet" diyoruz.
                if (yeniKitap.kaydet()) {
                    JOptionPane.showMessageDialog(this, "Kitap başarıyla eklendi.");
                    this.dispose(); // Pencereyi kapat
                } else {
                    JOptionPane.showMessageDialog(this, "Kayıt sırasında hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
                }

            } catch (GecersizGirisBilgisiException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Uyarı", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        card.add(Box.createVerticalStrut(20));
        card.add(btnKaydet);
        mainPanel.add(card);
    }

    // Yardımcı Metotlar
    private JTextField createField() {
        JTextField tf = new JTextField(15);
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return tf;
    }

    private void addLabel(JPanel p, String text, JTextField field) {
        JLabel l = new JLabel(text);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(5));
        p.add(field);
        p.add(Box.createVerticalStrut(10));
    }
}