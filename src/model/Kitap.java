package model;

import service.DosyaIslemleri;
import java.io.IOException;

public class Kitap implements Yazdirilabilir, Kaydedilebilir {

    private String kitapAdi;
    private String yazarAdi;
    private String isbn;
    private boolean musaitMi;

    // --- YAPICI METOT (CONSTRUCTOR) - SORUN BURADAYDI ---
    public Kitap(String kitapAdi, String yazarAdi, String isbn, boolean musaitMi) {
        this.kitapAdi = kitapAdi; // Bu satır yoksa tablo NULL olur
        this.yazarAdi = yazarAdi; // Bu satır yoksa yazar NULL olur
        this.isbn = isbn;         // Bu satır yoksa ISBN NULL olur
        this.musaitMi = musaitMi;
    }

    // --- GETTERLAR ---
    public String getYazarAdi() { return yazarAdi; }
    public String getIsbn() { return isbn; }
    public boolean isMusaitMi() { return musaitMi; }
    public void setMusaitMi(boolean musaitMi) { this.musaitMi = musaitMi; }

    // --- INTERFACE METOTLARI ---
    @Override
    public boolean kaydet() {
        try {
            String durum = musaitMi ? "Müsait" : "Oduncte";
            DosyaIslemleri.kitapEkle(kitapAdi, yazarAdi, isbn, durum);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean sil(String id) {
        try {
            DosyaIslemleri.kitapSil(id);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean guncelle() {
        sil(this.isbn);   // Eskisini sil
        return kaydet();  // Yenisini ekle
    }

    @Override
    public String bilgiRaporuOlustur() { return kitapAdi; }
    @Override
    public String detayliRaporOlustur() { return kitapAdi + " - " + isbn; }
    @Override
    public boolean durumKontrol() { return musaitMi; }
    @Override
    public String toString() { return kitapAdi; }
    // ... Diğer kodların en altına, class bitmeden hemen önceye ...


    // Kitap.java içindeki ciktiAl metodu
    @Override
    public void ciktiAl() {
        System.out.println("------------------------------------");
        System.out.println("📚 KİTAP BİLGİ KARTI"); // Başlığı düzelttik
        // Zaten detayliRaporOlustur() metodun isim ve ISBN döndürüyor,
        // yazar adını da ekleyerek tek satırda tertemiz yazalım:
        System.out.println("Kitap Detayı: " + this.detayliRaporOlustur() + " (Yazar: " + yazarAdi + ")");
        System.out.println("Durum: " + (musaitMi ? "Rafta" : "Ödünç Verildi"));
        System.out.println("------------------------------------");
    }
}