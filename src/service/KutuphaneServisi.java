package service;

import model.Kitap;
import model.KitapBaslangicVerisi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KutuphaneServisi {

    private Map<String, Kitap> kitapKatalogu;

    public KutuphaneServisi() {
        this.kitapKatalogu = new HashMap<>();
        baslangicVerisiYukle();
    }

    private void baslangicVerisiYukle() {
        try {
            List<String[]> dosyaVerileri = DosyaIslemleri.kitaplariOku();

            if (dosyaVerileri.isEmpty()) {
                // Dosya boşsa varsayılanları yükle
                List<Kitap> baslangicKitaplari = KitapBaslangicVerisi.get50Kitap();
                for (Kitap k : baslangicKitaplari) {
                    kitapKatalogu.put(k.getIsbn(), k);
                    // Varsayılan formatta kaydet
                    String durum = k.isMusaitMi() ? "Musait" : "Oduncte";
                    DosyaIslemleri.kitapEkle(k.getKitapAdi(), k.getYazarAdi(), k.getIsbn(), durum);                }
            } else {
                // Dosyadan verileri oku
                for (String[] veri : dosyaVerileri) {
                    // Veri formatı: [Ad, Yazar, ISBN, Durum, ...]
                    if (veri.length >= 4) {

                        // --- KESİN ÇÖZÜM BURASI ---
                        // Kelimeyi küçük harfe çevirip içinde 'usait' veya 'üsait' arıyoruz.
                        // Böylece başında gizli BOM karakteri, boşluk veya harf hatası olsa bile yakalar.
                        String okunanDurum = veri[3].toLowerCase();

                        boolean musaitMi = okunanDurum.contains("usait") || okunanDurum.contains("üsait");

                        // Eğer satırda "Oduncte" veya "odun" geçiyorsa kesinlikle müsait değildir.
                        if (okunanDurum.contains("odun")) {
                            musaitMi = false;
                        }

                        Kitap k = new Kitap(veri[0], veri[1], veri[2], musaitMi);
                        kitapKatalogu.put(k.getIsbn(), k);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Veri yukleme hatasi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Kitap isbnIleBul(String isbn) {
        return kitapKatalogu.get(isbn);
    }

    public boolean kitapOduncAl(String isbn) {
        Kitap kitap = kitapKatalogu.get(isbn);
        if (kitap != null && kitap.isMusaitMi()) {
            kitap.setMusaitMi(false);
            return true;
        }
        return false;
    }

    public List<Kitap> tumKitaplariGetir() {
        return new ArrayList<>(kitapKatalogu.values());
    }
}