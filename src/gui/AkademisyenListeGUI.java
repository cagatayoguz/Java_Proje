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

public class AkademisyenListeGUI extends JFrame {

    // Tabloyu filtrelemek için gerekli araç
    private TableRowSorter<DefaultTableModel> sorter;

    public AkademisyenListeGUI() {
        setTitle("Akademik Kadro");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- 1. ÜST PANEL (Başlık + Arama) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(248, 249, 250));
        topPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Başlık
        JLabel lblTitle = new JLabel("Akademik Personel Listesi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(33, 37, 41));
        topPanel.add(lblTitle, BorderLayout.WEST);

        // Arama Kısmı (Sağ Tarafta)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(248, 249, 250));

        JLabel lblAra = new JLabel("İsimle Ara: ");
        lblAra.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JTextField txtAra = new JTextField(15);
        txtAra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtAra.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(5, 5, 5, 5)
        ));

        searchPanel.add(lblAra);
        searchPanel.add(txtAra);
        topPanel.add(searchPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // --- 2. TABLO KISMI ---

        String[] kolonlar = {"Unvan", "Ad Soyad", "Uzmanlık Alanı", "E-Posta"};
        DefaultTableModel model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        // Verileri Yükle
        List<OgretimUyesi> hocalar = AkademisyenVerisi.getAkademisyenListesi();
        for (OgretimUyesi hoca : hocalar) {
            Object[] satir = {
                    hoca.getUnvan(),
                    hoca.getAd() + " " + hoca.getSoyad(),
                    hoca.getUzmanlikAlani(),
                    hoca.getEposta()
            };
            model.addRow(satir);
        }

        JTable table = new JTable(model);

        // --- SIRALAMA VE FİLTRELEME (GENERIC KULLANIMI) ---
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Arama Kutusu Dinleyicisi (Her tuşa basıldığında çalışır)
        txtAra.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filtrele(); }
            public void removeUpdate(DocumentEvent e) { filtrele(); }
            public void changedUpdate(DocumentEvent e) { filtrele(); }

            private void filtrele() {
                String text = txtAra.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    // (?i) büyük/küçük harf duyarsız arama yapar. 1. sütun (Ad Soyad) taranır.
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
                    } catch (Exception ex) {
                        // Regex hatası olursa yoksay
                    }
                }
            }
        });

        // Tablo Görsel Ayarları
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setSelectionForeground(Color.BLACK);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(240, 240, 240));

        // Sütun Genişlikleri
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 30, 30, 30));
        scrollPane.getViewport().setBackground(Color.WHITE);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Alt Bilgi (Toplam Sayı)
        JLabel lblToplam = new JLabel("Toplam Akademisyen Sayısı: " + hocalar.size() + "   ");
        lblToplam.setHorizontalAlignment(SwingConstants.RIGHT);
        lblToplam.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblToplam.setBorder(new EmptyBorder(5,0,10,30));
        mainPanel.add(lblToplam, BorderLayout.SOUTH);
    }
}