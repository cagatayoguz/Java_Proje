package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Yönetici panelinde, öğrencilerden gelen ödünç alma taleplerinin görüntülendiği
// ve onay/ret işlemlerinin yönetildiği arayüz sınıfı.
public class KutuphaneOnayGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public KutuphaneOnayGUI() {
        // Pencere yapılandırması (Başlık, Boyut, Konum) gerçekleştirildi.
        setTitle("Kitap Onay Ekranı");
        setSize(800, 500);
        // Pencere kapatıldığında ana yönetim panelinin açık kalması için DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // --- TABLO YAPILANDIRMASI ---
        String[] kolonlar = {"Kitap Adı", "ISBN", "Talep Eden Öğrenci", "Durum"};

        // Veri tutarlılığını korumak için hücre düzenlemesi kapatıldı.
        model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Bekleyen taleplerin tabloya yüklenmesi sağlandı.
        verileriYukle();

        // --- BUTONLAR VE AKSİYONLAR ---
        JPanel btnPanel = new JPanel();
        JButton btnOnayla = new JButton("Onayla");
        JButton btnReddet = new JButton("Reddet");

        btnOnayla.setBackground(new Color(25, 135, 84));
        btnOnayla.setForeground(Color.WHITE);

        btnReddet.setBackground(new Color(220, 53, 69));
        btnReddet.setForeground(Color.WHITE);

        // Tek bir metot üzerinden (islemYap) parametrik olarak aksiyon yönetimi sağlandı.
        btnOnayla.addActionListener(e -> islemYap(true));
        btnReddet.addActionListener(e -> islemYap(false));

        btnPanel.add(btnOnayla);
        btnPanel.add(btnReddet);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
    }

    // --- Veri Filtreleme ve Yükleme ---
    private void verileriYukle() {
        model.setRowCount(0); // Tablo temizlendi.
        List<String[]> kitaplar = DosyaIslemleri.kitaplariOkuDetayli();

        for (String[] k : kitaplar) {
            // Yalnızca statüsü "Bekliyor" olan kayıtlar filtrelenerek yönetici onayına sunuldu.
            if (k[3].equalsIgnoreCase("Bekliyor")) {
                model.addRow(new Object[]{k[0], k[2], k[5], "Onay Bekliyor"});
            }
        }
    }

    // --- İşlem Mantığı ---
    private void islemYap(boolean onay) {
        int row = table.getSelectedRow();

        // Satır seçimi kontrolü yapıldı.
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen işlem yapılacak talebi seçiniz.");
            return;
        }

        // Seçilen satırdaki ISBN bilgisi referans alınarak işlem yürütüldü.
        String isbn = (String) model.getValueAt(row, 1);

        try {
            if (onay) {
                // Onay durumunda kitap durumu 'Oduncte' olarak güncellendi.
                DosyaIslemleri.kitapOnayla(isbn);
                JOptionPane.showMessageDialog(this, "Kitap onaylandı ve öğrenciye teslim edildi.");
            } else {
                // Ret durumunda kitap tekrar 'Musait' durumuna çekildi.
                DosyaIslemleri.kitapReddet(isbn);
                JOptionPane.showMessageDialog(this, "Talep reddedildi, kitap tekrar rafa kaldırıldı.");
            }
            // İşlem sonrası liste güncellendi.
            verileriYukle();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}