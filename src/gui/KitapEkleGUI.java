package gui;

import service.DosyaIslemleri;
import javax.swing.*;
import java.awt.*;

public class KitapEkleGUI extends JFrame {
    private JTextField txtAd, txtYazar, txtIsbn;

    public KitapEkleGUI() {
        setTitle("Kitap Ekle");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Kitap Adı:")); txtAd = new JTextField(); add(txtAd);
        add(new JLabel("Yazar:")); txtYazar = new JTextField(); add(txtYazar);
        add(new JLabel("ISBN:")); txtIsbn = new JTextField(); add(txtIsbn);

        JButton btnKaydet = new JButton("Kaydet");
        btnKaydet.addActionListener(e -> kaydet());
        add(btnKaydet);

        setLocationRelativeTo(null);
    }

    private void kaydet() {
        try {
            DosyaIslemleri.kitapEkle(txtAd.getText(), txtYazar.getText(), txtIsbn.getText());
            JOptionPane.showMessageDialog(this, "Kitap başarıyla eklendi.");
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
    }
}