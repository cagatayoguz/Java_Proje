package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;

// Yönetici panelinden sisteme yeni duyuru girişi yapılmasını sağlayan arayüz sınıfı.
public class DuyuruEkleGUI extends JFrame {

    public DuyuruEkleGUI() {
        // Pencere temel özellikleri (Başlık, Boyut, Konum) ayarlandı.
        setTitle("Yeni Duyuru Yayınla");
        setSize(500, 550);
        // Pencere kapatıldığında sadece bu ekranın kapanması sağlandı (Ana uygulama çalışmaya devam eder).
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Formu ekranın tam ortasında tutmak için GridBagLayout tercih edildi.
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(248, 249, 250)); // Hafif gri arka plan
        setContentPane(mainPanel);

        // --- Kart Paneli Tasarımı ---
        // İçeriklerin bir arada ve düzenli durması için beyaz bir kart paneli oluşturuldu.
        JPanel cardPanel = new JPanel();
        // Bileşenlerin yukarıdan aşağıya (Dikey) sıralanması için BoxLayout kullanıldı.
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);

        // Panele ince gri bir çerçeve ve iç boşluk (padding) eklendi.
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(30, 30, 30, 30)));
        cardPanel.setPreferredSize(new Dimension(400, 450)); // Kart boyutu sabitlendi.

        // Form Başlığı
        JLabel lblTitle = new JLabel("Duyuru Oluştur");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Ortaya hizalandı
        cardPanel.add(lblTitle);

        // Görsel boşluk bırakmak için dikey strut eklendi.
        cardPanel.add(Box.createVerticalStrut(20));

        // --- Başlık Giriş Alanı ---
        JLabel l1 = new JLabel("Duyuru Başlığı:");
        l1.setAlignmentX(Component.LEFT_ALIGNMENT); // Sola hizalı
        JTextField txtBaslik = new JTextField();
        // Text alanının yatayda genişlemesi ancak dikeyde sabit kalması sağlandı.
        txtBaslik.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        // --- İçerik Giriş Alanı ---
        JLabel l2 = new JLabel("İçerik:");
        l2.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Çok satırlı veri girişi için TextArea kullanıldı.
        JTextArea txtIcerik = new JTextArea(8, 20);
        txtIcerik.setLineWrap(true); // Satır sonuna gelince alt satıra geçmesi sağlandı.

        // İçerik alanı taşarsa kaydırma çubuğu çıkması için ScrollPane içine alındı.
        JScrollPane scroll = new JScrollPane(txtIcerik);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Bileşenler sırasıyla panele eklendi ve aralarına boşluklar koyuldu.
        cardPanel.add(l1);
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(txtBaslik);
        cardPanel.add(Box.createVerticalStrut(15));

        cardPanel.add(l2);
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(scroll);
        cardPanel.add(Box.createVerticalStrut(20));

        // --- Yayınla Butonu ---
        JButton btnYayinla = new JButton("Yayınla");
        btnYayinla.setBackground(new Color(155, 89, 182)); // Dikkat çekici mor renk seçildi
        btnYayinla.setForeground(Color.WHITE);
        btnYayinla.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnYayinla.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnYayinla.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Buton Aksiyonu: Verilerin dosyaya kaydedilmesi
        btnYayinla.addActionListener(e -> {
            try {
                // Sistemden güncel tarih bilgisi alındı.
                String tarih = LocalDate.now().toString();

                // Servis katmanındaki metot çağrılarak duyuru dosyaya eklendi.
                DosyaIslemleri.duyuruEkle(tarih, txtBaslik.getText(), txtIcerik.getText());

                // Kullanıcıya geri bildirim verildi ve pencere kapatıldı.
                JOptionPane.showMessageDialog(this, "Duyuru yayınlandı.");
                this.dispose();
            } catch (Exception ex) {
                ex.printStackTrace(); // Hata durumunda konsola log düşüldü.
            }
        });

        cardPanel.add(btnYayinla);
        mainPanel.add(cardPanel); // Kart paneli ana panele eklendi.
    }
}