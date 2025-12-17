package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;

public class YemekhaneGUI extends JFrame {

    public YemekhaneGUI() {
        setTitle("Yemekhane Bilgi Sistemi");
        setSize(850, 500); // Ekran genişliği
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Bileşenler arası boşluk

        // --- ORTA PANEL (Yemekhane Bilgileri) ---
        JPanel ortaPanel = new JPanel(new GridLayout(1, 2, 20, 0)); // 1 Satır, 2 Sütun (Yan Yana)
        ortaPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Kenar boşlukları

        // 1. Merkez Yemekhane Paneli
        JPanel merkezPanel = yemekhanePanelOlustur("Merkez Yemekhane", "Kapasite: 800 Kişi", "veriler/resimler/merkez.jpg");
        ortaPanel.add(merkezPanel);

        // 2. Dökümhane Yemekhane Paneli
        JPanel dokumhanePanel = yemekhanePanelOlustur("Dökümhane Yemekhane", "Kapasite: 600 Kişi", "veriler/resimler/dokumhane.jpg");
        ortaPanel.add(dokumhanePanel);

        add(ortaPanel, BorderLayout.CENTER);

        // --- ALT PANEL (Butonlar) ---
        JPanel altPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));

        // Buton 1: Günün Menüsü (Veritabanından Çeker)
        JButton btnGununMenusu = new JButton("Günün Menüsü");
        btnGununMenusu.setFont(new Font("Arial", Font.BOLD, 14));
        btnGununMenusu.setPreferredSize(new Dimension(150, 40));

        btnGununMenusu.addActionListener(e -> {
            // Veritabanı sınıfından bugünün menüsünü iste
            String menu = service.YemekVeriTabani.gununMenusuGetir();

            // Metni göstereceğimiz şık bir alan oluştur
            JTextArea textArea = new JTextArea(menu);
            textArea.setFont(new Font("Monospaced", Font.BOLD, 14)); // Hizalı görünmesi için Monospaced font
            textArea.setEditable(false); // Kullanıcı değiştiremesin
            textArea.setBackground(new Color(240, 240, 240)); // Gri arka plan
            textArea.setMargin(new Insets(10, 10, 10, 10)); // İç boşluk

            // Ekrana bas
            JOptionPane.showMessageDialog(this,
                    textArea,
                    "Günün Yemek Menüsü",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Buton 2: Yemek Listesi (Resmi Açar)
        JButton btnYemekListesi = new JButton("Yemek Listesi");
        btnYemekListesi.setFont(new Font("Arial", Font.BOLD, 14));
        btnYemekListesi.setPreferredSize(new Dimension(150, 40));

        btnYemekListesi.addActionListener(e -> resimPenceresiAc("Yemek Listesi", "C:\\Users\\cagat\\Downloads\\yemek listesi.png"));

        altPanel.add(btnGununMenusu);
        altPanel.add(btnYemekListesi);

        add(altPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null); // Ekranın ortasında açılması için
    }

    // Yemekhane kutularını oluşturmak için yardımcı metot
    private JPanel yemekhanePanelOlustur(String baslik, String kapasite, String resimYolu) {
        JPanel panel = new JPanel(new BorderLayout());

        // Çerçeve ve Başlık
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), baslik);
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitleFont(new Font("Arial", Font.BOLD, 18));
        panel.setBorder(border);

        // Kapasite Yazısı
        JLabel lblKapasite = new JLabel(kapasite, SwingConstants.CENTER);
        lblKapasite.setFont(new Font("Arial", Font.BOLD, 14));
        lblKapasite.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(lblKapasite, BorderLayout.NORTH);

        // Yemekhane Resmi
        JLabel lblResim = new JLabel();
        lblResim.setHorizontalAlignment(SwingConstants.CENTER);

        File imgFile = new File(resimYolu);
        if (imgFile.exists()) {
            // Resmi kutuya sığacak şekilde ölçekle (300x200)
            ImageIcon icon = new ImageIcon(new ImageIcon(resimYolu).getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH));
            lblResim.setIcon(icon);
        } else {
            lblResim.setText("[Resim Yok]");
            lblResim.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }

        panel.add(lblResim, BorderLayout.CENTER);

        return panel;
    }

    // Resim açan yardımcı pencere (Yemek Listesi için)
    private void resimPenceresiAc(String baslik, String dosyaYolu) {
        JDialog dialog = new JDialog(this, baslik, true);
        dialog.setSize(600, 800);
        dialog.setLayout(new BorderLayout());

        JLabel lblResim = new JLabel();
        lblResim.setHorizontalAlignment(SwingConstants.CENTER);

        File dosya = new File(dosyaYolu);
        if (dosya.exists()) {
            ImageIcon icon = new ImageIcon(dosyaYolu);
            lblResim.setIcon(icon);
        } else {
            lblResim.setText("<html><center><h2>Görsel Bulunamadı</h2>" + dosyaYolu + "</center></html>");
        }

        dialog.add(new JScrollPane(lblResim));
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}