package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class NotHesaplamaGUI extends JFrame {

    private JPanel cards; // Ekranlar arası geçişi sağlayan panel
    private CardLayout cardLayout;

    // Renk Temaları
    private final Color BG_COLOR = new Color(248, 249, 250);
    private final Color BLUE_BTN = new Color(13, 110, 253);
    private final Color GREEN_BTN = new Color(25, 135, 84);
    private final Color GRAY_BTN = new Color(108, 117, 125);

    public NotHesaplamaGUI() {
        setTitle("Akademik Hesaplama Merkezi");
        setSize(900, 700); // Biraz uzattık
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // --- EKRANLARI OLUŞTUR VE EKLE ---
        cards.add(createMainMenu(), "AnaMenu");
        cards.add(createDersGecmePanel(), "DersGecme");
        cards.add(createOrtalamaMenu(), "OrtalamaMenu");
        cards.add(createDonemOrtalamaPanel(), "DonemOrtalama");
        cards.add(createGenelOrtalamaPanel(), "GenelOrtalama"); // BURASI GÜNCELLENDİ

        setContentPane(cards);
    }

    // --- 1. ANA MENÜ ---
    private JPanel createMainMenu() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);

        JPanel container = new JPanel(new GridLayout(2, 1, 20, 20));
        container.setBackground(BG_COLOR);
        container.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel lblTitle = new JLabel("Hangi işlemi yapmak istersiniz?", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        btnPanel.setBackground(BG_COLOR);

        JButton btnDers = createBigButton("📝 Ders Geçme Notu", "Vize/Final hesapla", BLUE_BTN);
        btnDers.addActionListener(e -> cardLayout.show(cards, "DersGecme"));

        JButton btnOrt = createBigButton("📊 Ortalama Hesaplama", "Dönem veya Genel", GREEN_BTN);
        btnOrt.addActionListener(e -> cardLayout.show(cards, "OrtalamaMenu"));

        btnPanel.add(btnDers);
        btnPanel.add(btnOrt);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        panel.add(lblTitle, gbc);
        gbc.gridy = 1;
        panel.add(btnPanel, gbc);

        return panel;
    }

    // --- 2. ORTALAMA SEÇİM MENÜSÜ ---
    private JPanel createOrtalamaMenu() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);

        JLabel lblTitle = new JLabel("Ortalama Türü Seçiniz", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        btnPanel.setBackground(BG_COLOR);

        JButton btnDonem = createBigButton("📅 Dönem Ortalaması", "Sadece bu dönemi hesapla", new Color(255, 193, 7));
        btnDonem.setForeground(Color.BLACK);
        btnDonem.addActionListener(e -> cardLayout.show(cards, "DonemOrtalama"));

        JButton btnGenel = createBigButton("🎓 Genel Ortalama (GNO)", "Eski GNO + Yeni Dersler", new Color(23, 162, 184));
        btnGenel.addActionListener(e -> cardLayout.show(cards, "GenelOrtalama"));

        btnPanel.add(btnDonem);
        btnPanel.add(btnGenel);

        JButton btnBack = new JButton("← Ana Menüye Dön");
        styleButton(btnBack, GRAY_BTN);
        btnBack.addActionListener(e -> cardLayout.show(cards, "AnaMenu"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        panel.add(lblTitle, gbc);
        gbc.gridy = 1;
        panel.add(btnPanel, gbc);
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 0, 0, 0);
        panel.add(btnBack, gbc);

        return panel;
    }

    // --- 3. DERS GEÇME NOTU HESAPLAMA PANELİ ---
    private JPanel createDersGecmePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 20));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.LIGHT_GRAY), "Tek Ders Hesaplama"));

        JTextField txtVize = new JTextField();
        JTextField txtFinal = new JTextField();
        JLabel lblSonuc = new JLabel("-");
        lblSonuc.setFont(new Font("Segoe UI", Font.BOLD, 16));

        form.add(new JLabel("Vize Notu (%40):")); form.add(txtVize);
        form.add(new JLabel("Final Notu (%60):")); form.add(txtFinal);
        form.add(new JLabel("Durum:")); form.add(lblSonuc);

        JButton btnHesapla = new JButton("Hesapla");
        styleButton(btnHesapla, BLUE_BTN);
        btnHesapla.addActionListener(e -> {
            try {
                double v = Double.parseDouble(txtVize.getText());
                double f = Double.parseDouble(txtFinal.getText());
                double ort = (v * 0.4) + (f * 0.6);
                String durum = (ort >= 50 && f >= 50) ? "GEÇTİ" : "KALDI";
                Color renk = (ort >= 50 && f >= 50) ? GREEN_BTN : Color.RED;
                lblSonuc.setText(String.format("%.2f (%s)", ort, durum));
                lblSonuc.setForeground(renk);
            } catch (Exception ex) {
                lblSonuc.setText("Hatalı Giriş!");
            }
        });

        JButton btnBack = new JButton("← Geri");
        styleButton(btnBack, GRAY_BTN);
        btnBack.addActionListener(e -> cardLayout.show(cards, "AnaMenu"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(form, gbc);
        gbc.gridy = 1;
        panel.add(btnHesapla, gbc);
        gbc.gridy = 2;
        panel.add(btnBack, gbc);

        return panel;
    }

    // --- 4. DÖNEM ORTALAMASI PANELİ ---
    private JPanel createDonemOrtalamaPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        topPanel.setBackground(Color.WHITE);
        JTextField txtDersSayisi = new JTextField(5);
        JButton btnOlustur = new JButton("Listele");
        styleButton(btnOlustur, BLUE_BTN);
        topPanel.add(new JLabel("Kaç dersiniz var?"));
        topPanel.add(txtDersSayisi);
        topPanel.add(btnOlustur);

        String[] cols = {"Ders Adı", "Kredi", "Harf Notu"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        JComboBox<String> cmbHarf = new JComboBox<>(new String[]{"AA", "BA", "BB", "CB", "CC", "DC", "DD", "FD", "FF"});
        table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(cmbHarf));

        btnOlustur.addActionListener(e -> {
            try {
                int sayi = Integer.parseInt(txtDersSayisi.getText());
                model.setRowCount(0);
                for (int i = 0; i < sayi; i++) model.addRow(new Object[]{"Ders " + (i + 1), "", "AA"});
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Geçerli sayı giriniz."); }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        bottomPanel.setBackground(Color.WHITE);
        JLabel lblGNO = new JLabel("Dönem Ortalaması: 0.00");
        lblGNO.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblGNO.setForeground(BLUE_BTN);
        JButton btnHesapla = new JButton("HESAPLA");
        styleButton(btnHesapla, GREEN_BTN);
        JButton btnBack = new JButton("← Geri");
        styleButton(btnBack, GRAY_BTN);
        btnBack.addActionListener(e -> cardLayout.show(cards, "OrtalamaMenu"));

        btnHesapla.addActionListener(e -> {
            double topPuan = 0, topKredi = 0;
            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    String sKr = (String) model.getValueAt(i, 1);
                    if (sKr != null && !sKr.isEmpty()) {
                        double kr = Double.parseDouble(sKr);
                        topPuan += kr * harfNotuCevir((String) model.getValueAt(i, 2));
                        topKredi += kr;
                    }
                } catch (Exception ex) {}
            }
            if(topKredi>0) lblGNO.setText(String.format("Dönem Ortalaması: %.2f", topPuan/topKredi));
            else lblGNO.setText("Kredi girilmedi!");
        });

        JPanel btnGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGroup.setBackground(Color.WHITE);
        btnGroup.add(btnBack); btnGroup.add(btnHesapla);
        bottomPanel.add(lblGNO, BorderLayout.WEST);
        bottomPanel.add(btnGroup, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    // --- 5. GENEL ORTALAMA PANELİ (YENİLENMİŞ HALİ) ---
    private JPanel createGenelOrtalamaPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // 1. ÜST KISIM: Mevcut Durum Girişi
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        JTextField txtMevcutGNO = new JTextField(6);
        JTextField txtMevcutKredi = new JTextField(6);
        JTextField txtYeniDersSayisi = new JTextField(6);
        JButton btnListele = new JButton("Dersleri Listele");
        styleButton(btnListele, BLUE_BTN);
        btnListele.setPreferredSize(new Dimension(140, 35));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.WEST;

        // Mevcut Veriler
        g.gridx=0; g.gridy=0; inputPanel.add(new JLabel("Mevcut Genel Ortalama (GNO):"), g);
        g.gridx=1; inputPanel.add(txtMevcutGNO, g);

        g.gridx=0; g.gridy=1; inputPanel.add(new JLabel("Mevcut Toplam Kredi:"), g);
        g.gridx=1; inputPanel.add(txtMevcutKredi, g);

        // Yeni Dönem Verileri
        g.gridx=0; g.gridy=2; inputPanel.add(new JLabel("Bu Dönem Kaç Ders Alıyorsunuz?"), g);
        g.gridx=1; inputPanel.add(txtYeniDersSayisi, g);

        g.gridx=2; g.gridy=2; inputPanel.add(btnListele, g);

        // 2. ORTA KISIM: TABLO
        String[] cols = {"Ders Adı", "Kredi", "Harf Notu"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        JComboBox<String> cmbHarf = new JComboBox<>(new String[]{"AA", "BA", "BB", "CB", "CC", "DC", "DD", "FD", "FF"});
        table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(cmbHarf));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(new EmptyBorder(0, 20, 0, 20));

        // Listele Butonu İşlemi
        btnListele.addActionListener(e -> {
            try {
                int sayi = Integer.parseInt(txtYeniDersSayisi.getText());
                model.setRowCount(0);
                for(int i=0; i<sayi; i++) model.addRow(new Object[]{"Ders " + (i+1), "", "AA"});
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli bir ders sayısı giriniz.");
            }
        });

        // 3. ALT KISIM: HESAPLA
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        bottomPanel.setBackground(Color.WHITE);

        JLabel lblSonuc = new JLabel("Yeni GNO: 0.00");
        lblSonuc.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblSonuc.setForeground(BLUE_BTN);

        JButton btnHesapla = new JButton("HESAPLA");
        styleButton(btnHesapla, GREEN_BTN);

        JButton btnBack = new JButton("← Geri");
        styleButton(btnBack, GRAY_BTN);
        btnBack.addActionListener(e -> cardLayout.show(cards, "OrtalamaMenu"));

        btnHesapla.addActionListener(e -> {
            try {
                // 1. Mevcut Durumu Al
                double mevcutGNO = Double.parseDouble(txtMevcutGNO.getText().replace(",", "."));
                double mevcutKredi = Double.parseDouble(txtMevcutKredi.getText().replace(",", "."));

                // 2. Yeni Derslerin Ağırlığını Hesapla
                double buDonemPuan = 0;
                double buDonemKredi = 0;

                for(int i=0; i<model.getRowCount(); i++) {
                    String sKredi = (String) model.getValueAt(i, 1);
                    String sHarf = (String) model.getValueAt(i, 2);

                    if(sKredi != null && !sKredi.isEmpty()) {
                        double kr = Double.parseDouble(sKredi);
                        double katsayi = harfNotuCevir(sHarf);

                        buDonemPuan += (kr * katsayi);
                        buDonemKredi += kr;
                    }
                }

                // 3. Genel Ortalamayı Birleştir
                double toplamKredi = mevcutKredi + buDonemKredi;
                double toplamAgirlik = (mevcutGNO * mevcutKredi) + buDonemPuan;

                if(toplamKredi > 0) {
                    double yeniGNO = toplamAgirlik / toplamKredi;
                    lblSonuc.setText(String.format("Yeni GNO: %.2f", yeniGNO));

                    if(yeniGNO >= 3.0) lblSonuc.setForeground(new Color(25, 135, 84));
                    else if(yeniGNO >= 2.0) lblSonuc.setForeground(new Color(230, 126, 34));
                    else lblSonuc.setForeground(new Color(220, 53, 69));
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lütfen mevcut GNO ve Kredi bilgilerini doğru giriniz.");
            }
        });

        JPanel btnGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGroup.setBackground(Color.WHITE);
        btnGroup.add(btnBack);
        btnGroup.add(btnHesapla);

        bottomPanel.add(lblSonuc, BorderLayout.WEST);
        bottomPanel.add(btnGroup, BorderLayout.EAST);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    // --- YARDIMCI METOTLAR ---
    private double harfNotuCevir(String harf) {
        switch (harf) {
            case "AA": return 4.0;
            case "BA": return 3.5;
            case "BB": return 3.0;
            case "CB": return 2.5;
            case "CC": return 2.0;
            case "DC": return 1.5;
            case "DD": return 1.0;
            case "FD": return 0.5;
            default: return 0.0;
        }
    }

    private JButton createBigButton(String title, String sub, Color color) {
        JButton btn = new JButton("<html><center><h2 style='margin:0'>" + title + "</h2><p>" + sub + "</p></center></html>");
        btn.setPreferredSize(new Dimension(220, 120));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 40));
    }
}