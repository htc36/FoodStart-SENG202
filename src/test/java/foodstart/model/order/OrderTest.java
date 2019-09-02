package foodstart.model.order;

import static org.junit.Assert.*;

import foodstart.model.DietaryRequirement;
import foodstart.model.PaymentMethod;
import foodstart.model.Unit;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OrderTest {

	private Order order;
	private Ingredient ingredient;
	private Recipe recipe;
	private Map<Ingredient, Integer> ingredients;

	@Before
	public void setUp() throws Exception {
//		Set<Recipe> recipes = new HashSet<Recipe>();

		//Ingredients for the test recipe
		Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
		ingredient = new Ingredient(Unit.GRAMS, "TestIngredient",
				0, safeFor, 5, 10);
		Map<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
		ingredients.put(ingredient, 1);

		//The list of the items being ordered
		recipe = new PermanentRecipe(1, "TestRecipeName", "Instructions", 5, ingredients);
		Map<Recipe, Integer> items = new HashMap<Recipe, Integer>();
		items.put(recipe, 1);

		order = new Order(1, items, "TestCustomer", 0, PaymentMethod.CASH);
	}

	@Test
	public void test() {
		assertTrue(true);
	}

	@Test
	public void getTotalCost() {
		assertTrue(order.getTotalCost() == recipe.getPrice());
//		assertEquals(order.getTotalCost(), recipe.getPrice());
	}

	@Test
	public void addItem() {
		int prevTotalOrderItems = order.getTotalItemCount();
		int amountAdded = 2;
		assertFalse(order.getItems().containsKey("TestRecipeToAdd"));

		Recipe recipeAdded = new PermanentRecipe(1, "RecipeAddedName", "RecipeAddedInstructions", 8, ingredients);
		order.addItem(recipeAdded, amountAdded);

		assertTrue(order.getTotalItemCount() == (prevTotalOrderItems + amountAdded));
		assertTrue(order.getItems().containsKey(recipeAdded));
	}
}
