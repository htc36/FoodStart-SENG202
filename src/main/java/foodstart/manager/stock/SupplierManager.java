package foodstart.manager.stock;

import foodstart.model.PhoneType;
import foodstart.model.stock.Supplier;

import java.util.*;


/**
 * Acts as a controller, storing and managing the suppliers in the model
 */
public class SupplierManager {
	/**
	 * The map of suppliers being modeled
	 */
	private Map<Integer, Supplier> suppliers;

	/**
	 * Constructs an instance of a supplier manager
	 */
	public SupplierManager() {
		this.suppliers = new HashMap<Integer, Supplier>();
	}

	/**
	 * Constructs and adds a new supplier to the map of suppliers based
	 *
	 * @param databaseId   the UID of the supplier
	 * @param supplierName name of the supplier
	 * @param phoneNumber  supplier contact phone number
	 * @param phoneType    supplier contact phone type
	 * @param email        supplier email address
	 * @param url          supplier website URL
	 * @param address      supplier physical address
	 */
	public void addSupplier(int databaseId, String supplierName, String phoneNumber, PhoneType phoneType, String email, String url, String address) {
		Supplier supplier = new Supplier(databaseId, supplierName, phoneNumber, phoneType, email, url, address);
		this.suppliers.put(databaseId, supplier);
	}

	/**
	 * Removes a supplier from the map of suppliers
	 *
	 * @param id the id of the supplier to remove
	 */
	public void removeSupplier(Integer id) {
		this.suppliers.remove(id);
	}

	/**
	 * Returns the map of suppliers
	 *
	 * @return the map of suppliers
	 */
	public Map<Integer, Supplier> getSuppliers() {
		return this.suppliers;
	}

	/**
	 * Gets a supplier from the supplier map based on its id
	 *
	 * @param id the id of the supplier to get
	 * @return the supplier matching the given id, or null if it was not found
	 */
	public Supplier getSupplier(int id) {
		return this.suppliers.get(id);
	}


	/**
	 * Returns the set of suppliers from the ids specified
	 *
	 * @param ids the ids of the suppliers to fetch
	 * @return the set of suppliers requested
	 */
	public Set<Supplier> getSuppliers(Collection<Integer> ids) {
		Set<Supplier> items = new HashSet<Supplier>();
		for (int id : ids) {
			Supplier item = this.suppliers.get(id);
			if (item != null) {
				items.add(item);
			}
		}
		return items;
	}

	/**
	 * Returns the set of all suppliers stored in the map
	 *
	 * @return the set of all suppliers stored in the map
	 */
	public Set<Supplier> getSupplierSet() {
		return new HashSet<Supplier>(this.suppliers.values());
	}

	/**
	 * Generates a new code for a new supplier
	 * @return the new supplier code
	 */
	public int generateNewCode() {
		return suppliers.keySet().size() == 0 ? 0 : Collections.max(suppliers.keySet()) + 1;
	}

}

