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
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private Unit unit;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
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
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private int kitchenStock;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private int truckStock;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Ingredient(Unit unit, String name, String databaseId, Map<DietaryRequirement, Boolean> safeFor, int kitchenStock, int truckStock) {
		this.unit = unit;
		this.name = name;
		this.databaseId = databaseId;
		this.safeFor = safeFor;
		this.kitchenStock = kitchenStock;
		this.truckStock = truckStock;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}

	public Map<DietaryRequirement, Boolean> getSafeFor() {
		return safeFor;
	}

	public void setSafeFor(Map<DietaryRequirement, Boolean> safeFor) {
		this.safeFor = safeFor;
	}

	public int getKitchenStock() {
		return kitchenStock;
	}

	public void setKitchenStock(int kitchenStock) {
		this.kitchenStock = kitchenStock;
	}

	public int getTruckStock() {
		return truckStock;
	}

	public void setTruckStock(int truckStock) {
		this.truckStock = truckStock;
	}
}

