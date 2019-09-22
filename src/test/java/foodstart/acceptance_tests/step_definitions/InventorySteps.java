package foodstart.acceptance_tests.step_definitions;

import foodstart.manager.stock.IngredientManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;
import cucumber.api.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

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

    @Given("That ingredient {string} is not found in the inventory")
    public void thatIngredientIsNotFoundInTheInventory(String ingredientName) {
        IngredientManager manager = new IngredientManager();
        this.ingredientManager = manager;
        this.ingredientName = ingredientName;

        assertNull(ingredientManager.getIngredientByName(ingredientName));
    }

    @And("Its unit type is {string}")
    public void itsUnitTypeIs(String unitType) {
        this.unitType = Unit.matchUnit(unitType);
    }

    @And("Its kitchen stock is {int}")
    public void itsKitchenStockIs(Integer kitchenStock) {
        this.kitchenStock = kitchenStock;
    }

    @And("Its truck stock is {int}")
    public void itsTruckStockIs(Integer truckStock) {
        this.truckStock = truckStock;
    }

    @And("Its dietary requirement is {string}")
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


}
