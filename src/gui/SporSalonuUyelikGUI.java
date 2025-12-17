package gui;

import service.DosyaIslemleri;
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
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        mainPanel.add(boldLabel("Ad Soyad:"));
        txtAdSoyad = new JTextField();
        mainPanel.add(txtAdSoyad);

        mainPanel.add(boldLabel("Öğrenci No:"));
        txtOgrenciNo = new JTextField();
        mainPanel.add(txtOgrenciNo);

        mainPanel.add(boldLabel("Üyelik Tipi:"));
        String[] tipler = {"Aylık Üyelik", "3 Aylık Üyelik", "Yıllık Üyelik"};
        cmbUyelikTipi = new JComboBox<>(tipler);
        cmbUyelikTipi.setBackground(Color.WHITE);
        cmbUyelikTipi.addActionListener(e -> fiyatGuncelle());
        mainPanel.add(cmbUyelikTipi);

        mainPanel.add(boldLabel("Ücret:"));
        lblUcret = boldLabel("500 TL");
        mainPanel.add(lblUcret);

        mainPanel.add(new JLabel(""));
        JButton btnUyeOl = new JButton("Üye Ol");
        btnUyeOl.setFont(new Font("Arial", Font.BOLD, 14));
        btnUyeOl.addActionListener(e -> kayitOlAction());
        mainPanel.add(btnUyeOl);

        add(mainPanel);
    }

    private JLabel boldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    private void fiyatGuncelle() {
        String secim = (String) cmbUyelikTipi.getSelectedItem();
        if ("Aylık Üyelik".equals(secim)) lblUcret.setText("500 TL");
        else if ("3 Aylık Üyelik".equals(secim)) lblUcret.setText("1350 TL");
        else if ("Yıllık Üyelik".equals(secim)) lblUcret.setText("5000 TL");
    }

    private void kayitOlAction() {
        if (txtAdSoyad.getText().trim().isEmpty() || txtOgrenciNo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurunuz!", "Hata", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // YENİ KISIM: Dosyaya Kaydet
            DosyaIslemleri.sporUyelikTalepEt(
                    txtAdSoyad.getText(),
                    txtOgrenciNo.getText(),
                    (String) cmbUyelikTipi.getSelectedItem(),
                    lblUcret.getText()
            );

            String mesaj = "Sayın " + txtAdSoyad.getText() + ",\n" +
                    "Kayıt talebiniz alınmıştır. Yönetici onayından sonra üyeliğiniz aktifleşecektir.";
            JOptionPane.showMessageDialog(this, mesaj, "Talep Oluşturuldu", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
    }
}