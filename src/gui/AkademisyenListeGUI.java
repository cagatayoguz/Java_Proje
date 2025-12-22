package gui;

import model.AkademisyenVerisi;
import model.OgretimUyesi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class AkademisyenListeGUI extends JFrame {

    private TableRowSorter<DefaultTableModel> sorter;
    private JTable table;
    private DefaultTableModel model;
    private List<OgretimUyesi> hocaListesi;

    public AkademisyenListeGUI() {
        setTitle("Akademik Kadro");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- ÜST PANEL (BAŞLIK VE ARAMA) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(248, 249, 250));
        topPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel lblTitle = new JLabel("Akademik Personel Listesi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        topPanel.add(lblTitle, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(248, 249, 250));
        JTextField txtAra = new JTextField(15);
        searchPanel.add(new JLabel("İsimle Ara: "));
        searchPanel.add(txtAra);
        topPanel.add(searchPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // TABLO KISMI
        String[] kolonlar = {"Unvan", "Ad Soyad", "Bölüm", "e posta"};

        model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        // Verileri statik sınıftan çekiyoruz (DosyaIslemleri henüz akademisyen desteklemediği için)
        hocaListesi = AkademisyenVerisi.getAkademisyenListesi();

        for (OgretimUyesi hoca : hocaListesi) {
            model.addRow(new Object[]{
                    hoca.getUnvan(),
                    hoca.getAd() + " " + hoca.getSoyad(), // Modeldeki getterları kullandık
                    hoca.getBolum(),
                    hoca.getPosta()
            });
        }

        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 30, 10, 30));
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // ARAMA FİLTRESİ
        txtAra.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filtrele(); }
            public void removeUpdate(DocumentEvent e) { filtrele(); }
            public void changedUpdate(DocumentEvent e) { filtrele(); }

            private void filtrele() {
                String text = txtAra.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    // 1. İndeks (Ad Soyad) kolonunda arama yapar
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
                }
            }
        });

        //  ALT PANEL
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 20));
        bottomPanel.setBackground(Color.WHITE);

        JLabel lblToplam = new JLabel("Toplam: " + hocaListesi.size() + " Akademisyen");
        lblToplam.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        JButton btnYazdir = new JButton("Seçili Hocayı Yazdır (Konsol)");
        btnYazdir.setBackground(new Color(13, 110, 253));
        btnYazdir.setForeground(Color.WHITE);
        btnYazdir.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnYazdir.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen listeden bir hoca seçiniz.");
                return;
            }

            // Sıralama/Filtreleme sonrası doğru indeksi bulma
            int modelRow = table.convertRowIndexToModel(viewRow);
            OgretimUyesi secilenHoca = hocaListesi.get(modelRow);

            secilenHoca.ciktiAl(); // Konsola yazdırır
            JOptionPane.showMessageDialog(this, "Bilgiler konsola yazdırıldı!");
        });

        bottomPanel.add(lblToplam);
        bottomPanel.add(btnYazdir);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
}