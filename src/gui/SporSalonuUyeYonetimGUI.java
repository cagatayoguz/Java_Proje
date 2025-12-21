package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Yönetici panelinde, spor salonunu aktif olarak kullanan üyelerin listelendiği
// ve üyelik iptal işlemlerinin yönetildiği arayüz sınıfı.
public class SporSalonuUyeYonetimGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public SporSalonuUyeYonetimGUI() {
        // Pencere yapılandırması (Başlık, Boyut, Konumlandırma) gerçekleştirildi.
        setTitle("Spor Salonu - Aktif Üye Listesi");
        setSize(900, 500);
        // Pencere kapatıldığında ana yönetim panelinin açık kalması için DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        //Başlık Panel
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(248, 249, 250)); // Kurumsal gri arka plan
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblBaslik = new JLabel(" Spor Salonu Aktif Üye Listesi");
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lblBaslik);
        mainPanel.add(header, BorderLayout.NORTH);

        //Tablo Yapılandırması
        String[] kolonlar = {"Ad Soyad", "Öğrenci No", "Üyelik Tipi", "Ücret", "Durum"};

        // Veri tutarlılığını korumak için hücrelerin elle düzenlenmesi engellendi.
        model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Silme işlemi yapılacağı için seçim rengi kırmızımsı bir ton olarak ayarlandı.
        table.setSelectionBackground(new Color(255, 230, 230));
        table.setSelectionForeground(Color.BLACK);

        // Mevcut aktif üyelerin tabloya yüklenmesi sağlandı.
        verileriYukle();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 20, 10, 20));
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //işle butonu
        JPanel altPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        altPanel.setBackground(Color.WHITE);

        JButton btnSil = new JButton("Seçili Üyeliği İptal Et (İade)");
        btnSil.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSil.setBackground(new Color(220, 53, 69)); // Kritik işlem (Kırmızı)
        btnSil.setForeground(Color.WHITE);

        // Butona tıklandığında silme/iptal metodu tetiklenir
        btnSil.addActionListener(e -> silmeIslemi());

        altPanel.add(btnSil);
        mainPanel.add(altPanel, BorderLayout.SOUTH);
    }

    // --- Veri Filtreleme ve Yükleme ---
    private void verileriYukle() {
        model.setRowCount(0); // Tablo temizlendi.
        List<String[]> uyelikler = DosyaIslemleri.sporUyelikleriOku();

        for (String[] u : uyelikler) {
            // FİLTRELEME MANTIĞI:
            // Yöneticinin bu ekranda sadece "Aktif" üyeleri görmesi amaçlandı.
            // "Bekliyor" durumundaki kayıtlar Onay ekranında gösterilmektedir.
            if ("Aktif".equals(u[4])) {
                model.addRow(u);
            }
        }
    }

    // --- Üyelik İptal ve Silme İşlemi ---
    private void silmeIslemi() {
        int row = table.getSelectedRow();

        // Satır seçimi kontrolü yapıldı.
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen iptal edilecek üyeliği seçiniz.");
            return;
        }

        // Seçilen satırdan gerekli bilgiler (Ad, No, Ücret) alındı.
        String adSoyad = (String) model.getValueAt(row, 0);
        String ogrenciNo = (String) model.getValueAt(row, 1);
        String ucret = (String) model.getValueAt(row, 3);

        // Kritik bir işlem olduğu için kullanıcıdan son onay istendi.
        int onay = JOptionPane.showConfirmDialog(this,
                "Sayın " + adSoyad + " isimli öğrencinin üyeliği iptal edilecek.\n" +
                        "Ödenecek İade Tutarı: " + ucret + "\n\nBu işlemi onaylıyor musunuz?",
                "Üyelik İptali ve İade", JOptionPane.YES_NO_OPTION);

        if (onay == JOptionPane.YES_OPTION) {
            try {
                // Servis katmanı üzerinden silme işlemi gerçekleştirildi.
                boolean sonuc = DosyaIslemleri.sporUyelikSil(ogrenciNo);

                if (sonuc) {
                    JOptionPane.showMessageDialog(this,
                            "Üyelik başarıyla sonlandırıldı.\nSistemden kayıt silindi ve iade işlemi yapıldı.",
                            "İşlem Tamamlandı", JOptionPane.INFORMATION_MESSAGE);
                    verileriYukle(); // Güncel listeyi göstermek için tablo yenilendi.
                } else {
                    JOptionPane.showMessageDialog(this, "Hata: Kayıt dosyada bulunamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "İşlem sırasında hata oluştu: " + ex.getMessage());
            }
        }
    }
}