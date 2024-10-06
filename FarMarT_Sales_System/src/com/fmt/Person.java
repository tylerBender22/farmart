package com.fmt;

import java.util.List;

/**
 * The Person class stores 4 fields used to identify a person, code, name,
 * address, and emails.
 * 
 * @param code
 * @param lastName
 * @param firstName
 * @param address
 * @param email
 */

public class Person {
	private String code;
	private String lastName;
	private String firstName;
	private Address address;
	private List<String> emails;

	/**
	 * @param code
	 * @param lastName
	 * @param firstName
	 * @param address
	 * @param emails
	 */
	public Person(String code, String lastName, String firstName, Address address, List<String> emails) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emails = emails;
	}
	/**
	 * getCode method for Person class
	 * 
	 * @return &lt;String> code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * getFirstName method for Person class
	 * 
	 * @return &lt;String> firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * getLastName method for Person class
	 * 
	 * @return  &lt;String> lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * getAddress method for Person class
	 * 
	 * @return &lt;String> address
	 */
	public Address getAddress() {
		return address;
	}
	
	public String getFullName()
	{
		return this.lastName + ", " + this.firstName;
	}
	
	
	/**
	 * getEmails method for Person class
	 * 
	 * @return List&lt;String> emails
	 */
	public List<String> getEmails() {
		return this.emails;
	}
}
