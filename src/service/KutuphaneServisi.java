package service;

import model.Kitap;
import model.Kutuphane;
import java.util.List;
import java.util.Map;

public class KutuphaneServisi {
    private Map<String, Kitap> kitapKatalogu;
    private Kutuphane fizikselKutuphane; // Kütüphane nesnesi

    public KutuphaneServisi() {
        // Örnek: 100 kitap kapasiteli bir kütüphane
        this.fizikselKutuphane = new Kutuphane("Merkez Kütüphane", 100);
        // ... (diğer başlatma kodları)
    }

    public void kitapEkle(Kitap yeniKitap) {
        int mevcutSayi = kitapKatalogu.size();

        // 1. Kapasite Kontrolü (Alan sınıfından gelen abstract metodun kullanımı)
        if (fizikselKutuphane.kapasiteKontrol(mevcutSayi)) {
            kitapKatalogu.put(yeniKitap.getIsbn(), yeniKitap);

            // 2. Kalan Kapasite Hesabı (Alan sınıfından gelen somut metodun kullanımı)
            int kalanYer = fizikselKutuphane.kapasiteKontrol(kitapKatalogu.size());
            System.out.println("Kitap eklendi. Kalan kitap kapasitesi: " + kalanYer);
        } else {
            System.out.println("Hata: Kütüphane kapasitesi dolu! (Maks: " + fizikselKutuphane.getKapasite() + ")");
        }
    }
}