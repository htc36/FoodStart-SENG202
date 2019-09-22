package foodstart.acceptance_tests.step_definitions;

import foodstart.model.stock.Ingredient;
import cucumber.api.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InventorySteps {
    @Given("An employee wants to view all ingredients")
    public void anEmployeeWantsToViewAllIngredients() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("The ingredients list is displayed")
    public void theIngredientsListIsDisplayed() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("All relevant information is shown, i.e. name, amount in each location \\(Truck & depot)")
    public void allRelevantInformationIsShownIENameAmountInEachLocationTruckDepot() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("The list of ingredients are shown")
    public void theListOfIngredientsAreShown() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("Ingredients are filtered out by stock")
    public void ingredientsAreFilteredOutByStock() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The ingredients will be displayed in ascending order of stocks available")
    public void theIngredientsWillBeDisplayedInAscendingOrderOfStocksAvailable() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("That ingredient {string} is not found in the inventory")
    public void thatIngredientIsNotFoundInTheInventory(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("Its unit type is {string} \\(g)")
    public void itsUnitTypeIsG(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("Its kitchen stock is {int}")
    public void itsKitchenStockIs(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("Its truck stock is {int}")
    public void itsTruckStockIs(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("It is vegan, vegetarian, gluten free, nut free and dairy free")
    public void itIsVeganVegetarianGlutenFreeNutFreeAndDairyFree() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("Ingredient {string} is manually added to the inventory")
    public void ingredientIsManuallyAddedToTheInventory(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("It will be stored under {string} in the inventory")
    public void itWillBeStoredUnderInTheInventory(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("The ingredient will no longer be needed")
    public void theIngredientWillNoLongerBeNeeded() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("It is removed from the list of ingredients")
    public void itIsRemovedFromTheListOfIngredients() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("It is removed from menu items that contain the ingredient")
    public void itIsRemovedFromMenuItemsThatContainTheIngredient() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("An ingredient is sold out and it is included in menu items")
    public void anIngredientIsSoldOutAndItIsIncludedInMenuItems() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("A customer wants to order the item with an ingredient that is sold out")
    public void aCustomerWantsToOrderTheItemWithAnIngredientThatIsSoldOut() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The employee will not be able to add the item to the order")
    public void theEmployeeWillNotBeAbleToAddTheItemToTheOrder() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("The manager wants to edit ingredients")
    public void theManagerWantsToEditIngredients() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("A specific item is selected, and the stock level is edited")
    public void aSpecificItemIsSelectedAndTheStockLevelIsEdited() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The items stock level is changed, and the updated kitchen and depot stock is shown")
    public void theItemsStockLevelIsChangedAndTheUpdatedKitchenAndDepotStockIsShown() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
