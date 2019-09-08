package foodstart.model.order;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import foodstart.manager.Managers;
import foodstart.manager.exceptions.InsufficientStockException;
import foodstart.model.PaymentMethod;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;

/**
 * Class that builds up an order and calculates whether a given item can be
 * added to the order with the current amount of stock in the truck
 * 
 * @author Alex Hobson
 * @date 08/09/2019
 */
public class OrderBuilder {

	/**
	 * The map of the current order, maps recipes to their quantity
	 */
	final Map<Recipe, Integer> currentOrder;

	/**
	 * Constructor for the order builder
	 */
	public OrderBuilder() {
		currentOrder = new LinkedHashMap<Recipe, Integer>();
		//LinkedHashMap preserves insertion order so the items in the GUI are
		//always in the same order
	}

	/**
	 * Gets the current total price of the order
	 * 
	 * @return The current total price of the order (2dp)
	 */
	public float getCurrentTotalPrice() {
		float total = 0.0F;
		for (Map.Entry<Recipe, Integer> item : currentOrder.entrySet()) {
			total += item.getKey().getPrice() * item.getValue();
		}
		return Math.round(total * 100F) / 100F; // round to 2dp
	}

	/**
	 * Calculates whether the proposed item can be added to the order
	 * 
	 * @param recipe
	 *            Recipe to see if it can be added
	 * @param quantity
	 *            How much of this recipe to try and add
	 * @return True if the item can be added to the order with the current stock,
	 *         false otherwise
	 */
	public boolean canAddItem(Recipe recipe, int quantity) {
		for (Map.Entry<Ingredient, Integer> recipeItem : recipe.getIngredients().entrySet()) {
			int truckStock = recipeItem.getKey().getTruckStock();
			int alreadyRequired = calculateRequiredStock(recipeItem.getKey());
			if (truckStock - alreadyRequired < (recipeItem.getValue() * quantity)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Adds the item to the order
	 * 
	 * @param recipe
	 * @param quantity
	 * @throws InsufficientStockException
	 *             when there isn't enough truck stock to add this to the order
	 */
	public void addItem(Recipe recipe, int quantity) {
		if (!canAddItem(recipe, quantity)) {
			String name = recipe instanceof PermanentRecipe ? ((PermanentRecipe) recipe).getDisplayName() : "Custom recipe";
			throw new InsufficientStockException("Insufficient stock to add '"+name+"' to the order");
		}
		if (currentOrder.containsKey(recipe)) {
			quantity += currentOrder.get(recipe);
		}
		currentOrder.put(recipe, quantity);
	}
	
	/**
	 * Remove all of a given recipe from the order
	 * @param recipe Recipe to remove
	 */
	public void removeItem(Recipe recipe) {
		this.currentOrder.remove(recipe);
	}
	
	/**
	 * Build the order with the given customer name and payment method
	 * @param customerName Customer's name
	 * @param paymentMethod Payment method used
	 */
	public void build(String customerName, PaymentMethod paymentMethod) {
		int id = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
		while (Managers.getOrderManager().getOrder(id) != null) {
			id++; //make sure the order id is unique
		}
		Managers.getOrderManager().addOrder(id, this.currentOrder, customerName, System.currentTimeMillis(), paymentMethod);
	}
	
	/**
	 * Gets all items in the current order
	 * @return Map of recipes and their quantities of items in the current order
	 */
	public Map<Recipe, Integer> getCurrentOrder() {
		return this.currentOrder;
	}
	
	/**
	 * Gets the quantity of an item in the order
	 * @param recipe Recipe to get the quantity of
	 * @return Quantity of this item in the order
	 */
	public int getQuantity(Recipe recipe) {
		return this.currentOrder.get(recipe);
	}

	/**
	 * Calculates the amount of stock needed to create the current order
	 * 
	 * @return Map of ingredients to the amount it needs
	 */
	private Map<Ingredient, Integer> calculateRequiredStock() {
		Map<Ingredient, Integer> requiredStock = new HashMap<Ingredient, Integer>();
		for (Ingredient ingredient : Managers.getIngredientManager().getIngredientSet()) {
			int required = calculateRequiredStock(ingredient);
			if (required > 0) {
				requiredStock.put(ingredient, required);
			}
		}
		return requiredStock;
	}

	/**
	 * Calculates the amount of stock needed of a particular ingredient to create
	 * the current order
	 * 
	 * @return Integer of how much is needed
	 */
	public int calculateRequiredStock(Ingredient ingredient) {
		int required = 0;
		for (Map.Entry<Recipe, Integer> item : currentOrder.entrySet()) {
			if (item.getKey().getIngredients().containsKey(ingredient)) {
				required += item.getKey().getIngredients().get(ingredient);
			}
		}
		return required;
	}
}
