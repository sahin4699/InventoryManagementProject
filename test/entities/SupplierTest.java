package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplierTest {

    private Supplier supplier;

    @BeforeEach
    void setUp() {
        supplier = new Supplier("S1", "ABC Ltd", "abc@mail.com");
    }

    // ---------- CONSTRUCTOR & GETTERS ----------

    @Test
    void testSupplierCreation() {
        assertEquals("S1", supplier.getSupplierId());
        assertEquals("ABC Ltd", supplier.getCompanyName());
        assertEquals("abc@mail.com", supplier.getContactEmail());
    }

    // ---------- SETTERS ----------

    @Test
    void testSetCompanyName() {
        supplier.setCompanyName("XYZ Corp");
        assertEquals("XYZ Corp", supplier.getCompanyName());
    }

    @Test
    void testSetContactEmail() {
        supplier.setContactEmail("xyz@mail.com");
        assertEquals("xyz@mail.com", supplier.getContactEmail());
    }
}
