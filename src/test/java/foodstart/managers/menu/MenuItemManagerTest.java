package foodstart.managers.menu;

import foodstart.manager.menu.MenuItemManager;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.stock.Ingredient;
import org.junit.*;

import java.util.HashMap;

import static org.junit.Assert.fail;

public class MenuItemManagerTest {
    //UNFINISHED
    private MenuItemManager testManager;
    private static PermanentRecipe recipe1, recipe, recipeSilly, recipeEmpty, recipeNull;

    @BeforeClass
    public static void setUpBeforeClass() {
        HashMap<Ingredient, Integer> dummyVariations = new HashMap<Ingredient, Integer>();
        recipe1 = new PermanentRecipe(1, "test1", "do some stuff", (float) 10.5, dummyVariations);
        recipe1 = new PermanentRecipe(2, "test2", "do some more stuff", (float) 0.65, dummyVariations);
        recipeEmpty = new PermanentRecipe(0, "", "", (float) 0.0, dummyVariations);
        String silly1 = "s,djhfsudzkyjsghadgilhser,kj,asmzdhgk,reoirsjzlg";
        String silly2 = ",mrak,gmfejramdy,thjgfhaersljd.ktlifksehzrdfkid,gjyfqgh";
        recipeSilly = new PermanentRecipe(589453432, silly1, silly2,
                (float) 46846849852416.5524736, dummyVariations);
        }

    @Ignore
    @Before
    public void setUp() throws Exception {
        testManager = new MenuItemManager();
    }

    @Ignore
    @After
    public void tearDown() throws Exception {
    }

    @Ignore
    @Test
    public void testAddMenuItem() {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testGetMenuItem() {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testGetMenuItems() {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testGetMenuItemsCollectionOfInteger() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testGetMenuItemSet() {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testGetApproxPrice() {
        fail("Not yet implemented");
    }

}
