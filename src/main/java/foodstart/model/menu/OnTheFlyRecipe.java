package foodstart.model.menu;

import foodstart.model.stock.Ingredient;

import java.util.HashMap;
import java.util.Map;

/**
 * Models an on the fly recipe, a recipe used to model a customised recipe
 */
public class OnTheFlyRecipe extends Recipe {

	/**
	 * The permanent recipe that the recipe is based on
	 */
	private PermanentRecipe basedOn;

	/**
	 * Constructs an instance of an on the fly recipe
	 *
	 * @param basis       the recipe that the recipe is based on
	 * @param ingredients the ingredients that the recipe contains
	 * @param price       the price of the recipe
	 */
	public OnTheFlyRecipe(PermanentRecipe basis, Map<Ingredient, Integer> ingredients, float price) {
		super(price, ingredients);
		this.basedOn = basis;
	}

	/**
	 * Returns the permanent recipe that this recipe is based on
	 *
	 * @return the permanent recipe that this recipe is based on
	 */
	public PermanentRecipe getBasedOn() {
		return basedOn;
	}

	/**
	 * Sets the permanent recipe that this recipe is based on
	 *
	 * @param basedOn the permanent recipe that this recipe is based on
	 */
	public void setBasedOn(PermanentRecipe basedOn) {
		this.basedOn = basedOn;
	}

	/**
	 * Returns the display name of the recipe this recipe is based on with the ending " (modified)"
	 *
	 * @return the display name of the recipe
	 */
	@Override
	public String getDisplayName() {
		return this.basedOn.getDisplayName().concat(" (modified)");
	}

	/**
	 * Returns the id of the recipe that this is based on
	 * @return the id of the recipe that this is based on
	 */
	@Override
	public int getId() {
		return this.basedOn.getId();
	}
	
	/**
	 * Returns a deep copy of this on-the-fly recipe.
	 * @return a deep copy of this object.
	 */
	public OnTheFlyRecipe clone() {
        Map<Ingredient, Integer> newIngredients = new HashMap<Ingredient, Integer>(getIngredients());
        return new OnTheFlyRecipe(basedOn, newIngredients, getPrice());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        OnTheFlyRecipe other = (OnTheFlyRecipe) obj;
        if (basedOn == null) {
            if (other.basedOn != null)
                return false;
        } else if (!basedOn.equals(other.basedOn))
            return false;
        return true;
    }
	
	
	
}

