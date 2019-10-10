package foodstart.model.stock;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import foodstart.model.DietaryRequirement;
import foodstart.model.PhoneType;
import foodstart.model.Unit;

/**
 * Unit tests for the Ingredient class. Data validation is not being included here for the time being as it
 * is being handled in the manager classes. The getter/setter methods do not have dedicated tests as they 
 * contain no logic and should be covered by unit tests of the Manager classes. 
 * @author Alan Wang
 */
public class IngredientTest {
    
    Ingredient ingrNorm, ingrEmpty;
    HashMap<DietaryRequirement, Boolean> dummyMap, testMap;
   
    @Before
    public void setUp() throws Exception {
        testMap = buildTestMap();
        dummyMap = new HashMap<DietaryRequirement, Boolean>();
        ingrNorm = new Ingredient(Unit.UNITS, "Buns", 12, testMap, 12, 7);
        ingrEmpty = new Ingredient(null, null, 0, null, 0, 0);
    }

    @After
    public void tearDown() throws Exception {
        ingrNorm = null;
        ingrEmpty = null;
    }
    
    private HashMap<DietaryRequirement, Boolean> buildTestMap() {
        HashMap<DietaryRequirement, Boolean> outputMap = new HashMap<DietaryRequirement, Boolean>();
        outputMap.put(DietaryRequirement.GLUTEN_FREE, false);
        outputMap.put(DietaryRequirement.LACTOSE_INTOLERANT, false);
        outputMap.put(DietaryRequirement.NUT_ALLERGY, true);
        outputMap.put(DietaryRequirement.VEGAN, false);
        outputMap.put(DietaryRequirement.VEGETARIAN, true);
        return outputMap;
    }
    

    @Test
    public void testClone() {
        Ingredient copy = ingrNorm.clone();
        Assert.assertTrue(ingrNorm != copy);
        Assert.assertEquals(copy, ingrNorm);
        ingrNorm.getSafeFor().remove(DietaryRequirement.VEGAN);
        Assert.assertNotEquals(copy, ingrNorm);
    }
    

    @Test
    public void testIsSafeFor() {
        Assert.assertEquals(false, ingrNorm.isSafeFor((DietaryRequirement.GLUTEN_FREE)));
        Assert.assertEquals(false, ingrNorm.isSafeFor(DietaryRequirement.LACTOSE_INTOLERANT));
        Assert.assertEquals(true, ingrNorm.isSafeFor(DietaryRequirement.NUT_ALLERGY));
        Assert.assertEquals(false, ingrNorm.isSafeFor(DietaryRequirement.VEGAN));
        Assert.assertEquals(true, ingrNorm.isSafeFor(DietaryRequirement.VEGETARIAN));
    }
    
    
    @Test 
    public void testEqualsIfSame() {
        Object test2 = ingrNorm;
        assertSame(ingrNorm, test2);
        assertEquals(ingrNorm, test2);
    }
    
    @Test 
    public void testNotEqualsIfNull() {
        assertNotEquals(ingrNorm, null);
    }
    
    @Test 
    public void testNotEqualsIfDifferentClass() {
        Object notIngredient = new String("not an ingredient");
        assertNotEquals(ingrNorm, notIngredient);
    }
    
    @Test 
    public void testNotEqualsIfDifferentID() {
        Ingredient other = new Ingredient(Unit.UNITS, "Buns", 122, testMap, 12, 7);
        assertNotEquals(ingrNorm, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentKitchenStock() {
        Ingredient other = new Ingredient(Unit.UNITS, "Buns", 12, testMap, 1, 7);
        assertNotEquals(ingrNorm, other);
    }
    
    @Test 
    public void testNotEqualsIfOnlySelfNullName() {
        Ingredient other = new Ingredient(null, "", 0, null, 0, 0);
        assertNotEquals(ingrEmpty, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentName() {
        Ingredient other = new Ingredient(Unit.UNITS, "Bns", 12, testMap, 12, 7);
        assertNotEquals(ingrNorm, other);
    }
    
    @Test 
    public void testNotEqualsIfOnlySelfNullSafeFor() {
        Ingredient other = new Ingredient(null, null, 0, dummyMap, 0, 0);
        assertNotEquals(ingrEmpty, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentSafeFor() {
        Ingredient other = new Ingredient(Unit.UNITS, "Buns", 12, dummyMap, 12, 7);
        assertNotEquals(ingrNorm, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentTruckStock() {
        Ingredient other = new Ingredient(Unit.UNITS, "Buns", 12, testMap, 12, 70);
        assertNotEquals(ingrNorm, other);
    }

    @Test 
    public void testNotEqualsIfDifferentUnit() {
        Ingredient other = new Ingredient(Unit.GRAMS, "Buns", 12, testMap, 12, 7);
        assertNotEquals(ingrNorm, other);
    }
    
    @Test 
    public void testEqualsIfAllFieldsNull() {
        Ingredient other = new Ingredient(null, null, 0, null, 0, 0);
        assertEquals(ingrEmpty, other);
    }
    
    @Test 
    public void testEqualsIfAllFieldsMatch() {
        Ingredient other = new Ingredient(Unit.UNITS, "Buns", 12, testMap, 12, 7);
        assertEquals(ingrNorm, other);
    }

}
