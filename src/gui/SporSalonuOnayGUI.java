package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SporSalonuOnayGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public SporSalonuOnayGUI() {
        setTitle("Spor Salonu - Üyelik Onay İşlemleri");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblInfo = new JLabel("Onay Bekleyen Üyelik Talepleri", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Arial", Font.BOLD, 16));
        lblInfo.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        add(lblInfo, BorderLayout.NORTH);

        String[] kolonlar = {"Ad Soyad", "Öğrenci No", "Üyelik Tipi", "Ücret", "Durum"};
        model = new DefaultTableModel(kolonlar, 0);
        table = new JTable(model);

        verileriYukle();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnOnayla = new JButton("Üyeliği Onayla");
        btnOnayla.setBackground(new Color(200, 255, 200));

        JButton btnReddet = new JButton("Reddet");
        btnReddet.setBackground(new Color(255, 200, 200));

        btnOnayla.addActionListener(e -> islemYap(true));
        btnReddet.addActionListener(e -> islemYap(false));

        btnPanel.add(btnOnayla);
        btnPanel.add(btnReddet);
        add(btnPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void verileriYukle() {
        model.setRowCount(0);
        List<String[]> uyelikler = DosyaIslemleri.sporUyelikleriOku();
        boolean bekleyenVar = false;

        for (String[] u : uyelikler) {
            // Sadece "Bekliyor" durumundakileri göster
            if (u[4].equals("Bekliyor")) {
                model.addRow(u);
                bekleyenVar = true;
            }
        }

        if (!bekleyenVar) {
            model.addRow(new Object[]{"Bekleyen talep yok.", "-", "-", "-", "-"});
        }
    }

    private void islemYap(boolean onaylandi) {
        int row = table.getSelectedRow();
        if (row == -1 || model.getValueAt(row, 1).equals("-")) {
            JOptionPane.showMessageDialog(this, "Lütfen listeden geçerli bir talep seçin.");
            return;
        }

        String ogrenciNo = (String) model.getValueAt(row, 1);

        try {
            DosyaIslemleri.sporUyelikGuncelle(ogrenciNo, onaylandi);
            if (onaylandi) JOptionPane.showMessageDialog(this, "Üyelik onaylandı ve aktif edildi.");
            else JOptionPane.showMessageDialog(this, "Üyelik talebi reddedildi (silindi).");
            verileriYukle();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}