package foodstart.manager.order;

import foodstart.model.PaymentMethod;
import foodstart.model.Unit;
import foodstart.model.menu.OnTheFlyRecipe;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;
import foodstart.model.stock.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class OrderManagerTest {

	OrderManager manager;
	LocalDateTime testTime;
	Map<Recipe, Integer> items;
	PermanentRecipe permanentRecipe;
	OnTheFlyRecipe otfRecipe;

	@Before
	public void setUp() {
		manager = new OrderManager();
		Ingredient ingredient1 = new Ingredient(Unit.UNITS, "TestIngredient1", 0, null, 5, 10);
		Ingredient ingredient2 = new Ingredient(Unit.MILLILITRES, "TestIngredient2", 1, null, 4, 8);
		Map<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
		ingredients.put(ingredient1, 10);
		ingredients.put(ingredient2, 5);
		permanentRecipe = new PermanentRecipe(0, "TestPermRecipe", "TestInstruction", 5, ingredients);
		Map<Ingredient, Integer> otfIngredients = new HashMap<Ingredient, Integer>(ingredients);
		otfRecipe = new OnTheFlyRecipe(permanentRecipe, otfIngredients, 45);
		items = new HashMap<Recipe, Integer>();
		items.put(permanentRecipe, 5);
		items.put(otfRecipe, 3);
		testTime = LocalDateTime.ofEpochSecond(1, 0, ZoneOffset.UTC);
		Order order1 = new Order(0, items, "Customer", testTime, PaymentMethod.CASH);
		manager.getOrders().put(0, order1);
	}

	@Test
	public void testGetOrderValidId() {
		Order order = manager.getOrder(0);
		assertNotNull(order);
		assertEquals(0, order.getId());
	}

	@Test
	public void testGetOrderInvalidId() {
		Order order = manager.getOrder(101);
		assertNull(order);
	}

	@Test
	public void testGetOrdersValidId() {
		Collection<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		assertEquals(1, manager.getOrders(ids).size());
	}

	@Test
	public void testGetOrdersInvalidId() {
		Collection<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		ids.add(101);
		assertEquals(1, manager.getOrders(ids).size());
	}

	@Test
	public void testGetOrdersMultipleIds() {
		manager.addOrder(1, items, "Customer", testTime, PaymentMethod.CASH);
		manager.addOrder(2, items, "Customer", testTime, PaymentMethod.CASH);
		manager.addOrder(3, items, "Customer", testTime, PaymentMethod.CASH);
		manager.addOrder(4, items, "Customer", testTime, PaymentMethod.CASH);
		Collection<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		ids.add(3);
		ids.add(2);
		assertEquals(3, manager.getOrders(ids).size());
	}

	@Test
	public void testAddOrderEpochSecondPriceless() {
		assertEquals(1, manager.getOrders().size());
		manager.addOrder(1, items, "Customer", 15, PaymentMethod.CASH);
		assertEquals(2, manager.getOrders().size());
	}

	@Test
	public void testAddOrderEpochSecondPriced() {
		assertEquals(1, manager.getOrders().size());
		manager.addOrder(2, items, "Customer", 15, PaymentMethod.CASH, 15);
		assertEquals(2, manager.getOrders().size());
	}

	@Test
	public void testAddOrderLocalDateTime() {
		assertEquals(1, manager.getOrders().size());
		manager.addOrder(3, items, "Customer", testTime, PaymentMethod.CASH);
		assertEquals(2, manager.getOrders().size());
	}

	@Test
	public void testAddOrderOverwriting() {
		assertEquals(1, manager.getOrders().size());
		manager.addOrder(0, items, "CustomerDifferent", testTime, PaymentMethod.CASH);
		assertEquals(1, manager.getOrders().size());
		assertEquals("CustomerDifferent", manager.getOrder(0).getCustomerName());
	}

	@Test
	public void testAddOrderNullItems() {
		assertEquals(1, manager.getOrders().size());
		manager.addOrder(3, null, "Customer", testTime, PaymentMethod.CASH);
		manager.addOrder(2, null, "Customer", 15, PaymentMethod.CASH, 15);
		manager.addOrder(1, null, "Customer", 15, PaymentMethod.CASH);
		assertEquals(4, manager.getOrders().size());
	}

	@Test
	public void testGetOrderSet() {
		assertEquals(1, manager.getOrderSet().size());
		manager.addOrder(1, items, "Customer", testTime, PaymentMethod.CASH);
		manager.addOrder(2, items, "Customer", testTime, PaymentMethod.CASH);
		manager.addOrder(3, items, "Customer", testTime, PaymentMethod.CASH);
		manager.addOrder(4, items, "Customer", testTime, PaymentMethod.CASH);
		assertEquals(5, manager.getOrderSet().size());
	}

	@Test
	public void testGetOrderSetAbuse() {
		assertEquals(1, manager.getOrderSet().size());
		Order order = new Order(1, items, "Customer", testTime, PaymentMethod.CASH);
		manager.getOrders().put(1, order);
		manager.getOrders().put(2, order);
		assertEquals(2, manager.getOrderSet().size());
	}

	@Test
	public void testGetItemsAsStringValidId() {
		String expected1 = "5x TestPermRecipe, 3x TestPermRecipe (modified)";
		String expected2 = "3x TestPermRecipe (modified), 5x TestPermRecipe";
		String actual = manager.getItemsAsString(0);
		assertTrue(actual.equals(expected1) || actual.equals(expected2));
	}

	@Test
	public void testGetItemsAsStringInvalidId() {
		assertEquals("", manager.getItemsAsString(5));
	}

	@Test
	public void testRemoveOrderValidId() {
		assertEquals(1, manager.getOrders().size());
		manager.removeOrder(0);
		assertEquals(0, manager.getOrders().size());
	}

	@Test
	public void testRemoveOrderInvalidId() {
		assertEquals(1, manager.getOrders().size());
		manager.removeOrder(101);
		assertEquals(1, manager.getOrders().size());
	}

	@Test
	public void testMutateOrderItemsValidId() {
		boolean changed = manager.mutateOrderItems(0, null);
		assertTrue(changed);
		assertNull(manager.getOrder(0).getItems());
	}

	@Test
	public void testMutateOrderItemsInvalidId() {
		boolean changed = manager.mutateOrderItems(101, null);
		assertFalse(changed);
	}

	@Test
	public void testMutateOrderValidId() {
		boolean changed = manager.mutateOrder(0, "Shad", testTime, 5, PaymentMethod.EFTPOS);
		assertTrue(changed);
		assertEquals("Shad", manager.getOrder(0).getCustomerName());
		assertEquals(5, manager.getOrder(0).getTotalCost(), 1e-2);
	}

	@Test
	public void testMutateOrderInvalidId() {
		boolean changed = manager.mutateOrder(101, "Shad", testTime, 5, PaymentMethod.EFTPOS);
		assertFalse(changed);
	}

	@Test
	public void testMutateOrderNullValue() {
		manager.getOrders().put(101, null);
		boolean changed = manager.mutateOrder(101, "Shad", testTime, 5, PaymentMethod.EFTPOS);
		assertFalse(changed);
	}

	@Test
	public void testGetOrderRecipesValidId() {
		assertEquals(items.keySet(), manager.getOrderRecipes(0));
	}

	@Test
	public void testGetOrderRecipesInvalidId() {
		assertNull(manager.getOrderRecipes(101));
	}
	
    
    @Test
    public void testPushToBufferTimeObject() {
        manager.removeAllOrders();
        Order order2 = new Order(1, items, "Customer", testTime, PaymentMethod.CASH);
        manager.pushToBuffer(1, items, "Customer", testTime, PaymentMethod.CASH);
        manager.writeBuffer();
        assertTrue(manager.getOrder(1).equals(order2));
    }
    
    @Test
    public void testPushToBufferTimeMs() {
        manager.removeAllOrders();
        Order order2 = new Order(1, items, "Customer", 10000000, PaymentMethod.CASH);
        manager.pushToBuffer(1, items, "Customer", 10000000, PaymentMethod.CASH);
        manager.writeBuffer();
        assertTrue(manager.getOrder(1).equals(order2));
    }
    
    @Test
    public void testPushToBufferWithCost() {
        manager.removeAllOrders();
        Order order2 = new Order(1, items, "Customer", 10000000, PaymentMethod.CASH, (float) 12.50);
        manager.pushToBuffer(1, items, "Customer", 10000000, PaymentMethod.CASH, (float) 12.50);
        manager.writeBuffer();
        assertTrue(manager.getOrder(1).equals(order2));
    }
    
    @Test
    public void testPushToBufferTimeObjectNullItems() {
        manager.removeAllOrders();
        Map<Recipe, Integer> empty = new HashMap<Recipe, Integer>();
        Order order2 = new Order(1, empty, "Customer", testTime, PaymentMethod.CASH);
        manager.pushToBuffer(1, null, "Customer", testTime, PaymentMethod.CASH);
        manager.writeBuffer();
        assertTrue(manager.getOrder(1).equals(order2));
    }
    
    @Test
    public void testPushToBufferTimeInMsNullItems() {
        manager.removeAllOrders();
        Map<Recipe, Integer> empty = new HashMap<Recipe, Integer>();
        Order order2 = new Order(1, empty, "Customer", 10000000, PaymentMethod.CASH);
        manager.pushToBuffer(1, null, "Customer", 10000000, PaymentMethod.CASH);
        manager.writeBuffer();
        assertTrue(manager.getOrder(1).equals(order2));
    }
    
    @Test
    public void testPushToBufferWithCostNullItems() {
        manager.removeAllOrders();
        Map<Recipe, Integer> empty = new HashMap<Recipe, Integer>();
        Order order2 = new Order(1, empty, "Customer", 10000000, PaymentMethod.CASH, (float) 12.50);
        manager.pushToBuffer(1, null, "Customer", 10000000, PaymentMethod.CASH, (float) 12.50);
        manager.writeBuffer();
        assertTrue(manager.getOrder(1).equals(order2));
    }

    @Test
    public void testDropBuffer() {
        manager.removeAllOrders();
        manager.pushToBuffer(1, items, "Customer", 10000000, PaymentMethod.CASH);
        manager.dropBuffer();
        manager.writeBuffer();
        assertTrue(manager.getOrders().isEmpty());
    }
    
    @Test
    public void testRemoveRecipeFromOrderNormal() {
        manager.removeRecipeFromOrders(permanentRecipe);
        assertFalse(manager.getOrder(0).getItems().containsKey(permanentRecipe));
    }
    
    @Test
    public void testRemoveRecipeFromOrderDeleteEmptyOrder() {
        manager.removeRecipeFromOrders(permanentRecipe);
        manager.removeRecipeFromOrders(otfRecipe);
        assertTrue(manager.getOrders().isEmpty());
    }
}