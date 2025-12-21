package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Sınav takvimi görüntüleme modülünde, kullanıcının fakülte seçimi yapmasını sağlayan arayüz sınıfı.
public class SinavFakulteSecimGUI extends JFrame {

    public SinavFakulteSecimGUI() {
        // Pencere yapılandırması (Başlık, Boyut, Konumlandırma) gerçekleştirildi.
        setTitle("Sınav Takvimi - Fakülte Seçimi");
        setSize(850, 550);
        // Ana menüye dönüşü engellememek için DISPOSE_ON_CLOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250)); // Kurumsal gri arka plan
        setContentPane(mainPanel);

        // --- 1. Başlık Alanı ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(30, 40, 10, 40));

        JLabel lblTitle = new JLabel("Sınav Takvimi İçin Fakülte Seçiniz");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(33, 37, 41));

        headerPanel.add(lblTitle, BorderLayout.WEST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 2. Fakülte Listesi (Grid Yapısı) ---
        // Seçeneklerin düzenli bir matris yapısında (2x2) görüntülenmesi için GridLayout tercih edildi.
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        gridPanel.setBackground(new Color(248, 249, 250));
        gridPanel.setBorder(new EmptyBorder(20, 40, 40, 40));

        // --- LİSTE ELEMANLARININ EKLENMESİ ---
        // Kod tekrarını önlemek amacıyla 'createCard' yardımcı metodu kullanıldı.
        // Tıklama olayında, seçilen fakülte bilgisi 'SinavBolumSecimGUI' sınıfına parametre olarak aktarıldı.

        // 1. Teknoloji Fakültesi
        gridPanel.add(createCard("Teknoloji Fakültesi", "Yazılım, EEM, Enerji...", "💻",
                e -> new SinavBolumSecimGUI("Teknoloji Fakültesi").setVisible(true)));

        // 2. Mühendislik Fakültesi
        gridPanel.add(createCard("Mühendislik Fakültesi", "Bilgisayar, İnşaat...", "🏗️",
                e -> new SinavBolumSecimGUI("Mühendislik Fakültesi").setVisible(true)));

        // 3. Fen Fakültesi
        gridPanel.add(createCard("Fen Fakültesi", "Matematik, Fizik, Kimya", "⚛️",
                e -> new SinavBolumSecimGUI("Fen Fakültesi").setVisible(true)));

        // 4. Eğitim Fakültesi
        gridPanel.add(createCard("Eğitim Fakültesi", "Öğretmenlik Bölümleri", "🎓",
                e -> new SinavBolumSecimGUI("Eğitim Fakültesi").setVisible(true)));

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

        // Kartın sınırları ve iç boşlukları (padding) ayarlandı.
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Tıklanabilirlik hissi verildi.

        // İkon Yerleşimi
        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        lblIcon.setBorder(new EmptyBorder(0, 0, 0, 15));
        card.add(lblIcon, BorderLayout.WEST);

        // Metin Yerleşimi
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(Color.WHITE);

        JLabel lblT = new JLabel(title);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblT.setForeground(new Color(33, 37, 41));

        JLabel lblD = new JLabel(desc);
        lblD.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblD.setForeground(Color.GRAY); // Açıklama metni daha soluk yapıldı.

        textPanel.add(lblT);
        textPanel.add(lblD);
        card.add(textPanel, BorderLayout.CENTER);

        // --- Kullanıcı Deneyimi (UX) ---
        // Mouse üzerine geldiğinde (Hover) kenarlık ve yazı rengi değiştirilerek
        // interaktif bir görünüm sağlandı.
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(null);
            }

            public void mouseEntered(MouseEvent e) {
                // Odaklanma rengi (Mavi)
                card.setBorder(new LineBorder(new Color(13, 110, 253), 1));
                lblT.setForeground(new Color(13, 110, 253));
            }

            public void mouseExited(MouseEvent e) {
                // Varsayılan görünüm
                card.setBorder(new LineBorder(new Color(230, 230, 230), 1));
                lblT.setForeground(new Color(33, 37, 41));
            }
        });
        return card;
    }
}