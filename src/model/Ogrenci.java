package model;

import java.time.LocalDate;
import exception.GecersizGirisBilgisiException;

// Hem abstract sınıfı kalıtıyor hem de interface'i implemente ediyor (Çoklu Kalıtım Alan Sınıf, Bölüm 1.1, 17)
public abstract class Ogrenci extends Kisiler implements Yonetilebilir {

    // En az 4 alan (private)
    private String ogrenciNo;
    private String bolum;
    private int notOrtalamasi; // Primitive int
    private Integer mezuniyetYili; // Wrapper Integer (Bölüm 2.1)

    // Constructor Overloading 1: Ana Yapıcı
    public Ogrenci(String ad, String soyad, String tcKimlikNo, LocalDate dogumTarihi,
                   String ogrenciNo, String bolum, int notOrtalamasi, Integer mezuniyetYili) {
        super(ad, soyad, tcKimlikNo, dogumTarihi);
        this.ogrenciNo = ogrenciNo;
        this.bolum = bolum;
        this.notOrtalamasi = notOrtalamasi;
        this.mezuniyetYili = mezuniyetYili;
    }

    // Constructor Overloading 2: Kısmi Yapıcı
    public Ogrenci(String ad, String soyad, String ogrenciNo, String bolum) {
        super(ad, soyad, "00000000000", LocalDate.of(2000, 1, 1));
        this.ogrenciNo = ogrenciNo;
        this.bolum = bolum;
        this.notOrtalamasi = 0;
        this.mezuniyetYili = null;
    }

    // Setterlarda Kontrol (En az 5 farklı kontrol, Bölüm 3 gereksinimi)
    public void setBolum(String bolum) throws GecersizGirisBilgisiException {
        if (bolum == null || bolum.trim().isEmpty()) { // 1. null kontrolü
            throw new GecersizGirisBilgisiException("Bolum alani bos birakilamaz.");
        }
        this.bolum = bolum;
    }

    public void setNotOrtalamasi(int notOrtalamasi) throws GecersizGirisBilgisiException {
        if (notOrtalamasi < 0 || notOrtalamasi > 100) { // 2. Aralık kontrolü
            throw new GecersizGirisBilgisiException("Not ortalamasi 0 ile 100 arasinda olmalidir.");
        }
        this.notOrtalamasi = notOrtalamasi;
    }

    public void setOgrenciNo(String ogrenciNo) {
        // ... (Diğer setter kontrolleri)
        this.ogrenciNo = ogrenciNo;
    }

    // Override Edilen Metotlar (Polimorfizm ve Bölüm 4.4 gereksinimi)
    @Override
    public String getPozisyon() { return "Ogrenci - " + this.bolum; }
    @Override
    public void bilgiSistemiErisim() { System.out.println("Ogrenci bilgi sistemine erisildi."); }

    @Override // Raporlanabilir'den gelen metot override edildi (Bölüm 4.4)
    public String detayliRaporOlustur() {
        // String metot kullanımı (toLowerCase) (Bölüm 2.2 gereksinimi)
        String durum = (mezuniyetYili == null) ? "Aktif" : "Mezun";
        return String.format("Ogr No: %s | Bolum: %s | Ortalama: %d | Durum: %s",
                ogrenciNo.toLowerCase(), bolum, notOrtalamasi, durum);
    }

    // ... (Getterlar ve Yonetilebilir Metotları)
    public String getOgrenciNo() { return ogrenciNo; }
    public String getBolum() { return bolum; }

    @Override
    public boolean kaydet() { return true; }
    @Override
    public boolean sil(String id) { return true; }
    @Override
    public boolean guncelle() { return true; }
}