package foodstart.model.stock;

import org.junit.Test;
import org.junit.Assert;
import foodstart.model.PhoneType;


public class SupplierTest {

    @Test
    public void testClone() {
        Supplier test = new Supplier(0, "Joe", "123", PhoneType.WORK, "", "", "Blahble Road");
        Supplier clone = test.clone();
        Assert.assertEquals(test, clone);
        Assert.assertNotSame(test, clone);
    }
    
}