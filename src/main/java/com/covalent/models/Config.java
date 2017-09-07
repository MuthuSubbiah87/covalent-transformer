package com.covalent.models;

import org.springframework.data.annotation.Id;

public class Config {
	
	@Id
	private String id;

	private String inputFileLocation;

	private String outputFileLocation;

	private String customDictObj;
	
	private String standardDictLocation;
	
	private String ignoreDictObj;
	
	private String outputFileSuffix;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInputFileLocation() {
		return inputFileLocation;
	}

	public void setInputFileLocation(String inputFileLocation) {
		this.inputFileLocation = inputFileLocation;
	}

	public String getOutputFileLocation() {
		return outputFileLocation;
	}

	public void setOutputFileLocation(String outputFileLocation) {
		this.outputFileLocation = outputFileLocation;
	}

	public String getCustomDictObj() {
		return customDictObj;
	}

	public void setCustomDictObj(String customDictObj) {
		this.customDictObj = customDictObj;
	}

	public String getStandardDictLocation() {
		return standardDictLocation;
	}

	public void setStandardDictLocation(String standardDictLocation) {
		this.standardDictLocation = standardDictLocation;
	}

	public String getIgnoreDictObj() {
		return ignoreDictObj;
	}

	public void setIgnoreDictObj(String ignoreDictObj) {
		this.ignoreDictObj = ignoreDictObj;
	}

	public String getOutputFileSuffix() {
		return outputFileSuffix;
	}

	public void setOutputFileSuffix(String outputFileSuffix) {
		this.outputFileSuffix = outputFileSuffix;
	}

	@Override
	public String toString() {
		return "Config [id=" + id + ", inputFileLocation=" + inputFileLocation + ", outputFileLocation="
				+ outputFileLocation + ", customDictObj=" + customDictObj + ", standardDictLocation="
				+ standardDictLocation + ", ignoreDictObj=" + ignoreDictObj + ", outputFileSuffix=" + outputFileSuffix
				+ "]";
	}

}
