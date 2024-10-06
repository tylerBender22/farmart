package com.fmt;

public class ProductPurchase implements Purchase{
	
	private Product item;
	private double quantity;
	
	
	public ProductPurchase(Product item, double quantity) {
		super();
		this.item = item;
		this.quantity = quantity;
	}


	/**
	 * getQuantity method for Product Purchse Sub-class
	 * @return this.quantity
	 */
	public double getQuantity() {
		return this.quantity;
	}


	@Override
	public Item getItem() {
		return this.item;
	}


	@Override
	public double getTotal() {
		return (this.item.getUnitPrice() * this.quantity);
	}


	@Override
	public double getTaxes() {
		return (this.getTotal() * 0.0715);
	}
	
	
}
