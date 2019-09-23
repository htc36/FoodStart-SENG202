package foodstart.manager.xml;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ImportFailureException;
import foodstart.model.DataType;
import foodstart.model.PhoneType;
import foodstart.model.stock.Ingredient;
import foodstart.model.stock.Supplier;

public class XMLSupplierParserTest {
    
    Persistence persistence;
    File dataFile;
    HashMap<Integer, Supplier> expectedSuppliers = new HashMap<Integer, Supplier>();
    Map<Integer, Supplier> actualSuppliers;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        
        
    }
    
    @Before
    public void setUp() throws Exception {
        persistence = new XMLPersistence();
        expectedSuppliers.clear();
        actualSuppliers = Managers.getSupplierManager().getSuppliers();
    }

    @After
    public void tearDown() throws Exception {
        Managers.getSupplierManager().removeAllSuppliers();
        persistence = null;
        dataFile = null;
    }

    @Test
    public void normalDataTest() {
        dataFile = new File("resources/data/supplier_xml_files/SupplierParserTestDataNormal.xml");
        persistence.importFile(dataFile, DataType.SUPPLIER);
        expectedSuppliers.put(1, new Supplier(1, "Countup", "3451234", PhoneType.WORK, "", "countup.co.nz/high", "12 High Street, Christchurch"));
        expectedSuppliers.put(2, new Supplier(2, "Funky Kumquat", "021352176", PhoneType.MOBILE, "maryC@fkveg.co.nz", "", "125 Woodside Lane, Christchurch"));
        expectedSuppliers.put(3, new Supplier(3, "Admiral Benjamin's Seafoods","021345666", PhoneType.MOBILE, "bigben@cfoods.co.nz", "cfoods.co.nz", "86 Low Street, Christchurch"));
        expectedSuppliers.put(4, new Supplier(4, "Dave's Drinks", "3515666", PhoneType.HOME, "", "", "Unit 5, Bert's Business Park, Christchurch"));
        assertTrue("Check suppliers loaded", actualSuppliers.size() == 4);
        for (Integer id: expectedSuppliers.keySet()) {
            Supplier expectedSupplier = expectedSuppliers.get(id), actualSupplier = actualSuppliers.get(id);
            assertTrue(String.format("Normal data ID%d import test", id), expectedSupplier.equals(actualSupplier));
        }
    }
    
    @Test
    public void wrongDataFormatTest() {
        dataFile = new File("resources/data/supplier_xml_files/SupplierParserTestDataWrongFormat.xml");
        try {
            persistence.importFile(dataFile, DataType.SUPPLIER);
            fail("Bad file format; should have thrown an ImportFailureException");
        } catch (ImportFailureException e){;
        } 
    }
    
    @Test
    public void noSuppliersDataTest() {
        dataFile = new File("resources/data/supplier_xml_files/SupplierParserTestDataNoSuppliers.xml");
        persistence.importFile(dataFile, DataType.SUPPLIER);
        assertEquals("SupplierManager should have no suppliers", 0, actualSuppliers.size());
    }
    
    @Test
    public void emptySuppliersDataTest() {
        dataFile = new File("resources/data/supplier_xml_files/SupplierParserTestDataEmptySuppliers.xml");
        try {
            persistence.importFile(dataFile, DataType.SUPPLIER);
            assertTrue("Check suppliers loaded", actualSuppliers.size() == 6);
            expectedSuppliers.put(0, new Supplier(0, "", "", PhoneType.HOME, "", "", ""));
            expectedSuppliers.put(1, new Supplier(1, "", "", PhoneType.WORK, "", "", ""));
            expectedSuppliers.put(2, new Supplier(2, "", "", PhoneType.MOBILE, "", "", ""));
            expectedSuppliers.put(3, new Supplier(3, "", "", PhoneType.HOME, "", "", ""));
            expectedSuppliers.put(4, new Supplier(4, "", "", PhoneType.HOME, "", "", ""));
            expectedSuppliers.put(5, new Supplier(5, "", "", PhoneType.HOME, "", "", ""));
            for (Integer id: actualSuppliers.keySet()) {
                Supplier expectedSupplier = expectedSuppliers.get(id), actualSupplier = actualSuppliers.get(id);
                assertTrue(String.format("Empty data ID%d import test", id), expectedSupplier.equals(actualSupplier));
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unhandled exception parsing XML file: " + e.getMessage());
        }
    }
    
    @Test
    public void nonIntegerDataTest() {
        dataFile = new File("resources/data/supplier_xml_files/SupplierParserTestDataNonIntegers.xml");
        try {
            persistence.importFile(dataFile, DataType.SUPPLIER);
            assertTrue("Import should have cancelled, SupplierManager should have no suppliers", actualSuppliers.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unhandled exception parsing XML file" + e.getMessage());
        }
    }
}
