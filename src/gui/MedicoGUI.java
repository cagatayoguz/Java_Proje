package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MedicoGUI extends JFrame {

    public MedicoGUI() {
        setTitle("Medico - Sağlık Merkezi");
        setSize(600, 700); // Saatler geldiği için biraz genişlettik
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- BAŞLIK ---
        JLabel lblBaslik = new JLabel("Medico Çalışma Saatleri ve Doktorlar", SwingConstants.CENTER);
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblBaslik.setForeground(Color.DARK_GRAY); // Kırmızı gitti, koyu gri geldi (Sadelik)
        lblBaslik.setBorder(new EmptyBorder(20, 0, 20, 0));
        mainPanel.add(lblBaslik, BorderLayout.NORTH);

        // --- LİSTE PANELİ ---
        JPanel listePanel = new JPanel(new GridLayout(0, 1, 0, 10)); // Araları 10px açtık
        listePanel.setBackground(Color.WHITE);
        listePanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // --- VERİLER (Bölüm, İsim, Durum, SAATLER) ---
        String[][] doktorlar = {
                {"Dahiliye", "Dr. Ahmet Yılmaz", "Müsait", "09:00 - 17:00"},
                {"Dahiliye", "Dr. Sevgi Kaya", "İzinli", "09:00 - 16:00"},
                {"Diş Hekimliği", "Dt. Mehmet Öz", "Müsait", "08:30 - 17:00"},
                {"Psikoloji", "Psk. Canan Çelik", "Dolu", "10:00 - 15:00"},
                {"Psikoloji", "Psk. Burak Soylu", "Müsait", "09:00 - 17:00"},
                {"Acil Servis", "Dr. Ali Vural", "Nöbetçi", "7/24 Açık"},
                {"Hemşire Odası", "Hem. Fatma Yıldız", "Müsait", "08:00 - 18:00"},
                {"Diyetisyen", "Dyt. Elif Su", "Toplantıda", "13:00 - 16:00"}
        };

        for (String[] dr : doktorlar) {
            listePanel.add(kartOlustur(dr[0], dr[1], dr[2], dr[3]));
        }

        JScrollPane scrollPane = new JScrollPane(listePanel);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    // --- SADELEŞTİRİLMİŞ KART TASARIMI ---
    private JPanel kartOlustur(String bolum, String isim, String durum, String saatler) {
        JPanel kart = new JPanel(new BorderLayout());
        kart.setBackground(new Color(250, 250, 250)); // Çok açık gri
        kart.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY)); // Sadece alt çizgi

        // İç boşluk (Padding)
        JPanel icPanel = new JPanel(new BorderLayout());
        icPanel.setOpaque(false);
        icPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // 1. SOL TARAF: İsim ve Bölüm
        JPanel solPanel = new JPanel(new GridLayout(2, 1));
        solPanel.setOpaque(false);

        JLabel lblIsim = new JLabel(isim);
        lblIsim.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblIsim.setForeground(Color.BLACK); // Siyah

        JLabel lblBolum = new JLabel(bolum);
        lblBolum.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblBolum.setForeground(Color.GRAY); // Gri

        solPanel.add(lblIsim);
        solPanel.add(lblBolum);

        // 2. SAĞ TARAF: Saat ve Durum
        JPanel sagPanel = new JPanel(new GridLayout(2, 1));
        sagPanel.setOpaque(false);

        // Saat Bilgisi (Sağa Yaslı)
        JLabel lblSaat = new JLabel("🕒 " + saatler, SwingConstants.RIGHT);
        lblSaat.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSaat.setForeground(Color.DARK_GRAY);

        // Durum Bilgisi (Sağa Yaslı)
        JLabel lblDurum = new JLabel(durum, SwingConstants.RIGHT);
        lblDurum.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Sadece burası renkli kalsın ki durum belli olsun
        if (durum.equals("Müsait") || durum.equals("Nöbetçi")) {
            lblDurum.setForeground(new Color(25, 135, 84)); // Yeşil
        } else {
            lblDurum.setForeground(new Color(220, 53, 69)); // Kırmızı
        }

        sagPanel.add(lblSaat);
        sagPanel.add(lblDurum);

        // Birleştirme
        icPanel.add(solPanel, BorderLayout.CENTER);
        icPanel.add(sagPanel, BorderLayout.EAST);

        kart.add(icPanel, BorderLayout.CENTER);

        // Yüksekliği biraz düşürdük, daha kompakt oldu
        kart.setPreferredSize(new Dimension(0, 65));

        return kart;
    }
}