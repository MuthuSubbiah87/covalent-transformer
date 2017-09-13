package com.covalent.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.covalent.common.CommonUtils;
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

	final static Logger logger = Logger.getLogger(FilesProcessService.class);

	@Autowired
	private final CovalentService service;
	
	private Properties covalentProperties;

	@Autowired
	FilesProcessService(CovalentService service) {
		this.service = service;
	}

	@Async
	public CompletableFuture<Iterable<FileModel>> processTheFile(
			@RequestParam("file") MultipartFile file, byte[] bytes)
			throws InterruptedException, IOException {
		logger.info("Got file to process");
		// Artificial delay of 1s for demonstration purposes
		return CompletableFuture.completedFuture(processFile(file, bytes));
	}

	private List<FileModel> processFile(@RequestParam("file") MultipartFile file, byte[] bytes)
			throws IOException {
		List<FileModel> fileList = null;
		try {
			/** Uploading File **/
			//logger.debug("Inside process method");
			//byte[] bytes = file.getBytes();
			logger.debug("Current Thread Name: " + Thread.currentThread());
			covalentProperties = getCovalentProperty();
			Path path = Paths.get(covalentProperties.getProperty("covalent.upload.path")
					+ file.getOriginalFilename());
			Files.write(path, bytes);
			FileModel fileModel = new FileModel();
			fileModel.setFileName(file.getOriginalFilename());
			fileModel.setFileUrl("/upload/" + fileModel.getFileName());
			fileModel.setFixedFileStatus(CovalentConstants.FILE_UPLOADED);
			fileModel = service.create(fileModel);
			logger.info("Upload Completed" + fileModel.toString());

			/** Parsing File **/
			List<MetaData> metaDataList = parseCSVFile(file
					.getOriginalFilename());
			fileModel.setRowCount(metaDataList.size());
			fileModel.setFixedFileStatus(CovalentConstants.FILE_PARSED);
			service.update(fileModel);
			logger.info("Parsing Completed");

			/** Transforming File **/
			List<MetaData> transFormedMetaDataList = transformData(
					metaDataList, fileModel, service);
			fileModel.setFixedFileStatus(CovalentConstants.FILE_TRANSFORMED);
			service.update(fileModel);
			logger.info("Data transformation Completed");

			/** Writing File **/
			writeTransformedData(file.getOriginalFilename(),
					transFormedMetaDataList);
			String fixedFile = file.getOriginalFilename();
			fileModel.setFixedFileName(fixedFile.substring(0,
					fixedFile.length() - 4)
					+ covalentProperties.getProperty("covalent.fixed.file.suffix")
					+ ".csv");
			logger.debug("Fixed file name: " + fileModel.getFixedFileName());
			fileModel.setFixedFileUrl("/download/" + fileModel.getFixedFileName());
			fileModel.setFixedFileStatus(CovalentConstants.FILE_FIXED);
			service.update(fileModel);
			logger.info("CSV generated Completed");

			fileList = service.findAll();
			logger.info("Fetched all records");

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

	public List<FileModel> getAllFilesList(){
		List<FileModel> fileList = service.findAll();
		return fileList;
	}

	private void writeTransformedData(String fileName,
			List<MetaData> metaDataList) {
		CsvUtil csvUtil = new CsvUtil();
		fileName = fileName.substring(0, fileName.length() - 4)
				+ covalentProperties.getProperty("covalent.fixed.file.suffix") + ".csv";
		CSVWriter csvWriter = csvUtil
				.getCSVWriter(covalentProperties.getProperty("covalent.download.path")
						+ fileName);
		List<String[]> data = CsvUtil.toStringArray(metaDataList);
		System.out.println(data);
		System.out.println(covalentProperties.getProperty("covalent.download.path")
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
				.getCSVReader(covalentProperties.getProperty("covalent.upload.path")
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
				covalentProperties.getProperty("covalent.standard.dict.path"),
				getIgnoreWordDictonary(), getCustomDictonary());
		Properties abbProperties = getIgnoreWordDictonary();
		int i = 0;
		for (MetaData metaData : metaDataList) {
			try {
				logger.info("-----------------------------------Spell Check Process Row " + i +"-----------------------------------");
				logger.info("Covalent Name input: "+metaData.getName()+", UTF8:"+URLEncoder.encode(metaData.getName(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(metaData.getName().contains("•")){ //"•"
				metaData.setCircleTake(metaData.YES);
				//metaData.setName(metaData.getName().substring(0,metaData.getName().length() - 1));
				logger.info("Invalid dot character present in Name field");
			}
			
			metaData.setName(metaData.getName() != null? removeInvalidChar(metaData.getName()):"");

			metaData.setDescription(spellChecker.doCorrection(metaData
					.getDescription()));
			metaData.setScriptSuperNotes(spellChecker.doCorrection(metaData
					.getScriptSuperNotes()));
			metaData.setCommentsTelecine(spellChecker.doCorrection(metaData
					.getCommentsTelecine()));
			
			
			metaData.setVfxSetId(metaData.getVfxSetId() != null? removeInvalidChar(metaData.getVfxSetId()):"");
			String msg = "Processed: " + i++ + "/" + fileModel.getRowCount();
			
			JSONObject obj = new JSONObject();
	        obj.put("processed",  new Integer(i));
	        obj.put("total", new Integer(fileModel.getRowCount()));
	        pusher.websocketNotify(obj.toJSONString());
			System.out.println(msg);
			fileModel.setFixedFileStatus(msg);
			service.update(fileModel);
		}
		return metaDataList;
	}
	
	private  String removeInvalidChar(String line) {
		String correctedString = line;
		correctedString = line.replaceAll("[^\\x20-\\x7e]", "");
		logger.debug("Ivalid Character corrected: UTFrem" + correctedString);
		return correctedString;
	}



	private Properties getIgnoreWordDictonary() {
		return CommonUtils.loadProperyOut("ignore.properties");
	}

	private Properties getCustomDictonary() {
		return CommonUtils.loadProperyOut("replace.properties");
	}
	
	private Properties getCovalentProperty() {
		return CommonUtils.loadProperyOut("covalent.properties");
	}

	/*private Properties loadPropery(String filename) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = getClass().getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				logger.info("Sorry, unable to find " + filename);
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
	}*/
	
	/*private Properties loadProperyOut(String filename) {
		Properties prop = new Properties();
	    try {
	        File jarPath=new File(FilesProcessService.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	        String propertiesPath = jarPath.getPath().substring(0,jarPath.getPath().length() - 47);
	        String finalPath = propertiesPath.substring(5, propertiesPath.length());
	        File prp = new File(finalPath + filename);
	        System.out.println(" propertiesPath-" + finalPath + filename);
	        prop.load(new FileInputStream(prp));
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    }
		return prop;
	}*/

}