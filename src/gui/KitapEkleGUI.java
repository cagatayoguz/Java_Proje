package gui;

import model.Kitap;
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

        // --- Kart Tasarımı ---
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(30, 30, 30, 30)
        ));

        // Başlık
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

        // --- BUTON İŞLEM MANTIĞI (OOP ENTEGRASYONU) ---
        btnKaydet.addActionListener(e -> {
            try {
                // 1. Validasyon
                if (txtAd.getText().trim().isEmpty() || txtIsbn.getText().trim().isEmpty()) {
                    throw new GecersizGirisBilgisiException("Kitap Adı ve ISBN zorunludur.");
                }

                // 2. Nesne Oluşturma
                // Model sınıfındaki constructor'a uygun olarak String durum ("Müsait") gönderiyoruz.
                Kitap yeniKitap = new Kitap(
                        txtAd.getText().trim(),
                        txtYazar.getText().trim(),
                        txtIsbn.getText().trim(),
                        "Müsait" // Varsayılan durum
                );

                // 3. Kaydetme İşlemi (Interface Kullanımı)
                // "yeniKitap" nesnesi, "Kaydedilebilir" interface'ini uyguladığı için
                // kaydet() metoduna sahiptir ve bu metot arka planda dosyaya yazar.
                if (yeniKitap.kaydet()) {
                    JOptionPane.showMessageDialog(this, "Kitap kütüphaneye başarıyla eklendi.");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Kayıt sırasında hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
                }

            } catch (GecersizGirisBilgisiException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Uyarı", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Sistem hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
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