package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class KutuphaneOnayGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public KutuphaneOnayGUI() {
        setTitle("Kütüphane - Ödünç Onay Ekranı");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // Başlık
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(248, 249, 250));
        header.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel lblTitle = new JLabel(" Ödünç Talepleri");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lblTitle);
        mainPanel.add(header, BorderLayout.NORTH);

        // Tablo
        String[] cols = {"Kitap Adı", "Yazar", "ISBN", "Durum"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(209, 231, 221)); // Yeşilimsi
        table.setSelectionForeground(Color.BLACK);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(10, 20, 10, 20));
        sp.getViewport().setBackground(Color.WHITE);
        mainPanel.add(sp, BorderLayout.CENTER);

        yukle();

        // Buton
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        footer.setBackground(Color.WHITE);
        JButton btnOnay = new JButton("Talebi Onayla (Teslim Et)");
        btnOnay.setBackground(new Color(13, 110, 253));
        btnOnay.setForeground(Color.WHITE);
        btnOnay.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnOnay.addActionListener(e -> onayla());
        footer.add(btnOnay);
        mainPanel.add(footer, BorderLayout.SOUTH);
    }

    private void yukle() {
        model.setRowCount(0);
        List<String[]> list = DosyaIslemleri.kitaplariOkuDetayli();
        for (String[] k : list) {
            // Sadece "Bekliyor" olanları göster
            if ("Bekliyor".equals(k[3])) {
                model.addRow(new Object[]{k[0], k[1], k[2], "Talep Edildi"});
            }
        }
        if(model.getRowCount() == 0) {
            // model.addRow(new Object[]{"Talep yok", "-", "-", "-"});
        }
    }

    private void onayla() {
        int r = table.getSelectedRow();
        if (r == -1 || model.getValueAt(r, 1).equals("-")) {
            JOptionPane.showMessageDialog(this, "Seçim yapınız.");
            return;
        }
        String isbn = (String) model.getValueAt(r, 2);

        try {
            // Durumu "Oduncte" olarak güncellemek için yardımcı metot
            kitapDurumuGuncelle(isbn, "Oduncte");
            JOptionPane.showMessageDialog(this, "Kitap öğrenciye teslim edildi (Ödünç Verildi).");
            yukle();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // DosyaIslemleri'nde bu metot eksikse diye burada lokal olarak hallediyoruz
    private void kitapDurumuGuncelle(String isbn, String yeniDurum) throws IOException {
        List<String[]> kitaplar = DosyaIslemleri.kitaplariOkuDetayli();
        File file = new File("veriler/kitaplar.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String[] k : kitaplar) {
                if (k[2].equals(isbn)) {
                    k[3] = yeniDurum;
                }
                writer.write(k[0] + "," + k[1] + "," + k[2] + "," + k[3] + "," + k[4]);
                writer.newLine();
            }
        }
    }
}