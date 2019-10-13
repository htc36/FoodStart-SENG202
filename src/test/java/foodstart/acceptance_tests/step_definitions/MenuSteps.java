package foodstart.acceptance_tests.step_definitions;

import foodstart.manager.menu.MenuItemManager;
import foodstart.manager.menu.RecipeManager;
import foodstart.manager.stock.IngredientManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class MenuSteps {
    private RecipeManager recipeManager;
    private Map<DietaryRequirement, Boolean> safeFor = new HashMap<>();
    private Map<Ingredient, Integer> ingredientSet = new HashMap<>();
    private Map<Ingredient, Integer> testIngredientSet = new HashMap<>();
    private PermanentRecipe recipe;
    private IngredientManager ingredientManager;
    private MenuItemManager menuItemManager;
    private int recipeID;
    private int testPrice = 10;

    @Before
    public void setup() {
        ingredientManager = new IngredientManager();
        recipeManager = new RecipeManager();
        menuItemManager = new MenuItemManager();
        safeFor.put(DietaryRequirement.matchDietaryRequirement("VEGAN"), true);
        ingredientManager.addIngredient(Unit.matchUnit("COUNTS"), "test ingredient", 0, safeFor, 10,10);
        ingredientManager.addIngredient(Unit.matchUnit("Counts"), "sausage", 1, safeFor, 5, 10);
        ingredientManager.addIngredient(Unit.matchUnit("COUNTS"), "bun", 2, safeFor, 7,8);
        ingredientSet.put(ingredientManager.getIngredientByName("sausage"), 2);
        ingredientSet.put(ingredientManager.getIngredientByName("bun"), 1);
        ingredientSet.put(ingredientManager.getIngredientByName("bun"), 1);
        testIngredientSet.put(ingredientManager.getIngredientByName("test ingredient"), 1);
        recipeManager.addRecipe(2, "Small HotDog", "Place in bun", 5, ingredientSet );
        recipeManager.addRecipe(0, "Test Recipe", "Testing purposes", 1, testIngredientSet);
        Set<PermanentRecipe> recipes = new HashSet<>();
        menuItemManager.addMenuItem(1, "HotDog", "Different types of hotdogs", recipes, null);
    }


    @Given("A recipe {string} exists")
    public void a_recipe_exists(String recipeName) {
        recipe = recipeManager.getRecipeByDisplayName(recipeName);
        assertNotNull(recipe);
    }

    @When("The price is changed to {float}")
    public void the_price_is_changed_to(float price) {
        recipe.setPrice(price);
    }

    @Then("The recipe costs {float}")
    public void the_recipe_costs(float price) {
        assertTrue(recipe.getPrice() == price);
    }
    @Given("The ingredient {string} is added with the unit quantity {int}")
    public void an_ingredient_is_added(String ingredient, int amount){
        Map<DietaryRequirement, Boolean> safeForTestIngredient = new HashMap<>();
        ingredientManager.addIngredient(Unit.matchUnit("COUNTS"), ingredient, 8, safeForTestIngredient, 8, 10);
        recipe.addIngredient(ingredientManager.getIngredientByName(ingredient), amount);
    }
    @When("The ingredient {string} is removed")
    public void an_ingredient_is_removed(String ingredient){
        recipe.removeIngredient(ingredientManager.getIngredientByName(ingredient));
    }
    @Then("The ingredients in the recipe {string} are {string} and {string}")
    public void the_ingredient_are(String recipe, String ingredient1, String ingredient2) {
        Map<Ingredient, Integer> ingredients = recipeManager.getRecipeByDisplayName(recipe).getIngredients();
        assertTrue(ingredients.containsKey(ingredientManager.getIngredientByName(ingredient1)));
        assertTrue(ingredients.containsKey(ingredientManager.getIngredientByName(ingredient2)));
    }
    @When("The recipe {string} is changed to {string}")
    public void the_recipe_is_changed_to(String oldName, String newName) {
        recipeManager.getRecipeByDisplayName(oldName).setDisplayName(newName);
    }
    @When("The recipe {string}, instructions are changed to {string}")
    public void the_recipes_instructions_are_changed(String recipeName, String instructions) {
        recipeManager.getRecipeByDisplayName(recipeName).setInstructions(instructions);
    }
    @Then("The recipe {string} has the instructions {string}")
    public void the_recipe_has_the_instructions(String recipeName, String instructions) {
        assertTrue(recipeManager.getRecipeByDisplayName(recipeName).getInstructions().equals(instructions));
    }
    @When("The recipe {string} is removed")
    public void the_recipe_is_removed(String recipeName) {
        recipeManager.removeRecipe(recipeManager.getRecipeByDisplayName(recipeName).getId());
    }
    @Then("The recipe {string} does not exist")
    public void the_recipe_does_not_exist(String recipeName){
        assertFalse(recipeManager.getRecipes().containsValue(recipe));
    }
    @Given("The menu item {string} exists")
    public void a_menu_item_exists(String menuItemName){
        assertNotNull(menuItemManager.getMenuItemByDisplayName(menuItemName));
    }
    @When("The recipe {string} is added to the {string} menu item")
    public void the_recipe_is_added_to_the_menu_item(String recipeName, String menuItemName) {
        int id = menuItemManager.getMenuItemByDisplayName(menuItemName).getId();
        menuItemManager.addRecipeToMenuItem(id, recipeManager.getRecipeByDisplayName(recipeName));
    }
    
    @Given("A recipe {string} does not exist")
    public void aRecipeDoesNotExist(String recipeName) {
        assertNull(recipeManager.getRecipeByDisplayName(recipeName));
    }

    @Given("The recipe has an ID of {int}")
    public void theRecipeHasAnIDOf(Integer recipeID) {
        this.recipeID = recipeID;
    }

    @When("The recipe {string} is manually added")
    public void theRecipeIsManuallyAdded(String recipeName) {
        recipeManager.addRecipe(recipeID, recipeName, "Test instructions", testPrice, testIngredientSet);
    }

    @Then("The recipe {string} exists in the recipes list")
    public void theRecipeExistsInTheRecipesList(String recipeName) {
        assertNotNull(recipeManager.getRecipeByDisplayName(recipeName));
    }

    @Then("The recipe {string} will have an ID of {int}")
    public void theRecipeWillHaveAnIDOf(String recipeName, Integer recipeID) {
        Integer actualID = recipeManager.getRecipeByDisplayName(recipeName).getId();
        assertEquals(recipeID, actualID);
    }
}
