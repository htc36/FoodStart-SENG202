package foodstart.model.order;

import static org.junit.Assert.*;
import org.junit.Assert;

import foodstart.model.DietaryRequirement;
import foodstart.model.PaymentMethod;
import foodstart.model.Unit;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class OrderTest {

	private Order testOrder, nullOrder;
	private Ingredient testIngredient;
	private Recipe testRecipe, dummyRecipe;
	private Map<Ingredient, Integer> testIngredients;
	private Map<Recipe, Integer> testItems, noItems;
	private LocalDateTime time;

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
		testIngredients = new HashMap<Ingredient, Integer>(testIngredients);
        dummyRecipe = new PermanentRecipe(1,"Dummy", "Dummy Steps", 0, testIngredients);
        noItems = new HashMap<Recipe, Integer>();
		testItems = new HashMap<Recipe, Integer>();
		testItems.put(testRecipe, 3);
		time = LocalDateTime.now();
		testOrder = new Order(1, testItems, "TestCustomerName", time, PaymentMethod.CASH);
		nullOrder = new Order(0, noItems, null, null, null);
		nullOrder.setItems(null);
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
	public void getPaymentMethod() {
		assertEquals(PaymentMethod.CASH, testOrder.getPaymentMethod());
		testOrder.setPaymentMethod(PaymentMethod.EFTPOS);
        assertEquals(PaymentMethod.EFTPOS, testOrder.getPaymentMethod());
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
		assertEquals(testOrder.getTotalCost(), 3 * testRecipe.getPrice(), 0.01);
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
    public void testAddExistingItemFunctions() {
        int prevAmount = testOrder.getItems().get(testRecipe), amountAdded = 2, returnVal = 0;
        returnVal = testOrder.addItem(testRecipe, amountAdded);
        assertEquals(Integer.valueOf(prevAmount + amountAdded), testOrder.getItems().get(testRecipe));
    }
	
	@Test
	public void testAddExistingItemReturns() {
        int prevAmount = testOrder.getItems().get(testRecipe), amountAdded = 2, returnVal = 0;
        returnVal = testOrder.addItem(testRecipe, amountAdded);
        Assert.assertEquals(prevAmount, returnVal);
    }

	@Test
	public void removeExistingItem() {
		assertEquals(1, testOrder.getItems().size());
		assertTrue(testOrder.getItems().containsKey(testRecipe));
		testOrder.removeItem(testRecipe);
		assertEquals(0, testOrder.getItems().size());
		assertFalse(testOrder.getItems().containsKey(testRecipe));

		/*Needs to test for when the recipe does not exist but is being removed from the list - edge case*/
	}
	
	@Test
	public void testRemoveItemMissing() {
	    Integer returnVal = testOrder.removeItem(dummyRecipe);
	    assertEquals(1, testOrder.getItems().size());
	    assertEquals(null, returnVal);
	}

	@Test
	public void setVariantAmount() {
		testOrder.setVariantAmount(testRecipe, 2);
		assertEquals(2, testOrder.getVariantCount(testRecipe));
	}

	@Test
	public void testDecreaseVariantAmountBySome() {
        testOrder.decreaseVariantAmount(testRecipe, 1);
        assertEquals(Integer.valueOf(2), testOrder.getItems().get(testRecipe));
    }
	
	@Test
    public void testDecreaseVariantAmountByAll() {
        testOrder.decreaseVariantAmount(testRecipe, 3);
        assertNull(testOrder.getItems().get(testRecipe));
    }
	
	@Test
	public void testCloneBasic() {
	    Order copy = testOrder.clone();
	    Assert.assertEquals(testOrder, copy);	    
	}
	
	@Test
	public void testCloneIsDeepCopy() {
	    Order copy = testOrder.clone();
	    copy.getItems().put(testRecipe, 4);
	    Assert.assertNotEquals(testOrder, copy);
	}
	
	@Test
	public void testEqualsIfSame() {
        Object test2 = testOrder;
        assertSame(testOrder, test2);
        assertEquals(testOrder, test2);
    }
    
    @Test 
    public void testNotEqualsIfNull() {
        assertNotEquals(nullOrder, null);
    }
    
    @Test 
    public void testNotEqualsIfDifferentClass() {
        Object notOrder = new String("not an order");
        assertNotEquals(testOrder, notOrder);
    }
    
    @Test 
    public void testNotEqualsIfOnlySelfNullName() {
        Order other = new Order(0, noItems, "", null, null);
        other.setItems(null);
        assertNotEquals(nullOrder, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentName() {
        Order other = new Order(1, testItems, "blah", time, PaymentMethod.CASH);
        assertNotEquals(testOrder, other);
    }

    @Test 
    public void testNotEqualsIfDifferentID() {
        Order other = new Order(5, testItems, "TestCustomerName", time, PaymentMethod.CASH);
        assertNotEquals(testOrder, other);
    }
    
    @Test 
    public void testNotEqualsIfOnlySelfNullItems() {
        Order other = new Order(0, noItems, null, null, null);
        assertNotEquals(nullOrder, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentItems() {
        Order other = new Order(1, noItems, "TestCustomerName", time, PaymentMethod.CASH);
        assertNotEquals(testOrder, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentPayment() {
        Order other = new Order(1, testItems, "TestCustomerName", time, PaymentMethod.EFTPOS);
        assertNotEquals(testOrder, other);
    }   
    
    @Test 
    public void testNotEqualsIfDifferentPrice() {
        Order other = new Order(1, testItems, "TestCustomerName", time, PaymentMethod.CASH);
        other.setPrice((float) 13245436.0);
        assertNotEquals(testOrder, other);
    }
    
    @Test 
    public void testNotEqualsIfOnlySelfNullTime() {
        Order other = new Order(0, noItems, null, time, null);
        other.setItems(null);
        assertNotEquals(nullOrder, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentTime() {
        Order other = new Order(1, testItems, "TestCustomerName", LocalDateTime.now().plusMinutes(10), PaymentMethod.CASH);
        assertNotEquals(testOrder, other);
    }
    
    @Test 
    public void testEqualsIfAllFieldsNull() {
        Order other = new Order(0, noItems, null, null, null);
        other.setItems(null);
        assertEquals(nullOrder, other);
    }
    
    @Test 
    public void testEqualsIfAllFieldsEqual() {
        Order other = new Order(1, testItems, "TestCustomerName", time, PaymentMethod.CASH);
        assertEquals(testOrder, other);
    }
}
