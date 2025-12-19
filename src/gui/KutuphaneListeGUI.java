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

public class KutuphaneListeGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter;

    private final Color BG_COLOR = new Color(248, 249, 250);
    private final Color ACCENT_BLUE = new Color(13, 110, 253);

    public KutuphaneListeGUI() {
        setTitle("Kütüphane Sistemi");
        setSize(1000, 650);
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
        JLabel lblSub = new JLabel("Kitap durumunu sorgula ve işlem yap.");
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
        JLabel lblSearchIcon = new JLabel("🔍 Kitap Ara:  ");
        lblSearchIcon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchPanel.add(lblSearchIcon, BorderLayout.WEST);
        searchPanel.add(txtAra, BorderLayout.CENTER);
        searchPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        headerPanel.add(titlePanel, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 2. TABLO ---
        String[] kolonlar = {"Kitap Adı", "Yazar", "ISBN", "Durum", "Hangi tarihe kadar ödünç alındı"};
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

        JButton btnTalep = createActionButton("Ödünç Talep Et", ACCENT_BLUE);
        JButton btnIade = createActionButton("İade Et", new Color(220, 53, 69));

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

            // Görsel düzeltme: Musait -> Müsait
            if(hamDurum.equalsIgnoreCase("Musait") || hamDurum.equalsIgnoreCase("Müsait")) {
                durumGoster = "Müsait";
            } else if(hamDurum.equals("Bekliyor")) {
                durumGoster = "Onay Bekliyor";
            } else if(hamDurum.equals("Oduncte")) {
                durumGoster = "Ödünçte";
            }

            model.addRow(new Object[]{k[0], k[1], k[2], durumGoster, k[4]});
        }
    }

    private void talepEtAction() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Lütfen bir kitap seçiniz."); return; }
        int modelRow = table.convertRowIndexToModel(row);

        String durum = (String) model.getValueAt(modelRow, 3);
        String isbn = (String) model.getValueAt(modelRow, 2);

        // --- HATA ÇÖZÜMÜ BURADA ---
        // Hem "Müsait" hem de "Musait" yazısını kabul edecek şekilde esnetildi
        if (!durum.equalsIgnoreCase("Müsait") && !durum.equalsIgnoreCase("Musait")) {
            JOptionPane.showMessageDialog(this, "Bu kitap şu an müsait değil (" + durum + ").");
            return;
        }

        String ogrenciAd = JOptionPane.showInputDialog(this, "Öğrenci Adı Soyadı:");
        if (ogrenciAd != null && !ogrenciAd.trim().isEmpty()) {
            try {
                DosyaIslemleri.kitapTalepEt(isbn, ogrenciAd);
                JOptionPane.showMessageDialog(this, "Talep yöneticiye iletildi.");
                verileriYukle();
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    private void iadeEtAction() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Lütfen bir kitap seçiniz."); return; }
        int modelRow = table.convertRowIndexToModel(row);

        String isbn = (String) model.getValueAt(modelRow, 2);
        String durum = (String) model.getValueAt(modelRow, 3);

        if (durum.equalsIgnoreCase("Müsait") || durum.equalsIgnoreCase("Musait")) {
            JOptionPane.showMessageDialog(this, "Bu kitap zaten kütüphanede.");
            return;
        }
        try {
            DosyaIslemleri.kitapIadeEt(isbn);
            JOptionPane.showMessageDialog(this, "Kitap iade alındı.");
            verileriYukle();
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}