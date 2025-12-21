package gui;

import model.LisansOgrenci; // Model sınıfını import ettik
import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

// Yönetici panelinde, sistemde kayıtlı öğrencilerin listelendiği ve
// kaydının silinebildiği arayüz sınıfı.
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

        //başlık
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(248, 249, 250));

        JLabel lblTitle = new JLabel(" Kayıtlı Öğrenciler (Silmek için seçiniz)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lblTitle);
        mainPanel.add(header, BorderLayout.NORTH);

        //tablo
        String[] cols = {"No", "Ad", "Soyad", "Bölüm"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(255, 230, 230));
        table.setSelectionForeground(Color.BLACK);

        verileriYukle();

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(10, 20, 10, 20));
        sp.getViewport().setBackground(Color.WHITE);
        mainPanel.add(sp, BorderLayout.CENTER);

        //sil butonu
        JPanel footer = new JPanel();
        footer.setBackground(Color.WHITE);

        JButton btnSil = new JButton("Seçili Öğrenciyi Sil");
        btnSil.setBackground(new Color(220, 53, 69));
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

    //silme işlei yapılıyor
    private void silmeIslemi() {
        int row = table.getSelectedRow();

        if(row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silinecek öğrenciyi seçiniz.");
            return;
        }

        String no = (String) model.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Öğrenci No: " + no + "\nBu kayıt silinsin mi?",
                "Silme Onayı", JOptionPane.YES_NO_OPTION);

        if(confirm == JOptionPane.YES_OPTION) {
            try {
                // direkt DosyaIslemleri çağırmak yerine Model üzerinden çağrılıyor
                // Parametreler boş olabilir çünkü sadece sil metodunu tetikleyecek
                boolean sonuc = new LisansOgrenci("", "", "", "").sil(no);

                if (sonuc) {
                    verileriYukle();
                    JOptionPane.showMessageDialog(this, "Öğrenci kaydı başarıyla silindi.");
                } else {
                    JOptionPane.showMessageDialog(this, "Silme işlemi başarısız oldu.", "Hata", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}