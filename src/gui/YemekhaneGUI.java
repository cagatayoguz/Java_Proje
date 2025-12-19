package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;

public class YemekhaneGUI extends JFrame {

    public YemekhaneGUI() {
        setTitle("Yemekhane Bilgi Sistemi");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        // Başlık
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(30, 40, 10, 40));
        JLabel lblTitle = new JLabel("Yemekhane Durumu");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Yemekhane Kartları (Merkez & Dökümhane)
        JPanel gridPanel = new JPanel(new GridLayout(1, 2, 25, 0));
        gridPanel.setBackground(new Color(248, 249, 250));
        gridPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        gridPanel.add(createYemekhaneCard("Merkez Yemekhane", "Kapasite: 800 | Doluluk: %45", "C:\\Users\\cagat\\Downloads\\Merkez yemekhane.jpg"));
        gridPanel.add(createYemekhaneCard("Dökümhane Yemekhane", "Kapasite: 600 | Doluluk: %70", "C:\\Users\\cagat\\Downloads\\dökümhane yemekhane.jpg"));

        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // Alt Butonlar
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
        footerPanel.setBackground(new Color(248, 249, 250));

        JButton btnMenu = createActionButton("Günün Menüsü", new Color(13, 110, 253));
        btnMenu.addActionListener(e -> {
            String menu = service.YemekVeriTabani.gununMenusuGetir();
            JTextArea textArea = new JTextArea(menu);
            textArea.setFont(new Font("Monospaced", Font.BOLD, 14));
            textArea.setEditable(false);
            textArea.setBackground(new Color(240, 240, 240));
            JOptionPane.showMessageDialog(this, textArea, "Günün Menüsü", JOptionPane.PLAIN_MESSAGE);
        });

        JButton btnList = createActionButton("Aylık Liste", new Color(108, 117, 125));
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

        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(350, 220, Image.SCALE_SMOOTH));
            lblImage.setIcon(icon);
        } else {
            lblImage.setText("Görsel Yok");
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

    private void resimAc(String path) {
        JDialog d = new JDialog(this, "Görsel", true);
        d.setSize(600, 800);
        JLabel l = new JLabel();
        if(new File(path).exists()) l.setIcon(new ImageIcon(path));
        else l.setText("Dosya yok: " + path);
        d.add(new JScrollPane(l));
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }
}