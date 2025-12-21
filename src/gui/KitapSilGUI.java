package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Yönetici panelinde, kütüphane envanterindeki kitapların listelendiği ve silme işleminin gerçekleştirildiği arayüz sınıfı.
public class KitapSilGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public KitapSilGUI() {
        // Pencere yapılandırması (Başlık, Boyut, Konum)
        setTitle("Kitap Sil");
        setSize(800, 500);
        // Pencere kapatıldığında ana menünün açık kalması için DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- Üst Panel (Header) ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(248, 249, 250)); // Kurumsal gri arka plan

        JLabel lbl = new JLabel(" Envanterdeki Kitaplar");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lbl);
        mainPanel.add(header, BorderLayout.NORTH);

        // --- Tablo Yapısı ---
        String[] cols = {"Kitap Adı", "Yazar", "ISBN", "Durum"};

        // Veri bütünlüğünü korumak amacıyla tablo hücrelerinin doğrudan düzenlenmesi engellendi.
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Pencere açılışında mevcut verilerin tabloya yüklenmesi sağlandı.
        listeyiYukle();

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(10, 20, 10, 20));
        sp.getViewport().setBackground(Color.WHITE);
        mainPanel.add(sp, BorderLayout.CENTER);

        // --- Alt Panel (İşlem Butonu) ---
        JPanel footer = new JPanel();
        footer.setBackground(Color.WHITE);

        JButton btnSil = new JButton("Seçili Kitabı Sil");
        // Silme işlemi kritik olduğu için buton rengi kırmızı (Danger) olarak belirlendi.
        btnSil.setBackground(new Color(220, 53, 69));
        btnSil.setForeground(Color.WHITE);
        btnSil.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnSil.addActionListener(e -> sil());

        footer.add(btnSil);
        mainPanel.add(footer, BorderLayout.SOUTH);
    }

    // --- Veri Yükleme Metodu ---
    private void listeyiYukle() {
        model.setRowCount(0); // Tablo temizlendi

        // Servis katmanından çekilen detaylı kitap verileri tablo modeline entegre edildi.
        List<String[]> list = DosyaIslemleri.kitaplariOkuDetayli();
        for(String[] k : list) {
            // Dizi elemanları sırasıyla: Ad, Yazar, ISBN, Durum
            model.addRow(new Object[]{k[0], k[1], k[2], k[3]});
        }
    }

    // --- Silme İşlemi Mantığı ---
    private void sil() {
        int r = table.getSelectedRow();

        // Kullanıcı seçimi kontrol edildi.
        if(r == -1) {
            JOptionPane.showMessageDialog(this,"Lütfen silinecek kitabı seçiniz.");
            return;
        }

        // Silme işlemi için benzersiz anahtar (Primary Key) olan ISBN değeri alındı.
        // ISBN, tablonun 2. indeksinde (3. sütun) yer almaktadır.
        String isbn = (String) model.getValueAt(r, 2);

        // Veri güvenliği için kullanıcıdan son onay istendi.
        int c = JOptionPane.showConfirmDialog(this,
                "ISBN: " + isbn + " olan kitap silinecek. Onaylıyor musunuz?",
                "Silme Onayı", JOptionPane.YES_NO_OPTION);

        if(c == JOptionPane.YES_OPTION) {
            try {
                // Servis katmanı üzerinden silme işlemi tetiklendi ve tablo güncellendi.
                DosyaIslemleri.kitapSil(isbn);
                listeyiYukle();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}