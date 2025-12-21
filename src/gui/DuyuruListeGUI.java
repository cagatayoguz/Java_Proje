package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import service.TarihIslemleri;

public class DuyuruListeGUI extends JFrame {

    public DuyuruListeGUI() {
        setTitle("Duyurular");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- Başlık ---
        JLabel lblBaslik = new JLabel("Güncel Duyurular");
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblBaslik.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.add(lblBaslik, BorderLayout.NORTH);

        // --- Tablo Modeli ---
        String[] kolonlar = {"Tarih", "Başlık", "İçerik"};
        DefaultTableModel model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        // --- Verileri Çek ve Filtrele (ÖNEMLİ KISIM) ---
        List<String[]> duyurular = DosyaIslemleri.duyurulariOku();

        // Bugünün tarihi
        LocalDate bugun = LocalDate.now();

        for (String[] d : duyurular) {
            // d[0] -> Tarih, d[1] -> Başlık, d[2] -> İçerik

            try {
                // ESKİSİ: LocalDate duyuruTarihi = LocalDate.parse(d[0].trim());

                // YENİSİ: Formatı belirterek parse ediyoruz
                LocalDate duyuruTarihi = LocalDate.parse(d[0].trim(), service.TarihIslemleri.TARIH_FORMATI);

                if (duyuruTarihi.isAfter(bugun)) {
                    continue;
                }

            } catch (Exception e) {
                // Hata olursa yine de göster (Eski formatlı veriler kaybolmasın diye)
            }

            // Filtreden geçerse tabloya ekle
            model.addRow(d);
        }

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Tabloyu sıralanabilir yap (Tarihe göre vs.)
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(0, 20, 20, 20));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bilgi Notu
        JLabel lblDipnot = new JLabel("* İleri tarihli duyurular gün gelince otomatik yayınlanır.");
        lblDipnot.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblDipnot.setForeground(Color.GRAY);
        lblDipnot.setBorder(new EmptyBorder(10, 20, 10, 20));
        mainPanel.add(lblDipnot, BorderLayout.SOUTH);
    }
}