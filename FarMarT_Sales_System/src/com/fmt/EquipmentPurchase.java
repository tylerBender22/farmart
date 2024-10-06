package com.fmt;

public class EquipmentPurchase implements Purchase {
	private Equipment item;
	private double price;

	public EquipmentPurchase(Equipment item, double price) {
		super();
		this.item = item;
		this.price = price;
	}
	/**
	 * getPrice method for EquipmentPurchase sub-class
	 * 
	 * @return &lt;double> price
	 */
	public double getPrice() {
		return this.price;
	}

	@Override
	public Item getItem() {
		return this.item;
	}

	@Override
	public double getTotal() {
		return (this.price - this.getTaxes());
	}

	@Override
	public double getTaxes() {
		return  (this.price * 0.0);
	}

}
