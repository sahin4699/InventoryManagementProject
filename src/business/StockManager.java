package business;
import entities.Inventory;
import entities.Product;
import entities.Supplier;
import entities.Order;

import java.io.*;
import java.util.Comparator;
import java.util.List;       // EKLENDİ
import java.util.ArrayList; // EKLENDİ

public class StockManager implements IStockService{

    private static final String PRODUCTS_FILE = "products.txt";
    private static final String SUPPLIERS_FILE = "suppliers.txt";
    private static final String ORDERS_FILE = "orders.txt";

    private Inventory inventory;
    private List<Order> orders = new ArrayList<>();

    public StockManager(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void addProduct(Product product) {

        if (findProduct(product.getId()) != null) {
            System.out.println("[ERROR] Aynı ID ile ürün zaten mevcut: " + product.getId());
            return;
        }

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
    public Product findProduct(String input) {
        for (Product p : inventory.getProducts()) {

            // ID ile arama
            if (p.getId().equalsIgnoreCase(input)) {
                return p;
            }

            // Ürün adı ile arama
            if (p.getName().equalsIgnoreCase(input)) {
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

    public void autoRestock(Product product, int threshold, int amount) {

        if (product.getStockQuantity() < threshold) {
            int oldStock = product.getStockQuantity();
            product.setStockQuantity(oldStock + amount);

            System.out.println(
                    "[AUTO] " + product.getName()
                            + " stoğu kritikti (" + oldStock + "). "
                            + amount + " adet otomatik eklendi. "
                            + "Yeni stok: " + product.getStockQuantity()
            );
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
        saveSuppliersToFile();
        saveProductsToFile();
        saveOrdersToFile();
    }

    @Override
    public void loadFromFile() {
        loadSuppliersFromFile();  // önce tedarikçiler
        loadProductsFromFile();// sonra ürünler
        loadOrdersFromFile();
    }

    public void addSupplier(Supplier supplier) {
        for (Supplier p : inventory.getSuppliers()) {
            if (p.getSupplierId().equals(supplier.getSupplierId())) {
                System.out.println("[ERROR] Aynı ID ile tedarikçi zaten mevcut." + supplier.getSupplierId());
                return;
            }
        }

        inventory.getSuppliers().add(supplier);
        System.out.println("[İNFO] Tedarikçi eklendi."+ supplier.getSupplierId());
    }

    public Supplier findSupplier(String supplierId) {
        for (Supplier p : inventory.getSuppliers()) {
            if (p.getSupplierId().equals(supplierId)) {
                return p;
            }
        }
        return null;
    }

    public void createOrder(Order order){
        Product product = order.getProduct();
        int quantity = order.getQuantity();

        if(product.getStockQuantity() >= quantity){

            // stok düşür
            product.setStockQuantity(product.getStockQuantity() - quantity);

            // siparişi kaydet
            orders.add(order);

            System.out.println(
                    "[INFO] Sipariş oluşturuldu. Ürün: "
                            + product.getName()
                            + " | Miktar: " + quantity
                            + " | Kalan stok: " + product.getStockQuantity()
            );

            // 👇 GERÇEK OTOMATİK STOK YENİLEME
            autoRestock(product, 5, 20);

        } else {
            System.out.println("[ERROR] Yetersiz stok! Sipariş oluşturulamadı.");
        }
    }


    public void saveSuppliersToFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(SUPPLIERS_FILE))){
            for (Supplier p : inventory.getSuppliers()) {
                writer.write(
                        p.getSupplierId()+ "," + p.getCompanyName() + "," + p.getContactEmail());
                writer.newLine();
            }
            System.out.println("[İNFO] Tedarikçiler dosyaya kaydedildi.");
        }
        catch (IOException e) {
            System.out.println("[ERROR] Tedarikçi dosyas yazılamadı!!");
        }
    }

    public void saveProductsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCTS_FILE))) {

            for (Product p : inventory.getProducts()) {

                String supplierId = (p.getSupplier() != null)
                        ? p.getSupplier().getSupplierId()
                        : "";

                writer.write(
                        p.getId() + "," +
                                p.getName() + "," +
                                p.getPrice() + "," +
                                p.getStockQuantity() + "," +
                                supplierId
                );
                writer.newLine();
            }

            System.out.println("[INFO] Ürünler dosyaya kaydedildi.");

        } catch (IOException e) {
            System.out.println("[ERROR] Ürün dosyası yazılamadı!");
        }
    }

    public void loadProductsFromFile() {
        inventory.getProducts().clear();
        try(BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE))) {

            String line;
            while((line=reader.readLine()) !=null){
                String[] p = line.split(",");

                if (p.length == 5) {
                    String id = p[0];
                    String name = p[1];
                    double price = Double.parseDouble(p[2]);
                    int stockQuantity = Integer.parseInt(p[3]);
                    String supplierId = p[4];

                    Supplier supplier= findSupplier(supplierId);
                    Product product = new Product(id, name, price, stockQuantity, supplier);
                    inventory.getProducts().add(product);

                }

            }
            System.out.println("[İNFO] Ürünler dosyayadan yüklendi.");

        }catch (IOException e){
            System.out.println("[WARN] Ürün dosyası okunamadı.");
        }
    }

    public void loadSuppliersFromFile() {
        inventory.getSuppliers().clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(SUPPLIERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 3) {
                    inventory.getSuppliers().add(
                            new Supplier(p[0], p[1], p[2])
                    );
                }
            }
        } catch (IOException e) {
            System.out.println("[WARN] Tedarikçi dosyası okunamadı.");
        }
    }

    public void sortProductsByPriceAsc() {
        inventory.getProducts()
                .sort(Comparator.comparingDouble(Product::getPrice));
    }

    public void sortProductsByPriceDesc() {
        inventory.getProducts()
                .sort(Comparator.comparingDouble(Product::getPrice).reversed());
    }

    public void listProducts() {
        if (inventory.getProducts().isEmpty()) {
            System.out.println("Listelenecek ürün yok.");
            return;
        }

        System.out.println("  ID | ÜRÜN ADI  | FİYAT   |STOK| TEDARİKÇİ");
        for (Product p : inventory.getProducts()) {
            System.out.println(
                    p.getId() + " | " +
                            p.getName() + " | " +
                            p.getPrice() + " | " +
                            p.getStockQuantity() + " | " +
                            (p.getSupplier() != null ? p.getSupplier().getCompanyName() : "-")
            );
        }
    }

    public void saveOrdersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE))) {
            for (Order o : orders) {
                writer.write(
                        o.getOrderId() + "," +
                                o.getProduct().getId() + "," +
                                o.getQuantity()
                );
                writer.newLine();
            }
            System.out.println("[INFO] Siparişler dosyaya kaydedildi.");
        } catch (IOException e) {
            System.out.println("[ERROR] Sipariş dosyası yazılamadı!");
        }
    }

    public void loadOrdersFromFile() {
        orders.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");

                if (p.length == 3) {
                    String orderId = p[0];
                    String productId = p[1];
                    int quantity = Integer.parseInt(p[2]);

                    Product product = findProduct(productId);
                    if (product != null) {
                        orders.add(new Order(orderId, product, quantity));
                    }
                }
            }
            System.out.println("[INFO] Siparişler dosyadan yüklendi.");
        } catch (IOException e) {
            System.out.println("[WARN] Sipariş dosyası okunamadı.");
        }
    }

    public void listOrders() {
        if (orders.isEmpty()) {
            System.out.println("Listelenecek sipariş yok.");
            return;
        } else {
            System.out.println("ID | ÜRÜN | MİKTAR");
            for (Order o : orders) {
                System.out.println(
                        o.getOrderId() + " | " +
                                o.getProduct().getName() + " | " +
                                o.getQuantity()
                );
            }
        }
    }
}
