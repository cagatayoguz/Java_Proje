package service;

import model.Kaydedilebilir;
import java.util.ArrayList;
import java.util.Collections; // Sıralama için gerekli
import java.util.List;

// Generic Sınıf <T>
public class Depo<T extends Kaydedilebilir> {


    private List<T> liste;

    public Depo() {
        this.liste = new ArrayList<>();
    }

    public void ekle(T item) {
        liste.add(item);
    }

    public void ekle(List<T> topluListe) {
        // Tek tek eklemek yerine koca listeyi ekler
        this.liste.addAll(topluListe);
    }

    public void ismeGoreSirala() {
        // Nesnelerin toString() metoduna (Kitap Adına) göre sıralar
        Collections.sort(liste, (o1, o2) -> o1.toString().compareToIgnoreCase(o2.toString()));
    }

    public List<T> getListe() {
        return liste;
    }
}