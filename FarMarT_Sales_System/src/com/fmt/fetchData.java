package com.fmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class fetchData {

	public static Address fetchAddress(int addressId) {
		Address addr = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "select street,city,state,zip,country from Address where addressId = ?;";

		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(query);
			ps.setInt(1, addressId);
			rs = ps.executeQuery();

			if (rs.next()) {
				String street = rs.getString(1);
				String city = rs.getString(2);
				String state = rs.getString(3);
				String zip = rs.getString(4);
				String country = rs.getString(5);

				addr = new Address(street, city, state, zip, country);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return addr;
	}

	public static Person fetchPerson(int personId) {
		Person person = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select personCode,lastName,firstName,addressId from Person where personId = ?;";

		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			rs = ps.executeQuery();

			if (rs.next()) {
				String code = rs.getString(1);
				String lastName = rs.getString(2);
				String firstName = rs.getString(3);
				int addressId = rs.getInt(4);

				List<String> emails = new ArrayList<String>();

				String emailsQuery = "select eAddress from Email where personId = ?;";
				PreparedStatement eps = conn.prepareStatement(emailsQuery);
				eps.setInt(1, personId);

				ResultSet ers = eps.executeQuery();
				while (ers.next()) {
					emails.add(ers.getString(1));
				}
				person = new Person(code, lastName, firstName, fetchAddress(addressId), emails);
				ers.close();
				eps.close();
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return person;
	}

	public static Store fetchStore(int storeId) {
		Store store = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select storeCode,managerId,addressId from Store where storeId = ?;";

		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(query);
			ps.setInt(1, storeId);
			rs = ps.executeQuery();

			if (rs.next()) {
				String code = rs.getString(1);
				int personId = rs.getInt(2);
				int addressId = rs.getInt(3);

				Person manager = fetchPerson(personId);
				Address address = fetchAddress(addressId);

				store = new Store(code, manager, address);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return store;
	}

	public static Item fetchItem(int itemId) {
		Item item = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select itemId,itemCode,name,type," + "unit,unitPrice,model,rate"
				+ " from Item where itemId = ?;";

		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(query);
			ps.setInt(1, itemId);
			rs = ps.executeQuery();

			if (rs.next()) {
				String code = rs.getString(2);
				String name = rs.getString(3);
				String type = rs.getString(4);

				switch (type) {
				case "E":
					String model = rs.getString(7);
					item = new Equipment(code, name, model);
					break;
				case "S":
					double rate = rs.getDouble(8);
					item = new Service(code, name, rate);
					break;
				case "P":
					String unit = rs.getString(5);
					double pricePerUnit = rs.getDouble(6);
					item = new Product(code, name, unit, pricePerUnit);
					break;
				default:
				}
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return item;
	}

	public static Invoice fetchInvoice(int invoiceId) {
		Invoice inv = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select invoiceId,invoiceCode,date,salesPersonId,storeId,customerId"
				+ " from Invoice where invoiceId = ?;";

		try {
			conn = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
			ps = conn.prepareStatement(query);
			ps.setInt(1, invoiceId);
			rs = ps.executeQuery();

			if (rs.next()) {

				String code = rs.getString(2);
				LocalDate date = LocalDate.parse(rs.getString(3));
				Person salesPerson = fetchPerson(rs.getInt(4));
				Store store = fetchStore(rs.getInt(5));
				Person customer = fetchPerson(rs.getInt(6));
					
				List<Purchase> sales = new ArrayList<Purchase>();
				
				// fetch all associated purchases
				
				String purchaseQuery = "select purchaseId,type,itemId,invoiceId,"
						+ "quantity,hoursBilled,price,monthlyFee,startDate,endDate"
						+ " from Purchase where invoiceId = ?"; 
				PreparedStatement pps = conn.prepareStatement(purchaseQuery); 
				pps.setInt(1, invoiceId);
				ResultSet prs = pps.executeQuery(); 
				
				while(prs.next()) { 
					String type = prs.getString(2);
					Item i = fetchItem(prs.getInt(3)); 
					Purchase p = null; 
					
					switch(type) { 
					case "P": 
						double quantity = prs.getDouble(5);
						p = new ProductPurchase((Product)i,quantity);
						break; 
					case "S":
						double hours = prs.getDouble(6);
						p = new ServicePurchase((Service)i,hours);
						break; 
					case "E":
						double price = prs.getDouble(7);
						p = new EquipmentPurchase((Equipment)i,price);
						break; 
					case "L": 
						double fee = prs.getDouble(8);
						LocalDate start = LocalDate.parse(prs.getString(9));
						LocalDate end = LocalDate.parse(prs.getString(10));
						p = new EquipmentLease((Equipment)i,fee,start,end);
						break;
					}
					
					sales.add(p);
				}
				inv = new Invoice(code,store,customer,salesPerson,date, sales);
				prs.close();
				pps.close(); 
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return inv;
	}
	
}
