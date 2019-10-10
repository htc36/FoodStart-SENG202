package foodstart.acceptance_tests.step_definitions;

import foodstart.manager.stock.IngredientManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.*;


public class InventorySteps {
    private IngredientManager ingredientManager = new IngredientManager();;
    private Map<Integer, Ingredient> existingIngredients;

    private Ingredient ingredient;
    private int id;
    private String name;
    private Unit unitType;
    private int kitchenStock;
    private int truckStock;
    private Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();


    /**
     * Creates a set of ingredients that can be added to the inventory before testing if needed
     */
    private void createIngredientSet() {

        HashMap<Integer, Ingredient> ingredientSet = new HashMap<Integer, Ingredient>();


        Map<DietaryRequirement, Boolean> safeForIngredient1 = new HashMap<DietaryRequirement, Boolean>();
        safeForIngredient1.put(DietaryRequirement.GLUTEN_FREE, true);
        safeForIngredient1.put(DietaryRequirement.VEGETARIAN, true);

        Map<DietaryRequirement, Boolean> safeForIngredient2 = new HashMap<DietaryRequirement, Boolean>();
        safeForIngredient2.put(DietaryRequirement.GLUTEN_FREE, true);
        safeForIngredient2.put(DietaryRequirement.VEGETARIAN, true);
        safeForIngredient2.put(DietaryRequirement.NUT_ALLERGY, true);
        safeForIngredient2.put(DietaryRequirement.VEGAN, true);
        safeForIngredient2.put(DietaryRequirement.LACTOSE_INTOLERANT, true);

        Map<DietaryRequirement, Boolean> safeForIngredient3 = new HashMap<DietaryRequirement, Boolean>();
        safeForIngredient3.put(DietaryRequirement.GLUTEN_FREE, true);
        safeForIngredient3.put(DietaryRequirement.VEGETARIAN, true);
        safeForIngredient3.put(DietaryRequirement.VEGAN, true);

        Ingredient mayo = new Ingredient(Unit.GRAMS, "Mayo", 1, safeForIngredient1, 150, 20);
        Ingredient peanutButter = new Ingredient(Unit.GRAMS, "Peanut Butter", 2, safeForIngredient2, 120, 45);
        Ingredient cucumber = new Ingredient(Unit.GRAMS, "Cucumber", 3, safeForIngredient3, 10, 100);

        ingredientSet.put(0, mayo);
        ingredientSet.put(1, peanutButter);
        ingredientSet.put(2, cucumber);

        existingIngredients = ingredientSet;

    }

    @Given("There are {int} ingredients in the inventory")
    public void thereAreIngredientsInTheInventory(Integer numberOfIngredients) {
        createIngredientSet();
        for (int i = 0; i < numberOfIngredients; i++) {
            ingredientManager.addIngredient(existingIngredients.get(i));
        }
    }

    @Given("That ingredient {string} is not found in the inventory")
    public void thatIngredientIsNotFoundInTheInventory(String ingredientName) {
        name = ingredientName;

        assertNull(ingredientManager.getIngredientByName(name));
    }

    @Given("Its unit type is {string}")
    public void itsUnitTypeIs(String ingredientUnitType) {
        unitType = Unit.matchUnit(ingredientUnitType);
    }

    @Given("Its kitchen stock is {int}")
    public void itsKitchenStockIs(Integer ingredientKitchenStock) {
        kitchenStock = ingredientKitchenStock;
    }

    @Given("Its truck stock is {int}")
    public void itsTruckStockIs(Integer ingredientTruckStock) {
        truckStock = ingredientTruckStock;
    }

    @Given("Its dietary requirement is {string}")
    public void itsDietaryRequirementIs(String dietaryRequirement) {
        List<String> listOfRequirements = Arrays.asList(dietaryRequirement.split(","));
        for (String requirement : listOfRequirements) {
            safeFor.put(DietaryRequirement.matchDietaryRequirement(requirement), true);
        }
    }

    @When("Ingredient {string} is manually added to the inventory")
    public void ingredientIsManuallyAddedToTheInventory(String ingredientName) {
        ingredientManager.addIngredient(unitType, ingredientName, 100, safeFor, kitchenStock, truckStock);
    }

    @Then("It will be stored under {string} in the inventory")
    public void itWillBeStoredUnderInTheInventory(String ingredientName) {
        assertNotNull((ingredientManager.getIngredientByName(ingredientName)));
    }

    @Given("An employee wants to view the inventory")
    public void anEmployeeWantsToViewTheInventory() {
        throw new cucumber.api.PendingException();
    }

    @Given("An employee wants to view the {int} ingredients stored inventory")
    public void anEmployeeWantsToViewTheIngredientsStoredInventory(int inventorySize) {
        assertTrue(ingredientManager.getIngredients().size() == inventorySize);
    }

    @Given("The ingredient {string} is in the inventory")
    public void theIngredientIsInTheInventory(String ingredientName) {
        assertNotNull(ingredientManager.getIngredientByName(ingredientName));
    }

    @Given("The ingredient's ID number is {int}")
    public void theIngredientSIDNumberIs(Integer ingredientID) {
        id = ingredientID;
    }

    @When("The ingredient {string} is manually removed")
    public void theIngredientIsManuallyRemoved(String ingredientName) {
        ingredientManager.removeIngredient(ingredientManager.getIngredientByName(ingredientName).getId());
    }

    @Then("The ingredient ID {int} no longer exists in the inventory")
    public void theIngredientIDNoLongerExistsInTheInventory(Integer ingredientID) {
        assertNull(ingredientManager.getIngredient(ingredientID));
    }

    @When("An order is placed that needs {int} of the ingredient {string}")
    public void anOrderIsPlacedThatNeedsOfTheIngredient(Integer count, String ingredientName) {
        Integer current = ingredientManager.getIngredientByName(ingredientName).getTruckStock();
        ingredientManager.getIngredientByName(ingredientName).setTruckStock(current - count);
    }

    @Then("The truck stock for {string} is {int}")
    public void theTruckStockForIs(String ingredientName, Integer count) {
        Integer current = ingredientManager.getIngredientByName(ingredientName).getTruckStock();
        assertEquals(count, current);
    }

    @Then("The kitchen stock for {string} is {int}")
    public void theKitchenStockForIs(String ingredientName, Integer count) {
        Integer current = ingredientManager.getIngredientByName(ingredientName).getKitchenStock();
        assertEquals(count, current);
    }

    @Then("The dietary requirement for {string} will have {string}")
    public void theDietaryRequirementForWillHave(String ingredientName, String dietaryRequirement) {
        Map<DietaryRequirement, Boolean> dietaryFlags = ingredientManager.getIngredientByName(ingredientName).getSafeFor();
        
        List<String> listOfRequirements = Arrays.asList(dietaryRequirement.split(","));
        for (String requirement : listOfRequirements) {
        	assertTrue(dietaryFlags.get(DietaryRequirement.matchDietaryRequirement(requirement)));
        }
    }
    
    @Then("The unit type for {string} is {string}")
    public void theUnitTypeForIs(String ingredientName, String unitType) {
        Unit unit = Unit.matchUnit(unitType);
        Ingredient ingredient = ingredientManager.getIngredientByName(ingredientName);
        assertEquals(ingredient.getUnit(), unit);
    }
    
    
    
    
}
