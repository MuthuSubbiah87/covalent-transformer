package com.covalent.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.covalent.common.CovalentConstants;
import com.covalent.csvutils.CsvUtil;
import com.covalent.csvutils.MetaData;
import com.covalent.models.FileModel;
import com.covalent.spellchecker.SpellChker;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;

@Service
public class FilesProcessService {

	private static final Logger logger = LoggerFactory
			.getLogger(FilesProcessService.class);

	@Autowired
	private final CovalentService service;

	@Autowired
	FilesProcessService(CovalentService service) {
		this.service = service;
	}

	@Async
	public CompletableFuture<Iterable<FileModel>> processTheFile(
			@RequestParam("file") MultipartFile file)
			throws InterruptedException, IOException {
		System.out.println("processTheFile Looking up users .... ");
		// Artificial delay of 1s for demonstration purposes
		return CompletableFuture.completedFuture(processFile(file));
	}

	private List<FileModel> processFile(@RequestParam("file") MultipartFile file)
			throws IOException {
		List<FileModel> fileList = null;
		try {
			/** Uploading File **/
			System.out.println("processFile");

			byte[] bytes = file.getBytes();
			System.out.println(" after file.getBytes()");
			System.out.println(Thread.currentThread());
			Path path = Paths.get(getCovalentProperty("covalent.upload.path")
					+ file.getOriginalFilename());
			Files.write(path, bytes);
			FileModel fileModel = new FileModel();
			fileModel.setFileName(file.getOriginalFilename());
			fileModel.setFileUrl(getCovalentProperty("covalent.upload.path")
					+ file.getOriginalFilename());
			fileModel.setFixedFileStatus(CovalentConstants.FILE_UPLOADED);
			fileModel = service.create(fileModel);
			System.out.println("Upload Completed" + fileModel.toString());

			/** Parsing File **/
			List<MetaData> metaDataList = parseCSVFile(file
					.getOriginalFilename());
			fileModel.setRowCount(metaDataList.size());
			fileModel.setFixedFileStatus(CovalentConstants.FILE_PARSED);
			service.update(fileModel);
			System.out.println("Parsing Completed");

			/** Transforming File **/
			List<MetaData> transFormedMetaDataList = transformData(
					metaDataList, fileModel, service);
			fileModel.setFixedFileStatus(CovalentConstants.FILE_TRANSFORMED);
			service.update(fileModel);
			System.out.println("Data transformation Completed");

			/** Writing File **/
			writeTransformedData(file.getOriginalFilename(),
					transFormedMetaDataList);
			String fixedFile = file.getOriginalFilename();
			fileModel.setFixedFileName(fixedFile.substring(0,
					fixedFile.length() - 4)
					+ getCovalentProperty("covalent.fixed.file.suffix")
					+ ".csv");
			fileModel
					.setFixedFileUrl(getCovalentProperty("covalent.download.path")
							+ fixedFile.substring(0, fixedFile.length() - 4)
							+ getCovalentProperty("covalent.fixed.file.suffix")
							+ ".csv");
			fileModel.setFixedFileStatus(CovalentConstants.FILE_FIXED);
			service.update(fileModel);
			System.out.println("CSV generated Completed");

			fileList = service.findAll();
			System.out.println("Fetched all records");

			/** Response **/
			/*
			 * redirectAttributes.addFlashAttribute("message",
			 * "Fixed CSV file generated succuessfully' " +
			 * getCovalentProperty("covalent.download.path") +
			 * fixedFile.substring(0, fixedFile.length() - 4) +
			 * getCovalentProperty("covalent.fixed.file.suffix") + ".csv" +
			 * "'");
			 * 
			 * List<FileModel> fileList = service.findAll();
			 * redirectAttributes.addFlashAttribute("fileList", fileList);
			 */
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return fileList;

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
	}

	private void writeTransformedData(String fileName,
			List<MetaData> metaDataList) {
		CsvUtil csvUtil = new CsvUtil();
		fileName = fileName.substring(0, fileName.length() - 4)
				+ getCovalentProperty("covalent.fixed.file.suffix") + ".csv";
		CSVWriter csvWriter = csvUtil
				.getCSVWriter(getCovalentProperty("covalent.download.path")
						+ fileName);
		List<String[]> data = CsvUtil.toStringArray(metaDataList);
		System.out.println(data);
		System.out.println(getCovalentProperty("covalent.download.path")
				+ fileName);
		csvWriter.writeAll(data);
		try {
			csvWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<MetaData> parseCSVFile(String fileName) {
		CsvUtil csvUtil = new CsvUtil();
		CsvToBean<MetaData> csvToBean = new CsvToBean<MetaData>();
		CSVReader coavlentCsvReader = csvUtil
				.getCSVReader(getCovalentProperty("covalent.upload.path")
						+ fileName);
		List<MetaData> metaDataList = csvToBean.parse(
				csvUtil.getColumnStrategy(), coavlentCsvReader);
		try {
			coavlentCsvReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return metaDataList;
	}

	@Autowired
	private WSPusher pusher;

	private List<MetaData> transformData(List<MetaData> metaDataList,
			FileModel fileModel, CovalentService service2) {
		SpellChker spellChecker = new SpellChker(
				getCovalentProperty("covalent.standard.dict.path"),
				getIgnoreWordDictonary(), getCustomDictonary());
		Properties abbProperties = getIgnoreWordDictonary();
		int i = 0;
		for (MetaData metaData : metaDataList) {
			metaData.setDescription(replaceAbbrevations(abbProperties,
					metaData.getDescription()));
			metaData.setScriptSuperNotes(replaceAbbrevations(abbProperties,
					metaData.getScriptSuperNotes()));
			metaData.setCommentsTelecine(replaceAbbrevations(abbProperties,
					metaData.getCommentsTelecine()));
			metaData.setDescription(spellChecker.doCorrection(metaData
					.getDescription()));
			metaData.setScriptSuperNotes(spellChecker.doCorrection(metaData
					.getScriptSuperNotes()));
			metaData.setCommentsTelecine(spellChecker.doCorrection(metaData
					.getCommentsTelecine()));
			String msg = "Processed: " + i++ + "/" + fileModel.getRowCount();
			pusher.websocketNotify(msg);
			System.out.println(msg);
			fileModel.setFixedFileStatus(msg);
			service.update(fileModel);
		}
		return metaDataList;
	}

	private Properties getIgnoreWordDictonary() {
		return loadPropery("ignore.properties");
	}

	private Properties getCustomDictonary() {
		return loadPropery("replace.properties");
	}

	private Properties loadPropery(String filename) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
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

	private String replaceAbbrevations(Properties abbrevationsList,
			String sentence) {
		String newsentence = "";
		String[] words = sentence.split("\\s+"); // splits by whitespace
		for (String wordsInLine : words) {
			if (abbrevationsList.containsKey(wordsInLine)) {
				wordsInLine = (String) abbrevationsList.get(wordsInLine);
			}
			newsentence = newsentence + wordsInLine + " ";
		}
		return newsentence;
	}

}