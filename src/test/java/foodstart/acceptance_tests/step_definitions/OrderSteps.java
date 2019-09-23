package foodstart.acceptance_tests.step_definitions;

import foodstart.manager.menu.RecipeManager;
import foodstart.manager.order.OrderManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.PaymentMethod;
import foodstart.model.Unit;
import foodstart.model.menu.OnTheFlyRecipe;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;
import foodstart.model.stock.Ingredient;
import gherkin.ast.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class OrderSteps {

    private float recipeCost;
    private Recipe recipe;
    private PermanentRecipe basedPermanentRecipe;
    private PermanentRecipe permanentRecipe;
    private OnTheFlyRecipe onTheFlyRecipe;
    private Ingredient ingredient1;
    private Ingredient ingredient2;
    private Map<Ingredient, Integer> recipeIngredients;
    private OrderManager orderManager;
    private Order order;
    private RecipeManager recipeManager;


    @Before
    public void setUp() {
        Map<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
        this.recipeIngredients = ingredients;

        Map<DietaryRequirement, Boolean> safeForIngredient1 = new HashMap<DietaryRequirement, Boolean>();
        safeForIngredient1.put(DietaryRequirement.GLUTEN_FREE, true);
        safeForIngredient1.put(DietaryRequirement.VEGETARIAN, true);

        Map<DietaryRequirement, Boolean> safeForIngredient2 = new HashMap<DietaryRequirement, Boolean>();
        safeForIngredient1.put(DietaryRequirement.GLUTEN_FREE, true);
        safeForIngredient1.put(DietaryRequirement.VEGETARIAN, true);
        safeForIngredient1.put(DietaryRequirement.NUT_ALLERGY, true);
        safeForIngredient1.put(DietaryRequirement.VEGAN, true);
        safeForIngredient1.put(DietaryRequirement.LACTOSE_INTOLERANT, true);

        Ingredient ingredient1 = new Ingredient(Unit.GRAMS, "Mayo", 1, safeForIngredient1, 150, 80);
        Ingredient ingredient2 = new Ingredient(Unit.GRAMS, "Peanut Butter", 2, safeForIngredient2, 120, 45);
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        recipeIngredients.put(this.ingredient1, 2);
        recipeIngredients.put(this.ingredient2, 4);

        this.recipeManager.addRecipe(1, "Recipe Base", "Recipe Instructions",12.5f, recipeIngredients);

        OrderManager order = new OrderManager();
        this.orderManager = order;
    }


    @Given("A {string} costs ${float}, which is a {string}")
    public void aCosts$WhichIsA(String recipeName, float cost, String recipeType) {
        this.recipeCost = cost;
        switch (recipeType) {
            case "permanent recipe":
                recipeManager.addRecipe(2, recipeName, "Recipe Instructions", recipeCost, recipeIngredients);
                break;
            case "on the fly recipe":
                recipeManager.otfManager.addRecipe(recipeManager.getRecipeByDisplayName(recipeName).getId(), recipeIngredients, recipeCost);
                break;
            default:
                throw new cucumber.api.PendingException();
        }
    }

    @When("The customer {string} orders {int} {string} and pays by {string}")
    public void theCustomerOrders(String customerName, int quantity, String recipeName, String paymentMethod) {
        PaymentMethod payment;
        order.addItem(recipeManager.getRecipeByDisplayName(recipeName), quantity);
        switch (paymentMethod) {
            case "cash":
                payment = PaymentMethod.CASH;
                break;
            case "eftpos":
                payment = PaymentMethod.EFTPOS;
                break;
            default:
                throw new cucumber.api.PendingException();
        }
        orderManager.addOrder(1, order.getItems(), customerName, LocalDateTime.now(), payment);
    }





    @Then("The customer will be charged ${double} total")
    public void theCustomerWillBeCharged$Total(Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }



}
