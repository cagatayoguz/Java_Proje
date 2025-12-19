package model;

import java.time.LocalDate;

public class LisansOgrenci extends Ogrenci {

    // Abstract sınıfın yapıcısını (constructor) çağırıyoruz
    public LisansOgrenci(String ad, String soyad, String ogrenciNo, String bolum) {
        super(ad, soyad, ogrenciNo, bolum);
    }

    // İstersen uzun yapıcıyı da ekleyebilirsin
    public LisansOgrenci(String ad, String soyad, String tcKimlikNo, LocalDate dogumTarihi,
                         String ogrenciNo, String bolum, int notOrtalamasi, Integer mezuniyetYili) {
        super(ad, soyad, tcKimlikNo, dogumTarihi, ogrenciNo, bolum, notOrtalamasi, mezuniyetYili);
    }

    @Override
    public boolean durumKontrol() {
        return false;
    }
}