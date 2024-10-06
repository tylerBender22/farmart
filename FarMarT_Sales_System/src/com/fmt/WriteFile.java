package com.fmt;

import java.io.FileWriter;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WriteFile {

	/**
	 * Exports a given {@link java.util.List#List List} of the type
	 * {@link com.fmt.Equipment#Equipment Equipment}, {@link com.fmt.Product#Product
	 * roduct}, or {@link com.fmt.Service#Service Service} as a JSON file using
	 * {@link com.google.gson.Gson#Gson Gson}.
	 * 
	 * @param list
	 * @param path
	 */
	public static void ToJSON(List<?> list, String path) {
		try {
			FileWriter fw = new FileWriter(path);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(list, fw);
			fw.close();

		} catch (Exception e) {
			throw new RuntimeException("Error writing to file" + path, e);
		}
	}
}
