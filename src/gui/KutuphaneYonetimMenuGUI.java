package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Yönetici panelinde; kütüphane envanter yönetimi ve ödünç alma taleplerinin
// onaylanması işlemlerini tek bir merkezde toplayan arayüz sınıfı.
public class KutuphaneYonetimMenuGUI extends JFrame {

    // Arayüz genelinde görsel bütünlüğü sağlamak amacıyla kurumsal renk kodları sabit (final) olarak tanımlandı.
    private final Color BG_COLOR = new Color(248, 249, 250);
    private final Color CARD_BG = Color.WHITE;
    private final Color ACCENT_COLOR = new Color(230, 126, 34); // Dikkat çekici Turuncu ton

    public KutuphaneYonetimMenuGUI() {
        setTitle("Kütüphane Yönetimi");
        // Pencere boyutları, üç adet işlem kartını yan yana sığdıracak şekilde genişletildi.
        setSize(800, 400);
        // Ana menü akışını bozmamak adına pencere kapatıldığında uygulamanın çalışmaya devam etmesi sağlandı.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        setContentPane(mainPanel);

        // --- Başlık Alanı ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(new EmptyBorder(30, 40, 10, 40));

        JLabel lblTitle = new JLabel("Kütüphane ve Kitap İşlemleri");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- Kartların Yerleşimi (Grid Layout) ---
        // İşlem kartlarının yan yana nizami sıralanması için 1 satır 3 sütunluk yapı kurgulandı.
        JPanel gridPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        gridPanel.setBackground(BG_COLOR);
        gridPanel.setBorder(new EmptyBorder(20, 40, 40, 40));

        // --- Modüllerin Entegrasyonu ---

        // 1. Kitap Ekleme Modülü
        gridPanel.add(createCard("Kitap Ekle", "Yeni kitap kaydı.", "📘",
                e -> new KitapEkleGUI().setVisible(true)));

        // 2. Kitap Silme Modülü
        gridPanel.add(createCard("Kitap Sil", "Envanterden kitap sil.", "🗑️",
                e -> new KitapSilGUI().setVisible(true)));

        // 3. Talep Onay Modülü (YENİ EKLENDİ)
        // Öğrencilerin gönderdiği ödünç alma isteklerinin yönetildiği ekrana yönlendirme yapıldı.
        gridPanel.add(createCard("Talepler", "Ödünç onay listesi.", "⏳",
                e -> new KutuphaneOnayGUI().setVisible(true)));

        mainPanel.add(gridPanel, BorderLayout.CENTER);
    }

    /**
     * Yazılım Tasarım Prensibi (DRY): Yardımcı Kart Oluşturucu
     * Tekrar eden arayüz bileşenlerini parametrik olarak üreten metot.
     * Bu sayede tasarım değişiklikleri tek merkezden yönetilebilir hale getirildi.
     */
    private JPanel createCard(String title, String desc, String icon, java.awt.event.ActionListener action) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);

        // Kart sınırları ve iç boşlukları (padding) ayarlandı.
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // İkon Yapılandırması
        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        lblIcon.setBorder(new EmptyBorder(0, 0, 0, 15));
        card.add(lblIcon, BorderLayout.WEST);

        // Metin Yapılandırması
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(CARD_BG);
        JLabel lblT = new JLabel(title);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JLabel lblD = new JLabel(desc);
        lblD.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblD.setForeground(Color.GRAY);
        textPanel.add(lblT);
        textPanel.add(lblD);
        card.add(textPanel, BorderLayout.CENTER);

        // --- Kullanıcı Deneyimi (UX) ---
        // Mouse hareketlerine duyarlı görsel efektler (Hover) implemente edildi.
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(null);
            }

            public void mouseEntered(MouseEvent e) {
                // Üzerine gelindiğinde kenarlık turuncu yapılarak odaklanma sağlandı.
                card.setBorder(new LineBorder(ACCENT_COLOR, 1));
                lblT.setForeground(ACCENT_COLOR);
            }

            public void mouseExited(MouseEvent e) {
                // Mouse çekildiğinde varsayılan görünüme dönüldü.
                card.setBorder(new LineBorder(new Color(230, 230, 230), 1));
                lblT.setForeground(Color.BLACK);
            }
        });
        return card;
    }
}