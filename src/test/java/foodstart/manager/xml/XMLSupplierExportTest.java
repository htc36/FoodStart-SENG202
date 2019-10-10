package foodstart.manager.xml;

import foodstart.manager.Managers;
import foodstart.model.DataType;
import foodstart.model.PhoneType;
import foodstart.model.stock.Supplier;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class XMLSupplierExportTest {

    
    XMLSupplierParser exporter;
    Document doc = null;
    XMLPersistence persistence = null;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        
    }

    @Before
    public void setUp() throws Exception {
        exporter = new XMLSupplierParser();
        persistence = new XMLPersistence();
        Managers.getSupplierManager().removeAllSuppliers();

        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        dBuilder.setErrorHandler(new SAXJUnitTestErrorHandler());
        doc = dBuilder.newDocument();
    }


    @After
    public void tearDown() throws Exception {
    }
    
    
    @Test
    public void exportNormalData() {
        Map<Integer, Supplier> expectedSuppliers = buildNormalSupplierMap(); 
        Managers.getSupplierManager().addSuppliers(expectedSuppliers);
        File exportedXML = new File("resources/exported_test_files/SupplierExportTestNormal.xml");
        persistence.exportFile(exportedXML, DataType.SUPPLIER);
        assertTrue("Normal supplier XML export test case", exportedFileMatchesExpected(exportedXML, expectedSuppliers));
    }
    
    
    @Test
    public void exportEmptyData() {
        Map<Integer, Supplier> expectedSuppliers = buildEmptySupplierMap(); 
        Managers.getSupplierManager().addSuppliers(expectedSuppliers);
        File exportedXML = new File("resources/exported_test_files/SupplierExportTestEmpty.xml");
        persistence.exportFile(exportedXML, DataType.SUPPLIER);
        assertTrue("Empty supplier XML export test case", exportedFileMatchesExpected(exportedXML, expectedSuppliers));
    }
    
    
    private boolean exportedFileMatchesExpected(File exportedFile, Map<Integer, Supplier> expectedSuppliers) {
        Managers.getSupplierManager().removeAllSuppliers();
        persistence.importFile(exportedFile, DataType.SUPPLIER);
        Managers.writeBuffer();
        Map<Integer, Supplier> actualSuppliers = Managers.getSupplierManager().getSuppliers();
        if (actualSuppliers.size() != expectedSuppliers.size()) {
            return false;
        }
        for (Integer id: actualSuppliers.keySet()) {
            Supplier expectedSupplier = expectedSuppliers.get(id), actualSupplier = actualSuppliers.get(id);
            if (!expectedSupplier.equals(actualSupplier)) {
                return false;
            }
        }
        return true;
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
