package business;
import entities.Inventory;
import entities.Product;

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
}
