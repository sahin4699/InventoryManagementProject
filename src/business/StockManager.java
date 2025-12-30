package business;
import entities.Inventory;
import entities.Product;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StockManager implements IStockService{
    private Inventory inventory;

    public StockManager(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void addProduct(Product product) {
        inventory.getProducts().add(product);
        System.out.println("[INFO] Ürün eklendi: " + product.getName());
    }

    @Override
    public void removeProduct(String productId) {
        Product productToRemove = findProduct(productId);
        if (productToRemove != null) {
            inventory.getProducts().remove(productToRemove);
            System.out.println("[INFO] Ürün silindi: " + productToRemove.getName());
        } else {
            System.out.println("[ERROR] Silinecek ürün bulunamadı!");
        }
    }

    @Override
    public void updateStock(String productId, int newQuantity) {
        Product product = findProduct(productId);
        if (product != null) {
            int oldStock = product.getStockQuantity();
            product.setStockQuantity(newQuantity);
            System.out.println("[UPDATE] " + product.getName() + " stoku güncellendi. (" + oldStock + " -> " + newQuantity + ")");
        } else {
            System.out.println("[ERROR] Stok güncellenecek ürün bulunamadı!");
        }
    }

    @Override
    public Product findProduct(String productId) {
        for (Product p : inventory.getProducts()) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void checkLowStock(int threshold) {
        System.out.println("\n--- KRİTİK STOK UYARISI (< " + threshold + ") ---");
        boolean found = false;
        for (Product p : inventory.getProducts()) {
            if (p.getStockQuantity() < threshold) {
                System.out.println("Uyarı: " + p.getName() + " stoku kritik seviyede! (Adet: " + p.getStockQuantity() + ")");
                found = true;
            }
        }
        if (!found) {
            System.out.println("Tüm stoklar güvenli seviyede.");
        }
    }

    @Override
    public void autoRestock(String productId, int threshold, int amount) {
        Product product = findProduct(productId);

        if (product != null) {
            if (product.getStockQuantity() < threshold) {
                int newQuantity = product.getStockQuantity() + amount;
                product.setStockQuantity(newQuantity);
                System.out.println(product.getName() + " stoğu azaldı. Otomatik olarak " + amount + " adet eklendi.");
                System.out.println("Yeni Stok: " + newQuantity);
            } else {
                System.out.println("Stok yeterli, yenilemeye gerek yok.");
            }
        } else {
            System.out.println("Ürün bulunamadı!");
        }
    }

    @Override
    public double getAveragePrice() {
        double total = 0;
        for (Product p : inventory.getProducts()) {
            total += p.getPrice();
        }
        return inventory.getProducts().isEmpty() ? 0 : total / inventory.getProducts().size();
    }

    @Override
    public Product getMostExpensiveProduct() {
        if (inventory.getProducts().isEmpty()) return null;

        Product max = inventory.getProducts().get(0);
        for (Product p : inventory.getProducts()) {
            if (p.getPrice() > max.getPrice()) {
                max = p;
            }
        }
        return max;
    }

    @Override
    public Product getCheapestProduct() {
        if (inventory.getProducts().isEmpty()) return null;

        Product min = inventory.getProducts().get(0);
        for (Product p : inventory.getProducts()) {
            if (p.getPrice() < min.getPrice()) {
                min = p;
            }
        }
        return min;
    }

    @Override
    public double getTotalInventoryValue() {
        double totalValue = 0;
        for (Product p : inventory.getProducts()) {
            totalValue += p.getPrice() * p.getStockQuantity();
        }
        return totalValue;
    }

    @Override
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inventory.txt"))) {

            for (Product p : inventory.getProducts()) {
                writer.write(
                        p.getId() + "," +
                                p.getName() + "," +
                                p.getPrice() + "," +
                                p.getStockQuantity()
                );
                writer.newLine();
            }

            System.out.println("[INFO] Envanter dosyaya başarıyla kaydedildi.");

        } catch (IOException e) {
            System.out.println("[ERROR] Dosya yazma hatası!");
        }
    }

    @Override
    public void loadFromFile() {
        inventory.getProducts().clear();

        try (java.io.BufferedReader reader =
                     new java.io.BufferedReader(new java.io.FileReader("inventory.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    String id = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int stock = Integer.parseInt(parts[3]);

                    Product product = new Product(id, name, price, stock);
                    inventory.getProducts().add(product);
                }
            }

            System.out.println("[INFO] Envanter dosyadan başarıyla yüklendi.");

        } catch (IOException e) {
            System.out.println("[ERROR] Dosya okuma hatası!");
        }
    }
}


