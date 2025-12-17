package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class YonetimMenuGUI extends JFrame {

    // Modern Web Renk Paleti
    private final Color BG_COLOR = new Color(248, 249, 250); // Çok açık gri (Bootstrap style)
    private final Color CARD_BG = Color.WHITE;
    private final Color TEXT_DARK = new Color(33, 37, 41);
    private final Color TEXT_LIGHT = new Color(108, 117, 125);
    private final Color ACCENT_BLUE = new Color(13, 110, 253); // Kurumsal Mavi

    public YonetimMenuGUI() {
        setTitle("Yönetim Paneli");
        setSize(800, 600); // Daha geniş, dashboard havası için
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Ana Panel ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        setContentPane(mainPanel);

        // --- 1. Üst Başlık (Header) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(new EmptyBorder(40, 50, 20, 50));

        JLabel lblTitle = new JLabel("Kontrol Paneli");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(TEXT_DARK);

        JLabel lblSubtitle = new JLabel("Sistem yönetimi ve veri giriş işlemleri");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(TEXT_LIGHT);

        headerPanel.add(lblTitle, BorderLayout.NORTH);
        headerPanel.add(lblSubtitle, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);


        // --- 2. Kartlar Alanı (Grid) ---
        // 2 Sütunlu, satır sayısı dinamik, aralarda 20px boşluk
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 25, 25));
        gridPanel.setBackground(BG_COLOR);
        gridPanel.setBorder(new EmptyBorder(10, 50, 50, 50));

        // -- Kartları Oluştur --

        // Öğrenci Kartı
        gridPanel.add(createMenuCard(
                "Öğrenci Yönetimi",
                "Öğrenci ekle, sil ve listele.",
                "👨‍🎓",
                e -> new OgrenciYonetimMenuGUI().setVisible(true)
        ));

        // Kütüphane Kartı
        gridPanel.add(createMenuCard(
                "Kütüphane Yönetimi",
                "Kitap ekle, sil ve stok takibi yap.",
                "📚",
                e -> new KutuphaneYonetimMenuGUI().setVisible(true)
        ));

        // Spor Salonu Kartı
        gridPanel.add(createMenuCard(
                "Spor Salonu",
                "Üyelik başvurularını onayla/reddet.",
                "💪",
                e -> new SporSalonuYonetimMenuGUI().setVisible(true)
        ));

        // Duyuru Kartı
        gridPanel.add(createMenuCard(
                "Duyuru Paneli",
                "Yeni duyuru yayınla veya kaldır.",
                "📢",
                e -> new DuyuruYonetimMenuGUI().setVisible(true)
        ));

        mainPanel.add(gridPanel, BorderLayout.CENTER);
    }

    /**
     * Modern bir "Kart" bileşeni oluşturur.
     * Bu aslında bir JPanel'dir ama buton gibi davranır.
     */
    private JPanel createMenuCard(String title, String description, String iconSymbol, java.awt.event.ActionListener action) {
        // Kart Paneli
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(CARD_BG);
        // İnce gri çerçeve ve iç boşluk
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(25, 25, 25, 25)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- Sol taraf (İkon) ---
        JLabel lblIcon = new JLabel(iconSymbol);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32)); // Emoji fontu
        lblIcon.setBorder(new EmptyBorder(0, 0, 0, 20)); // Yazı ile ikon arası boşluk
        card.add(lblIcon, BorderLayout.WEST);

        // --- Sağ Taraf (Metinler) ---
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setBackground(CARD_BG);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(TEXT_DARK);

        JLabel lblDesc = new JLabel(description);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDesc.setForeground(TEXT_LIGHT);

        textPanel.add(lblTitle);
        textPanel.add(lblDesc);
        card.add(textPanel, BorderLayout.CENTER);

        // --- Etkileşimler (Hover ve Tıklama) ---

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Tıklanınca aksiyonu çalıştır
                action.actionPerformed(null);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Üzerine gelince kenarlığı MAVİ yap ve hafif grileştir
                card.setBackground(new Color(250, 251, 255));
                card.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(ACCENT_BLUE, 1), // Mavi Çerçeve
                        new EmptyBorder(25, 25, 25, 25)
                ));
                lblTitle.setForeground(ACCENT_BLUE); // Başlığı mavi yap
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Eski haline dön
                card.setBackground(CARD_BG);
                card.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(230, 230, 230), 1), // Gri Çerçeve
                        new EmptyBorder(25, 25, 25, 25)
                ));
                lblTitle.setForeground(TEXT_DARK); // Başlığı siyah yap
            }
        });

        // Alt bileşenlere de tıklama özelliğini yaymak için (Kullanıcı yazıya tıklarsa da algılasın)
        // Swing'de panelin içindeki label'a tıklayınca panelin click eventi her zaman tetiklenmeyebilir.
        // Basit çözüm: MouseListener'ı panelin kendisine verdik, genelde paneli kaplayan bir layout yeterli olur.

        return card;
    }
}