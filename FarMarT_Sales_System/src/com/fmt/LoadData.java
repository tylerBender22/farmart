package com.fmt;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoadData {
	public static final String STORES_FILE_PATH = "data/Stores.csv";
	public static final String PERSONS_FILE_PATH = "data/Persons.csv";
	public static final String ITEMS_FILE_PATH = "data/Items.csv";
	public static final String INVOICE_DATA_FILE_PATH = "data/Invoices.csv";
	public static final String INVOICE_ITEM_DATA_FILE_PATH = "data/InvoiceItems.csv";


	/**
	 * Creates a {@link java.util.List#List List} of {@link Person Person}'s from a
	 * csv file whos path is denoted STORES_FILE_PATH.
	 * 
	 * @param
	 * @return List&lt;Person>
	 */
	public static List<Person> loadPersonsFromCSV() {
		List<Person> people = new ArrayList<Person>();

		String line = null;

		try (Scanner s = new Scanner(new File(PERSONS_FILE_PATH))) {
			int numPeople = Integer.parseInt(s.nextLine());
			for (int i = 0; i < numPeople; i++) {
				line = s.nextLine();
				if (!line.trim().isEmpty()) {
					Person p = null;
					String[] tokens = line.split(",");
					String code = tokens[0];
					String lastName = tokens[1];
					String firstName = tokens[2];
					String street = tokens[3];
					String city = tokens[4];
					String state = tokens[5];
					String zip = tokens[6];
					String country = tokens[7];
					List<String> emails = new ArrayList<String>();
					for (int x = 8; x < tokens.length; x++) {
						emails.add(tokens[x]);
					}
					Address a = new Address(street, city, state, zip, country);
					p = new Person(code, lastName, firstName, a, emails);
					people.add(p);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error encountered on line " + line, e);
		}

		return people;
	}
	
	
	/**
	 * Creates a {@link java.util.List#List List} of {@link Person Person}'s from
	 * the defined database.
	 * 
	 * @param
	 * @return List&lt;Person>
	 */
	public static List<Person> loadPersonsFromDataBase() {
		List<Person> retPeople = new ArrayList<Person>();
		Person p = null;
		String query = "SELECT personId FROM Person;";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				int personId = rs.getInt(1);
				p = fetchData.fetchPerson(personId);
			}
			retPeople.add(p);
			rs.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return retPeople;
	}
	

	/**
	 * Creates a {@link java.util.List#List List} of {@link Item Item}'s from a csv
	 * file whos path is denoted STORES_FILE_PATH.
	 * 
	 * @param
	 * @return List&lt;Item>
	 */
	public static List<Item> loadItemsFromCSV() {
		List<Item> items = new ArrayList<Item>();
		String line = null;

		try (Scanner s = new Scanner(new File(ITEMS_FILE_PATH))) {
			int numItems = Integer.parseInt(s.nextLine());
			for (int j = 0; j < numItems; j++) {
				line = s.nextLine();
				if (!line.trim().isEmpty()) {
					Item i = null;
					String[] tokens = null;
					tokens = line.split(",");
					String code = tokens[0];
					String name = tokens[2];
					String model = null, unit = null;
					double hourlyRate = 0.0, unitPrice = 0.0;
					if (tokens[1].toUpperCase().equals("P")) {
						unit = tokens[3];
						unitPrice = Double.parseDouble(tokens[4]);
						i = new Product(code, name, unit, unitPrice);
					} else if (tokens[1].toUpperCase().equals("S")) {
						hourlyRate = Double.parseDouble(tokens[3]);
						i = new Service(code, name, hourlyRate);
					} else if (tokens[1].toUpperCase().equals("E")) {
						model = tokens[3];
						i = new Equipment(code, name, model);
					}
					items.add(i);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error encountered on line " + line, e);
		}

		return items;
	}

	/**
	 * Creates a {@link java.util.List#List List} of {@link Item Item}'s from the
	 * specified database
	 * 
	 * @param
	 * @return List&lt;Item>
	 */
	public static List<Item> loadItemsFromDataBase() {
		List<Item> retItems = new ArrayList<Item>();
		String query = "SELECT i.itemCode AS itemCode, " + "i.name AS itemName, " + "i.type AS itemType, "
				+ "i.unit AS itemUnit, " + "i.unitPrice AS itemUnitPrice, " + "i.model AS itemModel, "
				+ "i.rate AS itemRate " + "FROM Item i ORDER BY itemCode";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			Item i = null;
			while (rs.next()) {
				String code = rs.getString("itemCode");
				String name = rs.getString("itemName");
				String model = null, unit = null;
				double hourlyRate = 0.0, unitPrice = 0.0;
				if (rs.getString("itemType").equals("Service")) {
					hourlyRate = rs.getDouble("itemRate");
					i = new Service(code, name, hourlyRate);
				} else if (rs.getString("itemType").equals("Product")) {
					unit = rs.getString("itemUnit");
					unitPrice = rs.getDouble("itemUnitPrice");
					i = new Product(code, name, unit, unitPrice);
				} else if (rs.getString("itemType").equals("Equipment")) {
					model = rs.getString("itemModel");
					i = new Equipment(code, name, model);
				}
				retItems.add(i);
			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return retItems;
	}

	/**
	 * Creates a {@link java.util.List#List List} of {@link Store Store}'s from a
	 * csv file whos path is denoted STORES_FILE_PATH.
	 * 
	 * @param people
	 * @return List&lt;Store>
	 */
	public static List<Store> loadStoresFromCSV(List<Person> people) {
		List<Store> stores = new ArrayList<Store>();
		String line = null;

		try (Scanner sc = new Scanner(new File(STORES_FILE_PATH))) {
			int numStores = Integer.parseInt(sc.nextLine());
			for (int j = 0; j < numStores; j++) {
				line = sc.nextLine();
				if (!line.trim().isEmpty()) {
					Store s = null;
					String[] tokens = null;
					tokens = line.split(",");
					String code = tokens[0];
					String manCode = tokens[1];
					Person p = null;
					for (Person person : people) {
						if (person.getCode().equals(manCode))
							p = new Person(manCode, person.getLastName(), person.getFirstName(), person.getAddress(),
									person.getEmails());
					}
					String street = tokens[2];
					String city = tokens[3];
					String state = tokens[4];
					String zip = tokens[5];
					String country = tokens[6];
					Address a = new Address(street, city, state, zip, country);
					s = new Store(code, p, a);
					stores.add(s);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("!Error encountered on line " + line, e);
		}

		return stores;
	}

	/**
	 * Creates a {@link java.util.List#List List} of {@link Store Store}'s from the
	 * specified database
	 * 
	 * @param people
	 * @return List&lt;Store>
	 */
	public static List<Store> loadStoresFromDataBase(List<Person> people) {
		List<Store> retStores = new ArrayList<Store>();
		String query = "SELECT s.storeCode AS storeCode, " + "p.personCode AS manCode, " + "a.street AS street, "
				+ "a.city AS city, " + "a.state AS state, " + "a.zip AS zip, " + "a.country AS country "
				+ "FROM Store s LEFT JOIN Person p on s.managerId = p.personId JOIN Address a on s.addressId = a.addressId ORDER BY storeCode";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			Store s = null;
			while (rs.next()) {
				String code = rs.getString("storeCode");
				String manCode = rs.getString("manCode");
				Person p = null;
				for (Person persons : people) {
					if (persons.getCode().equals(manCode)) {
						p = new Person(manCode, persons.getLastName(), persons.getFirstName(), persons.getAddress(),
								persons.getEmails());
					}
				}
				Address a = new Address(rs.getString("street"), rs.getString("city"), rs.getString("state"),
						rs.getString("zip"), rs.getString("country"));
				s = new Store(code, p, a);
				retStores.add(s);
			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return retStores;
	}

	/**
	 * Creates a {@link java.util.List#List List} of {@link Invoice Invoice}'s from
	 * a csv file whos path is denoted INVOICE_DATA_FILE_PATH.
	 * 
	 * @param List&lt;Store>, List&lt;Person>, List&lt;Item>
	 * @return List&lt;Invoice>
	 */
	public static List<Invoice> loadInvoiceDataFromCSV(List<Store> stores, List<Person> people, List<Item> items) {
		List<Invoice> invoiceData = new ArrayList<>();
		String line = null;
		try (Scanner sc = new Scanner(new File(INVOICE_DATA_FILE_PATH))) {
			int numInvoices = Integer.parseInt(sc.nextLine());
			for (int j = 0; j < numInvoices; j++) {
				line = sc.nextLine();
				if (!line.trim().isEmpty()) {
					Invoice i = null;
					String[] tokens = null;
					tokens = line.split(",");
					String id = tokens[0];
					String storeCode = tokens[1]; // set store
					Store s = null;
					for (Store store : stores) {
						if (store.getCode().equals(storeCode))
							s = new Store(storeCode, store.getManager(), store.getAddress());
					}
					String personCode = tokens[2]; // set customer
					Person p = null;
					for (Person person : people) {
						if (person.getCode().equals(personCode))
							p = new Person(personCode, person.getLastName(), person.getFirstName(), person.getAddress(),
									person.getEmails());
					}
					String salePersonCode = tokens[3]; // set salesPerson
					Person saleP = null;
					for (Person person : people) {
						if (person.getCode().equals(salePersonCode))
							saleP = new Person(salePersonCode, person.getLastName(), person.getFirstName(),
									person.getAddress(), person.getEmails());
					}
					LocalDate date = LocalDate.parse(tokens[4]);

					List<Purchase> purchases = new ArrayList<>();
					String nestLine = null;
					try (Scanner nestSc = new Scanner(new File(INVOICE_ITEM_DATA_FILE_PATH))) {
						int numPurchases = Integer.parseInt(nestSc.nextLine());
						for (int k = 0; k < numPurchases; k++) {
							nestLine = nestSc.nextLine();
							if (!nestLine.trim().isEmpty()) {
								String[] nestTokens = null;
								nestTokens = nestLine.split(",");
								String invId = nestTokens[0];
								if (invId.equals(id)) {
									Purchase pur = null;
									String iCode = nestTokens[1];
									Item newItem = null;
									for (Item it : items) {
										if (it.getCode().equals(iCode)) {
											if (it instanceof Equipment) {
												newItem = new Equipment(iCode, it.getDescriptor(),
														((Equipment) it).getModel());
												if (nestTokens[2].toUpperCase().equals("P")) {
													String price = nestTokens[3];
													pur = new EquipmentPurchase((Equipment) newItem,
															Double.parseDouble(price));
												} else if (nestTokens[2].toUpperCase().equals("L")) {
													String fee = nestTokens[3];
													String startDate = nestTokens[4];
													String endDate = nestTokens[5];
													pur = new EquipmentLease((Equipment) newItem,
															Double.parseDouble(fee), LocalDate.parse(startDate),
															LocalDate.parse(endDate));
												}
											} else if (it instanceof Service) {
												newItem = new Service(iCode, it.getDescriptor(),
														((Service) it).getHourlyRate());
												String hoursBilled = nestTokens[2];
												pur = new ServicePurchase((Service) newItem,
														Double.parseDouble(hoursBilled));
											} else if (it instanceof Product) {
												newItem = new Product(iCode, it.getDescriptor(),
														((Product) it).getUnit(), ((Product) it).getUnitPrice());
												String quantity = nestTokens[2];
												pur = new ProductPurchase((Product) newItem,
														Double.parseDouble(quantity));
											}
											purchases.add(pur);
										}
									}
								}
							}
						}
					} catch (Exception e) {
						throw new RuntimeException("!Error encountered on line " + line, e);
					}
					i = new Invoice(id, s, p, saleP, date, purchases);
					invoiceData.add(i);

				}
			}
		} catch (Exception e) {
			throw new RuntimeException("!Error encountered on line " + line, e);
		}
		return invoiceData;
	}

	public static List<Invoice> loadInvoicesFromDataBase(List<Store> stores, List<Person> people, List<Item> items) {
		List<Invoice> retInvoices = new ArrayList<Invoice>();
		String query = "";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			Invoice i = null;
			while (rs.next()) {

			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return retInvoices;
	}

}
