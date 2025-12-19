package model;

import java.util.ArrayList;
import java.util.List;
import exception.GecersizGirisBilgisiException;

// Kütüphane, fiziksel bir 'Alan'dır ve içinde kitaplar barındırır.
public class Kutuphane extends Alan {

    private List<Kitap> kitapListesi; // Kütüphanedeki kitaplar

    public Kutuphane(String adi, int kapasite) {
        super(adi, kapasite);
        this.kitapListesi = new ArrayList<>();
    }

    // --- ÖNEMLİ: Alan Sınıfından Gelen Metotların Mantığını Değiştirdik ---

    @Override
    public String kullanimAmaci() {
        return "Kitap arşivleme ve ödünç verme hizmetleri.";
    }

    // Kapasite kontrolü: "İçerideki insan sayısı" değil, "Raf kapasitesi" kontrolü
    // Polimorfizm örneği: Üst sınıfın metodunu kendi ihtiyacımıza göre eziyoruz.
    @Override
    public boolean kapasiteKontrol(int eklenecekKitapSayisi) {
        // Mevcut kitap sayısı + yeni eklenecekler > Kapasite ise FALSE döner
        return (kitapListesi.size() + eklenecekKitapSayisi) <= getKapasite();
    }

    // --- YENİ METOT: Kitap Ekleme İşlemi (Kontrollü) ---
    public void kitapEkle(Kitap kitap) throws GecersizGirisBilgisiException {
        // 1. Kapasite Kontrolü (Alan sınıfının metodu)
        if (!kapasiteKontrol(1)) {
            throw new GecersizGirisBilgisiException("Kütüphane kapasitesi DOLU! (" + getKapasite() + " kitaplık yer var)");
        }

        // 2. Mantıksal Kontrol (Daha önce aynı ISBN var mı?)
        for (Kitap k : kitapListesi) {
            if (k.getIsbn().equals(kitap.getIsbn())) {
                throw new GecersizGirisBilgisiException("Bu ISBN (" + kitap.getIsbn() + ") numarasına sahip kitap zaten var!");
            }
        }

        // Sorun yoksa listeye ekle
        kitapListesi.add(kitap);
    }

    // Listeyi dışarıdan doldurmak için (Dosyadan okuyunca buraya yükleyeceğiz)
    public void mevcutKitaplariYukle(List<Kitap> kitaplar) {
        // Listeyi sıfırlayıp yüklemek daha güvenlidir, tekrarı önler
        this.kitapListesi.clear();
        this.kitapListesi.addAll(kitaplar);
    }

    public int mevcutKitapSayisi() {
        return kitapListesi.size();
    }
}