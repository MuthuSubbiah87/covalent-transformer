package com.covalent.models;

import org.springframework.data.annotation.Id;

public class FileModel {

	public FileModel() {

	}

	public FileModel(String id, String fileName, String fileUrl, String fixedFileName, String fixedFileUrl,
			String fixedFileStatus, int rowCount) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.fixedFileName = fixedFileName;
		this.fixedFileUrl = fixedFileUrl;
		this.fixedFileStatus = fixedFileStatus;
		this.rowCount = rowCount;
	}

	@Id
	private String id;

	private String fileName;

	private String fileUrl;

	private String fixedFileName;

	private String fixedFileUrl;

	private String fixedFileStatus;
	
	private int rowCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFixedFileName() {
		return fixedFileName;
	}

	public void setFixedFileName(String fixedFileName) {
		this.fixedFileName = fixedFileName;
	}

	public String getFixedFileUrl() {
		return fixedFileUrl;
	}

	public void setFixedFileUrl(String fixedFileUrl) {
		this.fixedFileUrl = fixedFileUrl;
	}

	public String getFixedFileStatus() {
		return fixedFileStatus;
	}

	public void setFixedFileStatus(String fixedFileStatus) {
		this.fixedFileStatus = fixedFileStatus;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	@Override
	public String toString() {
		return "FileModel [id=" + id + ", fileName=" + fileName + ", fileUrl="
				+ fileUrl + ", fixedFileName=" + fixedFileName
				+ ", fixedFileUrl=" + fixedFileUrl + ", fixedFileStatus="
				+ fixedFileStatus + ", rowCount=" + rowCount + "]";
	}

	
}
