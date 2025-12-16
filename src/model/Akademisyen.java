package model;

import java.time.LocalDate;

public abstract class Akademisyen extends Kisiler {

    private String unvan;
    private String uzmanlikAlani;

    public Akademisyen(String ad, String soyad, String tcKimlikNo, LocalDate dogumTarihi,
                       String unvan, String uzmanlikAlani) {
        super(ad, soyad, tcKimlikNo, dogumTarihi);
        this.unvan = unvan;
        this.uzmanlikAlani = uzmanlikAlani;
    }

    @Override
    public String getPozisyon() { return unvan + " - " + uzmanlikAlani; }
    @Override
    public void bilgiSistemiErisim() { System.out.println("Akademisyen sistemine erisildi."); }

    // Getterlar
    public String getUnvan() { return unvan; }
    public String getUzmanlikAlani() { return uzmanlikAlani; }
}