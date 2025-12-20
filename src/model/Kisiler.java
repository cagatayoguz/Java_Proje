package model;
public abstract class Kisiler implements Yazdirilabilir {

    private String ad;
    private String soyad;

    // SADECE GEREKLİ OLAN CONSTRUCTOR
    public Kisiler(String ad, String soyad) {
        this.ad = ad;
        this.soyad = soyad;
    }

    // Ortak Metotlar
    public String tamAdGetir() {
        return this.ad + " " + this.soyad;
    }

    // Getterlar
    public String getAd() { return ad; }
    public String getSoyad() { return soyad; }

    // --- INTERFACE METOTLARI ---

    // Alt sınıflar (Ogrenci, Akademisyen) bunu kendine göre dolduracak
    public abstract String getPozisyon();

    @Override
    public String bilgiRaporuOlustur() {
        return tamAdGetir();
    }

    // Bu metodu burada tanımlıyoruz, alt sınıflar otomatik sahip oluyor
    @Override
    public void ciktiAl() {
        System.out.println("------------------------------------");
        System.out.println("👤 KİŞİ BİLGİ KARTI");
        System.out.println(this.detayliRaporOlustur());
        System.out.println("------------------------------------");
    }
}