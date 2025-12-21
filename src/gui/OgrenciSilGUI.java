package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

// Yönetici panelinde, sistemde kayıtlı öğrencilerin listelendiği ve
// kaydının silinebildiği arayüz sınıfı.
public class OgrenciSilGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public OgrenciSilGUI() {
        // Pencere yapılandırması (Başlık, Boyut, Konum) gerçekleştirildi.
        setTitle("Öğrenci Sil");
        setSize(800, 500);
        // Pencere kapatıldığında ana menünün açık kalması için DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- Başlık Alanı ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(248, 249, 250)); // Açık gri

        JLabel lblTitle = new JLabel(" Kayıtlı Öğrenciler (Silmek için seçiniz)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lblTitle);
        mainPanel.add(header, BorderLayout.NORTH);

        // --- Tablo Yapılandırması ---
        String[] cols = {"No", "Ad", "Soyad", "Bölüm"};

        // Veri tutarlılığı için hücrelerin elle düzenlenmesi engellendi.
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Silme işlemi olduğu için seçili satırın arka planı kırmızımsı yapılarak görsel uyarı verildi.
        table.setSelectionBackground(new Color(255, 230, 230));
        table.setSelectionForeground(Color.BLACK);

        // Mevcut verilerin tabloya yüklenmesi sağlandı.
        verileriYukle();

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(10, 20, 10, 20));
        sp.getViewport().setBackground(Color.WHITE);
        mainPanel.add(sp, BorderLayout.CENTER);

        // --- Silme Butonu ---
        JPanel footer = new JPanel();
        footer.setBackground(Color.WHITE);

        JButton btnSil = new JButton("Seçili Öğrenciyi Sil");
        btnSil.setBackground(new Color(220, 53, 69)); // Kritik işlem rengi (Kırmızı)
        btnSil.setForeground(Color.WHITE);
        btnSil.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Butona tıklandığında silme metodu tetiklendi.
        btnSil.addActionListener(e -> silmeIslemi());

        footer.add(btnSil);
        mainPanel.add(footer, BorderLayout.SOUTH);
    }

    // --- Veri Yükleme Metodu ---
    private void verileriYukle() {
        model.setRowCount(0); // Tablo temizlendi.

        // Servis katmanından Map yapısında dönen öğrenci verileri işlendi.
        Map<String, String[]> map = DosyaIslemleri.ogrencileriOku();

        // Map üzerindeki her bir kayıt (Anahtar-Değer) tabloya satır olarak eklendi.
        for (String no : map.keySet()) {
            String[] val = map.get(no);
            model.addRow(new Object[]{no, val[0], val[1], val[2]});
        }
    }

    // --- Silme İşlemi Mantığı ---
    private void silmeIslemi() {
        int row = table.getSelectedRow();

        // Seçim kontrolü yapıldı.
        if(row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silinecek öğrenciyi seçiniz.");
            return;
        }

        // Benzersiz anahtar (Primary Key) olan öğrenci numarası tablodan alındı.
        // Numara 0. sütunda yer almaktadır.
        String no = (String) model.getValueAt(row, 0);

        // Veri kaybını önlemek için kullanıcıdan son onay istendi.
        int confirm = JOptionPane.showConfirmDialog(this,
                "Öğrenci No: " + no + "\nBu kayıt silinsin mi?",
                "Silme Onayı", JOptionPane.YES_NO_OPTION);

        if(confirm == JOptionPane.YES_OPTION) {
            try {
                // Servis katmanı aracılığıyla silme işlemi gerçekleştirildi.
                DosyaIslemleri.ogrenciSil(no);

                // Tablo güncellendi ve kullanıcı bilgilendirildi.
                verileriYukle();
                JOptionPane.showMessageDialog(this, "Öğrenci kaydı başarıyla silindi.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}