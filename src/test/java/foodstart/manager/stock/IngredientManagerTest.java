package foodstart.manager.stock;

import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class IngredientManagerTest {

	private IngredientManager manager;

	@Before
	public void setUp() {
		manager = new IngredientManager();
		Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
		manager.addIngredient(Unit.UNITS, "TestIngredient", 0, safeFor, 100, 200);
	}

	@Test
	public void testAddIngredient() {
		assertFalse(manager.getIngredients().isEmpty());
		manager.addIngredient(Unit.GRAMS, "TestIngredient", 1, null, 150, 50);
		assertEquals(2, manager.getIngredients().size());
		manager.addIngredient(Unit.MILLILITRES, "OverwritingIngredient", 0, null, 25, 0);
		assertEquals(2, manager.getIngredients().size());
	}

	@Test
	public void removeIngredientValidId() {
		assertEquals(1, manager.getIngredients().size());
		manager.removeIngredient(0);
		assertEquals(0, manager.getIngredients().size());
	}

	@Test
	public void removeIngredientInvalidId() {
		assertEquals(1, manager.getIngredients().size());
		manager.removeIngredient(100098);
		assertEquals(1, manager.getIngredients().size());
	}

	@Test
	public void getIngredients() {
		Map<Integer, Ingredient> ingredientMap = manager.getIngredients();
		assertFalse(ingredientMap.isEmpty());
		assertEquals(manager.getIngredient(0), ingredientMap.get(0));
	}

	@Test
	public void testGetIngredientValidId() {
		assertEquals("TestIngredient", manager.getIngredient(0).getName());
	}

	@Test
	public void testGetIngredientInvalidId() {
		assertNull(manager.getIngredient(10000));
	}

	@Test
	public void getIngredientByNameValidName() {
		Ingredient ingredient = manager.getIngredientByName("TestIngredient");
		assertEquals(0, ingredient.getId());
	}

	@Test
	public void getIngredientByNameInvalidName() {
		Ingredient ingredient = manager.getIngredientByName("This doesn't exist");
		assertNull(ingredient);
	}

	@Ignore
	@Test
	public void updateTruckStock() {
	}

	@Ignore
	@Test
	public void updateKitchenStock() {
	}

	@Ignore
	@Test
	public void isInTruckStock() {
	}

	@Ignore
	@Test
	public void isInKitchenStock() {
	}

	@Ignore
	@Test
	public void testGetIngredients() {
	}

	@Ignore
	@Test
	public void getIngredientSet() {
	}

	@Ignore
	@Test
	public void safeForString() {
	}

	@Ignore
	@Test
	public void generateNewID() {
	}

	@Ignore
	@Test
	public void mutateIngredient() {
	}
}