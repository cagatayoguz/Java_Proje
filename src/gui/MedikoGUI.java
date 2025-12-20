package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class MedikoGUI extends JFrame {
    public MedikoGUI() {
        setTitle("Mediko Sağlık Merkezi");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] kolonlar = {"Bölüm", "Doktor Adı", "Uzmanlık", "Çalışma Günler"};
        DefaultTableModel model = new DefaultTableModel(kolonlar, 0);
        JTable tablo = new JTable(model);

        MedikoServisi servis = new MedikoServisi();
        for (Map.Entry<String, List<Doktor>> entry : servis.getBolumler().entrySet()) {
            for (Doktor d : entry.getValue()) {
                model.addRow(new Object[]{
                        entry.getKey(),
                        d.tamAdGetir(),
                        d.getUzmanlikAlani(),
                        String.join(", ", d.getCalismaGunleri())
                });
            }
        }

        add(new JScrollPane(tablo), BorderLayout.CENTER);
    }
}