package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SporSalonuSaatlerGUI extends JFrame {

    public SporSalonuSaatlerGUI() {
        setTitle("Spor Salonu - Çalışma Saatleri");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- Başlık ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Haftalık Çalışma Programı");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(33, 37, 41));

        headerPanel.add(lblTitle, BorderLayout.WEST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- Tablo Verileri ---
        String[] kolonlar = {"Günler", "Açılış", "Kapanış", "Notlar"};
        Object[][] veriler = {
                {"Pazartesi", "Kapalı", "Kapalı", "Genel Temizlik ve Bakım"},
                {"Salı",      "08:00", "20:00", "-"},
                {"Çarşamba",  "08:00", "20:00", "-"},
                {"Perşembe",  "08:00", "20:00", "-"},
                {"Cuma",      "08:00", "20:00", "-"},
                {"Cumartesi", "08:00", "20:00", "-"},
                {"Pazar",     "08:00", "20:00", "-"}
        };

        // Tablo Modeli
        DefaultTableModel model = new DefaultTableModel(veriler, kolonlar) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        // --- Tablo Tasarımı ---
        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setSelectionForeground(Color.BLACK);

        // Başlık Ayarı
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setForeground(new Color(50, 50, 50));
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        // Sütun Genişlikleri
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);

        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 20, 20, 20));
        scrollPane.getViewport().setBackground(Color.WHITE);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }
}