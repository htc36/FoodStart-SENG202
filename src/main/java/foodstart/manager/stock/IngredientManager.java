package foodstart.manager.stock;

import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;

import java.util.*;


/**
 * Acts as a controller, storing and managing the ingredients items in the model
 */
public class IngredientManager {
	/**
	 * The map of all ingredients modeled
	 */
	private Map<Integer, Ingredient> ingredients;


	/**
	 * Constructs an instance of an ingredient manager
	 */
	public IngredientManager() {
		this.ingredients = new HashMap<Integer, Ingredient>();
	}

	/**
	 * Constructs and adds an ingredient to the map of ingredients
	 *
	 * @param unit         Unit of the ingredient
	 * @param name         Name of the ingredient
	 * @param id           Identifier code of the ingredient
	 * @param safeFor      map of dietary requirements to whether or not the ingredient is considered safe for that requirement
	 * @param kitchenStock Amount of current stock in the kitchen
	 * @param truckStock   Amount of current stock in the truck
	 */
	public void addIngredient(Unit unit, String name, int id, Map<DietaryRequirement, Boolean> safeFor, int kitchenStock, int truckStock) {
		Ingredient ingredient = new Ingredient(unit, name, id, safeFor, kitchenStock, truckStock);
		this.ingredients.put(id, ingredient);
	}

	/**
	 * Returns the map of all menu items modeled
	 *
	 * @return the map of all menu items modeled
	 */
	public Map<Integer, Ingredient> getIngredients() {
		return this.ingredients;
	}

	/**
	 * Gets an ingredient from the map of ingredient by its UID
	 *
	 * @param id the UID of the ingredient
	 * @return The ingredient that the UID refers to, or null
	 */
	public Ingredient getIngredient(int id) {
		return this.ingredients.get(id);
	}

	/**
	 * Gets an ingredient from the map by its name
	 *
	 * @param name The name of the ingredient
	 * @return The ingredient that this name refers to, or null
	 */
	public Ingredient getIngredientByName(String name) {
		for (Ingredient ingredient : this.ingredients.values()) {
			if (ingredient.getName().equals(name)) {
				return ingredient;
			}
		}
		return null;
	}

	/**
	 * Updates the truck stock for some ingredient
	 *
	 * @param id     the id of the ingredient to update
	 * @param amount the amount to set the tuck stock to
	 */
	public void updateTruckStock(int id, int amount) {
		Ingredient ingredient = this.ingredients.get(id);
		ingredient.setTruckStock(amount);
	}

	/**
	 * Updates the kitchen stock for some ingredient
	 *
	 * @param id     the id of the ingredient to update
	 * @param amount the amount to set the kitchen stock to
	 */
	public void updateKitchenStock(int id, int amount) {
		Ingredient ingredient = this.ingredients.get(id);
		ingredient.setKitchenStock(amount);
	}

	/**
	 * Checks if an ingredient is in stock in the truck
	 *
	 * @param id the id of the ingredient to check
	 * @return true if the truck stock is greater than 0; false otherwise
	 */
	public boolean isInTruckStock(int id) {
		Ingredient ingredient = this.ingredients.get(id);
		return ingredient.getTruckStock() > 0;
	}

	/**
	 * Checks if an ingredient is in stock in the kitchen
	 *
	 * @param id the id of the ingredient to check
	 * @return true if the truck stock is greater than 0; false otherwise
	 */
	public boolean isInKitchenStock(int id) {
		Ingredient ingredient = this.ingredients.get(id);
		return ingredient.getKitchenStock() > 0;
	}

	/**
	 * Returns the set of ingredients from the ids specified
	 *
	 * @param ids the ids of the ingredients to fetch
	 * @return the set of ingredients requested
	 */
	public Set<Ingredient> getIngredients(Collection<Integer> ids) {
		Set<Ingredient> items = new HashSet<Ingredient>();
		for (int id : ids) {
			Ingredient item = this.ingredients.get(id);
			if (item != null) {
				items.add(item);
			}
		}
		return items;
	}

	/**
	 * Returns the set of all ingredients stored in the map
	 *
	 * @return the set of all ingredients stored in the map
	 */
	public Set<Ingredient> getIngredientSet() {
		return new HashSet<Ingredient>(this.ingredients.values());
	}

	public String safeForString(int id) {
		Ingredient ingredient = this.ingredients.get(id);
		ArrayList<String> safeForList = new ArrayList<String>();
		Map<DietaryRequirement, Boolean> safeDiets = ingredient.getSafeFor();
		for (Map.Entry<DietaryRequirement, Boolean> entry : safeDiets.entrySet()) {
			if (entry.getValue()) {
				safeForList.add(entry.getKey().getHumanName());
			}
		}
		return String.join(", ", safeForList);
	}
	public int generateNewID() {
		return ingredients.keySet().size() == 0 ? 0 : Collections.max(ingredients.keySet()) + 1;
	}

	/**
	 * Mutates an ingredient of the given id to have the given parameters
	 *
	 * @param unit         Unit of the ingredient
	 * @param name         Name of the ingredient
	 * @param id           Identifier code of the ingredient
	 * @param safeFor      map of dietary requirements to whether or not the ingredient is considered safe for that requirement
	 * @param kitchenStock Amount of current stock in the kitchen
	 * @param truckStock   Amount of current stock in the truck
	 */
	public void mutateIngredient(Unit unit, String name, int id, Map<DietaryRequirement, Boolean> safeFor, int kitchenStock, int truckStock) {
		Ingredient ingredient = this.ingredients.get(id);
		if (ingredient != null) {
			ingredient.setName(name);
			ingredient.setUnit(unit);
			ingredient.setSafeFor(safeFor);
			ingredient.setKitchenStock(kitchenStock);
			ingredient.setTruckStock(truckStock);
		}
	}
}

