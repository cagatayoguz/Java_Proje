package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // DateTimeFormatter kullanımı (Bölüm 6 gereksinimi)

public class TarihIslemleri {

    // Statik alan (Format, Bölüm 10)
    public static final DateTimeFormatter TARİH_FORMATI = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // Statik metot (Tarih Formatlama, Bölüm 6 gereksinimi)
    public static String tarihFormatla(LocalDate tarih) {
        return tarih.format(TARİH_FORMATI);

    }
    }