package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;
    private Supplier supplier;

    @BeforeEach
    void setUp() {
        supplier = new Supplier("S1", "ABC Ltd", "abc@mail.com");
        product = new Product("P1", "Laptop", 15000.0, 10, supplier);
    }

    // ---------- CONSTRUCTOR & GETTERS ----------

    @Test
    void testProductCreation() {
        assertEquals("P1", product.getId());
        assertEquals("Laptop", product.getName());
        assertEquals(15000.0, product.getPrice());
        assertEquals(10, product.getStockQuantity());
        assertEquals(supplier, product.getSupplier());
    }

    // ---------- SETTERS ----------

    @Test
    void testSetName() {
        product.setName("Gaming Laptop");
        assertEquals("Gaming Laptop", product.getName());
    }

    @Test
    void testSetPrice() {
        product.setPrice(18000.0);
        assertEquals(18000.0, product.getPrice());
    }

    @Test
    void testSetStockQuantityValid() {
        product.setStockQuantity(20);
        assertEquals(20, product.getStockQuantity());
    }

    @Test
    void testSetStockQuantityNegative() {
        product.setStockQuantity(-5);
        // negatif kabul edilmemeli, eski değer kalmalı
        assertEquals(10, product.getStockQuantity());
    }
}
