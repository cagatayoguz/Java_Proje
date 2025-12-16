package model;

import java.time.LocalDate;

public abstract class Kisiler implements Raporlanabilir { // Bir interface implemente etti

    private String ad;
    private String soyad;
    private String tcKimlikNo;
    private LocalDate dogumTarihi; // LocalDate kullanımı (Bölüm 6)

    public Kisiler(String ad, String soyad, String tcKimlikNo, LocalDate dogumTarihi) {
        this.ad = ad;
        this.soyad = soyad;
        this.tcKimlikNo = tcKimlikNo;
        this.dogumTarihi = dogumTarihi;
    }

    // Abstract metotlar
    public abstract String getPozisyon();
    public abstract void bilgiSistemiErisim();

    // Somut metotlar
    public String tamAdGetir() {
        return this.ad + " " + this.soyad;
    }

    // Tarih üzerinde basit hesaplama (Bölüm 6 gereksinimi)
    public int yasHesapla() {
        return LocalDate.now().getYear() - this.dogumTarihi.getYear();
    }

    // Getterlar (Encapsulation)
    public String getAd() { return ad; }
    public String getSoyad() { return soyad; }
    // Setterlar (Öğrenci sınıfında detaylıca kontrol edildiği için burada atlandı)

    // Raporlanabilir Interface Metotları
    @Override
    public String bilgiRaporuOlustur() {
        return "Ad: " + tamAdGetir() + ", Pozisyon: " + getPozisyon();
    }
    // Diğer rapor metotları alt sınıflarda override edilecek (Polimorfizm)
}