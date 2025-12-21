package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import exception.GecersizGirisBilgisiException;

// Yönetim paneline erişim sağlamak için kimlik doğrulamasının yapıldığı arayüz sınıfı.
public class YonetimGirisGUI extends JFrame {

    // Simülasyon amaçlı sabit (Hardcoded) yönetici kimlik bilgileri.
    private static final String KULLANICI_ADI = "admin";
    private static final String SIFRE = "1234";

    private JTextField txtKullaniciAdi;
    private JPasswordField txtSifre;
    private JButton btnGiris;

    public YonetimGirisGUI() {
        // Pencere yapılandırması (Başlık, Boyut, Konumlandırma) gerçekleştirildi.
        setTitle("Yönetim Paneli Girişi");
        setSize(450, 400); // Kullanıcı odaklı ideal boyut
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın tam ortasında açılması sağlandı.

        // --- Ana Panel (Arka Plan) ---
        // Modern bir görünüm için açık gri-mavi tonu tercih edildi.
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 242, 245));
        // Giriş kartını tam ortaya sabitlemek için GridBagLayout kullanıldı.
        mainPanel.setLayout(new GridBagLayout());
        setContentPane(mainPanel);

        // --- Kart Paneli (Giriş Formu) ---
        // Form elemanlarını barındıran beyaz zeminli kart yapısı oluşturuldu.
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        // Karta derinlik katmak için ince gri çerçeve ve geniş iç boşluk (Padding) eklendi.
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(40, 40, 40, 40)
        ));

        // --- 1. Başlık ---
        JLabel lblBaslik = new JLabel("Yönetici Girişi");
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblBaslik.setForeground(new Color(50, 60, 80));
        lblBaslik.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 2. Kullanıcı Adı Alanı ---
        JLabel lblUser = new JLabel("Kullanıcı Adı");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUser.setForeground(Color.GRAY);
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtKullaniciAdi = bilesenTextOlustur();

        // --- 3. Şifre Alanı ---
        JLabel lblPass = new JLabel("Şifre");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPass.setForeground(Color.GRAY);
        lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtSifre = new JPasswordField(15);
        txtSifre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSifre.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10)));
        txtSifre.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        // --- 4. Giriş Butonu ---
        btnGiris = new JButton("Giriş Yap");
        btnGiris.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnGiris.setForeground(Color.WHITE);
        btnGiris.setBackground(new Color(60, 100, 180)); // Güven veren mavi tonu
        btnGiris.setFocusPainted(false);
        btnGiris.setBorder(new EmptyBorder(10, 0, 10, 0));
        btnGiris.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnGiris.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Kullanıcı Deneyimi (UX): Hover Efekti
        // Mouse üzerine geldiğinde buton rengi açılarak etkileşim hissi verildi.
        btnGiris.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnGiris.setBackground(new Color(80, 120, 200));
            }
            public void mouseExited(MouseEvent evt) {
                btnGiris.setBackground(new Color(60, 100, 180));
            }
        });

        // Buton aksiyonu tanımlandı.
        btnGiris.addActionListener(this::girisYapAction);

        // *** UX İyileştirmesi: ENTER Tuşu ***
        // Form üzerindeyken Enter tuşuna basıldığında 'Giriş Yap' butonunun tetiklenmesi sağlandı.
        this.getRootPane().setDefaultButton(btnGiris);

        // --- Bileşenlerin Karta Yerleşimi ---
        cardPanel.add(lblBaslik);
        cardPanel.add(Box.createVerticalStrut(30));

        // Kullanıcı Adı Paneli (Hizalama için)
        JPanel pnlUser = new JPanel(new BorderLayout());
        pnlUser.setBackground(Color.WHITE);
        pnlUser.add(lblUser, BorderLayout.NORTH);
        pnlUser.add(txtKullaniciAdi, BorderLayout.CENTER);
        pnlUser.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

        cardPanel.add(pnlUser);
        cardPanel.add(Box.createVerticalStrut(15));

        // Şifre Paneli (Hizalama için)
        JPanel pnlPass = new JPanel(new BorderLayout());
        pnlPass.setBackground(Color.WHITE);
        pnlPass.add(lblPass, BorderLayout.NORTH);
        pnlPass.add(txtSifre, BorderLayout.CENTER);
        pnlPass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

        cardPanel.add(pnlPass);
        cardPanel.add(Box.createVerticalStrut(30));
        cardPanel.add(btnGiris);

        // Hazırlanan kart ana panele eklendi.
        mainPanel.add(cardPanel);
    }

    // --- Yardımcı Metot: Text Alanı Tasarımı ---
    private JTextField bilesenTextOlustur() {
        JTextField txt = new JTextField(15);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10))); // İçerik ile kenarlık arasına boşluk
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return txt;
    }

    // --- Giriş Doğrulama Mantığı ---
    private void girisYapAction(ActionEvent e) {
        String kullaniciAdi = txtKullaniciAdi.getText();
        String sifre = new String(txtSifre.getPassword());

        try {
            // Basit doğrulama kontrolü
            if (!kullaniciAdi.equals(KULLANICI_ADI) || !sifre.equals(SIFRE)) {
                // Hatalı giriş durumunda özel istisna (Custom Exception) fırlatıldı.
                throw new GecersizGirisBilgisiException("Kullanıcı adı veya şifre hatalı!");
            }

            // Başarılı giriş: Yönetim menüsü açıldı ve giriş ekranı kapatıldı.
            new YonetimMenuGUI().setVisible(true);
            this.dispose();

        } catch (GecersizGirisBilgisiException ex) {
            // Hata mesajı kullanıcıya gösterildi ve şifre alanı temizlendi.
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Giriş Hatası", JOptionPane.ERROR_MESSAGE);
            txtSifre.setText("");
        }
    }
}