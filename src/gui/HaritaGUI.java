package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class HaritaGUI extends JFrame {

    public HaritaGUI() {
        setTitle("Kampüs Haritası");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String dosyaYolu = "C:\\Users\\cagat\\Downloads\\gazi kampüs harita.jpg";

        JLabel lblResim = new JLabel();
        lblResim.setHorizontalAlignment(SwingConstants.CENTER);
        lblResim.setVerticalAlignment(SwingConstants.CENTER);

        File dosya = new File(dosyaYolu);
        if (dosya.exists()) {
            ImageIcon icon = new ImageIcon(dosyaYolu);



            lblResim.setIcon(icon);
        } else {
            lblResim.setText("<html><center><h2>Harita Görseli Bulunamadı!</h2><br>" +
                    "Lütfen 'kampus_harita.jpg' dosyasını<br>" +
                    "'veriler/resimler/' klasörüne ekleyiniz.</center></html>");
            lblResim.setForeground(Color.RED);
        }
// kaydırma çubuğu
        JScrollPane scrollPane = new JScrollPane(lblResim);
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(scrollPane);
    }
}