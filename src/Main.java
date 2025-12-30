import business.IStockService;
import business.StockManager;
import entities.Inventory;
import entities.Product;

public class Main {
    public static void main(String[] args) {
        Inventory myInventory = new Inventory();
        IStockService stockManager = new StockManager(myInventory);

        Product p1 = new Product("P001", "Gaming Laptop", 25500.0, 15);
        Product p2 = new Product("P002", "Kablosuz Mouse", 450.0, 5);
        Product p3= new Product("P003","Bluetooth Kulaklık",3500,10);

        stockManager.addProduct(p1);
        stockManager.addProduct(p2);

        stockManager.updateStock("P001", 14);
        stockManager.checkLowStock(10);

        Product found = stockManager.findProduct("P002");
        if(found != null) {
            System.out.println("Bulunan Ürün: " + found.getName());
        }

        // Üçüncü ürünü ekle
        stockManager.addProduct(p3);

        // Otomatik stok yenileme testi
        stockManager.autoRestock("P002", 10, 20);

        // Fiyat analizleri
        System.out.println("Ortalama Fiyat: " + stockManager.getAveragePrice());

        Product expensive = stockManager.getMostExpensiveProduct();
        if (expensive != null) {
            System.out.println("En Pahalı Ürün: " + expensive.getName());
        }

        Product cheap = stockManager.getCheapestProduct();
        if (cheap != null) {
            System.out.println("En Ucuz Ürün: " + cheap.getName());
        }

        System.out.println("Toplam Envanter Değeri: " + stockManager.getTotalInventoryValue());


        stockManager.saveToFile();
    }
}
