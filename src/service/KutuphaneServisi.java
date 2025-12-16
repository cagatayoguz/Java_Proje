package service;

import model.Kitap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap; // HashMap kullanımı (Bölüm 5.2)

public class KutuphaneServisi {

    // Kitapları depolamak için HashMap kullanımı (Bölüm 5.2)
    // Key: ISBN, Value: Kitap nesnesi
    private Map<String, Kitap> kitapKatalogu; // Map arayüzü referansı (Bölüm 5.2)

    public KutuphaneServisi() {
        this.kitapKatalogu = new HashMap<>();
        baslangicVerisiYukle();
    }

    // Başlangıçta 50 kitap verisini oluşturur ve dosyadan okur.
    private void baslangicVerisiYukle() {
        // Dosyadan oku ve Map'e ekle (I/O ve Ekleme gereksinimi)
        try {
            List<Kitap> dosyaKitaplari = DosyaIslemleri.kitaplariOku();
            if (dosyaKitaplari.isEmpty()) {
                // Eğer dosya boşsa, 50 adet başlangıç verisini oluştururuz
                dosyaKitaplari.addAll(KitapBaslangicVerisi.get50Kitap()); // Bu sınıfı sonra tanımlayacağız
                DosyaIslemleri.dosyayaYaz(DosyaIslemleri.KITAP_DOSYASI, dosyaKitaplari); // Dosyaya yazma
            }

            for (Kitap k : dosyaKitaplari) {
                kitapKatalogu.put(k.getIsbn(), k); // Map'e ekleme (put) (Bölüm 5.2)
            }

        } catch (Exception e) {
            System.err.println("Kitap verileri yuklenemedi: " + e.getMessage());
        }
    }

    // Arama (contains/get) (Bölüm 5.2)
    public Kitap isbnIleBul(String isbn) {
        return kitapKatalogu.get(isbn);
    }

    public boolean kitapOduncAl(String isbn) {
        Kitap kitap = kitapKatalogu.get(isbn);
        if (kitap != null && kitap.isMusaitMi()) {
            kitap.setMusaitMi(false); // Güncelleme (Bölüm 5.2)
            // Dosyayı güncelleme çağrısı burada yapılmalı
            return true;
        }
        return false;
    }

    // ... (Diğer metotlar: kitapIadeEt, kitapEkle, kitapSil)

    public List<Kitap> tumKitaplariGetir() {
        return new ArrayList<>(kitapKatalogu.values()); // Map'ten değerleri List'e atama
    }

    // Wildcard kullanımı (Bonus: Bölüm 5.1 gereksinimi)
    public List<?> listeyiYazdir(List<?> liste) {
        System.out.println("Gelen liste tipi bilinmiyor, yazdiriliyor."); // List<?>
        return liste;
    }
}