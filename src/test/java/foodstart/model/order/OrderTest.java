package foodstart.model.order;

import static org.junit.Assert.*;

import foodstart.model.DietaryRequirement;
import foodstart.model.PaymentMethod;
import foodstart.model.Unit;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class OrderTest {

	private Order testOrder;
	private Ingredient testIngredient;
	private Recipe testRecipe;
	private Map<Ingredient, Integer> testIngredients;
	private Map<Recipe, Integer> testItems;

	@Before
	public void setUp() throws Exception {
		//Ingredients for the test recipe
		Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
		testIngredient = new Ingredient(Unit.GRAMS, "TestIngredient",
				0, safeFor, 5, 10);
		testIngredients = new HashMap<Ingredient, Integer>();
		testIngredients.put(testIngredient, 1);

		//The list of the items being ordered
		testRecipe = new PermanentRecipe(1, "TestRecipeName", "TestRecipeInstructions", 5, testIngredients);
		testItems = new HashMap<Recipe, Integer>();
		testItems.put(testRecipe, 1);
		testOrder = new Order(1, testItems, "TestCustomerName", 0, PaymentMethod.CASH);
	}



	@Test
	public void getId() {
		assertNotNull(testOrder.getId());
		assertEquals(testOrder.getId(), 1);
	}

	@Test
	public void setId() {
		testOrder.setId(3);
		assertEquals(testOrder.getId(), 3);
	}

	@Test
	public void getItems() {
		assertEquals(testOrder.getItems(), testItems);
	}

	@Test
	public void setItems() {
		Map<Recipe, Integer> newItems = new HashMap<Recipe, Integer>();
		newItems.put(testRecipe, 4);

		Map<Recipe, Integer> previousItems = testOrder.getItems();
		assertNotEquals(previousItems, newItems);
		testOrder.setItems(newItems);
		assertEquals(newItems, testOrder.getItems());
	}

	@Test
	public void getCustomerName() {
		assertEquals("TestCustomerName", testOrder.getCustomerName());
	}

	@Test
	public void setCustomerName() {
		String newCustomerName = "TestNewCustomerName";
		String previousCustomerName = testOrder.getCustomerName();

		assertNotEquals(newCustomerName, previousCustomerName);
		testOrder.setCustomerName(newCustomerName);
		assertEquals(newCustomerName, testOrder.getCustomerName());
	}

	@Test
	public void getTimePlaced() {}

	@Ignore
	@Test
	public void setTimePlaced() {}

	@Ignore
	@Test
	public void getPaymentMethod() {
		assertEquals(PaymentMethod.CASH, testOrder.getPaymentMethod());
	}

	@Test
	public void setPaymentMethod() {
		PaymentMethod newPaymentMethod = PaymentMethod.EFTPOS;
		PaymentMethod previousPaymentMethod = testOrder.getPaymentMethod();

		testOrder.setPaymentMethod(newPaymentMethod);
		assertNotEquals(newPaymentMethod, previousPaymentMethod);
		assertEquals(newPaymentMethod, testOrder.getPaymentMethod());
	}

	@Test
	public void getTotalCost() {
		assertTrue(testOrder.getTotalCost() == testRecipe.getPrice());
//		assertEquals(order.getTotalCost(), recipe.getPrice());
	}

	@Test
	public void addItem() {
		int prevTotalOrderItems = testOrder.getTotalItemCount();
		int amountAdded = 2;
		assertFalse(testOrder.getItems().containsKey("TestRecipeToAdd"));

		Recipe newRecipe = new PermanentRecipe(1, "RecipeAddedName", "RecipeAddedInstructions", 8, testIngredients);
		testOrder.addItem(newRecipe, amountAdded);

		assertTrue(testOrder.getTotalItemCount() == (prevTotalOrderItems + amountAdded));
		assertTrue(testOrder.getItems().containsKey(newRecipe));
	}

	@Test
	public void removeItem() {
		assertTrue(true);
	}

	@Test
	public void setVariantAmount() {}

	@Test
	public void increaseVariantAmount() {}

	@Test
	public void decreaseVariantAmount() {}

	@Test
	public void getTotalItemCount() {}

	@Test
	public void getVariantCount() {}

}
