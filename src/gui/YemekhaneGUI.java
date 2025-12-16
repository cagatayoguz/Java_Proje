package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;

public class YemekhaneGUI extends JFrame {

    public YemekhaneGUI() {
        setTitle("Yemekhane Bilgi Sistemi");
        setSize(850, 500); // Ekran görüntüsüne uygun genişlik
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

        JButton btnGununMenusu = new JButton("Günün Menüsü");
        btnGununMenusu.setFont(new Font("Arial", Font.BOLD, 14));
        btnGununMenusu.setPreferredSize(new Dimension(150, 40));
        // Butona basınca menü resmini aç
        btnGununMenusu.addActionListener(e -> resimPenceresiAc("Günün Menüsü", "veriler/resimler/menu_gunluk.jpg"));

        JButton btnYemekListesi = new JButton("Yemek Listesi");
        btnYemekListesi.setFont(new Font("Arial", Font.BOLD, 14));
        btnYemekListesi.setPreferredSize(new Dimension(150, 40));
        // Butona basınca liste resmini aç
        btnYemekListesi.addActionListener(e -> resimPenceresiAc("Yemek Listesi", "veriler/resimler/menu_aylik.jpg"));

        altPanel.add(btnGununMenusu);
        altPanel.add(btnYemekListesi);

        add(altPanel, BorderLayout.SOUTH);
    }

    // Yemekhane kutularını oluşturmak için yardımcı metot (Kod tekrarını önler)
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

        // Yemekhane Resmi (Placeholder)
        JLabel lblResim = new JLabel();
        lblResim.setHorizontalAlignment(SwingConstants.CENTER);

        // Resmi yüklemeyi dene, yoksa uyarı yazısı göster
        File imgFile = new File(resimYolu);
        if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(new ImageIcon(resimYolu).getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH));
            lblResim.setIcon(icon);
        } else {
            lblResim.setText("[Resim Dosyası Bulunamadı]");
            lblResim.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }

        panel.add(lblResim, BorderLayout.CENTER);

        return panel;
    }

    // Menü butonuna basılınca açılacak resim penceresi
    private void resimPenceresiAc(String baslik, String dosyaYolu) {
        JDialog dialog = new JDialog(this, baslik, true); // true = Modal (arkadaki pencereye tıklanmaz)
        dialog.setSize(600, 800);
        dialog.setLayout(new BorderLayout());

        JLabel lblResim = new JLabel();
        lblResim.setHorizontalAlignment(SwingConstants.CENTER);

        File dosya = new File(dosyaYolu);
        if (dosya.exists()) {
            ImageIcon icon = new ImageIcon(dosyaYolu);
            // Resmi pencereye sığdırmak istersen scale işlemi yapabilirsin, şimdilik orjinal boyut:
            lblResim.setIcon(icon);
        } else {
            lblResim.setText("<html><center><h2>" + baslik + " Görseli Yüklenemedi</h2><br>Dosya yolu: " + dosyaYolu + " bulunamadı.</center></html>");
        }

        dialog.add(new JScrollPane(lblResim)); // Resim büyükse kaydırma çubuğu çıksın
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}