package com.fmt;

/**
 * The Product sub-class stores a(n) unitPrice for a given Product.<br>
 * Its super-class is {@link com.fmt.Item Item}.
 * 
 *
 */

public class Product extends Item {
	private String unit;
	private double unitPrice;

	/**
	 * Constructor for the {@link com.fmt.Product Product} sub-class.
	 * 
	 * @param code
	 * @param descriptor
	 * @param untiPrice
	 */
	public Product(String code, String name, String unit, double unitPrice) {
		super(code, name);
		this.unit = unit;
		this.unitPrice = unitPrice;
	}

	/**
	 * getUnit method for Product sub-class
	 * 
	 * @return &lt;String> unit
	 */
	public String getUnit() {
		return this.unit;
	}

	/**
	 * getUnitPrice method for Product sub-class
	 * 
	 * @return &lt;double> unitPrice
	 */
	public double getUnitPrice() {
		return this.unitPrice;
	}

	@Override
	public String getType() {
		return "Product";
	}

}
