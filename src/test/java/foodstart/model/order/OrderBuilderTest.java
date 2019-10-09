package foodstart.model.order;

import foodstart.manager.Managers;
import foodstart.manager.exceptions.InsufficientStockException;
import foodstart.model.DietaryRequirement;
import foodstart.model.PaymentMethod;
import foodstart.model.Unit;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.OnTheFlyRecipe;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

public class OrderBuilderTest {
    
    OrderBuilder testBuilder;
    Ingredient ingrFull, ingrSome, ingrNone;
    Map<DietaryRequirement, Boolean> testDiet;
    Map<Recipe, Integer> normOrderItems, emptyOrderItems;
    Recipe prMaxAvailable, prSomeAvailable, prUnavailable,
        otfMaxAvailable, otfSomeAvailable; 
    Order normalOrder, emptyOrder;
    final int QTY_USED = 5, MAX_USES = 4;
    final int BASE_STOCK = QTY_USED * MAX_USES;
    
    private void setStockLevels() {
        ingrFull.setKitchenStock(BASE_STOCK);
        ingrFull.setTruckStock(BASE_STOCK);
        ingrSome.setKitchenStock(BASE_STOCK - 1);
        ingrSome.setTruckStock(BASE_STOCK - 1);
        ingrNone.setKitchenStock(0);
        ingrNone.setTruckStock(0);
    }

    @Before
    public void setUp() throws Exception {
        testBuilder = new OrderBuilder();
        testDiet = new HashMap<DietaryRequirement, Boolean>();
        testDiet.put(DietaryRequirement.GLUTEN_FREE, true);
        testDiet.put(DietaryRequirement.NUT_ALLERGY, false);
        
        ingrFull = new Ingredient(Unit.GRAMS, "Cheese", 0, testDiet, 0, 0);
        ingrSome = new Ingredient(Unit.UNITS, "Potato", 1, testDiet, 0, 0);
        ingrNone = new Ingredient(Unit.UNITS, "Electric Boogaloo", 3, testDiet, 0, 0);
        
        HashMap<Ingredient, Integer> ingrMapMaxAvailable = new HashMap<Ingredient, Integer>();
        HashMap<Ingredient, Integer> ingrMapSomeAvailable = new HashMap<Ingredient, Integer>();
        HashMap<Ingredient, Integer> ingrMapUnavailable = new HashMap<Ingredient, Integer>();
        ingrMapMaxAvailable.put(ingrFull, QTY_USED);
        ingrMapSomeAvailable.put(ingrFull, QTY_USED);
        ingrMapSomeAvailable.put(ingrSome, QTY_USED);
        ingrMapUnavailable.put(ingrNone, QTY_USED);
        
        prMaxAvailable = new PermanentRecipe(0, "Plate of Cheese", "dummy", 10 , ingrMapMaxAvailable);
        prSomeAvailable = new PermanentRecipe(1, "Potato and Cheese", "dummy", 7 , ingrMapSomeAvailable);
        prUnavailable = new PermanentRecipe(2, "Plate of ElectricBoogaloo", "dummy", 4 , ingrMapUnavailable);
        otfMaxAvailable = new OnTheFlyRecipe((PermanentRecipe) prMaxAvailable, ingrMapMaxAvailable, 11);
        otfSomeAvailable = new OnTheFlyRecipe((PermanentRecipe) prUnavailable, ingrMapSomeAvailable, 1);
        
        normOrderItems = new HashMap<Recipe, Integer>();
        emptyOrderItems = new HashMap<Recipe, Integer>();
        normOrderItems.put(prMaxAvailable, MAX_USES - 1);
        
        normalOrder = new Order(0, normOrderItems, "Jom", 0, PaymentMethod.CASH);
        setStockLevels();
        Managers.getIngredientManager().getIngredients().clear();
        Managers.getOrderManager().getOrders().clear();
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testCanAddNegativeItems() {
        try {
            testBuilder.canAddItem(prMaxAvailable, -1);
        } catch (Exception e) {
            
        }
    }

    @Test
    public void testCanAddSomeItems() {
        assertTrue(testBuilder.canAddItem(prMaxAvailable, MAX_USES - 1));
    }
    
    @Test
    public void testCanAddExactMaxItems() {
        assertTrue(testBuilder.canAddItem(prMaxAvailable, MAX_USES));
    }
    
    @Test
    public void testCanAddExcessItems() {
        testBuilder.addItem(prMaxAvailable, 2);
        //assertFalse(testBuilder.canAddItem(prMaxAvailable, MAX_USES));
        HashMap<Ingredient, Integer> a = new HashMap<Ingredient, Integer>();
        HashMap<MenuItem, Integer> b = new HashMap<MenuItem, Integer>();
        a.put(ingrFull, 1);
        for (Ingredient i: prMaxAvailable.getIngredients().keySet()) {
            System.out.println(prMaxAvailable.getIngredients().containsKey(i));
        }
        Set<PermanentRecipe> bSet = new HashSet<PermanentRecipe>();
        bSet.add((PermanentRecipe) prMaxAvailable);
        MenuItem bKey = new MenuItem(0, "", "", bSet, (PermanentRecipe) prMaxAvailable);
        
    }
    
    @Test
    public void testCanAddExcessItemsFraction() {
        assertFalse(testBuilder.canAddItem(prSomeAvailable, MAX_USES));
    }
    
    @Test
    public void testAddNegativeItems() {
        try {
            testBuilder.addItem(prMaxAvailable, -5);
            fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            ;
        }
    }

    
    @Test
    public void testAddSomeItems() {
        testBuilder.addItem(prMaxAvailable, MAX_USES - 1);
        assertEquals(MAX_USES - 1, testBuilder.getQuantity(prMaxAvailable));
    }
    
    @Test
    public void testAddMaxItems() {
        testBuilder.addItem(prMaxAvailable, MAX_USES);
        assertEquals(MAX_USES, testBuilder.getQuantity(prMaxAvailable));
    }
    
    @Test
    public void testAddExcessItems() {
        try {
            testBuilder.addItem(prSomeAvailable, MAX_USES);
            fail("InsufficientStockException should have been thrown");
        } catch (InsufficientStockException e) {
            
        }
    }
    
    @Test
    public void testAddExistingItems() {
        testBuilder.addItem(prMaxAvailable, 1);
        testBuilder.addItem(prMaxAvailable, MAX_USES - 1);
        assertEquals(MAX_USES, testBuilder.getQuantity(prMaxAvailable));
    }
    
    @Test
    public void testAddCustomItems() {
        testBuilder.addItem(otfMaxAvailable, MAX_USES - 1);
        assertEquals(MAX_USES - 1, testBuilder.getQuantity(otfMaxAvailable));
    }
    
    @Test
    public void testAddExcessCustomItems() {
        try {
            testBuilder.addItem(otfSomeAvailable, MAX_USES);
            fail("InsufficientStockException should have been thrown");
        } catch (InsufficientStockException e) {
            
        }
    }
    
    @Test
    public void testRemoveItem() {
        testBuilder.addItem(prMaxAvailable, 3);
        testBuilder.removeItem(prMaxAvailable);
        assertFalse(testBuilder.getCurrentOrder().containsKey(prMaxAvailable));
    }
    
    @Test
    public void testRemoveMissingItem() {
        testBuilder.removeItem(prUnavailable);
        assertFalse(testBuilder.getCurrentOrder().containsKey(prUnavailable));
    }
    
    @Test
    public void testGetTotalPriceEmpty() {
        float price = testBuilder.getCurrentTotalPrice();
        assertEquals(0.0, price, 0.005);
    }
    
    @Test
    public void testGetTotalPriceNormal() {
        int countPRMax = 2, countPRSome = 2;
        testBuilder.addItem(prMaxAvailable, countPRMax);
        testBuilder.addItem(prSomeAvailable, countPRSome);
        float expectedPrice = countPRMax * prMaxAvailable.getPrice() + countPRSome * prSomeAvailable.getPrice(),
                actualPrice = testBuilder.getCurrentTotalPrice();
        assertEquals(expectedPrice, actualPrice, 0.005);
    }
    
    @Test
    public void testCalculateRequiredIngredientAmountEmpty() {
        assertEquals(0, testBuilder.calculateRequiredStock(ingrFull));
        assertEquals(0, testBuilder.calculateRequiredStock(ingrSome));
        assertEquals(0, testBuilder.calculateRequiredStock(ingrNone));
    }
    
    @Test
    public void testCalculateRequiredIngredientAmountBasic() {
        int countPRMax = 2, countPRSome = 2;
        testBuilder.addItem(prMaxAvailable, countPRMax);
        testBuilder.addItem(prSomeAvailable, countPRSome);
        int expectedCheese = (countPRMax + countPRSome) * QTY_USED, expectedPotato = countPRSome * QTY_USED;
        //System.out.println(testBuilder.calculateRequiredStock(ingrFull));
        //System.out.println(testBuilder.currentOrder);
        assertEquals(expectedCheese, testBuilder.calculateRequiredStock(ingrFull));
        assertEquals(expectedPotato, testBuilder.calculateRequiredStock(ingrSome));
    }
    
    
    @Test
    public void testCalculateRequiredIngredientAmountWithEditing() {
        int countPRMax = 2, countPRSome = 2;
        testBuilder.addItem(prMaxAvailable, countPRMax);
        testBuilder.addItem(prSomeAvailable, countPRSome);
        testBuilder.setEditing(prMaxAvailable, true);
        int expectedCheese = countPRSome * QTY_USED, expectedPotato = countPRSome * QTY_USED;
        //System.out.println(testBuilder.currentOrder);
        //System.out.println(testBuilder.calculateRequiredStock(ingrFull));
        assertEquals(expectedCheese, testBuilder.calculateRequiredStock(ingrFull));
        assertEquals(expectedPotato, testBuilder.calculateRequiredStock(ingrSome));
    }

    
    @Test
    public void testBuildNormal() {
        String name = "Jom";
        Managers.getIngredientManager().addIngredient(ingrFull);
        Managers.getIngredientManager().addIngredient(ingrSome);
        int id = Managers.getOrderManager().getOrders().keySet().size() == 0 ? 0
                : Collections.max(Managers.getOrderManager().getOrders().keySet()) + 1;
        while (Managers.getOrderManager().getOrder(id) != null) {
            id++;
        }
        LocalDateTime current = LocalDateTime.now();
        normalOrder = new Order(id, normOrderItems, name, current, PaymentMethod.CASH);
        testBuilder.addItem(prMaxAvailable, MAX_USES - 1);
        testBuilder.build(name, PaymentMethod.CASH);
        Managers.getOrderManager().getOrder(id).setTimePlaced(current);
        assertEquals("Check order has been built: ", normalOrder, Managers.getOrderManager().getOrder(id));
    }
    
    private Set<Recipe> setupEditing() {
        testBuilder.setEditing(otfMaxAvailable, true);
        testBuilder.setEditing(otfSomeAvailable, true);
        testBuilder.setEditing(prMaxAvailable, false);
        testBuilder.setEditing(prSomeAvailable, true);
        testBuilder.setEditing(prUnavailable, false);
        return new HashSet<Recipe>(testBuilder.editing);
    }
    
    @Test
    public void setEditingFromFalseToTrue() {
        Set<Recipe> expected = setupEditing();
        expected.add(prMaxAvailable);
        testBuilder.setEditing(prMaxAvailable, true);
        assertEquals(expected, testBuilder.editing);
    }
    
    @Test
    public void setEditingFromTrueToFalse() {
        Set<Recipe> expected = setupEditing();
        expected.remove(prSomeAvailable);
        testBuilder.setEditing(prSomeAvailable, false);
        assertEquals(expected, testBuilder.editing);
    }
    
    @Test
    public void setEditingFromFalseToFalse() {
        Set<Recipe> expected = setupEditing();
        testBuilder.setEditing(prMaxAvailable, false);
        assertEquals(expected, testBuilder.editing);
    }
    
    @Test
    public void setEditingFromTrueToTrue() {
        Set<Recipe> expected = setupEditing();
        testBuilder.setEditing(prSomeAvailable, true);
        assertEquals(expected, testBuilder.editing);
    }
}
