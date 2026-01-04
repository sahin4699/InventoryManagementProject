package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;
    private Product product;
    private Supplier supplier;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        supplier = new Supplier("S1", "ABC Ltd", "abc@mail.com");
        product = new Product("P1", "Laptop", 15000.0, 10, supplier);
    }

    // ---------- INITIAL STATE ----------

    @Test
    void testInventoryInitiallyEmpty() {
        assertTrue(inventory.getProducts().isEmpty());
        assertTrue(inventory.getSuppliers().isEmpty());
    }

    // ---------- PRODUCTS ----------

    @Test
    void testAddProductToInventory() {
        inventory.getProducts().add(product);
        assertEquals(1, inventory.getProducts().size());
        assertEquals(product, inventory.getProducts().get(0));
    }

    // ---------- SUPPLIERS ----------

    @Test
    void testAddSupplierToInventory() {
        inventory.getSuppliers().add(supplier);
        assertEquals(1, inventory.getSuppliers().size());
        assertEquals(supplier, inventory.getSuppliers().get(0));
    }

    // ---------- MULTIPLE ITEMS ----------

    @Test
    void testMultipleProducts() {
        inventory.getProducts().add(product);
        inventory.getProducts().add(
                new Product("P2", "Mouse", 1000.0, 20, supplier)
        );

        assertEquals(2, inventory.getProducts().size());
    }

    @Test
    void testMultipleSuppliers() {
        inventory.getSuppliers().add(supplier);
        inventory.getSuppliers().add(
                new Supplier("S2", "XYZ Ltd", "xyz@mail.com")
        );

        assertEquals(2, inventory.getSuppliers().size());
    }
}
