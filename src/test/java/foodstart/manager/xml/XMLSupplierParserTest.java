package foodstart.manager.xml;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ImportFailureException;
import foodstart.model.DataType;
import foodstart.model.PhoneType;
import foodstart.model.stock.Supplier;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

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
        expectedSuppliers = null;
        actualSuppliers = Managers.getSupplierManager().getSuppliers();
    }

    @After
    public void tearDown() throws Exception {
        Managers.getSupplierManager().removeAllSuppliers();
        persistence = null;
        dataFile = null;
    }

    @Test
    public void importNormalDataTest() {
        dataFile = new File("resources/data/supplier_xml_files/SupplierParserTestDataNormal.xml");
        persistence.importFile(dataFile, DataType.SUPPLIER);
        Managers.writeBuffer();
        expectedSuppliers = buildNormalSupplierMap();
        assertTrue("Check suppliers loaded", actualSuppliers.size() == 4);
        for (Integer id: expectedSuppliers.keySet()) {
            Supplier expectedSupplier = expectedSuppliers.get(id), actualSupplier = actualSuppliers.get(id);
            assertTrue(String.format("Normal data ID%d import test", id), expectedSupplier.equals(actualSupplier));
        }
    }
    
    @Test
    public void importWrongDataFormatTest() {
        dataFile = new File("resources/data/supplier_xml_files/SupplierParserTestDataWrongFormat.xml");
        try {
            persistence.importFile(dataFile, DataType.SUPPLIER);
            Managers.writeBuffer();
            fail("Bad file format; should have thrown an ImportFailureException");
        } catch (ImportFailureException e){;
        } 
    }
    
    @Test
    public void importNoSuppliersDataTest() {
        dataFile = new File("resources/data/supplier_xml_files/SupplierParserTestDataNoSuppliers.xml");
        persistence.importFile(dataFile, DataType.SUPPLIER);
        Managers.writeBuffer();
        assertEquals("SupplierManager should have no suppliers", 0, actualSuppliers.size());
    }
    
    @Test
    public void importEmptySuppliersDataTest() {
        dataFile = new File("resources/data/supplier_xml_files/SupplierParserTestDataEmptySuppliers.xml");
        try {
            persistence.importFile(dataFile, DataType.SUPPLIER);
            Managers.writeBuffer();
            assertTrue("Check suppliers loaded", actualSuppliers.size() == 6);
            expectedSuppliers = buildEmptySupplierMap();
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
    public void importNonIntegerDataTest() {
        dataFile = new File("resources/data/supplier_xml_files/SupplierParserTestDataNonIntegers.xml");
        try {
            persistence.importFile(dataFile, DataType.SUPPLIER);
            Managers.writeBuffer();
            assertTrue("Import should have cancelled, SupplierManager should have no suppliers", actualSuppliers.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unhandled exception parsing XML file" + e.getMessage());
        }
    }
    
    private HashMap<Integer, Supplier> buildNormalSupplierMap(){
        HashMap<Integer, Supplier> outputMap = new HashMap<Integer, Supplier>();
        outputMap.put(1, new Supplier(1, "Countup", "3451234", PhoneType.WORK, "", "countup.co.nz/high", "12 High Street, Christchurch"));
        outputMap.put(2, new Supplier(2, "Funky Kumquat", "021352176", PhoneType.MOBILE, "maryC@fkveg.co.nz", "", "125 Woodside Lane, Christchurch"));
        outputMap.put(3, new Supplier(3, "Admiral Benjamin's Seafoods","021345666", PhoneType.MOBILE, "bigben@cfoods.co.nz", "cfoods.co.nz", "86 Low Street, Christchurch"));
        outputMap.put(4, new Supplier(4, "Dave's Drinks", "3515666", PhoneType.HOME, "", "", "Unit 5, Bert's Business Park, Christchurch"));
        return outputMap;
    }
    
    private HashMap<Integer, Supplier> buildEmptySupplierMap(){
        HashMap<Integer, Supplier> outputMap = new HashMap<Integer, Supplier>();
        outputMap.put(0, new Supplier(0, "", "", PhoneType.HOME, "", "", ""));
        outputMap.put(1, new Supplier(1, "", "", PhoneType.WORK, "", "", ""));
        outputMap.put(2, new Supplier(2, "", "", PhoneType.MOBILE, "", "", ""));
        outputMap.put(3, new Supplier(3, "", "", PhoneType.HOME, "", "", ""));
        outputMap.put(4, new Supplier(4, "", "", PhoneType.HOME, "", "", ""));
        outputMap.put(5, new Supplier(5, "", "", PhoneType.HOME, "", "", ""));
        return outputMap;
    }
}
