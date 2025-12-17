package service;

import model.Kitap;
import java.io.*;
import java.util.*;

public class DosyaIslemleri {

    // --- DOSYA YOLLARI ---
    public static final String KITAP_DOSYASI = "veriler" + File.separator + "kitaplar.txt";
    public static final String OGRENCI_DOSYASI = "veriler" + File.separator + "ogrenciler.txt";
    public static final String DUYURU_DOSYASI = "veriler" + File.separator + "duyurular.txt";
    public static final String SPOR_DOSYASI = "veriler" + File.separator + "spor_uyelikleri.txt";

    // ==========================================
    //       1. GENEL YAZMA METODU
    // ==========================================
    public static <T> void dosyayaYaz(String dosyaYolu, List<T> liste) throws IOException {
        File dosya = new File(dosyaYolu);
        if (dosya.getParentFile() != null && !dosya.getParentFile().exists()) {
            dosya.getParentFile().mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, false))) {
            for (T item : liste) {
                if (item instanceof Kitap) {
                    Kitap k = (Kitap) item;
                    String durum = k.isMusaitMi() ? "Musait" : "Oduncte";
                    String satir = String.format("%s,%s,%s,%s,%s,%s",
                            k.getKitapAdi(), k.getYazarAdi(), k.getIsbn(), durum, "-", "-");
                    writer.write(satir);
                } else {
                    writer.write(item.toString());
                }
                writer.newLine();
            }
        }
    }

    // ==========================================
    //            2. ÖĞRENCİ İŞLEMLERİ
    // ==========================================
    public static void ogrenciEkle(String ad, String soyad, String no, String bolum, String sinif, String ortalama) throws IOException {
        File dosya = new File(OGRENCI_DOSYASI);
        if (!dosya.exists()) {
            if (dosya.getParentFile() != null) dosya.getParentFile().mkdirs();
            ogrenciBaslangicVerisiOlustur();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, true))) {
            String satir = String.format("%s,%s,%s,%s,%s,%s", no, ad, soyad, bolum, sinif, ortalama);
            writer.write(satir);
            writer.newLine();
        }
    }

    public static boolean ogrenciSil(String silinecekNo) throws IOException {
        File dosya = new File(OGRENCI_DOSYASI);
        if (!dosya.exists()) return false;
        List<String> satirlar = new ArrayList<>();
        boolean bulundu = false;
        try (Scanner scanner = new Scanner(dosya)) {
            while (scanner.hasNextLine()) {
                String satir = scanner.nextLine();
                if (!satir.startsWith(silinecekNo + ",")) {
                    satirlar.add(satir);
                } else {
                    bulundu = true;
                }
            }
        }
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

    public static Map<String, String[]> ogrencileriOku() {
        Map<String, String[]> ogrenciListesi = new HashMap<>();
        File dosya = new File(OGRENCI_DOSYASI);
        if (!dosya.exists()) ogrenciBaslangicVerisiOlustur();
        try (Scanner scanner = new Scanner(dosya)) {
            while (scanner.hasNextLine()) {
                String satir = scanner.nextLine();
                if (satir.trim().isEmpty()) continue;
                String[] parcalar = satir.split(",");
                if (parcalar.length >= 6) {
                    String[] bilgiler = {parcalar[1], parcalar[2], parcalar[3], parcalar[4], parcalar[5]};
                    ogrenciListesi.put(parcalar[0], bilgiler);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return ogrenciListesi;
    }

    private static void ogrenciBaslangicVerisiOlustur() {
        try {
            File dosya = new File(OGRENCI_DOSYASI);
            if (dosya.getParentFile() != null) dosya.getParentFile().mkdirs();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya))) {
                String[][] ilkVeriler = {
                        {"202100101", "Ahmet", "Yılmaz", "Bilgisayar Müh.", "3. Sınıf", "3.45"},
                        {"202100102", "Ayşe", "Kaya", "Elektrik-Elektronik", "2. Sınıf", "2.90"}
                };
                for (String[] ogr : ilkVeriler) {
                    writer.write(String.join(",", ogr));
                    writer.newLine();
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // ==========================================
    //            3. DUYURU İŞLEMLERİ
    // ==========================================
    public static void duyuruEkle(String tarih, String konu, String icerik) throws IOException {
        File dosya = new File(DUYURU_DOSYASI);
        if (!dosya.exists()) {
            if (dosya.getParentFile() != null) dosya.getParentFile().mkdirs();
            duyuruBaslangicVerisiOlustur();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, true))) {
            writer.write(tarih + "|" + konu + "|" + icerik);
            writer.newLine();
        }
    }

    public static boolean duyuruSil(String silinecekKonu) throws IOException {
        File dosya = new File(DUYURU_DOSYASI);
        if (!dosya.exists()) return false;
        List<String> satirlar = new ArrayList<>();
        boolean bulundu = false;
        try (Scanner scanner = new Scanner(dosya)) {
            while (scanner.hasNextLine()) {
                String satir = scanner.nextLine();
                String[] parcalar = satir.split("\\|");
                if (parcalar.length >= 2 && parcalar[1].equals(silinecekKonu)) {
                    bulundu = true;
                } else {
                    satirlar.add(satir);
                }
            }
        }
        if (bulundu) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, false))) {
                for (String s : satirlar) {
                    writer.write(s);
                    writer.newLine();
                }
            }
        }
        return bulundu;
    }

    public static List<String[]> duyurulariOku() {
        List<String[]> duyurular = new ArrayList<>();
        File dosya = new File(DUYURU_DOSYASI);
        if (!dosya.exists()) duyuruBaslangicVerisiOlustur();
        try (Scanner scanner = new Scanner(dosya)) {
            while (scanner.hasNextLine()) {
                String satir = scanner.nextLine();
                if (satir.trim().isEmpty()) continue;
                String[] parcalar = satir.split("\\|");
                if (parcalar.length >= 3) duyurular.add(parcalar);
            }
        } catch (IOException e) { e.printStackTrace(); }
        Collections.reverse(duyurular);
        return duyurular;
    }

    private static void duyuruBaslangicVerisiOlustur() {
        try {
            File dosya = new File(DUYURU_DOSYASI);
            if (dosya.getParentFile() != null) dosya.getParentFile().mkdirs();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya))) {
                writer.write("01.01.2024|Sistem Açılışı|Otomasyon sistemi kullanıma açılmıştır.");
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // ==========================================
    //            4. KİTAP İŞLEMLERİ
    // ==========================================
    public static void kitapEkle(String ad, String yazar, String isbn) throws IOException {
        File dosya = new File(KITAP_DOSYASI);
        if (!dosya.exists()) {
            if (dosya.getParentFile() != null) dosya.getParentFile().mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, true))) {
            String satir = String.format("%s,%s,%s,%s,%s,%s", ad, yazar, isbn, "Musait", "-", "-");
            writer.write(satir);
            writer.newLine();
        }
    }

    public static boolean kitapSil(String silinecekIsbn) throws IOException {
        List<String[]> liste = kitaplariOkuDetayli();
        boolean bulundu = false;
        File dosya = new File(KITAP_DOSYASI);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, false))) {
            for (String[] k : liste) {
                if (k[2].equals(silinecekIsbn)) {
                    bulundu = true;
                } else {
                    writer.write(String.join(",", k));
                    writer.newLine();
                }
            }
        }
        return bulundu;
    }

    public static List<String[]> kitaplariOkuDetayli() {
        List<String[]> kitaplar = new ArrayList<>();
        File dosya = new File(KITAP_DOSYASI);
        if (!dosya.exists()) return kitaplar;
        try (Scanner scanner = new Scanner(dosya)) {
            while (scanner.hasNextLine()) {
                String satir = scanner.nextLine();
                if (satir.trim().isEmpty()) continue;
                String[] veriler = satir.split(",");
                if (veriler.length < 6) {
                    String[] yeniVeri = {veriler[0], veriler[1], veriler[2], "Musait", "-", "-"};
                    kitaplar.add(yeniVeri);
                } else {
                    kitaplar.add(veriler);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return kitaplar;
    }

    public static List<Kitap> kitaplariOku() throws IOException {
        List<Kitap> liste = new ArrayList<>();
        List<String[]> detayli = kitaplariOkuDetayli();
        for(String[] s : detayli) {
            boolean musait = s[3].equals("Musait");
            liste.add(new Kitap(s[0], s[1], s[2], musait));
        }
        return liste;
    }

    public static void kitapTalepEt(String isbn, String ogrenciAd) throws IOException {
        kitapGuncelle(isbn, "Bekliyor", "-", ogrenciAd);
    }

    public static void kitapOnayla(String isbn) throws IOException {
        String iadeTarihi = java.time.LocalDate.now().plusWeeks(2)
                .format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String talepEden = talepEdeniBul(isbn);
        kitapGuncelle(isbn, "Oduncte", iadeTarihi, talepEden);
    }

    public static void kitapIadeEt(String isbn) throws IOException {
        kitapGuncelle(isbn, "Musait", "-", "-");
    }

    private static void kitapGuncelle(String hedefIsbn, String yeniDurum, String yeniTarih, String yeniKisi) throws IOException {
        List<String[]> tumKitaplar = kitaplariOkuDetayli();
        File dosya = new File(KITAP_DOSYASI);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, false))) {
            for (String[] k : tumKitaplar) {
                if (k[2].equals(hedefIsbn)) {
                    writer.write(String.format("%s,%s,%s,%s,%s,%s", k[0], k[1], k[2], yeniDurum, yeniTarih, yeniKisi));
                } else {
                    writer.write(String.join(",", k));
                }
                writer.newLine();
            }
        }
    }

    private static String talepEdeniBul(String isbn) {
        List<String[]> liste = kitaplariOkuDetayli();
        for(String[] k : liste) {
            if(k[2].equals(isbn)) return k[5];
        }
        return "-";
    }

    // ==========================================
    //       5. SPOR SALONU İŞLEMLERİ (YENİ)
    // ==========================================

    // Üyelik Talep Et (Dosyaya Kaydet)
    public static void sporUyelikTalepEt(String adSoyad, String ogrenciNo, String uyelikTipi, String ucret) throws IOException {
        File dosya = new File(SPOR_DOSYASI);
        if (!dosya.exists()) {
            if (dosya.getParentFile() != null) dosya.getParentFile().mkdirs();
        }
        // Format: AdSoyad,No,Tip,Ucret,Durum
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, true))) {
            String satir = String.format("%s,%s,%s,%s,%s", adSoyad, ogrenciNo, uyelikTipi, ucret, "Bekliyor");
            writer.write(satir);
            writer.newLine();
        }
    }

    // Üyelikleri Oku
    public static List<String[]> sporUyelikleriOku() {
        List<String[]> uyelikler = new ArrayList<>();
        File dosya = new File(SPOR_DOSYASI);
        if (!dosya.exists()) return uyelikler;

        try (Scanner scanner = new Scanner(dosya)) {
            while (scanner.hasNextLine()) {
                String satir = scanner.nextLine();
                if (satir.trim().isEmpty()) continue;
                String[] veriler = satir.split(",");
                if (veriler.length >= 5) {
                    uyelikler.add(veriler);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return uyelikler;
    }

    // Üyelik Onayla/Reddet
    public static void sporUyelikGuncelle(String ogrenciNo, boolean onaylandi) throws IOException {
        List<String[]> liste = sporUyelikleriOku();
        File dosya = new File(SPOR_DOSYASI);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, false))) {
            for (String[] u : liste) {
                // Öğrenci numarasına göre buluyoruz
                if (u[1].equals(ogrenciNo)) {
                    if (onaylandi) {
                        // Durumu Aktif Yap
                        writer.write(String.format("%s,%s,%s,%s,%s", u[0], u[1], u[2], u[3], "Aktif"));
                    } else {
                        // Reddedildiyse dosyaya yazmıyoruz (SİLİYORUZ) veya "Reddedildi" yazabiliriz.
                        // Temiz olması için siliyoruz:
                        continue;
                    }
                } else {
                    writer.write(String.join(",", u));
                }
                writer.newLine();
            }
        }
    }
}