package main;

import service.KutuphaneServisi;
import gui.AnaMenuGUI;

public class Main { // Ana Sınıf (main metodu içeren, projeyi başlatan sınıf)

    private static KutuphaneServisi kutuphaneServisi;

    public static void main(String[] args) {
        System.out.println("Kampüs Yönetim ve Bilgi Sistemi Başlatılıyor...");

        // Kütüphane Servisini başlat ve başlangıç verilerini yükle
        try {
            kutuphaneServisi = new KutuphaneServisi();
            System.out.println("Kütüphane verileri yüklendi. Toplam Kitap: " + kutuphaneServisi.tumKitaplariGetir().size());
        } catch (Exception e) {
            System.err.println("Veri yükleme sırasında kritik hata: " + e.getMessage());
            return;
        }

        // Swing Arayüzünü Başlat
        java.awt.EventQueue.invokeLater(() -> {
            new AnaMenuGUI().setVisible(true);
        });
    }
}