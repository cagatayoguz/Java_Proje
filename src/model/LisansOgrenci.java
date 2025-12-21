package model;

public class LisansOgrenci extends Ogrenci {

    public LisansOgrenci(String ad, String soyad, String ogrenciNo, String bolum) {
        // Veriyi üst sınıfa (Ogrenci) yollar, kayıt işlemini üst sınıf yapar.
        super(ad, soyad, ogrenciNo, bolum);
    }
}