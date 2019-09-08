package foodstart.model;

/**
 * The possible types of payment methods
 */
public enum PaymentMethod
{
	CASH("Cash"), EFTPOS("EFTPOS");
	
	/**
	 * The nice human readable name for this
	 */
	private final String niceName;
	
	/**
	 * Constructs a PaymentMethod
	 * @param niceName Nice human name for this
	 */
	private PaymentMethod(String niceName) {
		this.niceName = niceName;
	}
	
	/**
	 * Get the nice human readable name for this Payment Method
	 * @return Human friendly name for this payment method
	 */
	public String getNiceName() {
		return this.niceName;
	}
	
	/**
	 * Match the nice name to a payment method
	 * @param niceName Nice name to match
	 * @return PaymentMethod it represents, or null
	 */
	public static PaymentMethod matchNiceName(String niceName) {
		for (PaymentMethod method : values()) {
			if (method.niceName.equals(niceName)) {
				return method;
			}
		}
		return null;
	}
}
