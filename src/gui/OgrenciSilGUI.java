package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import java.awt.*;

public class OgrenciSilGUI extends JFrame {

    JTextField txtNo;

    public OgrenciSilGUI() {
        setTitle("Öğrenci Sil");
        setSize(300, 150);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        add(new JLabel("Silinecek Öğrenci No:"));
        txtNo = new JTextField(15);
        add(txtNo);

        JButton btnSil = new JButton("Sil");
        btnSil.addActionListener(e -> silAction());
        add(btnSil);

        setLocationRelativeTo(null);
    }

    private void silAction() {
        try {
            boolean sonuc = DosyaIslemleri.ogrenciSil(txtNo.getText());
            if (sonuc) {
                JOptionPane.showMessageDialog(this, "Öğrenci silindi.");
            } else {
                JOptionPane.showMessageDialog(this, "Bu numaraya ait öğrenci bulunamadı.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
    }
}