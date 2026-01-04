package business;

import entities.Inventory;
import entities.Product;
import entities.Supplier;
import entities.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockManagerTest {

    private StockManager stockManager;
    private Inventory inventory;
    private Supplier supplier;
    private Product product;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        stockManager = new StockManager(inventory);

        supplier = new Supplier("S1", "ABC Ltd", "abc@mail.com");
        inventory.getSuppliers().add(supplier);

        product = new Product("P1", "Laptop", 10000.0, 10, supplier);
    }

    // ---------- PRODUCT TESTLERİ ----------

    @Test
    void testAddProduct() {
        stockManager.addProduct(product);
        assertEquals(1, inventory.getProducts().size());
    }

    @Test
    void testAddDuplicateProduct() {
        stockManager.addProduct(product);
        stockManager.addProduct(product);
        assertEquals(1, inventory.getProducts().size());
    }

    @Test
    void testFindProductById() {
        stockManager.addProduct(product);
        Product found = stockManager.findProduct("P1");
        assertNotNull(found);
        assertEquals("Laptop", found.getName());
    }

    @Test
    void testFindProductByName() {
        stockManager.addProduct(product);
        Product found = stockManager.findProduct("Laptop");
        assertNotNull(found);
    }

    @Test
    void testRemoveProduct() {
        stockManager.addProduct(product);
        stockManager.removeProduct("P1");
        assertTrue(inventory.getProducts().isEmpty());
    }

    @Test
    void testUpdateStock() {
        stockManager.addProduct(product);
        stockManager.updateStock("P1", 20);
        assertEquals(20, product.getStockQuantity());
    }

    // ---------- PRICE & VALUE ----------

    @Test
    void testGetAveragePrice() {
        stockManager.addProduct(product);
        stockManager.addProduct(new Product("P2", "Mouse", 1000, 5, supplier));

        double avg = stockManager.getAveragePrice();
        assertEquals(5500, avg);
    }

    @Test
    void testGetMostExpensiveProduct() {
        stockManager.addProduct(product);
        stockManager.addProduct(new Product("P2", "Mouse", 1000, 5, supplier));

        Product max = stockManager.getMostExpensiveProduct();
        assertEquals("Laptop", max.getName());
    }

    @Test
    void testGetCheapestProduct() {
        stockManager.addProduct(product);
        stockManager.addProduct(new Product("P2", "Mouse", 1000, 5, supplier));

        Product min = stockManager.getCheapestProduct();
        assertEquals("Mouse", min.getName());
    }

    @Test
    void testTotalInventoryValue() {
        stockManager.addProduct(product);
        double total = stockManager.getTotalInventoryValue();
        assertEquals(100000, total);
    }

    // ---------- ORDER TESTLERİ ----------

    @Test
    void testCreateOrderSuccess() {
        stockManager.addProduct(product);
        Order order = new Order("O1", product, 5);

        stockManager.createOrder(order);

        assertEquals(5, product.getStockQuantity());
    }

    @Test
    void testCreateOrderFailDueToStock() {
        stockManager.addProduct(product);
        Order order = new Order("O2", product, 50);

        stockManager.createOrder(order);

        assertEquals(10, product.getStockQuantity());
    }

    // ---------- SORT TESTLERİ ----------

    @Test
    void testSortProductsByPriceAsc() {
        stockManager.addProduct(product);
        stockManager.addProduct(new Product("P2", "Mouse", 1000, 5, supplier));

        stockManager.sortProductsByPriceAsc();

        assertEquals("Mouse", inventory.getProducts().get(0).getName());
    }

    @Test
    void testSortProductsByPriceDesc() {
        stockManager.addProduct(product);
        stockManager.addProduct(new Product("P2", "Mouse", 1000, 5, supplier));

        stockManager.sortProductsByPriceDesc();

        assertEquals("Laptop", inventory.getProducts().get(0).getName());
    }
}
