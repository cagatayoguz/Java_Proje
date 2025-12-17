package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DuyuruSilGUI extends JFrame {
    private DefaultTableModel model;
    private JTable table;

    public DuyuruSilGUI() {
        setTitle("Duyuruları Yönet");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(248, 249, 250));
        JLabel lbl = new JLabel(" Yayındaki Duyurular");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lbl);
        mainPanel.add(header, BorderLayout.NORTH);

        String[] cols = {"Tarih", "Başlık", "İçerik (Önizleme)"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        yukle();

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(10, 20, 10, 20));
        sp.getViewport().setBackground(Color.WHITE);
        mainPanel.add(sp, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        footer.setBackground(Color.WHITE);
        JButton btnSil = new JButton("Seçili Duyuruyu Kaldır");
        btnSil.setBackground(new Color(220, 53, 69));
        btnSil.setForeground(Color.WHITE);
        btnSil.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSil.addActionListener(e -> sil());
        footer.add(btnSil);
        mainPanel.add(footer, BorderLayout.SOUTH);
    }

    private void yukle() {
        model.setRowCount(0);
        List<String[]> list = DosyaIslemleri.duyurulariOku();
        for(String[] d : list) model.addRow(d);
    }

    private void sil() {
        int r = table.getSelectedRow();
        if(r == -1) { JOptionPane.showMessageDialog(this,"Seçim yapınız."); return; }

        // Başlığı anahtar olarak kullanıp siliyoruz (Basit mantık)
        String baslik = (String) model.getValueAt(r, 1);

        int c = JOptionPane.showConfirmDialog(this, "Bu duyuru kaldırılsın mı?", "Onay", JOptionPane.YES_NO_OPTION);
        if(c == JOptionPane.YES_OPTION) {
            try {
                DosyaIslemleri.duyuruSil(baslik);
                yukle();
            } catch(Exception ex) { ex.printStackTrace(); }
        }
    }
}