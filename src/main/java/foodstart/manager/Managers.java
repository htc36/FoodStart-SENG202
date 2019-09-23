package foodstart.manager;

import foodstart.manager.menu.MenuItemManager;
import foodstart.manager.menu.MenuManager;
import foodstart.manager.menu.RecipeManager;
import foodstart.manager.order.OrderManager;
import foodstart.manager.stock.IngredientManager;
import foodstart.manager.stock.SupplierManager;
import foodstart.manager.xml.XMLPersistence;
import foodstart.model.DataFileType;

/**
 * Manager for all the managers
 *
 * @author Alex Hobson on 22/08/2019
 */
public class Managers {

	/**
	 * Functions relevant to orders
	 */
	private static OrderManager orderManager = new OrderManager();

	/**
	 * Functions relevant to suppliers
	 */
	private static SupplierManager supplierManager = new SupplierManager();

	/**
	 * Functions relevant to the menus
	 */
	private static MenuManager menuManager = new MenuManager();

	/**
	 * Functions relevant to ingredients
	 */
	private static IngredientManager ingredientManager = new IngredientManager();

	/**
	 * Default persistence
	 */
	private static Persistence xmlPersistence = new XMLPersistence();

	/**
	 * Menu item manager
	 */
	private static MenuItemManager menuItemManager = new MenuItemManager();

	/**
	 * Recipe manager
	 */
	private static RecipeManager recipeManager = new RecipeManager();

	/**
	 * Returns the ingredient manager
	 * @return the ingredient manager
	 */
	public static IngredientManager getIngredientManager() {
		return ingredientManager;
	}

	/**
	 * Returns the order manager
	 * @return the order manager
	 */
	public static OrderManager getOrderManager() {
		return orderManager;
	}

	/**
	 * Returns the supplier manager
	 * @return the supplier manager
	 */
	public static SupplierManager getSupplierManager() {
		return supplierManager;
	}

	/**
	 * Returns the menu manager
	 * @return the menu manager
	 */
	public static MenuManager getMenuManager() {
		return menuManager;
	}

	/**
	 * Returns the recipe manager
	 * @return the recipe manager
	 */
	public static RecipeManager getRecipeManager() {
		return recipeManager;
	}

	/**
	 * Returns the menu item manager
	 * @return the menu item manager
	 */
	public static MenuItemManager getMenuItemManager() {
		return menuItemManager;
	}

	/**
	 * Returns the default persistence manager
	 * @return the default persistence manager
	 */
	public static Persistence getDefaultPersistence() {
		return xmlPersistence;
	}

	/**
	 * Returns the persistence manager for a given file type
	 * @param fileType the file type to get the manager for
	 * @return the persistence manager for the file type
	 */
	public static Persistence getPersistence(DataFileType fileType) {
		switch(fileType) {
			case XML:
				return xmlPersistence;
			default:
				return getDefaultPersistence();
		}
	}
}
