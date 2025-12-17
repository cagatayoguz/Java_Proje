package service;

import model.Kitap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class KutuphaneServisi {

    private Map<String, Kitap> kitapKatalogu;

    public KutuphaneServisi() {
        this.kitapKatalogu = new HashMap<>();
        baslangicVerisiYukle();
    }

    // Başlangıçta kitap verilerini yükler
    private void baslangicVerisiYukle() {
        try {
            // HATA DÜZELTME: DosyaIslemleri.kitaplariOku() List<String[]> döndürür.
            List<String[]> dosyaVerileri = DosyaIslemleri.kitaplariOku();

            if (dosyaVerileri.isEmpty()) {
                // Eğer dosya boşsa, başlangıç verilerini al
                List<Kitap> baslangicKitaplari = KitapBaslangicVerisi.get50Kitap();

                for (Kitap k : baslangicKitaplari) {
                    // Map'e ekle
                    kitapKatalogu.put(k.getIsbn(), k);

                    // Dosyaya yaz (HATA DÜZELTME: DosyaIslemleri.dosyayaYaz metodu yoktu)
                    // Yıl bilgisi Kitap modelinde olmadığı için varsayılan "-" gönderiyoruz.
                    String durum = k.isMusaitMi() ? "Müsait" : "Oduncte";
                    DosyaIslemleri.kitapEkle(k.getKitapAdi(), k.getYazarAdi(), k.getIsbn(), durum, "-");
                }
            } else {
                // Dosyadan gelen String dizilerini Kitap nesnesine çevir
                for (String[] veri : dosyaVerileri) {
                    // Veri formatı: [Ad, Yazar, ISBN, Durum, Yıl]
                    if (veri.length >= 4) {
                        boolean musaitMi = "Müsait".equals(veri[3]);
                        Kitap k = new Kitap(veri[0], veri[1], veri[2], musaitMi);
                        kitapKatalogu.put(k.getIsbn(), k);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Kitap verileri yuklenemedi: " + e.getMessage());
        }
    }

    public Kitap isbnIleBul(String isbn) {
        return kitapKatalogu.get(isbn);
    }

    public boolean kitapOduncAl(String isbn) {
        Kitap kitap = kitapKatalogu.get(isbn);
        if (kitap != null && kitap.isMusaitMi()) {
            kitap.setMusaitMi(false);
            // Not: Gerçek uygulamada burada dosya güncellemesi de çağrılmalı
            return true;
        }
        return false;
    }

    public List<Kitap> tumKitaplariGetir() {
        return new ArrayList<>(kitapKatalogu.values());
    }

    public List<?> listeyiYazdir(List<?> liste) {
        System.out.println("Gelen liste tipi bilinmiyor, yazdiriliyor.");
        return liste;
    }
}