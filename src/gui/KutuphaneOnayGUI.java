package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class KutuphaneOnayGUI extends JFrame {
    private DefaultTableModel model;
    private JTable table;

    public KutuphaneOnayGUI() {
        setTitle("Kitap Onay Ekranı");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // --- TABLO ---
        String[] kolonlar = {"Kitap Adı", "ISBN", "Talep Eden Öğrenci", "Durum"};
        model = new DefaultTableModel(kolonlar, 0);
        table = new JTable(model);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        verileriYukle();

        // --- BUTONLAR ---
        JPanel btnPanel = new JPanel();
        JButton btnOnayla = new JButton("Onayla");
        JButton btnReddet = new JButton("Reddet");

        btnOnayla.setBackground(new Color(25, 135, 84));
        btnOnayla.setForeground(Color.WHITE);
        btnReddet.setBackground(new Color(220, 53, 69));
        btnReddet.setForeground(Color.WHITE);

        btnOnayla.addActionListener(e -> islemYap(true));
        btnReddet.addActionListener(e -> islemYap(false));

        btnPanel.add(btnOnayla);
        btnPanel.add(btnReddet);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
    }

    private void verileriYukle() {
        model.setRowCount(0);
        List<String[]> kitaplar = DosyaIslemleri.kitaplariOkuDetayli();
        for (String[] k : kitaplar) {
            // Sadece "Bekliyor" olanları göster
            if (k[3].equalsIgnoreCase("Bekliyor")) {
                model.addRow(new Object[]{k[0], k[2], k[5], "Onay Bekliyor"});
            }
        }
    }

    private void islemYap(boolean onay) {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String isbn = (String) model.getValueAt(row, 1);
        try {
            if (onay) {
                DosyaIslemleri.kitapOnayla(isbn);
                JOptionPane.showMessageDialog(this, "Kitap onaylandı ve ödünç verildi.");
            } else {
                DosyaIslemleri.kitapReddet(isbn);
                JOptionPane.showMessageDialog(this, "Talep reddedildi.");
            }
            verileriYukle();
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}