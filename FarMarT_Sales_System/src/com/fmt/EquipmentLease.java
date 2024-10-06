package com.fmt;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EquipmentLease implements Purchase{
	private Equipment item;
	private double monthlyCost;
	private LocalDate leaseStart;
	private LocalDate leaseEnd;
	
	public EquipmentLease(Equipment item, double monthlyCost, LocalDate leaseStart, LocalDate leaseEnd) {
		super();
		this.item = item;
		this.monthlyCost = monthlyCost;
		this.leaseStart = leaseStart;
		this.leaseEnd = leaseEnd;
	}
	/**
	 * getMonthlyCost method for EquipmentLease sub-class
	 * 
	 * @return &lt;LocalDate> monthlyCost
	 */
	public double getMonthlyCost() {
		return this.monthlyCost;
	}

	/**
	 * getLeaseStart method for EquipmentLease sub-class
	 * 
	 * @return &lt;LocalDate> leaseStart
	 */
	public LocalDate getLeaseStart() {
		return this.leaseStart;
	}

	/**
	 * getLeaseEnd method for EquipmentLease sub-class
	 * 
	 * @return &lt;LocalDate> leaseEnd
	 */
	public LocalDate getLeaseEnd() {
		return this.leaseEnd;
	}


	@Override
	public Item getItem() {
		return this.item;
	}

	@Override
	public double getTotal() {
		int days = (int) ChronoUnit.DAYS.between(leaseStart, leaseEnd);
		return (this.monthlyCost * (days/30));
	}

	@Override
	public double getTaxes() {
		return 500.00;
	}
}
