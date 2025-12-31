import business.IStockService;
import business.StockManager;
import entities.Inventory;
import entities.Product;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        IStockService stockManager = new StockManager(inventory);
        Scanner scanner = new Scanner(System.in);

        // Program açılışında dosyadan yükle
        stockManager.loadFromFile();

        // Program beklenmedik şekilde kapanırsa da veriyi kaydet
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    stockManager.saveToFile();
                    System.out.println("[INFO] Program kapatılırken veriler kaydedildi");
                })
        );


        int choice;

        do {
            System.out.println();
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║        ENVANTER YÖNETİM SİSTEMİ       ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1 │ Ürün Ekle                         ║");
            System.out.println("║ 2 │ Ürün Sil                          ║");
            System.out.println("║ 3 │ Stok Güncelle                     ║");
            System.out.println("║ 4 │ Kritik Stok Kontrolü              ║");
            System.out.println("║ 5 │ Ortalama Ürün Fiyatı              ║");
            System.out.println("║ 6 │ En Pahalı Ürün                    ║");
            System.out.println("║ 7 │ En Ucuz Ürün                      ║");
            System.out.println("║ 8 │ Toplam Envanter Değeri            ║");
            System.out.println("║ 9 │ Otomatik Stok Yenileme            ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 0 │ Çıkış ve Kaydet                   ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Seçiminiz: ");


            choice = scanner.nextInt();
            scanner.nextLine(); // buffer temizleme

            switch (choice) {

                case 1:
                    System.out.print("Ürün ID: ");
                    String id = scanner.nextLine();

                    System.out.print("Ürün Adı: ");
                    String name = scanner.nextLine();

                    System.out.print("Fiyat: ");
                    double price = scanner.nextDouble();

                    System.out.print("Stok Adedi: ");
                    int stock = scanner.nextInt();

                    stockManager.addProduct(new Product(id, name, price, stock));
                    break;

                case 2:
                    System.out.print("Silinecek Ürün ID: ");
                    String removeId = scanner.nextLine();
                    stockManager.removeProduct(removeId);
                    break;

                case 3:
                    System.out.print("Ürün ID: ");
                    String updateId = scanner.nextLine();

                    System.out.print("Yeni Stok Miktarı: ");
                    int newStock = scanner.nextInt();

                    stockManager.updateStock(updateId, newStock);
                    break;

                case 4:
                    System.out.print("Kritik stok eşiği: ");
                    int threshold = scanner.nextInt();
                    stockManager.checkLowStock(threshold);
                    break;

                case 5:
                    System.out.println("Ortalama Ürün Fiyatı: " + stockManager.getAveragePrice());
                    break;

                case 6:
                    Product max = stockManager.getMostExpensiveProduct();
                    if (max != null)
                        System.out.println("En pahalı ürün: " + max.getName() + " - " + max.getPrice());
                    else
                        System.out.println("Ürün yok.");
                    break;

                case 7:
                    Product min = stockManager.getCheapestProduct();
                    if (min != null)
                        System.out.println("En ucuz ürün: " + min.getName() + " - " + min.getPrice());
                    else
                        System.out.println("Ürün yok.");
                    break;

                case 8:
                    System.out.println("Toplam Envanter Değeri: " + stockManager.getTotalInventoryValue());
                    break;

                case 9:
                    System.out.print("Ürün ID: ");
                    String autoId = scanner.nextLine();

                    System.out.print("Eşik değer: ");
                    int autoThreshold = scanner.nextInt();

                    System.out.print("Eklenecek miktar: ");
                    int amount = scanner.nextInt();

                    stockManager.autoRestock(autoId, autoThreshold, amount);
                    break;

                case 0:
                    stockManager.saveToFile();
                    System.out.println("Çıkış yapıldı. Veriler kaydedildi.");
                    break;

                default:
                    System.out.println("Geçersiz seçim!");
            }

        } while (choice != 0);

        scanner.close();
    }
}
