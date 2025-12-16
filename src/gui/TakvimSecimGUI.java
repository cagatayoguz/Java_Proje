package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TakvimSecimGUI extends JFrame {

    public TakvimSecimGUI() {
        setTitle("Takvimler");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1, 10, 20)); // 2 Buton alt alta
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // 1. Buton: Akademik Takvim (Direkt PDF açar)
        JButton btnAkademik = new JButton("Akademik Takvim");
        btnAkademik.setFont(new Font("Arial", Font.BOLD, 16));
        btnAkademik.addActionListener(e -> akademikTakvimAc());
        add(btnAkademik);

        // 2. Buton: Sınav Takvimi (Fakülte Seçimine Gider)
        JButton btnSinav = new JButton("Sınav Takvimi");
        btnSinav.setFont(new Font("Arial", Font.BOLD, 16));
        btnSinav.addActionListener(e -> new SinavFakulteSecimGUI().setVisible(true));
        add(btnSinav);

        setLocationRelativeTo(null);
    }

    private void akademikTakvimAc() {
        try {
            // Dosya yolu: veriler/takvimler/akademik_takvim.pdf
            File dosya = new File("veriler/takvimler/akademik_takvim.pdf");
            if (dosya.exists()) {
                Desktop.getDesktop().open(dosya);
            } else {
                JOptionPane.showMessageDialog(this, "Akademik takvim dosyası bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Dosya açılırken hata oluştu: " + ex.getMessage());
        }
    }
}