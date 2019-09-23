package foodstart.acceptance_tests.step_definitions;

import foodstart.manager.stock.IngredientManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class InventorySteps {
    private IngredientManager ingredientManager;

    private Ingredient ingredient;
    private String ingredientName;
    private Unit unitType;
    private int kitchenStock;
    private int truckStock;
    private Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();

    @Before
    public void setUp() {
        IngredientManager manager = new IngredientManager();
        this.ingredientManager = manager;

        Map<DietaryRequirement, Boolean> safeForIngredient1 = new HashMap<DietaryRequirement, Boolean>();
        safeForIngredient1.put(DietaryRequirement.GLUTEN_FREE, true);
        safeForIngredient1.put(DietaryRequirement.VEGETARIAN, true);

        Map<DietaryRequirement, Boolean> safeForIngredient2 = new HashMap<DietaryRequirement, Boolean>();
        safeForIngredient1.put(DietaryRequirement.GLUTEN_FREE, true);
        safeForIngredient1.put(DietaryRequirement.VEGETARIAN, true);
        safeForIngredient1.put(DietaryRequirement.NUT_ALLERGY, true);
        safeForIngredient1.put(DietaryRequirement.VEGAN, true);
        safeForIngredient1.put(DietaryRequirement.LACTOSE_INTOLERANT, true);

        ingredientManager.addIngredient(Unit.GRAMS, "Mayo", 1, safeForIngredient1, 150, 80);
        ingredientManager.addIngredient(Unit.GRAMS, "Peanut Butter", 2, safeForIngredient2, 120, 45);
    }

    @Given("That ingredient {string} is not found in the inventory")
    public void thatIngredientIsNotFoundInTheInventory(String ingredientName) {
        this.ingredientName = ingredientName;

        assertNull(ingredientManager.getIngredientByName(ingredientName));
    }

    @Given("Its unit type is {string}")
    public void itsUnitTypeIs(String unitType) {
        this.unitType = Unit.matchUnit(unitType);
    }

    @Given("Its kitchen stock is {int}")
    public void itsKitchenStockIs(Integer kitchenStock) {
        this.kitchenStock = kitchenStock;
    }

    @Given("Its truck stock is {int}")
    public void itsTruckStockIs(Integer truckStock) {
        this.truckStock = truckStock;
    }

    @Given("Its dietary requirement is {string}")
    public void itsDietaryRequirementIs(String dietaryRequirement) {
        this.safeFor.put(DietaryRequirement.matchDietaryRequirement(dietaryRequirement), true);
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

    @When("The inventory is displayed")
    public void theInventoryIsDisplayed() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The ID, name, truck stock, kitchen stock and dietary requirements for all {int} ingredients are displayed")
    public void theIDNameTruckStockKitchenStockAndDietaryRequirementsForAllIngredientsAreDisplayed(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }


}
