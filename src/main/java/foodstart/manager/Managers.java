package foodstart.manager;

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
