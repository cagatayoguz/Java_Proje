package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DuyuruSilGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public DuyuruSilGUI() {
        setTitle("Duyuru Sil");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Tablo ---
        String[] kolonlar = {"Tarih", "Konu", "İçerik"};
        model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Düzenlenemesin, sadece seçilsin
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Tek tek seçilsin
        verileriYukle(); // Listeyi doldur

        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Alt Panel (Sil Butonu) ---
        JPanel altPanel = new JPanel();
        JButton btnSil = new JButton("Seçili Duyuruyu Sil");
        btnSil.setFont(new Font("Arial", Font.BOLD, 14));
        btnSil.setForeground(Color.RED);

        btnSil.addActionListener(e -> silmeIslemi());

        altPanel.add(btnSil);
        add(altPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void verileriYukle() {
        model.setRowCount(0); // Tabloyu temizle
        List<String[]> duyurular = DosyaIslemleri.duyurulariOku();
        for (String[] d : duyurular) {
            model.addRow(d);
        }
    }

    private void silmeIslemi() {
        int seciliSatir = table.getSelectedRow();

        if (seciliSatir == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silinecek bir duyuru seçin.");
            return;
        }

        // Tablodan konuyu al (1. sütun)
        String konu = (String) model.getValueAt(seciliSatir, 1);

        int onay = JOptionPane.showConfirmDialog(this,
                "'" + konu + "' başlıklı duyuruyu silmek istediğinize emin misiniz?",
                "Silme Onayı", JOptionPane.YES_NO_OPTION);

        if (onay == JOptionPane.YES_OPTION) {
            try {
                boolean sonuc = DosyaIslemleri.duyuruSil(konu);
                if (sonuc) {
                    JOptionPane.showMessageDialog(this, "Duyuru silindi.");
                    verileriYukle(); // Listeyi yenile
                } else {
                    JOptionPane.showMessageDialog(this, "Hata: Duyuru dosyada bulunamadı.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Bir hata oluştu: " + ex.getMessage());
            }
        }
    }
}