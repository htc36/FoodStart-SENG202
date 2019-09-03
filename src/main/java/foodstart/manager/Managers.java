package foodstart.manager;

import foodstart.manager.menu.MenuItemManager;
import foodstart.manager.menu.MenuManager;
import foodstart.manager.menu.RecipeManager;
import foodstart.manager.order.OrderManager;
import foodstart.manager.stock.IngredientManager;
import foodstart.manager.stock.SupplierManager;

import foodstart.manager.xml.XMLPersistence;

/**
 * Manager for all the managers
 * @author Alex Hobson
 * @date 22/08/2019
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
	private static Persistence persistence = new XMLPersistence();

	private static MenuItemManager menuItemManager = new MenuItemManager();

	private static RecipeManager recipeManager = new RecipeManager();

	public static IngredientManager getIngredientManager() {
		return ingredientManager;
	}

	public static OrderManager getOrderManager() {
		return orderManager;
	}

	public static SupplierManager getSupplierManager() {
		return supplierManager;
	}

	public static MenuManager getMenuManager() {
		return menuManager;
	}

	public static RecipeManager getRecipeManager() {
		return recipeManager;
	}

	public static MenuItemManager getMenuItemManager() {
		return menuItemManager;
	}

	public static Persistence getDefaultPersistence() {
		return persistence;
	}
}
