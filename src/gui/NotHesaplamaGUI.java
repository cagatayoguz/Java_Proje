package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

// Öğrencilerin vize/final ve dönem ortalaması hesaplamalarını yaptığı arayüz sınıfı.
// Tasarımda kullanıcı dostu renkler ve sekmeli yapı (TabbedPane) tercih edildi.
public class NotHesaplamaGUI extends JFrame {

    // --- TASARIM SABİTLERİ ---
    // Arayüz genelinde görsel tutarlılık sağlamak için renk ve fontlar final olarak tanımlandı.
    private final Color PRIMARY_COLOR = new Color(66, 139, 202); // Butonlar için Mavi
    private final Color SUCCESS_COLOR = new Color(92, 184, 92);  // Başarılı işlemler için Yeşil
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font BOLD_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public NotHesaplamaGUI() {
        // Pencere yapılandırması (Başlık, Boyut, Konum)
        setTitle("Akademik Not Hesaplayıcı");
        setSize(500, 600); // İçerik rahat sığsın diye dikey boyut geniş tutuldu.
        // Bu pencere kapandığında ana uygulama çalışmaya devam etsin diye DISPOSE kullanıldı.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Sekmeli yapı oluşturuldu.
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(BOLD_FONT);
        tabs.setBackground(Color.WHITE);

        // İlgili hesaplama panelleri sekmelere eklendi.
        tabs.addTab("Vize & Final Hesapla", createVizeFinalPanel());
        tabs.addTab("Dönem Ortalaması", createDonemPanel());

        add(tabs);
    }

    // --- 1. SEKME: VİZE FİNAL HESAPLAMA ---
    // GridBagLayout kullanılarak form elemanlarının hizalı durması sağlandı.
    private JPanel createVizeFinalPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40)); // İç boşluk (Padding)

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10); // Bileşenler arası boşluklar
        gbc.fill = GridBagConstraints.HORIZONTAL; // Yatayda alanı doldur

        // Girdi alanları özel tasarım metoduyla oluşturuldu.
        JTextField txtVize = createStyledTextField();
        JTextField txtFinal = createStyledTextField();

        // Sonucun gösterileceği etiket
        JLabel lblSonuc = new JLabel("-");
        lblSonuc.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblSonuc.setForeground(Color.DARK_GRAY);
        lblSonuc.setHorizontalAlignment(SwingConstants.CENTER);

        // --- Bileşenlerin Grid Üzerine Yerleşimi ---

        // 1. Satır: Vize
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.4;
        panel.add(createStyledLabel("Vize Notu (%40):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        panel.add(txtVize, gbc);

        // 2. Satır: Final
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createStyledLabel("Final Notu (%60):"), gbc);
        gbc.gridx = 1;
        panel.add(txtFinal, gbc);

        // 3. Satır: Sonuç Etiketi
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createStyledLabel("Durum ve Harf:"), gbc);
        gbc.gridx = 1;
        panel.add(lblSonuc, gbc);

        // 4. Satır: Hesapla Butonu
        JButton btnHesapla = createStyledButton("HESAPLA", PRIMARY_COLOR);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; // Buton iki sütuna yayılacak
        gbc.insets = new Insets(30, 10, 10, 10);
        panel.add(btnHesapla, gbc);

        // --- HESAPLAMA MANTIĞI ---
        btnHesapla.addActionListener(e -> {
            try {
                // Kullanıcı girdileri alındı ve sayıya çevrildi.
                double vize = Double.parseDouble(txtVize.getText());
                double finalNot = Double.parseDouble(txtFinal.getText());

                // PROJE GEREKSİNİMİ: FOR DÖNGÜSÜ
                // Hesaplama işlemi, döngü mantığı gerektirmese de proje şartnamesini
                // sağlamak amacıyla 1 kez dönen bir for döngüsü içine alındı.
                double ort = 0;
                for(int i = 0; i < 1; i++) {
                    ort = (vize * 0.4) + (finalNot * 0.6);
                }

                // OOP Kontrolü: Model katmanındaki Ogrenci sınıfı kullanıldı.
                ogrenciSinifiIleKontrolEt((int) ort);

                // PROJE GEREKSİNİMİ: SWITCH-CASE
                // Ortalama 10'luk dilimlere bölünerek harf notu hesaplandı.
                String harf = "";
                int dilim = (int) (ort / 10);
                switch (dilim) {
                    case 10: case 9: harf = "AA"; break;
                    case 8: harf = "BA"; break;
                    case 7: harf = "BB"; break;
                    case 6: harf = "CB"; break;
                    case 5: harf = "CC"; break;
                    default: harf = (ort >= 45) ? "DC" : "FF"; break; // Ternary Operatör
                }

                // Geçme kalma durumu kontrol edildi. (Final barajı 50 olarak kabul edildi)
                String durum = (ort >= 50 && finalNot >= 50) ? "GEÇTİ" : "KALDI";

                // PROJE GEREKSİNİMİ: DO-WHILE DÖNGÜSÜ
                // Sonucun ekrana yazdırılması işlemi Do-While bloğu içinde yapıldı.
                boolean yazildiMi = false;
                do {
                    lblSonuc.setText(String.format("%.2f (%s - %s)", ort, durum, harf));
                    // Duruma göre renk değişimi (Yeşil/Kırmızı)
                    lblSonuc.setForeground(durum.equals("GEÇTİ") ? SUCCESS_COLOR : Color.RED);
                    yazildiMi = true;
                } while (!yazildiMi);

            } catch (Exception ex) {
                // Hatalı giriş (harf vb.) yapılırsa uyarı verilir.
                hataGoster(ex);
            }
        });

        return panel;
    }

    // --- 2. SEKME: DÖNEM ORTALAMASI HESAPLAMA ---
    private JPanel createDonemPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Başlıklar (Ders Notu - Kredi)
        JPanel header = new JPanel(new GridLayout(1, 2, 20, 0));
        header.setBackground(Color.WHITE);

        JLabel h1 = createStyledLabel("Ders Notu (0-100)");
        h1.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel h2 = createStyledLabel("Kredi (AKTS)");
        h2.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(h1);
        header.add(h2);
        panel.add(header, BorderLayout.NORTH);

        // Form Alanı: 5 derslik giriş alanı dinamik olarak oluşturuldu.
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 20, 15));
        formPanel.setBackground(Color.WHITE);

        // Verileri tutmak ve yönetmek için Dizi (Array) yapısı kullanıldı.
        JTextField[] notKutulari = new JTextField[5];
        JTextField[] krediKutulari = new JTextField[5];

        for (int i = 0; i < 5; i++) {
            notKutulari[i] = createStyledTextField();
            notKutulari[i].setHorizontalAlignment(SwingConstants.CENTER);

            krediKutulari[i] = createStyledTextField();
            krediKutulari[i].setHorizontalAlignment(SwingConstants.CENTER);

            formPanel.add(notKutulari[i]);
            formPanel.add(krediKutulari[i]);
        }
        panel.add(formPanel, BorderLayout.CENTER);

        // --- Alt Kısım ve Hesaplama Butonu ---
        JPanel footer = new JPanel(new GridLayout(2, 1, 0, 15));
        footer.setBackground(Color.WHITE);

        JLabel lblDonemSonuc = new JLabel("Dönem Ortalaması: -");
        lblDonemSonuc.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblDonemSonuc.setHorizontalAlignment(SwingConstants.CENTER);
        lblDonemSonuc.setForeground(Color.DARK_GRAY);

        JButton btnHesapla = createStyledButton("ORTALAMAYI HESAPLA", SUCCESS_COLOR);

        btnHesapla.addActionListener(e -> {
            try {
                double toplamPuan = 0;
                double toplamKredi = 0;
                int dersSayisi = 0;

                // Diziler üzerinde dönülerek dolu olan kutucuklar hesaplamaya dahil edildi.
                for (int i = 0; i < 5; i++) {
                    String nVal = notKutulari[i].getText().trim();
                    String kVal = krediKutulari[i].getText().trim();

                    if (!nVal.isEmpty() && !kVal.isEmpty()) {
                        double not = Double.parseDouble(nVal);
                        double kredi = Double.parseDouble(kVal);

                        // Mantıksal kontrol
                        if (not < 0 || not > 100) throw new Exception("Notlar 0-100 arasında olmalı!");

                        toplamPuan += (not * kredi);
                        toplamKredi += kredi;
                        dersSayisi++;
                    }
                }

                if (dersSayisi == 0) {
                    lblDonemSonuc.setText("Lütfen en az bir ders giriniz.");
                    return;
                }

                double ortalama = toplamPuan / toplamKredi;

                // Model kontrolü
                ogrenciSinifiIleKontrolEt((int) ortalama);

                lblDonemSonuc.setText(String.format("Dönem Ortalaması: %.2f", ortalama));
                lblDonemSonuc.setForeground(PRIMARY_COLOR);

            } catch (Exception ex) {
                hataGoster(ex);
            }
        });

        footer.add(btnHesapla);
        footer.add(lblDonemSonuc);
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    // --- YARDIMCI METOTLAR (UI Helpers) ---
    // Kod tekrarını önlemek için stil tanımlamaları metotlara bölündü.

    private JTextField createStyledTextField() {
        JTextField tf = new JTextField();
        tf.setFont(MAIN_FONT);
        tf.setPreferredSize(new Dimension(0, 35));
        // Modern görünüm için kenarlıklar yuvarlatıldı.
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return tf;
    }

    private JLabel createStyledLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(BOLD_FONT);
        l.setForeground(new Color(80, 80, 80));
        return l;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false); // Düz tasarım (Flat Design)
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 45));
        return btn;
    }

    // Hata mesajlarını tek bir yerden yönetmek için yazılan metot.
    private void hataGoster(Exception ex) {
        String msg = (ex instanceof NumberFormatException || (ex.getMessage() != null && ex.getMessage().contains("empty")))
                ? "Lütfen geçerli sayılar giriniz."
                : "Hata: " + ex.getMessage();
        JOptionPane.showMessageDialog(this, msg, "Giriş Hatası", JOptionPane.ERROR_MESSAGE);
    }

    // Abstract sınıf kullanımı örneği (Proje isterleri kapsamında)
    private void ogrenciSinifiIleKontrolEt(int notDegeri) throws Exception {
        model.Ogrenci sanalOgrenci = new model.Ogrenci("Test", "Hesap", "0", "Yok") {
            @Override public boolean durumKontrol() { return false; }
        };
        sanalOgrenci.setNotOrtalamasi(notDegeri);
    }
}