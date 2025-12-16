package model;

public class Kutuphane extends Alan {

    public Kutuphane(String adi, int kapasite) {
        super(adi, kapasite);
    }

    @Override
    public String kullanimAmaci() {
        return "Ogrencilerin ve personelin arastirma ve okuma ihtiyaclarini karsilamak.";
    }

    @Override
    public boolean kapasiteKontrol(int mevcutKullaniciSayisi) {
        return mevcutKullaniciSayisi < getKapasite();
    }
}