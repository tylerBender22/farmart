package com.fmt;

/**
 * The Equipment sub-class stores a(n) model for a given Service.<br>
 * Its super-class is {@link com.fmt.Item Item}.
 * 
 *
 */

public class Equipment extends Item {
	private String model;

	/**
	 * Constructor for the {@link com.fmt.Equipment Equipment} sub-class.
	 * 
	 * @param code
	 * @param descriptor
	 * @param model
	 */
	public Equipment(String code, String name, String model) {
		super(code, name);
		this.model = model;
	}

	/**
	 * getModel method for Equipment sub-class
	 * 
	 * @return &lt;String> model
	 */
	public String getModel() {
		return this.model;
	}

	@Override
	public String getType() {
		return "Equipment";
	}

}
