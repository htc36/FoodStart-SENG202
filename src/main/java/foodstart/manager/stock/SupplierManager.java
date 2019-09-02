package foodstart.manager.stock;
import foodstart.model.PhoneType;
import foodstart.model.stock.Supplier;

import java.util.HashMap;
import java.util.Map;


/**
 * Acts as a controller, storing and managing the suppliers in the model
 */

public class SupplierManager
{
	/**
	 * The set of suppliers being modeled
	 */
	private Map<Integer, Supplier> suppliers;

	/**
	 * Constructs an instance of a supplier manager
	 */
	public SupplierManager(){
		this.suppliers = new HashMap<Integer, Supplier>();
	}

	/**
	 * Adds a pre-constructed supplier to the set of suppliers
	 * @param supplier the supplier to add
	 */
	public void addSupplier(Supplier supplier) {
		this.suppliers.put(supplier.getDatabaseId(), supplier);
	}

	/**
	 * Constructs and adds a new supplier to the set of suppliers based
	 * @param databaseId the UID of the supplier
	 * @param supplierName name of the supplier
	 * @param phoneNumber supplier contact phone number
	 * @param phoneType supplier contact phone type
	 * @param email supplier email address
	 * @param url supplier website URL
	 * @param address supplier physical address
	 */
	public void addSupplier(int databaseId, String supplierName, String phoneNumber, PhoneType phoneType, String email, String url, String address) {
		Supplier supplier = new Supplier(databaseId, supplierName, phoneNumber, phoneType, email, url, address);
		this.suppliers.put(databaseId, supplier);
	}

	/**
	 * Removes a supplier from the set of suppliers
	 * @param supplier the supplier to remove
	 */
	public void removeSupplier(Supplier supplier) {
		this.suppliers.remove(supplier);
	}

	/**
	 * Returns the set of suppliers
	 * @return the set of suppliers
	 */
	public Map<Integer, Supplier> getSuppliers() {
		return this.suppliers;
	}

	/**
	 * Gets a supplier from the supplier set based on its id
	 * @param id the id of the supplier to get
	 * @return the supplier matching the given id, or null if it was not found
	 */
	public Supplier getSupplier(int id) {
		return this.suppliers.get(id);
	}

}

