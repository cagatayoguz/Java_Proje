package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Yönetici panelinde, mevcut duyuruların listelendiği ve silinebildiği arayüz sınıfı.
public class DuyuruSilGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public DuyuruSilGUI() {
        // Pencere yapılandırması (Başlık, Boyut, Konum)
        setTitle("Duyuruları Yönet");
        setSize(800, 500);
        // Pencere kapatıldığında ana uygulama akışının devam etmesi için DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana panel oluşturuldu ve arka plan rengi belirlendi.
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- 1. Üst Panel (Header) ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(248, 249, 250)); // Hafif gri ton

        JLabel lbl = new JLabel(" Yayındaki Duyurular");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lbl);
        mainPanel.add(header, BorderLayout.NORTH);

        // --- 2. Tablo Yapısı ---
        String[] cols = {"Tarih", "Başlık", "İçerik (Önizleme)"};

        // Tablo modeli, hücrelerin doğrudan düzenlenmesini engellemek amacıyla
        // isCellEditable metodu geçersiz kılınarak (override) oluşturuldu.
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(30); // Satır yüksekliği okunabilirlik için artırıldı.
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Verilerin tabloya yüklenmesi işlemi başlatıldı.
        yukle();

        // Tablo taşma durumuna karşı ScrollPane içerisine alındı.
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(10, 20, 10, 20)); // Kenar boşlukları ayarlandı.
        sp.getViewport().setBackground(Color.WHITE);
        mainPanel.add(sp, BorderLayout.CENTER);

        // --- 3. Alt Panel (Butonlar) ---
        JPanel footer = new JPanel();
        footer.setBackground(Color.WHITE);

        JButton btnSil = new JButton("Seçili Duyuruyu Kaldır");
        // Silme işlemi kritik olduğu için buton rengi kırmızı (Danger) olarak ayarlandı.
        btnSil.setBackground(new Color(220, 53, 69));
        btnSil.setForeground(Color.WHITE);
        btnSil.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Butona tıklandığında silme metodunun çalışması sağlandı.
        btnSil.addActionListener(e -> sil());

        footer.add(btnSil);
        mainPanel.add(footer, BorderLayout.SOUTH);
    }

    // --- Veri Yükleme Metodu ---
    // Dosyadan okunan verilerin tablo modeline aktarılmasını sağlar.
    private void yukle() {
        // Tablo temizlenerek mükerrer kayıt oluşumu engellendi.
        model.setRowCount(0);

        // Servis katmanından güncel liste çekildi.
        List<String[]> list = DosyaIslemleri.duyurulariOku();
        for(String[] d : list) {
            model.addRow(d);
        }
    }

    // --- Silme İşlemi Mantığı ---
    private void sil() {
        int r = table.getSelectedRow();

        // Kullanıcının bir seçim yapıp yapmadığı kontrol edildi.
        if(r == -1) {
            JOptionPane.showMessageDialog(this,"Lütfen silinecek duyuruyu seçiniz.");
            return;
        }

        // Seçilen satırdaki 'Başlık' bilgisi (1. sütun) referans alınarak silme işlemi yapılacak.
        String baslik = (String) model.getValueAt(r, 1);

        // Kullanıcıdan son onay istendi (Güvenlik önlemi).
        int c = JOptionPane.showConfirmDialog(this,
                "'" + baslik + "' başlıklı duyuru kalıcı olarak silinecek. Onaylıyor musunuz?",
                "Silme Onayı", JOptionPane.YES_NO_OPTION);

        if(c == JOptionPane.YES_OPTION) {
            try {
                // Servis katmanındaki silme metodu tetiklendi.
                DosyaIslemleri.duyuruSil(baslik);

                // İşlem başarılıysa tablo güncellendi.
                yukle();
                JOptionPane.showMessageDialog(this, "Duyuru başarıyla kaldırıldı.");
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}