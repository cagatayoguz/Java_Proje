package gui;

import model.Kitap;
import model.Kutuphane;
import service.KutuphaneServisi;
import exception.GecersizGirisBilgisiException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

// Kullanıcının sisteme yeni kitap girişi yapmasını sağlayan arayüz sınıfı.
public class KitapEkleGUI extends JFrame {

    public KitapEkleGUI() {
        // Pencere yapılandırması (Başlık, Boyut, Konum) gerçekleştirildi.
        setTitle("Hızlı Kitap Ekle");
        setSize(400, 450);
        // Bu pencere kapatıldığında ana menü açık kalsın diye DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Formun ekranın tam ortasında ve estetik durması için GridBagLayout düzeni kullanıldı.
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(248, 249, 250)); // Kurumsal gri arka plan
        setContentPane(mainPanel);

        // --- Kart Paneli Tasarımı ---
        // Bileşenleri gruplamak ve modern bir görünüm sağlamak amacıyla kart yapısı kurgulandı.
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS)); // Dikey hizalama
        card.setBackground(Color.WHITE);

        // Karta ince bir çerçeve ve iç boşluk (padding) eklendi.
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(30, 30, 30, 30)
        ));

        // Başlık etiketi
        JLabel lblTitle = new JLabel("Kitap Kaydı");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(20)); // Dikey boşluk

        // --- Form Alanlarının Oluşturulması ---
        // Kod tekrarını önlemek için yardımcı metot (createField) kullanıldı.
        JTextField txtAd = createField();
        JTextField txtYazar = createField();
        JTextField txtIsbn = createField();

        // Etiket ve input alanları yardımcı metotla panele eklendi.
        addLabel(card, "Kitap Adı:", txtAd);
        addLabel(card, "Yazar:", txtYazar);
        addLabel(card, "ISBN No:", txtIsbn);

        // --- Kaydet Butonu ---
        JButton btnKaydet = new JButton("Kaydet");
        btnKaydet.setBackground(new Color(13, 110, 253)); // Mavi vurgu rengi
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnKaydet.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKaydet.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // --- BUTON İŞLEM MANTIĞI ---
        btnKaydet.addActionListener(e -> {
            try {
                // 1. Validasyon Kontrolü
                // Zorunlu alanların doluluğu kontrol edildi.
                if (txtAd.getText().trim().isEmpty() || txtIsbn.getText().trim().isEmpty()) {
                    throw new GecersizGirisBilgisiException("Kitap Adı ve ISBN zorunludur.");
                }

                // 2. OOP Entegrasyonu: Kutuphane Sınıfı
                // Doğrudan dosya işlemi yerine, iş mantığı katmanı (Business Layer) olan
                // Kutuphane sınıfı üzerinden işlem yürütüldü.
                Kutuphane raf = new Kutuphane("Sanal Raf");

                // Mükerrer ISBN kontrolü yapabilmek için mevcut kitap listesi servisten çekildi
                // ve sanal rafa yüklendi.
                KutuphaneServisi servis = new KutuphaneServisi();
                raf.mevcutKitaplariYukle(servis.tumKitaplariGetir());

                // Girilen verilerle yeni bir Kitap nesnesi örneklendi.
                Kitap yeniKitap = new Kitap(
                        txtAd.getText().trim(),
                        txtYazar.getText().trim(),
                        txtIsbn.getText().trim(),
                        true // Varsayılan olarak 'Mevcut' (true) işaretlendi.
                );

                // Kutuphane sınıfındaki ekleme metodu çağrılarak mantıksal kontrol (Duplicate Check) yapıldı.
                // Eğer aynı ISBN varsa, bu metot hata fırlatacak ve işlem duracaktır.
                raf.kitapEkle(yeniKitap);

                // 3. Kalıcılık (Persistence)
                // Mantıksal kontroller geçildikten sonra veri dosyaya yazıldı.
                if (yeniKitap.kaydet()) {
                    JOptionPane.showMessageDialog(this, "Kitap başarıyla eklendi.");
                    this.dispose(); // İşlem başarılıysa pencere kapatıldı.
                } else {
                    JOptionPane.showMessageDialog(this, "Kayıt sırasında hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
                }

            } catch (GecersizGirisBilgisiException ex) {
                // Kullanıcı hatası (Validasyon) uyarısı gösterildi.
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Uyarı", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                // Sistem hataları konsola basıldı.
                ex.printStackTrace();
            }
        });

        card.add(Box.createVerticalStrut(20));
        card.add(btnKaydet);
        mainPanel.add(card);
    }

    // --- Yardımcı Metotlar (Utility Methods) ---

    // Standart boyutlarda TextField üreten metot.
    private JTextField createField() {
        JTextField tf = new JTextField(15);
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return tf;
    }

    // Label ve TextField'i düzenli bir şekilde panele ekleyen metot.
    private void addLabel(JPanel p, String text, JTextField field) {
        JLabel l = new JLabel(text);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(5));
        p.add(field);
        p.add(Box.createVerticalStrut(10));
    }
}