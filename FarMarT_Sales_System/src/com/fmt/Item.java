package com.fmt;

/**
 * The Item super-class stores a(n) code and descriptor for a given item.<br>
 * Its sub-classes include {@link com.fmt.Equipment#Equipment Equipment},
 * {@link com.fmt.Product#Product Product}, and {@link com.fmt.Service#Service
 * Service}.
 * 
 *
 */
public abstract class Item {
	private String code;
	private String name;

	/**
	 * Constructor for the {@link com.fmt.Item Item} class.
	 * 
	 * @param code
	 * @param descriptor
	 */
	public Item(String code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * getCode method for Item class
	 * 
	 * @return &lt;String> code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * getDescriptor method for Item class
	 * 
	 * @return &lt;String> name
	 */
	public String getDescriptor() {
		return this.name;
	}

	/**
	 * Returns the "Type" of item for the specified sub-class as a String. <br>
	 * {@link com.fmt.Equipment#Equipment Equipment}, {@link com.fmt.Product#Product
	 * Product}, or {@link com.fmt.Service#Service Service}.
	 * 
	 * @implNote abstract
	 * @return &lt;String> type
	 */
	public abstract String getType();
}
