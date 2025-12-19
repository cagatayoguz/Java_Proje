package model;

public class LisansOgrenci extends Ogrenci {

    // Uzun ve kullanılmayan constructor SİLİNDİ.
    // Gereksiz importlar (LocalDate vb.) TEMİZLENDİ.

    // TEK VE GEREKLİ CONSTRUCTOR
    // GUI'den gelen verileri alıp üst sınıf olan Ogrenci'ye iletir.
    public LisansOgrenci(String ad, String soyad, String ogrenciNo, String bolum) {
        super(ad, soyad, ogrenciNo, bolum);
    }

    // Raporlanabilir Interface'inden gelen metot
    @Override
    public boolean durumKontrol() {
        return true; // Öğrenci sistemde aktif kabul edilir
    }
}