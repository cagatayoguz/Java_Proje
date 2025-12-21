package model;

public interface Kaydedilebilir extends Kampus {
    //Alt sınıflarda kullanılan metodlar
    boolean kaydet();
    boolean sil(String id);
    boolean guncelle();
}