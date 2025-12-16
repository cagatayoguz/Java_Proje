package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // DateTimeFormatter kullanımı (Bölüm 6 gereksinimi)

public class Formatlayici {

    // Statik alan (Format, Bölüm 10)
    public static final DateTimeFormatter TARİH_FORMATI = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Statik metot (Tarih Formatlama, Bölüm 6 gereksinimi)
    public static String tarihFormatla(LocalDate tarih) {
        return tarih.format(TARİH_FORMATI);
    }

    // Statik metot (LocalDateTime Formatlama, Bölüm 6 gereksinimi)
    public static String tarihSaatFormatla(LocalDateTime tarihSaat) {
        return tarihSaat.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
}