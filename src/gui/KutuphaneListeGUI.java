package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class KutuphaneListeGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter;

    public KutuphaneListeGUI() {
        setTitle("Kütüphane - Kitap Listesi");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- ÜST PANEL ---
        JPanel ustPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ustPanel.add(new JLabel("Kitap Ara: "));
        JTextField txtAra = new JTextField(20);
        ustPanel.add(txtAra);
        add(ustPanel, BorderLayout.NORTH);

        // --- TABLO ---
        // Yeni sütunlar: İade Tarihi eklendi
        String[] kolonlar = {"Kitap Adı", "Yazar", "ISBN", "Durum", "İade Tarihi"};
        model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        verileriYukle();
        add(new JScrollPane(table), BorderLayout.CENTER);

        txtAra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = txtAra.getText();
                if (text.trim().length() == 0) sorter.setRowFilter(null);
                else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        });

        // --- BUTONLAR ---
        JPanel altPanel = new JPanel();

        JButton btnTalep = new JButton("Ödünç Talep Et");
        btnTalep.setBackground(new Color(200, 230, 255)); // Açık Mavi

        JButton btnIade = new JButton("İade Et");
        btnIade.setBackground(new Color(255, 200, 200)); // Açık Kırmızı

        btnTalep.addActionListener(e -> talepEtAction());
        btnIade.addActionListener(e -> iadeEtAction());

        altPanel.add(btnTalep);
        altPanel.add(btnIade);
        add(altPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void verileriYukle() {
        model.setRowCount(0);
        List<String[]> kitaplar = DosyaIslemleri.kitaplariOkuDetayli();
        for (String[] k : kitaplar) {
            // Durum Türkçeleştirme ve Görsellik
            String durumGoster = k[3];
            if(k[3].equals("Bekliyor")) durumGoster = "Onay Bekliyor";
            else if(k[3].equals("Oduncte")) durumGoster = "Ödünçte";
            else durumGoster = "Müsait";

            model.addRow(new Object[]{k[0], k[1], k[2], durumGoster, k[4]});
        }
    }

    private void talepEtAction() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Kitap seçiniz.");
            return;
        }
        int modelRow = table.convertRowIndexToModel(row);

        String durum = (String) model.getValueAt(modelRow, 3);
        String isbn = (String) model.getValueAt(modelRow, 2);

        if (!durum.equals("Müsait")) {
            JOptionPane.showMessageDialog(this, "Bu kitap şu an müsait değil (Ödünçte veya Onay Bekliyor).");
            return;
        }

        String ogrenciAd = JOptionPane.showInputDialog(this, "Adınız Soyadınız:");
        if (ogrenciAd != null && !ogrenciAd.trim().isEmpty()) {
            try {
                DosyaIslemleri.kitapTalepEt(isbn, ogrenciAd);
                JOptionPane.showMessageDialog(this, "Talep oluşturuldu! Yönetici onayı bekleniyor.");
                verileriYukle();
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    private void iadeEtAction() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int modelRow = table.convertRowIndexToModel(row);
        String isbn = (String) model.getValueAt(modelRow, 2);
        String durum = (String) model.getValueAt(modelRow, 3);

        if (durum.equals("Müsait")) {
            JOptionPane.showMessageDialog(this, "Bu kitap zaten kütüphanede.");
            return;
        }

        try {
            DosyaIslemleri.kitapIadeEt(isbn); // İade edince boşa çıkar
            JOptionPane.showMessageDialog(this, "Kitap iade alındı.");
            verileriYukle();
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}