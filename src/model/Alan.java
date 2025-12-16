package model;

import exception.GecersizGirisBilgisiException;

public abstract class Alan {

    private String adi;
    private int kapasite;
    private boolean aktifMi; // Primitive boolean kullanimi

    public Alan(String adi, int kapasite) {
        this.adi = adi;
        this.kapasite = kapasite;
        this.aktifMi = true;
    }

    // Abstract metotlar
    public abstract String kullanimAmaci();
    public abstract boolean kapasiteKontrol(int mevcutKullaniciSayisi);

    // Somut metotlar
    public String getAlanAdi() {
        return this.adi;
    }

    // Aritmetik operatör kullanımı (Bölüm 2.2 gereksinimi)
    public int kalanKapasiteHesapla(int mevcutKullaniciSayisi) {
        return this.kapasite - mevcutKullaniciSayisi;
    }

    // Getterlar ve Setterlar
    public int getKapasite() { return kapasite; }
    public boolean isAktifMi() { return aktifMi; }
    //...
}