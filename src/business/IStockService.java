package business;

import entities.Product;
import entities.Supplier;
import entities.Order;

public interface IStockService {

    // Ürün ile ilgili işlemler
    void addProduct(Product product);
    void removeProduct(String productId);
    void updateStock(String productId, int newQuantity);
    Product findProduct(String input);
    void checkLowStock(int threshold);
    void autoRestock(String productId, int threshold, int amount);
    double getAveragePrice();
    Product getMostExpensiveProduct();
    Product getCheapestProduct();
    double getTotalInventoryValue();

    // Dosya işlemleri
    void saveToFile();
    void loadFromFile();
    void saveSuppliersToFile();
    void saveProductsToFile();
    void loadProductsFromFile();
    void loadSuppliersFromFile();
    void saveOrdersToFile();
    void loadOrdersFromFile();

    // Tedarikçi işlemleri
    void addSupplier(Supplier supplier);
    Supplier findSupplier(String supplierId);

    // Sipariş işlemleri
    void createOrder(Order order);

    // Listeleme ve sıralama
    void sortProductsByPriceAsc();
    void sortProductsByPriceDesc();
    void listProducts();
    void listOrders();
}
