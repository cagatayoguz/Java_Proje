package gui;

import model.Kitap; // Model sınıfını import ettik
import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Yönetici panelinde, kütüphane envanterindeki kitapların listelendiği ve silme işleminin gerçekleştirildiği arayüz sınıfı.
public class KitapSilGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public KitapSilGUI() {
        setTitle("Kitap Sil");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- Header ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(248, 249, 250));

        JLabel lbl = new JLabel(" Envanterdeki Kitaplar");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lbl);
        mainPanel.add(header, BorderLayout.NORTH);

        // --- Tablo ---
        String[] cols = {"Kitap Adı", "Yazar", "ISBN", "Durum"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        listeyiYukle();

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(10, 20, 10, 20));
        sp.getViewport().setBackground(Color.WHITE);
        mainPanel.add(sp, BorderLayout.CENTER);

        // --- Footer ---
        JPanel footer = new JPanel();
        footer.setBackground(Color.WHITE);

        JButton btnSil = new JButton("Seçili Kitabı Sil");
        btnSil.setBackground(new Color(220, 53, 69));
        btnSil.setForeground(Color.WHITE);
        btnSil.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnSil.addActionListener(e -> sil());

        footer.add(btnSil);
        mainPanel.add(footer, BorderLayout.SOUTH);
    }

    private void listeyiYukle() {
        model.setRowCount(0);
        List<String[]> list = DosyaIslemleri.kitaplariOkuDetayli();
        for(String[] k : list) {
            model.addRow(new Object[]{k[0], k[1], k[2], k[3]});
        }
    }

    // --- OOP ENTEGRE EDİLMİŞ SİLME İŞLEMİ ---
    private void sil() {
        int r = table.getSelectedRow();

        if(r == -1) {
            JOptionPane.showMessageDialog(this,"Lütfen silinecek kitabı seçiniz.");
            return;
        }

        String isbn = (String) model.getValueAt(r, 2);

        int c = JOptionPane.showConfirmDialog(this,
                "ISBN: " + isbn + " olan kitap silinecek. Onaylıyor musunuz?",
                "Silme Onayı", JOptionPane.YES_NO_OPTION);

        if(c == JOptionPane.YES_OPTION) {
            try {
                // DEĞİŞİKLİK BURADA:
                // Kitap nesnesi oluşturup onun sil metodunu çağırıyoruz.
                // Constructor parametreleri boş olabilir çünkü isbn'i parametre olarak vereceğiz.
                boolean sonuc = new Kitap("", "", "", "Müsait").sil(isbn);

                if (sonuc) {
                    listeyiYukle();
                    JOptionPane.showMessageDialog(this, "Kitap başarıyla silindi.");
                } else {
                    JOptionPane.showMessageDialog(this, "Silme işlemi sırasında hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                }

            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}