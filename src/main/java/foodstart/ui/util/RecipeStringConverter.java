package foodstart.ui.util;

import foodstart.manager.Managers;
import foodstart.model.menu.PermanentRecipe;
import javafx.util.StringConverter;

/**
 * Converts a recipe to a string
 */
public class RecipeStringConverter extends StringConverter<PermanentRecipe> {
	@Override
	public String toString(PermanentRecipe recipe) {
		if (recipe == null) {
			return "Please select a recipe";
		} else {
			return recipe.getDisplayName();
		}
	}

	@Override
	public PermanentRecipe fromString(String s) {
		return Managers.getRecipeManager().getRecipeByDisplayName(s);
	}
}
