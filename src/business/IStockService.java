package business;

import entities.Product;

public interface IStockService {
    void addProduct(Product product);

    void removeProduct(String productId);

    void updateStock(String productId, int newQuantity);

    Product findProduct(String productId);

    void checkLowStock(int threshold);
    void autoRestock(String productId, int threshold, int amount);
}

