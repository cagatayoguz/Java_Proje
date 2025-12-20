package service;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import exception.OgrenciBulunamadiException;

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
                if (parts.length > 0 && !parts[0].equals(silinecekNo)) satirlar.add(line);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, false))) {
            for (String satir : satirlar) {
                writer.write(satir);
                writer.newLine();
            }
        }
    }

    public static void ogrenciSil(String ad, String soyad) throws IOException {
        // Burada öğrenciyi numarası yerine Ad ve Soyadına göre bulup silen kod simülasyonu
        System.out.println(ad + " " + soyad + " isimli öğrenci aranıyor ve siliniyor...");
    }

    // --- KİTAP İŞLEMLERİ (TALEP VE ONAY SİSTEMİ) ---

    public static void kitapEkle(String KitapAdi, String yazar, String isbn, String durum) throws IOException {
        File file = new File(KITAP_DOSYASI);
        if (file.getParentFile() != null) file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(KitapAdi + "," + yazar + "," + isbn + "," + durum + ",-,-");
            writer.newLine();
        }
    }

    public static List<String[]> kitaplariOku() { return kitaplariOkuDetayli(); }

    public static List<String[]> kitaplariOkuDetayli() {
        List<String[]> list = new ArrayList<>();
        File file = new File(KITAP_DOSYASI);
        if (!file.exists()) return list;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String p3 = (parts.length > 3) ? parts[3] : "Müsait";
                    String p4 = (parts.length > 4) ? parts[4] : "-";
                    String p5 = (parts.length > 5) ? parts[5] : "-";
                    list.add(new String[]{parts[0], parts[1], parts[2], p3, p4, p5});
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    // 1. ADIM: TALEP ETME (Durumu "Bekliyor" yapar, Tarih atmaz)
    public static void kitapTalepEt(String isbn, String ogrenciAd) throws IOException {
        degistirVeKaydet(isbn, "Bekliyor", "-", ogrenciAd);
    }

    // 2. ADIM: ONAYLAMA (Durumu "Oduncte" yapar, Tarihi BUGÜN atar)
    // 2. ADIM: ONAYLAMA (Durumu "Oduncte" yapar, Tarihi BUGÜN atar)
    public static void kitapOnayla(String isbn) throws IOException {

        // --- BURASI DEĞİŞTİ: ARTIK SENİN FORMATLAYICI SINIFIN KULLANILIYOR ---
        // LocalDate ile 2 hafta sonrasını hesaplıyoruz
        LocalDate iadeTarihi = LocalDate.now().plusWeeks(2);

        // TarihIslemleri sınıfındaki statik metodu çağırarak String'e çeviriyoruz
        String tarihStr = TarihIslemleri.tarihFormatla(iadeTarihi);
        // -------------------------------------------------------------------

        // İsmi dosyadan bulup korumamız lazım ama burada basitçe o anki satırı güncelleyen yardımcı metod kullanıyoruz
        degistirVeKaydet(isbn, "Oduncte", tarihStr, null);
    }

    // 2. ADIM (ALTERNATİF): REDDETME (Durumu "Müsait" yapar)
    public static void kitapReddet(String isbn) throws IOException {
        degistirVeKaydet(isbn, "Müsait", "-", "-");
    }

    // İADE ETME
    public static void kitapIadeEt(String isbn) throws IOException {
        degistirVeKaydet(isbn, "Müsait", "-", "-");
    }

    public static void kitapSil(String silinecekISBN) throws IOException {
        List<String[]> kitaplar = kitaplariOkuDetayli();
        File file = new File(KITAP_DOSYASI);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String[] k : kitaplar) {
                if (!k[2].equals(silinecekISBN)) {
                    yaziciYardimcisi(writer, k);
                }
            }
        }
    }

    // --- YARDIMCI METOTLAR ---
    // Bu metot dosyadaki belirli bir ISBN'ye sahip satırın durumunu, tarihini ve kişisini günceller.
    // Eğer yeniDeger 'null' ise eski değerini korur.
    private static void degistirVeKaydet(String isbn, String yeniDurum, String yeniTarih, String yeniKisi) throws IOException {
        List<String[]> kitaplar = kitaplariOkuDetayli();
        File file = new File(KITAP_DOSYASI);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String[] k : kitaplar) {
                if (k[2].equals(isbn)) {
                    k = genislet(k);
                    if (yeniDurum != null) k[3] = yeniDurum;
                    if (yeniTarih != null) k[4] = yeniTarih;
                    if (yeniKisi != null) k[5] = yeniKisi;
                }
                yaziciYardimcisi(writer, k);
            }
        }
    }

    private static void yaziciYardimcisi(BufferedWriter writer, String[] k) throws IOException {
        k = genislet(k);
        writer.write(k[0] + "," + k[1] + "," + k[2] + "," + k[3] + "," + k[4] + "," + k[5]);
        writer.newLine();
    }

    private static String[] genislet(String[] k) {
        if (k.length < 6) {
            String[] yeni = new String[6];
            System.arraycopy(k, 0, yeni, 0, k.length);
            for(int i=k.length; i<6; i++) yeni[i] = "-";
            return yeni;
        }
        return k;
    }

    // --- DUYURU VE SPOR İŞLEMLERİ (AYNI) ---
    public static void duyuruEkle(String tarih, String baslik, String icerik) throws IOException {
        File file = new File(DUYURU_DOSYASI);
        if (file.getParentFile() != null) file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(tarih + "::" + baslik + "::" + icerik.replace("\n", " "));
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
                if (parts.length >= 3) list.add(parts);
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
                if (parts.length >= 5) list.add(parts);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }
    public static void sporUyelikGuncelle(String ogrenciNo, String yeniDurum) throws IOException {
        List<String[]> liste = sporUyelikleriOku();
        File file = new File(SPOR_DOSYASI);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String[] u : liste) {
                if (u[1].equals(ogrenciNo)) u[4] = yeniDurum;
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
                if (u[1].equals(silinecekNo)) bulundu = true;
                else {
                    writer.write(String.join(",", u));
                    writer.newLine();
                }
            }
        }
        return bulundu;
    }
    public static String[] ogrenciGetir(String ogrNo) throws OgrenciBulunamadiException {
        Map<String, String[]> ogrenciler = DosyaIslemleri.ogrencileriOku();

        if (!ogrenciler.containsKey(ogrNo)) {
            // Öğrenci yoksa hata nesnesini fırlatıyoruz
            throw new OgrenciBulunamadiException(ogrNo); //
        }

        return ogrenciler.get(ogrNo);
    }
}