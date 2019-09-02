package foodstart.model.order;
import java.util.Map;
import java.util.Set;

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
	
	private int id;

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
	public Order(int id, Map<Recipe, Integer> items, String customerName, long timePlaced, PaymentMethod paymentMethod) {
		this.id = id;
		this.items = items;
		this.customerName = customerName;
		this.timePlaced = timePlaced;
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Returns the id of the order
	 * @return the id of the order
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id of the order
	 * @param id the id of the order
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the map of recipes in the order to the amount of the recipe
	 * @return the map of recipes in the order to the amount of the recipe
	 */
	public Map<Recipe, Integer> getItems() {
		return items;
	}

	/**
	 * Sets the map of recipes in the order to the amount of the recipe
	 * @param items the map of recipes in the order to the amount of the recipe
	 */
	public void setItems(Map<Recipe, Integer> items) {
		this.items = items;
	}

	/**
	 * Returns the name of the customer
	 * @return the name of the customer
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Sets the name of the customer
	 * @param customerName the name of the customer
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Returns the time at which the order was placed
	 * @return the time at which the order was placed
	 */
	public long getTimePlaced() {
		return timePlaced;
	}

	/**
	 * Sets the time at which the order was placed
	 * @param timePlaced the time at which the order was placed
	 */
	public void setTimePlaced(long timePlaced) {
		this.timePlaced = timePlaced;
	}

	/**
	 * Returns the payment method for the order
	 * @return the payment method for the order
	 */
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Sets the payment method for the order
	 * @param paymentMethod the payment method for the order
	 */
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Calculates the total cost of an order
	 * @return totalCost The total cost of the order
	 */
	public float getTotalCost() {
		Set<Recipe> recipes = this.items.keySet();
		float total = 0;
		for (Recipe recipe : recipes) {
			total += recipe.getPrice();
		}
		return total;
	}

	/**
	 * Adds a recipe to the order. Overrides old value if the recipe already exists
	 * @param recipe the recipe to add
	 * @param amount the amount of the recipe to add
	 * @return the previous amount of the recipe, or null if the recipe did not exist
	 */
	public Integer addItem(Recipe recipe, int amount) {
		return this.items.put(recipe, amount);
	}

	/**
	 * Removes a recipe from the order
	 * @param recipe the recipe to remove
	 * @return the amount of the recipe, or null if the recipe did not exist
	 */
	public Integer removeItem(Recipe recipe) {
		return this.items.remove(recipe);
	}

	/** Calculates the total number of items ordered
	 * @return TotalOrderItems The total number of items ordered
	 */
	public Integer getTotalItemCount() {
		int total = 0;
		for (int i : this.items.values()) {
			total += i;
		}
		return total;
	}

	/** Gets the recipe amount ordered
	 * @return variantCount the amount of the ordered recipe
	 */
	public int getVariantCount(Recipe recipe) {
		return this.items.get(recipe);
	}

}

