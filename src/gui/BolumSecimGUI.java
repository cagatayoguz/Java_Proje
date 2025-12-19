package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class BolumSecimGUI extends JFrame {

    public BolumSecimGUI(String fakulteAdi) {
        setTitle(fakulteAdi + " - Ders Programları");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        // --- 1. Başlık ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(30, 40, 10, 40));

        JLabel lblTitle = new JLabel(fakulteAdi);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(33, 37, 41));
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JButton btnBack = new JButton("← Geri Dön");
        styleButton(btnBack);
        btnBack.addActionListener(e -> this.dispose());
        headerPanel.add(btnBack, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 2. Bölüm Listesi ---
        JPanel gridPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        gridPanel.setBackground(new Color(248, 249, 250));
        gridPanel.setBorder(new EmptyBorder(20, 40, 40, 40));

        // Fakülteye göre özel metodları çağır
        bolumleriYukle(fakulteAdi, gridPanel);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(new Color(248, 249, 250));
        wrapperPanel.add(gridPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void bolumleriYukle(String fakulte, JPanel panel) {

        // ---------------------------------------------------------
        // TEKNOLOJİ FAKÜLTESİ
        // ---------------------------------------------------------
        // ---------------------------------------------------------
        // TEKNOLOJİ FAKÜLTESİ
        // ---------------------------------------------------------
        if (fakulte.contains("Teknoloji")) {

            panel.add(createCard("Yazılım Mühendisliği", "Uygulama, Geliştirme...", "💾", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\teknoloji bilgisayar.xls");
            }));

            // MEKATRONİK YERİNE ELEKTRİK-ELEKTRONİK GELDİ
            panel.add(createCard("Elektrik-Elektronik Müh.", "Devreler, Sinyaller...", "⚡", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\teknoloji elektrik.pdf");
            }));

            // ENERJİ LOGOSU PİL OLDU (🔋)
            panel.add(createCard("Enerji Sistemleri Müh.", "Yenilenebilir Enerji...", "🔋", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Enerji Sistemleri teknoloji.xlsx");
            }));

            panel.add(createCard("Otomotiv Mühendisliği", "Araç Teknolojileri...", "🚗", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\teknoloji otomotiv.pdf");
            }));
        }

        // ---------------------------------------------------------
        // MÜHENDİSLİK FAKÜLTESİ
        // ---------------------------------------------------------
        else if (fakulte.contains("Mühendislik")) {

            panel.add(createCard("Bilgisayar Mühendisliği", "Donanım, Algoritma...", "💻", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Mühendislik Bilgisayar.pdf"); // Mesela bu PDF olabilir
            }));

            panel.add(createCard("Elektrik-Elektronik Müh.", "Devreler...", "⚡", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\mühendislik elektrik.pdf");
            }));

            panel.add(createCard("Endüstri Mühendisliği", "Verimlilik...", "🏭", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Mühendislik endüstri.pdf");
            }));

            panel.add(createCard("İnşaat Mühendisliği", "Yapı, Statik...", "🏗️", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\İNŞAAT MÜHENDİSLİĞİ Mühendislik.pdf");
            }));
        }

        // ---------------------------------------------------------
        // FEN FAKÜLTESİ
        // ---------------------------------------------------------
        else if (fakulte.contains("Fen")) {

            panel.add(createCard("Matematik", "Analiz, Cebir...", "📐", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\mat ders pro.pdf");
            }));

            panel.add(createCard("Fizik", "Kuantum, Optik...", "⚛️", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\fizik ders pro.pdf");
            }));

            panel.add(createCard("Kimya", "Organik, Analitik...", "🧪", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\kimya ders pro.pdf");
            }));
        }

        // ---------------------------------------------------------
        // EĞİTİM FAKÜLTESİ
        // ---------------------------------------------------------
        else if (fakulte.contains("Eğitim")) {

            panel.add(createCard("Sınıf Öğretmenliği", "İlköğretim...", "abc", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Sınıf öğretmenliği.pdf");
            }));

            panel.add(createCard("İngilizce Öğretmenliği", "Dil Eğitimi...", "🇬🇧", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\İngilizce Öğretmenliği.pdf");
            }));

            panel.add(createCard("Rehberlik ve PDR", "Psikolojik Danışma...", "🧠", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\REHBERLİK VE PSİKOLOJİK DANIŞMANLIK.pdf");
            }));
        }
    }

    // --- KART OLUŞTURMA METODU ---
    private JPanel createCard(String title, String desc, String icon, Runnable onClickAction) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(300, 100));

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

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onClickAction != null) onClickAction.run();
            }
            public void mouseEntered(MouseEvent e) {
                card.setBorder(new LineBorder(new Color(13, 110, 253), 1));
                lblT.setForeground(new Color(13, 110, 253));
            }
            public void mouseExited(MouseEvent e) {
                card.setBorder(new LineBorder(new Color(230, 230, 230), 1));
                lblT.setForeground(new Color(33, 37, 41));
            }
        });
        return card;
    }

    // --- GENEL DOSYA AÇMA METODU (YENİ) ---
    // Bu metot dosya ne olursa olsun (PDF, JPG, TXT) sistemin varsayılan uygulamasıyla açar.
    private void dosyaAc(String path) {
        File file = new File(path);

        if (!file.exists()) {
            JOptionPane.showMessageDialog(this,
                    "Dosya bulunamadı!\nAranan yol: " + file.getAbsolutePath(),
                    "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Masaüstü özelliği destekleniyor mu kontrol et
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (file.exists()) {
                    desktop.open(file); // BİLGİSAYARIN KENDİ UYGULAMASIYLA AÇAR
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sisteminiz dosya açma işlemini desteklemiyor.", "Hata", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Dosya açılırken hata oluştu: " + e.getMessage());
        }
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(new Color(100, 100, 100));
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(8, 20, 8, 20)
        ));
    }
}