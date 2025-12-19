package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class SinavBolumSecimGUI extends JFrame {

    public SinavBolumSecimGUI(String fakulteAdi) {
        setTitle(fakulteAdi + " - Sınav Takvimi");
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

        // --- TEKNOLOJİ FAKÜLTESİ ---
        if (fakulte.contains("Teknoloji")) {
            panel.add(createCard("Yazılım Mühendisliği", "Vize/Final Takvimi...", "💾", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Bilgisayar teknoloji Final.xlsx");
            }));

            panel.add(createCard("Elektrik-Elektronik Müh.", "Sınav Tarihleri...", "⚡", () -> {
                    dosyaAc("veriler/sinav_takvimleri/Elektrik-Elektronik Müh..jpg");
            }));

            panel.add(createCard("Enerji Sistemleri Müh.", "Program...", "🔋", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Enerji Sistemleri Final.xlsx");
            }));

            panel.add(createCard("Otomotiv Mühendisliği", "Sınavlar...", "🚗", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Otomotiv Final.xlsx");
            }));
        }

        // --- MÜHENDİSLİK FAKÜLTESİ ---
        else if (fakulte.contains("Mühendislik")) {
            panel.add(createCard("Bilgisayar Mühendisliği", "Sınav Takvimi...", "💻", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Mühendislk Bilgisayar Final.pdf");
            }));

            panel.add(createCard("Elektrik-Elektronik Müh.", "Tarihler...", "⚡", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Mühendislik Elektrik Final.xls");
            }));

            panel.add(createCard("Endüstri Mühendisliği", "Program...", "🏭", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Mühendilik Endüstri Fİnal.pdf");
            }));

            panel.add(createCard("İnşaat Mühendisliği", "Sınavlar...", "🏗️", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Mühendislik İnşaat Final.pdf");
            }));
        }

        // --- FEN FAKÜLTESİ ---
        else if (fakulte.contains("Fen")) {
            panel.add(createCard("Matematik", "Analiz, Cebir Sınavları...", "📐", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Matematik Final.pdf");
            }));

            panel.add(createCard("Fizik", "Fizik Sınavları...", "⚛️", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Fizik Final.pdf");
            }));

            panel.add(createCard("Kimya", "Kimya Sınavları...", "🧪", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Kimya Final.pdf");
            }));
        }

        // --- EĞİTİM FAKÜLTESİ ---
        else if (fakulte.contains("Eğitim")) {
            panel.add(createCard("Sınıf Öğretmenliği", "Sınavlar...", "abc", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\Sınıf Öğretmenliği Final.pdf");
            }));

            panel.add(createCard("İngilizce Öğretmenliği", "Sınavlar...", "🇬🇧", () -> {
                dosyaAc("veriler/sinav_takvimleri/İngilizce Öğretmenliği.jpg");
            }));

            panel.add(createCard("Rehberlik ve PDR", "Sınavlar...", "🧠", () -> {
                dosyaAc("C:\\Users\\cagat\\Downloads\\REHBERLİK VE PSİKOLOJİK Final.pdf");
            }));
        }
    }

    // --- KART OLUŞTURMA ---
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
                if(onClickAction != null) onClickAction.run();
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

    // --- DOSYA AÇMA METODU ---
    private void dosyaAc(String path) {
        File file = new File(path);

        if (!file.exists()) {
            JOptionPane.showMessageDialog(this,
                    "Sınav Takvimi Bulunamadı!\nAranan yol: " + file.getAbsolutePath(),
                    "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(this, "Sisteminiz dosya açmayı desteklemiyor.", "Hata", JOptionPane.WARNING_MESSAGE);
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