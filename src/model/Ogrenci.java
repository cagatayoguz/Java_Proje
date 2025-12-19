package model;

import java.time.LocalDate;
import exception.GecersizGirisBilgisiException;

// Hem abstract sınıfı kalıtıyor hem de interface'i implemente ediyor
public abstract class Ogrenci extends Kisiler implements Kaydedilebilir {

    private String ogrenciNo;
    private String bolum;
    private int notOrtalamasi;
    private Integer mezuniyetYili;

    // Constructor 1: Ana Yapıcı
    public Ogrenci(String ad, String soyad, String tcKimlikNo, LocalDate dogumTarihi,
                   String ogrenciNo, String bolum, int notOrtalamasi, Integer mezuniyetYili) {
        super(ad, soyad, tcKimlikNo, dogumTarihi);
        this.ogrenciNo = ogrenciNo;
        this.bolum = bolum;
        this.notOrtalamasi = notOrtalamasi;
        this.mezuniyetYili = mezuniyetYili;
    }

    // Constructor 2: Kısmi Yapıcı
    public Ogrenci(String ad, String soyad, String ogrenciNo, String bolum) {
        super(ad, soyad, "00000000000", LocalDate.of(2000, 1, 1));
        this.ogrenciNo = ogrenciNo;
        this.bolum = bolum;
        this.notOrtalamasi = 0;
        this.mezuniyetYili = null;
    }

    // Setter Kontrolleri
    public void setBolum(String bolum) throws GecersizGirisBilgisiException {
        if (bolum == null || bolum.trim().isEmpty()) {
            throw new GecersizGirisBilgisiException("Bolum alani bos birakilamaz.");
        }
        this.bolum = bolum;
    }

    public void setNotOrtalamasi(int notOrtalamasi) throws GecersizGirisBilgisiException {
        if (notOrtalamasi < 0 || notOrtalamasi > 100) {
            throw new GecersizGirisBilgisiException("Not ortalamasi 0 ile 100 arasinda olmalidir.");
        }
        this.notOrtalamasi = notOrtalamasi;
    }

    public void setOgrenciNo(String ogrenciNo) {
        this.ogrenciNo = ogrenciNo;
    }

    // Override Edilen Metotlar
    @Override
    public String getPozisyon() {
        return "Ogrenci - " + this.bolum;
    }

    @Override
    public void bilgiSistemiErisim() {
        System.out.println("Ogrenci bilgi sistemine erisildi.");
    }

    @Override
    public String detayliRaporOlustur() {
        String durum = (mezuniyetYili == null) ? "Aktif" : "Mezun";
        return String.format("Ogr No: %s | Bolum: %s | Ortalama: %d | Durum: %s",
                ogrenciNo.toLowerCase(), bolum, notOrtalamasi, durum);
    }

    // Interface Metotları
    @Override
    public boolean kaydet() { return true; }

    @Override
    public boolean sil(String id) { return true; }

    @Override
    public boolean guncelle() { return true; }

    // Getterlar
    public String getOgrenciNo() { return ogrenciNo; }
    public String getBolum() { return bolum; }

    // DÜZELTME: Object yerine int döndürmeli
    public int getNotOrtalamasi() { return notOrtalamasi; }
}