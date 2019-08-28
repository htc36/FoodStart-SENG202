package foodstart.model.stock;


import foodstart.model.PhoneType;

/**
 * Stores relevant information and methods about a supplier
 */

public class Supplier
{
	/**
	 * UID for supplier
	 */
	private String databaseId;

	/**
	 * Name of the supplier
	 */
	private String supplierName;

	/**
	 * Supplier contact phone number
	 */
	private String phoneNumber;

	/**
	 * Supplier contact phone type
	 */
	private PhoneType phoneType;

	/**
	 * Supplier contact email address
	 */
	private String email;

	/**
	 * Supplier website URL
	 */
	private String url;

	/**
	 * Supplier physical address
	 */
	private String address;

	/**
	 * Constructs an instance of a supplier
	 * @param databaseId the UID of the supplier
	 * @param supplierName name of the supplier
	 * @param phoneNumber supplier contact phone number
	 * @param phoneType supplier contact phone type
	 * @param email supplier email address
	 * @param url supplier website URL
	 * @param address supplier physical address
	 */
	public Supplier(String databaseId, String supplierName, String phoneNumber, PhoneType phoneType, String email, String url, String address) {
		this.databaseId = databaseId;
		this.supplierName = supplierName;
		this.phoneNumber = phoneNumber;
		this.phoneType = phoneType;
		this.email = email;
		this.url = url;
		this.address = address;
	}

	/**
	 * Returns the supplier UID
	 * @return the supplier UID
	 */
	public String getDatabaseId() {
		return databaseId;
	}

	/**
	 * Sets the supplier UID
	 * @param databaseId the supplier UID
	 */
	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}

	/**
	 * Returns the name of the supplier
	 * @return the name of the supplier
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * Sets the name of the supplier
	 * @param supplierName the name of the supplier
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * Returns the supplier contact phone number
	 * @return the supplier contact phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the supplier contact phone number
	 * @param phoneNumber the supplier contact phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Returns the supplier contact phone type
	 * @return the supplier contact phone type
	 */
	public PhoneType getPhoneType() {
		return phoneType;
	}

	/**
	 * Set the supplier contact phone type
	 * @param phoneType the supplier contact phone type
	 */
	public void setPhoneType(PhoneType phoneType) {
		this.phoneType = phoneType;
	}

	/**
	 * Returns the supplier contact email address
	 * @return the supplier contact email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the supplier contact email address
	 * @param email the supplier contact email address
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the supplier website URL
	 * @return the supplier website URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the supplier website URL
	 * @param url the supplier website URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Returns the physical address of the supplier
	 * @return the physical address of the supplier
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the physical address of the supplier
	 * @param address the physical address of the supplier
	 */
	public void setAddress(String address) {
		this.address = address;
	}
}
