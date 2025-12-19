package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TakvimSecimGUI extends JFrame {

    public TakvimSecimGUI() {
        setTitle("Takvim Seçimi");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        mainPanel.setBackground(new Color(248, 249, 250));

        // 1. AKADEMİK TAKVİM BUTONU
        JButton btnAkademik = new JButton("📅 Akademik Takvim");
        styleButton(btnAkademik, new Color(13, 110, 253));
        btnAkademik.addActionListener(e -> dosyaAc("C:\\Users\\cagat\\Downloads\\akademik takvim.pdf"));
        // Not: Akademik takvim dosyan varsa ismini buraya yaz

        // 2. SINAV TAKVİMİ BUTONU (HATA BURADAYDI, DÜZELTİLDİ)
        JButton btnSinav = new JButton("📝 Sınav Takvimi");
        styleButton(btnSinav, new Color(25, 135, 84)); // Yeşil Buton

        // ÖNEMLİ: Bu butona basınca artık FAKÜLTE LİSTESİ açılacak
        btnSinav.addActionListener(e -> new SinavFakulteSecimGUI().setVisible(true));

        mainPanel.add(btnAkademik);
        mainPanel.add(btnSinav);

        add(mainPanel);
    }

    private void styleButton(JButton btn, Color bgColor) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void dosyaAc(String path) {
        File file = new File(path);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "Dosya bulunamadı: " + path);
            return;
        }
        try {
            if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}