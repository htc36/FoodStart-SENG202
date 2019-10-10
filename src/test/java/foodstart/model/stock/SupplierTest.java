package foodstart.model.stock;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;

import foodstart.model.PhoneType;


/**
 * 
 * @author Alan Wang
 *
 */
public class SupplierTest {
    
    Supplier testSupplier, nullSupplier;
    
    @Before
    public void setUp() {
        testSupplier = new Supplier(0, "Joe", "123", PhoneType.WORK, "", "", "Blahble Road");
        nullSupplier = new Supplier(0, null, null, null, null, null, null);
    }

    @Test
    public void testClone() {
        Supplier clone = testSupplier.clone();
        assertEquals(testSupplier, clone);
        assertNotSame(testSupplier, clone);
        
    }
    
    
    @Test 
    public void testEqualsIfSame() {
        Object test2 = testSupplier;
        assertSame(testSupplier, test2);
        assertEquals(testSupplier, test2);
    }
    
    @Test 
    public void testNotEqualsIfNull() {
        Object nullObject = null;
        assertNotEquals(testSupplier, nullObject);
    }
    
    @Test 
    public void testNotEqualsIfDifferentClass() {
        Object notSupplier = new String("not  a supplier");
        assertNotEquals(testSupplier, notSupplier);
    }
    
    @Test 
    public void testNotEqualsIfOnlySelfNullAddress() {
        Supplier other = new Supplier(0, null, null, null, null, null, "");
        assertNotEquals(nullSupplier, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentAddress() {
        Supplier other = new Supplier(0, "Joe", "123", PhoneType.WORK, "", "", "Blahble Road 2");
        assertNotEquals(testSupplier, other);
    }
    
    @Test 
    public void testNotEqualsIfOnlySelfNullEmail() {
        Supplier other = new Supplier(0, null, null, null, "", null, null);
        assertNotEquals(nullSupplier, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentEmail() {
        Supplier other = new Supplier(0, "Joe", "123", PhoneType.WORK, "wibble", "", "Blahble Road");
        assertNotEquals(testSupplier, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentID() {
        Supplier other = new Supplier(2, "Joe", "123", PhoneType.WORK, "", "", "Blahble Road");
        assertNotEquals(testSupplier, other);
    }
    
    @Test 
    public void testNotEqualsIfOnlySelfNullPhone() {
        Supplier other = new Supplier(0, null, "", null, null, null, null);
        assertNotEquals(nullSupplier, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentPhone() {
        Supplier other = new Supplier(0, "Joe", "12123", PhoneType.WORK, "", "", "Blahble Road");
        assertNotEquals(testSupplier, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentPhoneType() {
        Supplier other = new Supplier(0, "Joe", "123", PhoneType.HOME, "", "", "Blahble Road");
        assertNotEquals(testSupplier, other);
    }
    
    @Test 
    public void testNotEqualsIfOnlySelfNullName() {
        Supplier other = new Supplier(0, "", null, null, null, null, null);
        assertNotEquals(nullSupplier, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentName() {
        Supplier other = new Supplier(0, "Je", "123", PhoneType.WORK, "", "", "Blahble Road");
        assertNotEquals(testSupplier, other);
    }
    
    @Test 
    public void testNotEqualsIfOnlySelfNullURL() {
        Supplier other = new Supplier(0, null, null, null, null, "", null);
        assertNotEquals(nullSupplier, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentURL() {
        Supplier other = new Supplier(0, "Joe", "123", PhoneType.WORK, "", "nottest", "Blahble Road");
        assertNotEquals(testSupplier, other);
    }
    
    @Test 
    public void testEqualsIfAllFieldsNull() {
        Supplier other = new Supplier(0, null, null, null, null, null, null);
        assertEquals(nullSupplier, other);
    }
    
    @Test 
    public void testEqualsIfAllFieldsMatch() {
        Supplier other = new Supplier(0, "Joe", "123", PhoneType.WORK, "", "", "Blahble Road");
        assertEquals(testSupplier, other);
    }
}