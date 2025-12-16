package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BolumSecimGUI extends JFrame {

    public BolumSecimGUI(String fakulteAdi) {
        setTitle(fakulteAdi + " - Bölümler");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Dinamik olarak buton ekleyeceğimiz için sayı belirsiz, dikey sıralama kullanıyoruz
        setLayout(new GridLayout(0, 1, 10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Hangi fakülte geldiyse ona göre bölümleri listele
        switch (fakulteAdi) {
            case "Teknoloji Fakültesi":
                bolumButonuEkle("Bilgisayar Mühendisliği", "teknoloji_bilgisayar.pdf");
                bolumButonuEkle("Elektrik-Elektronik Mühendisliği", "teknoloji_ee.pdf");
                bolumButonuEkle("Otomotiv Mühendisliği", "teknoloji_otomotiv.pdf");
                break;

            case "Mühendislik Fakültesi":
                bolumButonuEkle("Bilgisayar Mühendisliği", "muhendislik_bilgisayar.pdf");
                bolumButonuEkle("Elektrik-Elektronik Mühendisliği", "muhendislik_ee.pdf");
                bolumButonuEkle("Endüstri Mühendisliği", "muhendislik_endustri.pdf");
                break;

            case "Fen Fakültesi":
                bolumButonuEkle("Matematik", "fen_matematik.pdf");
                bolumButonuEkle("Fizik", "fen_fizik.pdf");
                bolumButonuEkle("Kimya", "fen_kimya.pdf");
                break;
        }
    }

    // Kod tekrarını önlemek için yardımcı metot
    private void bolumButonuEkle(String bolumAdi, String dosyaAdi) {
        JButton btn = new JButton(bolumAdi);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));

        // Tıklanınca dosyayı aç
        btn.addActionListener(e -> dosyayiAc(dosyaAdi));

        add(btn);
    }

    // Dosya açma işlemi (Bilgisayardaki varsayılan programla açar)
    private void dosyayiAc(String dosyaAdi) {
        try {
            // Dosyalar "veriler/ders_programlari" klasöründe aranacak
            String yol = "veriler/ders_programlari/" + dosyaAdi;
            File dosya = new File(yol);

            if (dosya.exists()) {
                // Desktop sınıfı ile dosyayı işletim sisteminin varsayılan uygulamasıyla aç (Örn: Adobe Reader)
                Desktop.getDesktop().open(dosya);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Ders programı dosyası bulunamadı!\nAranan yol: " + yol,
                        "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Dosya açılırken hata oluştu: " + ex.getMessage());
        }
    }
}