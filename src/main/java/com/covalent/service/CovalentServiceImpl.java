package com.covalent.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.PropertiesConfigurationFactory;
import org.springframework.stereotype.Service;

import com.covalent.models.CovalentConfig;
import com.covalent.models.FileModel;
import com.covalent.repository.CovalentRepository;


@Service
public final class CovalentServiceImpl implements CovalentService{
	
	final static Logger logger = Logger.getLogger(CovalentService.class);
	
	@Autowired
	private CovalentRepository covalentRepository;
	
	@Override
	public FileModel create(FileModel file) {
		FileModel fileModel = covalentRepository.save(file);
		return fileModel;
	}

	@Override
	public FileModel delete(String id) {
		covalentRepository.delete(id);
		return null;
	}

	@Override
	public List<FileModel> findAll() {
		List<FileModel> fileList = covalentRepository.findAll();
		return fileList;
	}

	@Override
	public FileModel findById(String id) {
		covalentRepository.findOne(id);
		return null;
	}

	@Override
	public FileModel update(FileModel fileModelIn) {
		FileModel fileModel = covalentRepository.save(fileModelIn);
		return fileModel;
	}
	
	@Override
	public String updateCovalentProperties() {
		String response = "success";
		try {
		PropertiesConfiguration config = new PropertiesConfiguration("covalent.properties");
		config.setProperty("covalent.upload.path", "/Test/");
		config.setProperty("covalent.download.path", "/Test/");
		config.setProperty("covalent.standard.dict.path", "/Test/");
		config.setProperty("covalent.fixed.file.suffix", "_test");
		config.save();
		
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			response = "failed";
		}
		return response;
	}
	
	@Override
	public CovalentConfig getCovalentConfig() {
		CovalentConfig config = new CovalentConfig();
		Properties prop = getCovalentProperty();
		config.setInputPath(prop.getProperty("covalent.upload.path"));
		config.setOutputPath(prop.getProperty("covalent.download.path"));
		return config;
	}
	
	private Properties getCovalentProperty() {
		return loadPropery("covalent.properties");
	}

	private Properties loadPropery(String filename) {
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
	}
	


}
