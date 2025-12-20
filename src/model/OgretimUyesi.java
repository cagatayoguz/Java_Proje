package model;

public class OgretimUyesi extends Akademisyen {

    public OgretimUyesi(String ad, String soyad, String unvan, String uzmanlikAlani, String eposta) {
        // Kişiler sınıfı TC ve Doğum Tarihi istiyor, onları varsayılan (dummy) veri geçebiliriz
        // çünkü listede sadece Ad, Unvan, Bölüm lazım.
        super(ad, soyad, unvan, uzmanlikAlani);
        this.eposta = eposta; // E-postayı aşağıda tanımladım
    }

    private String eposta;

    public String getEposta() { return eposta; }
    public void setEposta(String eposta) { this.eposta = eposta; }

    @Override
    public String detayliRaporOlustur() {
        return "";
    }

    @Override
    public boolean durumKontrol() {
        return false;
    }

}