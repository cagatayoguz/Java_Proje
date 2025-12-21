package model;

import java.util.ArrayList;
import java.util.List;
import exception.GecersizGirisBilgisiException;

// Kütüphane, fiziksel bir 'Alan'dır, içinde kitaplar barındırır.
public class Kutuphane extends Alan implements Kaydedilebilir,Yazdirilabilir {

    private List<Kitap> kitapListesi;

    public Kutuphane(String adi) {
        // BURASI DEĞİŞTİ: Kapasiteyi 50 yaptık.
        // Böylece 51. kitabı eklemeye çalışınca "Yer Yok" hatası verecek.
        super(adi, 50);
        this.kitapListesi = new ArrayList<>();
    }

    // --- ALAN SINIFINDAN GELEN METOTLAR (ARTIK İŞLEVSEL) ---

    @Override
    public String kullanimAmaci() {
        // Kapasite bilgisini de yazdırıyoruz
        return "Kitap arşivleme (Kapasite: " + getKapasite() + " Kitap)";
    }

    @Override
    public boolean kapasiteKontrol(int mevcutSayi) {
        // ARTIK İŞLEVSEL: Mevcut sayı kapasiteden küçükse true, değilse false döner.
        return mevcutSayi < getKapasite();
    }

    // --- KİTAP YÖNETİM METOTLARI ---

    public void kitapEkle(Kitap kitap) throws GecersizGirisBilgisiException {
        // 1. ADIM: KAPASİTE KONTROLÜ (Abstract Sınıftan Gelen Özellik)
        // Listemizin şu anki boyutunu kontrol ediyoruz
        if (!kapasiteKontrol(kitapListesi.size())) {
            throw new GecersizGirisBilgisiException(
                    "Kütüphane raf kapasitesi doldu! (Max: " + getKapasite() + " Kitap)\n" +
                            "Yeni kitap eklemek için önce eski kitaplardan silmelisiniz."
            );
        }

        // 2. ADIM: ISBN KONTROLÜ
        for (Kitap k : kitapListesi) {
            if (k.getIsbn() != null && kitap.getIsbn() != null && k.getIsbn().equals(kitap.getIsbn())) {
                throw new GecersizGirisBilgisiException("Bu ISBN numarasına sahip kitap zaten var!");
            }
        }

        // Sorun yoksa ekle
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

    //Kaydetmemize yarayan metod
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

    @Override
    public String bilgiRaporuOlustur() {
        return "";
    }

    @Override
    public String detayliRaporOlustur() {
        return "";
    }

    @Override
    public boolean durumKontrol() {
        return false;
    }

    @Override
    public void ciktiAl() {

    }
}