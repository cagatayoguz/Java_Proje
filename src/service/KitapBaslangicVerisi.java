package service;

import model.Kitap;
import java.util.ArrayList;
import java.util.List;

public class KitapBaslangicVerisi {

    public static List<Kitap> get50Kitap() {
        List<Kitap> kitaplar = new ArrayList<>();

        // İlk 15 kitap: Ekran görüntüsündeki örnekler
        kitaplar.add(new Kitap("Suç ve Ceza", "Dostoyevski", "978-605-7798-01-1", true));
        kitaplar.add(new Kitap("Kürk Mantolu Madonna", "Sabahattin Ali", "978-975-07-2720-0", true));
        kitaplar.add(new Kitap("1984", "George Orwell", "978-975-07-3531-1", true));
        kitaplar.add(new Kitap("Sefiller", "Victor Hugo", "978-605-360-951-4", true));
        kitaplar.add(new Kitap("Nutuk", "Mustafa Kemal Atatürk", "978-975-16-1681-3", true));
        kitaplar.add(new Kitap("Tutunamayanlar", "Oğuz Atay", "978-975-07-0683-0", true));
        kitaplar.add(new Kitap("Çalıkuşu", "Reşat Nuri Güntekin", "978-975-10-2187-5", true));
        kitaplar.add(new Kitap("İnce Memed", "Yaşar Kemal", "978-975-08-0775-9", true));
        kitaplar.add(new Kitap("Saatleri Ayarlama Enstitüsü", "Ahmet Hamdi Tanpınar", "978-975-08-0056-9", true));
        kitaplar.add(new Kitap("Medeniyetler Çatışması", "Samuel P. Huntington", "978-605-4764-58-1", true));
        kitaplar.add(new Kitap("Hayvan Çiftliği", "George Orwell", "978-975-07-2723-1", true));
        kitaplar.add(new Kitap("Fahrenheit 451", "Ray Bradbury", "978-975-273-047-9", true));
        kitaplar.add(new Kitap("Cesur Yeni Dünya", "Aldous Huxley", "978-975-07-1604-4", true));
        kitaplar.add(new Kitap("Yüz Yıllık Yalnızlık", "Gabriel García Márquez", "978-975-07-2826-9", true));
        kitaplar.add(new Kitap("Bülbülü Öldürmek", "Harper Lee", "978-975-07-2172-7", true));

        // Kalan kitapları döngü ile ekle (50'ye tamamlamak için)
        // for döngüsü kullanımı (Bölüm 9 gereksinimi)
        for (int i = 16; i <= 50; i++) {
            String isbn = String.format("978-605-12345-%02d", i);
            kitaplar.add(new Kitap("Kitap Adı " + i, "Yazar " + (i % 5), isbn, i % 3 != 0)); // % ile aritmetik operatör kullanımı
        }

        return kitaplar;
    }
}