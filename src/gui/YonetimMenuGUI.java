package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Yönetici (Admin) kullanıcısının tüm alt modüllere (Öğrenci, Kütüphane, Spor, Duyuru)
// erişim sağladığı ana kontrol paneli (Dashboard) sınıfı.
public class YonetimMenuGUI extends JFrame {

    // Tasarım Sabitleri
    private final Color BG_COLOR = new Color(248, 249, 250); // Arka plan (Bootstrap light gray)
    private final Color CARD_BG = Color.WHITE;
    private final Color TEXT_DARK = new Color(33, 37, 41);
    private final Color TEXT_LIGHT = new Color(108, 117, 125);
    private final Color ACCENT_BLUE = new Color(13, 110, 253); // Vurgu rengi (Primary Blue)

    public YonetimMenuGUI() {
        setTitle("Yönetim Paneli");
        setSize(800, 600); // Dashboard görünümü için geniş ekran boyutu tercih edildi.
        // Bu pencere kapatıldığında sadece kendisinin kapanması (Login ekranı arkada kalabilir) sağlandı.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        setContentPane(mainPanel);

        // Üst Başlık
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

        // Kartlar Alanı
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 25, 25));
        gridPanel.setBackground(BG_COLOR);
        gridPanel.setBorder(new EmptyBorder(10, 50, 50, 50));

        // Kartların Oluşturulması
        // 1. Öğrenci Yönetimi Modülü
        gridPanel.add(createMenuCard(
                "Öğrenci Yönetimi",
                "Öğrenci ekle, sil ve listele.",
                "👨‍🎓",
                e -> new OgrenciYonetimMenuGUI().setVisible(true)
        ));

        // 2. Kütüphane Yönetimi Modülü
        gridPanel.add(createMenuCard(
                "Kütüphane Yönetimi",
                "Kitap ekle, sil ve stok takibi yap.",
                "📚",
                e -> new KutuphaneYonetimMenuGUI().setVisible(true)
        ));

        // 3. Spor Salonu Modülü
        gridPanel.add(createMenuCard(
                "Spor Salonu",
                "Üyelik başvurularını onayla/reddet.",
                "💪",
                e -> new SporSalonuYonetimMenuGUI().setVisible(true)
        ));

        // 4. Duyuru Paneli Modülü
        gridPanel.add(createMenuCard(
                "Duyuru Paneli",
                "Yeni duyuru yayınla veya kaldır.",
                "📢",
                e -> new DuyuruYonetimMenuGUI().setVisible(true)
        ));

        mainPanel.add(gridPanel, BorderLayout.CENTER);
    }

    // Buton stillerini standartlaştıran yardımcı metot.
    private JPanel createMenuCard(String title, String description, String iconSymbol, java.awt.event.ActionListener action) {
        // Kart Paneli
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(CARD_BG);

        // Varsayılan kenarlık: İnce gri çizgi ve iç boşluk.
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(25, 25, 25, 25)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Tıklanabilirlik imleci

        // Sol Taraf (İkon)
        JLabel lblIcon = new JLabel(iconSymbol);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32)); // Emoji desteği
        lblIcon.setBorder(new EmptyBorder(0, 0, 0, 20));
        card.add(lblIcon, BorderLayout.WEST);

        // -Sağ Taraf (Metin Bilgileri)
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

        // Hover ve Click Efektleri
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Tıklama olayında ilgili parametrik aksiyon tetiklendi.
                action.actionPerformed(null);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Mouse üzerine geldiğinde kart rengi ve kenarlığı değiştirilerek odaklanma sağlandı.
                card.setBackground(new Color(250, 251, 255));
                card.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(ACCENT_BLUE, 1),
                        new EmptyBorder(25, 25, 25, 25)
                ));
                lblTitle.setForeground(ACCENT_BLUE); // Başlık rengi maviye döndü
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Mouse çekildiğinde varsayılan stile geri dönüldü.
                card.setBackground(CARD_BG);
                card.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(230, 230, 230), 1), // Gri Çerçeve
                        new EmptyBorder(25, 25, 25, 25)
                ));
                lblTitle.setForeground(TEXT_DARK); // Başlık rengi siyaha döndü
            }
        });

        return card;
    }
}