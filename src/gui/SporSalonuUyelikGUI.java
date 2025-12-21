package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import java.awt.*;

// Kullanıcıların spor salonu üyelik başvurularını gerçekleştirdiği form arayüzü.
public class SporSalonuUyelikGUI extends JFrame {

    // Sınıf genelinde erişilecek bileşenler tanımlandı.
    private JTextField txtAdSoyad;
    private JTextField txtOgrenciNo;
    private JComboBox<String> cmbUyelikTipi;
    private JLabel lblUcret;

    public SporSalonuUyelikGUI() {
        //başlık boyut konumlandırma
        setTitle("Spor Salonu - Aylık Üyelik");
        setSize(400, 350);
        // Pencere kapatıldığında ana menüye dönülmesi için DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Form elemanlarının düzenli yerleşimi için 5 satır 2 sütunlu GridLayout kurgulandı.
        // Bileşenler arasına boşluk (gap) ve kenarlara iç boşluk (border) eklendi.
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        //form Alanlarının Eklenmes

        // Ad Soyad Alanı
        mainPanel.add(boldLabel("Ad Soyad:"));
        txtAdSoyad = new JTextField();
        mainPanel.add(txtAdSoyad);

        // Öğrenci No Alanı
        mainPanel.add(boldLabel("Öğrenci No:"));
        txtOgrenciNo = new JTextField();
        mainPanel.add(txtOgrenciNo);

        // Üyelik Tipi Seçimi (ComboBox)
        mainPanel.add(boldLabel("Üyelik Tipi:"));
        String[] tipler = {"Aylık Üyelik", "3 Aylık Üyelik", "Yıllık Üyelik"};
        cmbUyelikTipi = new JComboBox<>(tipler);
        cmbUyelikTipi.setBackground(Color.WHITE);

        // Seçim değiştiğinde fiyatın güncellenmesi için ActionListener eklendi.
        cmbUyelikTipi.addActionListener(e -> fiyatGuncelle());
        mainPanel.add(cmbUyelikTipi);

        // Ücret Bilgisi (Dinamik Label)
        mainPanel.add(boldLabel("Ücret:"));
        lblUcret = boldLabel("500 TL"); // Varsayılan değer
        mainPanel.add(lblUcret);

        // Buton Alanı
        mainPanel.add(new JLabel("")); // Düzen bozulmasın diye boş etiket eklendi.
        JButton btnUyeOl = new JButton("Üye Ol");
        btnUyeOl.setFont(new Font("Arial", Font.BOLD, 14));

        // Kayıt işlemi butona bağlandı
        btnUyeOl.addActionListener(e -> kayitOlAction());
        mainPanel.add(btnUyeOl);

        add(mainPanel);
    }

    // Arayüzde görsel bütünlük sağlamak ve kod tekrarını önlemek amacıyla
    // etiket oluşturma işlemi parametrik bir metoda devredildi.
    private JLabel boldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }


    // Kullanıcının seçtiği üyelik tipine göre ödenecek tutar dinamik olarak güncellendi.
    private void fiyatGuncelle() {
        String secim = (String) cmbUyelikTipi.getSelectedItem();
        if ("Aylık Üyelik".equals(secim)) lblUcret.setText("500 TL");
        else if ("3 Aylık Üyelik".equals(secim)) lblUcret.setText("1350 TL");
        else if ("Yıllık Üyelik".equals(secim)) lblUcret.setText("5000 TL");
    }

    //kayıt İşlemi
    private void kayitOlAction() {
        // boş alan kontrolü sağlandı.
        if (txtAdSoyad.getText().trim().isEmpty() || txtOgrenciNo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurunuz!", "Hata", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            // Servis katmanı çağrılarak üyelik talebi sisteme iletildi.
            // Talep varsayılan olarak "Bekliyor" statüsünde kaydedildi, yönetici onayı beklenecek.
            DosyaIslemleri.sporUyelikEkle(
                    txtAdSoyad.getText(),
                    txtOgrenciNo.getText(),
                    (String) cmbUyelikTipi.getSelectedItem(),
                    lblUcret.getText(),
                    "Bekliyor"
            );

            //kullaanıcı geri Bildirimi
            String mesaj = "Sayın " + txtAdSoyad.getText() + ",\n" +
                    "Kayıt talebiniz alınmıştır. Yönetici onayından sonra üyeliğiniz aktifleşecektir.";
            JOptionPane.showMessageDialog(this, mesaj, "Talep Oluşturuldu", JOptionPane.INFORMATION_MESSAGE);

            this.dispose(); // İşlem başarılıysa pencere kapatıldı.

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
    }
}