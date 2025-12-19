package model;

import java.time.LocalDate;

public class LisansOgrenci extends Ogrenci {

    // Kısmi Yapıcı (GUI'den gelen veriler için)
    public LisansOgrenci(String ad, String soyad, String ogrenciNo, String bolum) {
        // Abstract sınıfın yapıcısını çağırıyoruz
        super(ad, soyad, ogrenciNo, bolum);
    }

    // Tam Yapıcı (İhtiyaç olursa diye)
    public LisansOgrenci(String ad, String soyad, String tcKimlikNo, LocalDate dogumTarihi,
                         String ogrenciNo, String bolum, int notOrtalamasi, Integer mezuniyetYili) {
        super(ad, soyad, tcKimlikNo, dogumTarihi, ogrenciNo, bolum, notOrtalamasi, mezuniyetYili);
    }

    @Override
    public boolean durumKontrol() {
        return false;
    }
}