package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class KitapEkleGUI extends JFrame {

    public KitapEkleGUI() {
        setTitle("Yeni Kitap Ekle");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1), new EmptyBorder(30, 30, 30, 30)));

        JLabel lblTitle = new JLabel("Kitap Bilgileri");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblTitle);
        cardPanel.add(Box.createVerticalStrut(20));

        JTextField txtAd = createField();
        JTextField txtYazar = createField();
        JTextField txtIsbn = createField();
        JTextField txtYil = createField();

        addLabel(cardPanel, "Kitap Adı:", txtAd);
        addLabel(cardPanel, "Yazar:", txtYazar);
        addLabel(cardPanel, "ISBN:", txtIsbn);
        addLabel(cardPanel, "Basım Yılı:", txtYil);

        JButton btnEkle = new JButton("Kitabı Kaydet");
        btnEkle.setBackground(new Color(13, 110, 253));
        btnEkle.setForeground(Color.WHITE);
        btnEkle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEkle.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEkle.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        btnEkle.addActionListener(e -> {
            try {
                DosyaIslemleri.kitapEkle(txtAd.getText(), txtYazar.getText(), txtIsbn.getText(), "Müsait", txtYil.getText());
                JOptionPane.showMessageDialog(this, "Kitap eklendi.");
                this.dispose();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(btnEkle);
        mainPanel.add(cardPanel);
    }

    private JTextField createField() {
        JTextField t = new JTextField(15);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        t.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return t;
    }
    private void addLabel(JPanel p, String txt, JTextField f) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l); p.add(Box.createVerticalStrut(5)); p.add(f); p.add(Box.createVerticalStrut(10));
    }
}