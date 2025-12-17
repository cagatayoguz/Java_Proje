package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import exception.GecersizGirisBilgisiException;

public class YonetimGirisGUI extends JFrame {

    // Sabit kullanıcı adı ve şifre
    private static final String KULLANICI_ADI = "admin";
    private static final String SIFRE = "1234";

    private JTextField txtKullaniciAdi;
    private JPasswordField txtSifre;
    private JButton btnGiris;

    public YonetimGirisGUI() {
        setTitle("Yönetim Paneli Girişi");
        setSize(450, 400); // Daha ideal bir boyut
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın tam ortasında aç

        // --- Ana Panel (Arka Plan) ---
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 242, 245)); // Açık gri-mavi modern arka plan
        mainPanel.setLayout(new GridBagLayout()); // Öğeleri ortalamak için
        setContentPane(mainPanel);

        // --- Kart Paneli (Beyaz Kutu) ---
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true), // İnce gri çerçeve
                new EmptyBorder(40, 40, 40, 40) // İç boşluk
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
        btnGiris.setBackground(new Color(60, 100, 180)); // Güzel bir mavi tonu
        btnGiris.setFocusPainted(false);
        btnGiris.setBorder(new EmptyBorder(10, 0, 10, 0));
        btnGiris.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnGiris.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Efekti (Üzerine gelince renk değişimi)
        btnGiris.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnGiris.setBackground(new Color(80, 120, 200));
            }
            public void mouseExited(MouseEvent evt) {
                btnGiris.setBackground(new Color(60, 100, 180));
            }
        });

        // Butona aksiyon ekle
        btnGiris.addActionListener(this::girisYapAction);

        // *** ENTER TUŞU AYARI ***
        // Bu pencere aktifken Enter'a basılırsa butona basılmış sayar.
        this.getRootPane().setDefaultButton(btnGiris);

        // --- Bileşenleri Karta Ekleme ---
        cardPanel.add(lblBaslik);
        cardPanel.add(Box.createVerticalStrut(30)); // Boşluk

        // Hizalama için sol tarafa yaslı paneller
        JPanel pnlUser = new JPanel(new BorderLayout());
        pnlUser.setBackground(Color.WHITE);
        pnlUser.add(lblUser, BorderLayout.NORTH);
        pnlUser.add(txtKullaniciAdi, BorderLayout.CENTER);
        pnlUser.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

        cardPanel.add(pnlUser);
        cardPanel.add(Box.createVerticalStrut(15));

        JPanel pnlPass = new JPanel(new BorderLayout());
        pnlPass.setBackground(Color.WHITE);
        pnlPass.add(lblPass, BorderLayout.NORTH);
        pnlPass.add(txtSifre, BorderLayout.CENTER);
        pnlPass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

        cardPanel.add(pnlPass);
        cardPanel.add(Box.createVerticalStrut(30));
        cardPanel.add(btnGiris);

        // Kartı ana panele ekle
        mainPanel.add(cardPanel);
    }

    // TextField tasarımı için yardımcı metot
    private JTextField bilesenTextOlustur() {
        JTextField txt = new JTextField(15);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10))); // İç boşluk (Padding)
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return txt;
    }

    private void girisYapAction(ActionEvent e) {
        String kullaniciAdi = txtKullaniciAdi.getText();
        String sifre = new String(txtSifre.getPassword());

        try {
            if (!kullaniciAdi.equals(KULLANICI_ADI) || !sifre.equals(SIFRE)) {
                // Hatalı giriş
                throw new GecersizGirisBilgisiException("Kullanıcı adı veya şifre hatalı!");
            }

            // Başarılı giriş
            new YonetimMenuGUI().setVisible(true);
            this.dispose(); // Giriş ekranını kapat

        } catch (GecersizGirisBilgisiException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Giriş Hatası", JOptionPane.ERROR_MESSAGE);
            // Şifre alanını temizle
            txtSifre.setText("");
        }
    }
}