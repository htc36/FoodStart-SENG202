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
import org.junit.Ignore;

public class PermanentRecipeTest {

	private PermanentRecipe burgerRecipe, nullRecipe; 
	private Map<Ingredient, Integer> ingredients, dummyIngredients;
	private Ingredient yeast;

	@Before
	public void setUp() throws Exception {
		 Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
		 Ingredient flour = new Ingredient(Unit.GRAMS, "Flour",
				 0, safeFor, 5, 10);
		 yeast = new Ingredient(Unit.GRAMS, "second test ingredient", 0, safeFor, 3, 13);
		 ingredients = new HashMap<Ingredient, Integer>();
	     ingredients.put(flour, 2);
	     dummyIngredients = new HashMap<Ingredient, Integer>();
		 burgerRecipe = new PermanentRecipe(0, "Burger", "Cook on high", 12, ingredients);
		 nullRecipe = new PermanentRecipe(0, null, null, 0, null);
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
        burgerRecipe.addIngredient(yeast, 1);
        burgerRecipe.removeIngredient(yeast);
        assertFalse(burgerRecipe.getIngredients().containsKey(yeast));
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
    
    @Test 
    public void testEqualsIfSame() {
        Object test = burgerRecipe;
        assertSame(burgerRecipe, test);
        assertEquals(burgerRecipe, test);
    }
    
    @Test 
    public void testNotEqualsIfNull() {
        assertNotEquals(nullRecipe, null);
    }
    
    @Test 
    public void testNotEqualsIfDifferentClass() {
        Recipe notRecipe = new OnTheFlyRecipe(burgerRecipe, ingredients, 12);
        assertNotEquals(burgerRecipe, notRecipe);
    }
    
    @Test 
    public void testNotEqualIfOnlySelfNullName() {
        Recipe other = new PermanentRecipe(0, "", null, 0, null);
        assertNotEquals(nullRecipe, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentName() {
        Recipe other = new PermanentRecipe(0, "ug", "Cook on high", 12, ingredients);
        assertNotEquals(burgerRecipe, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentID() {
        Recipe other = new PermanentRecipe(5, "Burger", "Cook on high", 12, ingredients);
        assertNotEquals(burgerRecipe, other);
    }
    
    @Test 
    public void testNotEqualIfOnlySelfNullInstructions() {
        Recipe other = new PermanentRecipe(0, null, "", 0, null);
        assertNotEquals(nullRecipe, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentInstructions() {
        Recipe other = new PermanentRecipe(0, "Burger", "Cn high", 12, ingredients);
        assertNotEquals(burgerRecipe, other);
    }
    
    @Test 
    public void testEqualsIfAllFieldsNull() {
        Recipe other = new PermanentRecipe(0, null, null, 0, null);
        assertEquals(nullRecipe, other);
    }
    
    @Test 
    public void testEqualsIfAllFieldsMatch() {
        Recipe other = new PermanentRecipe(0, "Burger", "Cook on high", 12, ingredients);
        assertEquals(burgerRecipe, other);
    }
}