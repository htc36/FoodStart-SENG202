package foodstart.manager.xml;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class XMLPersistenceTest {
    
    XMLPersistence persistence = new XMLPersistence();
    static final String DIRECTORY_PATH = "resources/exported_test_files/";
    static final String[] filenames = {"sales_log.dtd", "ingredient.dtd", "menu.dtd", "recipe.dtd", "supplier.dtd"};

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testCopyDTDFiles() {
        File directory = new File(DIRECTORY_PATH);
        for (String s : filenames) {
            File existingDTD = new File(DIRECTORY_PATH + s);
            if (existingDTD.exists()) {
                existingDTD.delete();
            }
        }
        try {
            persistence.copyDTDFiles(directory, true);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Unexpected exception occurred.");
        }
        for (String s : filenames) {
            File expectedDTD = new File(DIRECTORY_PATH + s);
            assertTrue("Checking file " + s + " exists:", expectedDTD.exists());
        }
    }

}
