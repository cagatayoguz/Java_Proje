package model;

import exception.GecersizGirisBilgisiException;

public abstract class Kisiler implements Yazdirilabilir,Kaydedilebilir {

    private String ad;
    private String soyad;

    // SADECE GEREKLİ OLAN CONSTRUCTOR
    public Kisiler(String ad, String soyad) {
        this.ad = ad;
        this.soyad = soyad;
    }

    // Ortak Metotlar
    public String tamAdGetir() {
        return this.ad + " " + this.soyad;
    }

    // Getterlar
    public String getAd() { return ad; }
    public String getSoyad() { return soyad; }

    // --- INTERFACE METOTLARI ---

    // Alt sınıflar (Ogrenci, Akademisyen) bunu kendine göre dolduracak
    public abstract String getPozisyon();

    public void setAd(String ad) throws GecersizGirisBilgisiException {
        if (ad == null || ad.trim().length() < 2) {
            throw new GecersizGirisBilgisiException("Ad en az 2 karakter olmalı ve boş bırakılmamalıdır.");
        }
        // İsimde rakam kontrolü (Basit regex)
        if (ad.matches(".*\\d.*")) {
            throw new GecersizGirisBilgisiException("Ad alanı rakam içeremez.");
        }
        this.ad = ad;
    }

    public void setSoyad(String soyad) throws GecersizGirisBilgisiException {
        if (soyad == null || soyad.trim().isEmpty()) {
            throw new GecersizGirisBilgisiException("Soyad alanı boş bırakılamaz.");
        }
        this.soyad = soyad;
    }

    // Bu metodu burada tanımlıyoruz, alt sınıflar otomatik sahip oluyor
    @Override
    public void ciktiAl() {
        System.out.println("------------------------------------");
        System.out.println("👤 KİŞİ BİLGİ KARTI");
        System.out.print(getPozisyon()+" ");
        System.out.print(getAd()+" ");
        System.out.print(getSoyad());
        System.out.println(this.detayliRaporOlustur());
        System.out.println("------------------------------------");
    }
}