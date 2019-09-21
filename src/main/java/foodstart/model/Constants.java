package foodstart.model;

import java.io.File;

public class Constants {

	public static final String persistencePath = System.getProperty("user.home") + File.separator + "foodstart";
	
	public static final File[] importOrder = new File[]{
			new File(persistencePath, "ingredients.xml"),
			new File(persistencePath, "recipes.xml"),
			new File(persistencePath, "menu.xml"),
			new File(persistencePath, "sales_log.xml"),
			new File(persistencePath, "suppliers.xml")
	};
}
