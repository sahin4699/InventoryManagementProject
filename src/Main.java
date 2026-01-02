import business.IStockService;
import business.StockManager;
import entities.Inventory;
import entities.Product;
import entities.Supplier;
import entities.Order;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        StockManager stockManager = new StockManager(inventory);
        Scanner scanner = new Scanner(System.in);

        stockManager.loadFromFile();

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    stockManager.saveToFile();
                    System.out.println("[INFO] Program kapatılırken veriler kaydedildi.");
                })
        );

        int choice;

        do {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║        📦 ENVANTER YÖNETİM SİSTEMİ    ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1 │ ➕ Ürün Ekle                      ║");
            System.out.println("║ 2 │ 🗑️ Ürün Sil                       ║");
            System.out.println("║ 3 │ 🔄 Stok Güncelle                  ║");
            System.out.println("║ 4 │ ⚠️ Kritik Stok Kontrolü           ║");
            System.out.println("║ 5 │ 📊 Ortalama Ürün Fiyatı           ║");
            System.out.println("║ 6 │ 💰 En Pahalı Ürün                 ║");
            System.out.println("║ 7 │ 🪙 En Ucuz Ürün                   ║");
            System.out.println("║ 8 │ 🧮 Toplam Envanter Değeri         ║");
            System.out.println("║ 9 │ 🤖 Otomatik Stok Yenileme         ║");
            System.out.println("║10 │ 🚚 Tedarikçi Ekle                 ║");
            System.out.println("║11 │ 📋 Tedarikçileri Listele          ║");
            System.out.println("║12 │ 📝 Sipariş Oluştur                ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 0 │ 🚪 Çıkış ve Kaydet                ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Seçiminiz: ");


            choice = scanner.nextInt();
            scanner.nextLine();

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
                    scanner.nextLine();

                    System.out.print("Tedarikçi ID: ");
                    String supId = scanner.nextLine();

                    Supplier supplier = stockManager.findSupplier(supId);
                    if (supplier == null) {
                        System.out.println("[ERROR] Tedarikçi bulunamadı!");
                        break;
                    }

                    stockManager.addProduct(
                            new Product(id, name, price, stock, supplier)
                    );
                    break;

                case 2:
                    System.out.print("Silinecek Ürün ID: ");
                    stockManager.removeProduct(scanner.nextLine());
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
                    stockManager.checkLowStock(scanner.nextInt());
                    break;

                case 5:
                    System.out.println("Ortalama Ürün Fiyatı: " + stockManager.getAveragePrice());
                    break;

                case 6:
                    Product max = stockManager.getMostExpensiveProduct();
                    System.out.println(max != null
                            ? max.getName() + " - " + max.getPrice()
                            : "Ürün yok.");
                    break;

                case 7:
                    Product min = stockManager.getCheapestProduct();
                    System.out.println(min != null
                            ? min.getName() + " - " + min.getPrice()
                            : "Ürün yok.");
                    break;

                case 8:
                    System.out.println("Toplam Envanter Değeri: "
                            + stockManager.getTotalInventoryValue());
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

                case 10:
                    System.out.print("Tedarikçi ID: ");
                    String sid = scanner.nextLine();

                    System.out.print("Firma Adı: ");
                    String cname = scanner.nextLine();

                    System.out.print("E-posta: ");
                    String mail = scanner.nextLine();

                    stockManager.addSupplier(
                            new Supplier(sid, cname, mail)
                    );
                    break;

                case 11:
                    System.out.println("--- TEDARİKÇİLER ---");
                    for (Supplier s : inventory.getSuppliers()) {
                        System.out.println(
                                s.getSupplierId() + " | " +
                                        s.getCompanyName() + " | " +
                                        s.getContactEmail()
                        );
                    }
                    break;

                case 12:
                    System.out.print("Ürün ID: ");
                    String pid = scanner.nextLine();

                    Product p = stockManager.findProduct(pid);
                    if (p == null) {
                        System.out.println("[ERROR] Ürün bulunamadı!");
                        break;
                    }

                    System.out.print("Sipariş Miktarı: ");
                    int qty = scanner.nextInt();

                    stockManager.createOrder(
                            new Order("ORD-" + System.currentTimeMillis(), p, qty)
                    );
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
