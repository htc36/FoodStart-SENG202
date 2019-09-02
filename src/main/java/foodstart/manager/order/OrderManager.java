package foodstart.manager.order;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import foodstart.model.PaymentMethod;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class OrderManager
{
	/**
	 * The set of all orders
	 */
	private Map<Integer, Order> orders;

	/**
	 * Constructs a new instance of an order manager
	 */
	public OrderManager(){
		this.orders = new HashMap<Integer, Order>();
	}

	/**
	 * Returns the set of all orders
	 * @return the set of all orders
	 */
	public Map<Integer, Order> getOrders() {
		return orders;
	}

	/**
	 * Gets an order from the set of orders by its UID
	 * @param id the UID of the order to get
	 * @return The order that the UID refers to, or null
	 */
	public Order getOrder(int id) {
		return this.orders.get(id);
	}

	/**
	 * Constructs and adds an order to the set of all orders
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
}

