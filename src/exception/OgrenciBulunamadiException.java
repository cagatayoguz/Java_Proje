package exception;

public class OgrenciBulunamadiException extends Exception {
    public OgrenciBulunamadiException(String ogrenciNo) {
        super("Belirtilen ogrenci numarasi (" + ogrenciNo + ") sistemde kayitli degildir.");
    }
}