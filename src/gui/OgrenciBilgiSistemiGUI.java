package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import exception.OgrenciBulunamadiException;

// Öğrenci numarası üzerinden veritabanı sorgulaması yapan ve
// sonuçları kullanıcıya gösteren arayüz sınıfı.
public class OgrenciBilgiSistemiGUI extends JFrame {

    public OgrenciBilgiSistemiGUI() {
        // Pencere yapılandırması (Başlık, Boyut, Konum) gerçekleştirildi.
        setTitle("Öğrenci Sorgulama");
        setSize(500, 400);

        // Pencere kapatıldığında ana menünün açık kalması için DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Ana Düzen (Layout) ---
        // Formun ekranın tam ortasında durması için GridBagLayout tercih edildi.
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(248, 249, 250)); // Açık gri arka plan
        setContentPane(mainPanel);

        // --- Kart Paneli Tasarımı ---
        // Görsel bütünlük sağlamak amacıyla bileşenler beyaz bir kart paneli içinde gruplandı.
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS)); // Dikey hizalama
        cardPanel.setBackground(Color.WHITE);

        // Karta ince bir çerçeve ve iç boşluk (padding) eklendi.
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(40, 40, 40, 40)
        ));

        // Başlık ve Açıklama Etiketleri
        JLabel lblTitle = new JLabel("Öğrenci Bilgisi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDesc = new JLabel("Numara ile sorgulama yapın");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDesc.setForeground(Color.GRAY);
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Veri Giriş Alanı (Input)
        JTextField txtNo = new JTextField(15);
        txtNo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtNo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Input alanına modern bir görünüm için özel kenarlık tanımlandı.
        txtNo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10)
        ));

        // Sorgula Butonu
        JButton btnSorgula = new JButton("Sorgula");
        btnSorgula.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSorgula.setForeground(Color.WHITE);
        btnSorgula.setBackground(new Color(13, 110, 253));
        btnSorgula.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnSorgula.setFocusPainted(false);
        btnSorgula.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- Bileşenlerin Karta Eklenmesi ---
        cardPanel.add(lblTitle);
        cardPanel.add(lblDesc);
        cardPanel.add(Box.createVerticalStrut(20)); // Boşluk
        cardPanel.add(new JLabel("Öğrenci Numarası:"));
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(txtNo);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(btnSorgula);

        mainPanel.add(cardPanel);

        // --- BUTON AKSİYONU ve HATA YÖNETİMİ ---
        btnSorgula.addActionListener(e -> {
            String ogrNo = txtNo.getText().trim();

            // 1. Validasyon: Boş giriş kontrolü
            if (ogrNo.isEmpty())
                return;

            try {
                // 2. Servis Katmanı Çağrısı

      // Veritabanı işlemleri için DosyaIslemleri sınıfı kullanıldı.
                // Eğer numara bulunamazsa, bu metot 'OgrenciBulunamadiException' fırlatır.
                String[] bilgiler = DosyaIslemleri.ogrenciGetir(ogrNo);
                // 3. Sonuç Gösterimi
                String mesaj = String.format(
                        "Ad: %s\nSoyad: %s\nBölüm: %s\nSınıf: %s\nOrtalama: %s",
                        bilgiler[0], bilgiler[1], bilgiler[2], bilgiler[3], bilgiler[4]);

                JOptionPane.showMessageDialog(this, mesaj, "Öğrenci Bulundu", JOptionPane.INFORMATION_MESSAGE);


            } catch (OgrenciBulunamadiException ex) {
                // 4. Özel İstisna Yönetimi (Custom Exception Handling)
                // Hata yakalandığında, Exception sınıfı içindeki özelleştirilmiş mesaj kullanıcıya gösterildi.
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}