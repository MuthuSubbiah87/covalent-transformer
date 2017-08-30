package com.covalent.csvutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.covalent.spellchecker.SpellChker;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;

public class Test {

	/*public static void main(String[] args) throws IOException {
		Test test = new Test();
		CsvUtil csvUtil = new CsvUtil();
		CsvToBean<MetaData> csvToBean = new CsvToBean<MetaData>();
		CSVReader coavlentCsvReader = csvUtil.getCSVReader( "/Users/muthu/Desktop/upload_test.csv");
		List<MetaData> metaDataList = null;
		metaDataList = csvToBean.parse(csvUtil.getColumnStrategy(), coavlentCsvReader);
		long parsestart = System.currentTimeMillis();
		SpellChker spellChecker = new SpellChker("/Users/muthu/Desktop/covalent_files/dictionary/words.txt");
		long i =1;
		for (MetaData metaData : metaDataList) {
			System.out.println(metaData.getDescription() + "--->" + spellChecker.doCorrection(metaData.getDescription()) + "--->" +  i++);
			metaData.setDescription(spellChecker.doCorrection(metaData.getDescription()));
			metaData.setScriptSuperNotes(spellChecker.doCorrection(metaData.getScriptSuperNotes()));
			metaData.setCommentsTelecine(spellChecker.doCorrection(metaData.getCommentsTelecine()));
		}
		
		long parsetimeTook = System.currentTimeMillis() - parsestart;
		System.out.println("CSV Parsed successfully!!!");
		System.out.println("Time took for " + metaDataList.size() + " records is " + (parsetimeTook/1000) + " seconds");
		long writestart = System.currentTimeMillis();
		CSVWriter csvWriter = csvUtil.getCSVWriter("/Users/muthu/Desktop/test.csv");
		List<String[]> data = CsvUtil.toStringArray(metaDataList);
		csvWriter.writeAll(data);
        csvWriter.close();
        long writetimeTook = System.currentTimeMillis() - writestart;
        System.out.println("CSV File written successfully!!!");
        System.out.println("Time took for " + metaDataList.size() + " write " + (parsetimeTook/1000) + " seconds");

		coavlentCsvReader.close();

	}
	
	private String getCovalentProperty(String property) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			String filename = "covalent.properties";
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
		return prop.getProperty(property);
	}*/

}
