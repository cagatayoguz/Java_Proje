package model;
import service.DosyaIslemleri;
import exception.GecersizGirisBilgisiException;
import java.io.IOException;

public class  Ogrenci extends Kisiler{

    private String ogrenciNo;
    private String bolum;
    private double notOrtalamasi;

    public Ogrenci(String ad, String soyad, String ogrenciNo, String bolum) {
        super(ad, soyad);
        this.ogrenciNo = ogrenciNo;
        this.bolum = bolum;
        this.notOrtalamasi = 0; // Varsayılan başlangıç değeri
    }

    // KAYDEDİLEBİLİR metodları

    @Override
    public boolean kaydet() {
        try {
            // Dosya servisini çağırarak veriyi dosyaya yazıyoruz.
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

    // DİĞER OVERRIDE VE GETTER/SETTER METOTLARI

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
    public String getBolum() { return bolum; }

    public void setBolum(String bolum) throws GecersizGirisBilgisiException {
        if (bolum == null || bolum.trim().isEmpty()) throw new GecersizGirisBilgisiException("Bölüm boş olamaz.");
        this.bolum = bolum;
    }
    public double getNotOrtalamasi() { return notOrtalamasi; }


    public void setOgrenciNo(String ogrenciNo) throws GecersizGirisBilgisiException {
        if (ogrenciNo == null || ogrenciNo.trim().isEmpty()) {
            throw new GecersizGirisBilgisiException("Öğrenci numarası boş olamaz!");
        }
        // Örnek: Öğrenci numarası 9 haneli olmalı kuralı
        if (ogrenciNo.length() != 9) {
            throw new GecersizGirisBilgisiException("Öğrenci numarası 9 haneli olmalıdır.");
        }
        this.ogrenciNo = ogrenciNo;
    }

    public void setNotOrtalamasi(double notOrtalamasi) throws GecersizGirisBilgisiException {
        if (notOrtalamasi < 0.0 || notOrtalamasi > 4.0) {
            throw new GecersizGirisBilgisiException("Not ortalaması 0.0 ile 4.0 arasında olmalıdır.");
        }
        this.notOrtalamasi = notOrtalamasi;
    }}