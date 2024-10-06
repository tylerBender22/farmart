package com.fmt;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * Generates three reports based on the input csv files stored in the "/data" directory
 * 
 * 
 */

public class InvoiceReport {
	
	private static Comparator<Invoice> compareTotal = new Comparator<>() {
		@Override
		public int compare(Invoice i1, Invoice i2) {
			return Double.compare(i1.getGrandTotal(), i2.getGrandTotal());
		}
	};

	private static Comparator<Invoice> compareName = new Comparator<Invoice>() {
		@Override
		public int compare(Invoice i1, Invoice i2) {
			return (int) i1.getCustomer().getFullName().compareTo(i2.getCustomer().getFullName());
		};

	};

	private static Comparator<Invoice> compareStore = new Comparator<Invoice>() {
		@Override
		public int compare(Invoice i1, Invoice i2) {
			int i = i1.getStore().getCode().compareTo(i2.getStore().getCode());

			if (i == 0)
				i = i1.getSalePerson().getFullName().compareTo(i2.getSalePerson().getFullName());

			return i;
		};

	};
	
	
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	public static void main(String[] args) {
		List<Person> persons = LoadData.loadPersonsFromCSV();
		List<Item> items = LoadData.loadItemsFromCSV();
		List<Store> stores = LoadData.loadStoresFromCSV(persons);
		List<Invoice> invoices = LoadData.loadInvoiceDataFromCSV(stores, persons, items);

		// by first/last name
		SortedArray<Invoice> sortedInvoices = new SortedArray<>(compareName);
		SortedArray.addInvoices(sortedInvoices, invoices);
		System.out.println("+-------------------------------------------------------------------------+\r\n"
				+ "| Sales by Customer                                                       |\r\n"
				+ "+-------------------------------------------------------------------------+");
		SortedArray.printInvoices(sortedInvoices);

		// by total
		sortedInvoices = new SortedArray<>(compareTotal);
		SortedArray.addInvoices(sortedInvoices, invoices);
		System.out.println("+-------------------------------------------------------------------------+\r\n"
				+ "| Sales by Total                                                          |\r\n"
				+ "+-------------------------------------------------------------------------+");
		SortedArray.printInvoices(sortedInvoices);

		// by store
		sortedInvoices = new SortedArray<>(compareStore);
		SortedArray.addInvoices(sortedInvoices, invoices);
		System.out.println("+-------------------------------------------------------------------------+\r\n"
				+ "| Sales by Store                                                          |\r\n"
				+ "+-------------------------------------------------------------------------+");
		SortedArray.printInvoices(sortedInvoices);	
	}
}
