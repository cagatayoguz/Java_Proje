package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

// Uygulamanın giriş kapısı olan Ana Menü ekranı.
// JFrame'den miras aldık, standart pencere özellikleri için.
public class AnaMenuGUI extends JFrame {

    // Arka plan resminin yolu.
    private static final String ARKA_PLAN_RESMI = "C:\\Users\\cagat\\Downloads\\gazi kampüs2.jpeg";

    public AnaMenuGUI() {
        setTitle("Üniversite Otomasyon Sistemi");
        setSize(1000, 600); // Ekranı doldursun diye geniş bir boyut verdim

        // Çarpıya basınca program tamamen kapansın (Ana menü olduğu için)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Pencere tam ortada açılsın

        // --- 1. Arka Plan Paneli ---
        BackgroundPanel backgroundPanel = new BackgroundPanel(ARKA_PLAN_RESMI);
        backgroundPanel.setLayout(new BorderLayout()); // Elemanları Kuzey-Güney diye dizeceğiz
        setContentPane(backgroundPanel); // Bu paneli ana içerik paneli yap

        // --- 2. Üst Kısım (Başlık) ---
        JLabel lblBaslik = new JLabel("Üniversite Bilgi ve Yönetim Sistemi", SwingConstants.CENTER);
        lblBaslik.setFont(new Font("SansSerif", Font.BOLD, 25));

        // Yazı rengi ayarlandı
        lblBaslik.setForeground(new Color(42, 15, 31));

        // Yazı tavana yapışmasın diye üstten biraz boşluk bıraktım
        lblBaslik.setBorder(new EmptyBorder(5, 0, 0, 0));
        backgroundPanel.add(lblBaslik, BorderLayout.NORTH);

        // --- 3. Alt Kısım (Butonlar) ---
        // Butonları yan yana ortalayarak dizmek için FlowLayout kullandım
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        buttonPanel.setOpaque(false); // Şeffaf yaptım ki arkadaki üniversite resmi görünsün
        buttonPanel.setBorder(new EmptyBorder(0, 0, 60, 0)); // Alt taraftan biraz yukarı itelim

        // Buton 1: Bilgi Sistemi
        // Aşağıdaki 'zarifButonOlustur' metodunu kullanarak kod tekrarından kurtuldum
        JButton btnBilgi = createCard("Bilgi Sistemi");
        btnBilgi.addActionListener(e -> {
            // Bilgi menüsünü aç
            new BilgiMenuGUI().setVisible(true);
            // Eğer ana menü kapansın istersek buraya this.dispose() ekleyebiliriz ama kalsın.
        });

        // Buton 2: Yönetim Paneli
        JButton btnYonetim = createCard("Yönetim Paneli");
        btnYonetim.addActionListener(e -> {
            // Yönetim paneline herkes giremez, önce Giriş (Login) ekranına yönlendiriyorum
            new YonetimGirisGUI().setVisible(true);
        });

        // Buton 3: Çıkış
        JButton btnCikis = createCard("Çıkış");

        // Çıkış butonu dikkat çeksin diye kırmızımsı yaptım
        btnCikis.setBackground(new Color(180, 60, 60, 220));

        // Çıkış butonuna özel hover (üzerine gelince renk değişimi) ekledim
        btnCikis.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnCikis.setBackground(new Color(200, 80, 80, 240)); // Daha açık kırmızı
            }
            public void mouseExited(MouseEvent evt) {
                btnCikis.setBackground(new Color(180, 60, 60, 220)); // Eski haline dön
            }
        });

        // Tıklanınca programı kapat
        btnCikis.addActionListener(e -> System.exit(0));

        // Butonları panele ekle
        buttonPanel.add(btnBilgi);
        buttonPanel.add(btnYonetim);
        buttonPanel.add(btnCikis);

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // --- Yardımcı Metot: Zarif Buton Tasarımı ---
    // Her buton için aynı kodları kopyala-yapıştır yapmamak için bu metodu yazdım.
    private JButton createCard(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setForeground(Color.WHITE); // Yazı rengi beyaz
        btn.setBackground(new Color(40, 60, 100, 220)); // Lacivert, biraz saydam (220)
        btn.setFocusPainted(false); // Tıklayınca çıkan o çirkin çerçeveyi kaldır

        // Kenarlık ayarları: Dışta beyaz çizgi, içte boşluk
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 2),
                BorderFactory.createEmptyBorder(15, 40, 15, 40)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Üzerine gelince el işareti çıksın

        // Butonların üzerine gelince renk değişsin (Hover Efekti)
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                // Çıkış butonu kırmızı kalsın, onu mavi yapmayalım diye kontrol ediyorum
                if (!btn.getText().equals("Çıkış")) {
                    btn.setBackground(new Color(60, 80, 140, 240)); // Daha açık mavi
                }
            }
            public void mouseExited(MouseEvent evt) {
                if (!btn.getText().equals("Çıkış")) {
                    btn.setBackground(new Color(40, 60, 100, 220)); // Orijinal renk
                }
            }
        });
        return btn;
    }

    // --- İç Sınıf (Inner Class): Arka Plan Resmi ---
    // JPanel normalde sadece düz renk boyar. Resim çizdirmek için paintComponent metodunu
    // ezmemiz (override) gerekiyor. Bu yüzden bu özel sınıfı yazdım.
    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String fileName) {
            try {
                File file = new File(fileName);
                if (file.exists()) {
                    // Resmi dosyadan yükle
                    backgroundImage = new ImageIcon(fileName).getImage();
                }
            } catch (Exception e) {
                // Hata olursa (dosya yoksa vs.) konsola bas ama programı çökertme
                e.printStackTrace();
            }
        }

        // Çizim işini yapan asıl metot burası
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Önce paneli temizle
            if (backgroundImage != null) {
                // Resmi panelin genişlik ve yüksekliğine göre sündürerek (scale) çiz
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }
}