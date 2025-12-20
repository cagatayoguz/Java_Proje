package model;

public abstract class Akademisyen extends Kisiler {

    private String unvan;
    private String uzmanlikAlani;

    public Akademisyen(String ad, String soyad, String unvan, String uzmanlikAlani) {
        super(ad, soyad);
        this.unvan = unvan;
        this.uzmanlikAlani = uzmanlikAlani;
    }

    @Override
    public String getPozisyon() { return unvan + " - " + uzmanlikAlani; }

    // Getterlar
    public String getUnvan() { return unvan; }
    public String getUzmanlikAlani() { return uzmanlikAlani; }
}