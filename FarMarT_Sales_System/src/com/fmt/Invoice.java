package com.fmt;

import java.time.LocalDate;
import java.util.List;

public class Invoice {
	private String id;
	private Store store;
	private Person customer;
	private Person salePerson;
	private LocalDate date;
	private List<Purchase> sales;
	
	

	public Invoice(String id, Store store, Person customer, Person salePerson, LocalDate date, List<Purchase> sales) {
		super();
		this.id = id;
		this.store = store;
		this.customer = customer;
		this.salePerson = salePerson;
		this.date = date;
		this.sales = sales;
	}
	/**
	 * getId method for Invoice class
	 * 
	 * @return &lt;String> id
	 */
	public String getId() {
		return this.id;
	}
	/**
	 * getStore method for Invoice class
	 * 
	 * @return &lt;Store> store
	 */
	public Store getStore() {
		return this.store;
	}
	/**
	 * getCustomer method for Invoice class
	 * 
	 * @return &lt;Person> customer
	 */
	public Person getCustomer() {
		return this.customer;
	}
	/**
	 * getSalePerson method for Invoice class
	 * 
	 * @return &lt;Person> salePerson
	 */
	public Person getSalePerson() {
		return this.salePerson;
	}
	/**
	 * getDate method for Invoice class
	 * 
	 * @return &lt;LocalDate> date
	 */
	public LocalDate getDate() {
		return this.date;
	}
	/**
	 * getSales method for Invoice class
	 * 
	 * @return &lt;List<Purchase>> sales
	 */
	public List<Purchase> getSales() {
		return this.sales;
	}
	/**
	 * getSubTotal method for Invoice class
	 * 
	 * @return &lt;double> subTotal
	 */
	public double getSubTotal() {
		double subTotal = 0.0;
		for(Purchase p : this.sales)
		{
			subTotal += p.getTotal();
		}
		return subTotal;
	}
	/**
	 * getTotalTax method for Invoice class
	 * 
	 * @return &lt;double> totalTax
	 */
	public double getTotalTax() {
		double totalTax = 0.0;
		for(Purchase p : this.sales)
		{
			totalTax += p.getTaxes();
		}
		return totalTax;
	}
	/**
	 * getGrandTotal method for Invoice class
	 * 
	 * @return &lt;double> grandTotal
	 */
	public double getGrandTotal() {
		return (this.getSubTotal() - this.getTotalTax());
	}
	
	
}
