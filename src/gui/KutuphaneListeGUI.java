package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.KampusUygulamasi;
import model.Kitap;
import java.awt.*;
import java.util.List;

public class KutuphaneListeGUI extends JFrame {

    private JTable kitapTable;
    private DefaultTableModel tableModel;

    public KutuphaneListeGUI() {
        setTitle("Kütüphane Kitap Listesi");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] kolonBasliklari = {"Kitap Adı", "Yazar", "ISBN", "Müsait"};
        tableModel = new DefaultTableModel(kolonBasliklari, 0);
        kitapTable = new JTable(tableModel);

        add(new JScrollPane(kitapTable), BorderLayout.CENTER);

        // Verileri Yükle
        kitaplariYukle(KampusUygulamasi.getKutuphaneServisi().tumKitaplariGetir());

        // Buton Paneli (Ödünç Al, İade Et, vb.)
        JPanel butonPanel = new JPanel(new FlowLayout());
        // Ödünç alma butonu, müsaitlik kontrolü yapmalı (Polimorfik Durum Kontrolü)
        JButton btnOduncAl = new JButton("Ödünç Al");
        btnOduncAl.addActionListener(e -> oduncAlAction());
        butonPanel.add(btnOduncAl);

        // ... (Diğer butonlar)
        add(butonPanel, BorderLayout.SOUTH);
    }

    private void kitaplariYukle(List<Kitap> kitaplar) {
        tableModel.setRowCount(0); // Tabloyu temizle
        for (Kitap kitap : kitaplar) {
            // Tüm kitapları listeler (Dosyadan okunan ve eklenen kitaplar)
            tableModel.addRow(new Object[]{
                    kitap.getKitapAdi(),
                    kitap.getYazarAdi(),
                    kitap.getIsbn(),
                    kitap.isMusaitMi() ? "Müsait" : "Ödünçte"
            });
        }
    }

    private void oduncAlAction() {
        int seciliSatir = kitapTable.getSelectedRow();
        if (seciliSatir == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir kitap seçin.");
            return;
        }

        String isbn = (String) tableModel.getValueAt(seciliSatir, 2);

        // Kütüphane servisi üzerinden ödünç alma işlemini çağır
        if (KampusUygulamasi.getKutuphaneServisi().kitapOduncAl(isbn)) {
            JOptionPane.showMessageDialog(this, "Kitap başarıyla ödünç alındı.");
        } else {
            // Müsait değil uyarısı (Ödünç alınan bir kitabı tekrar ödünç almama)
            JOptionPane.showMessageDialog(this, "Kitap müsait değil veya hata oluştu!", "Uyarı", JOptionPane.WARNING_MESSAGE);
        }

        // Listeyi yenile
        kitaplariYukle(KampusUygulamasi.getKutuphaneServisi().tumKitaplariGetir());
    }
}