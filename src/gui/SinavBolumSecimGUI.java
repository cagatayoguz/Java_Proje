package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SinavBolumSecimGUI extends JFrame {

    public SinavBolumSecimGUI(String fakulteAdi) {
        setTitle(fakulteAdi + " - Sınav Takvimi");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0, 1, 10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Fakülteye göre bölümleri ve dosya isimlerini belirle
        // Dosyalar "veriler/sinav_takvimleri/" klasöründe aranacak
        switch (fakulteAdi) {
            case "Teknoloji Fakültesi":
                butonEkle("Bilgisayar Mühendisliği", "teknoloji_bilgisayar_sinav.pdf");
                butonEkle("Elektrik-Elektronik Müh.", "teknoloji_ee_sinav.pdf");
                butonEkle("Otomotiv Mühendisliği", "teknoloji_otomotiv_sinav.pdf");
                break;

            case "Mühendislik Fakültesi":
                butonEkle("Bilgisayar Mühendisliği", "muhendislik_bilgisayar_sinav.pdf");
                butonEkle("Elektrik-Elektronik Müh.", "muhendislik_ee_sinav.pdf");
                butonEkle("Endüstri Mühendisliği", "muhendislik_endustri_sinav.pdf");
                break;

            case "Fen Fakültesi":
                butonEkle("Matematik", "fen_matematik_sinav.pdf");
                butonEkle("Fizik", "fen_fizik_sinav.pdf");
                butonEkle("Kimya", "fen_kimya_sinav.pdf");
                break;
        }
        setLocationRelativeTo(null);
    }

    private void butonEkle(String bolumAdi, String dosyaAdi) {
        JButton btn = new JButton(bolumAdi);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.addActionListener(e -> dosyayiAc(dosyaAdi));
        add(btn);
    }

    private void dosyayiAc(String dosyaAdi) {
        try {
            // Sınav takvimleri için ayrı bir klasör yolu
            String yol = "veriler/Sınıf/" + dosyaAdi;
            File dosya = new File(yol);

            if (dosya.exists()) {
                Desktop.getDesktop().open(dosya);
            } else {
                JOptionPane.showMessageDialog(this, "Sınav takvimi dosyası bulunamadı!\n" + yol, "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
    }
}