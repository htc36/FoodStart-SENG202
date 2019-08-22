package foodstart.model.order;
import java.util.Map;

import foodstart.model.PaymentMethod;
import foodstart.model.menu.Recipe;


/**
 * The class that holds information about an order and its methods
 */

public class Order
{
	/**
	 * The identifier code of the order
	 */
	
	private String id;

	/**
	 * The items that have been ordered and their quantities	
	 */
	
	private Map<Recipe, Integer> items;
	

	/**
	 * The name of the customer who made the order
	 */
	
	private String customerName;

	/**
	 * The time that the order was placed
	 */
	
	private long timePlaced;

	/**
	 * The payment method that the customer chose
	 */
	
	private PaymentMethod paymentMethod;

	/**
	 * The order constructor
	 * @param id The identifier code of the order
	 * @param items The items that have been ordered and their quantities	
	 * @param customerName The name of the customer who made the order
	 * @param timePlaced The time that the order was placed
	 * @param paymentMethod The payment method that the customer chose
	 */
	public Order(String id, Map<Recipe, Integer> items, String customerName, long timePlaced, PaymentMethod paymentMethod) {
		this.id = id;
		this.items = items;
		this.customerName = customerName;
		this.timePlaced = timePlaced;
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Calculates the total cost of an order
	 * @return totalCost The total cost of the order
	 */
	
	public float getTotalCost() {
		// TODO implement me
		
	
		return 0.0f;
	}

	/**
	 * Adds an item to the order
	 */
	
	public void addItem(Recipe parameter) {
		// TODO implement me
	}

}

