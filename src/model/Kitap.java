package model;

public class Kitap implements Raporlanabilir {

    private String kitapAdi;
    private String yazarAdi;
    private String isbn;
    private boolean musaitMi;

    // Yıl parametresi kalktı, kod sadeleşti
    public Kitap(String kitapAdi, String yazarAdi, String isbn, boolean musaitMi) {
        this.kitapAdi = kitapAdi;
        this.yazarAdi = yazarAdi;
        this.isbn = isbn;
        this.musaitMi = musaitMi;
    }

    // Getterlar
    public String getKitapAdi() { return kitapAdi; }
    public String getYazarAdi() { return yazarAdi; }
    public String getIsbn() { return isbn; }
    public boolean isMusaitMi() { return musaitMi; }
    public void setMusaitMi(boolean musaitMi) { this.musaitMi = musaitMi; }

    // Raporlama Metotları
    @Override
    public String bilgiRaporuOlustur() {
        return kitapAdi + " - " + yazarAdi;
    }

    @Override
    public String detayliRaporOlustur() {
        String durum = musaitMi ? "Müsait" : "Ödünçte";
        return String.format("%s, Yazar: %s, ISBN: %s, Durum: %s", kitapAdi, yazarAdi, isbn, durum);
    }

    @Override
    public boolean durumKontrol() { return musaitMi; }

    // Dosya yazımı için sade format
    public String toCsvString() {
        return String.format("%s,%s,%s,%b", kitapAdi, yazarAdi, isbn, musaitMi);
    }
}