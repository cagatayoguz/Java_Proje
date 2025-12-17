package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;

public class DuyuruEkleGUI extends JFrame {

    public DuyuruEkleGUI() {
        setTitle("Yeni Duyuru Yayınla");
        setSize(500, 550);
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
        cardPanel.setPreferredSize(new Dimension(400, 450));

        JLabel lblTitle = new JLabel("Duyuru Oluştur");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblTitle);
        cardPanel.add(Box.createVerticalStrut(20));

        // Başlık Alanı
        JLabel l1 = new JLabel("Duyuru Başlığı:");
        l1.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField txtBaslik = new JTextField();
        txtBaslik.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        // İçerik Alanı
        JLabel l2 = new JLabel("İçerik:");
        l2.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextArea txtIcerik = new JTextArea(8, 20);
        txtIcerik.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(txtIcerik);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        cardPanel.add(l1); cardPanel.add(Box.createVerticalStrut(5)); cardPanel.add(txtBaslik);
        cardPanel.add(Box.createVerticalStrut(15));
        cardPanel.add(l2); cardPanel.add(Box.createVerticalStrut(5)); cardPanel.add(scroll);
        cardPanel.add(Box.createVerticalStrut(20));

        JButton btnYayinla = new JButton("Yayınla");
        btnYayinla.setBackground(new Color(155, 89, 182)); // Mor
        btnYayinla.setForeground(Color.WHITE);
        btnYayinla.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnYayinla.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnYayinla.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        btnYayinla.addActionListener(e -> {
            try {
                String tarih = LocalDate.now().toString();
                DosyaIslemleri.duyuruEkle(tarih, txtBaslik.getText(), txtIcerik.getText());
                JOptionPane.showMessageDialog(this, "Duyuru yayınlandı.");
                this.dispose();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        cardPanel.add(btnYayinla);
        mainPanel.add(cardPanel);
    }
}