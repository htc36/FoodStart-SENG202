package foodstart.model;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */
public enum DietaryRequirement
{
	VEGAN("vegan"), VEGETARIAN("vegetarian"), GLUTEN_FREE("gluten_free"), NUT_ALLERGY("nut_free"), LACTOSE_INTOLERENT("dairy_free");
	
	/**
	 * Locally stored dbName
	 */
	private final String dbName;
	
	/**
	 * Constructor for Unit
	 * @param dbName The name as it should appear in the database (eg XML)
	 */
	private DietaryRequirement(String dbName) {
		this.dbName = dbName;
	}
	
	/**
	 * Gets the name of the unit as it should appear in the database
	 * @return Database name of the unit
	 */
	private String getDBName() {
		return this.dbName;
	}
	
	/**
	 * Matches a given string to a unit
	 * @param string The string to match
	 * @return The unit it matched to, or null if it didn't match
	 */
	public static DietaryRequirement matchDietaryRequirement(String string) {
		for (DietaryRequirement requirement : values()) {
			if (requirement.getDBName().equalsIgnoreCase(string)) {
				return requirement;
			}
		}
		return null;
	}
}
