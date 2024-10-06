package com.fmt;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {

	/**
	 * Removes all records from all tables in the database.
	 */
	public static void clearDatabase() {
		Connection conn = null;
		PreparedStatement ps = null;
		String itemDelete = "DELETE FROM Item";
		String addressDelete = "DELETE FROM Address";
		String personDelete = "DELETE FROM Person";
		String emailDelete = "DELETE FROM Email";
		String storeDelete = "DELETE FROM Store";
		String invoiceDelete = "DELETE FROM Invoice";
		String purchaseDelete = "DELETE FROM Purchase";
		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(purchaseDelete);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(invoiceDelete);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(storeDelete);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(emailDelete);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(personDelete);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(addressDelete);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(itemDelete);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method to add a person record to the database with the provided data.
	 *
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city,
			String state, String zip, String country) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String personInsert = "INSERT INTO Person (personCode, lastName, firstName, addressId) VALUES (?, ?, ?, ?)";
		String addressQuery = "SELECT addressId FROM Address WHERE street = ? & city = ? & state = ? & zip = ? & country = ?";
		String addressInsert = "INSERT INTO Address (street, city, state, zip, country) VALUES (?, ?, ?, ?, ?)";
		int addressId;
		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(addressQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			rs = ps.executeQuery(); // see if address already exsists

			if (rs.next()) // if exsists return addressId
			{
				addressId = rs.getInt(1);
			} else // create one and return addressId
			{
				ps = conn.prepareStatement(addressInsert, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, street);
				ps.setString(2, city);
				ps.setString(3, state);
				ps.setString(4, zip);
				ps.setString(5, country);
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				rs.next();
				addressId = rs.getInt(1);
			}
			// insert final person
			ps = conn.prepareStatement(personInsert);
			ps.setString(1, personCode);
			ps.setString(2, lastName);
			ps.setString(3, firstName);
			ps.setInt(4, addressId);
			ps.executeUpdate();

			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 *
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String personQuery = "SELECT personId from Person WHERE personCode = ?";
		String emailInsert = "INSERT INTO Email (eAddress, personId) VALUES (?, ?)";
		int personId;
		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(personQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			rs.next();
			personId = rs.getInt(1); // return personId from personCode

			ps = conn.prepareStatement(emailInsert); // insert email
			ps.setString(1, email);
			ps.setInt(2, personId);
			ps.executeUpdate();

			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a store record to the database managed by the person identified by the
	 * given code.
	 *
	 * @param storeCode
	 * @param managerCode
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addStore(String storeCode, String managerCode, String street, String city, String state,
			String zip, String country) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String personQuery = "SELECT personId FROM Person WHERE personCode = ?";
		String storeInsert = "INSERT INTO Store (storeCode, managerId, addressId) VALUES (?, ?, ?)";
		String addressQuery = "SELECT addressId FROM Address WHERE street = ? & city = ? & state = ? & zip = ? & country = ?";
		String addressInsert = "INSERT INTO Address (street, city, state, zip, country) VALUES (?, ?, ?, ?, ?)";
		int addressId;
		int personId;
		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);

			ps = conn.prepareStatement(addressQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			rs = ps.executeQuery(); // see if address already exsists

			if (rs.next()) // if exsists return addressId
			{
				addressId = rs.getInt(1);
			} 
			else // create one and return addressId
			{
				ps = conn.prepareStatement(addressInsert, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, street);
				ps.setString(2, city);
				ps.setString(3, state);
				ps.setString(4, zip);
				ps.setString(5, country);
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				rs.next();
				addressId = rs.getInt(1);
			}

			ps = conn.prepareStatement(personQuery, Statement.RETURN_GENERATED_KEYS); // return personId for manager
			ps.setString(1, managerCode);
			rs = ps.executeQuery();
			rs.next();
			personId = rs.getInt(1);

			ps = conn.prepareStatement(storeInsert); // insert finalStore
			ps.setString(1, storeCode);
			ps.setInt(2, personId);
			ps.setInt(3, addressId);
			ps.executeUpdate();

			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a product record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>unit</code> and <code>pricePerUnit</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param unit
	 * @param pricePerUnit
	 */
	public static void addProduct(String code, String name, String unit, double pricePerUnit) {
		Connection conn = null;
		PreparedStatement ps = null;
		String productInsert = "INSERT INTO Item (itemCode, name, type, unitPrice) VALUES (?, ?, ?, ?)";
		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(productInsert);
			ps.setString(1, code);
			ps.setString(2, name);
			ps.setString(3, "Product");
			ps.setDouble(4, pricePerUnit);
			
			ps.executeUpdate();
				
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds an equipment record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>modelNumber</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param modelNumber
	 */
	public static void addEquipment(String code, String name, String modelNumber) {
		Connection conn = null;
		PreparedStatement ps = null;
		String equipmentInsert = "INSERT INTO Item (itemCode, name, type, model) VALUES (?, ?, ?, ?)";
		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(equipmentInsert);
			ps.setString(1, code);
			ps.setString(2, name);
			ps.setString(3, "Equipment");
			ps.setString(4, modelNumber);
			
			ps.executeUpdate();
				
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a service record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>costPerHour</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param modelNumber
	 */
	public static void addService(String code, String name, double costPerHour) {
		Connection conn = null;
		PreparedStatement ps = null;
		String serviceInsert = "INSERT INTO Item (itemCode, name, type, rate) VALUES (?, ?, ?, ?)";
		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(serviceInsert);
			ps.setString(1, code);
			ps.setString(2, name);
			ps.setString(3, "Service");
			ps.setDouble(4, costPerHour);
			
			ps.executeUpdate();
				
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds an invoice record to the database with the given data.
	 *
	 * @param invoiceCode
	 * @param storeCode
	 * @param customerCode
	 * @param salesPersonCode
	 * @param invoiceDate
	 */
	public static void addInvoice(String invoiceCode, String storeCode, String customerCode, String salesPersonCode,
			String invoiceDate) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String invoiceInsert = "INSERT INTO Invoice (invoiceCode, date, salePersonId, storeId, customerId) VALUES (?, ?, ?, ?, ?)";
		String queryStore = "SELECT storeId FROM Store WHERE storeCode = ?";
		int storeId;
		String queryPerson = "SELECT personId FROM Person WHERE personCode = ?";
		int cusId;
		int saleId;
		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(queryStore);	// get store id
			ps.setString(1, storeCode);
			rs = ps.executeQuery();
			rs.next();
			storeId = rs.getInt(1);
			
			ps = conn.prepareStatement(queryPerson); //get customer id
			ps.setString(1, customerCode);
			rs = ps.executeQuery();
			rs.next();
			cusId = rs.getInt(1);
			
			ps = conn.prepareStatement(queryPerson); 
			ps.setString(1, salesPersonCode);	//get salepersonId
			rs = ps.executeQuery();
			rs.next();
			saleId = rs.getInt(1);
			
			ps = conn.prepareStatement(invoiceInsert);	// inseert invoice
			ps.setString(1, invoiceCode);
			ps.setString(2, invoiceDate);
			ps.setInt(3, saleId);
			ps.setInt(4, storeId);
			ps.setInt(5, cusId);
			ps.executeUpdate();
			
			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a particular product (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified quantity.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param quantity
	 */
	public static void addProductToInvoice(String invoiceCode, String itemCode, int quantity) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String invoiceInsert = "INSERT INTO Purchase (type, itemId, invoiceId, quantity) VALUES (?, ?, ?, ?)";
		String itemQuery = "SELECT itemId FROM Item WHERE itemCode = ?";
		String invoiceQuery = "SELECT invoiceId FROM Invoice WHERE invoiceCode = ?";
		int itemId;
		int invoiceId;
		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(itemQuery);	// get Item id
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			rs.next();
			itemId = rs.getInt(1);
			
			ps = conn.prepareStatement(invoiceQuery);	// get Invoice id
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			rs.next();
			invoiceId = rs.getInt(1);
			
			ps = conn.prepareStatement(invoiceInsert);	// insert invoice
			ps.setString(1, "Product Purchase");
			ps.setInt(2, itemId);
			ps.setInt(3, invoiceId);
			ps.setInt(4, quantity);
			
			ps.executeUpdate();
			
			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a particular equipment <i>purchase</i> (identified by
	 * <code>itemCode</code>) to a particular invoice (identified by
	 * <code>invoiceCode</code>) at the given <code>purchasePrice</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param purchasePrice
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double purchasePrice) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String invoiceInsert = "INSERT INTO Purchase (type, itemId, invoiceId, price) VALUES (?, ?, ?, ?)";
		String itemQuery = "SELECT itemId FROM Item WHERE itemCode = ?";
		String invoiceQuery = "SELECT invoiceId FROM Invoice WHERE invoiceCode = ?";
		int itemId;
		int invoiceId;
		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(itemQuery);	// get Item id
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			rs.next();
			itemId = rs.getInt(1);
			
			ps = conn.prepareStatement(invoiceQuery);	// get Invoice id
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			rs.next();
			invoiceId = rs.getInt(1);
			
			ps = conn.prepareStatement(invoiceInsert);	// insert invoice
			ps.setString(1, "Equipment Purchase");
			ps.setInt(2, itemId);
			ps.setInt(3, invoiceId);
			ps.setDouble(4, purchasePrice);
			
			ps.executeUpdate();
			
			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular equipment <i>lease</i> (identified by
	 * <code>itemCode</code>) to a particular invoice (identified by
	 * <code>invoiceCode</code>) with the given 30-day <code>periodFee</code> and
	 * <code>beginDate/endDate</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param amount
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double periodFee, String beginDate,
			String endDate) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String invoiceInsert = "INSERT INTO Purchase (type, itemId, invoiceId, monthlyFee, startDate, endDate) VALUES (?, ?, ?, ?, ?, ?)";
		String itemQuery = "SELECT itemId FROM Item WHERE itemCode = ?";
		String invoiceQuery = "SELECT invoiceId FROM Invoice WHERE invoiceCode = ?";
		int itemId;
		int invoiceId;
		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(itemQuery);	// get Item id
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			rs.next();
			itemId = rs.getInt(1);
			
			ps = conn.prepareStatement(invoiceQuery);	// get Invoice id
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			rs.next();
			invoiceId = rs.getInt(1);
			
			ps = conn.prepareStatement(invoiceInsert);	// insert invoice
			ps.setString(1, "Equipment Lease");
			ps.setInt(2, itemId);
			ps.setInt(3, invoiceId);
			ps.setDouble(4, periodFee);
			ps.setString(5, beginDate);
			ps.setString(6, endDate);
			
			ps.executeUpdate();
			
			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a particular service (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified number of hours.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param billedHours
	 */
	public static void addServiceToInvoice(String invoiceCode, String itemCode, double billedHours) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String invoiceInsert = "INSERT INTO Purchase (type, itemId, invoiceId, hoursBilled) VALUES (?, ?, ?, ?)";
		String itemQuery = "SELECT itemId FROM Item WHERE itemCode = ?";
		String invoiceQuery = "SELECT invoiceId FROM Invoice WHERE invoiceCode = ?";
		int itemId;
		int invoiceId;
		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(itemQuery);	// get Item id
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			rs.next();
			itemId = rs.getInt(1);
			
			ps = conn.prepareStatement(invoiceQuery);	// get Invoice id
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			rs.next();
			invoiceId = rs.getInt(1);
			
			ps = conn.prepareStatement(invoiceInsert);	// insert invoice
			ps.setString(1, "Equipment Lease");
			ps.setInt(2, itemId);
			ps.setInt(3, invoiceId);
			ps.setDouble(4, billedHours);
			
			ps.executeUpdate();
			
			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

}