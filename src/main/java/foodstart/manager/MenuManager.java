package foodstart.manager;

import java.util.HashSet;
import java.util.Set;

import foodstart.model.menu.Menu;
import foodstart.model.menu.PermanentRecipe;

public class MenuManager {

	/**
	 * The set of all menus modeled in the system
	 */
	private Set<Menu> menus;

	private Set<PermanentRecipe> recipes;

	public MenuManager() {
		this.menus = new HashSet<Menu>();
		this.recipes = new HashSet<PermanentRecipe>();
	}
	
	public void addMenu(Menu menu) {
		this.menus.add(menu);
	}
	
	/**
	 * Gets a recipe by its id, or null if the recipe is not defined
	 * @param id The unique recipe ID
	 * @return The recipe that the UID refers to, or null
	 */
	public PermanentRecipe getRecipeById(int id) {
		for (PermanentRecipe recipe : this.recipes) {
			if (recipe.getDatabaseId() == id) {
				return recipe;
			}
		}
		return null;
	}
}
