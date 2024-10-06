package com.fmt;

/**
 * The Address class stores five fields that denote an address,<br>
 * street, city, state, zip, and country
 * 
 * @param street
 * @param city
 * @param state
 * @param zip
 * @param country
 */

public class Address {
	private String street;
	private String city;

	private String state;
	private String zip;
	private String country;

	/**
	 * Constructor for Address class
	 * 
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @return Address
	 */
	public Address(String street, String city, String state, String zip, String country) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}

	/**
	 * getStreet method for Address class
	 * 
	 * @return &lt;String> street
	 */
	public String getStreet() {
		return this.street;
	}

	/**
	 * getCity method for Address class
	 * 
	 * @return &lt;String> city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * getState method for Address class
	 * 
	 * @return &lt;String state
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * getZip method for Address class
	 * 
	 * @return &lt;String> zip
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * getCountry method for Address class
	 * 
	 * @return &lt;String> country
	 */
	public String getCountry() {
		return this.country;
	}
}
