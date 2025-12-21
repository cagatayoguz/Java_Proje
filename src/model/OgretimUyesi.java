package model;

public class OgretimUyesi extends Akademisyen {

    public OgretimUyesi(String ad, String soyad, String sicilNo, String unvan, String bolum) {
        super(ad, soyad, sicilNo, unvan, bolum);
    }

    @Override
    public boolean durumKontrol() {
        return false;
    }

    @Override
    public void ciktiAl() {
        System.out.println("Öğretim Üyesi: " + detayliRaporOlustur());
    }
}