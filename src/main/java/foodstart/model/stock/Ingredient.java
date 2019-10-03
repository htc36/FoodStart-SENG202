package foodstart.model.stock;

import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;

import java.util.HashMap;
import java.util.Map;


/**
 * Stores relevant information and methods about an ingredient
 */
public class Ingredient {

	/**
	 * The unit of measurement for the ingredient
	 */
	private Unit unit;

	/**
	 * The name of the ingredient
	 */
	private String name;

	/**
	 * The identifier code of the ingredient
	 */
	private int id;

	/**
	 * A map of dietary requirements to whether or not the ingredient is considered safe for that requirement
	 */
	private Map<DietaryRequirement, Boolean> safeFor;


	/**
	 * The current stock available at the kitchen
	 */
	private int kitchenStock;

	/**
	 * The current stock available at the truck
	 */
	private int truckStock;

	/**
	 * Constructs an Ingredient object, taking all the fields of the class as parameters. 
	 *
	 * @param unit         Unit of the ingredient
	 * @param name         Name of the ingredient
	 * @param id           Identifier code of the ingredient
	 * @param safeFor      map of dietary requirements to whether or not the ingredient is considered safe for that requirement
	 * @param kitchenStock Amount of current stock in the kitchen
	 * @param truckStock   Amount of current stock in the truck
	 */
	public Ingredient(Unit unit, String name, int id, Map<DietaryRequirement, Boolean> safeFor, int kitchenStock, int truckStock) {
		this.unit = unit;
		this.name = name;
		this.id = id;
		this.safeFor = safeFor;
		this.kitchenStock = kitchenStock;
		this.truckStock = truckStock;
	}
	
	/**
	 * Gets the ingredient's unit of measurement
	 *
	 * @return unit Unit of the ingredient
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Sets the ingredient's unit of measurement
	 *
	 * @param unit the unit of the ingredient
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * Gets the ingredient's name
	 *
	 * @return name Name of the ingredient
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the ingredient's name
	 * @param name the name of the ingredient
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the ingredient's identifier code
	 *
	 * @return databaseId Identifier code of the ingredient
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the ingredient's identifier code
	 * @param databaseId the id of the ingredient
	 */
	public void setId(int databaseId) {
		this.id = databaseId;
	}

	/**
	 * Returns the map of dietary requirements
	 *
	 * @return safeFor the map of dietary requirements
	 */
	public Map<DietaryRequirement, Boolean> getSafeFor() {
		return safeFor;
	}

	/**
	 * Sets whether the ingredient complies with the dietary requirements
	 *
	 * @param safeFor the dietary requirements that the ingredient meets
	 */
	public void setSafeFor(Map<DietaryRequirement, Boolean> safeFor) {
		this.safeFor = safeFor;
	}

	/**
	 * Gets the current stock available at the kitchen
	 *
	 * @return kitchenStock current stock available at the kitchen
	 */
	public int getKitchenStock() {
		return kitchenStock;
	}

	/**
	 * Sets the current stock available at the kitchen
	 *
	 * @param kitchenStock the kitchen stock of the ingredient
	 */
	public void setKitchenStock(int kitchenStock) {
		this.kitchenStock = kitchenStock;
	}

	/**
	 * Gets the current stock available at the truck
	 *
	 * @return truckStock current stock available at the truck
	 */
	public int getTruckStock() {
		return truckStock;
	}

	/**
	 * Sets the current stock available at the truck
	 *
	 * @param truckStock the truck stock of the ingredient
	 */
	public void setTruckStock(int truckStock) {
		this.truckStock = truckStock;
	}
	
	
	/**
	 * Returns a deep copy of this Ingredient object.
	 * @return a new Ingredient object 
	 */
	@Override
	public Ingredient clone() {
	    Map<DietaryRequirement, Boolean> mapCopy = new HashMap<DietaryRequirement, Boolean>(safeFor);
		return new Ingredient(unit, name, id, mapCopy, kitchenStock, truckStock);
	}
	

	/**
	 * Returns whether the target object's fields are equal to this one's.
	 * @param target - the Ingredient object being compared with.
	 * @return True if all fields between the objects are equal; false otherwise.
	 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ingredient other = (Ingredient) obj;
        if (id != other.id)
            return false;
        if (kitchenStock != other.kitchenStock)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (safeFor == null) {
            if (other.safeFor != null)
                return false;
        } else if (!safeFor.equals(other.safeFor))
            return false;
        if (truckStock != other.truckStock)
            return false;
        if (unit != other.unit)
            return false;
        return true;
    }

    /**
	 * Checks if the ingredient is safe for some dietary requirement
	 * @param requirement the dietary requirement to check
	 * @return true of the ingredient is considered safe for the dietary requirement; false otherwise
	 */
	public Boolean isSafeFor(DietaryRequirement requirement) {
		return this.safeFor.getOrDefault(requirement, false);
	}

	/**
	 * Returns the name of the ingredient
	 * @return the name of the ingredient
	 */
	public String toString() {
		return this.getName();
	}
}

