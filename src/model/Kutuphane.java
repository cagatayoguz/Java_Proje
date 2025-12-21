package model;

import java.util.ArrayList;
import java.util.List;
import service.DosyaIslemleri; // Dosya işlemlerini bağladık
import exception.GecersizGirisBilgisiException;
import java.io.IOException;

public class Kutuphane extends Alan {

    private List<Kitap> kitapListesi;

    // Singleton veya Tekil kullanım için constructor
    public Kutuphane(String adi) {
        super(adi, 50); // Kapasite: 50
        this.kitapListesi = new ArrayList<>();
    }

    // --- MANTIKSAL METOTLAR ---

    public void kitapEkle(Kitap kitap) throws GecersizGirisBilgisiException {
        // 1. Kapasite Kontrolü
        // Dosyadaki mevcut kitap sayısını da kontrol edebiliriz
        int mevcutDosyaSayisi = DosyaIslemleri.kitaplariOkuDetayli().size();

        if (!kapasiteKontrol(mevcutDosyaSayisi)) {
            throw new GecersizGirisBilgisiException(
                    "Kütüphane doldu! (Kapasite: " + getKapasite() + ")\n" +
                            "Yeni kitap ekleyemezsiniz."
            );
        }

        // 2. ISBN (Mükerrer Kayıt) Kontrolü
        // Bellekteki liste boş olabilir, o yüzden dosyadan kontrol etmek daha garantidir.
        List<String[]> dosyadakiKitaplar = DosyaIslemleri.kitaplariOkuDetayli();
        for (String[] satir : dosyadakiKitaplar) {
            // satir[2] ISBN sütunudur
            if (satir.length > 2 && satir[2].equals(kitap.getIsbn())) {
                throw new GecersizGirisBilgisiException("Bu ISBN numaralı kitap zaten kütüphanede var!");
            }
        }

        // Kontroller geçildiyse listeye ekle
        kitapListesi.add(kitap);
    }

    // --- INTERFACE UYGULAMALARI ---

    @Override
    public boolean kaydet() {
        // Bu sınıfın 'kaydet' metodu, içindeki bekleyen kitapları dosyaya yazar.
        boolean basari = true;
        for (Kitap k : kitapListesi) {
            // Kitap sınıfının kendi kaydet metodunu çağırıyoruz
            if (!k.kaydet()) {
                basari = false;
            }
        }
        // Kayıt bitince listeyi temizleyebiliriz (tekrar eklenmesin diye)
        if(basari) kitapListesi.clear();
        return basari;
    }

    @Override
    public boolean sil(String isbn) {
        // Kütüphaneden kitap silme işlemi
        try {
            DosyaIslemleri.kitapSil(isbn);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- DİĞER METOTLAR ---

    @Override
    public boolean kapasiteKontrol(int mevcutSayi) {
        return mevcutSayi < getKapasite();
    }

    @Override
    public String detayliRaporOlustur() {
        return "Kütüphane: " + getKapasite() + " | Doluluk: " + mevcutKitapSayisi() + "/" + getKapasite();
    }

    // İşlevsiz metotları boş bırakabiliriz
    @Override public boolean guncelle() { return false; }
    @Override public boolean durumKontrol() { return true; }
    @Override public void ciktiAl() { System.out.println(detayliRaporOlustur()); }

    // Yardımcılar
    public void mevcutKitaplariYukle(List<Kitap> kitaplar) {
        this.kitapListesi.clear();
        this.kitapListesi.addAll(kitaplar);
    }

    public int mevcutKitapSayisi() {
        return DosyaIslemleri.kitaplariOkuDetayli().size();
    }
}