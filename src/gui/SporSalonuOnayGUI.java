package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SporSalonuOnayGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public SporSalonuOnayGUI() {
        setTitle("Spor Salonu - Başvuru Onay Ekranı");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- Başlık ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(248, 249, 250));
        header.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel lblTitle = new JLabel(" Onay Bekleyen Başvurular");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lblTitle);
        mainPanel.add(header, BorderLayout.NORTH);

        // --- Tablo ---
        String[] cols = {"Ad Soyad", "Öğrenci No", "Üyelik Tipi", "Ücret", "Durum"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(255, 243, 205)); // Sarımsı seçim
        table.setSelectionForeground(Color.BLACK);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(10, 20, 10, 20));
        sp.getViewport().setBackground(Color.WHITE);
        mainPanel.add(sp, BorderLayout.CENTER);

        verileriYukle();

        // --- Butonlar ---
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        footer.setBackground(Color.WHITE);

        JButton btnRed = new JButton("Reddet (Sil)");
        btnRed.setBackground(new Color(220, 53, 69)); // Kırmızı
        btnRed.setForeground(Color.WHITE);
        btnRed.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton btnOnay = new JButton("Onayla (Aktif Et)");
        btnOnay.setBackground(new Color(25, 135, 84)); // Yeşil
        btnOnay.setForeground(Color.WHITE);
        btnOnay.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnRed.addActionListener(e -> islemYap(false));
        btnOnay.addActionListener(e -> islemYap(true));

        footer.add(btnRed);
        footer.add(btnOnay);
        mainPanel.add(footer, BorderLayout.SOUTH);
    }

    private void verileriYukle() {
        model.setRowCount(0);
        List<String[]> list = DosyaIslemleri.sporUyelikleriOku();
        for (String[] u : list) {
            // Sadece "Bekliyor" durumundakileri göster
            if ("Bekliyor".equals(u[4])) {
                model.addRow(u);
            }
        }
    }

    private void islemYap(boolean onay) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir başvuru seçin.");
            return;
        }

        String ogrNo = (String) model.getValueAt(row, 1);
        String ad = (String) model.getValueAt(row, 0);

        try {
            if (onay) {
                // Durumu "Aktif" yap
                DosyaIslemleri.sporUyelikGuncelle(ogrNo, "Aktif");
                JOptionPane.showMessageDialog(this, ad + " isimli üye onaylandı.");
            } else {
                // Kaydı sil
                DosyaIslemleri.sporUyelikSil(ogrNo);
                JOptionPane.showMessageDialog(this, "Başvuru reddedildi ve silindi.");
            }
            verileriYukle(); // Tabloyu yenile
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "İşlem hatası: " + ex.getMessage());
        }
    }
}