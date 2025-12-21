package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;

// Üniversite yemekhanelerinin doluluk oranlarını, görsellerini ve
// güncel menü/listelerini sunan arayüz sınıfı.
public class YemekhaneGUI extends JFrame {

    public YemekhaneGUI() {
        setTitle("Yemekhane Bilgi Sistemi");
        setSize(900, 600);
        // Bu pencere kapatıldığında ana menüye dönülmesi için DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250)); // Kurumsal gri arka plan
        setContentPane(mainPanel);

        // Başlık Alanı
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(30, 40, 10, 40));

        JLabel lblTitle = new JLabel("Yemekhane Durumu");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Yemekhane Kartları
        JPanel gridPanel = new JPanel(new GridLayout(1, 2, 25, 0));
        gridPanel.setBackground(new Color(248, 249, 250));
        gridPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Kart oluşturuldu ve panele eklendi.
        gridPanel.add(createYemekhaneCard("Merkez Yemekhane", "Kapasite: 800 | Doluluk: %45", "C:\\Users\\cagat\\Downloads\\Merkez yemekhane.jpg"));
        gridPanel.add(createYemekhaneCard("Dökümhane Yemekhane", "Kapasite: 600 | Doluluk: %70", "C:\\Users\\cagat\\Downloads\\dökümhane yemekhane.jpg"));

        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // Alt Butonlar
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
        footerPanel.setBackground(new Color(248, 249, 250));

        // Günün Menüsü Butonu
        JButton btnMenu = createActionButton("Günün Menüsü", new Color(13, 110, 253)); // Mavi
        btnMenu.addActionListener(e -> {

            // Servis katmanından (YemekVeriTabani) güncel menü verisi çekildi.
            String menu = service.YemekVeriTabani.gununMenusuGetir();

            // Menü gösterimi için salt okunur bir TextArea kullanıldı.
            JTextArea textArea = new JTextArea(menu);
            textArea.setFont(new Font("Monospaced", Font.BOLD, 14));
            textArea.setEditable(false);
            textArea.setBackground(new Color(240, 240, 240));
            JOptionPane.showMessageDialog(this, textArea, "Günün Menüsü", JOptionPane.PLAIN_MESSAGE);
        });

        // Aylık Liste Butonu
        JButton btnList = createActionButton("Aylık Liste", new Color(108, 117, 125)); // Gri
        btnList.addActionListener(e -> resimAc("C:\\Users\\cagat\\Downloads\\yemek listesi.png"));

        footerPanel.add(btnMenu);
        footerPanel.add(btnList);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createYemekhaneCard(String title, String info, String imagePath) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // Resim Alanı
        JLabel lblImage = new JLabel();
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setBackground(new Color(240, 240, 240));
        lblImage.setOpaque(true);
        lblImage.setPreferredSize(new Dimension(300, 200));

        // Dosya kontrolü ve görüntü ölçeklendirme işlemi.
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(350, 220, Image.SCALE_SMOOTH));
            lblImage.setIcon(icon);
        } else {
            lblImage.setText("Görsel Bulunamadı");
        }
        card.add(lblImage, BorderLayout.CENTER);

        // Bilgi Alanı
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JLabel lblT = new JLabel(title, SwingConstants.CENTER);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel lblI = new JLabel(info, SwingConstants.CENTER);
        lblI.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblI.setForeground(Color.GRAY);

        infoPanel.add(lblT);
        infoPanel.add(lblI);
        card.add(infoPanel, BorderLayout.SOUTH);

        return card;
    }

    // Buton stillerini standartlaştıran yardımcı metot.
    private JButton createActionButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 25, 10, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Aylık listeyi (PNG/JPG) harici bir pencerede (Dialog) açan metot.
    private void resimAc(String path) {
        JDialog d = new JDialog(this, "Aylık Yemek Listesi", true);
        d.setSize(600, 800);

        JLabel l = new JLabel();
        l.setHorizontalAlignment(SwingConstants.CENTER);

        if(new File(path).exists()) {
            l.setIcon(new ImageIcon(path));
        } else {
            l.setText("Liste dosyası bulunamadı: " + path);
        }

        // Liste uzun olabileceği için ScrollPane içine alındı.
        d.add(new JScrollPane(l));
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }
}