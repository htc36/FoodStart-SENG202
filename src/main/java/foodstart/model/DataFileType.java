package foodstart.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum DataFileType {
	XML("XML files", "*.xml");

	private String description;
	private List<String> extensions;

	/**
	 * Constructs a data file type with a given description and extensions
	 * @param description the description of the file type
	 * @param extensions the possible extension of the file type
	 */
	private DataFileType(String description, String... extensions) {
		this.description = description;
		this.extensions = Arrays.asList(extensions);
	}

	/**
	 * Returns the description of the file type
	 * @return the description of the file type
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the possible extensions of the file type
	 * @return the possible extensions of the file type
	 */
	public List<String> getExtensions() {
		return extensions;
	}

	public static DataFileType getFromExtensions(List<String> extensions) {
		for (DataFileType type: DataFileType.values()) {
			if (extensions.equals(type.getExtensions())) {
				return type;
			}
		}
		return null;
	}
}
