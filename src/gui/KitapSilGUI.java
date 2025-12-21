package gui;

import model.Kitap; // Model sınıfı import edildi
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
        // Pencere yapılandırması (Başlık, Boyut, Konum) gerçekleştirildi.
        setTitle("Kitap Sil");
        setSize(800, 500);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Pencerenin ekranın ortasında açılması sağlandı.

        // Ana panel oluşturuldu ve yerleşim düzeni (BorderLayout) ayarlandı.
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // Üst Panel
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(248, 249, 250));

        JLabel lbl = new JLabel(" Envanterdeki Kitaplar");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lbl);
        mainPanel.add(header, BorderLayout.NORTH);

        // Tablo Yapısı
        String[] cols = {"Kitap Adı", "Yazar", "ISBN", "Durum"};

        // Veri bütünlüğünü korumak için tabloya erişim engellendi
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Pencere açılışında mevcut verilerin tabloya yüklenmesi sağlandı.
        listeyiYukle();

        // Tablonun kaydırılabilir olması için ScrollPane içine yerleştirildi.
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(10, 20, 10, 20));
        sp.getViewport().setBackground(Color.WHITE);
        mainPanel.add(sp, BorderLayout.CENTER);

        //Alt Panel
        JPanel footer = new JPanel();
        footer.setBackground(Color.WHITE);

        JButton btnSil = new JButton("Seçili Kitabı Sil");

        btnSil.setBackground(new Color(220, 53, 69));
        btnSil.setForeground(Color.WHITE);
        btnSil.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Butona tıklandığında silme metodu çalışıyor
        btnSil.addActionListener(e -> sil());

        footer.add(btnSil);
        mainPanel.add(footer, BorderLayout.SOUTH);
    }

    // Veri Yükleme Metodu
    private void listeyiYukle() {
        model.setRowCount(0); // Tablo içeriği temizlendi.

        // Servis katmanından çekilen detaylı kitap verileri tablo modeline entegre edildi.
        List<String[]> list = DosyaIslemleri.kitaplariOkuDetayli();
        for(String[] k : list) {
            model.addRow(new Object[]{k[0], k[1], k[2], k[3]});
        }
    }

    //  OOP ENTEGRE EDİLMİŞ SİLME İŞLEMİ
    private void sil() {
        int r = table.getSelectedRow();

        // Kullanıcının tablodan bir satır seçip seçmediği kontrol edildi.
        if(r == -1) {
            JOptionPane.showMessageDialog(this,"Lütfen silinecek kitabı seçiniz.");
            return;
        }

        // Silme işlemi için ISBN değeri seçili satırdan alındı.
        String isbn = (String) model.getValueAt(r, 2);

        // Veri güvenliği için kullanıcıdan son onay istendi.
        int c = JOptionPane.showConfirmDialog(this,
                "ISBN: " + isbn + " olan kitap silinecek. Onaylıyor musunuz?",
                "Silme Onayı", JOptionPane.YES_NO_OPTION);

        if(c == JOptionPane.YES_OPTION) {
            try {
                // Silme işlemi Kitap nesnesi üzerinden gerçekleştirildi.
                boolean sonuc = new Kitap("", "", "", "Müsait").sil(isbn);

                if (sonuc) {
                    // Silme işlemi başarılı olduğunda liste güncellendi ve kullanıcı bilgilendirildi.
                    listeyiYukle();
                    JOptionPane.showMessageDialog(this, "Kitap başarıyla silindi.");
                } else {
                    JOptionPane.showMessageDialog(this, "Silme işlemi sırasında hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                }

            } catch(Exception ex) {
                ex.printStackTrace(); // Olası hatalar konsola yazdırıldı.
            }
        }
    }
}