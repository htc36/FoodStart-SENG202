package foodstart.manager.xml;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
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

/**
 * @author Alan Wang
 *
 */
public class XMLSalesLogExportTest {

    Persistence persistence;
    File dataFile;
    Map<Integer, Order> expectedOrders = new HashMap<Integer, Order>();
    Map<Integer, Order> actualOrders;
    static final String DIRECTORY = "resources/exported_test_files/", PREFIX = "SalesLogExportTest";
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
        XMLPersistence dtdCopier = (XMLPersistence) persistence;
        dtdCopier.copyDTDFiles(new File(DIRECTORY), true);
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
    
    private Map<Integer, Order> importExportedData(File exportedData) {
        Managers.getOrderManager().removeAllOrders();
        persistence.importFile(exportedData, DataType.SALES_LOG);
        Managers.writeBuffer();
        return Managers.getOrderManager().getOrders();
    }

    @Test
    public final void testExportSalesLogNormalData() {
        expectedOrders = buildNormalSalesLog();
        for (Order order : expectedOrders.values()) {
            Managers.getOrderManager().addOrder(order);
        }
        dataFile = new File(DIRECTORY + PREFIX + "Normal.xml");
        persistence.exportFile(dataFile, DataType.SALES_LOG);
        actualOrders = importExportedData(dataFile);
        assertEquals(expectedOrders, actualOrders);
    }
    
    @Test
    public final void testExportSalesLogEmptyData() {
        dataFile = new File(DIRECTORY + PREFIX + "Normal.xml");
        persistence.exportFile(dataFile, DataType.SALES_LOG);
        actualOrders = importExportedData(dataFile);
        assertEquals(expectedOrders, actualOrders);
    }

}
