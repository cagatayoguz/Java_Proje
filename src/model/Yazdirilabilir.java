package model;

public interface Yazdirilabilir extends Kampus  {
    //Alt sınıflarda kullanılan metodlar
    // Tüm detayları dondurur
    String detayliRaporOlustur();
    // Müsait/Aktiflik durumunu kontrol eder
    boolean durumKontrol();
    //Terminale istediğimiz bilgileri yazar
    void ciktiAl();
}