package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

//Medico çalışanları ve nöbet saatleri listelenir
public class MedicoGUI extends JFrame {

    public MedicoGUI() {


        setTitle("Medico - Sağlık Merkezi");
        setSize(600, 700);

        // genişlik ayarlanıyor
        // Ana menü akışını bozmamak için DISPOSE tercih edildi.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); // Temiz bir görünüm için beyaz arka plan seçildi.
        setContentPane(mainPanel);

        //başlık alanı
        JLabel lblBaslik = new JLabel("Medico Çalışma Saatleri ve Doktorlar", SwingConstants.CENTER);
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblBaslik.setForeground(Color.DARK_GRAY);
        lblBaslik.setBorder(new EmptyBorder(20, 0, 20, 0));
        mainPanel.add(lblBaslik, BorderLayout.NORTH);

        //liste paneli
        // Elemanların alt alta nizami dizilmesi için tek sütunlu GridLayout kullanılıyor
        JPanel listePanel = new JPanel(new GridLayout(0, 1, 0, 10));
        listePanel.setBackground(Color.WHITE);
        listePanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Veritabanı kullanılmadı Array kullanıldı.
        // Format: {Bölüm, İsim, Durum, Çalışma Saatleri}
        String[][] doktorlar = {
                {"Dahiliye", "Dr. Ahmet Yılmaz", "Müsait", "09:00 - 18:00"},
                {"Dahiliye", "Dr. Sevgi Kaya", "İzinli", "09:00 - 16:00"},
                {"Diş Hekimliği", "Dt. Mehmet Öz", "Müsait", "08:30 - 17:00"},
                {"Psikoloji", "Psk. Canan Çelik", "Dolu", "10:00 - 15:00"},
                {"Psikoloji", "Psk. Burak Soylu", "Müsait", "09:00 - 17:00"},
                {"Acil Servis", "Dr. Ali Vural", "Nöbetçi", "7/24 Açık"},
                {"Hemşire Odası", "Hem. Fatma Yıldız", "Müsait", "08:00 - 18:00"},
                {"Diyetisyen", "Dyt. Elif Su", "Toplantıda", "13:00 - 16:00"}
        };

        // Veri setindeki her bir kayıt için dinamik olarak kart oluşturuldu.
        for (String[] dr : doktorlar) {
            listePanel.add(kartOlustur(dr[0], dr[1], dr[2], dr[3]));
        }


        //kaydırma çubuğu
        JScrollPane scrollPane = new JScrollPane(listePanel);
        scrollPane.setBorder(null); // Varsayılan kenarlık kaldırılarak modern görünüm sağlandı.
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }


    // Kod tekrarını önlemek ve tasarım standardını korumak için her satırı üreten metot.

    private JPanel kartOlustur(String bolum, String isim, String durum, String saatler) {
        JPanel kart = new JPanel(new BorderLayout());
        kart.setBackground(new Color(250, 250, 250));

        // Kartlar arasına sadece alt çizgi çekilerek sade bir liste görünümü elde edildi.
        kart.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JPanel icPanel = new JPanel(new BorderLayout());
        icPanel.setOpaque(false);
        icPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        //Kimlik Bilgilerinin olduğu panel
        JPanel solPanel = new JPanel(new GridLayout(2, 1));
        solPanel.setOpaque(false);

        JLabel lblIsim = new JLabel(isim);
        lblIsim.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblIsim.setForeground(Color.BLACK);

        JLabel lblBolum = new JLabel(bolum);
        lblBolum.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblBolum.setForeground(Color.GRAY);
        // Bölüm bilgisi hiyerarşide alt planda tutuldu.

        solPanel.add(lblIsim);
        solPanel.add(lblBolum);

        //Durum ve Zaman paneli
        JPanel sagPanel = new JPanel(new GridLayout(2, 1));
        sagPanel.setOpaque(false);

        // Saat Bilgisi
        JLabel lblSaat = new JLabel("🕒 " + saatler, SwingConstants.RIGHT);
        lblSaat.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSaat.setForeground(Color.DARK_GRAY);

        // Durum Bilgisi
        JLabel lblDurum = new JLabel(durum, SwingConstants.RIGHT);
        lblDurum.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Duruma göre dinamik renk ataması yapılarak kullanıcıya görsel geri bildirim verilir
        if (durum.equals("Müsait") || durum.equals("Nöbetçi")) {
            lblDurum.setForeground(new Color(25, 135, 84));
        } else {
            lblDurum.setForeground(new Color(220, 53, 69));
        }

        sagPanel.add(lblSaat);
        sagPanel.add(lblDurum);

        // Bileşenlerin birleştirilmesi
        icPanel.add(solPanel, BorderLayout.CENTER);
        icPanel.add(sagPanel, BorderLayout.EAST);
        kart.add(icPanel, BorderLayout.CENTER);

        //sabit yükseklik ayarlandı
        kart.setPreferredSize(new Dimension(0, 65));

        return kart;
    }
}