package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

// Seçilen fakülteye ait bölümlerin listelendiği ve ders programlarının görüntülendiği arayüz sınıfı.
public class BolumSecimGUI extends JFrame {

    public BolumSecimGUI(String fakulteAdi) {
        setTitle(fakulteAdi + " - Ders Programları");
        setSize(900, 600);
        // Bu pencere kapatıldığında ana uygulama çalışmaya devam etsin diye DISPOSE kullanıldı.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250)); // Göz yormayan açık gri ton
        setContentPane(mainPanel);

        // Başlık
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(30, 40, 10, 40));

        // Fakülte ismi başlık olarak eklendi
        JLabel lblTitle = new JLabel(fakulteAdi);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(33, 37, 41));
        headerPanel.add(lblTitle, BorderLayout.WEST);

        // Geri Dön butonu sağ üste konumlandırıldı
        JButton btnBack = new JButton("← Geri Dön");
        styleButton(btnBack);

        // Butona tıklandığında sadece bu pencerenin kapanması sağlandı
        btnBack.addActionListener(e -> this.dispose());
        headerPanel.add(btnBack, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Bölüm Listesi
        JPanel gridPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        gridPanel.setBackground(new Color(248, 249, 250));
        gridPanel.setBorder(new EmptyBorder(20, 40, 40, 40));

        // Parametre olarak gelen fakülte adına göre, ilgili bölümler listeye eklendi.
        bolumleriYukle(fakulteAdi, gridPanel);

        // Grid panel, kayma (scroll) sorunu olmaması için bir wrapper panele alındı.
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(new Color(248, 249, 250));
        wrapperPanel.add(gridPanel, BorderLayout.NORTH);

        // Bölüm sayısı ekranı taşarsa aşağı kaydırma özelliği eklendi.
        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }


    private void bolumleriYukle(String fakulte, JPanel panel) {

        // TEKNOLOJİ FAKÜLTESİ KONTROLÜ
        if (fakulte.contains("Teknoloji")) {

            panel.add(createCard("Yazılım Mühendisliği", "Uygulama, Geliştirme...", "💾", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\teknoloji bilgisayar.xls");
            }));

            // MEKATRONİK YERİNE ELEKTRİK-ELEKTRONİK GÜNCELLEMESİ YAPILDI
            panel.add(createCard("Elektrik-Elektronik Müh.", "Devreler, Sinyaller...", "⚡", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\teknoloji elektrik.pdf");
            }));

            // ENERJİ İÇİN İKON GÜNCELLEMESİ YAPILDI (PİL LOGOSU)
            panel.add(createCard("Enerji Sistemleri Müh.", "Yenilenebilir Enerji...", "🔋", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Enerji Sistemleri teknoloji.xlsx");
            }));

            panel.add(createCard("Otomotiv Mühendisliği", "Araç Teknolojileri...", "🚗", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\teknoloji otomotiv.pdf");
            }));
        }

        // MÜHENDİSLİK FAKÜLTESİ KONTROLÜ

        else if (fakulte.contains("Mühendislik")) {

            panel.add(createCard("Bilgisayar Mühendisliği", "Donanım, Algoritma...", "💻", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Mühendislik Bilgisayar.pdf");
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

        // FEN FAKÜLTESİ KONTROLÜ
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

        // EĞİTİM FAKÜLTESİ KONTROLÜ
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

    // Buton stillerini standartlaştıran yardımcı metot.
    private JPanel createCard(String title, String desc, String icon, Runnable onClickAction) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        // Kart etrafına ince gri çerçeve eklendi
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(300, 100)); // Boyutlar sabitlendi

        // Sol ikon
        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        lblIcon.setBorder(new EmptyBorder(0, 0, 0, 15));
        card.add(lblIcon, BorderLayout.WEST);

        // Başlık ve açıklama
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

        // Mouse olayları (Hover efekti ve Tıklama işlemi)
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Eğer bir aksiyon tanımlandıysa çalıştır
                if (onClickAction != null) onClickAction.run();
            }
            // Mouse üzerine gelince çerçeve mavi olur
            public void mouseEntered(MouseEvent e) {
                card.setBorder(new LineBorder(new Color(13, 110, 253), 1));
                lblT.setForeground(new Color(13, 110, 253));
            }
            // Mouse çıkınca eski haline döner
            public void mouseExited(MouseEvent e) {
                card.setBorder(new LineBorder(new Color(230, 230, 230), 1));
                lblT.setForeground(new Color(33, 37, 41));
            }
        });
        return card;
    }

    // -DOSYA AÇMA İŞLEMİ
    // Java'nın Desktop sınıfı kullanılarak, dosya uzantısı ne olursa olsun (PDF, Excel, vb.)
    // işletim sisteminin varsayılan uygulaması ile açılması sağlandı.
    private void dosyaAc(String path) {
        File file = new File(path);

        // Dosya var mı kontrolü yapıldı
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this,
                    "Dosya bulunamadı!\nAranan yol: " + file.getAbsolutePath(),
                    "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // İşletim sisteminin bu özelliği destekleyip desteklemediği kontrol edildi
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (file.exists()) {
                    desktop.open(file); // Varsayılan uygulama ile aç
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sisteminiz dosya açma işlemini desteklemiyor.", "Hata", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Dosya açılırken hata oluştu: " + e.getMessage());
        }
    }

    // Buton stillerini tek bir yerden yönetmek için yardımcı metot
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