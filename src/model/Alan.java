package model;

public abstract class Alan implements Yazdirilabilir,Kaydedilebilir {

    private String adi;
    private int kapasite;

    //Alan constructor
    public Alan(String adi, int kapasite) {
        this.adi = adi;
        this.kapasite = kapasite;
    }

    // --- Abstract Metotlar (Miras alanlar doldurmak zorunda) ---
    public abstract boolean kapasiteKontrol(int mevcutSayi);

    // Getter
    public int getAdi() {
        return adi.length();
    }
    public void setAdi(String adi) {
        this.adi = adi;
    }
    public int getKapasite() { return kapasite; }
}