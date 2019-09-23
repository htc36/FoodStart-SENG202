package foodstart.model;

import java.io.File;

/**
 * Contains constants for system
 */
public class Constants {

	/**
	 * The path to the default persistence files
	 */
	public static final String persistencePath = System.getProperty("user.home") + File.separator + "foodstart";

	/**
	 * An array of the default files to import in the required import order
	 */
	public static final File[] importOrder = new File[]{
			new File(persistencePath, "ingredients.xml"),
			new File(persistencePath, "recipes.xml"),
			new File(persistencePath, "menu.xml"),
			new File(persistencePath, "sales_log.xml"),
			new File(persistencePath, "suppliers.xml")
	};
}
