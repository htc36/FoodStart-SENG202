package foodstart.model.stock;
import java.util.Map;

import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Ingredient
{

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
	
	private String databaseId;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
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
	 * The ingredient constructor
	 * @param unit Unit of the ingredient
	 * @param name Name of the ingredient
	 * @param databaseId Identifier code of the ingredient
	 * @param safeFor ???
	 * @param kitchenStock Amount of current stock in the kitchen
	 * @param truckStock Amount of current stock in the truck
	 */
	public Ingredient(Unit unit, String name, String databaseId, Map<DietaryRequirement, Boolean> safeFor, int kitchenStock, int truckStock) {
		this.unit = unit;
		this.name = name;
		this.databaseId = databaseId;
		this.safeFor = safeFor;
		this.kitchenStock = kitchenStock;
		this.truckStock = truckStock;
	}
	
	/**
	 * Gets the ingredient's unit of measurement
	 * @return unit Unit of the ingredient 
	 */
	public Unit getUnit() {
		return unit;
	}
	
	/**
	 * Sets the ingredient's unit of measurement
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	/**
	 * Gets the ingredient's name
	 * @return name Name of the ingredient
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the ingredient's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the ingredient's identifier code
	 * @return databaseId Identifier code of the ingredient 
	 */
	public String getDatabaseId() {
		return databaseId;
	}
	
	/**
	 * Sets the ingredient's identifier code
	 */
	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}
	
	/**
	 * Gets the whether the ingredient complies with the dietary requirements
	 * @return safeFor ???
	 */
	public Map<DietaryRequirement, Boolean> getSafeFor() {
		return safeFor;
	}
	
	/**
	 * Sets whether the ingredient complies with the dietary requirements
	 */
	public void setSafeFor(Map<DietaryRequirement, Boolean> safeFor) {
		this.safeFor = safeFor;
	}
	
	/**
	 * Gets the current stock available at the kitchen
	 * @return kitchenStock current stock available at the kitchen
	 */
	public int getKitchenStock() {
		return kitchenStock;
	}

	/**
	 * Sets the current stock available at the kitchen
	 */
	public void setKitchenStock(int kitchenStock) {
		this.kitchenStock = kitchenStock;
	}
	
	/**
	 * Gets the current stock available at the truck
	 * @return truckStock current stock available at the truck
	 */
	public int getTruckStock() {
		return truckStock;
	}
	
	/**
	 * Sets the current stock available at the truck
	 */
	public void setTruckStock(int truckStock) {
		this.truckStock = truckStock;
	}
}

