package gui;

import service.DosyaIslemleri;
import model.LisansOgrenci; // Polimorfizm için kullanılan alt sınıf
import model.Ogrenci;       // Soyut (Abstract) temel sınıf
import exception.GecersizGirisBilgisiException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

// Yönetici panelinden sisteme yeni öğrenci kaydı yapılmasını sağlayan arayüz sınıfı.
// Bu sınıf, veri doğrulama işlemlerini model katmanındaki (Ogrenci) sınıflar üzerinden gerçekleştirir.
public class OgrenciEkleGUI extends JFrame {

    public OgrenciEkleGUI() {
        // Pencere yapılandırması (Başlık, Boyut, Konum) ayarlandı.
        setTitle("Yeni Öğrenci Kaydı");
        setSize(500, 600);
        // Bu pencere kapandığında ana menü açık kalsın diye DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // GridBagLayout kullanılarak form elemanlarının ortalanması sağlandı.
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(248, 249, 250)); // Açık gri arka plan
        setContentPane(mainPanel);

        // --- FORM KARTI ---
        // Bileşenlerin görsel bütünlüğü için dikey (Y_AXIS) hizalanmış bir kart paneli oluşturuldu.
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);

        // Karta çerçeve ve iç boşluk (Padding) eklendi.
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(40, 40, 40, 40)
        ));

        // Başlık
        JLabel lblTitle = new JLabel("Öğrenci Ekle");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblTitle);
        cardPanel.add(Box.createVerticalStrut(20)); // Dikey boşluk

        // Form Girdi Alanları (Yardımcı metot ile üretildi)
        JTextField txtAd = createField();
        JTextField txtSoyad = createField();
        JTextField txtNo = createField();
        JTextField txtBolum = createField();
        JTextField txtSinif = createField();
        JTextField txtOrt = createField();

        // Alanların Etiketleriyle Birlikte Panele Eklenmesi
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
        btnKaydet.setBackground(new Color(13, 110, 253)); // Kurumsal Mavi
        btnKaydet.setFocusPainted(false);
        btnKaydet.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKaydet.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnKaydet.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- OOP ENTEGRASYONU VE İŞ MANTIĞI ---
        btnKaydet.addActionListener(e -> {
            try {
                // 1. Temel Doğrulama (Basic Validation)
                // Form alanlarının boş olup olmadığı kontrol edildi.
                if (txtAd.getText().trim().isEmpty()) throw new GecersizGirisBilgisiException("Ad boş olamaz.");
                if (txtSoyad.getText().trim().isEmpty()) throw new GecersizGirisBilgisiException("Soyad boş olamaz.");
                if (txtNo.getText().trim().isEmpty()) throw new GecersizGirisBilgisiException("Öğrenci No boş olamaz.");
                if (txtBolum.getText().trim().isEmpty()) throw new GecersizGirisBilgisiException("Bölüm boş olamaz.");

                // 2. Nesne Oluşturma (Polimorfizm)
                // Veriler doğrudan dosyaya yazılmadan önce bir 'Ogrenci' nesnesine dönüştürüldü.
                // 'LisansOgrenci' (Concrete Class) üretilip, 'Ogrenci' (Abstract Class) referansında tutuldu.
                Ogrenci yeniOgrenci = new LisansOgrenci(
                        txtAd.getText().trim(),
                        txtSoyad.getText().trim(),
                        txtNo.getText().trim(),
                        txtBolum.getText().trim()
                );

                // 3. Kapsülleme (Encapsulation) ile Veri Kontrolü
                // Setter metotları, sınıf içindeki kuralları (Validation Rules) tetikler.
                yeniOgrenci.setBolum(txtBolum.getText().trim());

                // Ortalama alanının boşluk kontrolü
                if (txtOrt.getText().trim().isEmpty()) throw new GecersizGirisBilgisiException("Ortalama boş olamaz.");

                try {
                    // Sayısal veri dönüşümü
                    int ortDeger = Integer.parseInt(txtOrt.getText().trim());

                    // BURASI KRİTİK: Nesneye "Notumu ayarla" diyoruz.
                    // Eğer kullanıcı 0-100 dışı bir değer girdiyse, Ogrenci sınıfı hata fırlatacak.
                    // Böylece iş mantığı (Business Logic) arayüz kodundan ayrıştırılmış oldu.
                    yeniOgrenci.setNotOrtalamasi(ortDeger);

                } catch (NumberFormatException nfe) {
                    throw new GecersizGirisBilgisiException("Ortalama sayısal bir değer olmalıdır.");
                }

                // 4. Kalıcılık (Persistence)
                // Nesne üzerindeki doğrulanmış veriler dosya yazma servisine iletildi.
                DosyaIslemleri.ogrenciEkle(
                        yeniOgrenci.getAd(),
                        yeniOgrenci.getSoyad(),
                        yeniOgrenci.getBolum(),
                        yeniOgrenci.getOgrenciNo(),
                        txtSinif.getText().trim(), // Sınıf bilgisi modelde tutulmadığı için direkt alındı.
                        String.valueOf(txtOrt.getText().trim())
                );

                // İsteğe bağlı: Polimorfik metot çağrısı testi (Konsol çıktısı için)
                System.out.println(yeniOgrenci.detayliRaporOlustur());

                // Başarılı işlem bildirimi
                JOptionPane.showMessageDialog(this, "Öğrenci başarıyla eklendi.");
                this.dispose();

            } catch (GecersizGirisBilgisiException ex) {
                // Model katmanından (Ogrenci sınıfı) fırlatılan iş mantığı hataları burada yakalanır.
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Geçersiz İşlem", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                // Beklenmedik sistem hataları
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage(), "Sistem Hatası", JOptionPane.ERROR_MESSAGE);
            }
        });

        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(btnKaydet);
        mainPanel.add(cardPanel);
    }

    // --- Yardımcı Metotlar (UI Helpers) ---
    // Kod tekrarını önlemek için bileşen üretimleri metotlara bölündü.

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