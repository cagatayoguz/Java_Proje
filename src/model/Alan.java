package model;

public abstract class Alan {

    private String adi;
    private int kapasite;

    public Alan(String adi, int kapasite) {
        this.adi = adi;
        this.kapasite = kapasite;
    }

    // --- Abstract Metotlar (Miras alanlar doldurmak zorunda) ---
    public abstract String kullanimAmaci();
    public abstract boolean kapasiteKontrol(int mevcutSayi);

    // Getter
    public int getKapasite() { return kapasite; }
}