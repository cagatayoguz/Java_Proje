package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // DateTimeFormatter kullanımı (Bölüm 6 gereksinimi)

public class TarihIslemleri {

    // Tarih formatı belirledik
    public static final DateTimeFormatter TARİH_FORMATI = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // Tarih format işlemi
    public static String tarihFormatla(LocalDate tarih) {
        return tarih.format(TARİH_FORMATI);

    }
    }