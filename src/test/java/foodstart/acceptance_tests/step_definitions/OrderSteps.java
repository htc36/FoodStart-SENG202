package foodstart.acceptance_tests.step_definitions;

import foodstart.manager.menu.RecipeManager;
import foodstart.manager.order.OrderManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.PaymentMethod;
import foodstart.model.Unit;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;
import foodstart.model.stock.Ingredient;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class OrderSteps {

    private float recipeCost;
    private Ingredient ingredient1;
    private Ingredient ingredient2;
    private Map<Ingredient, Integer> recipeIngredients;
    private OrderManager orderManager;
    private Order order;
    private Map<Recipe, Integer> orderItems;
    private int orderId;
    private RecipeManager recipeManager;
    private boolean booleanReq;


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
        orderItems.put(recipeManager.getRecipeByDisplayName(recipeName), quantity);
        order = new Order(orderId, orderItems, customerName, LocalDateTime.now(), payment);
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
    }

    @When("The customer {string} orders {int} {string} and {int} {string} and pays by {string}")
    public void theCustomerOrdersItemsAndAndPaysBy(String customerName, int recipe1Quantity, String recipe1Name, int recipe2Quantity, String recipe2Name, String paymentMethod) {
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
        orderItems.put(recipeManager.getRecipeByDisplayName(recipe1Name), recipe1Quantity);
        orderItems.put(recipeManager.getRecipeByDisplayName(recipe2Name), recipe2Quantity);
        order = new Order(orderId, orderItems, customerName, LocalDateTime.now(), payment);
    }



    @And("The current order has {int} {string} and {int} {string} with a current total of ${float}")
    public void theCurrentOrderHasAnd(int recipe1Quantity, String recipe1Name, int recipe2Quantity, String recipe2Name, float currentTotal) {
        orderId = 1;
        order = new Order(orderId, orderItems, "Sam", LocalDateTime.now(), PaymentMethod.EFTPOS);
        order.addItem(recipeManager.getRecipeByDisplayName(recipe1Name), recipe1Quantity);
        order.addItem(recipeManager.getRecipeByDisplayName(recipe2Name), recipe2Quantity);
        assertTrue(currentTotal == order.getTotalCost());
    }

    @When("{int} {string} is removed from the order")
    public void isRemovedFromTheOrder(Integer quantity, String recipeName) {
        order.removeItem(recipeManager.getRecipeByDisplayName(recipeName));

    }


    @Then("Only {int} {string} appears in the order and does not contain {string}")
    public void onlyAppearsInTheOrderAndDoesNotContain(int quantity, String recipeName, String recipeRemoved) {
        assertFalse(order.getItems().containsKey(recipeRemoved));
    }

    @Then("The customer will be charged ${float} total")
    public void theCustomerWillBeCharged$Total(float totalCost) {
        System.out.println(totalCost);
        System.out.println(order.getTotalCost());
        assertTrue(totalCost == order.getTotalCost());
    }

    @Given("A customer wants to know if the {string} is {string}")
    public void aCustomerWantsToKnowIfTheIs(String recipeName, String dietaryReq) {
        setUp();
        Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
        switch (dietaryReq) {
            case "gluten-free":
                safeFor.put(DietaryRequirement.GLUTEN_FREE, true);
                break;
            case "vegan":
                safeFor.put(DietaryRequirement.VEGAN, true);
                break;
            case "vegetarian":
                safeFor.put(DietaryRequirement.VEGETARIAN, true);
                break;
            case "containing dairy":
                safeFor.put(DietaryRequirement.LACTOSE_INTOLERANT, true);
                break;
            case "containing nuts":
                safeFor.put(DietaryRequirement.NUT_ALLERGY, true);
                break;

            default:
                throw new cucumber.api.PendingException();
        }

        Ingredient ingredient = new Ingredient(Unit.GRAMS, "ingredient", 1, safeFor, 150, 80);
        Map<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
        ingredients.put(ingredient, 2);
        recipeManager.addRecipe(1, recipeName, "Recipe Instructions",12.5f, ingredients);
    }

    @When("The employee checks the if {string} is {string}")
    public void theEmployeeChecksTheIfIs(String recipeName, String dietaryReq) {
        switch (dietaryReq) {
            case ("gluten-free"):
                booleanReq = recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.GLUTEN_FREE);
                break;
            case ("vegan"):
                booleanReq = recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.VEGAN);
                break;
            case ("vegetarian"):
                booleanReq = recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.VEGETARIAN);
                break;
            case ("containing dairy"):
                booleanReq = recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.LACTOSE_INTOLERANT);
                break;
            case ("containing nuts"):
                booleanReq = recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.NUT_ALLERGY);
                break;
            default:
                throw new cucumber.api.PendingException();
        }
    }

    @Then("The {string} should be {string}")
    public void theShouldBe(String recipeName, String dietaryReq) {
        switch (dietaryReq) {
            case ("gluten-free"):
                assertTrue(recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.GLUTEN_FREE));
                break;
            case ("vegan"):
                assertTrue(recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.VEGAN));
                break;
            case ("vegetarian"):
                assertTrue(recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.VEGETARIAN));
                break;
            case ("containing dairy"):
                assertTrue(recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.LACTOSE_INTOLERANT));
                break;
            case ("containing nuts"):
                assertTrue(recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.NUT_ALLERGY));
                break;
            default:
                throw new cucumber.api.PendingException();
        }
    }




    @Given("A customer wants to know if the {string} is {string}, but it is not")
    public void aCustomerWantsToKnowIfTheIsButItIsNot(String recipeName, String dietaryReq) {
        setUp();
        Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
        Ingredient ingredient = new Ingredient(Unit.GRAMS, "ingredient", 1, safeFor, 150, 80);
        Map<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
        ingredients.put(ingredient, 2);
        recipeManager.addRecipe(1, recipeName, "Recipe Instructions",12.5f, ingredients);
    }

    @Then("The {string} should not be {string}")
    public void theShouldNotBe(String recipeName, String dietaryReq) {
        switch (dietaryReq) {
            case ("gluten-free"):
                assertFalse(recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.GLUTEN_FREE));
                break;
            case ("vegan"):
                assertFalse(recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.VEGAN));
                break;
            case ("vegetarian"):
                assertFalse(recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.VEGETARIAN));
                break;
            case ("containing dairy"):
                assertFalse(recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.LACTOSE_INTOLERANT));
                break;
            case ("containing nuts"):
                assertFalse(recipeManager.getRecipeByDisplayName(recipeName).isSafeFor(DietaryRequirement.NUT_ALLERGY));
                break;
            default:
                throw new cucumber.api.PendingException();
        }
    }

}
