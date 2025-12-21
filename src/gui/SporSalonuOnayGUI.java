package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Yönetici panelinde, öğrencilerin spor salonu üyelik başvurularının görüntülendiği
// ve onay/ret işlemlerinin yönetildiği arayüz sınıfı.
public class SporSalonuOnayGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public SporSalonuOnayGUI() {
        // Pencere yapılandırması (Başlık, Boyut, Konumlandırma) gerçekleştirildi.
        setTitle("Spor Salonu - Başvuru Onay Ekranı");
        setSize(850, 500);
        // Pencere kapatıldığında ana yönetim panelinin açık kalması için DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- Başlık Paneli ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(248, 249, 250)); // Kurumsal gri arka plan
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitle = new JLabel(" Onay Bekleyen Başvurular");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lblTitle);
        mainPanel.add(header, BorderLayout.NORTH);

        // --- Tablo Yapılandırması ---
        String[] cols = {"Ad Soyad", "Öğrenci No", "Üyelik Tipi", "Ücret", "Durum"};

        // Veri tutarlılığını korumak ve hatalı girişi önlemek için hücre düzenlemesi kapatıldı.
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(30); // Okunabilirlik için satır yüksekliği artırıldı.
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Seçim yapıldığında belirgin olması için sarımsı bir vurgu rengi kullanıldı.
        table.setSelectionBackground(new Color(255, 243, 205));
        table.setSelectionForeground(Color.BLACK);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(10, 20, 10, 20));
        sp.getViewport().setBackground(Color.WHITE);
        mainPanel.add(sp, BorderLayout.CENTER);

        // Mevcut başvuruların yüklenmesi sağlandı.
        verileriYukle();

        // --- Alt Panel (Butonlar) ---
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        footer.setBackground(Color.WHITE);

        JButton btnRed = new JButton("Reddet (Sil)");
        btnRed.setBackground(new Color(220, 53, 69)); // Kritik işlem (Kırmızı)
        btnRed.setForeground(Color.WHITE);
        btnRed.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton btnOnay = new JButton("Onayla (Aktif Et)");
        btnOnay.setBackground(new Color(25, 135, 84)); // Olumlu işlem (Yeşil)
        btnOnay.setForeground(Color.WHITE);
        btnOnay.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Buton aksiyonları tek bir metot üzerinden yönetildi.
        btnRed.addActionListener(e -> islemYap(false));
        btnOnay.addActionListener(e -> islemYap(true));

        footer.add(btnRed);
        footer.add(btnOnay);
        mainPanel.add(footer, BorderLayout.SOUTH);
    }

    // --- Veri Filtreleme ve Yükleme ---
    private void verileriYukle() {
        model.setRowCount(0); // Tablo temizlendi.
        List<String[]> list = DosyaIslemleri.sporUyelikleriOku();

        for (String[] u : list) {
            // Yöneticinin sadece aksiyon alması gereken "Bekliyor" durumundaki kayıtlar filtrelendi.
            // Aktif üyeler bu ekranda gösterilmez.
            if ("Bekliyor".equals(u[4])) {
                model.addRow(u);
            }
        }
    }

    // --- İşlem Mantığı ---
    private void islemYap(boolean onay) {
        int row = table.getSelectedRow();

        // Satır seçimi kontrolü yapıldı.
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir başvuru seçin.");
            return;
        }

        // Seçilen satırdan öğrenci numarası ve ad bilgisi alındı.
        String ad = (String) model.getValueAt(row, 0);
        String ogrNo = (String) model.getValueAt(row, 1);

        try {
            if (onay) {
                // Onay durumunda; veritabanındaki (dosyadaki) durum "Aktif" olarak güncellendi.
                DosyaIslemleri.sporUyelikGuncelle(ogrNo, "Aktif");
                JOptionPane.showMessageDialog(this, ad + " isimli üye başarıyla onaylandı.");
            } else {
                // Ret durumunda; başvuru kaydı sistemden tamamen silindi.
                DosyaIslemleri.sporUyelikSil(ogrNo);
                JOptionPane.showMessageDialog(this, "Başvuru reddedildi ve kayıtlardan silindi.");
            }
            // İşlem sonrası güncel liste tekrar yüklendi.
            verileriYukle();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "İşlem sırasında hata oluştu: " + ex.getMessage());
        }
    }
}