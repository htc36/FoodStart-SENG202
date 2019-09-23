package foodstart.manager.stock;

import foodstart.model.PhoneType;
import foodstart.model.stock.Supplier;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SupplierManagerTest {

    private SupplierManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new SupplierManager();
        manager.addSupplier(0, "Don'tEatKFC",
                "555-555-5555", PhoneType.HOME, "itSmellsAwful@truth.com",
                "www.AndTastesBad.com", "TestAddress");
    }

    @Test
    public void testAddSupplierBuilder() {
        assertFalse(manager.getSuppliers().isEmpty());
        manager.addSupplier(1, "TestName",
                "555-555-5555", PhoneType.WORK, "smith@example.com",
                "www.example.com", "TestAddress");
        assertEquals(2, manager.getSuppliers().size());
        manager.addSupplier(1, "Overwritten",
                "555-555-5555", PhoneType.WORK, "smith@example.com",
                "www.example.com", "TestAddress");
        assertEquals(2, manager.getSuppliers().size());
        assertEquals("Overwritten", manager.getSupplier(1).getSupplierName());
    }

    @Test
    public void testAddSupplierPrebuilt() {
        assertFalse(manager.getSuppliers().isEmpty());
        Supplier supplier1 = new Supplier(1, "TestName",
                "555-555-5555", PhoneType.WORK, "smith@example.com",
                "www.example.com", "TestAddress");
        manager.addSupplier(supplier1);
        assertEquals(2, manager.getSuppliers().size());
        Supplier supplier2 = new Supplier(1, "Overwritten",
                "555-555-5555", PhoneType.WORK, "smith@example.com",
                "www.example.com", "TestAddress");
        manager.addSupplier(supplier2);
        assertEquals(2, manager.getSuppliers().size());
        assertEquals("Overwritten", manager.getSupplier(1).getSupplierName());
    }

    @Test
    public void testAddSupplierPrebuiltNull() {
        assertEquals(1, manager.getSuppliers().size());
        manager.addSupplier(null);
        assertEquals(1, manager.getSuppliers().size());
    }

    @Test
    public void testAddSuppliers() {
        Map<Integer, Supplier> suppliers = new HashMap<Integer, Supplier>();
        Supplier supplier1 = new Supplier(1, "TestName1",
                "555-555-5555", PhoneType.WORK, "smith@example.com",
                "www.example.com", "TestAddress");
        suppliers.put(100, supplier1);
        Supplier supplier2 = new Supplier(2, "TestName2",
                "555-555-5555", PhoneType.WORK, "smith@example.com",
                "www.example.com", "TestAddress");
        suppliers.put(102, supplier2);
        manager.addSuppliers(suppliers);
        assertEquals(3, manager.getSuppliers().size());
    }

    @Test
    public void testAddSuppliersOverwriting() {
        Map<Integer, Supplier> suppliers = new HashMap<Integer, Supplier>();
        Supplier supplier1 = new Supplier(1, "TestName1",
                "555-555-5555", PhoneType.WORK, "smith@example.com",
                "www.example.com", "TestAddress");
        suppliers.put(100, supplier1);
        Supplier supplier2 = new Supplier(1, "TestName2",
                "555-555-5555", PhoneType.WORK, "smith@example.com",
                "www.example.com", "TestAddress");
        suppliers.put(102, supplier2);
        manager.addSuppliers(suppliers);
        assertEquals(2, manager.getSuppliers().size());
    }

    @Test
    public void testAddSuppliersNull() {
        Map<Integer, Supplier> suppliers = new HashMap<Integer, Supplier>();
        Supplier supplier1 = new Supplier(1, "TestName1",
                "555-555-5555", PhoneType.WORK, "smith@example.com",
                "www.example.com", "TestAddress");
        suppliers.put(100, supplier1);
        suppliers.put(102, null);
        manager.addSuppliers(suppliers);
        assertEquals(2, manager.getSuppliers().size());
    }

    @Test
    public void testRemoveSupplierValidId() {
        assertEquals(1, manager.getSuppliers().size());
        manager.removeSupplier(0);
        assertEquals(0, manager.getSuppliers().size());
    }

    @Test
    public void testRemoveSupplierInvalidId() {
        assertEquals(1, manager.getSuppliers().size());
        manager.removeSupplier(1);
        assertEquals(1, manager.getSuppliers().size());
    }

    @Test
    public void testRemoveAllSuppliers() {
        assertEquals(1, manager.getSuppliers().size());
        manager.removeAllSuppliers();
        assertEquals(0, manager.getSuppliers().size());
    }

    @Test
    public void testGetSupplierValidId() {
        Supplier supplier = manager.getSupplier(0);
        assertEquals(0, supplier.getId());
        assertEquals("Don'tEatKFC", supplier.getSupplierName());
    }

    @Test
    public void testGetSupplierInvalidId() {
        Supplier supplier = manager.getSupplier(5);
        assertNull(supplier);
    }

    @Test
    public void testGetSuppliers() {
        Map<Integer, Supplier> supplierMap = manager.getSuppliers();
        assertFalse(supplierMap.isEmpty());
        assertEquals(manager.getSupplier(0), supplierMap.get(0));
    }

    @Test
    public void testGetIngredientSet() {
        assertEquals(1, manager.getSupplierSet().size());
        manager.addSupplier(2, "TestName2",
                "555-555-5555", PhoneType.WORK, "smith@example.com",
                "www.example.com", "TestAddress");
        assertEquals(2, manager.getSupplierSet().size());
    }

    @Test
    public void testGetIngredientSetAbuse() {
        Supplier supplier = new Supplier(2, "TestName2",
                "555-555-5555", PhoneType.WORK, "smith@example.com",
                "www.example.com", "TestAddress");
        manager.getSuppliers().put(1, supplier);
        manager.getSuppliers().put(2, supplier);
        assertEquals(2, manager.getSupplierSet().size());
    }

    @Test
    public void testGenerateNewCode() {

    }

    @Test
    public void testGenerateNewID() {
        assertEquals(1, manager.generateNewCode());
        manager.addSupplier(1023, "TestName2",
                "555-555-5555", PhoneType.WORK, "smith@example.com",
                "www.example.com", "TestAddress");
        assertEquals(1024, manager.generateNewCode());
    }

    @Test
    public void testGenerateNewIDEmpty() {
        SupplierManager newManager = new SupplierManager();
        assertEquals(0, newManager.generateNewCode());
    }
}