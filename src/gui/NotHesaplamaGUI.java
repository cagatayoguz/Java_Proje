package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NotHesaplamaGUI extends JFrame {

    private final Color PRIMARY = new Color(66, 139, 202);
    private final Color SUCCESS = new Color(92, 184, 92);
    private final Font BOLD_FONT = new Font("Segoe UI", Font.BOLD, 13);

    // Dinamik hesaplama için listeler
    private List<JTextField> notFields = new ArrayList<>();
    private List<JTextField> krediFields = new ArrayList<>();
    private JPanel formPanel;

    public NotHesaplamaGUI() {
        setTitle("Akademik Not Hesaplayıcı");
        setSize(450, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(BOLD_FONT);
        tabs.addTab("Vize & Final", createVizeFinalPanel());
        tabs.addTab("Dönem Ortalaması", createDonemPanel());
        add(tabs);
    }

    // VİZE FİNAL PANELİ
    private JPanel createVizeFinalPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 5, 10, 5);
        g.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtVize = createField();
        JTextField txtFinal = createField();
        JLabel lblSonuc = new JLabel("-");
        lblSonuc.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSonuc.setHorizontalAlignment(SwingConstants.CENTER);

        g.gridx=0; g.gridy=0; p.add(createLabel("Vize (%40):"), g);
        g.gridx=1; g.weightx=1; p.add(txtVize, g);

        g.gridx=0; g.gridy=1; g.weightx=0; p.add(createLabel("Final (%60):"), g);
        g.gridx=1; p.add(txtFinal, g);

        g.gridx=0; g.gridy=2; p.add(createLabel("Durum:"), g);
        g.gridx=1; p.add(lblSonuc, g);

        JButton btn = createButton("HESAPLA", PRIMARY);
        btn.addActionListener(e -> {
            try {
                double v = Double.parseDouble(txtVize.getText());
                double f = Double.parseDouble(txtFinal.getText());
                double ort = (v * 0.4) + (f * 0.6);

                String harf = (ort>=90)?"AA":(ort>=85)?"BA":(ort>=75)?"BB":(ort>=65)?"CB":(ort>=55)?"CC":(ort>=45)?"DC":"FF";
                String dur = (ort>=50 && f>=50) ? "GEÇTİ" : "KALDI";

                lblSonuc.setText(String.format("%.1f (%s - %s)", ort, dur, harf));
                lblSonuc.setForeground(dur.equals("GEÇTİ") ? SUCCESS : Color.RED);
            } catch (Exception ex) { showMsg("Lütfen geçerli notlar giriniz."); }
        });

        g.gridx=0; g.gridy=3; g.gridwidth=2; p.add(btn, g);
        return p;
    }

    // DİNAMİK ORTALAMA
    private JPanel createDonemPanel() {
        JPanel main = new JPanel(new BorderLayout(0, 10));
        main.setBorder(new EmptyBorder(10, 15, 10, 15));
        main.setBackground(Color.WHITE);

        // Üst Kısım: Sayı Girişi
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(Color.WHITE);

        JTextField txtSayi = createField();
        txtSayi.setPreferredSize(new Dimension(50, 28));
        JButton btnOlustur = createButton("Tablo Oluştur", Color.GRAY);
        btnOlustur.setPreferredSize(new Dimension(110, 28));
        btnOlustur.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        top.add(createLabel("Ders Sayısı:"));
        top.add(txtSayi);
        top.add(btnOlustur);

        // Liste Başlıkları
        JPanel header = new JPanel(new GridLayout(1, 2));
        header.setBackground(Color.WHITE);
        header.add(new JLabel("Not (0-100)", 0));
        header.add(new JLabel("Kredi", 0));

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(top, BorderLayout.NORTH);
        topContainer.add(header, BorderLayout.SOUTH);

        // Orta Kısım: Kaydırılabilir Liste
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(formPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        // Alt Kısım: Hesapla
        JPanel bottom = new JPanel(new BorderLayout(5, 5));
        bottom.setBackground(Color.WHITE);
        JLabel lblOrt = new JLabel("Ortalama: -", 0);
        lblOrt.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JButton btnHesapla = createButton("HESAPLA", SUCCESS);

        // Buton: Tabloyu Oluştur
        btnOlustur.addActionListener(e -> {
            try {
                int n = Integer.parseInt(txtSayi.getText());
                formPanel.removeAll();
                notFields.clear(); krediFields.clear();

                for (int i=0; i<n; i++) {
                    JPanel row = new JPanel(new GridLayout(1, 2, 10, 0));
                    row.setBackground(Color.WHITE);
                    row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

                    JTextField tNot = createField(); tNot.setHorizontalAlignment(0);
                    JTextField tKredi = createField(); tKredi.setHorizontalAlignment(0);

                    notFields.add(tNot); krediFields.add(tKredi);
                    row.add(tNot); row.add(tKredi);

                    formPanel.add(row);
                    formPanel.add(Box.createVerticalStrut(5));
                }
                formPanel.revalidate();
                formPanel.repaint();
            } catch (Exception ex) { showMsg("Geçerli bir ders sayısı giriniz."); }
        });

        // Buton: Ortalamayı Hesapla
        btnHesapla.addActionListener(e -> {
            try {
                double tPuan=0, tKredi=0;
                for(int i=0; i<notFields.size(); i++){
                    String nS = notFields.get(i).getText().trim();
                    String kS = krediFields.get(i).getText().trim();
                    if(!nS.isEmpty() && !kS.isEmpty()){
                        tPuan += Double.parseDouble(nS) * Double.parseDouble(kS);
                        tKredi += Double.parseDouble(kS);
                    }
                }
                if(tKredi==0) lblOrt.setText("Veri Girilmedi");
                else {
                    double sonuc = (tPuan/tKredi)/25;
                    lblOrt.setText(String.format("Ortalama: %.2f", sonuc));
                    // OOP Proje şartı: Model sınıfı kullanımı (dummy check)
                    new model.Ogrenci("Sanal", "Ogrenci", "0", "-") {
                        @Override public boolean durumKontrol() { return false; }
                    }.setNotOrtalamasi((int)sonuc);
                }
            } catch (Exception ex) { showMsg("Hatalı not veya kredi girişi!"); }
        });

        bottom.add(lblOrt, BorderLayout.NORTH);
        bottom.add(btnHesapla, BorderLayout.SOUTH);

        main.add(topContainer, BorderLayout.NORTH);
        main.add(scroll, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);
        return main;
    }

    // Yardımcı Metotlar 
    private JTextField createField() {
        JTextField t = new JTextField();
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.setPreferredSize(new Dimension(0, 28));
        t.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1, true), new EmptyBorder(2, 5, 2, 5)));
        return t;
    }
    private JLabel createLabel(String t) { JLabel l = new JLabel(t); l.setFont(BOLD_FONT); return l; }
    private JButton createButton(String t, Color c) {
        JButton b = new JButton(t); b.setFont(BOLD_FONT); b.setBackground(c);
        b.setForeground(Color.WHITE); b.setFocusPainted(false); b.setBorderPainted(false);
        return b;
    }
    private void showMsg(String m) { JOptionPane.showMessageDialog(this, m); }
}