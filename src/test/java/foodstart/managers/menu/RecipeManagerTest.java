package foodstart.managers.menu;

import foodstart.manager.menu.RecipeManager;
import foodstart.model.Unit;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.stock.Ingredient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class RecipeManagerTest {

	private RecipeManager manager;
	private PermanentRecipe permanentRecipe;
	private Map<Ingredient, Integer> ingredients;

	@Before
	public void setUp() {
		manager = new RecipeManager();
		Ingredient ingredient1 = new Ingredient(Unit.UNITS, "TestIngredient1", 0, null, 5, 10);
		Ingredient ingredient2 = new Ingredient(Unit.MILLILITRES, "TestIngredient2", 1, null, 4, 8);
		ingredients = new HashMap<Ingredient, Integer>();
		ingredients.put(ingredient1, 10);
		ingredients.put(ingredient2, 5);
		permanentRecipe = new PermanentRecipe(0, "TestPermRecipe", "TestInstruction", 5, ingredients);

		manager.getRecipes().put(0, permanentRecipe);
	}

	@Test
	public void testAddRecipe() {
		assertEquals(1, manager.getRecipes().size());
		manager.addRecipe(1, "TestAddRecipe", "TestInstructions", 15, null);
		assertEquals(2, manager.getRecipes().size());
	}

	@Test
	public void testAddRecipeOverwriting() {
		assertEquals(1, manager.getRecipes().size());
		manager.addRecipe(0, "OverwritingRecipe", "OverwritesAnotherRecipe", 30, null);
		assertEquals(1, manager.getRecipes().size());
		assertEquals(30, manager.getRecipe(0).getPrice(), 2e-1);
	}

	@Test
	public void testGetRecipeValidId() {
		PermanentRecipe recipe = manager.getRecipe(0);
		assertNotNull(recipe);
		assertEquals(0, recipe.getId());
	}

	@Test
	public void testGetRecipeInvalidId() {
		assertNull(manager.getRecipe(1024));
	}

	@Ignore
	@Test
	public void testGetRecipeByDisplayNameValidName() {
		//TODO: Clarify some things about display name
		fail("Not yet implemented");
	}

	@Test
	public void testGetRecipesValidId() {
		Collection<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		assertEquals(1, manager.getRecipes(ids).size());
	}

	@Test
	public void testGetRecipesInvalid() {
		Collection<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		ids.add(5000);
		assertEquals(1, manager.getRecipes(ids).size());
	}

	@Test
	public void testGetRecipesMultipleIds() {
		manager.addRecipe(1, "TestAddRecipe1", "TestInstructions", 15, null);
		manager.addRecipe(2, "TestAddRecipe2", "TestInstructions", 16, null);
		manager.addRecipe(3, "TestAddRecipe3", "TestInstructions", 17, null);
		manager.addRecipe(4, "TestAddRecipe4", "TestInstructions", 18, null);
		Collection<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		ids.add(4);
		ids.add(2);
		assertEquals(3, manager.getRecipes(ids).size());
	}

	@Test
	public void testGetRecipeSet() {
		assertEquals(1, manager.getRecipeSet().size());
		manager.addRecipe(1, "TestAddRecipe1", "TestInstructions", 15, null);
		manager.addRecipe(2, "TestAddRecipe2", "TestInstructions", 16, null);
		manager.addRecipe(3, "TestAddRecipe3", "TestInstructions", 17, null);
		manager.addRecipe(4, "TestAddRecipe4", "TestInstructions", 18, null);
		assertEquals(5, manager.getRecipeSet().size());
	}

	@Test
	public void testGetRecipeSetAbuse() {
		PermanentRecipe recipe = new PermanentRecipe(1, "TestRecipe", "TestInstructions", 12, null);
		manager.getRecipes().put(1, recipe);
		manager.getRecipes().put(2, recipe);
		assertEquals(2, manager.getRecipeSet().size());
	}

	@Test
	public void testGetIngredientsAsStringValidId() {
		String expected = "";
		for (Ingredient ingredient : ingredients.keySet()) {
			expected = expected.concat(String.format("%dx %s ", ingredient.getTruckStock(), ingredient.getName()));
		}
		assertEquals(expected, manager.getIngredientsAsString(0));
	}

	@Test
	public void testGetIngredientsAsStringInvalidId() {
		assertEquals("", manager.getIngredientsAsString(300));
	}
}
