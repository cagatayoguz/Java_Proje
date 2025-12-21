package model;

import service.DosyaIslemleri;
import exception.GecersizGirisBilgisiException;
import java.io.IOException;

public class Ogrenci extends Kisiler implements Kaydedilebilir, Yazdirilabilir {

    private String ogrenciNo;
    private String bolum;
    private int notOrtalamasi;

    public Ogrenci(String ad, String soyad, String ogrenciNo, String bolum) {
        super(ad, soyad);
        this.ogrenciNo = ogrenciNo;
        this.bolum = bolum;
        this.notOrtalamasi = 0; // Varsayılan başlangıç değeri
    }

    // --- INTERFACE (KAYDEDİLEBİLİR) METOTLARI ---

    @Override
    public boolean kaydet() {
        try {
            // Dosya servisini çağırarak veriyi gerçekten dosyaya yazıyoruz.
            DosyaIslemleri.ogrenciEkle(
                    getAd(),
                    getSoyad(),
                    this.bolum,
                    this.ogrenciNo,
                    "1. Sınıf", // Varsayılan değer
                    String.valueOf(this.notOrtalamasi)
            );
            return true;
        } catch (IOException e) {
            System.err.println("Kayıt hatası: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean sil(String id) {
        try {
            // Dosya servisinden silme işlemini çağırıyoruz.
            DosyaIslemleri.ogrenciSil(id);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean guncelle() {
        // Güncelleme mantığı: Önce sil, sonra yeni haliyle tekrar kaydet.
        if (sil(this.ogrenciNo)) {
            return kaydet();
        }
        return false;
    }

    // --- DİĞER OVERRIDE VE GETTER/SETTER METOTLARI ---

    @Override
    public String getPozisyon() {
        return "Öğrenci - " + this.bolum;
    }

    @Override
    public String detayliRaporOlustur() {
        return String.format("No: %s | Bölüm: %s | Ort: %d", ogrenciNo, bolum, notOrtalamasi);
    }

    @Override
    public boolean durumKontrol() {
        return false;
    }

    @Override
    public void ciktiAl() {
        System.out.println(detayliRaporOlustur());
    }

    public void setBolum(String bolum) throws GecersizGirisBilgisiException {
        if (bolum == null || bolum.trim().isEmpty()) throw new GecersizGirisBilgisiException("Bölüm boş olamaz.");
        this.bolum = bolum;
    }

    public void setNotOrtalamasi(int not) throws GecersizGirisBilgisiException {
        if (not < 0 || not > 100) throw new GecersizGirisBilgisiException("Not 0-100 arası olmalı.");
        this.notOrtalamasi = not;
    }

    public String getOgrenciNo() { return ogrenciNo; }
    public String getBolum() { return bolum; }
    public int getNotOrtalamasi() { return notOrtalamasi; }
}