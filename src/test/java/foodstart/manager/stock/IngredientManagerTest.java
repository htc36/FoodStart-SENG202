package foodstart.manager.stock;

import foodstart.model.DietaryRequirement;
import foodstart.model.PhoneType;
import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;
import foodstart.model.stock.Supplier;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
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
	public void testGetIngredientByNameValidName() {
		Ingredient ingredient = manager.getIngredientByName("TestIngredient");
		assertEquals(0, ingredient.getId());
	}

	@Test
	public void testGetIngredientByNameInvalidName() {
		Ingredient ingredient = manager.getIngredientByName("This doesn't exist");
		assertNull(ingredient);
	}

	@Test
	public void testUpdateTruckStock() {
		assertEquals(200, manager.getIngredient(0).getTruckStock());
		manager.updateTruckStock(0, 150);
		assertEquals(150, manager.getIngredient(0).getTruckStock());
	}

	@Test
	public void testUpdateKitchenStock() {
		assertEquals(100, manager.getIngredient(0).getKitchenStock());
		manager.updateKitchenStock(0, 999999);
		assertEquals(999999, manager.getIngredient(0).getKitchenStock());
	}

	@Test
	public void testIsInTruckStockInStock() {
		assertTrue(manager.isInTruckStock(0));
	}

	@Test
	public void testIsInTruckStockNotInStock() {
		manager.updateTruckStock(0, 0);
		assertFalse(manager.isInTruckStock(0));
	}

	@Test
	public void testIsInTruckStockInvalidId() {
		assertFalse(manager.isInTruckStock(15));
	}

	@Test
	public void testIsInKitchenStockInStock() {
		assertTrue(manager.isInKitchenStock(0));
	}

	@Test
	public void testIsInKitchenStockNotInStock() {
		manager.updateKitchenStock(0, 0);
		assertFalse(manager.isInKitchenStock(0));
	}

	@Test
	public void testIsInKitchenStockInvalidId() {
		assertFalse(manager.isInKitchenStock(15));
	}

	@Test
	public void testGetIngredientsValidId() {
		Collection<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		assertEquals(1, manager.getIngredients(ids).size());
	}

	@Test
	public void testGetIngredientsInvalid() {
		Collection<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		ids.add(5000);
		assertEquals(1, manager.getIngredients(ids).size());
	}

	@Test
	public void testGetIngredientsMultipleIds() {
		manager.addIngredient(Unit.GRAMS, "TestGetIngredient1", 1, null, 5, 15);
		manager.addIngredient(Unit.GRAMS, "TestGetIngredient2", 2, null, 6, 16);
		manager.addIngredient(Unit.UNITS, "TestGetIngredient3", 3, null, 7, 17);
		manager.addIngredient(Unit.MILLILITRES, "TestGetIngredient4", 4, null, 8, 18);
		Collection<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		ids.add(4);
		ids.add(2);
		assertEquals(3, manager.getIngredients(ids).size());
	}

	@Test
	public void testGetIngredientSet() {
		assertEquals(1, manager.getIngredientSet().size());
		manager.addIngredient(Unit.GRAMS, "TestGetIngredient1", 1, null, 5, 15);
		manager.addIngredient(Unit.GRAMS, "TestGetIngredient2", 2, null, 6, 16);
		manager.addIngredient(Unit.UNITS, "TestGetIngredient3", 3, null, 7, 17);
		manager.addIngredient(Unit.MILLILITRES, "TestGetIngredient4", 4, null, 8, 18);
	assertEquals(5, manager.getIngredientSet().size());
}

	@Test
	public void testGetIngredientSetAbuse() {
		Ingredient recipe = new Ingredient(Unit.GRAMS, "TestGetIngredient1", 1, null, 5, 15);
		manager.getIngredients().put(1, recipe);
		manager.getIngredients().put(2, recipe);
		assertEquals(2, manager.getIngredientSet().size());
	}

	@Test
	public void testSafeForStringValid() {
		Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
		safeFor.put(DietaryRequirement.NUT_ALLERGY, true);
		safeFor.put(DietaryRequirement.LACTOSE_INTOLERANT, true);
		safeFor.put(DietaryRequirement.VEGAN, false);
		manager.addIngredient(Unit.GRAMS, "TestGetIngredient1", 1, safeFor, 5, 15);
		String expected1 = "Dairy Free, Nut Free";
		String expected2 = "Nut Free, Dairy Free";
		String actual = manager.safeForString(1);
		assertTrue(actual.equals(expected1)||actual.equals(expected2));
	}

	@Test
	public void testSafeForStringEmptyMap() {
		assertEquals("", manager.safeForString(0));
	}

	@Test
	public void testSafeForStringInvalidId() {
		assertNull(manager.safeForString(5));
	}

	@Test
	public void testGenerateNewID() {
		assertEquals(1, manager.generateNewID());
		manager.addIngredient(Unit.GRAMS, "TestGetIngredient", 9998, null, 5, 15);
		assertEquals(9999, manager.generateNewID());
	}

	@Test
	public void testGenerateNewIDEmpty() {
		IngredientManager newManager = new IngredientManager();
		assertEquals(0, newManager.generateNewID());
	}

	@Test
	public void testMutateIngredientValidId() {
		manager.mutateIngredient(Unit.UNITS, "MutatedIngredient", 0, null, 8, 9);
		Ingredient ingredient = manager.getIngredient(0);
		assertEquals("MutatedIngredient", ingredient.getName());
		assertEquals(null, ingredient.getSafeFor());
	}

	@Test
	public void testMutateIngredientInvalidId() {
		manager.mutateIngredient(Unit.UNITS, "MutatedIngredient", 99, null, 8, 9);
		Ingredient ingredient = manager.getIngredient(0);
		assertEquals("TestIngredient", ingredient.getName());
	}
	
    @Test
    public void testPushToBuffer() {
        manager.getIngredients().clear();
        Ingredient testOrder = new Ingredient(Unit.UNITS, "test", 3, null, 0, 0);
        manager.pushToBuffer(Unit.UNITS, "test", 3, null, 0, 0);
        manager.writeBuffer();
        assertTrue(manager.getIngredients().containsValue(testOrder));
    }
    
    @Test
    public void testDropBuffer() {
        manager.getIngredients().clear();
        manager.pushToBuffer(Unit.UNITS, "test", 3, null, 0, 0);
        manager.dropBuffer();
        manager.writeBuffer();
        assertTrue(manager.getIngredients().isEmpty());
    }
}