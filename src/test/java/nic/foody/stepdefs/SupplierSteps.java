package nic.foody.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Map;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nic.foody.io.LoadData;
import nic.foody.model.Supplier;
import nic.foody.parsing.SupplierHandler;

public class SupplierSteps {
    private Supplier s = null;


    @Given("Supplier Funky Kumquat has no URL")
    public void supplierFunkyKumquatHasNoURL() {
        /* 
         * Should really verify here that the test data 
         * meets the mising URL criterion.  Just pretend
         * for now...*/
        assertNull("Haven't loaded anything yet", s);
    }

    @When("The Funky Kumquat is loaded")
    public void theFunkyKumquatIsLoaded() {
         // Is it cheating to know this supplier is in this sample data file?
         String fName = "resources/data/Suppliers.xml";   
         LoadData.loadSuppliers(fName);
 
         Map<String, Supplier>suppsLoaded = SupplierHandler.getSuppliers();
 
         for (Supplier sup : suppsLoaded.values()) {
             if(sup.name().equals("Funky Kumquat")) {
                 s = sup;
                 break;
             }
         }
         assertNotNull("Funky Kumquat should be loaded now", s);
    }

    @Then("a default URL is assigned")
    public void aDefaultURLIsAssigned() {
        assertEquals("default URL", Supplier.UNKNOWN_URL, s.url());
    }
}