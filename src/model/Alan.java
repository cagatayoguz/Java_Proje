package model;

public abstract class Alan {
    private String adi;
    private int kapasite;

    public Alan(String adi, int kapasite) {
        this.adi = adi;
        this.kapasite = kapasite;
    }

    public abstract String kullanimAmaci();
    public abstract boolean kapasiteKontrol(int mevcutKullaniciSayisi);

    public int getKapasite() { return kapasite; }
    public String getAlanAdi() { return adi; }

    public int kalanKapasiteHesapla(int mevcutKullaniciSayisi) {
        return this.kapasite - mevcutKullaniciSayisi;
    }
}