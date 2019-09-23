package foodstart.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Models the possible persistence files that the system can import and export
 */
public enum DataFileType {
	/**
	 * Extensible Markup Language
	 */
	XML("XML files", "*.xml");

	/**
	 * The name/description of the file type
	 */
	private String description;

	/**
	 * The possible extensions for the file type
	 */
	private List<String> extensions;

	/**
	 * Constructs a data file type with a given description and extensions
	 * @param description the description of the file type
	 * @param extensions the possible extension of the file type
	 */
	DataFileType(String description, String... extensions) {
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

	/**
	 * Returns the first DataFileType with the given extensions
	 * @param extensions the extension of the DataFileType to get
	 * @return the first DataFileType with the given extensions
	 */
	public static DataFileType getFromExtensions(List<String> extensions) {
		for (DataFileType type: DataFileType.values()) {
			if (extensions.equals(type.getExtensions())) {
				return type;
			}
		}
		return null;
	}
}
