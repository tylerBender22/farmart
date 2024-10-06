package com.fmt;

/**
 * Purchase interface which holds abstract declarations for the methods, getItem, getTotal, and getTaxes.
 *
 */
public interface Purchase {
	
	/**
	 * Returns the item included in the specified Purchase interface sub-class
	 * @return Item
	 */
	public Item getItem();
	/**
	 * returns the total of the specified purchase
	 * @return total
	 */
	public double getTotal();
	/**
	 * returns the taxes of the specified purchase
	 * @return
	 */
	public double getTaxes();
}
