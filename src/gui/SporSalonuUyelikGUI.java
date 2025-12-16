package gui;

import javax.swing.*;
import java.awt.*;

public class SporSalonuUyelikGUI extends JFrame {

    private JTextField txtAdSoyad;
    private JTextField txtOgrenciNo;
    private JComboBox<String> cmbUyelikTipi;
    private JLabel lblUcret;

    public SporSalonuUyelikGUI() {
        setTitle("Spor Salonu - Aylık Üyelik");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın ortasında aç

        // Ana Panel (Kenar boşlukları için)
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 20)); // 5 Satır, 2 Sütun, Boşluklar
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // 1. Satır: Ad Soyad
        mainPanel.add(boldLabel("Ad Soyad:"));
        txtAdSoyad = new JTextField();
        mainPanel.add(txtAdSoyad);

        // 2. Satır: Öğrenci No
        mainPanel.add(boldLabel("Öğrenci No:"));
        txtOgrenciNo = new JTextField();
        mainPanel.add(txtOgrenciNo);

        // 3. Satır: Üyelik Tipi (Combobox)
        mainPanel.add(boldLabel("Üyelik Tipi:"));
        String[] tipler = {"Aylık Üyelik", "3 Aylık Üyelik", "Yıllık Üyelik"};
        cmbUyelikTipi = new JComboBox<>(tipler);
        cmbUyelikTipi.setBackground(Color.WHITE); // Arka planı beyaz yap (üsttekilere benzesin)

        // Seçim değişince fiyatı güncelle
        cmbUyelikTipi.addActionListener(e -> fiyatGuncelle());
        mainPanel.add(cmbUyelikTipi);

        // 4. Satır: Ücret
        mainPanel.add(boldLabel("Ücret:"));
        lblUcret = boldLabel("500 TL"); // Başlangıç fiyatı
        mainPanel.add(lblUcret);

        // 5. Satır: Boşluk ve Buton
        mainPanel.add(new JLabel("")); // Sol taraf boş kalsın
        JButton btnUyeOl = new JButton("Üye Ol");
        btnUyeOl.setFont(new Font("Arial", Font.BOLD, 14));
        btnUyeOl.addActionListener(e -> kayitOlAction());
        mainPanel.add(btnUyeOl);

        add(mainPanel);
    }

    // Kod tekrarını önlemek için yardımcı metot
    private JLabel boldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(50, 50, 50)); // Koyu gri yazı rengi
        return label;
    }

    // Fiyatı seçime göre değiştiren metot
    private void fiyatGuncelle() {
        String secim = (String) cmbUyelikTipi.getSelectedItem();
        if ("Aylık Üyelik".equals(secim)) {
            lblUcret.setText("500 TL");
        } else if ("3 Aylık Üyelik".equals(secim)) {
            lblUcret.setText("1350 TL"); // İndirimli :)
        } else if ("Yıllık Üyelik".equals(secim)) {
            lblUcret.setText("5000 TL");
        }
    }

    // Kayıt Butonu İşlemi
    private void kayitOlAction() {
        if (txtAdSoyad.getText().trim().isEmpty() || txtOgrenciNo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurunuz!", "Hata", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String mesaj = "Sayın " + txtAdSoyad.getText() + ",\n" +
                cmbUyelikTipi.getSelectedItem() + " kaydınız başarıyla oluşturulmuştur.\n" +
                "Ödenecek Tutar: " + lblUcret.getText();

        JOptionPane.showMessageDialog(this, mesaj, "Kayıt Başarılı", JOptionPane.INFORMATION_MESSAGE);
        this.dispose(); // Pencereyi kapat
    }
}