package foodstart.acceptance_tests.step_definitions;

import foodstart.model.order.Order;
import gherkin.ast.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class OrderSteps {

    private float hamburgerCost;
    private float chipsCost;


    @Given("A {string} costs ${float}")
    public void aCosts$(String recipeName, float cost) {
        switch (recipeName) {
            case "hamburger":
                this.hamburgerCost = cost;
                break;
            case "chips":
                chipsCost = cost;
                break;
            default:
                throw new cucumber.api.PendingException();
        }
    }

    @When("The customer orders {int} {string}")
    public void theCustomerOrders(Integer quantity, String recipeName) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The customer will be charged ${double} total")
    public void theCustomerWillBeCharged$Total(Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("The current order has {int} {string}")
    public void theCurrentOrderHas(Integer int1, String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("The order costs ${double} in total")
    public void theOrderCosts$InTotal(Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("{int} {string} is removed from the order")
    public void isRemovedFromTheOrder(Integer int1, String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("Only {int} {string} appears in the order")
    public void onlyAppearsInTheOrder(Integer int1, String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("A {string} contains {string}")
    public void aContains(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("The customer wants to remove {string} from the {string}")
    public void theCustomerWantsToRemoveFromThe(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The {string} has no {string}")
    public void theHasNo(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

//    @When("The customer will be charged ${double} total")
//    public void theCustomerWillBeCharged$Total(Double double1) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
//    }

    @When("The flavour is edited to tropical")
    public void theFlavourIsEditedToTropical() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The system checks that it exists and replaces orange with tropical")
    public void theSystemChecksThatItExistsAndReplacesOrangeWithTropical() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("A customer with celiac disease and would like to know what items are gluten-free")
    public void aCustomerWithCeliacDiseaseAndWouldLikeToKnowWhatItemsAreGlutenFree() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("The employee selects gluten-free options")
    public void theEmployeeSelectsGlutenFreeOptions() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The items available are filtered to only show items that are gluten-free")
    public void theItemsAvailableAreFilteredToOnlyShowItemsThatAreGlutenFree() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("A customer wants a hamburger but is sold out")
    public void aCustomerWantsAHamburgerButIsSoldOut() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("The employee is about to order the item")
    public void theEmployeeIsAboutToOrderTheItem() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The employee will not be able to place hamburger to the order")
    public void theEmployeeWillNotBeAbleToPlaceHamburgerToTheOrder() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("Customer {string} ordered {int} {string}")
    public void customerOrdered(String string, Integer int1, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("The employee confirms the order")
    public void theEmployeeConfirmsTheOrder() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("{string} will be charged ${double} total")
    public void willBeCharged$Total(String string, Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The order is recorded in the sales history")
    public void theOrderIsRecordedInTheSalesHistory() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("The total order costs ${double}")
    public void theTotalOrderCosts$(Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("The customer pays ${double}")
    public void theCustomerPays$(Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("The payment is finalised")
    public void thePaymentIsFinalised() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The customer receives ${double} change")
    public void theCustomerReceives$Change(Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("The payment is short by ${double}")
    public void thePaymentIsShortBy$(Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("The manager looks for {string} in the sales log")
    public void theManagerLooksForInTheSalesLog(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("All details are displayed i.e time, items sold, amounts and price")
    public void allDetailsAreDisplayedIETimeItemsSoldAmountsAndPrice() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }


}
