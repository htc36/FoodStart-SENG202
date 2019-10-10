package foodstart.model.menu;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;

public class RecipeTest {
    
    Recipe testRecipe, nullRecipe;
    Map<Ingredient, Integer> dummyMap, testMap;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        dummyMap = new HashMap<Ingredient, Integer>();
        testMap = new HashMap<Ingredient, Integer>();
        testMap.put(new Ingredient(null, null, 0, null, 0, 0), 1);
        testRecipe = new PermanentRecipe(10, "hello", "lovely spam", (float) 0.123, testMap);
        nullRecipe = new PermanentRecipe(0, null, null, 0, null);
    }

    @Test 
    public void testEqualsIfSame() {
        Object test = testRecipe;
        assertSame(testRecipe, test);
        assertEquals(testRecipe, test);
    }
    
    @Test 
    public void testNotEqualsIfNull() {
        assertNotEquals(nullRecipe, null);
    }
    
    @Test 
    public void testNotEqualsIfDifferentClass() {
        Object notRecipe = new String("not an ingredient");
        assertNotEquals(testRecipe, notRecipe);
    }
    
    @Test 
    public void testNotEqualsIfOnlySelfNullIngredients() {
        Recipe other = new PermanentRecipe(0, null, null, 0, dummyMap);
        assertNotEquals(nullRecipe, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentIngredients() {
        Recipe other = new PermanentRecipe(10, "hello", "lovely spam", (float) 0.123, dummyMap);
        assertNotEquals(testRecipe, other);
    }
    
    @Test 
    public void testNotEqualsIfDifferentPrice() {
        Recipe other = new PermanentRecipe(10, "hello", "lovely spam", (float) 0.13, testMap);
        assertNotEquals(testRecipe, other);
    }
    
    @Test 
    public void testEqualsIfAllFieldsNull() {
        Recipe other = new PermanentRecipe(0, null, null, 0, null);
        assertEquals(nullRecipe, other);
    }
    
    @Test 
    public void testEqualsIfAllFieldsMatch() {
        Recipe other = new PermanentRecipe(10, "hello", "lovely spam", (float) 0.123, testMap);
        assertEquals(testRecipe, other);
    }

}
