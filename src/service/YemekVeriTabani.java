package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class YemekVeriTabani {

    private static Map<String, String> menuListesi = new HashMap<>();

    static {
       //Yemek listesi
        menuListesi.put("01.12.2025",
                "Çorba: Sebze Çorba\n" +
                        "Ana Yemek: Et Sote / Elma Dil. Pat.\n" +
                        "Vejetaryen: *Etsiz Kabak\n" +
                        "Yan Yemek: Nohutlu Pirinç Pilavı\n" +
                        "Ekstra: Yoğurtlu Pancar\n" +
                        "Kalori: 1200 kcal");

        menuListesi.put("02.12.2025",
                "Çorba: Yayla Çorba\n" +
                        "Ana Yemek: Şehriyeli Güveç\n" +
                        "Vejetaryen: *Şehriye Pilavı\n" +
                        "Yan Yemek: Yeşil Mercimek Piyazı\n" +
                        "Ekstra: Meyve\n" +
                        "Kalori: 1150 kcal");

        menuListesi.put("03.12.2025",
                "Çorba: Tarhana Çorba\n" +
                        "Ana Yemek: İzmir Köfte\n" +
                        "Vejetaryen: *Etsiz Ispanak\n" +
                        "Yan Yemek: Bulgur Pilavı\n" +
                        "Ekstra: Triliçe\n" +
                        "Kalori: 1250 kcal");

        menuListesi.put("04.12.2025",
                "Çorba: Mercimek Çorba\n" +
                        "Ana Yemek: Tavuk Büryani\n" +
                        "Vejetaryen: *Soslu Makarna\n" +
                        "Yan Yemek: Zeytinyağlı Pırasa\n" +
                        "Ekstra: Yoğurt\n" +
                        "Kalori: 1150 kcal");

        menuListesi.put("05.12.2025",
                "Çorba: Düğün Çorba\n" +
                        "Ana Yemek: Etli Mevsim Türlü\n" +
                        "Vejetaryen: *Etsiz Mevsim Türlü\n" +
                        "Yan Yemek: Pirinç Pilavı\n" +
                        "Ekstra: Kemalpaşa Tatlısı\n" +
                        "Kalori: 1250 kcal");

        // --- 2. HAFTA (8-12 Aralık 2025) ---
        menuListesi.put("08.12.2025",
                "Çorba: Kış Çorbası\n" +
                        "Ana Yemek: Etli Nohut\n" +
                        "Vejetaryen: *Etsiz Nohut\n" +
                        "Yan Yemek: Pirinç Pilavı\n" +
                        "Ekstra: Turşu\n" +
                        "Kalori: 1150 kcal");

        menuListesi.put("09.12.2025",
                "Çorba: Mercimek Çorba\n" +
                        "Ana Yemek: Tavuk Baget / Pat. Kız.\n" +
                        "Vejetaryen: *Etsiz Kabak\n" +
                        "Yan Yemek: Peynirli Makarna\n" +
                        "Ekstra: İrmik Helvası\n" +
                        "Kalori: 1200 kcal");

        menuListesi.put("10.12.2025",
                "Çorba: Yayla Çorba\n" +
                        "Ana Yemek: Çiftlik Köfte\n" +
                        "Vejetaryen: *Etsiz Bezelye\n" +
                        "Yan Yemek: Bulgur Pilavı\n" +
                        "Ekstra: Meyve\n" +
                        "Kalori: 1200 kcal");

        menuListesi.put("11.12.2025",
                "Çorba: Arabaşı Çorba\n" +
                        "Ana Yemek: Kıymalı Karnabahar\n" +
                        "Vejetaryen: *Etsiz Karnabahar\n" +
                        "Yan Yemek: Su Böreği\n" +
                        "Ekstra: Ayran\n" +
                        "Kalori: 1200 kcal");

        menuListesi.put("12.12.2025",
                "Çorba: Ezogelin Çorba\n" +
                        "Ana Yemek: Bolu Kebabı\n" +
                        "Vejetaryen: *Etsiz Patlıcan\n" +
                        "Yan Yemek: Pirinç Pilavı\n" +
                        "Ekstra: Vişneli Kup\n" +
                        "Kalori: 1250 kcal");

        // --- 3. HAFTA (15-19 Aralık 2025) ---
        menuListesi.put("15.12.2025",
                "Çorba: Domates Çorba\n" +
                        "Ana Yemek: Kıy. Mantı Makarna\n" +
                        "Vejetaryen: *Yoğurtlu Makarna\n" +
                        "Yan Yemek: Yeşil Mercimek Piyazı\n" +
                        "Ekstra: Baklava\n" +
                        "Kalori: 1250 kcal");

        menuListesi.put("16.12.2025",
                "Çorba: Ispanak Çorba\n" +
                        "Ana Yemek: Pilav Üstü Et Kav.\n" +
                        "Vejetaryen: *Etsiz Patates\n" +
                        "Yan Yemek: Salata\n" +
                        "Ekstra: Ayran\n" +
                        "Kalori: 1200 kcal");

        menuListesi.put("17.12.2025",
                "Çorba: Mantar Çorba\n" +
                        "Ana Yemek: Tavuk But / Pat. Kız.\n" +
                        "Vejetaryen: *Etsiz Bamya\n" +
                        "Yan Yemek: İç Pilav\n" +
                        "Ekstra: Aşure\n" +
                        "Kalori: 1250 kcal");

        menuListesi.put("18.12.2025",
                "Çorba: Anadolu Çorba\n" +
                        "Ana Yemek: Beğendili Köfte\n" +
                        "Vejetaryen: *Etsiz Patlıcan\n" +
                        "Yan Yemek: Erişte\n" +
                        "Ekstra: Salata\n" +
                        "Kalori: 1200 kcal");

        menuListesi.put("19.12.2025",
                "Çorba: Köylü Çorba\n" +
                        "Ana Yemek: Etli Taze Fasülye\n" +
                        "Vejetaryen: *Etsiz Taze Fasülye\n" +
                        "Yan Yemek: Pirinç Pilavı\n" +
                        "Ekstra: Meyve\n" +
                        "Kalori: 1150 kcal");

        // --- 4. HAFTA (22-26 Aralık 2025) ---
        menuListesi.put("22.12.2025",
                "Çorba: Ezogelin Çorba\n" +
                        "Ana Yemek: Tas Kebabı\n" +
                        "Vejetaryen: *Etsiz Patates\n" +
                        "Yan Yemek: Erişte\n" +
                        "Ekstra: Tahin Helva\n" +
                        "Kalori: 1250 kcal");

        menuListesi.put("23.12.2025",
                "Çorba: Tarhana Çorba\n" +
                        "Ana Yemek: Etli Kuru Fasülye\n" +
                        "Vejetaryen: *Etsiz Kuru Fasülye\n" +
                        "Yan Yemek: Pirinç Pilavı\n" +
                        "Ekstra: Turşu\n" +
                        "Kalori: 1150 kcal");

        menuListesi.put("24.12.2025",
                "Çorba: Şehriye Çorba\n" +
                        "Ana Yemek: Beşamelli Kebap\n" +
                        "Vejetaryen: *Etsiz Patlıcan\n" +
                        "Yan Yemek: Mercimekli Bulgur Pilavı\n" +
                        "Ekstra: Cacık\n" +
                        "Kalori: 1200 kcal");

        menuListesi.put("25.12.2025",
                "Çorba: Yayla Çorba\n" +
                        "Ana Yemek: Tavuk Baget / Elma dil. Pat.\n" +
                        "Vejetaryen: *Peynirli Makarna\n" +
                        "Yan Yemek: Barbunya Piyazı\n" +
                        "Ekstra: Sup\n" +
                        "Kalori: 1200 kcal");

        menuListesi.put("26.12.2025",
                "Çorba: Mercimek Çorba\n" +
                        "Ana Yemek: Ekşili Köfte\n" +
                        "Vejetaryen: *Etsiz Karnabahar\n" +
                        "Yan Yemek: Soslu Makarna\n" +
                        "Ekstra: Meyve\n" +
                        "Kalori: 1200 kcal");

        // --- 5. HAFTA (29 Aralık 2025 - 2 Ocak 2026) ---
        menuListesi.put("29.12.2025",
                "Çorba: Yeşil Mercimek Çorba\n" +
                        "Ana Yemek: Çoban Kavurma\n" +
                        "Vejetaryen: *Etsiz Taze Fasülye\n" +
                        "Yan Yemek: Patatesli Kol Böreği\n" +
                        "Ekstra: Ayran\n" +
                        "Kalori: 1200 kcal");

        menuListesi.put("30.12.2025",
                "Çorba: Brokoli Çorba\n" +
                        "Ana Yemek: Tavuk But / Pat. Kız.\n" +
                        "Vejetaryen: *Etsiz Pırasa\n" +
                        "Yan Yemek: Bulgur Pilavı\n" +
                        "Ekstra: Meyve\n" +
                        "Kalori: 1200 kcal");

        menuListesi.put("31.12.2025",
                "Çorba: Tarhana Çorba\n" +
                        "Ana Yemek: Çiftlik Köfte\n" +
                        "Vejetaryen: *Etsiz Kabak\n" +
                        "Yan Yemek: Soslu Makarna\n" +
                        "Ekstra: Kakaolu İrmik Tatlısı\n" +
                        "Kalori: 1200 kcal");

        menuListesi.put("01.01.2026",
                "*** RESMİ TATİL ***");

        menuListesi.put("02.01.2026",
                "Çorba: Ezogelin Çorba\n" +
                        "Ana Yemek: Kıymalı Bezelye\n" +
                        "Vejetaryen: *Etsiz Bezelye\n" +
                        "Yan Yemek: Pirinç Pilavı\n" +
                        "Ekstra: Yoğurt\n" +
                        "Kalori: 1150 kcal");
    }

    public static String gununMenusuGetir() {
        // Bugünün tarihini al (Örn: 17.12.2025 formatında)
        String bugunTarih = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        // Listede var mı bak
        if (menuListesi.containsKey(bugunTarih)) {
            return "TARİH: " + bugunTarih + "\n----------------------------\n" + menuListesi.get(bugunTarih);
        } else {
            return "TARİH: " + bugunTarih + "\n\nBugün için belirlenmiş bir yemek listesi bulunamadı.\n(Hafta sonu olabilir.)";
        }
    }
}