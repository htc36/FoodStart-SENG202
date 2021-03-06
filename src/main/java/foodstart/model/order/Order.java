package foodstart.model.order;

import foodstart.model.PaymentMethod;
import foodstart.model.menu.Recipe;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;


/**
 * The class that holds information about an order and its methods
 *
 * @author Frankie Oprenario
 */

public class Order {

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
	private LocalDateTime timePlaced;

	/**
	 * The payment method that the customer chose
	 */
	private PaymentMethod paymentMethod;

	/**
	 * The total price of the order
	 */
	private float price;

	/**
	 * The order constructor
	 *
	 * @param id            The identifier code of the order
	 * @param items         The items that have been ordered and their quantities
	 * @param customerName  The name of the customer who made the order
	 * @param timePlaced    The time that the order was placed
	 * @param paymentMethod The payment method that the customer chose
	 */
	public Order(int id, Map<Recipe, Integer> items, String customerName, LocalDateTime timePlaced, PaymentMethod paymentMethod) {


		this.id = id;
		this.items = items;
		this.customerName = customerName;
		this.timePlaced = timePlaced;
		this.paymentMethod = paymentMethod;
		calculateCost();
	}

	/**
	 * The order constructor
	 *
	 * @param id            The identifier code of the order
	 * @param items         The items that have been ordered and their quantities
	 * @param customerName  The name of the customer who made the order
	 * @param timePlaced    The time that the order was placed
	 * @param paymentMethod The payment method that the customer chose
	 */
	public Order(int id, Map<Recipe, Integer> items, String customerName, long timePlaced, PaymentMethod paymentMethod) {
		this.id = id;
		this.items = items;
		this.customerName = customerName;
		//Ignores timezones by using UTC
		this.timePlaced = LocalDateTime.ofEpochSecond(timePlaced / 1000, 0, OffsetDateTime.now().getOffset());
		this.paymentMethod = paymentMethod;
		calculateCost();
	}

	/**
	 * The order constructor
	 *
	 * @param id            The identifier code of the order
	 * @param items         The items that have been ordered and their quantities
	 * @param customerName  The name of the customer who made the order
	 * @param timePlaced    The time that the order was placed
	 * @param paymentMethod The payment method that the customer chose
	 * @param cost          The total cost of the order
	 */
	public Order(int id, Map<Recipe, Integer> items, String customerName, long timePlaced, PaymentMethod paymentMethod, float cost) {
		this.id = id;
		this.items = items;
		this.customerName = customerName;
		//Ignores timezones by using UTC
		this.timePlaced = LocalDateTime.ofEpochSecond(timePlaced / 1000, 0, ZoneOffset.UTC);
		this.paymentMethod = paymentMethod;
		this.price = cost;
	}

	/**
	 * Returns the id of the order
	 *
	 * @return the id of the order
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id of the order
	 *
	 * @param id the id of the order
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the map of recipes in the order to the amount of the recipe
	 *
	 * @return the map of recipes in the order to the amount of the recipe
	 */
	public Map<Recipe, Integer> getItems() {
		return items;
	}

	/**
	 * Sets the map of recipes in the order to the amount of the recipe
	 *
	 * @param items the map of recipes in the order to the amount of the recipe
	 */
	public void setItems(Map<Recipe, Integer> items) {
		this.items = items;
	}

	/**
	 * Returns the name of the customer
	 *
	 * @return the name of the customer
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Sets the name of the customer
	 *
	 * @param customerName the name of the customer
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Returns the time at which the order was placed
	 *
	 * @return the time at which the order was placed
	 */
	public LocalDateTime getTimePlaced() {
		return timePlaced;
	}

	/**
	 * Sets the time at which the order was placed
	 *
	 * @param timePlaced the time at which the order was placed
	 */
	public void setTimePlaced(LocalDateTime timePlaced) {
		this.timePlaced = timePlaced;
	}

	/**
	 * Returns the payment method for the order
	 *
	 * @return the payment method for the order
	 */
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Sets the payment method for the order
	 *
	 * @param paymentMethod the payment method for the order
	 */
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Returns the total cost of an order
	 *
	 * @return The total cost of the order
	 */
	public float getTotalCost() {
		return this.price;
	}

	/**
	 * Adds a recipe to the order. Overrides old value if the recipe already exists
	 *
	 * @param recipe the recipe to add
	 * @param amount the amount of the recipe to add
	 * @return the previous amount of the recipe, or null if the recipe did not exist
	 */
	public Integer addItem(Recipe recipe, int amount) {
		if (!this.items.containsKey(recipe))  {
			this.items.put(recipe, amount);
			calculateCost();
			return null;
		} else {
			Integer previousAmount = items.get(recipe);
			items.put(recipe, previousAmount + amount);
			calculateCost();

			return previousAmount;
		}
	}

	/**
	 * Removes a recipe from the order
	 *
	 * @param recipe the recipe to remove
	 * @return the amount of the recipe, or null if the recipe did not exist
	 */
	public Integer removeItem(Recipe recipe) {
		if (this.items.remove(recipe) == null)  {
			return null;
		} else {
			Integer recipeAmount = this.items.remove(recipe);
			calculateCost();
			return recipeAmount;
		}
	}

	/**
	 * Sets the amount for the recipe in the order
	 *
	 * @param recipe the recipe that requires for the amount to be altered
	 * @param amount the amount to be set
	 */
	public void setVariantAmount(Recipe recipe, int amount) {
		this.items.put(recipe, amount);
	}

	/**
	 * Decreases the amount of the recipe in the order
	 *
	 * @param recipe the recipe that requires its amount to be decreased
	 * @param amount the amount to subtract from the current amount in the order
	 */
	public void decreaseVariantAmount(Recipe recipe, int amount) {
		if (this.items.containsKey(recipe)) {
			if ((this.items.get(recipe) - amount) > 0) {
				setVariantAmount(recipe, (this.items.get(recipe) - amount)); // changes the amount of the item
				calculateCost();
			} else if ((this.items.get(recipe) - amount) <= 0) {
				removeItem(recipe); // removes the item completely from the order
				calculateCost();
			}
		} // else, should throw an exception about the recipe not already existing in the order
	}

	/**
	 * Calculates the total number of items ordered
	 *
	 * @return TotalOrderItems The total number of items ordered
	 */
	public Integer getTotalItemCount() {
		int total = 0;
		for (int i : this.items.values()) {
			total += i;
		}
		return total;
	}

	/**
	 * Gets the recipe amount ordered
	 *
	 * @param recipe the recipe to get the number quantity of
     * @return variantCount the amount of the ordered recipe
	 */
	public int getVariantCount(Recipe recipe) {
		return this.items.get(recipe);
	}

	/**
	 * Calculates the total price of the order based on the current price of the recipes included
	 */
	private void calculateCost() {

		float total = 0;
		for (Map.Entry<Recipe, Integer> entry : items.entrySet()) {
			total += entry.getKey().getPrice() * entry.getValue();
		}
		this.price = total;
	}

	/**
	 * Returns a deep copy of this order.
	 * @return a deep copy of this order.
	 */
	public Order clone() {
	    Map<Recipe, Integer> itemsCopy = new HashMap<Recipe, Integer>(items);
	    return new Order(id, itemsCopy, customerName, timePlaced, paymentMethod);
	}

	/**
	 * Returns the hash code of the order
	 *
	 * @return the hash code of the order
	 */
	@Override
	public int hashCode() {
		return ((Integer) this.id).hashCode();
	}

	/**
	 * Returns true if the given object has the same parameters
	 * @param obj the object to check against
	 * @return true if the objects have equal parameters; false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (id != other.id)
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (paymentMethod != other.paymentMethod)
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		if (timePlaced == null) {
			if (other.timePlaced != null)
				return false;
		} else if (!timePlaced.equals(other.timePlaced))
			return false;
		return true;
	}

    /**
	 * Sets the total price of the order, used if the cost of recipes changes
	 *
	 * @param price the total price of the order
	 */
	public void setPrice(float price) {
		this.price = price;
	}
}

