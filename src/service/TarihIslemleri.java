package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TarihIslemleri {

    //beklirli tarih formatı tanımlandı
    public static final DateTimeFormatter TARIH_FORMATI = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // Tarihi String'e çeviren metot
    public static String tarihFormatla(LocalDate tarih) {
        return tarih.format(TARIH_FORMATI);
    }
}