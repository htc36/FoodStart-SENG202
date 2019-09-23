package foodstart.acceptance_tests.step_definitions;

import foodstart.manager.menu.RecipeManager;
import foodstart.manager.order.OrderManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.PaymentMethod;
import foodstart.model.Unit;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class OrderSteps {

    private float recipeCost;
    private Recipe recipe;
    private Ingredient ingredient1;
    private Ingredient ingredient2;
    private Map<Ingredient, Integer> recipeIngredients;
    private OrderManager orderManager;
    private Order order;
    private Map<Recipe, Integer> orderItems;
    private int orderId;
    private RecipeManager recipeManager;


    public void setUp() {
        recipeIngredients = new HashMap<Ingredient, Integer>();

        Map<DietaryRequirement, Boolean> safeForIngredient1 = new HashMap<DietaryRequirement, Boolean>();
        safeForIngredient1.put(DietaryRequirement.GLUTEN_FREE, true);
        safeForIngredient1.put(DietaryRequirement.VEGETARIAN, true);

        Map<DietaryRequirement, Boolean> safeForIngredient2 = new HashMap<DietaryRequirement, Boolean>();
        safeForIngredient1.put(DietaryRequirement.GLUTEN_FREE, true);
        safeForIngredient1.put(DietaryRequirement.VEGETARIAN, true);
        safeForIngredient1.put(DietaryRequirement.NUT_ALLERGY, true);
        safeForIngredient1.put(DietaryRequirement.VEGAN, true);
        safeForIngredient1.put(DietaryRequirement.LACTOSE_INTOLERANT, true);

        ingredient1 = new Ingredient(Unit.GRAMS, "Mayo", 1, safeForIngredient1, 150, 80);
        ingredient2 = new Ingredient(Unit.GRAMS, "Peanut Butter", 2, safeForIngredient2, 120, 45);

        recipeIngredients.put(ingredient1, 2);
        recipeIngredients.put(ingredient2, 4);

        recipeManager= new RecipeManager();
        recipeManager.addRecipe(1, "Recipe Base", "Recipe Instructions",12.5f, recipeIngredients);
        orderManager = new OrderManager();
        orderItems = new HashMap<Recipe, Integer>();

    }


    @Given("A {string} costs ${double}, which is a {string}")
    public void aCosts$WhichIsA(String recipeName, double cost, String recipeType) {
        setUp();
        recipeCost = (float) cost;
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
        orderId = 1;
        order = new Order(orderId, orderItems, customerName, LocalDateTime.now(), payment);
        order.addItem(recipeManager.getRecipeByDisplayName(recipeName), quantity);
        orderManager.addOrder(orderId, order.getItems(), customerName, LocalDateTime.now(), payment);
    }

    @Given("A {string} costs ${double}, which is a {string} and {string} costs ${double}, which is a {string}")
    public void aCosts$WhichIsAAndCosts$WhichIsA(String recipe1Name, double recipe1Cost, String recipe1Type, String recipe2Name, double recipe2Cost, String recipe2Type) {
        setUp();
        switch (recipe1Type) {
            case "permanent recipe":
                recipeManager.addRecipe(2, recipe1Name, "Recipe Instructions", (float) recipe1Cost, recipeIngredients);
                break;
            case "on the fly recipe":
                recipeManager.otfManager.addRecipe(recipeManager.getRecipeByDisplayName(recipe1Name).getId(), recipeIngredients, (float) recipe1Cost);
                break;
            default:
                throw new cucumber.api.PendingException();
        }

        switch (recipe2Type) {
            case "permanent recipe":
                recipeManager.addRecipe(3, recipe2Name, "Recipe Instructions", (float) recipe2Cost, recipeIngredients);
                break;
            case "on the fly recipe":
                recipeManager.otfManager.addRecipe(recipeManager.getRecipeByDisplayName(recipe1Name).getId(), recipeIngredients, (float) recipe2Cost);
                break;
            default:
                throw new cucumber.api.PendingException();
        }

        System.out.println(recipeManager.getRecipes());
    }

    @When("The customer {string} orders {int} {string} and {int} {string} and pays by {string}")
    public void theCustomerOrdersItemsAndAndPaysBy(String customerName, int recipe1Quantity, String recipe1, int recipe2Quantity, String recipe2, String paymentMethod) {
        PaymentMethod payment;
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
        orderId = 1;
        orderItems.put(recipeManager.getRecipeByDisplayName(recipe1), recipe1Quantity);
        orderItems.put(recipeManager.getRecipeByDisplayName(recipe2), recipe2Quantity);
        order = new Order(orderId, orderItems, customerName, LocalDateTime.now(), payment);
        orderManager.addOrder(orderId, order.getItems(), customerName, LocalDateTime.now(), payment);
    }

    @Then("The customer will be charged ${float} total")
    public void theCustomerWillBeCharged$Total(float totalCost) {
        order = orderManager.getOrder(orderId);
        assertTrue(totalCost == order.getTotalCost());
    }




}
