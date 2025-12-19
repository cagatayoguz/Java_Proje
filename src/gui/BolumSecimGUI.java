package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class BolumSecimGUI extends JFrame {

    public BolumSecimGUI(String fakulteAdi) {
        setTitle(fakulteAdi + " - Bölümler");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        // --- 1. Başlık ve Üst Panel ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(30, 40, 10, 40));

        JLabel lblTitle = new JLabel(fakulteAdi);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(33, 37, 41));
        headerPanel.add(lblTitle, BorderLayout.WEST);

        // --- ÖZELLEŞTİRİLMİŞ GERİ DÖN BUTONU ---
        JButton btnBack = new JButton("← Geri Dön");
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setForeground(new Color(100, 100, 100)); // Koyu gri yazı
        btnBack.setBackground(Color.WHITE); // Beyaz arka plan
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Şık Çerçeve ve İç Boşluk
        btnBack.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true), // Yuvarlatılmış ince gri kenar
                new EmptyBorder(8, 20, 8, 20) // İç dolgu (Padding)
        ));

        // Hover Efekti (Üzerine gelince)
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnBack.setBackground(new Color(240, 240, 240)); // Hafif grileşir
                btnBack.setForeground(new Color(50, 50, 50)); // Yazı koyulaşır
                btnBack.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(180, 180, 180), 1, true),
                        new EmptyBorder(8, 20, 8, 20)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnBack.setBackground(Color.WHITE); // Normale dön
                btnBack.setForeground(new Color(100, 100, 100));
                btnBack.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(220, 220, 220), 1, true),
                        new EmptyBorder(8, 20, 8, 20)
                ));
            }
        });

        btnBack.addActionListener(e -> this.dispose());
        headerPanel.add(btnBack, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 2. Bölümleri Listeleme (Grid) ---
        // ScrollPane ekleyelim ki bölüm sayısı artarsa taşmasın
        JPanel gridPanel = new JPanel(new GridLayout(0, 2, 20, 20)); // 2 Sütun, Satır otomatik
        gridPanel.setBackground(new Color(248, 249, 250));
        gridPanel.setBorder(new EmptyBorder(20, 40, 40, 40));

        // Fakülteye göre bölümleri ekle
        bolumleriYukle(fakulteAdi, gridPanel);

        // Grid Paneli yukarı yaslamak için wrapper panel
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(new Color(248, 249, 250));
        wrapperPanel.add(gridPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(null); // Çerçevesiz scroll
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Hızlı kaydırma

        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void bolumleriYukle(String fakulte, JPanel panel) {
        if (fakulte.contains("Teknoloji")) {
            panel.add(createCard("Yazılım Mühendisliği", "Uygulama, Geliştirme...", "💾"));
            panel.add(createCard("Mekatronik Mühendisliği", "Robotik, Otomasyon...", "🤖"));
            panel.add(createCard("Enerji Sistemleri Müh.", "Yenilenebilir Enerji...", "⚡"));
            panel.add(createCard("Otomotiv Mühendisliği", "Araç Teknolojileri...", "🚗"));
        }
        else if (fakulte.contains("Mühendislik")) {
            panel.add(createCard("Bilgisayar Mühendisliği", "Donanım, Algoritma...", "💻"));
            panel.add(createCard("Elektrik-Elektronik Müh.", "Devreler, Sinyaller...", "⚡"));
            panel.add(createCard("Endüstri Mühendisliği", "Verimlilik, Yönetim...", "🏭"));
            panel.add(createCard("İnşaat Mühendisliği", "Yapı, Statik...", "🏗️"));
        }
        else if (fakulte.contains("Fen")) { // TIP YERİNE FEN GELDİ
            panel.add(createCard("Matematik", "Analiz, Cebir...", "📐"));
            panel.add(createCard("Fizik", "Kuantum, Optik...", "⚛️"));
            panel.add(createCard("Kimya", "Organik, Analitik...", "🧪"));
        }
        else if (fakulte.contains("Eğitim")) {
            panel.add(createCard("Sınıf Öğretmenliği", "İlköğretim...", "abc"));
            panel.add(createCard("İngilizce Öğretmenliği", "Dil Eğitimi...", "🇬🇧"));
            panel.add(createCard("Rehberlik ve PDR", "Psikolojik Danışma...", "🧠"));
        }
    }

    private JPanel createCard(String title, String desc, String icon) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(300, 100)); // Kart yüksekliğini sabitle

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        lblIcon.setBorder(new EmptyBorder(0, 0, 0, 15));
        card.add(lblIcon, BorderLayout.WEST);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(Color.WHITE);
        JLabel lblT = new JLabel(title);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblT.setForeground(new Color(33, 37, 41));

        JLabel lblD = new JLabel(desc);
        lblD.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblD.setForeground(Color.GRAY);

        textPanel.add(lblT);
        textPanel.add(lblD);
        card.add(textPanel, BorderLayout.CENTER);

        // --- TIKLAMA OLAYI ---
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String dosyaYolu = "veriler/resimler/" + title + ".jpg";
                resimAc(dosyaYolu, title);
            }

            public void mouseEntered(MouseEvent e) {
                // Mavi Vurgu
                card.setBorder(new LineBorder(new Color(13, 110, 253), 1));
                lblT.setForeground(new Color(13, 110, 253));
            }
            public void mouseExited(MouseEvent e) {
                // Normale Dön
                card.setBorder(new LineBorder(new Color(230, 230, 230), 1));
                lblT.setForeground(new Color(33, 37, 41));
            }
        });

        return card;
    }

    private void resimAc(String path, String baslik) {
        JDialog d = new JDialog(this, baslik + " - Haftalık Ders Programı", true);
        d.setSize(700, 900);

        JLabel l = new JLabel();
        l.setHorizontalAlignment(SwingConstants.CENTER);

        File f = new File(path);
        if(f.exists()) {
            ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(680, 850, Image.SCALE_SMOOTH));
            l.setIcon(icon);
        } else {
            l.setText("<html><center><h2>" + baslik + "</h2><br>Ders programı görseli henüz yüklenmemiş.<br>(" + path + ")</center></html>");
            l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }

        d.add(new JScrollPane(l));
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }
}