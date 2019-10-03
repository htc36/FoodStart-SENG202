package foodstart.model.menu;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import foodstart.model.DietaryRequirement;

public class MenuItemTest {

    private MenuItem testitem;
    private List<PermanentRecipe> recipes;

    @Before
    public void setUp() throws Exception {
        Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
        safeFor.put(DietaryRequirement.GLUTEN_FREE, true);
        List<PermanentRecipe> recipes = new ArrayList<PermanentRecipe>();
        recipes.add(new PermanentRecipe(0, "Cheese", "method", 0, null));
        recipes.add(new PermanentRecipe(1, "Nuts", "method", 0, null));
        recipes.add(new PermanentRecipe(2, "Jesus", "method", 0, null));
        testitem = new MenuItem(0, "TestItem", "MenuItem test item", recipes);
    }

    @Test
    public void getId() {
    	assertEquals(testitem.getId(), 0);
    }

    @Test
    public void setId() {
    	testitem.setDatabaseId(5);
    	assertEquals(testitem.getId(), 5);
    }

    @Test
    public void getName() {
    	assertEquals(testitem.getName(), "TestItem");
    }

    @Test
    public void setName() {
    	testitem.setName("Hamburger");
    	assertEquals(testitem.getName(), "Hamburger");

    }

    @Test
    public void getDescription() {
    	assertEquals(testitem.getDescription(), "MenuItem test item");
    }

    @Test
    public void setDescription() {
    	testitem.setDescription("Juicy patty on a seasame seed bun");
    	assertEquals(testitem.getDescription(), "Juicy patty on a seasame seed bun");
    }

    @Test
    public void getVariants() {
    	testitem.setVariants(recipes);
    	assertEquals((testitem.getVariants()), recipes);
    }

    @Test
    public void setVariants() { 
    	testitem.setVariants(recipes);
    	assertEquals((testitem.getVariants()), recipes);

    }
    
    @Test
    public void testGetVariantsAsString() {
        String expected = "Cheese Nuts Jesus ", actual = testitem.getVariantsAsString();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void testCloneBasic() {
        MenuItem copy = testitem.clone();
        Assert.assertEquals(testitem, copy);
    }
    
    @Test
    public void testCloneIsDeep() {
        MenuItem copy = testitem.clone();
        testitem.getVariants().clear();
        Assert.assertNotEquals(testitem, copy); 
    }
}