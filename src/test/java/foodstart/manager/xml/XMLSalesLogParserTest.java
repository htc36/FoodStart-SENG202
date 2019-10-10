package foodstart.manager.xml;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.manager.exceptions.IDLeadsNowhereException;
import foodstart.manager.exceptions.ImportFailureException;
import foodstart.manager.menu.RecipeManager;
import foodstart.manager.stock.IngredientManager;
import foodstart.model.DataType;
import foodstart.model.DietaryRequirement;
import foodstart.model.PaymentMethod;
import foodstart.model.Unit;
import foodstart.model.menu.OnTheFlyRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;
import foodstart.model.stock.Ingredient;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class XMLSalesLogParserTest {
    
    Persistence persistence;
    File dataFile;
    Map<Integer, Order> expectedOrders = new HashMap<Integer, Order>();
    Map<Integer, Order> actualOrders;
    static final String DIRECTORY = "resources/data/sales_log_xml_files/", PREFIX = "SalesLogParserTestData";
    static IngredientManager ingredientManager;
    static RecipeManager recipeManager;
    
    @BeforeClass
    public static void setUpBeforeClass() {
        recipeManager = Managers.getRecipeManager();
        Map<Ingredient, Integer> dummyIngrMap = new HashMap<Ingredient, Integer>();
        recipeManager.addRecipe(1, "PermRecipe1", "", (float) 2, dummyIngrMap);
        recipeManager.addRecipe(2, "PermRecipe2", "", (float) 3, dummyIngrMap);
        recipeManager.addRecipe(3, "PermRecipe3", "", (float) 5, dummyIngrMap);
        recipeManager.addRecipe(4, "PermRecipe4", "", (float) 8, dummyIngrMap);

        ingredientManager = Managers.getIngredientManager();
        Map<DietaryRequirement, Boolean> dummySafeFor = new HashMap<DietaryRequirement, Boolean>();
        ingredientManager.addIngredient(Unit.GRAMS, "Ingredient1", 1, dummySafeFor, 100, 100);
        ingredientManager.addIngredient(Unit.UNITS, "Ingredient2", 2, dummySafeFor, 100, 100);
        ingredientManager.addIngredient(Unit.MILLILITRES, "Ingredient3", 3, dummySafeFor, 100, 100);
        ingredientManager.addIngredient(Unit.GRAMS, "Ingredient4", 4, dummySafeFor, 100, 100);
    }

    @Before
    public void setUp() throws Exception {
        persistence = new XMLPersistence();
        expectedOrders = new HashMap<Integer, Order>();
        actualOrders = Managers.getOrderManager().getOrders();
    }

    @After
    public void tearDown() throws Exception {
        Managers.getOrderManager().removeAllOrders();
        persistence = null;
        dataFile = null;
    }
    
    private HashMap<Integer, Order> buildNormalSalesLog() {
        HashMap<Integer, Order> expectedMap = new HashMap<Integer, Order>();
        
        Map<Ingredient, Integer> otfIngredients = new HashMap<Ingredient, Integer>();
        otfIngredients.put(ingredientManager.getIngredient(1), 3);
        otfIngredients.put(ingredientManager.getIngredient(2), 2);
        
        Map<Recipe, Integer> orderItems = new HashMap<Recipe, Integer>();
        orderItems.put(recipeManager.getRecipe(1), 3);
        orderItems.put(recipeManager.getRecipe(3), 2);
        orderItems.put(new OnTheFlyRecipe(recipeManager.getRecipe(2), otfIngredients, (float) 7.50), 2);
        
        expectedMap.put(1, new Order(1, orderItems, "Jom", (long) 668512349, PaymentMethod.CASH, (float) 23.50));
        return expectedMap;
    }
    
    @Test
    public void testImportNormalSalesLog() {
        expectedOrders = buildNormalSalesLog();
        dataFile = new File(DIRECTORY + PREFIX + "Normal.xml");
        persistence.importFile(dataFile, DataType.SALES_LOG);
        Managers.writeBuffer();
        for (Integer id: expectedOrders.keySet()) {
            Order expected = expectedOrders.get(id), actual = actualOrders.get(id);
            assertEquals(String.format("Normal data ID%d import test", id), expected, actual);
        }
    }
    
    @Test
    public void testImportEmptySalesLog() {
        dataFile = new File(DIRECTORY + PREFIX + "Empty.xml");
        persistence.importFile(dataFile, DataType.SALES_LOG);
        Managers.writeBuffer();
        assertEquals(0, actualOrders.size());
    }
    
    @Test
    public void testImportBadFormatSalesLog() {
        boolean threwException = false;
        try {
            dataFile = new File(DIRECTORY + PREFIX + "BadFormat.xml");
            persistence.importFile(dataFile, DataType.SALES_LOG);
        } catch (ImportFailureException e) {
            threwException = true;
        }
        assertTrue(threwException);
    }
    
    @Test
    public void testImportBadForeignKeysSalesLog() {
        boolean threwException = false;
        try {
            dataFile = new File(DIRECTORY + PREFIX + "BadForeignKeys.xml");
            persistence.importFile(dataFile, DataType.SALES_LOG);
        } catch (IDLeadsNowhereException e) {
            threwException = true;
        }
        assertTrue(threwException);
    }
    
    @Test
    public void testImportNonIntegerIDSalesLog() {
        boolean threwException = false;
        try {
            dataFile = new File(DIRECTORY + PREFIX + "NonIntegerID.xml");
            persistence.importFile(dataFile, DataType.SALES_LOG);
        } catch (NumberFormatException e) {
            threwException = true;
        }
        assertTrue(threwException);
    }
}
