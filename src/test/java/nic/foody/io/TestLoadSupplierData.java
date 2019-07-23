/**
 * Unit tests for Loading Supplier Data using SAX.
 */

package nic.foody.io;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import nic.foody.model.Supplier;
import nic.foody.parsing.SupplierHandler;
import nic.foody.util.PhoneType;

/**
 * Unit tests for example SAX parsing code. Individual tests aren't usually
 * documented extensively since their namesare supposed to be informative and
 * their content is supposed to be small enough to be understood (e.g. aiming to
 * have one assertion per test).
 */
public class TestLoadSupplierData {
    private Map<String, Supplier> suppsLoaded;

    /**
     * Make sure each test starts with a clean copy of the loaded data - in case
     * previous tests have side-effects.
     */
    @Before
    public void testLoadSupplierFile() {
        String fName = "resources/data/Suppliers.xml";
       
        LoadData.loadSuppliers(fName);

        suppsLoaded = SupplierHandler.getSuppliers();
        /*
         * If necessary... for (Supplier sup : suppsLoaded.values()) {
         * System.out.println(sup); }
         */
        assertEquals("All XML records should be added", 4, suppsLoaded.size());
    }

    @Test
    public void testSomeKnownValues() {
        Supplier s = suppsLoaded.get("s1");
        assertEquals("First supplier name check", "Countup", s.name());
    }

    @Test
    public void testOptionalFields() {
        Supplier s = suppsLoaded.get("s2");
        assertEquals("Second supplier has no URL", Supplier.UNKNOWN_URL, s.url());
    }

    @Test
    public void testPhoneTypeAttribute() {
        Supplier s = suppsLoaded.get("s2");
        assertEquals("Loaded attribute", PhoneType.MOBILE, s.phoneType());
    }
}