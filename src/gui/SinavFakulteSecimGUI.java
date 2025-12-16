package gui;

import javax.swing.*;
import java.awt.*;

public class SinavFakulteSecimGUI extends JFrame {

    public SinavFakulteSecimGUI() {
        setTitle("Sınav Takvimi - Fakülte Seçimi");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnTeknoloji = new JButton("Teknoloji Fakültesi");
        JButton btnMuhendislik = new JButton("Mühendislik Fakültesi");
        JButton btnFen = new JButton("Fen Fakültesi");

        Font butonFont = new Font("Arial", Font.BOLD, 14);
        btnTeknoloji.setFont(butonFont);
        btnMuhendislik.setFont(butonFont);
        btnFen.setFont(butonFont);

        // Tıklanınca Sınav Bölüm Seçimine Yönlendir
        btnTeknoloji.addActionListener(e -> new SinavBolumSecimGUI("Teknoloji Fakültesi").setVisible(true));
        btnMuhendislik.addActionListener(e -> new SinavBolumSecimGUI("Mühendislik Fakültesi").setVisible(true));
        btnFen.addActionListener(e -> new SinavBolumSecimGUI("Fen Fakültesi").setVisible(true));

        add(btnTeknoloji);
        add(btnMuhendislik);
        add(btnFen);

        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLocationRelativeTo(null);
    }
}