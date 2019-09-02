package foodstart.manager.stock;
import java.util.HashSet;
import java.util.Set;

import foodstart.model.PhoneType;
import foodstart.model.stock.Supplier;


/**
 * Acts as a controller, storing and managing the suppliers in the model
 */

public class SupplierManager
{
	/**
	 * The set of suppliers being modeled
	 */
	private Set<Supplier> suppliers;

	/**
	 * Constructs an instance of a supplier manager
	 */
	public SupplierManager(){
		this.suppliers = new HashSet<Supplier>();
	}

	/**
	 * Adds a pre-constructed supplier to the set of suppliers
	 * @param supplier the supplier to add
	 */
	public void addSupplier(Supplier supplier) {
		this.suppliers.add(supplier);
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
	public void addSupplier(String databaseId, String supplierName, String phoneNumber, PhoneType phoneType, String email, String url, String address) {
		Supplier supplier = new Supplier(databaseId, supplierName, phoneNumber, phoneType, email, url, address);
		this.suppliers.add(supplier);
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
	public Set<Supplier> getSuppliers() {
		return this.suppliers;
	}

	/**
	 * Gets a supplier from the supplier set based on its id
	 * @param id the id of the supplier to get
	 * @return the supplier matching the given id, or null if it was not found
	 */
	public Supplier getSupplier(String id) {
		for (Supplier supplier : this.suppliers) {
			if (supplier.getDatabaseId().equals(id)) {
				return supplier;
			}
		}
		return null;
	}

}

