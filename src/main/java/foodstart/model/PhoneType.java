package foodstart.model;

/**
 * The possible types of phone numbers
 */
public enum PhoneType {
	/**
	 * Cellphone
	 */
	MOBILE("Mobile"),
	/**
	 * Work/Company phone
	 */
	WORK("Work"),
	/**
	 * Personal phone
	 */
	HOME("Home");

	/**
	 * The name of the phone type
	 */
	private String name;

	/**
	 * Constructs an instance of a phone type
	 * @param name the name of the phone type
	 */
	PhoneType(String name) {
		this.name = name;
	}

	/**
	 * Matches a given string to a phone type
	 *
	 * @param string The string to match
	 * @return The type it matched to, or null if it didn't match
	 */
	public static PhoneType matchType(String string) {
		for (PhoneType type : values()) {
			if (type.name().equalsIgnoreCase(string)) {
				return type;
			}
		}
		return null;
	}
}
