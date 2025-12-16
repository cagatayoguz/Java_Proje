package model;

public interface Raporlanabilir {
    String bilgiRaporuOlustur(); // Temel bilgileri dondurur
    String detayliRaporOlustur(); // Tüm detayları dondurur
    boolean durumKontrol(); // Müsait/Aktiflik durumunu kontrol eder
}