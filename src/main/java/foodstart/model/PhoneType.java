package foodstart.model;

/**
 * The possible types of phone numbers
 */
public enum PhoneType {
	MOBILE("Mobile"), WORK("Work"), HOME("Home");

	private String name;

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
