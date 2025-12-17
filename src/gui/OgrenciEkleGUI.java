package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class OgrenciEkleGUI extends JFrame {

    public OgrenciEkleGUI() {
        setTitle("Yeni Öğrenci Kaydı");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout()); // Ortalamak için
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        // --- FORM KARTI ---
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(40, 40, 40, 40)
        ));

        // Başlık
        JLabel lblTitle = new JLabel("Öğrenci Ekle");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblTitle);
        cardPanel.add(Box.createVerticalStrut(20));

        // Form Alanları
        JTextField txtAd = createField("Adı");
        JTextField txtSoyad = createField("Soyadı");
        JTextField txtNo = createField("Öğrenci No");
        JTextField txtBolum = createField("Bölümü");
        JTextField txtSinif = createField("Sınıfı");
        JTextField txtOrt = createField("Ortalaması");

        // Panele Ekleme
        addLabeledField(cardPanel, "Adı:", txtAd);
        addLabeledField(cardPanel, "Soyadı:", txtSoyad);
        addLabeledField(cardPanel, "Öğrenci No:", txtNo);
        addLabeledField(cardPanel, "Bölümü:", txtBolum);
        addLabeledField(cardPanel, "Sınıfı:", txtSinif);
        addLabeledField(cardPanel, "Ortalama:", txtOrt);

        // Kaydet Butonu
        JButton btnKaydet = new JButton("Kaydet");
        btnKaydet.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setBackground(new Color(13, 110, 253)); // Mavi
        btnKaydet.setFocusPainted(false);
        btnKaydet.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKaydet.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnKaydet.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnKaydet.addActionListener(e -> {
            try {
                DosyaIslemleri.ogrenciEkle(
                        txtAd.getText(), txtSoyad.getText(), txtBolum.getText(),
                        txtNo.getText(), txtSinif.getText(), txtOrt.getText()
                );
                JOptionPane.showMessageDialog(this, "Öğrenci başarıyla eklendi.");
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
            }
        });

        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(btnKaydet);

        mainPanel.add(cardPanel);
    }

    private JTextField createField(String ph) {
        JTextField tf = new JTextField(15);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200)), new EmptyBorder(5, 8, 5, 8)));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return tf;
    }

    private void addLabeledField(JPanel p, String labelText, JTextField field) {
        JLabel l = new JLabel(labelText);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(5));
        p.add(field);
        p.add(Box.createVerticalStrut(10));
    }
}