package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SporSalonuSaatlerGUI extends JFrame {

    public SporSalonuSaatlerGUI() {
        setTitle("Spor Salonu Çalışma Saatleri");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- ÜST BİLGİ LABELI ---
        // Resimdeki turuncu uyarı yazısı
        JLabel lblUyari = new JLabel("Pazartesi günleri spor salonu kapalıdır.", SwingConstants.CENTER);
        lblUyari.setFont(new Font("Arial", Font.BOLD, 16));
        lblUyari.setForeground(new Color(200, 100, 0)); // Turuncuya yakın renk
        lblUyari.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Biraz boşluk
        add(lblUyari, BorderLayout.NORTH);

        // --- TABLO ---
        String[] kolonlar = {"Gün", "Durum", "Açılış", "Kapanış"};

        // Verileri resimden birebir alıyoruz
        Object[][] veriler = {
                {"Pazartesi", "Kapalı", "-", "-"},
                {"Salı", "Açık", "08:00", "20:00"},
                {"Çarşamba", "Açık", "08:00", "20:00"},
                {"Perşembe", "Açık", "08:00", "20:00"},
                {"Cuma", "Açık", "08:00", "20:00"},
                {"Cumartesi", "Açık", "08:00", "20:00"},
                {"Pazar", "Açık", "08:00", "20:00"}
        };

        DefaultTableModel model = new DefaultTableModel(veriler, kolonlar) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tablo düzenlenemesin
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30); // Satırları biraz genişlet
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        add(new JScrollPane(table), BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }
}