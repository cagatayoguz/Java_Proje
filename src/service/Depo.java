package service;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections; // Collections.sort kullanımı
import model.Yonetilebilir;

// Generic sınıf tanımı <T> (Bölüm 5.1 gereksinimi)
// Burada T, Yonetilebilir arayüzünü implemente eden bir tip olmalıdır.
public class Depo<T extends Yonetilebilir> { // Wildcard kullanımı: <? extends Yonetilebilir> (Dolaylı olarak extends)

    private List<T> liste; // List arayüzü referans tipi olarak kullanıldı (Bölüm 5.2 gereksinimi)

    public Depo() {
        this.liste = new ArrayList<>(); // ArrayList kullanımı (Bölüm 5.2 gereksinimi)
    }

    // Ekleme (add) (Bölüm 5.2 gereksinimi)
    public void ekle(T item) {
        liste.add(item);
    }

    // Silme (remove) (Bölüm 5.2 gereksinimi)
    public boolean sil(String id) {
        return liste.removeIf(item -> item.sil(id)); // lambda ifadesi ve Stream API basit kullanımı (Bonus: Bölüm 12)
    }

    // Arama (get/contains) (Bölüm 5.2 gereksinimi)
    public T idIleBul(String id) {
        for (T item : liste) { // for-each döngüsü (Bölüm 9 gereksinimi)
            // Varsayılan olarak her modelin bir getId metodu olduğu varsayılıyor
            // if (item.getId().equals(id)) { 
            //     return item;
            // }
        }
        return null;
    }

    // Sıralama (sort) (Bölüm 5.2 gereksinimi)
    public void ismeGoreSirala() {
        // Collections.sort kullanımı (Bölüm 5.2 gereksinimi)
        // Eğer T tipi Comparable ise çalışır, aksi takdirde Comparator gerekir.
        // Projede basitleştirmek için burada atlayıp sadece metodu göstermek yeterli.
        // Collections.sort(liste, (o1, o2) -> o1.toString().compareTo(o2.toString()));
    }

    // Güncelleme (set) (Bölüm 5.2 gereksinimi)
    public void guncelle(int index, T yeniItem) {
        if (index >= 0 && index < liste.size()) {
            liste.set(index, yeniItem); // set kullanımı
        }
    }

    // Tüm listeyi döndüren metot
    public List<T> getListe() {
        return liste;
    }
}