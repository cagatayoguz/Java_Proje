package service;

import java.io.*;
import java.util.*;
import model.Kitap;
import model.Ogrenci;

public class DosyaIslemleri {

    // Dosya Yolları
    // File.separator, Windows'ta "\" Mac/Linux'ta "/" olmasını sağlar.
    public static final String KITAP_DOSYASI = "veriler" + File.separator + "kitaplar.txt";
    public static final String OGRENCI_DOSYASI = "veriler" + File.separator + "ogrenciler.txt";

    // --- GENEL YAZMA METODU (Kitaplar İçin) ---
    public static <T> void dosyayaYaz(String dosyaYolu, List<T> liste) throws IOException {
        File dosya = new File(dosyaYolu);
        File klasor = dosya.getParentFile();
        if (klasor != null && !klasor.exists()) {
            klasor.mkdirs(); // Klasör yoksa oluştur
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, false))) {
            for (T item : liste) {
                if (item instanceof Kitap) {
                    writer.write(((Kitap) item).toCsvString());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Dosyaya yazma hatasi olustu: " + e.getMessage());
            throw e;
        }
    }

    // --- KİTAP OKUMA METODU ---
    public static List<Kitap> kitaplariOku() throws IOException {
        List<Kitap> kitaplar = new ArrayList<>();
        File dosya = new File(KITAP_DOSYASI);
        if (!dosya.exists()) {
            return kitaplar;
        }

        try (Scanner scanner = new Scanner(dosya)) {
            while (scanner.hasNextLine()) {
                String satir = scanner.nextLine();
                if (satir.trim().isEmpty()) continue;
                String[] veriler = satir.split(",");
                if (veriler.length == 4) {
                    boolean musait = Boolean.parseBoolean(veriler[3]);
                    kitaplar.add(new Kitap(veriler[0], veriler[1], veriler[2], musait));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Kitap dosyasi bulunamadi: " + e.getMessage());
        }
        return kitaplar;
    }

    // ==========================================
    //       YENİ EKLENEN ÖĞRENCİ METOTLARI
    // ==========================================

    // 1. Öğrenci Ekleme
    public static void ogrenciEkle(String ad, String soyad, String no, String bolum, String sinif, String ortalama) throws IOException {
        File dosya = new File(OGRENCI_DOSYASI);

        // Eğer dosya veya klasör yoksa oluşturmayı dene
        if (!dosya.exists()) {
            if (dosya.getParentFile() != null) {
                dosya.getParentFile().mkdirs();
            }
            baslangicVerisiOlustur(); // Dosya yoksa başlangıç verilerini yaz
        }

        // true parametresi dosyanın sonuna ekleme (append) yapılacağını belirtir
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, true))) {
            // CSV formatı: No,Ad,Soyad,Bolum,Sinif,Ortalama
            String satir = String.format("%s,%s,%s,%s,%s,%s", no, ad, soyad, bolum, sinif, ortalama);
            writer.write(satir);
            writer.newLine();
        }
    }

    // 2. Öğrenci Silme
    public static boolean ogrenciSil(String silinecekNo) throws IOException {
        File dosya = new File(OGRENCI_DOSYASI);
        if (!dosya.exists()) return false;

        List<String> satirlar = new ArrayList<>();
        boolean bulundu = false;

        // Dosyayı satır satır oku
        try (Scanner scanner = new Scanner(dosya)) {
            while (scanner.hasNextLine()) {
                String satir = scanner.nextLine();
                // Satırın başındaki numara silinecek numara mı?
                // CSV formatında ilk veri numara olduğu için startsWith kontrolü yeterli
                if (!satir.startsWith(silinecekNo + ",")) {
                    satirlar.add(satir);
                } else {
                    bulundu = true;
                }
            }
        }

        // Dosyayı silinmiş haliyle yeniden yaz
        if (bulundu) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, false))) {
                for (String satir : satirlar) {
                    writer.write(satir);
                    writer.newLine();
                }
            }
        }
        return bulundu;
    }

    // 3. Tüm Öğrencileri Okuma
    public static Map<String, String[]> ogrencileriOku() {
        Map<String, String[]> ogrenciListesi = new HashMap<>();
        File dosya = new File(OGRENCI_DOSYASI);

        if (!dosya.exists()) {
            baslangicVerisiOlustur(); // Dosya yoksa oluştur
        }

        try (Scanner scanner = new Scanner(dosya)) {
            while (scanner.hasNextLine()) {
                String satir = scanner.nextLine();
                if (satir.trim().isEmpty()) continue;

                String[] parcalar = satir.split(",");
                if (parcalar.length >= 6) {
                    // Key: No
                    // Value Dizi: Ad, Soyad, Bolum, Sinif, Ortalama
                    String[] bilgiler = {parcalar[1], parcalar[2], parcalar[3], parcalar[4], parcalar[5]};
                    ogrenciListesi.put(parcalar[0], bilgiler);
                }
            }
        } catch (IOException e) {
            System.err.println("Öğrenci okuma hatası: " + e.getMessage());
        }
        return ogrenciListesi;
    }

    // 4. Başlangıç Verisi Oluşturma (Rastgele 10 Öğrenci)
    private static void baslangicVerisiOlustur() {
        try {
            File dosya = new File(OGRENCI_DOSYASI);
            if (dosya.getParentFile() != null) {
                dosya.getParentFile().mkdirs(); // veriler klasörünü garantiye al
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya))) {
                String[][] ilkVeriler = {
                        {"202100101", "Ahmet", "Yılmaz", "Bilgisayar Müh.", "3. Sınıf", "3.45"},
                        {"202100102", "Ayşe", "Kaya", "Elektrik-Elektronik", "2. Sınıf", "2.90"},
                        {"202200205", "Mehmet", "Demir", "Makine Müh.", "1. Sınıf", "3.10"},
                        {"202000301", "Fatma", "Çelik", "Endüstri Müh.", "4. Sınıf", "3.85"},
                        {"202100155", "Ali", "Şahin", "Bilgisayar Müh.", "3. Sınıf", "2.50"},
                        {"202200402", "Zeynep", "Yıldız", "Mimarlık", "2. Sınıf", "3.20"},
                        {"202300100", "Can", "Öztürk", "İnşaat Müh.", "1. Sınıf", "2.80"},
                        {"202000505", "Elif", "Arslan", "Tıp Fakültesi", "5. Sınıf", "3.95"},
                        {"202100303", "Burak", "Polat", "Hukuk", "3. Sınıf", "3.00"},
                        {"202200606", "Selin", "Koç", "Diş Hekimliği", "2. Sınıf", "3.50"}
                };

                for (String[] ogr : ilkVeriler) {
                    // Virgülle birleştirip yaz
                    writer.write(String.join(",", ogr));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Başlangıç verisi oluşturulamadı: " + e.getMessage());
        }
    }
}