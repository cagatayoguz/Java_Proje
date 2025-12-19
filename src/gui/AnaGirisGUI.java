package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class AnaGirisGUI extends JFrame {

    // Arka plan görselinin yolu (Adım 1'de buraya koyduğundan emin ol)
    private static final String ARKA_PLAN_RESMI = "veriler/resimler/image_1405d8.png";

    public AnaGirisGUI() {
        setTitle("Üniversite Otomasyon Sistemi");
        setSize(1000, 600); // Geniş ve modern bir boyut
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın ortasında aç

        // --- 1. Arka Plan Paneli ---
        // Özel bir panel oluşturuyoruz, bu panel resmi çizecek.
        BackgroundPanel backgroundPanel = new BackgroundPanel(ARKA_PLAN_RESMI);
        // Ana düzeni BorderLayout yapıyoruz ki butonları alta sabitleyebilelim.
        backgroundPanel.setLayout(new BorderLayout());
        // Bu paneli pencerenin ana içeriği olarak ayarlıyoruz.
        setContentPane(backgroundPanel);


        // --- 2. Üst Kısım (Başlık - İsteğe Bağlı) ---
        // Şık görünmesi için üst tarafa bir başlık veya logo alanı ekleyebiliriz.
        JLabel lblBaslik = new JLabel("Üniversite Bilgi ve Yönetim Sistemi", SwingConstants.CENTER);
        lblBaslik.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblBaslik.setForeground(Color.WHITE); // Yazı rengi beyaz
        // Yazının etrafına biraz gölge efekti vererek okunabilirliği artıralım
        lblBaslik.setBorder(new EmptyBorder(50, 0, 0, 0));
        backgroundPanel.add(lblBaslik, BorderLayout.NORTH);


        // --- 3. Alt Kısım (Zarif Butonlar) ---
        // Butonları tutacak panel. Şeffaf olacak.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        buttonPanel.setOpaque(false); // Panelin kendisi görünmez, sadece içindekiler görünür.
        buttonPanel.setBorder(new EmptyBorder(0, 0, 50, 0)); // Alttan boşluk bırak

        // Buton 1: Bilgi Sistemi Girişi
        JButton btnBilgi = zarifButonOlustur("Bilgi Sistemi Girişi");
        btnBilgi.addActionListener(e -> {
            new BilgiMenuGUI().setVisible(true);
            // this.dispose(); // İstersen bu ana ekranı kapatabilirsin
        });

        // Buton 2: Yönetim Paneli Girişi
        JButton btnYonetim = zarifButonOlustur("Yönetim Paneli Girişi");
        btnYonetim.addActionListener(e -> {
            // Burada normalde bir şifre ekranı olur ama şimdilik direkt açalım
            new YonetimMenuGUI().setVisible(true);
            // this.dispose();
        });

        // İstersen bir de Çıkış butonu ekleyebilirsin (Opsiyonel)
        /*
        JButton btnCikis = zarifButonOlustur("Çıkış");
        btnCikis.setBackground(new Color(200, 50, 50)); // Kırmızımsı
        btnCikis.addActionListener(e -> System.exit(0));
        buttonPanel.add(btnCikis);
        */

        buttonPanel.add(btnBilgi);
        buttonPanel.add(btnYonetim);

        // Buton panelini ana panelin altına (SOUTH) ekle
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // --- Yardımcı Metot: Zarif Buton Oluşturma ---
    private JButton zarifButonOlustur(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setForeground(Color.WHITE); // Yazı rengi
        btn.setBackground(new Color(40, 60, 100, 220)); // Yarı saydam koyu mavi arka plan
        btn.setFocusPainted(false); // Tıklanınca çıkan çerçeveyi kaldır
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1), // İnce beyaz çerçeve
                BorderFactory.createEmptyBorder(15, 30, 15, 30) // İç boşluk (Padding)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Üzerine gelince el işareti çıksın

        // Fare üzerine gelince renk değişimi efekti (Hover)
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(60, 80, 140, 240)); // Daha açık ve parlak mavi
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(new Color(40, 60, 100, 220)); // Normal haline dön
            }
        });
        return btn;
    }

    // --- İç Sınıf: Arka Plan Resmini Çizen Özel Panel ---
    // Bu sınıf sadece bu dosyada kullanılacağı için "inner class" olarak tanımladık.
    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String fileName) {
            try {
                File file = new File(fileName);
                if (file.exists()) {
                    backgroundImage = new ImageIcon(fileName).getImage();
                } else {
                    System.err.println("Arka plan resmi bulunamadı: " + fileName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Panelin çizim metodunu (paintComponent) geçersiz kılıyoruz (override).
        // Böylece panel boyanırken önce bizim resmimizi çiziyor.
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Resmi panelin boyutuna göre ölçekleyerek çiz
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }

    // Test için main metodu (Normalde uygulamanın ana main'inden çağrılır)
    public static void main(String[] args) {
        // Arayüzün daha modern görünmesi için "Nimbus" temasını deneyelim
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Nimbus yoksa varsayılan tema kullanılır, sorun değil.
        }

        SwingUtilities.invokeLater(() -> new AnaGirisGUI().setVisible(true));
    }
}