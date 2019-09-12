package foodstart.managers.menu;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import foodstart.manager.menu.MenuItemManager;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.stock.Ingredient;

public class MenuItemManagerTest {
    //UNFINISHED
    private MenuItemManager testManager;
    private PermanentRecipe recipe1, recipe, recipeSilly, recipeEmpty, recipeNull;
    
    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        HashMap<Ingredient, Integer> dummyVariations = new HashMap<Ingredient, Integer>();
        recipe1 = new PermanentRecipe(1, "test1", "do some stuff", (float) 10.5, dummyVariations);
        recipe1 = new PermanentRecipe(2, "test2", "do some more stuff", (float) 0.65, dummyVariations);
        recipeEmpty = new PermanentRecipe(0, "", "", (float) 0.0, dummyVariations);
        String silly1 = "s,djhfsudzkyjsghadgilhser,kj,asmzdhgk,reoirsjzlg";
        String silly2 = ",mrak,gmfejramdy,thjgfhaersljd.ktlifksehzrdfkid,gjyfqgh";
        recipeSilly = new PermanentRecipe(589453432, silly1, silly2, 
                (float) 46846849852416.5524736, dummyVariations);
        }

    @Before
    public void setUp() throws Exception {
        testManager = new MenuItemManager();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddMenuItem() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetMenuItem() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetMenuItems() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetMenuItemsCollectionOfInteger() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetMenuItemSet() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetApproxPrice() {
        fail("Not yet implemented");
    }

}
