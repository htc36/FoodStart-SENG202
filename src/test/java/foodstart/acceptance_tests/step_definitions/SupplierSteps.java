package foodstart.acceptance_tests.step_definitions;

import foodstart.manager.stock.SupplierManager;
import foodstart.model.PhoneType;
import foodstart.model.stock.Supplier;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class SupplierSteps {
    private SupplierManager supplierManager = new SupplierManager();
    private HashMap<Integer, Supplier> existingSupplierSet;

    private Integer code;
    private String name;
    private String phoneNumber;
    private PhoneType phoneType;
    private String email;
    private String website;
    private String address;

    /**
     * Creates a set of suppliers that can be added to the supplier list before testing if needed
     */
    private void createSupplierSet() {
        HashMap<Integer, Supplier> supplierSet = new HashMap<Integer, Supplier>();
        Supplier CountUp = new Supplier(123, "Count Up", "0123456789", PhoneType.WORK,
                "onetwothree@countup.com", "www.countup.com", "1234 Five Road");
        supplierSet.put(0, CountUp);

        Supplier UnderValue = new Supplier(1, "Under Value", "01111111", PhoneType.WORK,
                "super@undervalue.com", "www.undervalue.com", "11 Costly Road");
        supplierSet.put(1, UnderValue);

        existingSupplierSet = supplierSet;
    }

    @Given("There are {int} suppliers in the suppliers list")
    public void thereAreSuppliersInTheSuppliersList(Integer numberOfSuppliers) {
        createSupplierSet();
        for (int i = 0; i < numberOfSuppliers; i++) {
            supplierManager.addSupplier(existingSupplierSet.get(i));
        }
    }

    @Given("Supplier with code {int} does not exist in the supplier list")
    public void supplierWithCodeDoesNotExistInTheSupplierList(Integer supplierCode) {
        assertNull(supplierManager.getSupplier(supplierCode));
    }

    @Given("Its code is {int}")
    public void itsCodeIs(Integer supplierCode) {
        code = supplierCode;
    }

    @Given("Its name is {string}")
    public void itsNameIs(String supplierName) {
        name = supplierName;
    }

    @Given("Its phone number is {string}")
    public void itsPhoneNumberIs(String supplierPhoneNumber) {
        phoneNumber = supplierPhoneNumber;
    }

    @Given("Its phone type is {string}")
    public void itsPhoneTypeIs(String supplierPhoneType) {
        phoneType = PhoneType.matchType(supplierPhoneType);
    }

    @Given("Its email is {string}")
    public void itsEmailIs(String supplierEmail) {
        email = supplierEmail;
    }

    @Given("Its website is {string}")
    public void itsWebsiteIs(String supplierWebsite) {
        website = supplierWebsite;
    }

    @Given("Its address is {string}")
    public void itsAddressIs(String supplierAddress) {
        address = supplierAddress;
    }

    @When("Supplier with code {int} is manually added to the supplier list")
    public void supplierIsManuallyAddedToTheSupplierList(Integer supplierCode) {
        supplierManager.addSupplier(supplierCode, name, phoneNumber, phoneType, email, website, address);
    }

    @Then("It will be stored with code {int} in the supplier list")
    public void itWillBeStoredUnderInTheSupplierList(Integer supplierCode) {
        assertNotNull(supplierManager.getSupplier(supplierCode));
    }

    @Then("Its name will be {string}")
    public void itsNameWillBe(String supplierName) {
        assertEquals(supplierName, supplierManager.getSupplier(code).getSupplierName());
    }

    @Then("Its phone number will be {string}")
    public void itsPhoneNumberWillBe(String supplierPhoneNumber) {
        assertEquals(supplierPhoneNumber, supplierManager.getSupplier(code).getPhoneNumber());
    }

    @Then("Its phone type will be {string}")
    public void itsPhoneTypeWillBe(String supplierPhoneType) {
        assertEquals(PhoneType.matchType(supplierPhoneType), supplierManager.getSupplier(code).getPhoneType());
    }

    @Then("Its email will be {string}")
    public void itsEmailWillBe(String supplierEmail) {
        assertEquals(supplierEmail, supplierManager.getSupplier(code).getEmail());
    }

    @Then("Its website will be {string}")
    public void itsWebsiteWillBe(String supplierWebsite) {
        assertEquals(supplierWebsite, supplierManager.getSupplier(code).getUrl());
    }

    @Then("Its address will be {string}")
    public void itsAddressWillBe(String supplierAddress) {
        assertEquals(supplierAddress, supplierManager.getSupplier(code).getAddress());
    }

    @Given("Supplier with code {int} exists in the supplier list")
    public void supplierWithCodeExistsInTheSupplierList(Integer supplierCode) {
        assertNotNull(supplierManager.getSupplier(supplierCode));
    }


    @When("Supplier with code {int} is manually removed from the supplier list")
    public void supplierWithCodeIsManuallyRemovedFromTheSupplierList(Integer supplierCode) {
        supplierManager.removeSupplier(supplierCode);
    }

    @Then("Supplier with code {int} will not exist in the supplier list")
    public void supplierWithCodeWillNotExistInTheSupplierList(Integer supplierCode) {
        assertNull(supplierManager.getSupplier(supplierCode));
    }

    @When("The name of the supplier with code {int} is changed to {string}")
    public void theNameOfTheSupplierWithCodeIsChangedTo(Integer supplierCode, String newSupplierName) {
        name = newSupplierName;
        Supplier supplier = supplierManager.getSupplier(supplierCode);
        Supplier editedSupplier = new Supplier(supplierCode, newSupplierName, supplier.getPhoneNumber(), supplier.getPhoneType(),
                supplier.getEmail(), supplier.getUrl(), supplier.getAddress());
        supplierManager.removeSupplier(supplierCode);
        supplierManager.addSupplier(editedSupplier);
    }

    @When("The supplier with code {int} is edited to name {string}, phone {string}, type {string}, email {string}, website {string}, address {string}")
    public void theSupplierWithCodeIsEditedToNamePhoneTypeEmailWebsiteAddress(Integer supplierCode, String newName, String newPhone,
                                                                       String newType, String newEmail, String newWebsite, String newAddress) {
        name = newName;
        phoneNumber = newPhone;
        phoneType = PhoneType.matchType(newType);
        email = newEmail;
        website = newWebsite;
        address = newAddress;
        Supplier supplier = supplierManager.getSupplier(supplierCode);
        Supplier editedSupplier = new Supplier(supplierCode, newName, newPhone, PhoneType.matchType(newType), newEmail, newWebsite,
                newAddress);
        supplierManager.removeSupplier(supplierCode);
        supplierManager.addSupplier(editedSupplier);
    }

    @Then("Its code will be {int}")
    public void itsCodeWillBe(Integer supplierCode) {
        assertEquals(supplierCode, code);
    }

}
