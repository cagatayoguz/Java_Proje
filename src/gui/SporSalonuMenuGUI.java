package gui;

import javax.swing.*;
import java.awt.*;

public class SporSalonuMenuGUI extends JFrame {

    public SporSalonuMenuGUI() {
        setTitle("Spor Salonu");
        setSize(350, 200); // Küçük ve kompakt boyut
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Düzen (Layout): Dikey olarak sıralanmış, ortalanmış
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 20)); // 2 Satır, 1 Sütun, Aralarda boşluk
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Kenarlardan boşluk

        // Buton 1: Çalışma Saatleri
        JButton btnSaatler = new JButton("Çalışma Saatleri");
        btnSaatler.setFont(new Font("Arial", Font.BOLD, 14));
        // Tıklanınca Saatler Tablosunu Aç
        btnSaatler.addActionListener(e -> new SporSalonuSaatlerGUI().setVisible(true));

        // Buton 2: Aylık Üyelik
        JButton btnUyelik = new JButton("Aylık Üyelik");
        btnUyelik.setFont(new Font("Arial", Font.BOLD, 14));
        // Üyelik işlemleri için buraya kod eklenebilir, şimdilik mesaj verelim
        btnUyelik.addActionListener(e -> new SporSalonuUyelikGUI().setVisible(true));
        panel.add(btnSaatler);
        panel.add(btnUyelik);

        add(panel);
        setLocationRelativeTo(null); // Ekranın ortasında açılsın
    }
}