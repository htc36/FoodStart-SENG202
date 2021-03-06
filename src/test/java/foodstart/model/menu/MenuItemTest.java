package foodstart.model.menu;

import foodstart.model.DietaryRequirement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MenuItemTest {

    private MenuItem testItem, nullItem;
	private Set<PermanentRecipe> recipes, testRecipes, dummyRecipes;

    @Before
    public void setUp() throws Exception {
        Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
        safeFor.put(DietaryRequirement.GLUTEN_FREE, true);
		Set<PermanentRecipe> recipes = new HashSet<PermanentRecipe>();
		dummyRecipes = new HashSet<PermanentRecipe>();
        recipes.add(new PermanentRecipe(0, "Cheese", "method", 0, null));
        recipes.add(new PermanentRecipe(1, "Nuts", "method", 0, null));
        recipes.add(new PermanentRecipe(2, "Jesus", "method", 0, null));
		testRecipes = new HashSet<PermanentRecipe>();
        testRecipes.add(new PermanentRecipe(0, "Cheese", "method", 0, null));
        testRecipes.add(new PermanentRecipe(1, "Nuts", "method", 0, null));
        testRecipes.add(new PermanentRecipe(2, "Jesus", "method", 0, null));
		testItem = new MenuItem(0, "TestItem", "MenuItem test item", recipes, null);
		nullItem = new MenuItem(0, null, null, null, null);
    }

    @Test
    public void getId() {
    	assertEquals(testItem.getId(), 0);
    }

    @Test
    public void setId() {
    	testItem.setDatabaseId(5);
    	assertEquals(testItem.getId(), 5);
    }

    @Test
    public void getName() {
    	assertEquals(testItem.getName(), "TestItem");
    }

    @Test
    public void setName() {
    	testItem.setName("Hamburger");
    	assertEquals(testItem.getName(), "Hamburger");

    }

    @Test
    public void getDescription() {
    	assertEquals(testItem.getDescription(), "MenuItem test item");
    }

    @Test
    public void setDescription() {
    	testItem.setDescription("Juicy patty on a seasame seed bun");
    	assertEquals(testItem.getDescription(), "Juicy patty on a seasame seed bun");
    }

    @Test
    public void getVariants() {
    	testItem.setVariants(recipes);
    	assertEquals((testItem.getVariants()), recipes);
    }

    @Test
    public void setVariants() { 
    	testItem.setVariants(recipes);
    	assertEquals((testItem.getVariants()), recipes);

    }
    
    @Test
    public void testGetVariantsAsString() {
		List<String> expected = Arrays.asList("Cheese", "Nuts", "Jesus");
		List<String> actual = Arrays.asList(testItem.getVariantsAsString().split(" "));
		assertEquals(expected.size(), actual.size());
		assertEquals(new HashSet<String>(expected), new HashSet<String>(actual));
    }
    
    @Test
    public void testRemove(){
        Assert.assertEquals(testItem.getVariants().size(), 3);
        testItem.remove(new PermanentRecipe(0, "Cheese", "method", 0, null));
        Assert.assertEquals(testItem.getVariants().size(), 2);
        Assert.assertFalse(testItem.getVariants().contains(new PermanentRecipe(0, "Cheese", "method", 0, null)));
    }
    
    @Test
    public void testCloneBasic() {
        MenuItem copy = testItem.clone();
        Assert.assertEquals(testItem, copy);
    }
    
    @Test
    public void testCloneIsDeep() {
        MenuItem copy = testItem.clone();
        testItem.getVariants().clear();
        Assert.assertNotEquals(testItem, copy); 
    }
    
    @Test 
    public void testEqualsIfSame() {
        Object test2 = testItem;
        assertSame(testItem, test2);
        assertEquals(testItem, test2);
    }
    
    @Test 
    public void testNotEqualsIfNull() {
        assertNotEquals(testItem, null);
    }
    
    @Test 
    public void testNotEqualsIfDifferentClass() {
        Object notIngredient = new String("not an ingredient");
        assertNotEquals(testItem, notIngredient);
    }
    
    @Test 
    public void testNotEqualsIfOnlySelfNullDescription() {
		MenuItem other = new MenuItem(0, null, "", null, null);
        assertNotEquals(nullItem, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentDescription() {
		MenuItem other = new MenuItem(0, "TestItem", "MenuItem ", testRecipes, null);
        assertNotEquals(testItem, other);
    }
    
    @Test 
    public void testNotEqualsIfOnlySelfNullName() {
		MenuItem other = new MenuItem(0, "", null, null, null);
        assertNotEquals(nullItem, other);
    }
    
    @Test 
    public void testEqualsIfDifferentName() {
		MenuItem other = new MenuItem(0, "tem", "MenuItem test item", testRecipes, null);
        assertNotEquals(testItem, other);
    }
    
    @Test 
    public void testEqualsIfDifferentID() {
		MenuItem other = new MenuItem(5, "TestItem", "MenuItem test item", testRecipes, null);
        assertNotEquals(testItem, other);
    }
    
    @Test 
    public void testNotEqualsIfOnlySelfNullVariants() {
		MenuItem other = new MenuItem(0, null, null, dummyRecipes, null);
        assertNotEquals(nullItem, other);
    }
    
    @Test 
    public void testEqualsIfDifferentVariants() {
		MenuItem other = new MenuItem(0, "TestItem", "MenuItem test item", dummyRecipes, null);
        assertNotEquals(testItem, other);
    }
    
    @Test 
    public void testEqualsIfAllFieldsNull() {
		MenuItem other = new MenuItem(0, null, null, null, null);
        assertEquals(nullItem, other);
    }
    
    @Test 
    public void testEqualsIfAllFieldsMatch() {
		MenuItem other = new MenuItem(0, "TestItem", "MenuItem test item", testRecipes, null);
        assertEquals(testItem, other);
    }
}