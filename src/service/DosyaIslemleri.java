package service;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DosyaIslemleri {

    private static final String OGRENCI_DOSYASI = "veriler/ogrenciler.txt";
    private static final String KITAP_DOSYASI = "veriler/kitaplar.txt";
    private static final String SPOR_DOSYASI = "veriler/spor_uyelikleri.txt";
    private static final String DUYURU_DOSYASI = "veriler/duyurular.txt";

    // --- ÖĞRENCİ İŞLEMLERİ (AYNEN KORUNDU) ---
    public static void ogrenciEkle(String ad, String soyad, String bolum, String no, String sinif, String ort) throws IOException {
        File file = new File(OGRENCI_DOSYASI);
        if (file.getParentFile() != null) file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(no + "," + ad + "," + soyad + "," + bolum + "," + sinif + "," + ort);
            writer.newLine();
        }
    }

    public static Map<String, String[]> ogrencileriOku() {
        Map<String, String[]> ogrenciler = new HashMap<>();
        File file = new File(OGRENCI_DOSYASI);
        if (!file.exists()) return ogrenciler;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    ogrenciler.put(parts[0], new String[]{parts[1], parts[2], parts[3], parts[4], parts[5]});
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return ogrenciler;
    }

    public static void ogrenciSil(String silinecekNo) throws IOException {
        File dosya = new File(OGRENCI_DOSYASI);
        if (!dosya.exists()) return;

        List<String> satirlar = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dosya))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && !parts[0].equals(silinecekNo)) {
                    satirlar.add(line);
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, false))) {
            for (String satir : satirlar) {
                writer.write(satir);
                writer.newLine();
            }
        }
    }

    // --- KİTAP İŞLEMLERİ (DÜZELTİLDİ) ---

    public static void kitapEkle(String KitapAdi, String yazar, String isbn, String durum) throws IOException {
        File file = new File(KITAP_DOSYASI);
        if (file.getParentFile() != null) file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // Kitabı 6 sütunlu olarak başlatıyoruz (Tarih ve Alan Kişi boş)
            writer.write(KitapAdi + "," + yazar + "," + isbn + "," + durum + ",-,-");
            writer.newLine();
        }
    }

    public static List<String[]> kitaplariOku() {
        return kitaplariOkuDetayli();
    }

    public static List<String[]> kitaplariOkuDetayli() {
        List<String[]> list = new ArrayList<>();
        File file = new File(KITAP_DOSYASI);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String ad = parts[0];
                    String yazar = parts[1];
                    String isbn = parts[2];
                    String durum = (parts.length > 3) ? parts[3] : "Müsait";
                    // Tarih ve Alan Kişi (yoksa tire)
                    String tarih = (parts.length > 4) ? parts[4] : "-";
                    String alan = (parts.length > 5) ? parts[5] : "-";

                    list.add(new String[]{ad, yazar, isbn, durum, tarih, alan});
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    public static void kitapSil(String silinecekISBN) throws IOException {
        List<String[]> kitaplar = kitaplariOkuDetayli();
        File file = new File(KITAP_DOSYASI);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String[] k : kitaplar) {
                if (!k[2].equals(silinecekISBN)) {
                    // Silinmeyenleri yazarken 6 sütunu da koru
                    String d3 = (k.length > 3) ? k[3] : "Müsait";
                    String d4 = (k.length > 4) ? k[4] : "-";
                    String d5 = (k.length > 5) ? k[5] : "-";
                    writer.write(k[0] + "," + k[1] + "," + k[2] + "," + d3 + "," + d4 + "," + d5);
                    writer.newLine();
                }
            }
        }
    }

    // GÜNCELLENEN TALEP METODU: İsmi ve Tarihi Kaydeder
    public static void kitapTalepEt(String isbn, String ogrenciAd) throws IOException {
        List<String[]> kitaplar = kitaplariOkuDetayli();
        File file = new File(KITAP_DOSYASI);

        // Otomatik 2 hafta sonrasını hesapla
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String tarihStr = LocalDate.now().plusWeeks(2).format(format);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String[] k : kitaplar) {
                // Diziyi 6 elemanlı yap (Eğer eski veri kısaysa)
                if (k.length < 6) {
                    String[] yeniK = new String[6];
                    System.arraycopy(k, 0, yeniK, 0, k.length);
                    if(yeniK[4] == null) yeniK[4] = "-";
                    if(yeniK[5] == null) yeniK[5] = "-";
                    k = yeniK;
                }

                if (k[2].equals(isbn)) {
                    k[3] = "Oduncte";  // Durumu "Ödünçte" yap
                    k[4] = tarihStr;   // İade Tarihini yaz
                    k[5] = ogrenciAd;  // Alan Öğrenciyi yaz
                }

                // Dosyaya Yaz
                writer.write(k[0] + "," + k[1] + "," + k[2] + "," + k[3] + "," + k[4] + "," + k[5]);
                writer.newLine();
            }
        }
    }

    public static void kitapIadeEt(String isbn) throws IOException {
        List<String[]> kitaplar = kitaplariOkuDetayli();
        File file = new File(KITAP_DOSYASI);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String[] k : kitaplar) {
                if (k.length < 6) {
                    String[] yeniK = new String[6];
                    System.arraycopy(k, 0, yeniK, 0, k.length);
                    if(yeniK[4] == null) yeniK[4] = "-";
                    if(yeniK[5] == null) yeniK[5] = "-";
                    k = yeniK;
                }

                if (k[2].equals(isbn)) {
                    k[3] = "Müsait";
                    k[4] = "-"; // Tarihi temizle
                    k[5] = "-"; // İsmi temizle
                }
                writer.write(k[0] + "," + k[1] + "," + k[2] + "," + k[3] + "," + k[4] + "," + k[5]);
                writer.newLine();
            }
        }
    }

    // --- DUYURU İŞLEMLERİ (AYNEN KORUNDU) ---
    public static void duyuruEkle(String tarih, String baslik, String icerik) throws IOException {
        File file = new File(DUYURU_DOSYASI);
        if (file.getParentFile() != null) file.getParentFile().mkdirs();

        String temizIcerik = icerik.replace("\n", " ").replace("\r", " ");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(tarih + "::" + baslik + "::" + temizIcerik);
            writer.newLine();
        }
    }

    public static List<String[]> duyurulariOku() {
        List<String[]> list = new ArrayList<>();
        File file = new File(DUYURU_DOSYASI);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("::");
                if (parts.length >= 3) {
                    list.add(parts);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    public static void duyuruSil(String silinecekBaslik) throws IOException {
        List<String[]> duyurular = duyurulariOku();
        File file = new File(DUYURU_DOSYASI);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String[] d : duyurular) {
                if (!d[1].equals(silinecekBaslik)) {
                    writer.write(d[0] + "::" + d[1] + "::" + d[2]);
                    writer.newLine();
                }
            }
        }
    }

    // --- SPOR SALONU İŞLEMLERİ (AYNEN KORUNDU) ---
    public static void sporUyelikEkle(String ad, String no, String tip, String ucret, String durum) throws IOException {
        File file = new File(SPOR_DOSYASI);
        if (file.getParentFile() != null) file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(ad + "," + no + "," + tip + "," + ucret + "," + durum);
            writer.newLine();
        }
    }

    public static List<String[]> sporUyelikleriOku() {
        List<String[]> list = new ArrayList<>();
        File file = new File(SPOR_DOSYASI);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    list.add(parts);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    public static void sporUyelikGuncelle(String ogrenciNo, String yeniDurum) throws IOException {
        List<String[]> liste = sporUyelikleriOku();
        File file = new File(SPOR_DOSYASI);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String[] u : liste) {
                if (u[1].equals(ogrenciNo)) {
                    u[4] = yeniDurum;
                }
                writer.write(String.join(",", u));
                writer.newLine();
            }
        }
    }

    public static boolean sporUyelikSil(String silinecekNo) throws IOException {
        List<String[]> liste = sporUyelikleriOku();
        File dosya = new File(SPOR_DOSYASI);
        boolean bulundu = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, false))) {
            for (String[] u : liste) {
                if (u[1].equals(silinecekNo)) {
                    bulundu = true;
                } else {
                    writer.write(String.join(",", u));
                    writer.newLine();
                }
            }
        }
        return bulundu;
    }
}