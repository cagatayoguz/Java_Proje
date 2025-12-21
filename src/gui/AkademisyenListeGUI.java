package gui;

import model.AkademisyenVerisi;
import model.OgretimUyesi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

// Bu sınıf akademisyenlerin listelendiği pencereyi oluşturuyor.
public class AkademisyenListeGUI extends JFrame {

    // Tabloda sıralama ve filtreleme yapabilmek için sorter nesnesi lazım
    private TableRowSorter<DefaultTableModel> sorter;
    private JTable table;
    private DefaultTableModel model;

    // Hoca nesnelerini burada bir liste olarak tutuyoruz.
    private List<OgretimUyesi> hocaListesi;

    public AkademisyenListeGUI() {
        // Pencere başlığı ve temel ayarlar
        setTitle("Akademik Kadro");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın ortasında açılsın

        // Ana panel, her şeyi bunun içine dizeceğiz
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // Burası sayfanın en üstündeki gri alan
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(248, 249, 250)); // Hafif gri bir ton
        topPanel.setBorder(new EmptyBorder(20, 30, 20, 30)); // Kenar boşlukları

        JLabel lblTitle = new JLabel("Akademik Personel Listesi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        topPanel.add(lblTitle, BorderLayout.WEST);

        // Sağ üstteki arama kısmı
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(248, 249, 250));
        JTextField txtAra = new JTextField(15);
        searchPanel.add(new JLabel("İsimle Ara: "));
        searchPanel.add(txtAra);
        topPanel.add(searchPanel, BorderLayout.EAST);

        // Üst paneli ana panele ekle
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // --- 2. TABLO KISMI ---
        String[] kolonlar = {"Unvan", "Ad Soyad", "Uzmanlık Alanı", "E-Posta"};

        // Tablo modelini anonim sınıf olarak oluşturdum çünkü
        // hücrelerin üzerine çift tıklayınca düzenlenmesini istemiyorum (isCellEditable false).
        model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        // Verileri static metoddan çekip listeye atıyoruz
        hocaListesi = AkademisyenVerisi.getAkademisyenListesi();

        // Döngü ile listeyi gezip tabloya satır satır ekliyoruz
        for (OgretimUyesi hoca : hocaListesi) {
            model.addRow(new Object[]{
                    hoca.getUnvan(),
                    hoca.tamAdGetir(),
                    hoca.getUzmanlikAlani(),
                    hoca.getEposta()
            });
        }

        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Tablonun biraz daha güzel görünmesi için ayarlar
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Tablo taşarsa kaydırma çubuğu çıksın diye ScrollPane içine koydum
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 30, 10, 30));
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Arama Fonksiyonu: Kullanıcı her harf yazdığında tablo filtrelensin
        txtAra.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filtrele(); }
            public void removeUpdate(DocumentEvent e) { filtrele(); }
            public void changedUpdate(DocumentEvent e) { filtrele(); }

            private void filtrele() {
                String text = txtAra.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null); // Yazı yoksa filtreyi kaldır
                } else {
                    // Regex ile ismin içinde geçen harfleri arıyor (büyük/küçük harf duyarsız)
                    // 1 numaralı indeks "Ad Soyad" kolonu olduğu için oraya bakıyor
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
                }
            }
        });

        //3. ALT PANEL (YAZDIR BUTONU BURADA)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 20));
        bottomPanel.setBackground(Color.WHITE);

        // Toplam kaç hoca olduğunu gösteren etiket
        JLabel lblToplam = new JLabel("Toplam: " + hocaListesi.size() + " Akademisyen");
        lblToplam.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        //Yazdır butonu
        JButton btnYazdir = new JButton("Seçili Hocayı Yazdır (Konsol)");
        btnYazdir.setBackground(new Color(13, 110, 253)); // Bootstrap mavisi gibi olsun istedim
        btnYazdir.setForeground(Color.WHITE);
        btnYazdir.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Butona tıklanınca ne olacağını burada yazıyoruz
        btnYazdir.addActionListener(e -> {
            int viewRow = table.getSelectedRow();

            if (viewRow == -1) { // Eğer hiçbir şey seçilmediyse uyarı ver
                JOptionPane.showMessageDialog(this, "Lütfen listeden bir hoca seçiniz.");
                return;
            }

            // BURASI ÇOK ÖNEMLİ: Tabloyu A'dan Z'ye sıraladığımızda veya filtrelediğimizde
            int modelRow = table.convertRowIndexToModel(viewRow);

            // Şimdi gerçek indeksi kullanarak listeden hocayı çekebiliriz
            OgretimUyesi secilenHoca = hocaListesi.get(modelRow);

            // Bu metod hocanın bilgilerini konsola basacak
            secilenHoca.ciktiAl();

            JOptionPane.showMessageDialog(this, "Bilgiler aşağıdaki siyah ekrana (Konsol) yazdırıldı!");
        });

        bottomPanel.add(lblToplam);
        bottomPanel.add(btnYazdir);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
}