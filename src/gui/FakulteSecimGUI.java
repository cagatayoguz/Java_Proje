package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Ders programı görüntüleme modülünde, kullanıcının fakülte seçimi yapmasını sağlayan arayüz sınıfı.
public class FakulteSecimGUI extends JFrame {

    public FakulteSecimGUI() {
        setTitle("Ders Programı - Fakülte Seçimi");
        setSize(800, 500);
        // Ana menüye dönüşü engellememek için DISPOSE_ON_CLOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250)); // Kurumsal gri ton
        setContentPane(mainPanel);

        //  Başlık Paneli
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(30, 40, 10, 40));

        JLabel lblTitle = new JLabel("Fakülte Seçimi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Seçenekler Paneli
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        gridPanel.setBackground(new Color(248, 249, 250));
        gridPanel.setBorder(new EmptyBorder(20, 40, 40, 40));


        // Her butona tıklandığında 'BolumSecimGUI' sınıfına ilgili fakülte adı parametre olarak gönderildi.
        // 1. Teknoloji Fakültesi Kartı
        gridPanel.add(createCard("Teknoloji Fakültesi", "Yazılım, Mekatronik...", "💻",
                e -> new BolumSecimGUI("Teknoloji Fakültesi").setVisible(true)));

        // 2. Mühendislik Fakültesi Kartı
        gridPanel.add(createCard("Mühendislik Fakültesi", "Bilgisayar, İnşaat...", "🏗️",
                e -> new BolumSecimGUI("Mühendislik Fakültesi").setVisible(true)));

        // 3. Fen Fakültesi Kartı
        gridPanel.add(createCard("Fen Fakültesi", "Matematik, Fizik...", "⚛\uFE0F",
                e -> new BolumSecimGUI("Fen Fakültesi").setVisible(true)));

        // 4. Eğitim Fakültesi Kartı
        gridPanel.add(createCard("Eğitim Fakültesi", "Öğretmenlik Bölümleri", "🎓",
                e -> new BolumSecimGUI("Eğitim Fakültesi").setVisible(true)));

        mainPanel.add(gridPanel, BorderLayout.CENTER);
    }


    // Buton stillerini standartlaştıran yardımcı metot.
    private JPanel createCard(String title, String desc, String icon, java.awt.event.ActionListener action) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);

        // Kartın sınırları ve iç boşlukları (padding) ayarlandı.
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // İkon yerleşimi (Sol taraf)
        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        lblIcon.setBorder(new EmptyBorder(0, 0, 0, 15));
        card.add(lblIcon, BorderLayout.WEST);

        // Metin yerleşimi (Orta kısım)
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(Color.WHITE);

        JLabel lblT = new JLabel(title);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel lblD = new JLabel(desc);
        lblD.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblD.setForeground(Color.GRAY); // Açıklama metni daha soluk yapıldı.

        textPanel.add(lblT);
        textPanel.add(lblD);
        card.add(textPanel, BorderLayout.CENTER);

        // Mouse üzerine geldiğinde (Hover) kenarlık rengi değiştirilerek görsel geri bildirim sağlandı.
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { action.actionPerformed(null); }

            public void mouseEntered(MouseEvent e) {
                card.setBorder(new LineBorder(new Color(13, 110, 253), 1));
            }

            // Mouse çekildiğinde kenarlık eski haline getirildi.
            public void mouseExited(MouseEvent e) {
                card.setBorder(new LineBorder(new Color(230, 230, 230), 1));
            }
        });
        return card;
    }
}