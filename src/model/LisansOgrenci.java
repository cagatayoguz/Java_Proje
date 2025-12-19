package model;

import java.time.LocalDate;

public class LisansOgrenci extends Ogrenci {

    public LisansOgrenci(String ad, String soyad, String tcKimlikNo, LocalDate dogumTarihi,
                         String ogrenciNo, String bolum, int notOrtalamasi, Integer mezuniyetYili) {
        super(ad, soyad, tcKimlikNo, dogumTarihi, ogrenciNo, bolum, notOrtalamasi, mezuniyetYili);
    }

    // Sadece kayıt yaparken kullanılan basit yapıcı
    public LisansOgrenci(String ad, String soyad, String ogrenciNo, String bolum) {
        super(ad, soyad, ogrenciNo, bolum);
    }

    @Override
    public boolean durumKontrol() {
        return false;
    }
}