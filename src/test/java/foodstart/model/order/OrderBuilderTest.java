package foodstart.model.order;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import foodstart.manager.exceptions.InsufficientStockException;
import foodstart.model.DietaryRequirement;
import foodstart.model.PaymentMethod;
import foodstart.model.Unit;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;

public class OrderBuilderTest {
    
    OrderBuilder testBuilder;
    static Ingredient inAllStock, fraction, empty;
    static Map<DietaryRequirement, Boolean> testDiet;
    static Map<Recipe, Integer> normOrderItems, emptyOrderItems;
    static Recipe prAvailable, prFraction, prUnavailable,
        otfRecipe1, otfRecipe2; 
    static Order normalOrder, emptyOrder;
    static final int QTY_USED = 5, MAX_USES = 4;
    static final int BASE_STOCK = QTY_USED * MAX_USES;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testDiet = new HashMap<DietaryRequirement, Boolean>();
        testDiet.put(DietaryRequirement.GLUTEN_FREE, true);
        testDiet.put(DietaryRequirement.NUT_ALLERGY, false);
        
        inAllStock = new Ingredient(Unit.GRAMS, "Cheese", 0, testDiet, 0, 0);
        fraction = new Ingredient(Unit.UNITS, "Potato", 1, testDiet, 0, 0);
        empty = new Ingredient(Unit.UNITS, "Electric Boogaloo", 3, testDiet, 0, 0);
        
        HashMap<Ingredient, Integer> permMap1 = new HashMap<Ingredient, Integer>();
        HashMap<Ingredient, Integer> ingredientMapFraction = new HashMap<Ingredient, Integer>();
        HashMap<Ingredient, Integer> permMap2 = new HashMap<Ingredient, Integer>();
        permMap1.put(inAllStock, QTY_USED);
        ingredientMapFraction.put(inAllStock, QTY_USED);
        ingredientMapFraction.put(fraction, QTY_USED);
        permMap2.put(empty, QTY_USED);
        
        prAvailable = new PermanentRecipe(0, "Cheese", "dummy", 10 , permMap1);
        prFraction = new PermanentRecipe(1, "Cheese", "dummy", 7 , ingredientMapFraction);
        prUnavailable = new PermanentRecipe(3, "ElectricBoogaloo", "dummy", 4 , permMap2);
        
        normOrderItems = new HashMap<Recipe, Integer>();
        emptyOrderItems = new HashMap<Recipe, Integer>();
        normOrderItems.put(prAvailable, MAX_USES - 1);
        
        normalOrder = new Order(0, normOrderItems, "Jom", 0, PaymentMethod.CASH);
    }
    
    private void setStockLevels() {
        inAllStock.setKitchenStock(BASE_STOCK);
        inAllStock.setTruckStock(BASE_STOCK);
        fraction.setKitchenStock(BASE_STOCK - 1);
        fraction.setTruckStock(BASE_STOCK - 1);
        empty.setKitchenStock(0);
        empty.setTruckStock(0);
    }

    @Before
    public void setUp() throws Exception {
        testBuilder = new OrderBuilder();
        testBuilder.currentOrder.putAll(normOrderItems);
        setStockLevels();
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testCanAddNegativeItems() {
        try {
            testBuilder.canAddItem(prAvailable, -1);
        } catch (Exception e) {
            
        }
    }

    @Test
    public void testCanAddSomeItems() {
        assertTrue(testBuilder.canAddItem(prAvailable, MAX_USES - 1));
    }
    
    @Test
    public void testCanAddExactMaxItems() {
        assertTrue(testBuilder.canAddItem(prAvailable, MAX_USES));
    }
    
    @Test
    public void testCanAddExcessItems() {
        testBuilder.addItem(prAvailable, 2);
        assertFalse(testBuilder.canAddItem(prAvailable, MAX_USES));
    }
    
    @Test
    public void testCanAddExcessItemsFraction() {
        assertFalse(testBuilder.canAddItem(prFraction, MAX_USES));
    }
    
    @Test
    public void testAddNegativeItems() {
        try {
            testBuilder.addItem(prAvailable, -5);
            fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            ;
        }
    }

    
    @Test
    public void testAddSomeItems() {
        testBuilder.addItem(prAvailable, MAX_USES - 1);
        assertEquals(MAX_USES - 1, testBuilder.getQuantity(prAvailable));
    }
    
    @Test
    public void testAddMaxItems() {
        testBuilder.addItem(prAvailable, MAX_USES);
        assertEquals(MAX_USES, testBuilder.getQuantity(prAvailable));
    }
    
    @Test
    public void testAddExcessItems() {
        try {
            testBuilder.addItem(prFraction, MAX_USES);
            fail("InsufficientStockException should have been thrown");
        } catch (InsufficientStockException e) {
            
        }
    }
    
    @Test
    public void testRemoveItem() {
        testBuilder.addItem(prAvailable, 3);
        testBuilder.removeItem(prAvailable);
        assertFalse(testBuilder.getCurrentOrder().containsKey(prAvailable));
    }
    
    @Test
    public void testRemoveMissingItem() {
        testBuilder.removeItem(prUnavailable);
        assertFalse(testBuilder.getCurrentOrder().containsKey(prUnavailable));
    }
    
    public void testBuildNormal() {
        assertEquals("Checking setup is correct:", 2, testBuilder.currentOrder.size());
        
        
    }

}
