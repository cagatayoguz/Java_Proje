package model;

import java.util.ArrayList;
import java.util.List;

public class AkademisyenVerisi {

    public static List<OgretimUyesi> getAkademisyenListesi() {
        List<OgretimUyesi> liste = new ArrayList<>();

        // --- MÜHENDİSLİK FAKÜLTESİ ---
        liste.add(new OgretimUyesi("Ahmet", "Yılmaz", "Prof. Dr.", "Bilgisayar Müh.", "ahmet.yilmaz@uni.edu.tr"));
        liste.add(new OgretimUyesi("Mehmet", "Öztürk", "Doç. Dr.", "Bilgisayar Müh.", "mehmet.ozturk@uni.edu.tr"));
        liste.add(new OgretimUyesi("Ayşe", "Kaya", "Dr. Öğr. Üyesi", "Yazılım Müh.", "ayse.kaya@uni.edu.tr"));
        liste.add(new OgretimUyesi("Fatma", "Demir", "Arş. Gör.", "Yazılım Müh.", "fatma.demir@uni.edu.tr"));
        liste.add(new OgretimUyesi("Mustafa", "Çelik", "Prof. Dr.", "Elektrik-Elektronik", "mustafa.celik@uni.edu.tr"));
        liste.add(new OgretimUyesi("Emre", "Aydın", "Doç. Dr.", "Elektrik-Elektronik", "emre.aydin@uni.edu.tr"));
        liste.add(new OgretimUyesi("Selin", "Yıldız", "Dr. Öğr. Üyesi", "Endüstri Müh.", "selin.yildiz@uni.edu.tr"));
        liste.add(new OgretimUyesi("Burak", "Can", "Arş. Gör.", "Endüstri Müh.", "burak.can@uni.edu.tr"));
        liste.add(new OgretimUyesi("Canan", "Erkin", "Prof. Dr.", "Makine Müh.", "canan.erkin@uni.edu.tr"));
        liste.add(new OgretimUyesi("Volkan", "Bozkurt", "Doç. Dr.", "Makine Müh.", "volkan.bozkurt@uni.edu.tr"));
        liste.add(new OgretimUyesi("Zeynep", "Sönmez", "Dr. Öğr. Üyesi", "İnşaat Müh.", "zeynep.sonmez@uni.edu.tr"));
        liste.add(new OgretimUyesi("Ali", "Veli", "Arş. Gör.", "İnşaat Müh.", "ali.veli@uni.edu.tr"));
        liste.add(new OgretimUyesi("Hakan", "Şahin", "Prof. Dr.", "Kimya Müh.", "hakan.sahin@uni.edu.tr"));
        liste.add(new OgretimUyesi("Elif", "Polat", "Doç. Dr.", "Gıda Müh.", "elif.polat@uni.edu.tr"));
        liste.add(new OgretimUyesi("Cem", "Uzan", "Dr. Öğr. Üyesi", "Mekatronik Müh.", "cem.uzan@uni.edu.tr"));

        // --- TIP FAKÜLTESİ ---
        liste.add(new OgretimUyesi("Kemal", "Sunal", "Prof. Dr.", "Kardiyoloji", "kemal.sunal@uni.edu.tr"));
        liste.add(new OgretimUyesi("Adile", "Naşit", "Prof. Dr.", "Pediatri", "adile.nasit@uni.edu.tr"));
        liste.add(new OgretimUyesi("Şener", "Şen", "Doç. Dr.", "Nöroloji", "sener.sen@uni.edu.tr"));
        liste.add(new OgretimUyesi("Türkan", "Şoray", "Prof. Dr.", "Dahiliye", "turkan.soray@uni.edu.tr"));
        liste.add(new OgretimUyesi("Kadir", "İnanır", "Doç. Dr.", "Genel Cerrahi", "kadir.inanir@uni.edu.tr"));
        liste.add(new OgretimUyesi("Cüneyt", "Arkın", "Dr. Öğr. Üyesi", "Ortopedi", "cuneyt.arkin@uni.edu.tr"));
        liste.add(new OgretimUyesi("Tarık", "Akan", "Arş. Gör.", "Göz Hastalıkları", "tarik.akan@uni.edu.tr"));
        liste.add(new OgretimUyesi("Halit", "Akçatepe", "Dr. Öğr. Üyesi", "KBB", "halit.akcatepe@uni.edu.tr"));
        liste.add(new OgretimUyesi("Münir", "Özkul", "Prof. Dr.", "Psikiyatri", "munir.ozkul@uni.edu.tr"));
        liste.add(new OgretimUyesi("Zeki", "Alasya", "Doç. Dr.", "Radyoloji", "zeki.alasya@uni.edu.tr"));

        // --- HUKUK FAKÜLTESİ ---
        liste.add(new OgretimUyesi("Metin", "Akpınar", "Prof. Dr.", "Anayasa Hukuku", "metin.akpinar@uni.edu.tr"));
        liste.add(new OgretimUyesi("Oya", "Aydoğan", "Doç. Dr.", "Ceza Hukuku", "oya.aydogan@uni.edu.tr"));
        liste.add(new OgretimUyesi("Nevra", "Serezli", "Dr. Öğr. Üyesi", "Medeni Hukuk", "nevra.serezli@uni.edu.tr"));
        liste.add(new OgretimUyesi("Gülşen", "Bubikoğlu", "Arş. Gör.", "İdare Hukuku", "gulsen.bubikoglu@uni.edu.tr"));
        liste.add(new OgretimUyesi("Ediz", "Hun", "Prof. Dr.", "Ticaret Hukuku", "ediz.hun@uni.edu.tr"));

        // --- EĞİTİM FAKÜLTESİ ---
        liste.add(new OgretimUyesi("Filiz", "Akın", "Prof. Dr.", "Sınıf Öğretmenliği", "filiz.akin@uni.edu.tr"));
        liste.add(new OgretimUyesi("Hülya", "Koçyiğit", "Doç. Dr.", "Okul Öncesi", "hulya.kocyigit@uni.edu.tr"));
        liste.add(new OgretimUyesi("Fatma", "Girik", "Dr. Öğr. Üyesi", "Rehberlik", "fatma.girik@uni.edu.tr"));
        liste.add(new OgretimUyesi("Sadri", "Alışık", "Arş. Gör.", "Özel Eğitim", "sadri.alisik@uni.edu.tr"));
        liste.add(new OgretimUyesi("Ayhan", "Işık", "Prof. Dr.", "Türkçe Eğitimi", "ayhan.isik@uni.edu.tr"));

        // --- İKTİSADİ VE İDARİ BİLİMLER ---
        liste.add(new OgretimUyesi("Erol", "Taş", "Prof. Dr.", "İktisat", "erol.tas@uni.edu.tr"));
        liste.add(new OgretimUyesi("Hulusi", "Kentmen", "Doç. Dr.", "İşletme", "hulusi.kentmen@uni.edu.tr"));
        liste.add(new OgretimUyesi("Ali", "Poyrazoğlu", "Dr. Öğr. Üyesi", "Maliye", "ali.poyrazoglu@uni.edu.tr"));
        liste.add(new OgretimUyesi("Levent", "Kırca", "Arş. Gör.", "Uluslararası İlş.", "levent.kirca@uni.edu.tr"));
        liste.add(new OgretimUyesi("Nejat", "Uygur", "Prof. Dr.", "Siyaset Bilimi", "nejat.uygur@uni.edu.tr"));

        // --- DİŞ HEKİMLİĞİ ---
        liste.add(new OgretimUyesi("İsmail Onur", "Koru", "Prof. Dr.", "Ağız Cerrahisi", "ismail.koru@uni.edu.tr"));
        liste.add(new OgretimUyesi("Barış", "Manço", "Doç. Dr.", "Ortodonti", "baris.manco@uni.edu.tr"));
        liste.add(new OgretimUyesi("Cem", "Karaca", "Dr. Öğr. Üyesi", "Pedodonti", "cem.karaca@uni.edu.tr"));
        liste.add(new OgretimUyesi("Erkin", "Koray", "Arş. Gör.", "Periodontoloji", "erkin.koray@uni.edu.tr"));
        liste.add(new OgretimUyesi("Sezen", "Aksu", "Prof. Dr.", "Protetik Diş", "sezen.aksu@uni.edu.tr"));

        return liste;
    }
}