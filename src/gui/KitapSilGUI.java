package gui;

import model.Kitap;
import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class KitapSilGUI extends JFrame {
    private DefaultTableModel model;
    private JTable table;

    public KitapSilGUI() {
        setTitle("Kitap Sil");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] kolonlar = {"Kitap Adı", "Yazar", "ISBN"};
        model = new DefaultTableModel(kolonlar, 0);
        table = new JTable(model);

        verileriYukle();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnSil = new JButton("Seçili Kitabı Sil");
        btnSil.setForeground(Color.RED);
        btnSil.addActionListener(e -> sil());
        add(btnSil, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void verileriYukle() {
        model.setRowCount(0);
        try {
            List<Kitap> kitaplar = DosyaIslemleri.kitaplariOku();
            for (Kitap k : kitaplar) {
                model.addRow(new Object[]{k.getKitapAdi(), k.getYazarAdi(), k.getIsbn()});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void sil() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seçim yapınız.");
            return;
        }
        String isbn = (String) model.getValueAt(row, 2);
        try {
            DosyaIslemleri.kitapSil(isbn);
            JOptionPane.showMessageDialog(this, "Silindi.");
            verileriYukle();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage()); }
    }
}