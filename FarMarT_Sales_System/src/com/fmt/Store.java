package com.fmt;

/**
 * The Store class stores a(n) code for the store, a manager stored as a
 * {@link com.fmt.Person Person}, and an {@link com.fmt.Address Address}<br>
 * 
 * 
 *
 */

public class Store {
	private String code;
	private Person manager;
	private Address address;

	/**
	 * Constructor for the {@link com.fmt.Store Store} class.
	 * 
	 * @param code
	 * @param descriptor
	 */
	public Store(String code, Person manager, Address address) {
		super();
		this.code = code;
		this.manager = manager;
		this.address = address;
	}

	/**
	 * getCode method for Store class
	 * 
	 * @return &lt;String> code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * getManager method for Store class
	 * 
	 * @return &lt;String> manager
	 */
	public Person getManager() {
		return this.manager;
	}

	/**
	 * getAddress method for Store class
	 * 
	 * @return &lt;Person> address
	 */
	public Address getAddress() {
		return this.address;
	}

}
