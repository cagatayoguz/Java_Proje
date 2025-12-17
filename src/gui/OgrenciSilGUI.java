package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class OgrenciSilGUI extends JFrame {
    private DefaultTableModel model;
    private JTable table;

    public OgrenciSilGUI() {
        setTitle("Öğrenci Sil");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // Başlık
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(248, 249, 250));
        JLabel lblTitle = new JLabel(" Kayıtlı Öğrenciler (Silmek için seçiniz)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lblTitle);
        mainPanel.add(header, BorderLayout.NORTH);

        // Tablo
        String[] cols = {"No", "Ad", "Soyad", "Bölüm"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(255, 230, 230)); // Kırmızımsı seçim
        table.setSelectionForeground(Color.BLACK);

        // Verileri Yükle
        verileriYukle();

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(10, 20, 10, 20));
        sp.getViewport().setBackground(Color.WHITE);
        mainPanel.add(sp, BorderLayout.CENTER);

        // Sil Butonu
        JPanel footer = new JPanel();
        footer.setBackground(Color.WHITE);
        JButton btnSil = new JButton("Seçili Öğrenciyi Sil");
        btnSil.setBackground(new Color(220, 53, 69)); // Kırmızı
        btnSil.setForeground(Color.WHITE);
        btnSil.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSil.addActionListener(e -> silmeIslemi());
        footer.add(btnSil);
        mainPanel.add(footer, BorderLayout.SOUTH);
    }

    private void verileriYukle() {
        model.setRowCount(0);
        Map<String, String[]> map = DosyaIslemleri.ogrencileriOku();
        for (String no : map.keySet()) {
            String[] val = map.get(no);
            model.addRow(new Object[]{no, val[0], val[1], val[2]});
        }
    }

    private void silmeIslemi() {
        int row = table.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(this, "Seçim yapmadınız.");
            return;
        }
        String no = (String) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Bu öğrenci silinsin mi?", "Onay", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            try {
                DosyaIslemleri.ogrenciSil(no);
                verileriYukle();
                JOptionPane.showMessageDialog(this, "Silindi.");
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }
}