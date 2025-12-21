package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Spor salonu modülüne ait alt işlemlerin (Üyelik, Çalışma Saatleri)
// seçildiği ana menü arayüzü.
public class SporSalonuMenuGUI extends JFrame {

    public SporSalonuMenuGUI() {
        // Pencere yapılandırması (Başlık, Boyut, Konumlandırma) gerçekleştirildi.
        setTitle("Spor Salonu");
        setSize(600, 400);
        // Bu pencere kapatıldığında ana uygulamanın çalışmaya devam etmesi için DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250)); // Kurumsal gri arka plan
        setContentPane(mainPanel);

        // --- Başlık Alanı ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(30, 40, 10, 40));

        JLabel lblTitle = new JLabel("Spor Salonu");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- Menü Kartları (Grid Düzeni) ---
        // Seçeneklerin yan yana ve düzenli durması için 1 satır 2 sütunlu Grid yapısı kurgulandı.
        JPanel gridPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        gridPanel.setBackground(new Color(248, 249, 250));
        gridPanel.setBorder(new EmptyBorder(20, 40, 40, 40));

        // --- Modüllerin Entegrasyonu ---
        // Kod tekrarını önlemek amacıyla 'createCard' yardımcı metodu kullanılarak menü elemanları eklendi.

        // 1. Üyelik Başvuru Kartı
        gridPanel.add(createCard("Üyelik Başvurusu", "Aylık/Yıllık kayıt ol.", "📝",
                e -> new SporSalonuUyelikGUI().setVisible(true)));

        // 2. Çalışma Saatleri Kartı
        // Tablo yapısında saatleri gösteren arayüz çağrıldı.
        gridPanel.add(createCard("Çalışma Saatleri", "Haftalık programı gör.", "⏰",
                e -> new SporSalonuSaatlerGUI().setVisible(true)));

        mainPanel.add(gridPanel, BorderLayout.CENTER);
    }

    /**
     * UI Helper: Kart Oluşturucu Metot
     * Tekrar eden görsel bileşenleri (Kartları) parametrik olarak üreten metot.
     * Bu sayede tasarım standardizasyonu sağlandı ve kod bakımı kolaylaştırıldı.
     */
    private JPanel createCard(String title, String desc, String icon, java.awt.event.ActionListener action) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);

        // Kart sınırları ve iç boşlukları (padding) ayarlandı.
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Tıklanabilirlik hissi verildi.

        // İkon Yapılandırması
        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        lblIcon.setBorder(new EmptyBorder(0, 0, 0, 15));
        card.add(lblIcon, BorderLayout.WEST);

        // Metin Yapılandırması
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(Color.WHITE);
        JLabel lblT = new JLabel(title);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JLabel lblD = new JLabel(desc);
        lblD.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblD.setForeground(Color.GRAY);
        textPanel.add(lblT);
        textPanel.add(lblD);
        card.add(textPanel, BorderLayout.CENTER);

        // --- Kullanıcı Deneyimi (UX) ---
        // Mouse üzerine geldiğinde (Hover) kenarlık rengi yeşil yapılarak görsel geri bildirim sağlandı.
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(null);
            }

            public void mouseEntered(MouseEvent e) {
                // Spor salonu temasına uygun olarak Yeşil renk tercih edildi.
                card.setBorder(new LineBorder(new Color(46, 204, 113), 1));
            }

            public void mouseExited(MouseEvent e) {
                // Mouse çekildiğinde varsayılan görünüme dönüldü.
                card.setBorder(new LineBorder(new Color(230, 230, 230), 1));
            }
        });
        return card;
    }
}