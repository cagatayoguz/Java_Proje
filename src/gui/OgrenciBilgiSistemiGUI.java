package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class OgrenciBilgiSistemiGUI extends JFrame {

    // Öğrenci verilerini tutacağımız basit bir veritabanı (Harita)
    // Anahtar: Öğrenci No, Değer: {Ad, Soyad, Bölüm, Sınıf, Ortalama}
    private Map<String, String[]> ogrenciVerileri;

    // Ekrandaki bilgi kutucukları (Label)
    private JLabel lblAd, lblSoyad, lblBolum, lblSinif, lblOrtalama;
    private JTextField txtOgrenciNo;

    public OgrenciBilgiSistemiGUI() {
        setTitle("Öğrenci Bilgi Sorgulama");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Verileri Hazırla
        veriTabaniniDoldur();

        // 2. Üst Panel (Arama Kısmı)
        JPanel ustPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        ustPanel.setBackground(new Color(240, 240, 240));

        ustPanel.add(new JLabel("Öğrenci No Giriniz:"));
        txtOgrenciNo = new JTextField(15);
        txtOgrenciNo.setFont(new Font("Arial", Font.BOLD, 14));
        ustPanel.add(txtOgrenciNo);

        JButton btnSorgula = new JButton("Sorgula");
        btnSorgula.setFont(new Font("Arial", Font.BOLD, 14));
        btnSorgula.addActionListener(e -> sorgulaAction()); // Butona basınca çalışacak
        ustPanel.add(btnSorgula);

        add(ustPanel, BorderLayout.NORTH);

        // 3. Orta Panel (Öğrenci Bilgi Kartı)
        JPanel ortaPanel = new JPanel(new GridBagLayout());
        ortaPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Şık bir çerçeve ekleyelim
        TitledBorder border = BorderFactory.createTitledBorder("Öğrenci Bilgileri");
        border.setTitleFont(new Font("Arial", Font.BOLD, 16));
        ortaPanel.setBorder(border);

        // Bilgi satırlarını oluştur
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Boşluklar
        gbc.anchor = GridBagConstraints.WEST; // Sola yasla
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ad
        gbc.gridx = 0; gbc.gridy = 0;
        ortaPanel.add(etiketOlustur("Ad:"), gbc);
        gbc.gridx = 1;
        lblAd = degerEtiketiOlustur("-");
        ortaPanel.add(lblAd, gbc);

        // Soyad
        gbc.gridx = 0; gbc.gridy = 1;
        ortaPanel.add(etiketOlustur("Soyad:"), gbc);
        gbc.gridx = 1;
        lblSoyad = degerEtiketiOlustur("-");
        ortaPanel.add(lblSoyad, gbc);

        // Bölüm
        gbc.gridx = 0; gbc.gridy = 2;
        ortaPanel.add(etiketOlustur("Bölüm:"), gbc);
        gbc.gridx = 1;
        lblBolum = degerEtiketiOlustur("-");
        ortaPanel.add(lblBolum, gbc);

        // Sınıf
        gbc.gridx = 0; gbc.gridy = 3;
        ortaPanel.add(etiketOlustur("Sınıf:"), gbc);
        gbc.gridx = 1;
        lblSinif = degerEtiketiOlustur("-");
        ortaPanel.add(lblSinif, gbc);

        // Ortalama
        gbc.gridx = 0; gbc.gridy = 4;
        ortaPanel.add(etiketOlustur("Genel Not Ort:"), gbc);
        gbc.gridx = 1;
        lblOrtalama = degerEtiketiOlustur("-");
        ortaPanel.add(lblOrtalama, gbc);

        add(ortaPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    // Yardımcı Metot: Kalın Başlık Etiketi
    private JLabel etiketOlustur(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        lbl.setForeground(Color.DARK_GRAY);
        return lbl;
    }

    // Yardımcı Metot: Değer Etiketi (Mavi renkli)
    private JLabel degerEtiketiOlustur(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        lbl.setForeground(new Color(0, 50, 150)); // Koyu mavi
        return lbl;
    }

    // Butona basılınca çalışan metot
    private void sorgulaAction() {
        String no = txtOgrenciNo.getText().trim();

        if (ogrenciVerileri.containsKey(no)) {
            // Öğrenci Bulundu -> Bilgileri Ekrana Yaz
            String[] bilgiler = ogrenciVerileri.get(no);
            lblAd.setText(bilgiler[0]);
            lblSoyad.setText(bilgiler[1]);
            lblBolum.setText(bilgiler[2]);
            lblSinif.setText(bilgiler[3]);
            lblOrtalama.setText(bilgiler[4]);
        } else {
            // Bulunamadı -> Uyarı ver ve temizle
            JOptionPane.showMessageDialog(this, "Bu numaraya ait öğrenci bulunamadı!", "Sonuç Yok", JOptionPane.WARNING_MESSAGE);
            temizle();
        }
    }

    private void temizle() {
        lblAd.setText("-");
        lblSoyad.setText("-");
        lblBolum.setText("-");
        lblSinif.setText("-");
        lblOrtalama.setText("-");
    }

    // 10 Adet Rastgele Veriyi Haritaya Yükle
    private void veriTabaniniDoldur() {
        // Artık verileri dosyadan okuyoruz!
        ogrenciVerileri = service.DosyaIslemleri.ogrencileriOku();
    }
}