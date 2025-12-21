package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TarihIslemleri {

    // SENİN İSTEDİĞİN FORMAT BURADA TANIMLANDI
    public static final DateTimeFormatter TARIH_FORMATI = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // Tarihi String'e çeviren metot (Kaydederken kullanılır)
    public static String tarihFormatla(LocalDate tarih) {
        return tarih.format(TARIH_FORMATI);
    }

    // String'i Tarihe çeviren metot (Okurken kullanılır)
    public static LocalDate tarihCozumle(String tarihStr) {
        return LocalDate.parse(tarihStr, TARIH_FORMATI);
    }
}