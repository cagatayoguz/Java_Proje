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

    private final Color BG_COLOR = new Color(248, 249, 250);
    private final Color ACCENT_BLUE = new Color(13, 110, 253);

    public KutuphaneListeGUI() {
        setTitle("Kütüphane Sistemi - Kitap İşlemleri");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        setContentPane(mainPanel);

        // --- 1. ÜST PANEL ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(new EmptyBorder(30, 40, 20, 40));

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(BG_COLOR);
        JLabel lblTitle = new JLabel("Kütüphane Arşivi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        JLabel lblSub = new JLabel("Kitabı kimin aldığını gör, iade al veya talep et.");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(Color.GRAY);
        titlePanel.add(lblTitle);
        titlePanel.add(lblSub);

        JTextField txtAra = new JTextField(20);
        txtAra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtAra.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(BG_COLOR);
        JLabel lblSearchIcon = new JLabel("🔍 Kitap/Öğrenci Ara:  ");
        lblSearchIcon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchPanel.add(lblSearchIcon, BorderLayout.WEST);
        searchPanel.add(txtAra, BorderLayout.CENTER);
        searchPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        headerPanel.add(titlePanel, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 2. TABLO ---
        String[] kolonlar = {"Kitap Adı", "Yazar", "ISBN", "Durum", "İade Tarihi", "Alan Öğrenci"};

        model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setSelectionForeground(Color.BLACK);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setBorder(new LineBorder(new Color(230,230,230)));

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(new EmptyBorder(0, 40, 0, 40));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        verileriYukle();

        // Arama Kutusu
        txtAra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = txtAra.getText();
                if (text.trim().length() == 0) sorter.setRowFilter(null);
                else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        });

        // --- 3. ALT BUTONLAR ---
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 30));
        footerPanel.setBackground(BG_COLOR);
        footerPanel.setBorder(new EmptyBorder(0, 40, 0, 40));

        JButton btnTalep = createActionButton("Ödünç Ver", ACCENT_BLUE);
        JButton btnIade = createActionButton("İade Al", new Color(25, 135, 84));

        btnTalep.addActionListener(e -> talepEtAction());
        btnIade.addActionListener(e -> iadeEtAction());

        footerPanel.add(btnTalep);
        footerPanel.add(btnIade);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private JButton createActionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(12, 25, 12, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void verileriYukle() {
        model.setRowCount(0);
        List<String[]> kitaplar = DosyaIslemleri.kitaplariOkuDetayli();

        for (String[] k : kitaplar) {
            String hamDurum = k[3];
            String durumGoster = hamDurum;

            if(hamDurum.toLowerCase().contains("usait") || hamDurum.toLowerCase().contains("üsait")) {
                durumGoster = "Müsait";
            } else if(hamDurum.equals("Oduncte")) {
                durumGoster = "Ödünçte";
            }

            // Müsaitse tarih ve kişiyi gösterme
            String tarih = (k.length > 4 && !durumGoster.equals("Müsait")) ? k[4] : "-";
            String alanKisi = (k.length > 5 && !durumGoster.equals("Müsait")) ? k[5] : "-";

            model.addRow(new Object[]{k[0], k[1], k[2], durumGoster, tarih, alanKisi});
        }
    }

    // --- 1. ÖDÜNÇ VERME ---
    private void talepEtAction() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Lütfen bir kitap seçiniz."); return; }
        int modelRow = table.convertRowIndexToModel(row);

        String durum = (String) model.getValueAt(modelRow, 3);
        String isbn = (String) model.getValueAt(modelRow, 2);
        String kitapAdi = (String) model.getValueAt(modelRow, 0);

        if (!durum.equalsIgnoreCase("Müsait") && !durum.equalsIgnoreCase("Musait")) {
            JOptionPane.showMessageDialog(this, "Bu kitap şu an müsait değil (" + durum + ").");
            return;
        }

        String ogrenciNo = JOptionPane.showInputDialog(this, "Ödünç alacak öğrencinin numarasını giriniz:");

        if (ogrenciNo != null && !ogrenciNo.trim().isEmpty()) {
            Map<String, String[]> ogrenciler = DosyaIslemleri.ogrencileriOku();

            if (ogrenciler.containsKey(ogrenciNo)) {
                String[] bilgiler = ogrenciler.get(ogrenciNo);
                String tamAd = bilgiler[0] + " " + bilgiler[1];

                try {
                    DosyaIslemleri.kitapTalepEt(isbn, tamAd);
                    JOptionPane.showMessageDialog(this, "İşlem Başarılı!\nKitap: " + kitapAdi + "\nAlan: " + tamAd);
                    verileriYukle();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Öğrenci bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // --- 2. İADE ALMA (HATA DÜZELTİLEN KISIM) ---
    private void iadeEtAction() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Lütfen iade edilecek kitabı seçiniz."); return; }
        int modelRow = table.convertRowIndexToModel(row);

        String kitapAdi = (String) model.getValueAt(modelRow, 0);
        String isbn = (String) model.getValueAt(modelRow, 2);
        String durum = (String) model.getValueAt(modelRow, 3);
        String kitapAlanKisi = (String) model.getValueAt(modelRow, 5); // Tablodaki İsim

        if (durum.equalsIgnoreCase("Müsait") || durum.equalsIgnoreCase("Musait")) {
            JOptionPane.showMessageDialog(this, "Bu kitap zaten kütüphanede.");
            return;
        }

        String ogrenciNo = JOptionPane.showInputDialog(this, "İade eden öğrencinin numarasını giriniz:");

        if (ogrenciNo != null && !ogrenciNo.trim().isEmpty()) {
            Map<String, String[]> ogrenciler = DosyaIslemleri.ogrencileriOku();

            if (ogrenciler.containsKey(ogrenciNo)) {
                String[] bilgiler = ogrenciler.get(ogrenciNo);
                String iadeEdenIsim = bilgiler[0] + " " + bilgiler[1];

                // İsimler Uyuşuyor mu?
                boolean isimlerAyni = kitapAlanKisi.trim().equalsIgnoreCase(iadeEdenIsim.trim());

                if (isimlerAyni) {
                    // 1. Durum: İsimler Eşleşti -> Direkt Onay İste
                    int onay = JOptionPane.showConfirmDialog(this,
                            "Kitap: " + kitapAdi + "\nİade Eden: " + iadeEdenIsim + "\n\nOnaylıyor musunuz?",
                            "İade Onayı", JOptionPane.YES_NO_OPTION);

                    if (onay == JOptionPane.YES_OPTION) iadeyiTamamla(isbn);

                } else {
                    // 2. Durum: İsimler Eşleşmedi -> Yöneticiye Sor (ZORLA İADE)
                    // Kullanıcının işi tıkanmasın diye "Yine de iade al" seçeneği sunuyoruz.
                    String hataMesaji = "<html><body style='width: 300px'>" +
                            "<h3 style='color:red'>⚠️ İsim Uyuşmazlığı</h3>" +
                            "<p><b>Kitabı Alan:</b> " + kitapAlanKisi + "</p>" +
                            "<p><b>Numara Sahibi:</b> " + iadeEdenIsim + "</p><br>" +
                            "<p>Kişiler farklı görünüyor. <b>Yine de iade almak istiyor musunuz?</b></p>" +
                            "</body></html>";

                    int zorlaOnay = JOptionPane.showConfirmDialog(this, hataMesaji, "Güvenlik Uyarısı", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (zorlaOnay == JOptionPane.YES_OPTION) iadeyiTamamla(isbn);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Öğrenci bulunamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void iadeyiTamamla(String isbn) {
        try {
            DosyaIslemleri.kitapIadeEt(isbn);
            JOptionPane.showMessageDialog(this, "İade başarıyla alındı.");
            verileriYukle();
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}