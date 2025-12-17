package gui;

import javax.swing.*;
import java.awt.*;

public class SporSalonuYonetimMenuGUI extends JFrame {

    public SporSalonuYonetimMenuGUI() {
        setTitle("Spor Salonu Yönetimi");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1, 1, 10, 10)); // Tek buton var şimdilik
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JButton btnOnay = new JButton("Üyelik Onay İşlemleri");
        btnOnay.setFont(new Font("Arial", Font.BOLD, 14));
        btnOnay.setBackground(new Color(255, 255, 200));
        // Onay Ekranını Aç
        btnOnay.addActionListener(e -> new SporSalonuOnayGUI().setVisible(true));

        add(btnOnay);
        setLocationRelativeTo(null);
    }
}