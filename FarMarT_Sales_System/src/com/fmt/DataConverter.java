package com.fmt;

import java.util.List;

public class DataConverter {
	private static final String PERSONS_PATH = "data/Persons.json";
	private static final String STORES_PATH = "data/Stores.json";
	private static final String ITEMS_PATH = "data/Items.json";

	/**
	 * Main method that reads in the three csv file for Persons, Stores, and Items
	 * respectivly.<br>
	 * Serializes the data and returns JSON files for each of the data types.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<Person> persons = LoadData.loadPersonsFromDataBase();
		WriteFile.ToJSON(persons, PERSONS_PATH);
		List<Item> items = LoadData.loadItemsFromDataBase();
		WriteFile.ToJSON(items, ITEMS_PATH);
		List<Store> stores = LoadData.loadStoresFromDataBase(persons);
		WriteFile.ToJSON(stores, STORES_PATH);
	}

}
