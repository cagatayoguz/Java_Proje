package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class AnaMenuGUI extends JFrame {

    // Arka plan görselinin yolu
    private static final String ARKA_PLAN_RESMI = "C:\\Users\\cagat\\Downloads\\gazi kampüs2.jpeg";

    public AnaMenuGUI() {
        setTitle("Üniversite Otomasyon Sistemi");
        setSize(1000, 600); // Geniş ve modern bir boyut
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın ortasında aç

        // --- 1. Arka Plan Paneli ---
        BackgroundPanel backgroundPanel = new BackgroundPanel(ARKA_PLAN_RESMI);
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // --- 2. Üst Kısım (Başlık) ---
        JLabel lblBaslik = new JLabel("Üniversite Bilgi ve Yönetim Sistemi", SwingConstants.CENTER);
        lblBaslik.setFont(new Font("SansSerif", Font.BOLD, 25));
        lblBaslik.setForeground(new Color(42, 15, 31));
        // Yazıya hafif gölge efekti (Okunabilirlik için)
        lblBaslik.setBorder(new EmptyBorder(5, 0, 0, 0));
        backgroundPanel.add(lblBaslik, BorderLayout.NORTH);

        // --- 3. Alt Kısım (Butonlar) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        buttonPanel.setOpaque(false); // Şeffaf panel
        buttonPanel.setBorder(new EmptyBorder(0, 0, 60, 0)); // Alttan boşluk

        // Buton 1: Bilgi Sistemi
        JButton btnBilgi = zarifButonOlustur("Bilgi Sistemi");
        btnBilgi.addActionListener(e -> {
            new BilgiMenuGUI().setVisible(true);
            // Ana menüyü kapatmak istersen: this.dispose();
        });

        // Buton 2: Yönetim Paneli
        JButton btnYonetim = zarifButonOlustur("Yönetim Paneli");
        btnYonetim.addActionListener(e -> {
            // Önce giriş ekranına yönlendiriyoruz
            new YonetimGirisGUI().setVisible(true);
        });

        // Buton 3: Çıkış (Opsiyonel ama şık durur)
        JButton btnCikis = zarifButonOlustur("Çıkış");
        btnCikis.setBackground(new Color(180, 60, 60, 220)); // Kırmızımsı özel renk
        btnCikis.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnCikis.setBackground(new Color(200, 80, 80, 240));
            }
            public void mouseExited(MouseEvent evt) {
                btnCikis.setBackground(new Color(180, 60, 60, 220));
            }
        });
        btnCikis.addActionListener(e -> System.exit(0));

        buttonPanel.add(btnBilgi);
        buttonPanel.add(btnYonetim);
        buttonPanel.add(btnCikis);

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // --- Yardımcı Metot: Zarif Buton Tasarımı ---
    private JButton zarifButonOlustur(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(40, 60, 100, 220)); // Yarı saydam koyu mavi
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 2), // Beyaz çerçeve
                BorderFactory.createEmptyBorder(15, 40, 15, 40) // İç boşluk
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Efekti
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                // Kırmızı butonun kendi rengi var, onu ezmeyelim diye kontrol ediyoruz
                if (!btn.getText().equals("Çıkış")) {
                    btn.setBackground(new Color(60, 80, 140, 240));
                }
            }
            public void mouseExited(MouseEvent evt) {
                if (!btn.getText().equals("Çıkış")) {
                    btn.setBackground(new Color(40, 60, 100, 220));
                }
            }
        });
        return btn;
    }

    // --- İç Sınıf: Arka Plan Resmi ---
    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String fileName) {
            try {
                File file = new File(fileName);
                if (file.exists()) {
                    backgroundImage = new ImageIcon(fileName).getImage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Resmi panel boyutuna göre uyarla
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }
}