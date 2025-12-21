package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Yönetici panelinde; spor salonu başvuru onayları ve aktif üye yönetim
// işlemlerinin seçildiği ara menü sınıfı.
public class SporSalonuYonetimMenuGUI extends JFrame {

    // Arayüzde görsel tutarlılığı sağlamak için renk kodları sabit (final) olarak tanımlandı.
    private final Color BG_COLOR = new Color(248, 249, 250);
    private final Color CARD_BG = Color.WHITE;
    private final Color ACCENT_COLOR = new Color(46, 204, 113); // Spor salonu için Yeşil Ton

    public SporSalonuYonetimMenuGUI() {
        setTitle("Spor Salonu Yönetimi");
        setSize(600, 400);
        // Bu pencere kapatıldığında ana uygulamanın çalışmaya devam etmesi için DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        setContentPane(mainPanel);

        // --- Başlık Alanı ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(new EmptyBorder(30, 40, 10, 40));

        JLabel lblTitle = new JLabel("Spor Salonu İşlemleri");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- Menü Kartları (Grid Layout) ---
        // Kartların yan yana düzenli durması için 1 satır 2 sütunluk Grid yapısı kurgulandı.
        JPanel gridPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        gridPanel.setBackground(BG_COLOR);
        gridPanel.setBorder(new EmptyBorder(20, 40, 40, 40));

        // 1. Onay Bekleyenler Kartı
        // Lambda ifadesi ile başvuru onay ekranının açılması sağlandı.
        gridPanel.add(createCard("Onay Bekleyenler", "Yeni başvuruları yönet.", "📝",
                e -> new SporSalonuOnayGUI().setVisible(true)));

        // 2. Üye Listesi / İptal Kartı
        // Aktif üyelerin listelendiği ve silindiği ekran yönlendirmesi.
        gridPanel.add(createCard("Üye Listesi / İptal", "Üyeleri gör veya sil.", "👥",
                e -> new SporSalonuUyeYonetimGUI().setVisible(true)));

        mainPanel.add(gridPanel, BorderLayout.CENTER);
    }

    /**
     * Helper Metot: Kart Oluşturucu
     * Kod tekrarını önlemek (DRY Prensibi) ve tasarım standardını korumak amacıyla
     * kart üretim süreci parametrik bir metoda devredildi.
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
        // Mouse üzerine geldiğinde (Hover) kenarlık rengi değiştirilerek görsel geri bildirim sağlandı.
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(null);
            }

            public void mouseEntered(MouseEvent e) {
                card.setBorder(new LineBorder(ACCENT_COLOR, 1));
                lblT.setForeground(ACCENT_COLOR);
            }

            public void mouseExited(MouseEvent e) {
                card.setBorder(new LineBorder(new Color(230, 230, 230), 1));
                lblT.setForeground(Color.BLACK);
            }
        });
        return card;
    }
}