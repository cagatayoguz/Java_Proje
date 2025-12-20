package service;

import model.Kitap;
import model.KitapBaslangicVerisi;
import java.util.List;
import java.util.ArrayList;

public class KutuphaneServisi {

    // Ödev Gereksinimi: Generic Sınıf Kullanımı (Depo<T>)
    private Depo<Kitap> kitapDeposu;

    public KutuphaneServisi() {
        // Depo nesnesini oluşturuyoruz
        this.kitapDeposu = new Depo<>();
        baslangicVerisiYukle();
    }

    private void baslangicVerisiYukle() {
        try {
            // Dosyadan verileri detaylı okuyoruz (6 sütunlu yapı için)
            List<String[]> dosyaVerileri = DosyaIslemleri.kitaplariOkuDetayli();

            if (dosyaVerileri.isEmpty()) {
                // DURUM 1: Dosya boşsa varsayılan 50 kitabı yükle
                List<Kitap> baslangicKitaplari = KitapBaslangicVerisi.get50Kitap();

                for (Kitap k : baslangicKitaplari) {
                    // 1. Depoya (RAM) ekle
                    kitapDeposu.ekle(k);

                    // 2. Dosyaya kalıcı olarak kaydet (Interface metodu üzerinden)
                    k.kaydet();
                }
            } else {
                // DURUM 2: Dosyada veri varsa onları RAM'e (Depo'ya) al
                for (String[] veri : dosyaVerileri) {
                    if (veri.length >= 3) { // En az Ad, Yazar, ISBN olmalı

                        // --- KESİN ÇÖZÜM (Karakter Hatası Kontrolü) ---
                        String okunanDurum = (veri.length > 3) ? veri[3].toLowerCase() : "musait";

                        // İçinde "usait" veya "üsait" geçiyorsa Müsait kabul et
                        boolean musaitMi = okunanDurum.contains("usait") || okunanDurum.contains("üsait");

                        // Ancak "odun" veya "odün" geçiyorsa kesinlikle Müsait değildir
                        if (okunanDurum.contains("odun") || okunanDurum.contains("ödün")) {
                            musaitMi = false;
                        }

                        // Kitap nesnesini oluştur
                        Kitap k = new Kitap(veri[0], veri[1], veri[2], musaitMi);

                        // Generic Depo'ya ekle
                        kitapDeposu.ekle(k);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Veri yükleme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Ödev Gereksinimi: Listeyi Sıralı Getirme (Collections.sort)
    public List<Kitap> getSiraliKitapListesi() {
        // Depo içindeki sıralama metodunu tetikliyoruz
        kitapDeposu.ismeGoreSirala();

        // Sıralanmış listeyi döndürüyoruz
        return kitapDeposu.getListe();
    }

    // Eski kodlarla uyumluluk için (Sıralı olmayan veya direkt liste)
    public List<Kitap> tumKitaplariGetir() {
        return kitapDeposu.getListe();
    }
    public List<Kitap> tumKitaplariGetir(String yazarAdi) {
        List<Kitap> tumu = kitapDeposu.getListe();
        List<Kitap> filtreli = new ArrayList<>();

        for (Kitap k : tumu) {
            if (k.getYazarAdi().equalsIgnoreCase(yazarAdi)) {
                filtreli.add(k);
            }
        }
        return filtreli;
    }

}