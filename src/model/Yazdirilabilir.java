package model;

public interface Yazdirilabilir {
    String bilgiRaporuOlustur(); // Temel bilgileri dondurur
    String detayliRaporOlustur(); // Tüm detayları dondurur
    boolean durumKontrol(); // Müsait/Aktiflik durumunu kontrol eder
    void ciktiAl();
}