package service;

import model.Ogrenci;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap; // HashMap kullanımı (Bölüm 5.2 gereksinimi)
import exception.OgrenciBulunamadiException;

/**
 * Ogrenci varliklarinin yonetimi, arama, ekleme ve silme islemlerini (is mantigi) gerceklestirir.
 * Bu sinif ayni zamanda bir yardimci (utility) sinifidir.
 */
public class OgrenciServisi {

    // Ogrenci listesini tutan Map yapisi (Key: Ogrenci No, Value: Ogrenci nesnesi)
    private Map<String, Ogrenci> ogrenciKatalogu; // Map arayuzu referansi kullanimi (Bölüm 5.2 gereksinimi)

    public OgrenciServisi() {
        this.ogrenciKatalogu = new HashMap<>();
        // Gerçek bir uygulamada, burada DosyaIslemleri ile dosyadan veri okunur.
        // Simdilik Map'e ornek bir ogrenci ekleyelim ki sorgulama calissin:
        ornekVeriEkle();
    }

    // Ornek veri ekleyen (geçici) metot
    private void ornekVeriEkle() {
        // Primitive tip long kullanımı (Bölüm 2.1 gereksinimi)
        long yil = 1999;

        // Birkaç örnek öğrenci ekleyelim
        Ogrenci ogr1 = new Ogrenci("Ahmet", "Yılmaz", "12345678901",
                java.time.LocalDate.of((int)yil, 5, 10),
                "180305011", "Bilgisayar Mühendisliği", 85, null);

        Ogrenci ogr2 = new Ogrenci("Ayşe", "Kaya", "22222222222",
                java.time.LocalDate.of((int)yil, 9, 1),
                "190305005", "Elektrik Mühendisliği", 72, 2024);

        ogrenciKatalogu.put(ogr1.getOgrenciNo(), ogr1); // Ekleme (put) (Bölüm 5.2 gereksinimi)
        ogrenciKatalogu.put(ogr2.getOgrenciNo(), ogr2);
    }

    // Yeni öğrenciyi ekler
    public void ogrenciEkle(Ogrenci ogrenci) {
        ogrenciKatalogu.put(ogrenci.getOgrenciNo(), ogrenci);
        // NOT: Kalıcılık için burada DosyaIslemleri çağrılmalıdır.
    }

    // Öğrenci numarasına göre arama yapar
    public Ogrenci ogrenciNoIleBul(String ogrenciNo) throws OgrenciBulunamadiException {
        // Arama (get) (Collections gereksinimi)
        if (!ogrenciKatalogu.containsKey(ogrenciNo)) {
            // Özel Exception fırlatma (Bölüm 7 gereksinimi)
            throw new OgrenciBulunamadiException(ogrenciNo);
        }
        return ogrenciKatalogu.get(ogrenciNo);
    }

    // Öğrenci silme
    public boolean ogrenciSil(String ogrenciNo) throws OgrenciBulunamadiException {
        if (ogrenciKatalogu.containsKey(ogrenciNo)) {
            // Silme (remove) (Bölüm 5.2 gereksinimi)
            ogrenciKatalogu.remove(ogrenciNo);
            // Dosyaya yazma işlemi
            return true;
        }
        throw new OgrenciBulunamadiException(ogrenciNo);
    }

    // Tüm öğrencileri listeler
    public List<Ogrenci> tumOgrencileriGetir() {
        return new ArrayList<>(ogrenciKatalogu.values());
    }

    // Güncelleme metodu (Öğrenci nesnesini Map'e tekrar ekleyerek güncelleme (replace/put) (Bölüm 5.2 gereksinimi)
    public void ogrenciGuncelle(Ogrenci ogrenci) {
        ogrenciKatalogu.put(ogrenci.getOgrenciNo(), ogrenci);
    }
}
