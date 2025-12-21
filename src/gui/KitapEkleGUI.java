package gui;

import model.Kitap;
import exception.GecersizGirisBilgisiException;
import model.Kutuphane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class KitapEkleGUI extends JFrame {

    public KitapEkleGUI() {
        // Pencerenin başlığını ve boyutlarını ayarladım
        setTitle("Hızlı Kitap Ekle");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Kapatınca ana menü gitmesin diye dispose yaptım
        setLocationRelativeTo(null); // Ekranın ortasında açılsın

        // Ana paneli oluşturuyoruz, düzen için GridBagLayout kullandım
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(248, 249, 250)); // Arka plan rengi açık gri
        setContentPane(mainPanel);

        // --- Tasarım Kısmı ---
        // Kitap bilgilerini gireceğimiz beyaz kart görünümü
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS)); // Alt alta dizilsinler
        card.setBackground(Color.WHITE);
        // Kenarlarına ince çizgi ve iç boşluk ekledim ki güzel dursun
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(30, 30, 30, 30)
        ));

        // Formun Başlığı
        JLabel lblTitle = new JLabel("Kitap Kaydı");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Ortaya hizaladım
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(20)); // Araya biraz boşluk attım

        // Kullanıcının yazı yazacağı kutucukları oluşturuyoruz
        // createField metodunu aşağıda ben yazdım, sürekli aynı kodu yazmamak için
        JTextField txtAd = createField();
        JTextField txtYazar = createField();
        JTextField txtIsbn = createField();

        // Kutucukları etiketleriyle beraber ekliyoruz
        addLabel(card, "Kitap Adı:", txtAd);
        addLabel(card, "Yazar:", txtYazar);
        addLabel(card, "ISBN No:", txtIsbn);

        // Kaydet Butonu özellikleri
        JButton btnKaydet = new JButton("Kaydet");
        btnKaydet.setBackground(new Color(13, 110, 253)); // Mavi renk
        btnKaydet.setForeground(Color.WHITE); // Yazısı beyaz olsun
        btnKaydet.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnKaydet.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKaydet.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // --- BUTONA BASINCA NE OLACAK? ---
        // ... KitapEkleGUI buton aksiyonu kısmı ...

        btnKaydet.addActionListener(e -> {
            try {
                // 1. Validasyon (Boş alan kontrolü)
                if (txtAd.getText().trim().isEmpty() || txtIsbn.getText().trim().isEmpty()) {
                    throw new GecersizGirisBilgisiException("Kitap adı ve ISBN zorunludur.");
                }

                // --- İŞTE BURADA KUTUPHANE SINIFINI KULLANIYORUZ ---

                // Sanal bir kütüphane yöneticisi oluşturuyoruz
                Kutuphane merkezKutuphane = new Kutuphane("Merkez Kütüphane");

                // Eklenecek adayı oluşturuyoruz
                Kitap adayKitap = new Kitap(
                        txtAd.getText().trim(),
                        txtYazar.getText().trim(),
                        txtIsbn.getText().trim(),
                        "Müsait"
                );

                // 2. Kütüphaneye "Bunu ekleyebilir miyim?" diye soruyoruz.
                // Bu metot; Kapasite 50'yi geçti mi? Aynı ISBN var mı? diye bakar.
                // Eğer sorun varsa HATA FIRLATIR ve kod catch bloğuna atlar (Kaydetmez).
                merkezKutuphane.kitapEkle(adayKitap);

                // 3. Eğer yukarıdaki satır hata vermediyse, onay alınmıştır. Kaydedebiliriz.
                // İsterseniz direkt kitap üzerinden, isterseniz kütüphane üzerinden kaydedebilirsiniz.
                if (adayKitap.kaydet()) { // Veya merkezKutuphane.kaydet();
                    JOptionPane.showMessageDialog(this, "Kitap başarıyla eklendi.");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Kayıt hatası oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                }

            } catch (GecersizGirisBilgisiException ex) {
                // Kütüphane sınıfından fırlatılan "Kapasite Dolu" veya "Aynı ISBN Var" hataları burada yakalanır.
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Engellendi", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Butonu da karta ekleyip bitiriyoruz
        card.add(Box.createVerticalStrut(20));
        card.add(btnKaydet);
        mainPanel.add(card);
    }

    // --- Yardımcı Metotlar ---
    // Kod tekrarı yapmamak için text alanlarını burada oluşturuyorum
    private JTextField createField() {
        JTextField tf = new JTextField(15);
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return tf;
    }

    // Etiket ve text alanını düzgünce eklemek için metot
    private void addLabel(JPanel p, String text, JTextField field) {
        JLabel l = new JLabel(text);
        l.setAlignmentX(Component.LEFT_ALIGNMENT); // Sola yasla
        p.add(l);
        p.add(Box.createVerticalStrut(5)); // Biraz boşluk
        p.add(field);
        p.add(Box.createVerticalStrut(10)); // Sonraki elemanla arası açılsın
    }
}