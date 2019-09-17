package foodstart.model;

/**
 * Enumerator of Unit of food/ingredients
 *
 * @author Alex Hobson on 22/08/2019
 */
public enum Unit {
	MILLILITRES("ml"), GRAMS("g"), UNITS("count");

	/**
	 * Locally stored dbName
	 */
	private final String dbName;

	/**
	 * Constructor for Unit
	 *
	 * @param dbName The name as it should appear in the database (eg XML)
	 */
	Unit(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * Matches a given string to a unit
	 *
	 * @param string The string to match
	 * @return The unit it matched to, or null if it didn't match
	 */
	public static Unit matchUnit(String string) {
		for (Unit unit : values()) {
			if (unit.getDBName().equalsIgnoreCase(string)) {
				return unit;
			}
		}
		return null;
	}

	/**
	 * Gets the name of the unit as it should appear in the database
	 *
	 * @return Database name of the unit
	 */
	public String getDBName() {
		return this.dbName;
	}
}
