package foodstart.managers.menu;

import foodstart.manager.menu.RecipeManager;
import foodstart.model.Unit;
import foodstart.model.menu.OnTheFlyRecipe;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;
import org.junit.*;

import java.util.HashMap;
import java.util.Map;


public class RecipeManagerTest {

	private RecipeManager manager;
	private PermanentRecipe permanentRecipe;
	private Recipe permanentRecipeWrapped;
	private OnTheFlyRecipe onTheFlyRecipe;
	private Recipe onTheFlyRecipeWrapped;
	private Map<Ingredient, Integer> ingredientsPerm;
	private Map<Ingredient, Integer> ingredientsOTF;

	@Before
	public void setUp() {
		manager = new RecipeManager();
		Ingredient ingredient1 = new Ingredient(Unit.UNITS, "TestIngredient1", 0, null, 5, 10);
		Ingredient ingredient2 = new Ingredient(Unit.MILLILITRES, "TestIngredient2", 1, null, 4, 8);
		Ingredient ingredient3 = new Ingredient(Unit.GRAMS, "TestIngredient3", 2, null, 3, 6);
		ingredientsPerm = new HashMap<Ingredient, Integer>();
		ingredientsOTF = new HashMap<Ingredient, Integer>();
		ingredientsPerm.put(ingredient1, 10);
		ingredientsPerm.put(ingredient2, 5);
		ingredientsOTF.put(ingredient2, 100);
		ingredientsOTF.put(ingredient3, 5);
		permanentRecipe = new PermanentRecipe(0, "TestPermRecipe", "TestInstruction", 5, ingredientsPerm);
		permanentRecipeWrapped = permanentRecipe;
		onTheFlyRecipe = new OnTheFlyRecipe(permanentRecipe, ingredientsOTF, 500);
		onTheFlyRecipeWrapped = onTheFlyRecipe;
	}
}
