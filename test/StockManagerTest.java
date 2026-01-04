import business.StockManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import entities.Product;
import entities.Inventory; // Bunu eklememiz gerekiyordu!

public class StockManagerTest {

    @Test
     void addProduct() {
        // 1. HAZIRLIK
        // DOĞRUSU BU: StockManager, 'Inventory' (Envanter) ile çalışıyor.
        StockManager manager = new StockManager(new Inventory());

        // Ürün oluşturma (Tedarikçi kısmı null, bu doğru)
        Product yeniUrun = new Product("P001", "Oyuncak Araba", 100.0, 50, null);

        // 2. EYLEM
        manager.addProduct(yeniUrun);

        // 3. KONTROL
        Product bulunanUrun = manager.findProduct("P001");

        assertNotNull(bulunanUrun, "Ürün null döndü, eklenemedi!");
        assertEquals("Oyuncak Araba", bulunanUrun.getName(), "Ürün ismi yanlış!");
    }

    @Test
    void removeProduct() {
        // 1. HAZIRLIK: Önce silinecek bir ürün ekleyip ortamı hazırlıyoruz.
        StockManager manager = new StockManager(new Inventory());
        Product silinecekUrun = new Product("P002", "Silgi", 5.0, 100, null);
        manager.addProduct(silinecekUrun);

        // 2. EYLEM: Ürünü ID'si ile siliyoruz.
        manager.removeProduct("P002");

        // 3. KONTROL: Ürünü aradığımızda BULUNAMAMASI (null olması) lazım.
        Product sonuc = manager.findProduct("P002");

        assertNull(sonuc, "Hata: Ürün silinmedi, hala sistemde duruyor!");
    }
    @Test
    void findProduct() {
        // 1. HAZIRLIK: Test için listeye bir ürün koyuyoruz.
        StockManager manager = new StockManager(new Inventory());
        Product urun = new Product("P003", "Not Defteri", 25.0, 100, null);
        manager.addProduct(urun);

        // 2. EYLEM: Ürünü ID'si ile sistemden istiyoruz.
        Product bulunan = manager.findProduct("P003");

        // 3. KONTROL:
        // Gelen ürün boş (null) olmamalı ve ismi doğru gelmeli.
        assertNotNull(bulunan, "Hata: Ürün sistemde var ama bulunamadı!");
        assertEquals("Not Defteri", bulunan.getName(), "Hata: Yanlış ürün getirildi!");
    }
    @Test
    void checkLowStock() {
        // 1. HAZIRLIK: Biri az stoklu (5 adet), biri çok stoklu (50 adet) iki ürün ekliyoruz.
        StockManager manager = new StockManager(new Inventory());

        Product azStokluUrun = new Product("P004", "Tükenen Kalem", 10.0, 5, null);
        Product cokStokluUrun = new Product("P005", "Dolu Çanta", 500.0, 50, null);

        manager.addProduct(azStokluUrun);
        manager.addProduct(cokStokluUrun);

        // 2. EYLEM: "Stoğu 10'dan az olanları uyar" diyoruz.
        System.out.println("--- Stok Kontrol Testi Başladı ---");
        manager.checkLowStock(10);
        System.out.println("--- Stok Kontrol Testi Bitti ---");

        // 3. KONTROL: Bu test yeşil yanacaktır.
        // Aşağıdaki konsol ekranında "Tükenen Kalem için stok az!" gibi bir uyarı görmelisin.
    }
}