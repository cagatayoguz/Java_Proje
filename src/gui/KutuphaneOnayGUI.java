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
        setTitle("Kütüphane - Onay İşlemleri");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Başlık
        JLabel lblInfo = new JLabel("Onay Bekleyen Kitap Talepleri", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Arial", Font.BOLD, 16));
        lblInfo.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        add(lblInfo, BorderLayout.NORTH);

        // Tablo
        String[] kolonlar = {"Kitap Adı", "ISBN", "Talep Eden Kişi", "Durum"};
        model = new DefaultTableModel(kolonlar, 0);
        table = new JTable(model);

        verileriYukle();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Butonlar
        JPanel btnPanel = new JPanel();
        JButton btnOnayla = new JButton("Onayla (2 Hafta Ver)");
        btnOnayla.setBackground(new Color(200, 255, 200)); // Yeşil

        JButton btnReddet = new JButton("Reddet");
        btnReddet.setBackground(new Color(255, 200, 200)); // Kırmızı

        btnOnayla.addActionListener(e -> islemYap(true));
        btnReddet.addActionListener(e -> islemYap(false));

        btnPanel.add(btnOnayla);
        btnPanel.add(btnReddet);
        add(btnPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void verileriYukle() {
        model.setRowCount(0);
        List<String[]> kitaplar = DosyaIslemleri.kitaplariOkuDetayli();
        boolean bekleyenVar = false;

        for (String[] k : kitaplar) {
            // Sadece "Bekliyor" durumundakileri listele
            if (k[3].equals("Bekliyor")) {
                model.addRow(new Object[]{k[0], k[2], k[5], "Onay Bekliyor"});
                bekleyenVar = true;
            }
        }

        if (!bekleyenVar) {
            model.addRow(new Object[]{"Bekleyen talep yok.", "-", "-", "-"});
        }
    }

    private void islemYap(boolean onaylandi) {
        int row = table.getSelectedRow();
        if (row == -1 || model.getValueAt(row, 1).equals("-")) {
            JOptionPane.showMessageDialog(this, "Lütfen listeden geçerli bir talep seçin.");
            return;
        }

        String isbn = (String) model.getValueAt(row, 1);

        try {
            if (onaylandi) {
                DosyaIslemleri.kitapOnayla(isbn); // Tarihi atar, durumu Oduncte yapar
                JOptionPane.showMessageDialog(this, "Kitap onaylandı. İade tarihi 2 hafta sonrasına ayarlandı.");
            } else {
                DosyaIslemleri.kitapIadeEt(isbn); // Reddedilince durum Müsait'e döner
                JOptionPane.showMessageDialog(this, "Talep reddedildi.");
            }
            verileriYukle(); // Listeyi yenile
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}