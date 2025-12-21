package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// Sisteme yüklü olan Akademik Takvim ve Sınav Takvimlerine erişim sağlayan seçim arayüzü.
public class TakvimSecimGUI extends JFrame {

    public TakvimSecimGUI() {
        setTitle("Takvim Seçimi");
        setSize(500, 350);
        // Bu pencere kapatıldığında ana menüye dönülmesi için DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40)); // Kenar boşlukları
        mainPanel.setBackground(new Color(248, 249, 250)); // Açık gri arka plan

        // AKADEMİK TAKVİM BUTONU
        JButton btnAkademik = new JButton("📅 Akademik Takvim");
        styleButton(btnAkademik, new Color(13, 110, 253)); // Mavi Buton

        // Lambda ifadesi ile yerel diskteki PDF dosyasının açılması sağlandı.
        btnAkademik.addActionListener(e -> dosyaAc("C:\\Users\\cagat\\Downloads\\akademik takvim.pdf"));

        // SINAV TAKVİMİ BUTONU
        JButton btnSinav = new JButton("📝 Sınav Takvimi");
        styleButton(btnSinav, new Color(25, 135, 84)); // Yeşil Buton

        btnSinav.addActionListener(e -> new SinavFakulteSecimGUI().setVisible(true));

        mainPanel.add(btnAkademik);
        mainPanel.add(btnSinav);

        add(mainPanel);
    }

    // Buton stillerini standartlaştıran yardımcı metot.
    private void styleButton(JButton btn, Color bgColor) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Dosya Açma Metodu
    // Java Desktop API kullanılarak, dosya uzantısından bağımsız (PDF, JPG vb.)
    // işletim sisteminin varsayılan görüntüleyicisi ile açılması sağlandı.
    private void dosyaAc(String path) {
        File file = new File(path);

        // Dosya varlığı kontrol edildi.
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "Dosya bulunamadı: " + path, "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Masaüstü desteği kontrol edilerek dosya açma komutu gönderildi.
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (IOException ex) {
            ex.printStackTrace(); // Hata durumunda log basıldı.
        }
    }
}