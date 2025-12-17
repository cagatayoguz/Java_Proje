package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AkademisyenListeGUI extends JFrame {

    public AkademisyenListeGUI() {
        setTitle("Akademik Kadro");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Ana Panel ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- 1. Başlık Kısmı ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250)); // Açık gri
        headerPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel lblTitle = new JLabel("Akademik Personel Listesi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(33, 37, 41));

        headerPanel.add(lblTitle, BorderLayout.WEST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 2. Tablo (Liste) Kısmı ---

        // Sütun Başlıkları
        String[] kolonlar = {"Unvan", "Ad Soyad", "Bölüm / Fakülte", "E-Posta"};

        // Örnek Veriler (Normalde veritabanından gelir)
        Object[][] veriler = {
                {"Prof. Dr.", "Ahmet Yılmaz", "Bilgisayar Mühendisliği", "ahmet.yilmaz@uni.edu.tr"},
                {"Prof. Dr.", "Zeynep Çelik", "Tıp Fakültesi", "zeynep.celik@uni.edu.tr"},
                {"Doç. Dr.", "Ayşe Kaya", "Elektrik-Elektronik Müh.", "ayse.kaya@uni.edu.tr"},
                {"Doç. Dr.", "Murat Demir", "Makine Mühendisliği", "murat.demir@uni.edu.tr"},
                {"Dr. Öğr. Üyesi", "Mehmet Öztürk", "Yazılım Mühendisliği", "mehmet.ozturk@uni.edu.tr"},
                {"Dr. Öğr. Üyesi", "Selin Şahin", "Eğitim Fakültesi", "selin.sahin@uni.edu.tr"},
                {"Öğr. Gör.", "Fatma Yıldız", "Yabancı Diller Y.O.", "fatma.yildiz@uni.edu.tr"},
                {"Arş. Gör.", "Ali Veli", "İnşaat Mühendisliği", "ali.veli@uni.edu.tr"},
                {"Arş. Gör.", "Burak Can", "Endüstri Mühendisliği", "burak.can@uni.edu.tr"},
                {"Arş. Gör.", "Elif Polat", "Hukuk Fakültesi", "elif.polat@uni.edu.tr"}
        };

        // Tablo Modeli (Hücreler düzenlenemez olsun)
        DefaultTableModel model = new DefaultTableModel(veriler, kolonlar) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        // --- Tablo Görsel Ayarları ---
        table.setRowHeight(35); // Satır yüksekliği
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowVerticalLines(false); // Dikey çizgileri gizle (Daha modern)
        table.setIntercellSpacing(new Dimension(0, 0)); // Çizgi boşluklarını kaldır
        table.setSelectionBackground(new Color(232, 240, 254)); // Seçim rengi (Açık mavi)
        table.setSelectionForeground(Color.BLACK);

        // Başlık (Header) Tasarımı
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setForeground(new Color(50, 50, 50));
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        // Sütun Genişlikleri Ayarı
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // Unvan
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Ad Soyad
        table.getColumnModel().getColumn(2).setPreferredWidth(250); // Bölüm
        table.getColumnModel().getColumn(3).setPreferredWidth(200); // E-Posta

        // ScrollPane içine al
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 30, 30, 30)); // Kenar boşlukları
        scrollPane.getViewport().setBackground(Color.WHITE); // Arka plan beyaz

        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }
}