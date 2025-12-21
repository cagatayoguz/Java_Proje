package model;

import service.DosyaIslemleri;
import java.io.IOException;

public class Kitap implements Kaydedilebilir, Yazdirilabilir {

    private String kitapAdi;
    private String yazar;
    private String isbn;
    private String durum; // "Müsait", "Oduncte" vb.

    //Constructo
    public Kitap(String kitapAdi, String yazar, String isbn, String durum) {
        this.kitapAdi = kitapAdi;
        this.yazar = yazar;
        this.isbn = isbn;
        this.durum = durum;
    }

    //Yeni kitapları dosyaya yazan metod
    @Override
    public boolean kaydet() {
        try {
            // Kitabı servisi kullanarak dosyaya ekler.
            DosyaIslemleri.kitapEkle(this.kitapAdi, this.yazar, this.isbn, this.durum);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Kitapları silen metod
    @Override
    public boolean sil(String id) {
        try {
            // ISBN numarasına göre dosyadan siler.
            DosyaIslemleri.kitapSil(id);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Geliştirilecek metod
    @Override
    public boolean guncelle() {
        if (sil(this.isbn)) {
            return kaydet();
        }
        return false;
    }

    // DİĞER METOTLAR
    @Override
    public String detayliRaporOlustur() {
        return String.format("Kitap: %s | Yazar: %s | ISBN: %s | Durum: %s", kitapAdi, yazar, isbn, durum);
    }

    @Override
    public boolean durumKontrol() {
        return false;
    }

    @Override
    public void ciktiAl() {
        System.out.println(detayliRaporOlustur());
    }

    // Getter & Setter
    public String getKitapAdi() { return kitapAdi; }
    public String getYazar() { return yazar; }
    public String getIsbn() { return isbn; }
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
}