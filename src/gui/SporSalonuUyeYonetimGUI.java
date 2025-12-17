package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SporSalonuUyeYonetimGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public SporSalonuUyeYonetimGUI() {
        setTitle("Spor Salonu - Aktif Üye Listesi");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Başlık
        JLabel lblBaslik = new JLabel("Spor Salonu Aktif Üye Listesi", SwingConstants.CENTER);
        lblBaslik.setFont(new Font("Arial", Font.BOLD, 18));
        lblBaslik.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblBaslik, BorderLayout.NORTH);

        // Tablo
        String[] kolonlar = {"Ad Soyad", "Öğrenci No", "Üyelik Tipi", "Ücret", "Durum"};
        model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tablo üzerinde düzenleme yapılamasın
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);

        // Verileri Yükle
        verileriYukle();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Alt Panel (Silme Butonu)
        JPanel altPanel = new JPanel();
        JButton btnSil = new JButton("Seçili Üyeliği Sil (Ücret İadesi)");
        btnSil.setFont(new Font("Arial", Font.BOLD, 14));
        btnSil.setBackground(new Color(255, 100, 100)); // Kırmızımsı
        btnSil.setForeground(Color.WHITE);

        btnSil.addActionListener(e -> silmeIslemi());

        altPanel.add(btnSil);
        add(altPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void verileriYukle() {
        model.setRowCount(0); // Tabloyu temizle
        List<String[]> uyelikler = DosyaIslemleri.sporUyelikleriOku();

        boolean veriVar = false;
        for (String[] u : uyelikler) {
            // FİLTRELEME BURADA YAPILIYOR
            // Sadece durumu "Aktif" olanları ekle (Bekleyenler görünmesin)
            if ("Aktif".equals(u[4])) {
                model.addRow(u);
                veriVar = true;
            }
        }

        if (!veriVar) {
            model.addRow(new Object[]{"Kayıtlı aktif üye yok.", "-", "-", "-", "-"});
        }
    }

    private void silmeIslemi() {
        int row = table.getSelectedRow();

        // Seçim yapılmadıysa veya boş satır seçildiyse uyar
        if (row == -1 || model.getValueAt(row, 1).equals("-")) {
            JOptionPane.showMessageDialog(this, "Lütfen silinecek bir üye seçin.");
            return;
        }

        String adSoyad = (String) model.getValueAt(row, 0);
        String ogrenciNo = (String) model.getValueAt(row, 1);
        String ucret = (String) model.getValueAt(row, 3);

        int onay = JOptionPane.showConfirmDialog(this,
                "Sayın " + adSoyad + " isimli öğrencinin üyeliği silinecek.\n" +
                        "Ödenecek İade Tutarı: " + ucret + "\n\nOnaylıyor musunuz?",
                "Üyelik İptali ve İade", JOptionPane.YES_NO_OPTION);

        if (onay == JOptionPane.YES_OPTION) {
            try {
                boolean sonuc = DosyaIslemleri.sporUyelikSil(ogrenciNo);
                if (sonuc) {
                    JOptionPane.showMessageDialog(this,
                            "Üyelik başarıyla silindi.\nÜcret iadesi yapıldı.",
                            "İşlem Tamamlandı", JOptionPane.INFORMATION_MESSAGE);
                    verileriYukle(); // Listeyi yenile
                } else {
                    JOptionPane.showMessageDialog(this, "Hata: Kayıt dosyada bulunamadı.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hata oluştu: " + ex.getMessage());
            }
        }
    }
}