package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NotHesaplamaGUI extends JFrame {

    public NotHesaplamaGUI() {
        setTitle("Not Hesaplama Aracı");
        setSize(450, 400); // Pencere boyutu küçüldü
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menülerle uğraşmak yerine sekmeli yapı (TabbedPane) kullandık
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Vize/Final Hesapla", createVizeFinalPanel());
        tabs.addTab("GNO (Ortalama) Hesapla", createGnoPanel());

        add(tabs);
    }

    // --- 1. SEKME: VİZE FİNAL ---
    // --- 1. SEKME: VİZE FİNAL (GÜNCELLENDİ: KÜÇÜK KUTULAR + HARF NOTU) ---
    // --- 1. SEKME: VİZE FİNAL (GÜNCELLENDİ: KÜÇÜK KUTULAR + HARF NOTU) ---
    // --- 1. SEKME: VİZE FİNAL (BÜYÜTÜLMÜŞ KUTULAR) ---
    private JPanel createVizeFinalPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // --- DERS: KUTU BÜYÜTME ---
        // 1. YÖNTEM: Parantez içini 5'ten 8'e çıkardık (Genişlik arttı)
        JTextField txtVize = new JTextField(8);
        JTextField txtFinal = new JTextField(8);

        // 2. YÖNTEM: Yükseklik ayarı (Daha kalıplı dursun diye)
        // Dimension(Genişlik, Yükseklik) -> Genişliği 0 versek de yukarıdaki 8 geçerli olur, ama Yükseklik 30px oldu.
        txtVize.setPreferredSize(new Dimension(0, 30));
        txtFinal.setPreferredSize(new Dimension(0, 30));

        // İPUCU: Yazı tipini de büyütürsen kutu otomatik büyür
        txtVize.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtFinal.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblSonuc = new JLabel("-");
        lblSonuc.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Sonuç yazısını da büyüttük

        // 1. Satır
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Vize Notu (%40):"), gbc);
        gbc.gridx = 1;
        panel.add(txtVize, gbc);

        // 2. Satır
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Final Notu (%60):"), gbc);
        gbc.gridx = 1;
        panel.add(txtFinal, gbc);

        // 3. Satır
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Durum ve Harf:"), gbc);
        gbc.gridx = 1;
        panel.add(lblSonuc, gbc);

        // 4. Satır: Hesapla Butonu (Onu da biraz büyütelim)
        JButton btnHesapla = new JButton("Hesapla");
        btnHesapla.setBackground(new Color(13, 110, 253));
        btnHesapla.setForeground(Color.WHITE);
        btnHesapla.setPreferredSize(new Dimension(120, 40)); // Buton boyutu

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnHesapla, gbc);

        // --- AKSİYON ---
        btnHesapla.addActionListener(e -> {
            try {
                double v = Double.parseDouble(txtVize.getText());
                double f = Double.parseDouble(txtFinal.getText());
                double ort = (v * 0.4) + (f * 0.6);

                ogrenciSinifiIleKontrolEt((int) ort);

                String durum = (ort >= 50 && f >= 50) ? "GEÇTİ" : "KALDI";
                String harf = harfNotuHesapla(ort);

                lblSonuc.setText(String.format("%.2f (%s - %s)", ort, durum, harf));

                if (durum.equals("GEÇTİ")) {
                    lblSonuc.setForeground(new Color(25, 135, 84));
                } else {
                    lblSonuc.setForeground(Color.RED);
                }
            } catch (Exception ex) {
                if(ex.getMessage() != null && ex.getMessage().contains("empty"))
                    JOptionPane.showMessageDialog(this, "Lütfen sayı giriniz.");
                else
                    JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
            }
        });

        return panel;
    }

    // --- YARDIMCI: HARF NOTU HESAPLAYICI ---
    private String harfNotuHesapla(double not) {
        if (not >= 90) return "AA";
        else if (not >= 80) return "BA";
        else if (not >= 70) return "BB";
        else if (not >= 65) return "CB";
        else if (not >= 60) return "CC";
        else if (not >= 55) return "DC";
        else if (not >= 50) return "DD";
        else if (not >= 40) return "FD";
        else return "FF";
    }

    // --- 2. SEKME: GENEL ORTALAMA (GNO) ---
    private JPanel createGnoPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JTextField txtEskiGno = new JTextField();
        JTextField txtEskiKredi = new JTextField();
        JTextField txtYeniOrt = new JTextField();
        JTextField txtYeniKredi = new JTextField();
        JLabel lblSonuc = new JLabel("Yeni GNO: -");
        lblSonuc.setFont(new Font("Segoe UI", Font.BOLD, 14));

        panel.add(new JLabel("Mevcut GNO:")); panel.add(txtEskiGno);
        panel.add(new JLabel("Mevcut Toplam Kredi:")); panel.add(txtEskiKredi);
        panel.add(new JLabel("Bu Dönem Ortalaması:")); panel.add(txtYeniOrt);
        panel.add(new JLabel("Bu Dönem Kredi:")); panel.add(txtYeniKredi);
        panel.add(new JLabel("SONUÇ:")); panel.add(lblSonuc);

        JButton btnHesapla = new JButton("Hesapla");
        btnHesapla.setBackground(new Color(25, 135, 84));
        btnHesapla.setForeground(Color.WHITE);

        btnHesapla.addActionListener(e -> {
            try {
                double eskiG = Double.parseDouble(txtEskiGno.getText());
                double eskiK = Double.parseDouble(txtEskiKredi.getText());
                double yeniG = Double.parseDouble(txtYeniOrt.getText());
                double yeniK = Double.parseDouble(txtYeniKredi.getText());

                double toplamPuan = (eskiG * eskiK) + (yeniG * yeniK);
                double toplamKredi = eskiK + yeniK;
                double sonucGno = toplamPuan / toplamKredi;

                // --- ÖĞRENCİ SINIFI İLE KONTROL ---
                // GNO 4'lük sistemdedir, bunu kontrol için 100'lük sisteme çevirip yolluyoruz
                ogrenciSinifiIleKontrolEt((int) (sonucGno * 25));

                lblSonuc.setText(String.format("%.2f", sonucGno));
                lblSonuc.setForeground(Color.BLUE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("")); panel.add(btnHesapla);
        return panel;
    }

    // --- YARDIMCI: Abstract Ogrenci Sınıfını Kullanan Metot ---
    private void ogrenciSinifiIleKontrolEt(int notDegeri) throws Exception {
        // Abstract sınıf olduğu için "Anonymous Class" tekniğiyle geçici üretiyoruz.
        model.Ogrenci sanalOgrenci = new model.Ogrenci("Test", "Hesap", "0", "Yok") {
            @Override
            public boolean durumKontrol() {
                return false;
            }
            // Abstract metotları doldurmaya gerek yok, çünkü sadece setter'ı test edeceğiz.
        };

        // Bu metot not 0-100 arasında değilse HATA FIRLATIR (DosyaIslemleri'ndeki validation)
        sanalOgrenci.setNotOrtalamasi(notDegeri);
    }
}