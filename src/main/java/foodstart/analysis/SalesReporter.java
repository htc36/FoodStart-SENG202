package foodstart.analysis;

import foodstart.manager.Managers;
import foodstart.model.Constants;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates a report based on the sales modeled by the order manager
 */
public class SalesReporter {

	/**
	 * A map of all recipes that have been sold to the amount sold
	 */
	private Map<Recipe, Integer> sales;

	/**
	 * Constructs an instance of a sales reporter
	 */
	public SalesReporter() {
		sales = new HashMap<Recipe, Integer>();
	}

	/**
	 * Collects the data for the sales report
	 */
	public void collectData() {
		for (Order order : Managers.getOrderManager().getOrderSet()) {
			Map<Recipe, Integer> recipes = order.getItems();
			for (Recipe recipe : recipes.keySet()) {
				sales.put(recipe, recipes.get(recipe) + sales.getOrDefault(recipe, 0));
			}
		}
	}

	/**
	 * Writes the data that has been collected to a CSV file
	 * @return true if write was successful; false otherwise
	 */
	public boolean writeData() {
		try {
			File directory = new File(Constants.persistencePath);
			String filename = directory.getAbsolutePath() + File.separator + String.format("sales_report_%s.csv", LocalDateTime.now().toLocalDate().toString());
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			for (Recipe recipe : sales.keySet()) {
				writer.println(String.format("%s,%d", recipe.getDisplayName(), sales.get(recipe)));
			}
			writer.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (UnsupportedEncodingException e) {
			return false;
		}
	}
}
