package foodstart.model.stock;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;

/**
 * Unit tests for the Ingredient class. Data validation is not being included here for the time being as it
 * is being handled in the manager classes. The getter/setter methods do not have dedicated tests as they 
 * contain no logic and should be covered by unit tests of the Manager classes. 
 * @author Alan Wang
 */
public class IngredientTest {
    
    Ingredient ingrNorm, ingrSilly, ingrEmpty, ingrNull;
    static HashMap<DietaryRequirement, Boolean> dummyMap, testMap;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testMap = new HashMap<DietaryRequirement, Boolean>();
        testMap.put(DietaryRequirement.GLUTEN_FREE, false);
        testMap.put(DietaryRequirement.LACTOSE_INTOLERANT, false);
        testMap.put(DietaryRequirement.NUT_ALLERGY, true);
        testMap.put(DietaryRequirement.VEGAN, false);
        testMap.put(DietaryRequirement.VEGETARIAN, true);
        dummyMap = new HashMap<DietaryRequirement, Boolean>();
    }
   
    @Before
    public void setUp() throws Exception {
        ingrNorm = new Ingredient(Unit.UNITS, "Buns", 12, testMap, 12, 7);
    }

    @After
    public void tearDown() throws Exception {
        ingrNorm = null;
        ingrSilly = null;
        ingrEmpty = null;
        ingrNull = null;
    }
    

    @Test
    public void testClone() {
        Ingredient copy = ingrNorm.clone();
        Assert.assertEquals(ingrNorm.getName(), copy.getName());
        Assert.assertEquals(ingrNorm.getId(), copy.getId());
        Assert.assertEquals(ingrNorm.getUnit(), copy.getUnit());
        Assert.assertEquals(ingrNorm.getKitchenStock(), copy.getKitchenStock());
        Assert.assertEquals(ingrNorm.getTruckStock(), copy.getTruckStock());
        Assert.assertNotSame(ingrNorm.getSafeFor(), copy.getSafeFor());
        for (DietaryRequirement d: DietaryRequirement.values()) {
            Assert.assertEquals(ingrNorm.getSafeFor().get(d), copy.getSafeFor().get(d));
        }
    }
    
    @Test
    public void testEquals() {
        Assert.assertTrue(ingrNorm.equals(ingrNorm.clone()));
    }

    @Test
    public void testIsSafeFor() {
        Assert.assertEquals(false, ingrNorm.isSafeFor((DietaryRequirement.GLUTEN_FREE)));
        Assert.assertEquals(false, ingrNorm.isSafeFor(DietaryRequirement.LACTOSE_INTOLERANT));
        Assert.assertEquals(true, ingrNorm.isSafeFor(DietaryRequirement.NUT_ALLERGY));
        Assert.assertEquals(false, ingrNorm.isSafeFor(DietaryRequirement.VEGAN));
        Assert.assertEquals(true, ingrNorm.isSafeFor(DietaryRequirement.VEGETARIAN));
    }

}
