/**
 * 
 */
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
import foodstart.model.DataType;
import foodstart.model.Unit;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;
import foodstart.model.stock.Ingredient;

/**
 * @author Alan Wang
 *
 */
public class XMLRecipeExportTest {


    Persistence persistence = new XMLPersistence();
    File dataFile;
    static final RecipeManager recipeManager= Managers.getRecipeManager();
    static final String DIRECTORY = "resources/exported_test_files/", PREFIX = "SalesLogExportTest";
    static PermanentRecipe permRecipeNorm1, permRecipeNorm2, permRecipeNorm3;
    static Map<Integer, PermanentRecipe> expectedRecipes, actualRecipes;
    static Map<Ingredient, Integer> dummyIngredients;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        dummyIngredients = new HashMap<Ingredient, Integer>();
        dummyIngredients.put(new Ingredient(Unit.GRAMS, "dummy", 0, null, 0, 0), 2);
        permRecipeNorm1 = new PermanentRecipe(1, "testRecipe", "", 10, dummyIngredients);
        permRecipeNorm2 = new PermanentRecipe(2, "testRecipe2", "", 20, dummyIngredients);
        permRecipeNorm3 = new PermanentRecipe(3, "testRecipe3", "", 30, dummyIngredients);
    }
    
    @Before
    public void setUp() throws Exception {
        actualRecipes =  new HashMap<Integer, PermanentRecipe>();
        expectedRecipes =  new HashMap<Integer, PermanentRecipe>();
        Managers.getRecipeManager().removeAllRecipes();
    }

    
    @After
    public void tearDown() throws Exception {
        actualRecipes = null;
        expectedRecipes = null;
        Managers.getRecipeManager().removeAllRecipes();
    }
    
    private Map<Integer, PermanentRecipe> getExportedData(File exportedData) {
        Managers.getOrderManager().removeAllOrders();
        persistence.importFile(exportedData, DataType.SALES_LOG);
        Managers.writeBuffer();
        return Managers.getRecipeManager().getRecipes();
    }

    @Test
    public final void testExportRecipeNormal() {
        expectedRecipes.put(1, permRecipeNorm1);
        expectedRecipes.put(2, permRecipeNorm2);
        expectedRecipes.put(3, permRecipeNorm3);
        for (PermanentRecipe recipe : expectedRecipes.values()) {
            Managers.getRecipeManager().addRecipe(recipe);
        }
        dataFile = new File(DIRECTORY + PREFIX + "Normal.xml");
        persistence.exportFile(dataFile, DataType.RECIPE);
        actualRecipes = getExportedData(dataFile);
        assertEquals(expectedRecipes, actualRecipes);
    }
    
    @Test
    public final void testExportRecipeEmpty() {
        dataFile = new File(DIRECTORY + PREFIX + "Normal.xml");
        persistence.exportFile(dataFile, DataType.RECIPE);
        actualRecipes = getExportedData(dataFile);
        assertEquals(expectedRecipes, actualRecipes);
    }

}
