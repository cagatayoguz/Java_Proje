package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import exception.GecersizGirisBilgisiException;

public class OgrenciEkleGUI extends JFrame {

    public OgrenciEkleGUI() {
        setTitle("Yeni Öğrenci Kaydı");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        // --- FORM KARTI ---
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(40, 40, 40, 40)
        ));

        // Başlık
        JLabel lblTitle = new JLabel("Öğrenci Ekle");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblTitle);
        cardPanel.add(Box.createVerticalStrut(20));

        // Form Alanları
        JTextField txtAd = createField();
        JTextField txtSoyad = createField();
        JTextField txtNo = createField();
        JTextField txtBolum = createField();
        JTextField txtSinif = createField();
        JTextField txtOrt = createField();

        // Panele Ekleme
        addLabeledField(cardPanel, "Adı:", txtAd);
        addLabeledField(cardPanel, "Soyadı:", txtSoyad);
        addLabeledField(cardPanel, "Öğrenci No:", txtNo);
        addLabeledField(cardPanel, "Bölümü:", txtBolum);
        addLabeledField(cardPanel, "Sınıfı:", txtSinif);
        addLabeledField(cardPanel, "Ortalama (Örn: 3.50):", txtOrt);

        // Kaydet Butonu
        JButton btnKaydet = new JButton("Kaydet");
        btnKaydet.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setBackground(new Color(13, 110, 253)); // Mavi
        btnKaydet.setFocusPainted(false);
        btnKaydet.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKaydet.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnKaydet.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- BUTON TIKLAMA VE AYRI AYRI KONTROLLER ---
        // --- GÜNCELLENMİŞ BUTON KODU (OOP KULLANAN HALİ) ---
                btnKaydet.addActionListener(e -> {
                    try {
                        // Önce basit boşluk kontrollerini yapalım
                        if (txtAd.getText().trim().isEmpty() || txtNo.getText().trim().isEmpty()) {
                            throw new GecersizGirisBilgisiException("Ad ve Numara zorunludur.");
                        }

                        // --- İŞTE BURADA SENİN SINIFINI KULLANIYORUZ ---

                        // 1. Nesne Oluştur (Polimorfizm)
                        // LisansOgrenci oluşturuyoruz ama Ogrenci referansında tutabiliriz
                        model.Ogrenci yeniOgrenci = new model.LisansOgrenci(
                                txtAd.getText().trim(),
                                txtSoyad.getText().trim(),
                                txtNo.getText().trim(),
                                txtBolum.getText().trim()
                        );

                        // 2. Setter Metotlarını Kullan (Senin yazdığın kurallar çalışsın)
                        // Sınıf bilgisini setleyelim (Senin modelde sınıf yoktu ama GUI'de var, o yüzden es geçmiyoruz)
                        // yeniOgrenci.setSinif(txtSinif.getText()); // Eğer modele eklersen açarsın

                        // Not Ortalamasını ata (Burada senin yazdığın 0-100 kontrolü devreye girer!)
                        try {
                            int notOrt = Integer.parseInt(txtOrt.getText().trim());
                            yeniOgrenci.setNotOrtalamasi(notOrt); // Hata varsa GecersizGirisBilgisiException fırlatır
                        } catch (NumberFormatException nfe) {
                            throw new GecersizGirisBilgisiException("Ortalama sayısal bir değer olmalıdır.");
                        }

                        // --- MODEL DOĞRULAMASI BİTTİ, ŞİMDİ KAYDET ---

                        DosyaIslemleri.ogrenciEkle(
                                yeniOgrenci.getAd(),      // Nesneden alıyoruz
                                yeniOgrenci.getSoyad(),
                                yeniOgrenci.getBolum(),
                                yeniOgrenci.getOgrenciNo(),
                                txtSinif.getText(),       // Modelde olmadığı için direkt textfield'dan
                                String.valueOf(txtOrt.getText())
                        );

                        // Override ettiğin metodu konsolda hocaya göstermek için:
                        yeniOgrenci.bilgiSistemiErisim();
                        System.out.println(yeniOgrenci.detayliRaporOlustur());

                        JOptionPane.showMessageDialog(this, "Öğrenci OOP kurallarına uygun şekilde eklendi.");
                        this.dispose();

                    } catch (GecersizGirisBilgisiException ex) {
                        // Senin model sınıfında fırlattığın hatalar burada yakalanır
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Geçersiz İşlem", JOptionPane.WARNING_MESSAGE);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
                    }
                });;

        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(btnKaydet);

        mainPanel.add(cardPanel);
    }

    private JTextField createField() {
        JTextField tf = new JTextField(15);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200)), new EmptyBorder(5, 8, 5, 8)));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return tf;
    }

    private void addLabeledField(JPanel p, String labelText, JTextField field) {
        JLabel l = new JLabel(labelText);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(5));
        p.add(field);
        p.add(Box.createVerticalStrut(10));
    }
}