package foodstart.acceptance_tests.step_definitions;

import foodstart.manager.menu.RecipeManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.Map;

public class MenuSteps {
    private RecipeManager recipeManager;
    private PermanentRecipe hamburger;
    private String recipeName;
    private float price;
    private Unit unit;
    private Map<DietaryRequirement, Boolean> safeFor = new HashMap<>();
    private Ingredient ingredient;
    private Ingredient ingredient2;
    private int id;
    private String instructions;
    private Map<Ingredient, Integer> ingredientSet = new HashMap<>();


    @Given("The recipe {string} is not in the menu")
    public void anEmployeeWantsToSeeARecipe(String recipeName) {
        RecipeManager manager = new RecipeManager();
        this.recipeManager = manager;
        this.recipeName = recipeName;
    }
    @Given("Its price is {float}")
    public void itsPriceIs(float price) { this.price = price; }

    @Given("Its ingredients are {int} {string}s and {int} {string}")
    public void itsIngredientsAre(int ingredientAmount, String ingredientName, int ingredient2Amount, String ingredientName2) {
        this.safeFor.put(DietaryRequirement.matchDietaryRequirement("VEGAN"), true);
        ingredient = new Ingredient(Unit.matchUnit("GRAMS"), ingredientName, 1, safeFor, 5, 10);
        ingredient2 = new Ingredient(Unit.matchUnit("COUNTS"), ingredientName2, 2, safeFor, 7,8);
        ingredientSet.put(ingredient, ingredientAmount);
        ingredientSet.put(ingredient2, ingredient2Amount);
    }
    @Given ("Its id is {int}")
    public void itsIdIs(int id){
        this.id = id;
    }
    @Given ("Its instructions are {string}")
    public void itsInstructionsAre(String instructions) {
        this.instructions = instructions;
    }

    @When("Recipe {string} is manually added")
    public void recipeIsManuallyAdded(String recipeName) {
        recipeManager.addRecipe(id, recipeName, instructions, price, ingredientSet);
    }

    @Then("{string} that costs {float} with {int} {string}s and {int} {string}, id being {int}, instructions being {string}")
    public void theCorrespondingRecipeIsDisplayed(String name, float price, int amount, String iName, int amount2, String iName2, int id, String instructions) {
        PermanentRecipe recipe = recipeManager.getRecipe(id);
        assert(id == recipe.getId());
        assert(name.equals(recipe.getDisplayName()));
        assert(price == recipe.getPrice());
        assert(instructions.equals(recipe.getInstructions()));
    }

    @Given("An employee is looking through recipes")
    public void anEmployeeIsLookingThroughRecipes() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("The employee edits a specific recipe")
    public void theEmployeeEditsASpecificRecipe() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The recipe is updated in the system")
    public void theRecipeIsUpdatedInTheSystem() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("An employee is looking through menu items")
    public void anEmployeeIsLookingThroughMenuItems() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("The employee selects a specific menu item")
    public void theEmployeeSelectsASpecificMenuItem() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The corresponding recipes of the menu item are displayed")
    public void theCorrespondingRecipesVeganGFEtcOfTheMenuItemAreDisplayed() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("The employee edits a specific a menu item")
    public void theEmployeeEditsASpecificAMenuItemIETheDescriptionNameAndRecipes() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The corresponding menu item is updated with the new information")
    public void theCorrespondingMenuItemIsUpdatedWithTheNewInformation() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("An employee is looking through different menu sets")
    public void anEmployeeIsLookingThroughDifferentMenuSets() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("The employee selects a specific menu set")
    public void theEmployeeSelectsASpecificMenuSet() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The corresponding menu is displayed including name of menu and all recipes")
    public void theCorrespondingMenuIsDisplayedIncludingNameOfMenuAndAllRecipes() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("The employee edits a specific menu set")
    public void theEmployeeEditsASpecificMenuSetIEAddAndRemove() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The menu set is updated with the new information")
    public void theMenuSetIsUpdatedWithTheNewInformation() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
