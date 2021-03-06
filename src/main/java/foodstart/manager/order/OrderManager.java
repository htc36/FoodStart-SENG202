package foodstart.manager.order;

import foodstart.model.PaymentMethod;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;

import java.time.LocalDateTime;
import java.util.*;


/**
 * Acts as a controller, storing and managing the orders in the model
 */
public class OrderManager {
	/**
	 * The map of all orders
	 */
	private Map<Integer, Order> orders;

	/**
	 * A buffer for putting orders in before writing them to the system
	 */
	private Map<Integer, Order> buffer;

	/**
	 * Constructs a new instance of an order manager
	 */
	public OrderManager() {
		this.orders = new HashMap<Integer, Order>();
		this.buffer = new HashMap<Integer, Order>();
	}

	/**
	 * Returns the map of all orders
	 *
	 * @return the map of all orders
	 */
	public Map<Integer, Order> getOrders() {
		return orders;
	}

	/**
	 * Gets an order from the map of orders by its UID
	 *
	 * @param id the UID of the order to get
	 * @return The order that the UID refers to, or null
	 */
	public Order getOrder(int id) {
		return this.orders.get(id);
	}

	/**
	 * Constructs and adds an order to the map of all orders
	 *
	 * @param id            The identifier code of the order
	 * @param items         The items that have been ordered and their quantities
	 * @param customerName  The name of the customer who made the order
	 * @param timePlaced    The time that the order was placed
	 * @param paymentMethod The payment method that the customer chose
	 */
	public void addOrder(int id, Map<Recipe, Integer> items, String customerName, long timePlaced, PaymentMethod paymentMethod) {
		if (items == null) {
			items = new HashMap<Recipe, Integer>();
		}
		Order order = new Order(id, items, customerName, timePlaced, paymentMethod);
		this.orders.put(id, order);
	}
	
	/**
	 * Adds the given Order object to the manager.
	 * @param targetOrder the Order object to be added to the manager.
	 */
	public void addOrder(Order targetOrder) {
	    orders.put(targetOrder.getId(), targetOrder);
	}

	/**
	 * Constructs and adds an order to the map of all orders
	 *
	 * @param id            The identifier code of the order
	 * @param items         The items that have been ordered and their quantities
	 * @param customerName  The name of the customer who made the order
	 * @param timePlaced    The time that the order was placed
	 * @param paymentMethod The payment method that the customer chose
	 */
	public void addOrder(int id, Map<Recipe, Integer> items, String customerName, LocalDateTime timePlaced, PaymentMethod paymentMethod) {
		if (items == null) {
			items = new HashMap<Recipe, Integer>();
		}
		Order order = new Order(id, items, customerName, timePlaced, paymentMethod);
		this.orders.put(id, order);
	}

	/**
	 * Constructs and adds an order to the map of all orders
	 *
	 * @param id            The identifier code of the order
	 * @param items         The items that have been ordered and their quantities
	 * @param customerName  The name of the customer who made the order
	 * @param timePlaced    The time that the order was placed
	 * @param paymentMethod The payment method that the customer chose
	 * @param cost          The total cost of the order
	 */
	public void addOrder(int id, Map<Recipe, Integer> items, String customerName, long timePlaced, PaymentMethod paymentMethod, float cost) {
		if (items == null) {
			items = new HashMap<Recipe, Integer>();
		}
		Order order = new Order(id, items, customerName, timePlaced, paymentMethod, cost);
		this.orders.put(id, order);
	}

	/**
	 * Returns the set of orders from the ids specified
	 *
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
	 *
	 * @return the set of all orders stored in the map
	 */
	public Set<Order> getOrderSet() {
		return new HashSet<Order>(this.orders.values());
	}

	/**
	 * Returns the list of items and item quantities in an order of a given id as a string
	 *
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
			output += String.format("%dx %s, ", items.get(recipe), recipe.getDisplayName());
		}
		return output.substring(0, output.length() - 2);
	}

	/**
	 * Removes an order from the map of sales based on it's id
	 *
	 * @param id the id of the sale to remove
	 * @return true if the sale was removed, false otherwise
	 */
	public boolean removeOrder(int id) {
		Order removed = this.orders.remove(id);
		return removed != null;
	}

	/**
	 * Mutates an order, changing the ordered items
	 *
	 * @param id    The identifier code of the order
	 * @param items The items that have been ordered and their quantities
	 * @return true if the order could be found and changed; false otherwise
	 */
	public boolean mutateOrderItems(int id, Map<Recipe, Integer> items) {
		Order order = this.getOrder(id);
		if (order != null) {
			order.setItems(items);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Mutates an order, changing it's data
	 *
	 * @param id            The identifier code of the order
	 * @param customerName  The name of the customer who made the order
	 * @param timePlaced    The time that the order was placed
	 * @param price         The total price of the order
	 * @param paymentMethod The payment method that the customer chose
	 * @return true if the order could be found and changed; false otherwise
	 */
	public boolean mutateOrder(int id, String customerName, LocalDateTime timePlaced, float price, PaymentMethod paymentMethod) {
		Order order = this.getOrder(id);
		if (order != null) {
			order.setCustomerName(customerName);
			order.setTimePlaced(timePlaced);
			order.setPaymentMethod(paymentMethod);
			order.setPrice(price);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns all recipes included in the order of a given id
	 * @param id the id of the order
	 * @return a set of all permanent and on the fly recipes in the sale
	 */
	public Set<Recipe> getOrderRecipes(int id) {
		Order order = this.orders.get(id);
		if (order != null) {
			return order.getItems().keySet();
		} else {
			return null;
		}
	}

	/**
	 * Removes all orders from the order manager
	 */
	public void removeAllOrders() {
	    orders.clear();
	}

	/**
	 * Pushes a new order to the buffer
	 *
	 * @param id            The identifier code of the order
	 * @param items         The items that have been ordered and their quantities
	 * @param customerName  The name of the customer who made the order
	 * @param timePlaced    The time that the order was placed
	 * @param paymentMethod The payment method that the customer chose
	 */
	public void pushToBuffer(int id, Map<Recipe, Integer> items, String customerName, long timePlaced, PaymentMethod paymentMethod) {
		if (items == null) {
			items = new HashMap<Recipe, Integer>();
		}
		Order order = new Order(id, items, customerName, timePlaced, paymentMethod);
		this.buffer.put(id, order);
	}

	/**
	 * Pushes a new order to the buffer
	 *
	 * @param id            The identifier code of the order
	 * @param items         The items that have been ordered and their quantities
	 * @param customerName  The name of the customer who made the order
	 * @param timePlaced    The time that the order was placed
	 * @param paymentMethod The payment method that the customer chose
	 */
	public void pushToBuffer(int id, Map<Recipe, Integer> items, String customerName, LocalDateTime timePlaced, PaymentMethod paymentMethod) {
		if (items == null) {
			items = new HashMap<Recipe, Integer>();
		}
		Order order = new Order(id, items, customerName, timePlaced, paymentMethod);
		this.buffer.put(id, order);
	}

	/**
	 * Pushes a new order to the buffer
	 *
	 * @param id            The identifier code of the order
	 * @param items         The items that have been ordered and their quantities
	 * @param customerName  The name of the customer who made the order
	 * @param timePlaced    The time that the order was placed
	 * @param paymentMethod The payment method that the customer chose
	 * @param cost          The total cost of the order
	 */
	public void pushToBuffer(int id, Map<Recipe, Integer> items, String customerName, long timePlaced, PaymentMethod paymentMethod, float cost) {
		if (items == null) {
			items = new HashMap<Recipe, Integer>();
		}
		Order order = new Order(id, items, customerName, timePlaced, paymentMethod, cost);
		this.buffer.put(id, order);
	}

	/**
	 * Adds the current data in the buffer to the modeled orders
	 */
	public void writeBuffer() {
		this.orders.putAll(this.buffer);
		buffer.clear();
	}

	/**
	 * Drops the current data in the buffer
	 */
	public void dropBuffer() {
		this.buffer.clear();
	}

	/**
	 * Removes a recipe from all modelled orders.
	 * If the number of recipes in the order after deletion is 0, the order is deleted
	 *
	 * @param recipe the recipe to remove from the orders
	 */
	public void removeRecipeFromOrders(Recipe recipe) {
		for (Order order : getOrderSet()) {
			Map<Recipe, Integer> items = order.getItems();
			items.remove(recipe);
			order.setItems(items);
			if (order.getItems().size() == 0) {
				orders.remove(order.getId());
			}
		}
	}
}

