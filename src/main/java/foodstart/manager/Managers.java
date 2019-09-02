package foodstart.manager;

import foodstart.manager.menu.MenuManager;
import foodstart.manager.order.OrderManager;
import foodstart.manager.stock.IngredientManager;
import foodstart.manager.stock.InventoryManager;
import foodstart.manager.stock.SupplierManager;

/**
 * Manager for all the managers
 * @author Alex Hobson
 * @date 22/08/2019
 */
public class Managers {

	/**
	 * Functions relevant to the inventory and stock
	 */
	private static InventoryManager inventoryManager = new InventoryManager();

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

	public static IngredientManager getIngredientManager() {
		return ingredientManager;
	}

	public static InventoryManager getInventoryManager() {
		return inventoryManager;
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

}
