import entities.Inventory;
import entities.Product;

public class Main {
    public static void main(String[] args) {
        //envanter oluşturuyoruz
        Inventory myInventory = new Inventory();

        // ürünlerimiz
        Product p1 = new Product("P001", "Gaming Laptop", 25500.0, 15);
        Product p2 = new Product("P002", "Kablosuz Mouse", 450.0, 5); // Stok az, uyarı testi için ideal
        Product p3 = new Product("P003", "Mekanik Klavye", 1200.0, 25);

        //envantere ürün ekliyoruz
        myInventory.getProducts().add(p1);
        myInventory.getProducts().add(p2);
        myInventory.getProducts().add(p3);

        //console'a yazdırıp test ediyoruz.
        System.out.println("--- Mevcut Envanter Listesi ---");
        for (Product p : myInventory.getProducts()) {
            System.out.println("Ürün: " + p.getName() +
                    " | Fiyat: " + p.getPrice() + " TL" +
                    " | Stok: " + p.getStockQuantity());
        }

        //set örneği test edelim
        p1.setPrice(28000.0);
        System.out.println("\nGüncelleme Sonrası " + p1.getName() + " Yeni Fiyat: " + p1.getPrice() + " TL");
    }
}