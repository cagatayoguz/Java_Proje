package service;

import model.Kitap;
import model.KitapBaslangicVerisi;
import java.util.List;
import java.util.ArrayList;

public class KutuphaneServisi {

    // Ödev Gereksinimi: Generic Sınıf Kullanımı (Depo<T>)
    private Depo<Kitap> kitapDeposu;

    public KutuphaneServisi() {
        // Generic depo nesnesini oluşturuyoruz
        this.kitapDeposu = new Depo<>();
        baslangicVerisiYukle();
    }

    private void baslangicVerisiYukle() {
        try {
            // Dosyadan verileri detaylı okuyoruz (Ad, Yazar, ISBN, Durum, Tarih, AlanKişi)
            List<String[]> dosyaVerileri = DosyaIslemleri.kitaplariOkuDetayli();

            if (dosyaVerileri.isEmpty()) {
                // DURUM 1: Dosya boşsa varsayılan 50 kitabı yükle
                // (KitapBaslangicVerisi sınıfının da yeni Kitap yapısına uygun olduğunu varsayıyoruz)
                List<Kitap> baslangicKitaplari = KitapBaslangicVerisi.get50Kitap();

                for (Kitap k : baslangicKitaplari) {
                    // 1. Depoya (RAM) ekle
                    kitapDeposu.ekle(k);

                    // 2. Dosyaya kalıcı olarak kaydet
                    // (Artık k.kaydet() metodu DosyaIslemleri'ni otomatik çağırıyor)
                    k.kaydet();
                }
            } else {
                // DURUM 2: Dosyada veri varsa onları RAM'e (Depo'ya) al
                for (String[] veri : dosyaVerileri) {
                    // En az Ad, Yazar, ISBN olmalı
                    if (veri.length >= 3) {

                        String ad = veri[0];
                        String yazar = veri[1];
                        String isbn = veri[2];

                        // DÜZELTME: Artık karmaşık boolean kontrolüne gerek yok.
                        // Dosyadan gelen 3. indeks zaten durumu (String) tutuyor.
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

    // Ödev Gereksinimi: Listeyi Sıralı Getirme
    public List<Kitap> getSiraliKitapListesi() {
        // Depo içindeki sıralama metodunu tetikliyoruz (Kitap sınıfındaki CompareTo çalışır)
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
            // Kitap sınıfındaki getter metodunun adı 'getYazar' olmalı
            if (k.getYazar().equalsIgnoreCase(yazarAdi)) {
                filtreli.add(k);
            }
        }
        return filtreli;
    }
}