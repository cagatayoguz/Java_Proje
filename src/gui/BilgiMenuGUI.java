package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BilgiMenuGUI extends JFrame {

    // Modern Renk Paleti
    private final Color BG_COLOR = new Color(248, 249, 250);
    private final Color CARD_BG = Color.WHITE;
    private final Color ACCENT_BLUE = new Color(13, 110, 253); // Vurgu Rengi

    public BilgiMenuGUI() {
        setTitle("Bilgi Sistemi");
        setSize(1000, 800); // Biraz daha uzattık
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Ana Panel ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);

        // İçerik çok olduğu için Scroll (Kaydırma)
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Kaydırma hızı
        setContentPane(scrollPane);

        // --- 1. Başlık (Header) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(new EmptyBorder(40, 50, 20, 50));

        JLabel lblTitle = new JLabel("Bilgi Sistemi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(33, 37, 41));

        JLabel lblSubtitle = new JLabel("Öğrenci, akademik ve sosyal hizmetlere hızlı erişim.");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitle.setForeground(new Color(108, 117, 125));

        headerPanel.add(lblTitle, BorderLayout.NORTH);
        headerPanel.add(lblSubtitle, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 2. Kartlar Alanı (Grid) ---
        // DİKKAT: (0, 2) yaptık. Yani "Satır sınırı yok, Sütun 2 olsun"
        JPanel gridPanel = new JPanel(new GridLayout(0, 2, 25, 25));
        gridPanel.setBackground(BG_COLOR);
        gridPanel.setBorder(new EmptyBorder(10, 50, 50, 50));

        // 1. Öğrenci Bilgi
        gridPanel.add(createCard(
                "Öğrenci Sorgulama",
                "Öğrenci no ile detaylı bilgi görüntüle.",
                "🎓",
                e -> new OgrenciBilgiSistemiGUI().setVisible(true)
        ));

        // 2. Akademisyen Bilgi
        gridPanel.add(createCard(
                "Akademisyenler",
                "Öğretim üyeleri ve iletişim bilgileri.",
                "👨‍🏫",
                e -> new AkademisyenListeGUI().setVisible(true)
        ));

        // 3. Ders Programı
        gridPanel.add(createCard(
                "Ders Programı",
                "Fakülte ve bölümlere göre haftalık program.",
                "📅",
                e -> new FakulteSecimGUI().setVisible(true)
        ));

        // 4. Yemekhane
        gridPanel.add(createCard(
                "Yemekhane Menüsü",
                "Günün yemeği ve aylık liste.",
                "🍽️",
                e -> new YemekhaneGUI().setVisible(true)
        ));

        // 5. Kütüphane
        gridPanel.add(createCard(
                "Kütüphane",
                "Kitap arama, ödünç alma ve iade.",
                "📚",
                e -> new KutuphaneListeGUI().setVisible(true)
        ));

        // 6. Spor Salonu
        gridPanel.add(createCard(
                "Spor Salonu",
                "Çalışma saatleri ve üyelik başvurusu.",
                "🏋️",
                e -> new SporSalonuMenuGUI().setVisible(true)
        ));

        // 7. Takvimler
        gridPanel.add(createCard(
                "Takvimler",
                "Akademik takvim ve sınav tarihleri.",
                "🗓️",
                e -> new TakvimSecimGUI().setVisible(true)
        ));

        // 8. Duyurular
        gridPanel.add(createCard(
                "Duyurular",
                "Üniversiteden güncel haberler.",
                "📢",
                e -> new DuyuruListeGUI().setVisible(true)
        ));

        // 9. Not Hesaplama
        gridPanel.add(createCard(
                "Not Hesapla",
                "Vize/Final ortalaması ve GNO hesapla.",
                "🧮",
                e -> new NotHesaplamaGUI().setVisible(true)
        ));

        // 10. Kampüs Haritası
        gridPanel.add(createCard(
                "Kampüs Haritası",
                "Yerleşke planı ve bina konumları.",
                "🗺️",
                e -> new HaritaGUI().setVisible(true)
        ));

        // --- YENİ EKLENEN MEDICO KARTI ---
        gridPanel.add(createCard(
                "Medico (Sağlık)",
                "Doktorlar, poliklinikler ve randevu.",
                "🏥",
                e -> new MedicoGUI().setVisible(true)
        ));

        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // --- 3. Alt Kısım (Geri Dön Butonu) ---
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(BG_COLOR);
        footerPanel.setBorder(new EmptyBorder(0, 0, 30, 0));

        JButton btnBack = new JButton("Ana Menüye Dön");
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setForeground(new Color(100, 100, 100));
        btnBack.setBackground(Color.WHITE);
        btnBack.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(10, 30, 10, 30)
        ));
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Basit hover efekti
        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnBack.setBackground(new Color(240, 240, 240)); }
            public void mouseExited(MouseEvent e) { btnBack.setBackground(Color.WHITE); }
        });

        btnBack.addActionListener(e -> this.dispose());
        footerPanel.add(btnBack);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * Modern Kart Bileşeni Oluşturucu
     */
    private JPanel createCard(String title, String desc, String icon, java.awt.event.ActionListener action) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        // İnce gri çerçeve
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(20, 25, 20, 25)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Boyut sabitleme
        card.setPreferredSize(new Dimension(300, 100));

        // Sol İkon
        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        lblIcon.setBorder(new EmptyBorder(0, 0, 0, 20));
        card.add(lblIcon, BorderLayout.WEST);

        // Sağ Metinler
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setBackground(CARD_BG);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(33, 37, 41));

        JLabel lblDesc = new JLabel(desc);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDesc.setForeground(new Color(108, 117, 125));

        textPanel.add(lblTitle);
        textPanel.add(lblDesc);
        card.add(textPanel, BorderLayout.CENTER);

        // Etkileşimler
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(null);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Hover: Mavi çerçeve ve hafif arka plan
                card.setBackground(new Color(250, 251, 255));
                card.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(ACCENT_BLUE, 1),
                        new EmptyBorder(20, 25, 20, 25)
                ));
                lblTitle.setForeground(ACCENT_BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Normal hal
                card.setBackground(CARD_BG);
                card.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(230, 230, 230), 1),
                        new EmptyBorder(20, 25, 20, 25)
                ));
                lblTitle.setForeground(new Color(33, 37, 41));
            }
        });

        return card;
    }
}