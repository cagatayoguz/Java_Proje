package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collections; // Bu kütüphaneyi eklemeyi unutma
import java.util.List;

public class DuyuruListeGUI extends JFrame {

    public DuyuruListeGUI() {
        setTitle("Duyurular");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Başlık ---
        JLabel lblBaslik = new JLabel("Güncel Duyurular (Yeniden Eskiye)", SwingConstants.CENTER);
        lblBaslik.setFont(new Font("Arial", Font.BOLD, 20));
        lblBaslik.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblBaslik, BorderLayout.NORTH);

        // --- Tablo Modeli ---
        String[] kolonlar = {"Tarih", "Konu", "İçerik"};
        DefaultTableModel model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);

        // Sütun Genişlikleri
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(500);

        // Verileri Yükle
        verileriYukle(model);

        add(new JScrollPane(table), BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    private void verileriYukle(DefaultTableModel model) {
        // Dosyadan tüm duyuruları çekiyoruz (Eskiden yeniye gelir)
        List<String[]> duyurular = DosyaIslemleri.duyurulariOku();

        // --- İŞTE SİHİRLİ KOD BURASI ---
        // Listeyi ters çeviriyoruz ki en son eklenen (listenin sonundaki) en başa gelsin.
        Collections.reverse(duyurular);
        // -------------------------------

        for (String[] d : duyurular) {
            model.addRow(d);
        }
    }
}