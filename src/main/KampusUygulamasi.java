package main;
import service.KutuphaneServisi;
import gui.AnaMenuGUI;

public class KampusUygulamasi {

    private static KutuphaneServisi kutuphaneServisi;

    public static void main(String[] args) {
        System.out.println("Kampüs Yönetim ve Bilgi Sistemi Başlatılıyor...");

        // Kütüphane Servisini başlat ve başlangıç verilerini yükle
        try {
            kutuphaneServisi = new KutuphaneServisi();
            System.out.println("Kütüphane verileri yüklendi. Toplam Kitap: " + kutuphaneServisi.tumKitaplariGetir().size());
        } catch (Exception e) {
            System.err.println("Veri yükleme sırasında kritik hata: " + e.getMessage());
            return;
        }

        // Arayüz kodunu basitleştirmek için burada ana menüyü başlatıyoruz.
        java.awt.EventQueue.invokeLater(() -> {
            new AnaMenuGUI().setVisible(true);
        });

    }

    // Diğer sınıfların servis nesnelerine erişimi için statik getter metotları
    public static KutuphaneServisi getKutuphaneServisi() {
        return kutuphaneServisi;
    }
}