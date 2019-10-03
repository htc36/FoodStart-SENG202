package foodstart.model.menu;

import org.junit.Test;

import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;
import org.junit.Assert;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;

public class PermanentRecipeTest {

	private PermanentRecipe burgerRecipe; 
	private Map<Ingredient, Integer> ingredients;
	private Ingredient yeast;

	@Before
	public void setUp() throws Exception {
		 Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
		 Ingredient flour = new Ingredient(Unit.GRAMS, "Flour",
				 0, safeFor, 5, 10);
		 yeast = new Ingredient(Unit.GRAMS, "second test ingredient", 0, safeFor, 3, 13);
		 ingredients = new HashMap<Ingredient, Integer>();
	     ingredients.put(flour, 2);
		 burgerRecipe = new PermanentRecipe(0, "Burger", "Cook on high", 12, ingredients);
	}

    @Test
    public void isSafeFor() {
    	
    }

    @Test
    public void isAvailable() {
    	
    	assertEquals(true, burgerRecipe.isAvailable());
    }

    @Test
    public void isAvailable2() {
    	Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
		Ingredient ingredient2 = new Ingredient(Unit.GRAMS, "TestIngredient",
				 0, safeFor, 0, 10);
		ingredients.put(ingredient2, 0);

		Recipe wrapRecipe = new PermanentRecipe(0, "Wrap", "Cook on high", 10, ingredients);
    	assertEquals(false, wrapRecipe.isAvailable());
    }

    @Test
    public void getPrice() {
    	assertEquals(12, burgerRecipe.getPrice(), 0);
    }

    @Test
    public void setPrice() {
    	burgerRecipe.setPrice(15);
    	assertEquals(15, burgerRecipe.getPrice(), 0);
    }

    @Test
    public void getIngredients() {
    	assertEquals(ingredients, burgerRecipe.getIngredients());
    }

    @Test
    public void setIngredients() {
    	burgerRecipe.setIngredients(ingredients);
    	assertEquals(ingredients, burgerRecipe.getIngredients());
    	
    }

    @Test
    public void addIngredient() {
   	    ingredients.put(yeast,5);
    	burgerRecipe.addIngredient(yeast, 5);
    	assertTrue(ingredients.equals(burgerRecipe.getIngredients()));
    }

    @Test
    public void removeIngredient() {
    //	assertEquals()
    }

    @Test
    public void getInstructions() {
    	assertEquals("Cook on high", burgerRecipe.getInstructions());
    }

    @Test
    public void setInstructions() {
    	burgerRecipe.setInstructions("Pamper with flour and lightly glaze");
    	assertEquals("Pamper with flour and lightly glaze", burgerRecipe.getInstructions());

    }

    @Test
    public void getDisplayName() {
    	assertEquals("Burger", burgerRecipe.getDisplayName());
    }

    @Test
    public void setDisplayName() {
    	burgerRecipe.setDisplayName("Hearty Burger");
    	assertEquals("Hearty Burger", burgerRecipe.getDisplayName());
    }

    @Test
    public void getId() {
    	assertEquals(0, burgerRecipe.getId());
    }
    
    @Test
    public void testCloneBasic() {
        PermanentRecipe copy = burgerRecipe.clone();
        Assert.assertEquals(burgerRecipe, copy);
    }
    
    @Test
    public void testCloneIsDeep() {
        PermanentRecipe copy = burgerRecipe.clone();
        burgerRecipe.getIngredients().clear();
        Assert.assertNotEquals(burgerRecipe, copy); 
    }
}