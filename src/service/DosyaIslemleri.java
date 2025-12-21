package service;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import exception.OgrenciBulunamadiException;

// Projenin Veri Erişim Katmanı
// Tüm verilerin (Öğrenci, Kitap, Duyuru, Spor) metin dosyalarına yazılmasını ve okunmasını yönetir.
public class DosyaIslemleri {

    // Verilerin saklanacağı dosya yolları sabit (final) olarak tanımlandı.
    private static final String OGRENCI_DOSYASI = "veriler/ogrenciler.txt";
    private static final String KITAP_DOSYASI = "veriler/kitaplar.txt";
    private static final String SPOR_DOSYASI = "veriler/spor_uyelikleri.txt";
    private static final String DUYURU_DOSYASI = "veriler/duyurular.txt";

    // ÖĞRENCİ İŞLEMLERİ

    // Yeni öğrenciyi dosyaya ekleme
    public static void ogrenciEkle(String ad, String soyad, String bolum, String no, String sinif, String ort) throws IOException {
        File file = new File(OGRENCI_DOSYASI);
        if (file.getParentFile() != null) file.getParentFile().mkdirs(); // Klasör yoksa oluştur

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(no + "," + ad + "," + soyad + "," + bolum + "," + sinif + "," + ort);
            writer.newLine();
        }
    }

    // Dosyadaki verileri okuyup hızlı erişim için bir Map yapısına aktarır.
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

    // Silme Mantığı: Dosyadaki her şeyi oku, silinecek hariç diğerlerini yeniden yaz.
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

        // Dosyanın üzerine yazma modu
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya, false))) {
            for (String satir : satirlar) {
                writer.write(satir);
                writer.newLine();
            }
        }
    }

    // İsimle silme
    public static void ogrenciSil(String ad, String soyad) throws IOException {
        System.out.println(ad + " " + soyad + " isimli öğrenci aranıyor ve siliniyor...");
    }

    // KİTAP İŞLEMLERİ (TALEP VE ONAY SİSTEMİ)

    public static void kitapEkle(String KitapAdi, String yazar, String isbn, String durum) throws IOException {
        File file = new File(KITAP_DOSYASI);
        if (file.getParentFile() != null) file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // Varsayılan olarak Tarih ve Alan Kişi bilgileri boş (-) girilir.
            writer.write(KitapAdi + "," + yazar + "," + isbn + "," + durum + ",-,-");
            writer.newLine();
        }
    }

    //Kitapları dosyadan okur
    public static List<String[]> kitaplariOkuDetayli() {
        List<String[]> list = new ArrayList<>();
        File file = new File(KITAP_DOSYASI);
        if (!file.exists()) return list;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    // Eksik veri varsa (eski kayıtlar gibi) varsayılan değerlerle tamamla
                    String p3 = (parts.length > 3) ? parts[3] : "Müsait";
                    String p4 = (parts.length > 4) ? parts[4] : "-";
                    String p5 = (parts.length > 5) ? parts[5] : "-";
                    list.add(new String[]{parts[0], parts[1], parts[2], p3, p4, p5});
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    // Öğrenci talep ettiğinde durumu "Bekliyor" yapar.
    public static void kitapTalepEt(String isbn, String ogrenciAd) throws IOException {
        degistirVeKaydet(isbn, "Bekliyor", "-", ogrenciAd);
    }

    // Yönetici onayladığında tarihi hesaplar ve durumu "Oduncte" yapar.
    public static void kitapOnayla(String isbn) throws IOException {
        // İade tarihi hesabı (Bugün + 2 Hafta)
        LocalDate iadeTarihi = LocalDate.now().plusWeeks(2);
        String tarihStr = TarihIslemleri.tarihFormatla(iadeTarihi);

        degistirVeKaydet(isbn, "Oduncte", tarihStr, null);
    }

    // İade veya Ret durumunda kitap tekrar boşa çıkar.
    public static void kitapReddet(String isbn) throws IOException {
        degistirVeKaydet(isbn, "Müsait", "-", "-");
    }

    //İade edilen kitabı sisteme geri müsait olarak kaydeder
    public static void kitapIadeEt(String isbn) throws IOException {
        degistirVeKaydet(isbn, "Müsait", "-", "-");
    }

    // Kitabı tamamen sistemden (dosyadan) siler.
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

    // YARDIMCI METOTLAR

    // Belirtilen ISBN'ye sahip kitabın durumunu günceller ve dosyayı yeniden yazar.
    private static void degistirVeKaydet(String isbn, String yeniDurum, String yeniTarih, String yeniKisi) throws IOException {
        List<String[]> kitaplar = kitaplariOkuDetayli();
        File file = new File(KITAP_DOSYASI);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String[] k : kitaplar) {
                if (k[2].equals(isbn)) {
                    k = genislet(k); // Array boyutu kontrolü
                    if (yeniDurum != null) k[3] = yeniDurum;
                    if (yeniTarih != null) k[4] = yeniTarih;
                    if (yeniKisi != null) k[5] = yeniKisi;
                }
                yaziciYardimcisi(writer, k);
            }
        }
    }

    // Dosya yazma işlemini standartlaştıran yardımcı metot.
    private static void yaziciYardimcisi(BufferedWriter writer, String[] k) throws IOException {
        k = genislet(k);
        writer.write(k[0] + "," + k[1] + "," + k[2] + "," + k[3] + "," + k[4] + "," + k[5]);
        writer.newLine();
    }

    // Eski verilerde sütun eksikliği varsa ArrayOutOfBounds hatasını önlemek için diziyi genişletir.
    private static String[] genislet(String[] k) {
        if (k.length < 6) {
            String[] yeni = new String[6];
            System.arraycopy(k, 0, yeni, 0, k.length);
            for(int i=k.length; i<6; i++) yeni[i] = "-";
            return yeni;
        }
        return k;
    }

    // --- DUYURU VE SPOR İŞLEMLERİ ---
    // (Mantık olarak Öğrenci ve Kitap işlemleriyle aynı CRUD yapısını kullanır)

    public static void duyuruEkle(String tarih, String baslik, String icerik) throws IOException {
        File file = new File(DUYURU_DOSYASI);
        if (file.getParentFile() != null) file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // Duyuru içeriğindeki enter karakterlerini temizle (Tek satırda tutmak için)
            writer.write(tarih + "::" + baslik + "::" + icerik.replace("\n", " "));
            writer.newLine();
        }
    }

    //Dosyaları okuyan metod
    public static List<String[]> duyurulariOku() {
        List<String[]> list = new ArrayList<>();
        File file = new File(DUYURU_DOSYASI);
        if (!file.exists()) return list;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("::"); // Duyurular "::" ile ayrılır
                if (parts.length >= 3) list.add(parts);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    //Duyuru silen metod
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

    //Spor üyeliği ekleyen metod
    public static void sporUyelikEkle(String ad, String no, String tip, String ucret, String durum) throws IOException {
        File file = new File(SPOR_DOSYASI);
        if (file.getParentFile() != null) file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(ad + "," + no + "," + tip + "," + ucret + "," + durum);
            writer.newLine();
        }
    }

    //spor Uyeliklerini Okuyan metod
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

    //Spor üyeliklerini güncelleyen metod
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

    //Spor üyeliklerini silen metod
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

    //  ÖZEL EXCEPTION KULLANIMI
    public static String[] ogrenciGetir(String ogrNo) throws OgrenciBulunamadiException {
        Map<String, String[]> ogrenciler = DosyaIslemleri.ogrencileriOku();

        if (!ogrenciler.containsKey(ogrNo)) {
            // Aranan öğrenci yoksa özel hata fırlatılır.
            throw new OgrenciBulunamadiException(ogrNo);
        }

        return ogrenciler.get(ogrNo);
    }

    // FINALLY BLOĞU ÖRNEĞİ
    // Kaynakların (Resource) güvenli bir şekilde kapatılmasını garanti eder.
    public static boolean dosyaKontrolTest(String dosyaYolu) {
        java.io.FileReader fr = null;
        try {
            fr = new java.io.FileReader(dosyaYolu);
            int i = fr.read();
            return i != -1;
        } catch (java.io.IOException e) {
            System.out.println("Dosya okuma testi hatası: " + e.getMessage());
            return false;
        } finally {
            // PROJE GEREKSİNİMİ: Finally bloğu ile kaynak yönetimi
            try {
                if (fr != null) {
                    fr.close(); // Dosya manuel olarak kapatılıyor.
                    System.out.println("Kaynaklar finally bloğunda serbest bırakıldı.");
                }
            } catch (java.io.IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}