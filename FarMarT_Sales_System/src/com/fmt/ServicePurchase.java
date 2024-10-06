package com.fmt;

public class ServicePurchase implements Purchase{
	private Service item;
	private double hoursBilled;
	public ServicePurchase(Service item, double hoursBilled) {
		super();
		this.item = item;
		this.hoursBilled = hoursBilled;
	}
	/**
	 * getHoursBilled method for ServicePurchase sub-class
	 * 
	 * @return &lt;double> HoursBilled
	 */
	public double getHoursBilled() {
		return hoursBilled;
	}
	@Override
	public Item getItem() {
		return this.item;
	}
	@Override
	public double getTotal() {
		return (this.item.getHourlyRate() * this.hoursBilled);
	}
	@Override
	public double getTaxes() {
		return (this.getTotal() * 0.0345);
	}
	
	
}
