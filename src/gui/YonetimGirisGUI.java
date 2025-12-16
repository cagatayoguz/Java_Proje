package gui;

import javax.swing.*;
import exception.GecersizGirisBilgisiException;
import java.awt.event.ActionEvent;

public class YonetimGirisGUI extends JFrame {

    // Basitlik için sabit kullanıcı adı ve şifre
    private static final String KULLANICI_ADI = "admin";
    private static final String SIFRE = "1234";

    private JTextField txtKullaniciAdi;
    private JPasswordField txtSifre;
    private JButton btnGiris;

    public YonetimGirisGUI() {
        setTitle("Yönetim Girişi");
        setSize(400, 250);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtKullaniciAdi = new JTextField(15);
        txtSifre = new JPasswordField(15);
        btnGiris = new JButton("Giriş Yap");

        // Ekleme bileşenleri (Layout kodları atlanmıştır)
        add(new JLabel("Kullanıcı Adı:"));
        add(txtKullaniciAdi);
        add(new JLabel("Şifre:"));
        add(txtSifre);
        add(btnGiris);

        btnGiris.addActionListener(this::girisYapAction);
    }

    private void girisYapAction(ActionEvent e) {
        String kullaniciAdi = txtKullaniciAdi.getText();
        String sifre = new String(txtSifre.getPassword());

        try {
            if (!kullaniciAdi.equals(KULLANICI_ADI) || !sifre.equals(SIFRE)) {
                // Özel Exception fırlatma (Bölüm 7 gereksinimi)
                throw new GecersizGirisBilgisiException("Kullanici adi veya sifre hatali!");
            }

            // Başarılı giriş: Yönetim Menüsünü aç
            new YonetimMenuGUI().setVisible(true);
            this.dispose();

        } catch (GecersizGirisBilgisiException ex) {
            // try-catch bloğu ve özel exception yakalama (Bölüm 7 gereksinimi)
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ... (Kalan metodlar)
}