package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AkademisyenListeGUI extends JFrame {

    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public AkademisyenListeGUI() {
        setTitle("Akademisyen Yönetimi");
        setSize(1000, 600); // Ekran görüntüsüne uygun genişlik
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- ÜST PANEL (Arama Çubuğu) ---
        JPanel ustPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ustPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblAra = new JLabel("Ad Soyad Ara: ");
        lblAra.setFont(new Font("Arial", Font.BOLD, 12));

        JTextField txtAra = new JTextField(30);

        ustPanel.add(lblAra);
        ustPanel.add(txtAra);

        add(ustPanel, BorderLayout.NORTH);

        // --- ORTA PANEL (Tablo) ---
        // Sütun Başlıkları
        String[] kolonlar = {"Ad Soyad", "Ünvan", "Alan (Ne Hocası)", "E-Posta", "Oda"};

        // Model Oluşturma (Hücrelerin düzenlenmesini engellemek için override ettik)
        model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tablo üzerinde değişiklik yapılamasın
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(25); // Satır yüksekliği
        table.getTableHeader().setReorderingAllowed(false); // Sütun yerleri değişmesin

        // Arama/Filtreleme Fonksiyonu için Sorter
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Verileri Yükle (Ekran görüntüsündeki veriler)
        verileriYukle();

        // ScrollPane içine tabloyu ekle
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- ARAMA İŞLEVİ ---
        // Her tuşa basıldığında filtreleme yapar
        txtAra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = txtAra.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    // (?i) büyük/küçük harf duyarsız arama yapar
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    } catch (Exception ex) {
                        // Regex hatası olursa yoksay
                    }
                }
            }
        });
    }

    private void verileriYukle() {
        // Ekran görüntüsündeki verileri satır satır ekliyoruz
        Object[][] veriler = {
                {"Ahmet Yılmaz", "Prof. Dr.", "Bilgisayar Mühendisliği", "ahmet.yilmaz@gazi.edu.tr", "A-203"},
                {"Mehmet Kaya", "Prof. Dr.", "Elektrik-Elektronik", "mehmet.kaya@gazi.edu.tr", "A-205"},
                {"Ayşe Demir", "Doç. Dr.", "Bilgisayar Mühendisliği", "ayse.demir@gazi.edu.tr", "B-108"},
                {"Fatma Çelik", "Doç. Dr.", "Endüstri Mühendisliği", "fatma.celik@gazi.edu.tr", "B-110"},
                {"Ali Şahin", "Dr. Öğr. Üyesi", "Yazılım Mühendisliği", "ali.sahin@gazi.edu.tr", "C-301"},
                {"Zeynep Arslan", "Dr. Öğr. Üyesi", "Bilgisayar Ağları", "zeynep.arslan@gazi.edu.tr", "C-302"},
                {"Murat Koç", "Dr. Öğr. Üyesi", "Veri Tabanları", "murat.koc@gazi.edu.tr", "C-303"},
                {"Emre Aydın", "Araş. Gör.", "Bilgisayar Mühendisliği", "emre.aydin@gazi.edu.tr", "D-101"},
                {"Elif Özkan", "Araş. Gör.", "Elektrik-Elektronik", "elif.ozkan@gazi.edu.tr", "D-102"},
                {"Caner Polat", "Araş. Gör.", "Endüstri Mühendisliği", "caner.polat@gazi.edu.tr", "D-103"},
                {"Burak Tunç", "Araş. Gör.", "Yazılım Mühendisliği", "burak.tunc@gazi.edu.tr", "D-104"},
                {"Seda Karaca", "Araş. Gör.", "Bilgisayar Ağları", "seda.karaca@gazi.edu.tr", "D-105"},
                {"Hakan Güneş", "Doç. Dr.", "Elektronik", "hakan.gunes@gazi.edu.tr", "B-112"},
                {"Serkan Yıldız", "Dr. Öğr. Üyesi", "Otomotiv Mühendisliği", "serkan.yildiz@gazi.edu.tr", "C-304"},
                {"Büşra Akın", "Araş. Gör.", "Makine Mühendisliği", "busra.akin@gazi.edu.tr", "D-106"},
                {"Okan Eren", "Araş. Gör.", "Bilgisayar Mühendisliği", "okan.eren@gazi.edu.tr", "D-107"},
                {"Nihat Özdemir", "Prof. Dr.", "Endüstri Mühendisliği", "nihat.ozdemir@gazi.edu.tr", "A-207"},
                {"Gamze Kurt", "Doç. Dr.", "Yapay Zeka", "gamze.kurt@gazi.edu.tr", "B-115"},
                {"İsmail Toprak", "Dr. Öğr. Üyesi", "Bilgisayar Donanımı", "ismail.toprak@gazi.edu.tr", "C-305"},
                {"Ece Yalçın", "Araş. Gör.", "Yazılım Testi", "ece.yalcin@gazi.edu.tr", "D-108"},
                {"Kerem Aslan", "Araş. Gör.", "Bilgisayar Mühendisliği", "kerem.aslan@gazi.edu.tr", "D-109"},
                {"Selin Yurt", "Araş. Gör.", "Veri Bilimi", "selin.yurt@gazi.edu.tr", "D-110"},
                {"Umut Korkmaz", "Dr. Öğr. Üyesi", "Yapay Zeka", "umut.korkmaz@gazi.edu.tr", "C-306"},
                {"Deniz Aksoy", "Doç. Dr.", "Bilgisayar Grafikleri", "deniz.aksoy@gazi.edu.tr", "B-116"},
                {"Merve Şen", "Araş. Gör.", "Matematik", "merve.sen@gazi.edu.tr", "D-111"},
                {"Tolga Er", "Prof. Dr.", "Makine Mühendisliği", "tolga.er@gazi.edu.tr", "A-210"},
                {"Onur Kaplan", "Araş. Gör.", "Fizik", "onur.kaplan@gazi.edu.tr", "D-112"},
                {"İlayda Kılıç", "Araş. Gör.", "Kimya", "ilayda.kilic@gazi.edu.tr", "D-113"},
                {"Barış Yılmaz", "Dr. Öğr. Üyesi", "Endüstri Mühendisliği", "baris.yilmaz@gazi.edu.tr", "C-307"},
                {"Sinem Çetin", "Doç. Dr.", "Elektronik", "sinem.cetin@gazi.edu.tr", "B-118"}
        };

        for (Object[] satir : veriler) {
            model.addRow(satir);
        }
    }
}