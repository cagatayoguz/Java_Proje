package service;

import model.Kitap;
import model.KitapBaslangicVerisi;
import java.util.List;
import java.util.ArrayList;

public class KutuphaneServisi {

    // Generic Sınıf
    private Depo<Kitap> kitapDeposu;

    public KutuphaneServisi() {
        // Generic depo nesnesini oluşturuyoruz
        this.kitapDeposu = new Depo<>();
        baslangicVerisiYukle();
    }

    private void baslangicVerisiYukle() {
        try {
            // Dosyadan verileri detaylı okuyoruz
            List<String[]> dosyaVerileri = DosyaIslemleri.kitaplariOkuDetayli();

            if (dosyaVerileri.isEmpty()) {
                // Dosya boşsa varsayılan 50 kitabı yükle
                List<Kitap> baslangicKitaplari = KitapBaslangicVerisi.get50Kitap();

                for (Kitap k : baslangicKitaplari) {
                    // Depoya ekle
                    kitapDeposu.ekle(k);

                    // Dosyaya kalıcı olarak kaydet
                    k.kaydet();
                }
            } else {
                // Dosyada veri varsa onları Depo'ya al
                for (String[] veri : dosyaVerileri) {
                    // En az Ad, Yazar, ISBN olmalı
                    if (veri.length >= 3) {

                        String ad = veri[0];
                        String yazar = veri[1];
                        String isbn = veri[2];

                        // Eğer veri eksikse varsayılan olarak "Müsait" atıyoruz.
                        String durum = (veri.length > 3) ? veri[3] : "Müsait";

                        // Yeni Kitap Constructor yapısına uygun nesne oluşturma
                        Kitap k = new Kitap(ad, yazar, isbn, durum);

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

    // Listeyi Sıralı Getirme
    public List<Kitap> getSiraliKitapListesi() {
        // Depo içindeki sıralama metodunu tetikliyoruz
        kitapDeposu.ismeGoreSirala();
        return kitapDeposu.getListe();
    }

    // Sıralama olmadan direkt listeyi verir
    public List<Kitap> tumKitaplariGetir() {
        return kitapDeposu.getListe();
    }

    // Yazar adına göre filtreleme
    public List<Kitap> tumKitaplariGetir(String yazarAdi) {
        List<Kitap> tumu = kitapDeposu.getListe();
        List<Kitap> filtreli = new ArrayList<>();

        for (Kitap k : tumu) {
            if (k.getYazar().equalsIgnoreCase(yazarAdi)) {
                filtreli.add(k);
            }
        }
        return filtreli;
    }
}