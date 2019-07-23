/**
 * Unit tests for Loading Supplier Data using SAX.
 */

package nic.foody.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import nic.foody.model.MenuItem;


/**
 * Unit tests for example DOM parsing code. Individual tests aren't usually
 * documented extensively since their namesare supposed to be informative and
 * their content is supposed to be small enough to be understood (e.g. aiming to
 * have one assertion per test).
 */
public class TestLoadMenuData {
    private Map<String, MenuItem> menu;
    private MenuItem item;

    /**
     * Make sure each test starts with a clean copy of the loaded data - in case
     * previous tests have side-effects.
     */
    @Before
    public void testLoadMenuFile() {
        String fName = "resources/data/SampleMenu.xml";
       
        LoadData.loadMenu(fName);
        menu = LoadData.menuItems();
        assertEquals("All XML records should be added", 6, menu.size());
    }

    /**
     * Way too many assertions in this test, but you get the idea...
     */
    @Test
    public void testBabyFace() {
       assertNotNull("Baby face is in the sample menu", menu.get("BF"));
        item = menu.get("BF");
        assertTrue("Made with vodka", item.ingredientNames().contains("Vodka"));
        assertTrue("Made with cassis", item.ingredientNames().contains("Cassis"));
        assertTrue("Made with cream", item.ingredientNames().contains("Cream"));
        assertEquals("Made from three things", 3, item.ingredientNames().size());
        assertFalse("Not made with Beetroot", item.ingredientNames().contains("Beetroot"));
    }

}