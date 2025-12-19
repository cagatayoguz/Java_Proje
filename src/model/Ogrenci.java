package model;

import java.time.LocalDate;
import exception.GecersizGirisBilgisiException;

public abstract class Ogrenci extends Kisiler implements Kaydedilebilir {

    private String ogrenciNo;
    private String bolum;
    private int notOrtalamasi;

    // SADECE KULLANILAN CONSTRUCTOR (Kısmi Yapıcı)
    // TC ve Doğum Tarihi projede tutulmadığı için üst sınıfa (Kisiler) varsayılan değer gönderiyoruz.
    public Ogrenci(String ad, String soyad, String ogrenciNo, String bolum) {
        super(ad, soyad);
        this.ogrenciNo = ogrenciNo;
        this.bolum = bolum;
        this.notOrtalamasi = 0; // Varsayılan başlangıç
    }

    // --- SETTER METOTLARI (Validation İçin Kullanılıyor) ---

    // OgrenciEkleGUI'de kullanılıyor
    public void setBolum(String bolum) throws GecersizGirisBilgisiException {
        if (bolum == null || bolum.trim().isEmpty()) {
            throw new GecersizGirisBilgisiException("Bolum alani bos birakilamaz.");
        }
        this.bolum = bolum;
    }

    // OgrenciEkleGUI'de kullanılıyor
    public void setNotOrtalamasi(int notOrtalamasi) throws GecersizGirisBilgisiException {
        if (notOrtalamasi < 0 || notOrtalamasi > 100) {
            throw new GecersizGirisBilgisiException("Not ortalamasi 0 ile 100 arasinda olmalidir.");
        }
        this.notOrtalamasi = notOrtalamasi;
    }

    // --- OVERRIDE METOTLAR ---

    @Override
    public String getPozisyon() {
        return "Ogrenci - " + this.bolum;
    }

    @Override
    public String detayliRaporOlustur() {
        // Mezuniyet yılı kalktığı için burayı sadeleştirdik
        return String.format("Ogr No: %s | Bolum: %s | Ortalama: %d | Durum: Aktif",
                ogrenciNo, bolum, notOrtalamasi);
    }

    // --- INTERFACE METOTLARI (Depo sınıfı için gerekli) ---
    @Override
    public boolean kaydet() { return true; }

    @Override
    public boolean sil(String id) { return true; }

    @Override
    public boolean guncelle() { return true; }

    // --- GETTER METOTLARI (DosyaIslemleri için gerekli) ---
    public String getOgrenciNo() { return ogrenciNo; }

    public String getBolum() { return bolum; }

    public int getNotOrtalamasi() { return notOrtalamasi; }
}