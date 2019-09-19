package foodstart.model.menu;

import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class OnTheFlyRecipeTest {
    private OnTheFlyRecipe testOnTheFlyRecipe;
    private PermanentRecipe testPermanentRecipeBasis;
    private Map<Ingredient, Integer> testPermanentIngredients;
    private Map<Ingredient, Integer> testAddOnIngredients;
    private Ingredient testIngredient1;
    private Ingredient testIngredient2;
    private Ingredient testIngredient3;


    @Before
    public void setUp() {
        Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
        safeFor.put(DietaryRequirement.GLUTEN_FREE, true);
        safeFor.put(DietaryRequirement.NUT_ALLERGY, true);

        testIngredient1 = new Ingredient(Unit.GRAMS, "Ingredient1",
                1, safeFor, 8, 10);
        testIngredient2 = new Ingredient(Unit.UNITS, "Ingredient2",
                2, safeFor, 5, 0);
        testIngredient3 = new Ingredient(Unit.MILLILITRES, "Ingredient3",
                3, safeFor, 10, 10);

        testPermanentIngredients = new HashMap<Ingredient, Integer>();
        testPermanentIngredients.put(testIngredient1, 1);

        testAddOnIngredients = new HashMap<Ingredient, Integer>();
        testAddOnIngredients.put(testIngredient2, 2);

        testPermanentRecipeBasis = new PermanentRecipe(1, "PermanentRecipeName", "PermanentRecipeInstructions", 5, testPermanentIngredients);
        testOnTheFlyRecipe = new OnTheFlyRecipe(testPermanentRecipeBasis, testAddOnIngredients, 8);
    }

    @Test
    public void isSafeFor() {
        assertTrue(testOnTheFlyRecipe.isSafeFor(DietaryRequirement.GLUTEN_FREE));
        assertTrue(testOnTheFlyRecipe.isSafeFor(DietaryRequirement.NUT_ALLERGY));
        assertFalse(testOnTheFlyRecipe.isSafeFor(DietaryRequirement.VEGAN));
        assertFalse(testOnTheFlyRecipe.isSafeFor(DietaryRequirement.VEGETARIAN));
        assertFalse(testOnTheFlyRecipe.isSafeFor(DietaryRequirement.LACTOSE_INTOLERANT));
    }

    @Test
    public void isAvailable() {
        assertTrue(((Recipe) testOnTheFlyRecipe).isAvailable());
        assertTrue(testOnTheFlyRecipe.getIngredients().containsKey(testIngredient2));

        testIngredient2.setKitchenStock(0);
        assertFalse(((Recipe) testOnTheFlyRecipe).isAvailable());
    }

    @Test
    public void getPrice() {
    }

    @Test
    public void setPrice() {
    }

    @Test
    public void getIngredients() {
    }

    @Test
    public void setIngredients() {
    }

    @Test
    public void addIngredient() {
        //Add an ingredient that doesn't exist on the current ingredients list of the item
        assertFalse(testOnTheFlyRecipe.getIngredients().containsKey(testIngredient3));
        testOnTheFlyRecipe.addIngredient(testIngredient3, 1);
        assertTrue(testOnTheFlyRecipe.getIngredients().containsKey(testIngredient3));
        assertTrue(testOnTheFlyRecipe.getIngredients().get(testIngredient3) == 1);

        //Add an ingredient that already exists on the current ingredients list of the item
        testOnTheFlyRecipe.addIngredient(testIngredient3, 2);
        assertTrue(testOnTheFlyRecipe.getIngredients().get(testIngredient3) == 2);
    }

    @Test
    public void removeIngredient() { //throw exception that ingredient does not exist
        //Remove an ingredient that has exists on the current ingredients list of the item
        assertTrue(testOnTheFlyRecipe.getIngredients().containsKey(testIngredient2));
        testOnTheFlyRecipe.removeIngredient(testIngredient2);
        assertFalse(testOnTheFlyRecipe.getIngredients().containsKey(testIngredient2));

        //Remove an ingredient that doesn't exists on the current ingredients list of the item
        assertNull(testOnTheFlyRecipe.removeIngredient(testIngredient2));
    }

    @Test
    public void getBasedOn() {
    }

    @Test
    public void setBasedOn() {
    }
}