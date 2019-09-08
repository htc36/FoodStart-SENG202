package foodstart.manager.order;
import foodstart.model.PaymentMethod;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;

import java.util.*;


/**
 * Acts as a controller, storing and managing the orders in the model
 */
public class OrderManager
{
	/**
	 * The map of all orders
	 */
	private Map<Integer, Order> orders;

	/**
	 * Constructs a new instance of an order manager
	 */
	public OrderManager(){
		this.orders = new HashMap<Integer, Order>();
	}

	/**
	 * Returns the map of all orders
	 * @return the map of all orders
	 */
	public Map<Integer, Order> getOrders() {
		return orders;
	}

	/**
	 * Gets an order from the map of orders by its UID
	 * @param id the UID of the order to get
	 * @return The order that the UID refers to, or null
	 */
	public Order getOrder(int id) {
		return this.orders.get(id);
	}

	/**
	 * Constructs and adds an order to the map of all orders
	 * @param id The identifier code of the order
	 * @param items The items that have been ordered and their quantities
	 * @param customerName The name of the customer who made the order
	 * @param timePlaced The time that the order was placed
	 * @param paymentMethod The payment method that the customer chose
	 */
	public void addOrder(int id, Map<Recipe, Integer> items, String customerName, long timePlaced, PaymentMethod paymentMethod) {
		Order order = new Order(id, items, customerName, timePlaced, paymentMethod);
		this.orders.put(id, order);
	}

	/**
	 * Returns the set of orders from the ids specified
	 * @param ids the ids of the orders to fetch
	 * @return the set of orders requested
	 */
	public Set<Order> getOrders(Collection<Integer> ids) {
		Set<Order> items = new HashSet<Order>();
		for (int id : ids) {
			Order item = this.orders.get(id);
			if (item != null) {
				items.add(item);
			}
		}
		return items;
	}

	/**
	 * Returns the set of all orders stored in the map
	 * @return the set of all orders stored in the map
	 */
	public Set<Order> getOrderSet() {
		Set<Order> orderSet = new HashSet<Order>(this.orders.values());
		return orderSet;
	}

	/**
	 * Returns the list of items and item quantities in an order of a given id as a string
	 * @param id the id of the order
	 * @return a string representation of the items in the order
	 */
	public String getItemsAsString(int id) {
		Order order = this.orders.get(id);
		if (order == null) {
			return "";
		}
		String output = "";
		Map<Recipe, Integer> items = order.getItems();
		for (Recipe recipe : items.keySet()) {
			if (recipe instanceof PermanentRecipe) {
				//Permanent recipe
				output += String.format("%dx %s", items.get(recipe), ((PermanentRecipe) recipe).getDisplayName());
			} else {
				//OTF Recipe
				output += String.format("%dx %s (Modified)", items.get(recipe), ((PermanentRecipe) recipe).getDisplayName());
			}
		}
		return output;
	}

	/**
	 * Removes an order from the map of sales based on it's id
	 * @param id the id of the sale to remove
	 * @return true if the sale was removed, false otherwise
	 */
	public boolean removeOrder(int id) {
		Order removed = this.orders.remove(id);
		if (removed == null) {
			return false;
		} else {
			return true;
		}
	}
}

