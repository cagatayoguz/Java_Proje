package model;

import java.util.ArrayList;
import java.util.List;

public class AkademisyenVerisi {

    public static List<OgretimUyesi> getAkademisyenListesi() {
        List<OgretimUyesi> liste = new ArrayList<>();

        // Mühendislik Fakültesi
        liste.add(new OgretimUyesi("Berna", "Yılmaz", "Prof. Dr.", "Bilgisayar Müh.", "berna.yilmaz@uni.edu.tr"));
        liste.add(new OgretimUyesi("Koray", "Demir", "Doç. Dr.", "Bilgisayar Müh.", "koray.demir@uni.edu.tr"));
        liste.add(new OgretimUyesi("Hande", "Çelik", "Dr. Öğr. Üyesi", "Yazılım Müh.", "hande.celik@uni.edu.tr"));
        liste.add(new OgretimUyesi("Mert", "Kaya", "Arş. Gör.", "Yazılım Müh.", "mert.kaya@uni.edu.tr"));
        liste.add(new OgretimUyesi("Sinan", "Koç", "Prof. Dr.", "Elektrik-Elektronik", "sinan.koc@uni.edu.tr"));
        liste.add(new OgretimUyesi("Deniz", "Arslan", "Doç. Dr.", "Elektrik-Elektronik", "deniz.arslan@uni.edu.tr"));
        liste.add(new OgretimUyesi("Seda", "Bulut", "Dr. Öğr. Üyesi", "Endüstri Müh.", "seda.bulut@uni.edu.tr"));
        liste.add(new OgretimUyesi("Eren", "Yıldız", "Arş. Gör.", "Endüstri Müh.", "eren.yildiz@uni.edu.tr"));
        liste.add(new OgretimUyesi("Faruk", "Özkan", "Prof. Dr.", "Makine Müh.", "faruk.ozkan@uni.edu.tr"));
        liste.add(new OgretimUyesi("Gülşah", "Aydın", "Doç. Dr.", "Makine Müh.", "gulsah.aydin@uni.edu.tr"));
        liste.add(new OgretimUyesi("Murat", "Şahin", "Dr. Öğr. Üyesi", "İnşaat Müh.", "murat.sahin@uni.edu.tr"));
        liste.add(new OgretimUyesi("Ceren", "Tekin", "Arş. Gör.", "İnşaat Müh.", "ceren.tekin@uni.edu.tr"));
        liste.add(new OgretimUyesi("Oğuz", "Aksoy", "Prof. Dr.", "Kimya Müh.", "oguz.aksoy@uni.edu.tr"));
        liste.add(new OgretimUyesi("Pelin", "Erdoğan", "Doç. Dr.", "Gıda Müh.", "pelin.erdogan@uni.edu.tr"));
        liste.add(new OgretimUyesi("Ufuk", "Keskin", "Dr. Öğr. Üyesi", "Mekatronik Müh.", "ufuk.keskin@uni.edu.tr"));

        // Tıp Fakültesi
        liste.add(new OgretimUyesi("Leyla", "Sönmez", "Prof. Dr.", "Kardiyoloji", "leyla.sonmez@uni.edu.tr"));
        liste.add(new OgretimUyesi("Hakan", "Yıldırım", "Prof. Dr.", "Pediatri", "hakan.yildirim@uni.edu.tr"));
        liste.add(new OgretimUyesi("Zeynep", "Kurt", "Doç. Dr.", "Nöroloji", "zeynep.kurt@uni.edu.tr"));
        liste.add(new OgretimUyesi("Serdar", "Güneş", "Prof. Dr.", "Dahiliye", "serdar.gunes@uni.edu.tr"));
        liste.add(new OgretimUyesi("Mine", "Polat", "Doç. Dr.", "Genel Cerrahi", "mine.polat@uni.edu.tr"));
        liste.add(new OgretimUyesi("Burak", "Tunç", "Dr. Öğr. Üyesi", "Ortopedi", "burak.tunc@uni.edu.tr"));
        liste.add(new OgretimUyesi("Esra", "Can", "Arş. Gör.", "Göz Hastalıkları", "esra.can@uni.edu.tr"));
        liste.add(new OgretimUyesi("Volkan", "Ekinci", "Dr. Öğr. Üyesi", "KBB", "volkan.ekinci@uni.edu.tr"));
        liste.add(new OgretimUyesi("Nuray", "Başar", "Prof. Dr.", "Psikiyatri", "nuray.basar@uni.edu.tr"));
        liste.add(new OgretimUyesi("Cemil", "Vural", "Doç. Dr.", "Radyoloji", "cemil.vural@uni.edu.tr"));

        // Hukuk Fakültesi
        liste.add(new OgretimUyesi("Selim", "Korkmaz", "Prof. Dr.", "Anayasa Hukuku", "selim.korkmaz@uni.edu.tr"));
        liste.add(new OgretimUyesi("Banu", "Acar", "Doç. Dr.", "Ceza Hukuku", "banu.acar@uni.edu.tr"));
        liste.add(new OgretimUyesi("Tamer", "Duran", "Dr. Öğr. Üyesi", "Medeni Hukuk", "tamer.duran@uni.edu.tr"));
        liste.add(new OgretimUyesi("Derya", "Öztürk", "Arş. Gör.", "İdare Hukuku", "derya.ozturk@uni.edu.tr"));
        liste.add(new OgretimUyesi("Naci", "Erdem", "Prof. Dr.", "Ticaret Hukuku", "naci.erdem@uni.edu.tr"));

        // Eğitim Fakültesi
        liste.add(new OgretimUyesi("Nuri", "Genç", "Prof. Dr.", "Sınıf Öğretmenliği", "nuri.genc@uni.edu.tr"));
        liste.add(new OgretimUyesi("Gamze", "Çetin", "Doç. Dr.", "Okul Öncesi", "gamze.cetin@uni.edu.tr"));
        liste.add(new OgretimUyesi("Onur", "Kılıç", "Dr. Öğr. Üyesi", "Rehberlik", "onur.kilic@uni.edu.tr"));
        liste.add(new OgretimUyesi("Melis", "Ünal", "Arş. Gör.", "Özel Eğitim", "melis.unal@uni.edu.tr"));
        liste.add(new OgretimUyesi("Zafer", "Sarı", "Prof. Dr.", "Türkçe Eğitimi", "zafer.sari@uni.edu.tr"));

        // İktisadi ve İdari Bilimler Fakültesi
        liste.add(new OgretimUyesi("Tolga", "Yavuz", "Prof. Dr.", "İktisat", "tolga.yavuz@uni.edu.tr"));
        liste.add(new OgretimUyesi("Sevgi", "Deniz", "Doç. Dr.", "İşletme", "sevgi.deniz@uni.edu.tr"));
        liste.add(new OgretimUyesi("Metin", "Uçar", "Dr. Öğr. Üyesi", "Maliye", "metin.ucar@uni.edu.tr"));
        liste.add(new OgretimUyesi("Elif", "Bozkurt", "Arş. Gör.", "Uluslararası İlş.", "elif.bozkurt@uni.edu.tr"));
        liste.add(new OgretimUyesi("Refik", "Altun", "Prof. Dr.", "Siyaset Bilimi", "refik.altun@uni.edu.tr"));

        // Diş Hekimliği Fakültesi
        liste.add(new OgretimUyesi("İsmail", "Kaya", "Prof. Dr.", "Ağız Cerrahisi", "ismail.kaya@uni.edu.tr"));
        liste.add(new OgretimUyesi("Arzu", "Şen", "Doç. Dr.", "Ortodonti", "arzu.sen@uni.edu.tr"));
        liste.add(new OgretimUyesi("Kemal", "Varol", "Dr. Öğr. Üyesi", "Pedodonti", "kemal.varol@uni.edu.tr"));
        liste.add(new OgretimUyesi("Funda", "Er", "Arş. Gör.", "Periodontoloji", "funda.er@uni.edu.tr"));
        liste.add(new OgretimUyesi("Rıza", "Koçak", "Prof. Dr.", "Protetik Diş", "riza.kocak@uni.edu.tr"));

        return liste;
    }
}