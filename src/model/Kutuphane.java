package model;

import java.util.ArrayList;
import java.util.List;
import exception.GecersizGirisBilgisiException;

// Kütüphane, fiziksel bir 'Alan'dır, içinde kitaplar barındırır ve Kaydedilebilir.
public class Kutuphane extends Alan implements Kaydedilebilir {

    private List<Kitap> kitapListesi;

    public Kutuphane(String adi) {
        super(adi, 99999); // Sembolik kapasite
        this.kitapListesi = new ArrayList<>();
    }

    // --- ALAN SINIFINDAN GELEN METOTLAR ---
    @Override
    public String kullanimAmaci() {
        return "Kitap arşivleme ve ödünç verme hizmetleri.";
    }

    @Override
    public boolean kapasiteKontrol(int sayi) {
        return true;
    }

    // --- KİTAP YÖNETİM METOTLARI ---
    public void kitapEkle(Kitap kitap) throws GecersizGirisBilgisiException {
        // HATA ÇÖZÜMÜ BURADA YAPILDI:
        for (Kitap k : kitapListesi) {
            // NullPointerException önlemi:
            // Eğer listedeki kitabın ISBN'i null ise veya yeni kitabın ISBN'i null ise hata vermesin diye kontrol ekledik.
            if (k.getIsbn() != null && kitap.getIsbn() != null && k.getIsbn().equals(kitap.getIsbn())) {
                throw new GecersizGirisBilgisiException("Bu ISBN numarasına sahip kitap zaten var!");
            }
        }
        kitapListesi.add(kitap);
    }

    public void mevcutKitaplariYukle(List<Kitap> kitaplar) {
        this.kitapListesi.clear();
        this.kitapListesi.addAll(kitaplar);
    }

    public int mevcutKitapSayisi() {
        return kitapListesi.size();
    }

    public List<Kitap> getKitapListesi() {
        return kitapListesi;
    }

    // --- KAYDEDİLEBİLİR INTERFACE METOTLARI ---
    @Override
    public boolean kaydet() {
        boolean hepsiBasarili = true;
        for (Kitap k : kitapListesi) {
            if (!k.kaydet()) {
                hepsiBasarili = false;
            }
        }
        return hepsiBasarili;
    }

    @Override
    public boolean sil(String id) { return true; }

    @Override
    public boolean guncelle() { return true; }
}