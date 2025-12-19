package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SinavFakulteSecimGUI extends JFrame {

    public SinavFakulteSecimGUI() {
        setTitle("Sınav Takvimi - Fakülte Seçimi");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        // --- 1. Başlık ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(30, 40, 10, 40));

        JLabel lblTitle = new JLabel("Sınav Takvimi İçin Fakülte Seçiniz");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(33, 37, 41));

        headerPanel.add(lblTitle, BorderLayout.WEST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 2. Fakülte Listesi (Grid) ---
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        gridPanel.setBackground(new Color(248, 249, 250));
        gridPanel.setBorder(new EmptyBorder(20, 40, 40, 40));

        // 1. Teknoloji Fakültesi
        gridPanel.add(createCard("Teknoloji Fakültesi", "Yazılım, EEM, Enerji...", "💻",
                e -> new SinavBolumSecimGUI("Teknoloji Fakültesi").setVisible(true)));

        // 2. Mühendislik Fakültesi
        gridPanel.add(createCard("Mühendislik Fakültesi", "Bilgisayar, İnşaat...", "🏗️",
                e -> new SinavBolumSecimGUI("Mühendislik Fakültesi").setVisible(true)));

        // 3. Fen Fakültesi
        gridPanel.add(createCard("Fen Fakültesi", "Matematik, Fizik, Kimya", "⚛️",
                e -> new SinavBolumSecimGUI("Fen Fakültesi").setVisible(true)));

        // 4. Eğitim Fakültesi
        gridPanel.add(createCard("Eğitim Fakültesi", "Öğretmenlik Bölümleri", "🎓",
                e -> new SinavBolumSecimGUI("Eğitim Fakültesi").setVisible(true)));

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
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        lblIcon.setBorder(new EmptyBorder(0, 0, 0, 15));
        card.add(lblIcon, BorderLayout.WEST);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(Color.WHITE);

        JLabel lblT = new JLabel(title);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblT.setForeground(new Color(33, 37, 41));

        JLabel lblD = new JLabel(desc);
        lblD.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblD.setForeground(Color.GRAY);

        textPanel.add(lblT);
        textPanel.add(lblD);
        card.add(textPanel, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { action.actionPerformed(null); }
            public void mouseEntered(MouseEvent e) {
                card.setBorder(new LineBorder(new Color(13, 110, 253), 1));
                lblT.setForeground(new Color(13, 110, 253));
            }
            public void mouseExited(MouseEvent e) {
                card.setBorder(new LineBorder(new Color(230, 230, 230), 1));
                lblT.setForeground(new Color(33, 37, 41));
            }
        });
        return card;
    }
}