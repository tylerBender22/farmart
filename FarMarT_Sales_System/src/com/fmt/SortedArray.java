package com.fmt;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class SortedArray<T> implements Iterable<T> {
	private static final DecimalFormat df = new DecimalFormat("0.00");
	private T[] array;
	private int size;
	private Comparator<T> comp;

	/**
	 * Constructor for the generalized list ADT, SortedArray.
	 * @param comp
	 */
	/**
	 * @param comp
	 */
	public SortedArray(Comparator<T> comp) {
		array = (T[]) new Object[2];
		size = 0;
		this.comp = comp;
	}

	/**
	 * Adds an given Type T to the Sorted Array in the correct location depected by the comparator.
	 * @param t
	 */
	public void add(T t) {
		if (array.length < (size + 1)) {
			array = Arrays.copyOf(array, size + 1);
		}

		if (size == 0) {
			array[0] = t;
		} else if (comp.compare(t, array[size - 1]) >= 0) {
			array[size] = t;
		} else {
			int l = 0;
			int r = size - 1;

			int i = 0;

			while (l <= r) {
				i = (l + r) / 2;

				int result = comp.compare(t, array[i]);

				if (result == 0)
					break;
				else if (result < 0)
					r = i - 1;
				else
					l = i + 1;
			}

			if (comp.compare(t, array[i]) > 0)
				i++;
			for (int j = size - 1; j >= i; j--) {
				array[j + 1] = array[j];
			}

			array[i] = t;
		}

		size++;
	}

	/**
	 * Remove an element by searching for a match (t).
	 * 
	 * @param t
	 */
	public void remove(T t) {
		int index = 0;
		for (T type : array) {
			if (type.equals(t)) {
				removeAtIndex(index);
				return;
			}
			index++;
		}
	}

	/**
	 * Remove an element at index (i).
	 * 
	 * @param i
	 */
	public void removeAtIndex(int i) {
		if (i >= size || i < 0)
			throw new IndexOutOfBoundsException(String.format("index %d out of bounds 0-%d", i, size));
		for (int j = i; j < size - 1; j++) {
			array[j] = array[j + 1];
		}
		array = Arrays.copyOf(array, size - 1);
		this.size--;
	}

	/**
	 * Returns the given SortedArray.
	 * 
	 * @param index
	 * @return
	 */
	public T get(int index) {
		return this.array[index];
	}
	/**
	 * Returns the size of the given SortedArray.
	 * 
	 * @param
	 * @return
	 */
	public int size() {
		return this.size;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private int index = 0;

			@Override
			public boolean hasNext() {
				return (this.index < size);
			}

			@Override
			public T next() {
				return array[index++];
			}

		};
	}

	@Override
	public String toString() {
		return Arrays.toString(this.array);
	}

	
	/**
	 * Returns the array associated with the given SortedArray.
	 * @return
	 */
	public T[] toArray() {
		return this.array;
	}

	/**
	 * Prints all invoices in a given SortedArray&ltInvoice&gt
	 * @param inv
	 */
	public static void printInvoices(SortedArray<Invoice> inv) {
		for (Invoice s : inv) {
			System.out.println(s.getId() + " \t" + s.getStore().getCode() + " \t" + s.getCustomer().getFullName()
					+ " \t$" + df.format(s.getGrandTotal()));
		}
	}

	
	/**
	 * Adds an List&ltInvoice&gt of invoices to an SortedArray&ltInvoice&gt
	 * @param inv
	 */
	public static void addInvoices(SortedArray<Invoice> inv, List<Invoice> invs) {
		for (Invoice i : invs) {
			inv.add(i);
		}
	}
}