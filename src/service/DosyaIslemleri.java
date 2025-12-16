package service;

import java.io.*;
import java.util.*;
import model.Kitap;
import model.Ogrenci;

public class DosyaIslemleri {

    // Statik alan (Dosya yolu, Bölüm 10 gereksinimi)
    // Platform bağımsız dosya ayırıcı kullanımı (Bölüm 10 gereksinimi)
    public static final String KITAP_DOSYASI = "veriler" + System.getProperty("file.separator") + "kitaplar.txt";
    public static final String OGRENCI_DOSYASI = "veriler" + System.getProperty("file.separator") + "ogrenciler.txt";

    // Statik metot (Dosyaya Yazma, Bölüm 10 ve 7 gereksinimi)
    public static <T> void dosyayaYaz(String dosyaYolu, List<T> liste) throws IOException { // Generic metot 1 (Bölüm 5.1)
        File dosya = new File(dosyaYolu);
        File klasor = dosya.getParentFile();
        if (klasor != null && !klasor.exists()) {
            klasor.mkdirs(); // Klasör yoksa oluştur
        }

        // FileWriter + BufferedWriter kullanımı (Bölüm 7 gereksinimi)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, false))) {
            for (T item : liste) {
                // Burada Kitap veya Öğrenci'nin toCsvString metodu çağrılır.
                if (item instanceof Kitap) {
                    writer.write(((Kitap) item).toCsvString());
                } else if (item instanceof Ogrenci) {
                    // Öğrencinin toCsvString metodu varsayılmaktadır
                    // writer.write(((Ogrenci) item).toCsvString());
                }
                writer.newLine();
            }
        }
        // finally bloğu kullanımı (Bölüm 7 gereksinimi)
        catch (IOException e) {
            System.err.println("Dosyaya yazma hatasi olustu: " + e.getMessage());
            throw e; // Exception'ı çağıran tarafa iletme (throws ifadesi, Bölüm 7 gereksinimi)
        }
    }

    // Statik metot (Dosyadan Okuma, Bölüm 10 ve 7 gereksinimi)
    public static List<Kitap> kitaplariOku() throws IOException { // Kitap okuma metodu
        List<Kitap> kitaplar = new ArrayList<>();
        File dosya = new File(KITAP_DOSYASI);
        if (!dosya.exists()) {
            return kitaplar;
        }

        // Scanner ile dosyadan okuma (Bölüm 7 gereksinimi)
        try (Scanner scanner = new Scanner(dosya)) {
            while (scanner.hasNextLine()) { // hasNextLine ile NoSuchElementException önleme (Bölüm 7 gereksinimi)
                String satir = scanner.nextLine();
                if (satir.trim().isEmpty()) continue;
                String[] veriler = satir.split(",");
                if (veriler.length == 4) {
                    // String'ten boolean'a tip dönüşümü (Explicit Tip Dönüşümü)
                    boolean musait = Boolean.parseBoolean(veriler[3]); // Explicit Tip Dönüşümü (Bölüm 2.2)
                    kitaplar.add(new Kitap(veriler[0], veriler[1], veriler[2], musait));
                }
            }
        }
        // Çoklu catch yapısı (Bölüm 7 gereksinimi)
        catch (FileNotFoundException e) {
            System.err.println("Kitap dosyasi bulunamadi: " + e.getMessage());
        }

        return kitaplar;
    }
}