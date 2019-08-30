package foodstart.model.menu;

import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;
import org.junit.Before;
import org.junit.Test;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.Recipe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class MenuItemTest {

    private MenuItem testitem;

    @Before
    public void setUp() throws Exception {
        Map<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
        Ingredient ingredient = new Ingredient(Unit.GRAMS, "TestIngredient",
                0, safeFor, 5, 10);
        Set<Recipe> recipes = new HashSet<Recipe>();
        testitem = new MenuItem(0, "TestItem", "MenuItem test item", recipes);
    }

    @Test
    public void getDatabaseId() {
    }

    @Test
    public void setDatabaseId() {
    }

    @Test
    public void getName() {
    }

    @Test
    public void setName() {
    }

    @Test
    public void getDescription() {
    }

    @Test
    public void setDescription() {
    }

    @Test
    public void getVariants() {
    }

    @Test
    public void setVariants() {
    }
}