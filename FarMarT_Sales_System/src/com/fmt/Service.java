package com.fmt;
/**
 * The Service sub-class stores a(n) hourlyRate for a given Service.<br>
 * Its super-class is {@link com.fmt.Item Item}.
 * 
 *
 */

public class Service extends Item{
	private double hourlyRate;
	/**
	 * Constructor for the {@link com.fmt.Service Service} sub-class.
	 * @param code
	 * @param descriptor
	 * @param hourlyRate
	 */
	public Service(String code, String name, double hourlyRate) {
		super(code, name);
		this.hourlyRate = hourlyRate;
	}

	/**
	 * getHourlyRate method for Service sub-class
	 * 
	 * @return &lt;double> hourlyRate
	 */
	public double getHourlyRate() {
		return this.hourlyRate;
	}
	@Override
	public String getType() {
		return "Service";
	}

}
