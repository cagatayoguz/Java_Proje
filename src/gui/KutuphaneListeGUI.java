package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

// Kütüphane envanterinin görüntülendiği, arama yapıldığı ve
// Ödünç/İade işlemlerinin tetiklendiği ana arayüz sınıfı.
public class KutuphaneListeGUI extends JFrame {

    // Tablo işlemlerini yönetmek için gerekli model ve sıralayıcı nesneler.
    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter;

    public KutuphaneListeGUI() {
        // Pencere ayarları (Başlık, Boyut, Kapanma davranışı)
        setTitle("Kütüphane - Kitap Listesi");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        // Başlık ve Arama
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        JLabel lblTitle = new JLabel("Kütüphane Arşivi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerPanel.add(lblTitle, BorderLayout.NORTH);

        // Arama çubuğu
        JTextField txtAra = new JTextField(20);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(248, 249, 250));
        searchPanel.add(new JLabel("🔍 Ara: "));
        searchPanel.add(txtAra);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // TABLO YAPISI
        String[] kolonlar = {"Kitap Adı", "Yazar", "ISBN", "Durum", "İade Tarihi", "Alan Öğrenci"};

        // Tablo üzerindeki hücrelerin elle değiştirilmesini engellendi
        model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(30); // Okunabilirlik için satır yüksekliği artırıldı.

        // Tabloyu filtreleyebilmek için Sorter bağladım.
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(0, 40, 0, 40));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Verileri dosyadan çekip tabloya dolduran metodu çağırdım.
        verileriYukle();

        // Arama Fonksiyonu
        txtAra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = txtAra.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null); // Yazı yoksa filtreyi kaldır
                } else {
                    // Regex kullanarak büyük/küçük harf duyarsız arama yapılıyor.
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        // ALT PANEL (Butonlar)
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 30));
        footerPanel.setBackground(new Color(248, 249, 250));

        JButton btnTalep = new JButton("Talep Oluştur");
        btnTalep.setBackground(new Color(13, 110, 253)); // Mavi
        btnTalep.setForeground(Color.WHITE);

        JButton btnIade = new JButton("İade Et");
        btnIade.setBackground(new Color(220, 53, 69)); // Kırmızı
        btnIade.setForeground(Color.WHITE);

        // Buton aksiyonları ilgili metotlara yönlendirildi.
        btnTalep.addActionListener(e -> talepEtAction());
        btnIade.addActionListener(e -> iadeEtAction());

        footerPanel.add(btnTalep);
        footerPanel.add(btnIade);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    // Veri Yükleme Metodu
    private void verileriYukle() {
        model.setRowCount(0); // Tabloyu temizle
        List<String[]> kitaplar = DosyaIslemleri.kitaplariOkuDetayli();

        for (String[] k : kitaplar) {
            String durum = k[3];
            // Veritabanındaki kodları kullanıcı dostu metinlere çevirdim.
            if(durum.equals("Bekliyor")) durum = "Onay Bekliyor";
            else if(durum.equals("Oduncte")) durum = "Ödünçte";

            model.addRow(new Object[]{k[0], k[1], k[2], durum, k[4], k[5]});
        }
    }

    // TALEP ETME İŞLEMİ
    private void talepEtAction() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir kitap seçiniz.");
            return;
        }

        // Sıralama yapıldığı için modeldeki gerçek indeksi bulmamız gerekiyor.
        int modelRow = table.convertRowIndexToModel(row);

        String durum = (String) model.getValueAt(modelRow, 3);
        String isbn = (String) model.getValueAt(modelRow, 2);

        // Kitap müsait değilse işlem yapılmasına izin verilmiyor.
        if (!durum.equalsIgnoreCase("Müsait") && !durum.equalsIgnoreCase("Musait")) {
            JOptionPane.showMessageDialog(this, "Bu kitap şu an müsait değil (" + durum + ").");
            return;
        }

        // Öğrenci doğrulama adımı
        String ogrenciNo = JOptionPane.showInputDialog(this, "Öğrenci Numaranız:");
        if (ogrenciNo != null && !ogrenciNo.trim().isEmpty()) {
            Map<String, String[]> ogrenciler = DosyaIslemleri.ogrencileriOku();

            if (ogrenciler.containsKey(ogrenciNo)) {
                String[] bilgiler = ogrenciler.get(ogrenciNo);
                String tamAd = bilgiler[0] + " " + bilgiler[1];

                try {
                    // Servis katmanına talep iletiliyor.
                    DosyaIslemleri.kitapTalepEt(isbn, tamAd);
                    JOptionPane.showMessageDialog(this, "Talebiniz alındı! Yönetici onayından sonra teslim alabilirsiniz.");
                    verileriYukle(); // Tabloyu güncelle
                } catch (Exception ex) { ex.printStackTrace(); }
            } else {
                JOptionPane.showMessageDialog(this, "Öğrenci numarası bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // İADE METODU (Kitabın doğru kişiden alınıp alınmadığını kontrol eden algoritma)
    private void iadeEtAction() {
        // Seçim Kontrolü
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen iade edilecek kitabı seçiniz.");
            return;
        }

        // Veri Çekme
        int modelRow = table.convertRowIndexToModel(row);
        String kitapAdi = (String) model.getValueAt(modelRow, 0);
        String isbn = (String) model.getValueAt(modelRow, 2);
        String durum = (String) model.getValueAt(modelRow, 3);
        String kitapAlanKisi = (String) model.getValueAt(modelRow, 5); // Kitabı alan asıl kişi

        // Mantıksal Kontrol: Zaten kütüphanedeyse iade alınamaz.
        if (durum.equalsIgnoreCase("Müsait") || durum.equalsIgnoreCase("Musait")) {
            JOptionPane.showMessageDialog(this, "Bu kitap zaten kütüphanede (Ödünç verilmemiş).");
            return;
        }

        // Öğrenci Doğrulama
        String ogrenciNo = JOptionPane.showInputDialog(this, "İade eden öğrencinin numarasını giriniz:");

        if (ogrenciNo != null && !ogrenciNo.trim().isEmpty()) {
            Map<String, String[]> ogrenciler = DosyaIslemleri.ogrencileriOku();

            if (ogrenciler.containsKey(ogrenciNo)) {
                String[] bilgiler = ogrenciler.get(ogrenciNo);
                String iadeEdenIsim = bilgiler[0] + " " + bilgiler[1];

                // Kitabı alan kişi ile iade eden kişi aynı mı?
                boolean isimlerAyni = kitapAlanKisi.trim().equalsIgnoreCase(iadeEdenIsim.trim());

                if (!isimlerAyni) {
                    // İsimler tutmuyorsa işlem kesinlikle reddediliyor.
                    JOptionPane.showMessageDialog(this,
                            "⛔ HATA: İsim Uyuşmazlığı!\n\n" +
                                    "Kitabı Alan: " + kitapAlanKisi + "\n" +
                                    "İade Eden: " + iadeEdenIsim + "\n\n" +
                                    "Güvenlik gereği iade işlemi gerçekleştirilemez.",
                            "İşlem Engellendi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Onay ve İşlem
                int onay = JOptionPane.showConfirmDialog(this,
                        "Kitap: " + kitapAdi + "\nİade Eden: " + iadeEdenIsim + "\n\nİade işlemini onaylıyor musunuz?",
                        "İade Onayı", JOptionPane.YES_NO_OPTION);

                if (onay == JOptionPane.YES_OPTION) {
                    try {
                        // Dosya işlemleri servisi çağrılarak kitap durumu güncelleniyor.
                        DosyaIslemleri.kitapIadeEt(isbn);

                        JOptionPane.showMessageDialog(this, "✅ İade başarıyla alındı.");
                        verileriYukle(); // Listeyi yenile
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Girdiğiniz numaraya ait öğrenci bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}