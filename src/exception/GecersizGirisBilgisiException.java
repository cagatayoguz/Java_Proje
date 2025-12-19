package exception;

/**
 * Bu sınıf, kullanıcı formlarda eksik veya hatalı bilgi girdiğinde
 * fırlatılacak olan özel hata sınıfıdır.
 */
public class GecersizGirisBilgisiException extends Exception {

    // 1. Özel mesaj ile hata fırlatma (Örn: "Öğrenci No boş olamaz!")
    public GecersizGirisBilgisiException(String mesaj) {
        super(mesaj);
    }

    // 2. Mesajsız hata fırlatma (Varsayılan mesaj kullanılır)
    public GecersizGirisBilgisiException() {
        super("Girdiğiniz bilgiler geçersiz veya eksik. Lütfen kontrol ediniz.");
    }
}