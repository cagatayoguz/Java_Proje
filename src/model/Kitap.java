package model;

import java.util.ArrayList;

public class Kitap implements Raporlanabilir { // Sadece Interface implemente etti

    // En az 4 alan
    private String kitapAdi;
    private String yazarAdi;
    private String isbn; // Eşsiz ID
    private boolean musaitMi;

    // Yapıcı Metot
    public Kitap(String kitapAdi, String yazarAdi, String isbn, boolean musaitMi) {
        this.kitapAdi = kitapAdi;
        this.yazarAdi = yazarAdi;
        this.isbn = isbn;
        this.musaitMi = musaitMi;
    }


    // Getterlar ve Setterlar
    public String getKitapAdi() { return kitapAdi; }
    public String getYazarAdi() { return yazarAdi; }
    public String getIsbn() { return isbn; }
    public boolean isMusaitMi() { return musaitMi; }

    public void setMusaitMi(boolean musaitMi) { this.musaitMi = musaitMi; }

    // Raporlanabilir Interface Metotları
    @Override
    public String bilgiRaporuOlustur() {
        return kitapAdi + " - " + yazarAdi;
    }

    @Override
    public String detayliRaporOlustur() {
        String durum = musaitMi ? "Müsait" : "Ödünçte";
        return String.format("%s, Yazar: %s, ISBN: %s, Durum: %s",
                kitapAdi, yazarAdi, isbn, durum);
    }

    @Override
    public boolean durumKontrol() {
        return musaitMi;
    }

    // Dosya İşlemleri için yardımcı metot (Metot Overloading - Bölüm 4.4)
    public String toCsvString() { // Overload 1
        return String.format("%s,%s,%s,%b", kitapAdi, yazarAdi, isbn, musaitMi);
    }

    public String toCsvString(String ayirici) { // Overload 2
        return String.format("%s%s%s%s%s%s%s%s%b", kitapAdi, ayirici, yazarAdi, ayirici, isbn, ayirici, musaitMi);
    }
}