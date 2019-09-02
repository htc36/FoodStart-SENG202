package foodstart.model.menu;

import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;
import org.junit.Before;
import org.junit.Test;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.Recipe;
import foodstart.model.menu.PermanentRecipe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class MenuItemTest {

    private MenuItem testitem;
    private Set<Recipe> recipes;

    @Before
    public void setUp() throws Exception {
       Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
//       Ingredient ingredient = new Ingredient(Unit.GRAMS, "TestIngredient",
//                0, safeFor, 5, 10);
        Set<Recipe> recipes = new HashSet<Recipe>();
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
    	//Map<Ingredient, Integer> ingrediants = new HashMap<Ingredient, Integer>();
    	//ingrediants.put(ingrediant, 2);
    	//Recipe burgerRecipe = new PermanentRecipe("Burger", "Cook on high", 12, ingrediants);
    	//recipes.add(burgerRecipe);
    	testitem.setVariants(recipes);
    	assertEquals((testitem.getVariants()), recipes);

    }
}