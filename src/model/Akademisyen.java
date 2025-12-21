package model;

import service.DosyaIslemleri; // Gelecekte kullanılacak
import java.io.IOException;

public abstract class Akademisyen extends Kisiler {

    private String unvan;
    private String bolum;
    private String posta;

    public Akademisyen(String ad, String soyad, String sicilNo, String unvan, String bolum) {
        super(ad, soyad);
        this.unvan = sicilNo;
        this.bolum = unvan;
        this.posta = bolum;
    }

    // --- INTERFACE (KAYDEDİLEBİLİR) METOTLARI ---

    @Override
    public boolean kaydet() {
        // NOT: DosyaIslemleri sınıfında henüz 'akademisyenEkle' metodu olmadığı için
        // burası şimdilik işlem yapmaz. Servise o metot eklendiğinde buradaki yorumu kaldırabilirsin.
        /*
        try {
            DosyaIslemleri.akademisyenEkle(getAd(), getSoyad(), sicilNo, unvan, bolum);
            return true;
        } catch (IOException e) {
            return false;
        }
        */
        System.out.println("UYARI: Akademisyen kayıt servisi henüz aktif değil.");
        return false;
    }

    @Override
    public boolean sil(String id) {
        // DosyaIslemleri.akademisyenSil(id); eklendiğinde burası aktif edilecek.
        return false;
    }

    @Override
    public boolean guncelle() {
        return false;
    }

    // --- DİĞER METOTLAR ---

    @Override
    public String getPozisyon() {
        return bolum + " - " + posta;
    }

    @Override
    public String detayliRaporOlustur() {
        return String.format("%s %s %s | Sicil: %s", bolum, getAd(), getSoyad(), unvan);
    }

    // Getterlar
    public String getUnvan() { return unvan; }
    public String getBolum() { return bolum; }
    public String getPosta() { return posta; }
}