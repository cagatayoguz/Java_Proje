package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DuyuruListeGUI extends JFrame {

    public DuyuruListeGUI() {
        setTitle("Duyurular");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Başlık
        JLabel lblBaslik = new JLabel("Güncel Duyurular", SwingConstants.CENTER);
        lblBaslik.setFont(new Font("Arial", Font.BOLD, 20));
        lblBaslik.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblBaslik, BorderLayout.NORTH);

        // Tablo Modeli: Hücrelerin üzerine çift tıklandığında düzenlenmesini engellemek için
        // isCellEditable metodunu override ettik.
        String[] kolonlar = {"Tarih", "Konu", "İçerik"};
        DefaultTableModel model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);

        // İçerik kısmı uzun olacağı için sütun genişliklerini içeriğe göre optimize ettik.
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // Tarih
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Konu
        table.getColumnModel().getColumn(2).setPreferredWidth(500); // İçerik

        // Verileri Yükle
        verileriYukle(model);

        add(new JScrollPane(table), BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    private void verileriYukle(DefaultTableModel model) {
        // Service katmanındaki dosya okuma metodu çağrılarak tablo dolduruldu.
        List<String[]> duyurular = DosyaIslemleri.duyurulariOku();
        for (String[] d : duyurular) {
            model.addRow(d);
        }
    }
}