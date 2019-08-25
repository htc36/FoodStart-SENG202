package foodstart.model.menu;

import foodstart.model.stock.Ingredient;
import java.util.Map;

/**
 * Models an on the fly recipe, a recipe used to model a customised recipe
 */
public class OnTheFlyRecipe extends Recipe
{

	/**
	 * The permanent recipe that the recipe is based on
	 */
	private PermanentRecipe basedOn;

	/**
	 * Constructs an instance of an on the fly recipe
	 * @param basis the recipe that the recipe is based on
	 * @param ingredients the ingredients that the recipe contains
	 * @param price the price of the recipe
	 */
	public OnTheFlyRecipe(PermanentRecipe basis, Map<Ingredient, Integer> ingredients, float price){
		super(price, ingredients);
		this.basedOn = basis;
	}

}

