package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

public class KutuphaneListeGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter;

    public KutuphaneListeGUI() {
        setTitle("Kütüphane - Kitap Listesi");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        // --- ÜST PANEL ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        JLabel lblTitle = new JLabel("Kütüphane Arşivi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerPanel.add(lblTitle, BorderLayout.NORTH);

        JTextField txtAra = new JTextField(20);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(248, 249, 250));
        searchPanel.add(new JLabel("🔍 Ara: "));
        searchPanel.add(txtAra);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- TABLO ---
        String[] kolonlar = {"Kitap Adı", "Yazar", "ISBN", "Durum", "İade Tarihi", "Alan Öğrenci"};
        model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(0, 40, 0, 40));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        verileriYukle();

        txtAra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = txtAra.getText();
                if (text.trim().length() == 0) sorter.setRowFilter(null);
                else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        });

        // --- ALT BUTONLAR ---
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 30));
        footerPanel.setBackground(new Color(248, 249, 250));

        JButton btnTalep = new JButton("Talep Oluştur");
        btnTalep.setBackground(new Color(13, 110, 253));
        btnTalep.setForeground(Color.WHITE);

        JButton btnIade = new JButton("İade Et");
        btnIade.setBackground(new Color(220, 53, 69));
        btnIade.setForeground(Color.WHITE);

        btnTalep.addActionListener(e -> talepEtAction());
        btnIade.addActionListener(e -> iadeEtAction());

        footerPanel.add(btnTalep);
        footerPanel.add(btnIade);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private void verileriYukle() {
        model.setRowCount(0);
        List<String[]> kitaplar = DosyaIslemleri.kitaplariOkuDetayli();
        for (String[] k : kitaplar) {
            String durum = k[3];
            // Durum Türkçeleştirme
            if(durum.equals("Bekliyor")) durum = "Onay Bekliyor";
            else if(durum.equals("Oduncte")) durum = "Ödünçte";

            model.addRow(new Object[]{k[0], k[1], k[2], durum, k[4], k[5]});
        }
    }

    private void talepEtAction() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Lütfen bir kitap seçiniz."); return; }
        int modelRow = table.convertRowIndexToModel(row);

        String durum = (String) model.getValueAt(modelRow, 3);
        String isbn = (String) model.getValueAt(modelRow, 2);

        if (!durum.equalsIgnoreCase("Müsait") && !durum.equalsIgnoreCase("Musait")) {
            JOptionPane.showMessageDialog(this, "Bu kitap şu an müsait değil (" + durum + ").");
            return;
        }

        String ogrenciNo = JOptionPane.showInputDialog(this, "Öğrenci Numaranız:");
        if (ogrenciNo != null && !ogrenciNo.trim().isEmpty()) {
            Map<String, String[]> ogrenciler = DosyaIslemleri.ogrencileriOku();
            if (ogrenciler.containsKey(ogrenciNo)) {
                String[] bilgiler = ogrenciler.get(ogrenciNo);
                String tamAd = bilgiler[0] + " " + bilgiler[1];

                try {
                    DosyaIslemleri.kitapTalepEt(isbn, tamAd);
                    JOptionPane.showMessageDialog(this, "Talebiniz alındı! Yönetici onayından sonra teslim alabilirsiniz.");
                    verileriYukle();
                } catch (Exception ex) { ex.printStackTrace(); }
            } else {
                JOptionPane.showMessageDialog(this, "Öğrenci numarası bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // --- GÜVENLİ İADE METODU ---
    private void iadeEtAction() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Lütfen iade edilecek kitabı seçiniz."); return; }
        int modelRow = table.convertRowIndexToModel(row);

        String isbn = (String) model.getValueAt(modelRow, 2);
        String durum = (String) model.getValueAt(modelRow, 3);
        String alanKisi = (String) model.getValueAt(modelRow, 5); // Kitabı alan kişinin adı

        // Sadece "Ödünçte" olan kitap iade edilebilir
        if (!durum.equals("Ödünçte")) {
            JOptionPane.showMessageDialog(this, "Bu kitap şu an ödünçte değil (Durum: " + durum + ").");
            return;
        }

        // 1. Öğrenci Numarasını İste
        String ogrenciNo = JOptionPane.showInputDialog(this, "İade işlemi için Öğrenci Numaranızı giriniz:");
        if (ogrenciNo == null || ogrenciNo.trim().isEmpty()) return;

        // 2. Numarayı Kontrol Et
        Map<String, String[]> ogrenciler = DosyaIslemleri.ogrencileriOku();
        if (!ogrenciler.containsKey(ogrenciNo)) {
            JOptionPane.showMessageDialog(this, "HATA: Girilen numara sistemde kayıtlı değil!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. İsimleri Karşılaştır
        String[] bilgiler = ogrenciler.get(ogrenciNo);
        String iadeEdenIsim = bilgiler[0] + " " + bilgiler[1]; // Ad + Soyad

        // Not: Küçük/büyük harf duyarsız ve boşlukları temizleyerek kontrol et
        if (alanKisi.trim().equalsIgnoreCase(iadeEdenIsim.trim())) {
            // Eşleşme Başarılı -> İadeyi Yap
            try {
                DosyaIslemleri.kitapIadeEt(isbn);
                JOptionPane.showMessageDialog(this, "Teşekkürler " + iadeEdenIsim + ", kitap iade alındı.");
                verileriYukle();
            } catch(Exception e) { e.printStackTrace(); }
        } else {
            // Eşleşme Başarısız -> Hata Ver
            JOptionPane.showMessageDialog(this,
                    "HATA: Yetkisiz İşlem!\n\n" +
                            "Bu kitabı alan kişi: " + alanKisi + "\n" +
                            "Sizin isminiz: " + iadeEdenIsim + "\n\n" +
                            "Kitabı sadece alan kişi iade edebilir.",
                    "Güvenlik Uyarısı", JOptionPane.ERROR_MESSAGE);
        }
    }
}