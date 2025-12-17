package service;

import java.io.*;
import java.util.*;

public class DosyaIslemleri {

    private static final String OGRENCI_DOSYASI = "veriler/ogrenciler.txt";
    private static final String KITAP_DOSYASI = "veriler/kitaplar.txt";
    private static final String SPOR_DOSYASI = "veriler/spor_uyelikleri.txt";
    private static final String DUYURU_DOSYASI = "veriler/duyurular.txt";

    // --- ÖĞRENCİ İŞLEMLERİ ---
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

    // --- KİTAP İŞLEMLERİ ---

    public static void kitapEkle(String ad, String yazar, String isbn, String durum, String yil) throws IOException {
        File file = new File(KITAP_DOSYASI);
        if (file.getParentFile() != null) file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(ad + "," + yazar + "," + isbn + "," + durum + "," + yil);
            writer.newLine();
        }
    }

    // *** BU METODU EKLEDİK (HATAYI ÇÖZEN KISIM) ***
    // Eski kodlar "kitaplariOku" diye çağırıyorsa, onları yeni metoda yönlendiriyoruz.
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
                    String yil = (parts.length > 4) ? parts[4] : "-";

                    list.add(new String[]{ad, yazar, isbn, durum, yil});
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
                    writer.write(k[0] + "," + k[1] + "," + k[2] + "," + k[3] + "," + k[4]);
                    writer.newLine();
                }
            }
        }
    }

    public static void kitapTalepEt(String isbn, String ogrenciAd) throws IOException {
        List<String[]> kitaplar = kitaplariOkuDetayli();
        File file = new File(KITAP_DOSYASI);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String[] k : kitaplar) {
                if (k[2].equals(isbn)) {
                    k[3] = "Bekliyor";
                }
                writer.write(k[0] + "," + k[1] + "," + k[2] + "," + k[3] + "," + k[4]);
                writer.newLine();
            }
        }
    }

    public static void kitapIadeEt(String isbn) throws IOException {
        List<String[]> kitaplar = kitaplariOkuDetayli();
        File file = new File(KITAP_DOSYASI);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String[] k : kitaplar) {
                if (k[2].equals(isbn)) {
                    k[3] = "Müsait";
                }
                writer.write(k[0] + "," + k[1] + "," + k[2] + "," + k[3] + "," + k[4]);
                writer.newLine();
            }
        }
    }

    // --- DUYURU İŞLEMLERİ ---
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

    // --- SPOR SALONU İŞLEMLERİ ---
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