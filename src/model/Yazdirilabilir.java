package model;

public interface Yazdirilabilir extends Kampus  {
    //Alt sınıflarda kullanılan metodlar
    String detayliRaporOlustur(); // Tüm detayları dondurur
    boolean durumKontrol(); // Müsait/Aktiflik durumunu kontrol eder
    void ciktiAl();
}