package com.covalent.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class Test {

	/*public static void main(String[] args) {
		Test test = new Test();
		String sampleTxt = "hsdfgh.csv";
		sampleTxt = sampleTxt.substring(0, sampleTxt.length() - 4);
		System.out.println(sampleTxt);
		//System.out.println(test.replaceAbbrevations(test.getAbbreviations(), sampleTxt));
		//test.printThemAll();

	}
	
	private void printThemAll() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			String filename = "covalent.properties";
			input = getClass().getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
				return;
			}

			prop.load(input);

			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				System.out.println("Key : " + key + ", Value : " + value);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private Properties getAbbreviations() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			String filename = "abbreviation.properties";
			input = getClass().getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
			}
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}

	private  String replaceAbbrevations(Properties abbrevationsList, String sentence) {
		String newsentence = "";
		String[] words = sentence.split("\\s+"); // splits by whitespace
		for (String wordsInLine : words) {
			if (abbrevationsList.containsKey(wordsInLine)) {
				wordsInLine = (String) abbrevationsList.get(wordsInLine);
			}
			newsentence = newsentence + wordsInLine + " ";
		}
		return newsentence;
	}*/

}
