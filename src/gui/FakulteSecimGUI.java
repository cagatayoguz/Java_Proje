package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FakulteSecimGUI extends JFrame {

    public FakulteSecimGUI() {
        setTitle("Ders Programı - Fakülte Seçimi");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        // Başlık
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(30, 40, 10, 40));
        JLabel lblTitle = new JLabel("Fakülte Seçimi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Grid (Kartlar)
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        gridPanel.setBackground(new Color(248, 249, 250));
        gridPanel.setBorder(new EmptyBorder(20, 40, 40, 40));

        // --- GÜNCELLENEN LİSTE ---

        // 1. Teknoloji Fakültesi
        gridPanel.add(createCard("Teknoloji Fakültesi", "Yazılım, Mekatronik...", "💻",
                e -> new BolumSecimGUI("Teknoloji Fakültesi").setVisible(true)));

        // 2. Mühendislik Fakültesi
        gridPanel.add(createCard("Mühendislik Fakültesi", "Bilgisayar, İnşaat...", "🏗️",
                e -> new BolumSecimGUI("Mühendislik Fakültesi").setVisible(true)));

        // 3. Tıp Fakültesi
        gridPanel.add(createCard("Tıp Fakültesi", "Temel Tıp, Cerrahi...", "⚕️",
                e -> new BolumSecimGUI("Tıp Fakültesi").setVisible(true)));

        // 4. Eğitim Fakültesi
        gridPanel.add(createCard("Eğitim Fakültesi", "Öğretmenlik Bölümleri", "🎓",
                e -> new BolumSecimGUI("Eğitim Fakültesi").setVisible(true)));

        mainPanel.add(gridPanel, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, String desc, String icon, java.awt.event.ActionListener action) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        lblIcon.setBorder(new EmptyBorder(0, 0, 0, 15));
        card.add(lblIcon, BorderLayout.WEST);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(Color.WHITE);
        JLabel lblT = new JLabel(title);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JLabel lblD = new JLabel(desc);
        lblD.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblD.setForeground(Color.GRAY);
        textPanel.add(lblT);
        textPanel.add(lblD);
        card.add(textPanel, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { action.actionPerformed(null); }
            public void mouseEntered(MouseEvent e) {
                card.setBorder(new LineBorder(new Color(13, 110, 253), 1));
            }
            public void mouseExited(MouseEvent e) {
                card.setBorder(new LineBorder(new Color(230, 230, 230), 1));
            }
        });
        return card;
    }
}